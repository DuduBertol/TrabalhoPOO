import java.io.File;
import java.util.*;

public class ConsultasDB {
    private ArrayList<ArrayList<String>> consultasTable;

    public void init() {
        this.consultasTable = new ArrayList<>();
    }

    public ArrayList<ArrayList<String>> getConsultasTable() {
        return this.consultasTable;
    }

    public void readCSV(){
        String SEPARADOR = ",";

        ArrayList<ArrayList<String>> tabela = new ArrayList<>();
        try
        {
            String pathToCSV = "CSVs/Consultas.csv";
            File arquivo = new File(pathToCSV);
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
        this.consultasTable = tabela;
    }

    public ArrayList<Consulta> getConsultaByCPF(String cpf){

        ArrayList<Consulta> consultas = new ArrayList<>();

        for (List<String> linha : this.consultasTable) {
            String actualCPF = linha.get(3);
            if (Objects.equals(actualCPF, cpf)) {

                Consulta consulta = new Consulta();

                consulta.data = linha.get(0);
                consulta.horario = linha.get(1);
                consulta.id = Integer.parseInt(linha.get(2));
                consulta.cpf = cpf;

                consultas.add(consulta);
            }
        }

        return consultas;
    }


}
