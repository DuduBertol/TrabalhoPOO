    //1. Cada médico possui os seguintes dados:
    //a. Nome (string)
    //b. Código (valor inteiro): número único de identificação do médico
    //c. Lista de pacientes


import java.io.*;
import java.util.*;

public class Medico{
    public static void main(String[] args)
    {
        ReadCSV();
    }

    //Funcao que le o .csv dos médicos
    public static void ReadCSV(){
        String NOME_ARQUIVO = "CSVs/Medicos.csv";
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
}
