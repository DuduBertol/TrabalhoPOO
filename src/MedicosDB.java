import java.io.File;
import java.util.*;

public class MedicosDB {
    private ArrayList<ArrayList<String>> medicosTable;

    public void visualizarPacientes(Medico medico) {
        System.out.printf("> Pacientes: (%d)\n", medico.pacientes.size());
        for (Paciente paciente : medico.pacientes) {
            System.out.printf("    > Nome: %s | CPF: %s\n", paciente.getNome(), paciente.getCpf());
        }
    }

    public void visualizaConsultarPeriodo(Medico medico, int inicio, int fim) {

    }

    public String getNameFromID(int id){
        String name = "";

        for(int i = 0; i < this.medicosTable.size(); i++) {
            List<String> linha = this.medicosTable.get(i);

            int actualID = Integer.parseInt(linha.get(1));
            if(actualID == id) {
                name = linha.get(0);
            }
        }

        return name;
    }

    public void readCSV(){
        String SEPARADOR = ",";

        ArrayList<ArrayList<String>> tabela = new ArrayList<>();
        try
        {
            String pathToCSV = "CSVs/Medicos.csv";
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
        this.medicosTable = tabela;
    }
}
