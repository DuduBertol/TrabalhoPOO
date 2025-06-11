package Programa02;

import Programa02.Databases.*;
import Programa02.Exception.IdInvalidoException;
import Programa02.Exception.PeriodoInvalidoException;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;


//Faz o meio campo entre a database antiga e a view
    //assim tirei o trabalho da main de ler os CSVs por lá
public class MedicoService {

    Medico medico;

    //Medicos Database
    MedicosDB medicosDB = new MedicosDB();

    //Pacientes Database
    PacientesDB pacientesDB = new PacientesDB();

    //Consultas Database
    ConsultasDB consultasDB = new ConsultasDB();

    // CSVs FILE READER - Pesistência de Dados
    public void fileReader(){
        medicosDB.readFile();
        pacientesDB.readFile();
        consultasDB.readFile();
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(int id){

        String nome = medicosDB.getNameFromID(id);
        ArrayList<String> cpfsPacientes = consultasDB.getCPFsFromID(id);
        ArrayList<Paciente> pacientes = new ArrayList<>();
        for (String cpf : cpfsPacientes) {
            pacientes.add(pacientesDB.createPacienteFromCPF(cpf, consultasDB.getConsultasByCPF(cpf)));
        }

        //Seta o Objeto do Médico dessa sessão
        medico = new Medico(nome, id, pacientes);

        System.out.printf("Seja Bem Vindo - %s! \n", medico.getNome());
    }

    public String presetRespostaPesquisaMedico(){
        return String.format("%s | ID: %d\n\n", medico.getNome(), medico.getId());
    }

    public boolean validarMedico(int id) throws IdInvalidoException {
        if(consultasDB.isIDValid(id)){
            setMedico(id);
            return true;
        }
        else{
            throw new IdInvalidoException("O ID do médico deve estar entre 1000 e 1050.");
        }
    }


    // 03 OPCOES DE PESQUISA - MEDICO

    public String consultarTodosPacientes() {
        String resultado = "";
        resultado += presetRespostaPesquisaMedico();

        ArrayList<Paciente> pacientes = medico.getPacientes(); // Paciente não pode ser Set<> pois o objeto recebe um ID único
        Set<String> cpfs = new HashSet<>(); // CPF é sempre igual, então cai na ausência de duplicatas do Set<>
        for (Paciente paciente : pacientes) {
            cpfs.add(paciente.getCpf());
        }

        resultado += String.format("Pacientes (%d) \n", cpfs.size());

        for (String cpf : cpfs) {
            resultado += String.format("  > Paciente: " + pacientesDB.getNameFromCpf(cpf));
            resultado += String.format(" | CPF: " + cpf + "\n");

            for (Consulta consulta : consultasDB.getConsultasByID(medico.getId())) {
                if (Objects.equals(consulta.getCpf(), cpf)) {
                    resultado += String.format("    >> Data: %s | Horário: %s \n", consulta.getData().toString(), consulta.getHorario());
                }
            }
            resultado += ("\n");
        }

        return resultado;
    }

    public String consultarPorPeriodo(String mesInicio, String mesFinal) throws PeriodoInvalidoException {
        if (Integer.parseInt(mesInicio) == Integer.parseInt(mesFinal)) {
            throw new PeriodoInvalidoException("Meses iguais");
        }
        else if (Integer.parseInt(mesInicio) > Integer.parseInt(mesFinal)) {
            throw new PeriodoInvalidoException("Mês de início maior que Mês final.");
        }
        else if (Integer.parseInt(mesInicio) > 12 || Integer.parseInt(mesFinal) > 12) {
            throw new PeriodoInvalidoException("Mês maior que 12.");
        }
        else if (Integer.parseInt(mesInicio) < 0 || Integer.parseInt(mesFinal) < 0) {
            throw new PeriodoInvalidoException("Mês menor que 0.");
        }


        String resultado = "";
        resultado += presetRespostaPesquisaMedico();

        ArrayList<Consulta> consultasPeriodo = consultasDB.visualizaConsultasPeriodo(medico.getId(), mesInicio, mesFinal);

        if(consultasPeriodo.isEmpty()){ return ("Nenhuma consulta encontrada!"); }

        resultado += String.format("Consultas: (%d)", consultasPeriodo.size());
        resultado += ("\n");

        for (Consulta consulta: consultasPeriodo){
            resultado += String.format("Data: " + consulta.getData().format(DateTimeFormatter.ofPattern("dd-MM-uuuu")));
            resultado += String.format(" | Horario: " + consulta.getHorario());
            resultado += String.format(" | Paciente: " + pacientesDB.createPacienteFromCPF(consulta.getCpf(), consultasDB.getConsultasByCPF(consulta.getCpf())).getNome());
            resultado += String.format("\n");
        }

        return resultado;
    }

    public String consultarAusentes(int meses) throws Exception {
        if (meses < 0) {
            throw new Exception("Quantidade inválida.");
        }


        String resultado = "";
        resultado += presetRespostaPesquisaMedico();

        int dias = meses * 30;

        ArrayList<Consulta> consultasPeriodo = consultasDB.getConsultaPassadasMesesAtras(medico.getId(), dias);

        if(consultasPeriodo.isEmpty()){ return ("Nenhuma consulta encontrada!"); }

        resultado += String.format("Pacientes (%d)\n", consultasPeriodo.size());
        for (Consulta consulta: consultasPeriodo) {
            resultado += String.format("  > " + pacientesDB.createPacienteFromCPF(consulta.getCpf(), consultasDB.getConsultasByCPF(consulta.getCpf())).getNome());
            resultado += String.format(" | " + consulta.getCpf());
            resultado += String.format(" | " + consulta.getData().format(DateTimeFormatter.ofPattern("dd-MM-uuuu")));
            resultado += String.format("\n");
        }

        return resultado;
    }

    // Exportar pesquisa
    public void exportarResultado(String resultado){
        String textoParaExportar = resultado;

        // Seletor de Arquivos
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar Resultados");

        // Nome default
        String nomeArquivoSugerido = "resultados-" + new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date()) + ".txt";
        fileChooser.setSelectedFile(new File(nomeArquivoSugerido));

        int selecao = fileChooser.showSaveDialog(null);

        // Se o usuário confirmar a seleção
        if (selecao == JFileChooser.APPROVE_OPTION) {
            File arquivoParaSalvar = fileChooser.getSelectedFile();

            // Arquivo com extensão .txt
            if (!arquivoParaSalvar.getName().toLowerCase().endsWith(".txt")) {
                arquivoParaSalvar = new File(arquivoParaSalvar.getParentFile(), arquivoParaSalvar.getName() + ".txt");
            }

            // 3. Tenta escrever o arquivo (com tratamento de exceções)
            // Usando try-with-resources para garantir que o FileWriter seja fechado automaticamente
            try (FileWriter writer = new FileWriter(arquivoParaSalvar)) {
                writer.write(textoParaExportar);
                JOptionPane.showMessageDialog(null,
                        "Arquivo salvo com sucesso em:\n" + arquivoParaSalvar.getAbsolutePath(),
                        "Exportação Concluída",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,
                        "Erro ao salvar o arquivo: " + ex.getMessage(),
                        "Erro de I/O",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
