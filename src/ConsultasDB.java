import java.io.File;
import java.util.*;

public class ConsultasDB {
    private ArrayList<ArrayList<String>> consultasTable;

    public ConsultasDB() {
        this.consultasTable = new ArrayList<>();
    }

    public ArrayList<ArrayList<String>> getConsultasTable() {
        return this.consultasTable;
    }

    public ArrayList<Consulta> getConsultasByCPF(String cpf){

        ArrayList<Consulta> consultas = new ArrayList<>();

        for (List<String> linha : this.consultasTable) {
            String actualCPF = linha.get(3);
            if (Objects.equals(actualCPF, cpf)) {

                String data = linha.get(0);
                String horario = linha.get(1);
                int id = Integer.parseInt(linha.get(2));

                consultas.add(new Consulta(data, horario, id, cpf));
            }
        }

        return consultas;
    }

    public ArrayList<String> getCPFsFromID(int id){
        ArrayList<String> cpfs = new ArrayList<>();

        for(int i = 0; i < this.consultasTable.size(); i++) {
            List<String> linha = this.consultasTable.get(i);

            int actualID = Integer.parseInt(linha.get(2));
            if(actualID == id) {
                cpfs.add(linha.get(3));
            }
        }

        return cpfs;
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
}
