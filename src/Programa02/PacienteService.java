package Programa02;

import Programa02.Databases.*;
import Programa02.Exception.CPFInvalidoException;
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
public class PacienteService {

    Paciente paciente;

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

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(String cpf){
        String nome = pacientesDB.getNameFromCpf(cpf);
        ArrayList<Consulta> consultas = consultasDB.getConsultasByCPF(cpf);

        //Cria o Objeto do Paciente dessa sessão
        paciente = new Paciente(nome, cpf, consultas);

        System.out.printf("Seja Bem Vindo - %s! \n", paciente.getNome());
    }

    public String presetRespostaPesquisaPaciente(){
        return String.format("%s | CPF: %s\n\n", paciente.getNome(), paciente.getCpf());
    }

    public boolean validarPaciente(String cpf) throws CPFInvalidoException {
        if(consultasDB.isCpfValid(cpf)){
            setPaciente(cpf);
            return true;
        }
        else{
            throw new CPFInvalidoException("O CPF deve existir e ser válido.");
        }
    }


    // 03 OPCOES DE PESQUISA - PACIENTE
    public String consultarTodosMedicos(){
        String resultado = "";
        resultado += presetRespostaPesquisaPaciente();

        ArrayList<Consulta> consultas = paciente.getConsultas();
        Set<Integer> medicosIDs = new HashSet<>();
        for (Consulta consulta : consultas) {
            medicosIDs.add(consulta.getId());
        }

        resultado += String.format("Médicos (%d) \n", medicosIDs.size());

        for (Integer medicoID : medicosIDs) {
            resultado += String.format("  >> Médico: " + medicosDB.getNameFromID(medicoID));
            resultado += String.format(" | ID: " + medicoID + "\n");

            for (Consulta consulta : consultas) {
                if (consulta.getId() == medicoID) {
                    resultado += String.format("    >> Data: %s | Horário: %s \n", consulta.getData().toString(), consulta.getHorario());
                }
            }
            resultado += ("\n");
        }

        return resultado;
    }

    public String consultarAgendamentosMedicoEspecifico(String id) throws Exception {
        String resultado = "";
        resultado += presetRespostaPesquisaPaciente();


        ArrayList<Consulta> consultasPassadas = consultasDB.getConsultasPassadasMedicoDeterminado(paciente.getCpf(), Integer.parseInt(id));

        if(consultasPassadas.isEmpty()){ return ("Nenhuma consulta encontrada!"); }

        resultado += String.format("Consultas: (%d)", consultasPassadas.size());
        resultado += ("\n");

        for (Consulta consulta : consultasPassadas) {
            resultado += String.format("Médico: " + medicosDB.getNameFromID(Integer.parseInt(id)));
            resultado += String.format(" | Data: " + consulta.getData().format(DateTimeFormatter.ofPattern("dd-MM-uuuu")));
            resultado += String.format(" | Horario: " + consulta.getHorario());
            resultado += ("\n");
        }


        return resultado;
    }

    public String consultarAgendamentoFuturos() throws Exception {
        String resultado = "";
        resultado += presetRespostaPesquisaPaciente();


        ArrayList<Consulta> consultasFuturas = consultasDB.getConsultasFuturas(paciente.getConsultas());

        if(consultasFuturas.isEmpty()){ return ("Nenhuma consulta encontrada!"); }

        resultado += String.format("Consultas: (%d)", consultasFuturas.size());
        resultado += ("\n");

        for (Consulta consulta : consultasFuturas) {
            resultado += String.format("  >> Médico: " + medicosDB.getNameFromID(consulta.getId()));
            resultado += String.format(" | Data: " + consulta.getData().format(DateTimeFormatter.ofPattern("dd-MM-uuuu")));
            resultado += String.format(" | Horario: " + consulta.getHorario());
            resultado += ("\n");
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
