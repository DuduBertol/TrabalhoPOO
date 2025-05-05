import java.util.ArrayList;
import java.util.Scanner;

public class InterfacePaciente_Rafa {

    //B. Interface do Paciente permite realizar as seguintes operações de pesquisa:
    //1. Quais são todos os médicos de um paciente, isto é, médicos que o
    //paciente já se consultou ou tem consulta agendada?
    //2. Quando ocorreram todas as consultas de um determinado paciente com
    //determinado médico? (Somente consultas realizadas no passado são
    //consideradas.)
    //3. Quais são todas as consultas agendadas que um determinado paciente
    //possui? (Somente consultas agendadas para o futuro são consideradas.)

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        PacientesDB pacientesDB = new PacientesDB();
        pacientesDB.readCSV();

        ConsultasDB consultasDB = new ConsultasDB();
        consultasDB.readCSV();
// public ArrayList<String> getIDFromCpfs(String cpf){
//        ArrayList<String> id = new ArrayList<>();
//
//        for(int i = 0; i < this.consultasTable.size(); i++) {
//            List<String> linha = this.consultasTable.get(i);
//
//            String actualCpf = (linha.get(2));
//            if(actualCpf == cpf) {
//                id.add(linha.get(3));
//            }
//        }
//
//        return id;
//    }


        //  Um Sonho(Pegar Id de medico pelo Cpf)
        //ArrayList<String> IdMedicos = consultasDB.getIDFromCpfs(cpf);


        //Recebe CPF do Queride
        System.out.println("Digite seu CPF: ");
        String cpf = input.nextLine();

        //pega as consultas do CPF
        ArrayList<Consulta> consultasDoPaciente = consultasDB.getConsultasByCPF(cpf);




        System.out.println(pacientesDB.getNameFromCpf(cpf));

    }
}
