package Programa02;    //2. Cada paciente possui os seguintes dados:
    //a. Nome (string)
    //b. CPF (número de 9 dígitos e mais 2 dígitos de controle)
    //c. Lista de consultas


import java.util.*;

public class Paciente{
    private String nome;
    private String cpf;
    private ArrayList<Consulta> consultas;

    public Paciente(String nome, String cpf, ArrayList<Consulta> consultas){
        this.nome = nome;
        this.cpf = cpf;
        this.consultas = consultas;
    }

    public String getNome(){
        return nome;
    }

    public String getCpf(){
        return cpf;
    }

    public ArrayList<Consulta> getConsultas(){
        return consultas;
    }
}

/*

public static void ReadCSV(){
        String NOME_ARQUIVO = "CSVs/Pacientes.csv";
        String SEPARADOR = ",";
        List<List<String>> tabela = new ArrayList<>();
        try
        {
            File arquivo = new File(NOME_ARQUIVO);
            Scanner scanner_arquivo = new Scanner(arquivo);
            String cabecalho = scanner_arquivo.nextLine();
            System.out.println(cabecalho);

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

        System.out.println("Tabela");
        System.out.println(tabela);
        System.out.println();

        System.out.println("NOME | ID");
        for (List<String> registro: tabela) {
            for (String campo : registro)
                System.out.print(campo + " | ");
            System.out.println();
        }
    }

 */