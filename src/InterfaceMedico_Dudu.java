//A. Interface do Médico permite realizar as seguintes operações de pesquisa:
//1. Quais são todos os pacientes de um determinado médico?
//2. Quais são todas as consultas agendadas para um determinado médico
//em determinado período (definido por uma data inicial e uma data final),
//na ordem crescente dos horários? (O período pode cobrir tanto o passado
//como o futuro.)
//3. Quais são os pacientes de um determinado médico que não o consulta há
//mais que um determinado tempo (em meses)?


import java.io.*;
import java.util.*;

public class InterfaceMedico_Dudu {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Olá Médico. Digite seu ID: ");
        int id = input.nextInt();
        //System.out.println(id);

        String nome = GetNameFromID(id);
        //System.out.println(nome);

        ArrayList<Paciente> pacientes = GetListPacientesFromID(id);

        Medico medico = new Medico();
        medico.init(nome, id, pacientes);

        System.out.println("Seja bem vindo(a) - " + medico.nome + " !");
        System.out.println("**Medico: " + medico + "\nNome: " + medico.nome + "\nid: " + medico.id + "\npacientes: " + medico.pacientes);

    }

    public static String GetNameFromID(int id){
        List<List<String>> tabelaMedicos = ReadCSVGetTable("CSVs/Medicos.csv");
        String medico = "";

        //TRY-CATCH de ID - Tratamento de erro
        for(int i = 0; i < tabelaMedicos.size(); i++) {
            List<String> linha = tabelaMedicos.get(i);

            int actualID = Integer.parseInt(linha.get(1));
            if(actualID == id) {
                medico = linha.get(0);
            }
        }

        return medico;
    }

    public static ArrayList<Paciente> GetListPacientesFromID(int id){
        List<List<String>> tabelaConsultas = ReadCSVGetTable("CSVs/Consultas.csv");

        ArrayList<Paciente> pacientes = new ArrayList<>();

        for(int i = 0; i < tabelaConsultas.size(); i++) {
            List<String> linha = tabelaConsultas.get(i);

            int actualID = Integer.parseInt(linha.get(2));
            if(actualID == id) {

                int cpf = Integer.parseInt(linha.get(3));
                Paciente paciente = GetPacienteFromCPF(cpf);
                pacientes.add(paciente);
            }
        }

        return pacientes;
    }

    public static Paciente GetPacienteFromCPF(int cpf){
        List<List<String>> tabelaPacientes = ReadCSVGetTable("CSVs/Pacientes.csv");

        Paciente paciente = new Paciente();

        for(int i = 0; i < tabelaPacientes.size(); i++) {
            List<String> linha = tabelaPacientes.get(i);

            int actualCPF = Integer.parseInt(linha.get(2));
            if(actualCPF == cpf) {

                paciente.init(linha.get(0), cpf, new ArrayList<Consulta>());

            }
        }

        return paciente;
    }

    /*
    public static ArrayList<Consulta> GetConsultasFromCPF(int cpf){
        List<List<String>> tabelaConsultas = ReadCSVGetTable("CSVs/Consultas.csv");

        ArrayList<Consulta> consultas = new ArrayList<>();

        for(int i = 0; i < tabelaConsultas.size(); i++) {
            List<String> linha = tabelaConsultas.get(i);

            int actualID = Integer.parseInt(linha.get(2));
            if(actualID == id) {

                Consulta consulta = new Consulta();


            }
        }

        return pacientes;
    }
     */


    //Funcao que le o .csv dos médicos
    public static List<List<String>> ReadCSVGetTable(String path){
        final String NOME_ARQUIVO = path;
        final String SEPARADOR = ",";

        List<List<String>> tabela = new ArrayList<>();

        try
        {
            File arquivo = new File(NOME_ARQUIVO);
            Scanner scanner_arquivo = new Scanner(arquivo);

            String cabecalho = scanner_arquivo.nextLine();
            //System.out.println(cabecalho);

            while (scanner_arquivo.hasNextLine())
            {
                String linha = scanner_arquivo.nextLine();
                Scanner scanner_linha = new Scanner(linha);
                scanner_linha.useDelimiter(SEPARADOR);
                List<String> registro = new ArrayList<>();
                while (scanner_linha.hasNext())
                {
                    String campo = scanner_linha.next();
                    registro.add(campo);
                }
                tabela.add(registro);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return tabela;
    }
}


/* ITERADOR DE LINHA-COLUNA
for (int i = 0; i < tabela.size(); i++) {
    List<String> linha = tabela.get(i);
    for (int j = 0; j < linha.size(); j++) {
        String valor = linha.get(j);
        System.out.println("Linha " + i + ", Coluna " + j + ": " + valor);
    }
}
*/
