package Programa02;

import Programa02.Databases.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


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

    // CSVs READER
    public void fileReader(){

        //aqui faço a persistencia de objetos/dados

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

    public boolean validarMedico(int id) {
        if(consultasDB.isIDValid(id)){
            setMedico(id);
            return true;
        }
        else{
            return false;
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

    public String consultarPorPeriodo(String mesInicio, String mesFinal) throws Exception {
        if (Integer.parseInt(mesInicio) == Integer.parseInt(mesFinal)) {
            throw new Exception("Meses iguais");
        }
        else if (Integer.parseInt(mesInicio) > Integer.parseInt(mesFinal)) {
            throw new Exception("Mês de início maior que Mês final.");
        }
        else if (Integer.parseInt(mesInicio) > 12 || Integer.parseInt(mesFinal) > 12) {
            throw new Exception("Mês maior que 12.");
        }
        else if (Integer.parseInt(mesInicio) < 0 || Integer.parseInt(mesFinal) < 0) {
            throw new Exception("Mês menor que 0.");
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

}
