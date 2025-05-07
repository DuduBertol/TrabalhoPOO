import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ConsultasDB {
    private ArrayList<ArrayList<String>> consultasTable;
    private final DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd-MM-uuuu");
    private final Scanner input = new Scanner(System.in);

    public ConsultasDB() {
        this.consultasTable = new ArrayList<>();
    }

    public ArrayList<ArrayList<String>> getConsultasTable() {
        return this.consultasTable;
    }

    public ArrayList<Consulta> getConsultaPassadasMesesAtras(int id, int dias) {
        ArrayList<Consulta> consultasPassadas = new ArrayList<>();

        ArrayList<Consulta> consultasMedico = getConsultasByID(id);
        LocalDate hoje = LocalDate.now();

        for (Consulta consulta : consultasMedico) {
            if(hoje.getDayOfYear() - consulta.getData().getDayOfYear() >= dias) {
                consultasPassadas.add(consulta);
            }
        }

        return consultasPassadas;
    }

    public ArrayList<Consulta> visualizaConsultasPeriodo(int id) {
        System.out.println("Digite o mês de início do período que você deseja visualizar: (MM)");
        String mesInicio = input.nextLine();
        LocalDate dataInicio = LocalDate.parse(String.format("01-%s-2025",mesInicio), this.formatoBR);

        System.out.println("Digite o mês de fim do período que você deseja visualizar: (MM)");
        String mesFinal = input.nextLine();
        LocalDate dataFinal = LocalDate.parse(String.format("01-%s-2025",mesFinal), this.formatoBR);

        ArrayList<Consulta> consultasPeriodo = new ArrayList<>();

        ArrayList<Consulta> consultasMedico = getConsultasByID(id);
        for (Consulta consulta : consultasMedico) {
            if(consulta.getData().isAfter(dataInicio) && consulta.getData().isBefore(dataFinal)){
                consultasPeriodo.add(consulta);
            }
        }

        return consultasPeriodo;
    }

    public ArrayList<Consulta> getConsultasFuturas(ArrayList<Consulta> consultasDoPaciente) {
        ArrayList<Consulta> consultasFuturas = new ArrayList<>();

        for (Consulta consulta : consultasDoPaciente) {
            if (consulta.getData().isAfter(LocalDate.now())) {
                consultasFuturas.add(consulta);
            }
        }
        return consultasFuturas;
    }

    public ArrayList<Consulta> getConsultasPassadasMedicoDeterminado(String cpf, int id) {
        ArrayList<Consulta> consultasPassadas = new ArrayList<>();

        LocalDate dataHoje = LocalDate.now();

        ArrayList<Consulta> consultasPaciente = getConsultasByCPF(cpf);
        for (Consulta consulta : consultasPaciente) {
            if(consulta.getData().isBefore(dataHoje) && consulta.getId() == id){
                consultasPassadas.add(consulta);
            }
        }

        return consultasPassadas;
    }

    public ArrayList<Consulta> getConsultasAsArrayList() {
        ArrayList<Consulta> consultas = new ArrayList<>();
        for (ArrayList<String> linha : this.consultasTable) {
            Consulta consulta = new Consulta(LocalDate.parse(linha.get(0), this.formatoBR), linha.get(1), Integer.parseInt(linha.get(2)), linha.get(3));
            consultas.add(consulta);
        }
        return consultas;
    }

    public ArrayList<LocalDate> getListOfLocalDatasFromConsultas(){
        ArrayList<LocalDate> listDatas = new ArrayList<>();

        for (List<String> linha : this.consultasTable) {
            String dataString = linha.get(0);
            LocalDate data = LocalDate.parse(dataString, this.formatoBR);
            listDatas.add(data);
        }

        return listDatas;
    }

    public ArrayList<Consulta> getConsultasByIDAndCpf(int id, String cpf){

        ArrayList<Consulta> consultas = new ArrayList<>();

        for (List<String> linha : this.consultasTable) {
            int actualID = Integer.parseInt(linha.get(2));
            String actualCPF = linha.get(3);
            if (actualID == id && Objects.equals(actualCPF, cpf)) {

                LocalDate data = LocalDate.parse(linha.get(0), this.formatoBR);
                String horario = linha.get(1);

                consultas.add(new Consulta(data, horario, id, cpf));
            }
        }

        return consultas;
    }

    public ArrayList<Consulta> getConsultasByID(int id){

        ArrayList<Consulta> consultas = new ArrayList<>();

        for (List<String> linha : this.consultasTable) {
            int actualID = Integer.parseInt(linha.get(2));
            if (actualID == id) {

                LocalDate data = LocalDate.parse(linha.get(0), this.formatoBR);
                String horario = linha.get(1);
                String cpf = linha.get(3);

                consultas.add(new Consulta(data, horario, id, cpf));
            }
        }

        return consultas;
    }

//    public int getIDByCpf(String cpf){
//        int id = 0;
//
//        for(int i = 0; i < this.consultasTable.size(); i++) {
//            List<String> linha = this.consultasTable.get(i);
//
//            String actualCpf = (linha.get(2));
//            if(actualCpf == cpf) {
//                id.add(linha.get(3));
//            }
//        }
//
//        return id;
//    }

    public ArrayList<Consulta> getConsultasByCPF(String cpf){

        ArrayList<Consulta> consultas = new ArrayList<>();

        for (List<String> linha : this.consultasTable) {
            String actualCPF = linha.get(3);
            if (Objects.equals(actualCPF, cpf)) {

                LocalDate data = LocalDate.parse(linha.get(0), this.formatoBR);
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
