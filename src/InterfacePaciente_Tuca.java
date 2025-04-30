import java.util.Scanner;

//B. Interface do Paciente permite realizar as seguintes operações de pesquisa:
//1. Quais são todos os médicos de um paciente, isto é, médicos que o
//paciente já se consultou ou tem consulta agendada?
//2. Quando ocorreram todas as consultas de um determinado paciente com
//determinado médico? (Somente consultas realizadas no passado são
//consideradas.)
//3. Quais são todas as consultas agendadas que um determinado paciente
//possui? (Somente consultas agendadas para o futuro são consideradas.)

//3-
//Receber CPF, procurar paciente, acessar consultas, verficar data > data atual

public class InterfacePaciente_Tuca {
    public static void main(String[] args) {
        Scanner cpf = new Scanner(System.in);

        System.out.println("Digite seu CPF: ");
        String CPF = cpf.nextLine();

        if (CPF.length() != 11) {
            System.out.println("CPF invalido");
        }
        else {
            System.out.println("Seu CPF: " + CPF);
        }
    }
}
