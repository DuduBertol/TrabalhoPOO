package Programa01;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVFile implements Serializable{

    ArrayList<ArrayList<String>> tabela = new ArrayList<>();
    String path;

    public CSVFile(String path) {
        this.path = path;
        this.tabela = readCSV(path);
    }

    public ArrayList<ArrayList<String>> getTabela() {
        return tabela;
    }

    public ArrayList<ArrayList<String>> readCSV(String path){
        String SEPARADOR = ",";

        ArrayList<ArrayList<String>> tabela = new ArrayList<>();
        try
        {
//            String pathToCSV = path;
            File arquivo = new File(path);
            Scanner scanner_arquivo = new Scanner(arquivo);

            String cabecalho = scanner_arquivo.nextLine();
            //System.out.println(cabecalho);

            while (scanner_arquivo.hasNextLine())
            {
                String linha = scanner_arquivo.nextLine();
                Scanner scanner_linha = new Scanner(linha);
                scanner_linha.useDelimiter(SEPARADOR);
                ArrayList<String> registro = new ArrayList<>();
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


    public void salvar(String nome_arquivo) throws IOException {
        FileOutputStream arquivo = new FileOutputStream(nome_arquivo);
        ObjectOutputStream gravador = new ObjectOutputStream(arquivo);
        gravador.writeObject(this);
        gravador.close();
        arquivo.close();
    }

    public static CSVFile abrir(String nome_arquivo) throws IOException, ClassNotFoundException {
        CSVFile file = null;
        FileInputStream arquivo = new FileInputStream(nome_arquivo);
        ObjectInputStream restaurador = new ObjectInputStream(arquivo);
        file = (CSVFile) restaurador.readObject();
        restaurador.close();
        arquivo.close();
        return file;
    }

}
