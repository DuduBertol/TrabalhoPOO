package Programa02;

import Programa02.Databases.ConsultasDB;
import Programa02.Databases.MedicosDB;
import Programa02.Databases.PacientesDB;

import java.time.format.DateTimeFormatter;
import java.util.*;


public class InterfaceMedico_Old {
    enum Options{
        VisualizarPacientes,
        VisualizarConsultasPeriodo,
        VisualizarPacientesSemSeConsultarPeriodo
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        //Medicos Database - Load CSVs
        MedicosDB medicosDB = new MedicosDB();
//        medicosDB.readCSV();

        //Pacientes Database - Load CSVs
        PacientesDB pacientesDB = new PacientesDB();
//        pacientesDB.readCSV();

        //Consultas Database - Load CSVs
        ConsultasDB consultasDB = new ConsultasDB();
//        consultasDB.readCSV();

        System.out.println("Olá Médico.");
        int id;

        //Validator
        do {
            System.out.println("Digite seu ID: (10XX)");
            id = input.nextInt();
        } while (!consultasDB.isIDValid(id));

        String nome = medicosDB.getNameFromID(id);
        ArrayList<String> cpfsPacientes = consultasDB.getCPFsFromID(id);
        ArrayList<Paciente> pacientes = new ArrayList<>();
        for (String cpf : cpfsPacientes) {
            pacientes.add(pacientesDB.createPacienteFromCPF(cpf, consultasDB.getConsultasByCPF(cpf)));
        }

        //Cria o Objeto do Médico dessa sessão
        Medico medico = new Medico(nome, id, pacientes);

        System.out.printf("Seja Bem Vindo - %s! \n", medico.getNome());

        System.out.println("====================");
        System.out.println("> Nome: " + medico.getNome());
        System.out.println("> ID: " + medico.getId());
        System.out.println("====================");

        int choice = 0;
        do {
            System.out.println("""
                    O que você deseja fazer?
                        [ 1 ] - Visualizar todos os seus pacientes (passados e futuros).
                        [ 2 ] - Visualizar consultas em determinado período de tempo. 
                        [ 3 ] - Visualizar pacientes que não se consultam há determinado tempo.""");

            System.out.print("> ");
            choice = input.nextInt();

        } while (choice < 1 || choice > 3);

        Options option = Options.values()[choice-1];
        switch (option) {
            case VisualizarPacientes: {

                Set<String> cpfs = new HashSet<>();
                for (Paciente paciente : pacientes) {
                    cpfs.add(paciente.getCpf());
                }

                System.out.printf("\nPacientes (%d) \n", cpfs.size());

                for (String cpf : cpfs) {
                    System.out.print("  > Programa02.Paciente: " + pacientesDB.getNameFromCpf(cpf));
                    System.out.print(" | CPF: " + cpf + "\n");

                    for (Consulta consulta : consultasDB.getConsultasByID(id)) {
                        if (Objects.equals(consulta.getCpf(), cpf)) {
                            System.out.printf("    >> Data: %s | Horário: %s \n", consulta.getData().toString(), consulta.getHorario());
                        }
                    }
                    System.out.println();
                }
                break;
            }
            case VisualizarConsultasPeriodo: {
                ArrayList<Consulta> consultasPeriodo = null; //fake news pra nao dar erro
//                ArrayList<Consulta> consultasPeriodo = consultasDB.visualizaConsultasPeriodo(medico.getId());

                //Validator
                if(consultasPeriodo.isEmpty()){
                    System.out.println("Nenhuma consulta encontrada!");
                    break;
                }

                System.out.println(String.format("Consultas: (%d)", consultasPeriodo.size()));
                for (Consulta consulta: consultasPeriodo){
                    System.out.print("Data: " + consulta.getData().format(DateTimeFormatter.ofPattern("dd-MM-uuuu")));
                    System.out.print(" | Horario: " + consulta.getHorario());
                    System.out.print(" | Programa02.Paciente: " + pacientesDB.createPacienteFromCPF(consulta.getCpf(), consultasDB.getConsultasByCPF(consulta.getCpf())).getNome());
                    System.out.println();
                }
                break;
            }
            case VisualizarPacientesSemSeConsultarPeriodo: {
                System.out.println("Tempo de checagem: A quantos meses seus pacientes nao se consultam?");
                int meses = input.nextInt();
                int dias = meses * 30;

                ArrayList<Consulta> consultasPeriodo = consultasDB.getConsultaPassadasMesesAtras(medico.getId(), dias);

                //Validator
                if(consultasPeriodo.isEmpty()){
                    System.out.println("Nenhuma consulta encontrada!");
                    break;
                }

                System.out.printf("Pacientes (%d)\n", consultasPeriodo.size());
                for (Consulta consulta: consultasPeriodo){
                    System.out.print("  > " + pacientesDB.createPacienteFromCPF(consulta.getCpf(), consultasDB.getConsultasByCPF(consulta.getCpf())).getNome());
                    System.out.print(" | " + consulta.getCpf());
                    System.out.print(" | " + consulta.getData().format(DateTimeFormatter.ofPattern("dd-MM-uuuu")));
                    System.out.println();
                }
                break;
            }
        }

    }
}