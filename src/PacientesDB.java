import java.io.File;
import java.util.*;

public class PacientesDB {
    private ArrayList<ArrayList<String>> pacientesTable;

    public Paciente createPacienteFromCPF(String cpf, ArrayList<Consulta> consultas){
        Paciente paciente = null;

        for(int i = 0; i < this.pacientesTable.size(); i++) {
            List<String> linha = this.pacientesTable.get(i);

            String actualCPF = linha.get(1);
            if(Objects.equals(actualCPF, cpf)) {
                paciente = new Paciente(linha.get(0), cpf, consultas);
            }
        }

        return paciente;
    }

    public String getNameFromCpf(String cpf){
        String nome = null;

        for(int i = 0; i < this.pacientesTable.size(); i++) {
            List<String> linha = this.pacientesTable.get(i);

            String actualCPF = linha.get(1);
            if(Objects.equals(actualCPF, cpf)) {
                nome = linha.get(0);
            }
        }

        return nome;
    }



    public void readCSV(){
        String SEPARADOR = ",";

        ArrayList<ArrayList<String>> tabela = new ArrayList<>();
        try
        {
            String pathToCSV = "CSVs/Pacientes.csv";
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
        this.pacientesTable = tabela;
    }

}
