package Programa02;

import Programa02.Databases.ConsultasDB;
import Programa02.Databases.MedicosDB;
import Programa02.Databases.PacientesDB;

import java.time.format.DateTimeFormatter;
import java.util.*;

public class InterfacePaciente {
    enum Options{
        VisualizarMedicos,
        VisualizarConsultasPassadasMedicoDeterminado,
        VisualizarConsultasFuturas
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        //Medicos Database - Load CSVs
        MedicosDB medicosDB = new MedicosDB();
        medicosDB.readFile();

        //Pacientes Database - Load CSVs
        PacientesDB pacientesDB = new PacientesDB();
        pacientesDB.readFile();

        //Consultas Database - Load CSVs
        ConsultasDB consultasDB = new ConsultasDB();
        consultasDB.readFile();

        System.out.println("Olá Programa02.Paciente.");
        String cpf;

        //Validator
        do {
            System.out.println("Digite seu CPF: (XXX.XXX.XXX-XX)");
            cpf = input.nextLine().strip();
        } while (!consultasDB.isCpfValid(cpf));

        String nome = pacientesDB.getNameFromCpf(cpf);
        ArrayList<Consulta> consultas = consultasDB.getConsultasByCPF(cpf);

        //Cria o Objeto do Programa02.Paciente dessa sessão
        Paciente paciente = new Paciente(nome, cpf, consultas);

        System.out.printf("Seja Bem Vindo - %s! \n", paciente.getNome());

        System.out.println("====================");
        System.out.println("> Nome: " + paciente.getNome());
        System.out.println("> CPF: " + paciente.getCpf());
        System.out.println("====================");

        int choice = 0;
        do {
            System.out.println("""
                    O que você deseja fazer?
                        [ 1 ] - Visualizar todos os seus médicos (passados e futuros).
                        [ 2 ] - Visualizar consultas passadas com determinado médico. 
                        [ 3 ] - Visualizar consultas futuras.""");

            System.out.print("> ");
            choice = input.nextInt();

        } while(choice < 1 || choice > 3);

        Options option = Options.values()[choice - 1];
        switch (option) {
            case VisualizarMedicos: {
                Set<Integer> medicosIDs = new HashSet<>();
                for (Consulta consulta : consultas) {
                    medicosIDs.add(consulta.getId());
                }

                System.out.printf("\nMédicos (%d) \n", medicosIDs.size());

                for (Integer medicoID : medicosIDs) {
                    System.out.print("  >> Médico: " + medicosDB.getNameFromID(medicoID));
                    System.out.print(" | ID: " + medicoID + "\n");

                    for (Consulta consulta : consultas) {
                        if (consulta.getId() == medicoID) {
                            System.out.printf("    >> Data: %s | Horário: %s \n", consulta.getData().toString(), consulta.getHorario());
                        }
                    }
                    System.out.println();
                }
                break;
            }
            case VisualizarConsultasPassadasMedicoDeterminado: {
                int id;

                //Validator
                do {
                    System.out.println("Digite o ID do médico que você deseja visualizar: (10XX)");
                    id = input.nextInt();
                } while (!consultasDB.isIDValid(id));

                ArrayList<Consulta> consultasPassadas = consultasDB.getConsultasPassadasMedicoDeterminado(cpf, id);

                //Validator
                if(consultasPassadas.isEmpty()){
                    System.out.println("Nenhuma consulta encontrada!");
                    break;
                }

                System.out.println();
                System.out.println(String.format("Consultas: (%d)", consultasPassadas.size()));
                for (Consulta consulta : consultasPassadas) {
                    System.out.print("Médico: " + medicosDB.getNameFromID(id));
                    System.out.print(" | Data: " + consulta.getData().format(DateTimeFormatter.ofPattern("dd-MM-uuuu")));
                    System.out.print(" | Horario: " + consulta.getHorario());
                    System.out.println();
                }
                break;
            }
            case VisualizarConsultasFuturas: {
                ArrayList<Consulta> consultasFuturas = consultasDB.getConsultasFuturas(consultas);

                //Validator
                if(consultasFuturas.isEmpty()){
                    System.out.println("Nenhuma consulta encontrada!");
                    break;
                }

                System.out.println(String.format("Consultas: (%d)", consultasFuturas.size()));
                for (Consulta consulta : consultasFuturas) {
                    System.out.print("  >> Médico: " + medicosDB.getNameFromID(consulta.getId()));
                    System.out.print(" | Data: " + consulta.getData().format(DateTimeFormatter.ofPattern("dd-MM-uuuu")));
                    System.out.print(" | Horario: " + consulta.getHorario());
                    System.out.println();
                }
                break;
            }
        }
    }
}
