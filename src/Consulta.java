    //3. Cada consulta possui os seguintes dados:
    //a. Data
    //b. Horário
    //c. Referência para um paciente (por exemplo, o CPF do paciente)
    //d. Referência para um médico (por exemplo, o código do médico)

    import java.io.*;
    import java.util.*;

public class Consulta{
    public static void main(String[] args)
    {
        ReadCSV();
    }

    public static void ReadCSV(){
        String NOME_ARQUIVO = "CSVs/Consultas.csv";
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