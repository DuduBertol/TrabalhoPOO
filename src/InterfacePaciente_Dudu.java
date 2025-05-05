import java.time.format.DateTimeFormatter;
import java.util.*;

public class InterfacePaciente_Dudu {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        //Medicos Database - Load CSVs
        MedicosDB medicosDB = new MedicosDB();
        medicosDB.readCSV();

        //Pacientes Database - Load CSVs
        PacientesDB pacientesDB = new PacientesDB();
        pacientesDB.readCSV();

        //Consultas Database - Load CSVs
        ConsultasDB consultasDB = new ConsultasDB();
        consultasDB.readCSV();


        System.out.println("Olá Paciente. Digite seu CPF: (XXX.XXX.XXX-XX)");
        String cpf = input.nextLine();
        String nome = pacientesDB.getNameFromCpf(cpf);
        ArrayList<Consulta> consultas = consultasDB.getConsultasByCPF(cpf);

        //Cria o Objeto do Paciente dessa sessão
        Paciente paciente = new Paciente(nome, cpf, consultas);

        System.out.printf("Seja Bem Vindo - %s! \n", paciente.getNome());

        System.out.println("====================");
        System.out.println("> Nome: " + paciente.getNome());
        System.out.println("> ID: " + paciente.getCpf());
        System.out.println("====================");

        System.out.println("""
                    O que você deseja fazer?
                        [ 1 ] - Visualizar todos os seus médicos (passados e futuros).
                        [ 2 ] - Visualizar consultas passadas com determinado médico. 
                        [ 3 ] - Visualizar consultas futuras.""");

        System.out.print("> ");
    }
}
