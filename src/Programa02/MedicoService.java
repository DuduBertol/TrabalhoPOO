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
    public void csvReader(){

        //aqui faço a persistencia de objetos/dados

        medicosDB.readCSV();
        pacientesDB.readCSV();
        consultasDB.readCSV();

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

        ArrayList<Paciente> pacientes = medico.getPacientes();
        Set<Paciente> pacienteSet = new HashSet<>(pacientes);

        resultado += String.format("\nPacientes (%d) \n", pacientes.size());
        resultado += ("\n");

        for (Paciente p : pacienteSet) {
            resultado += String.format("  > Paciente: " + p.getNome());
            resultado += String.format(" | CPF: " + p.getCpf() + "\n");

            for (Consulta consulta : consultasDB.getConsultasByID(medico.getId())) {
                if (Objects.equals(consulta.getCpf(), p.getCpf())) {
                    resultado += String.format("    >> Data: %s | Horário: %s \n", consulta.getData().toString(), consulta.getHorario());
                }
            }
            resultado += ("\n");
        }

        return resultado;
    }

    public String consultarPorPeriodo(String mesInicio, String mesFinal) {
        String resultado = "";

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

    public String consultarAusentes(int meses) {
        // Sua lógica que recebe o número de meses e retorna os pacientes ausentes
        return "Pacientes ausentes há mais de " + meses + " meses...";
    }
}