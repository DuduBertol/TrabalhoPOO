package Programa02;

import Programa02.Databases.*;


//Faz o meio campo entre a database antiga e a view
    //assim tirei o trabalho da main de ler os CSVs por lá
public class MedicoService {

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

    public boolean validarMedico(int id) {
        return consultasDB.isIDValid(id);
    }

    public String consultarTodosPacientes() {
        // Sua lógica que retorna a lista de todos os pacientes como uma String
        // Exemplo: "Paciente 1: João Silva\nPaciente 2: Maria Souza\n..."
        return "Lista de todos os pacientes...";
    }

    public String consultarPorPeriodo(String dataInicio, String dataFim) {
        // Sua lógica que recebe as datas e retorna as consultas agendadas
        return "Consultas entre " + dataInicio + " e " + dataFim + "...";
    }

    public String consultarAusentes(int meses) {
        // Sua lógica que recebe o número de meses e retorna os pacientes ausentes
        return "Pacientes ausentes há mais de " + meses + " meses...";
    }
}