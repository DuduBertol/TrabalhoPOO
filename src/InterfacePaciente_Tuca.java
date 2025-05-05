import java.util.*;
import java.time.LocalDate;


//B. Interface do Paciente permite realizar as seguintes operações de pesquisa:
//1. Quais são todos os médicos de um paciente, isto é, médicos que o
//paciente já se consultou ou tem consulta agendada?
//2. Quando ocorreram todas as consultas de um determinado paciente com
//determinado médico? (Somente consultas realizadas no passado são
//consideradas.)
//3. Quais são todas as consultas agendadas que um determinado paciente
//possui? (Somente consultas agendadas para o futuro são consideradas.)

//3-
//Precisa fazer -> Receber CPF, procurar paciente, acessar consultas, verificar data > data atual

public class InterfacePaciente_Tuca {
    /*public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Digite seu CPF: ");
        String cpf = input.nextLine();
        Paciente paciente = GetPacienteFromCPF(cpf);

        System.out.println(paciente.getNome());
        //to aprendendo java calma
        System.out.println(paciente.cpf);
    }*/

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        PacientesDB pacientesDB = new PacientesDB();
        pacientesDB.readCSV();

        ConsultasDB consultasDB = new ConsultasDB();
        consultasDB.readCSV();

        //Recebe CPF
        System.out.println("Digite seu CPF: ");
        String cpf = input.nextLine();

        //pega as consultas do CPF
        ArrayList<Consulta> consultasDoPaciente = consultasDB.getConsultasByCPF(cpf);

        //cria o objeto Paciente com as consultas
        Paciente paciente = pacientesDB.createPacienteFromCPF(cpf, consultasDoPaciente);

        if (paciente == null) {
            System.out.println("Paciente não encontrado.");
            return;
        }

        System.out.println("Bem-vindo " + paciente.getNome());

    }

}