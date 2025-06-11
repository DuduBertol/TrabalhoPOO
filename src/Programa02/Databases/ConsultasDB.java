package Programa02.Databases;

import Programa01.CSVFile;
import Programa02.Consulta;

import java.io.IOException;
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
            if (hoje.getDayOfYear() - consulta.getData().getDayOfYear() >= dias) {
                consultasPassadas.add(consulta);
            }
        }

        return consultasPassadas;
    }

    public ArrayList<Consulta> visualizaConsultasPeriodo(int id, String mesInicio, String mesFinal) {

        LocalDate dataInicio = LocalDate.parse(String.format("01-%s-2025", mesInicio), this.formatoBR);
        LocalDate dataFinal = LocalDate.parse(String.format("01-%s-2025", mesFinal), this.formatoBR);

        ArrayList<Consulta> consultasPeriodo = new ArrayList<>();

        ArrayList<Consulta> consultasMedico = getConsultasByID(id);
        for (Consulta consulta : consultasMedico) {
            if (consulta.getData().isAfter(dataInicio) && consulta.getData().isBefore(dataFinal)) {
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
            if (consulta.getData().isBefore(dataHoje) && consulta.getId() == id) {
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

    public ArrayList<LocalDate> getListOfLocalDatasFromConsultas() {
        ArrayList<LocalDate> listDatas = new ArrayList<>();

        for (List<String> linha : this.consultasTable) {
            String dataString = linha.get(0);
            LocalDate data = LocalDate.parse(dataString, this.formatoBR);
            listDatas.add(data);
        }

        return listDatas;
    }

    public ArrayList<Consulta> getConsultasByIDAndCpf(int id, String cpf) {

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

    public ArrayList<Consulta> getConsultasByID(int id) {

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

    public ArrayList<Integer> getIDsByCpf(String cpf) {

        ArrayList<Integer> ids = new ArrayList<>();

        for (List<String> linha : this.consultasTable) {

            String actualCpf = (linha.get(3));
            if (Objects.equals(actualCpf, cpf)) {
                ids.add(Integer.parseInt(linha.get(2)));
            }
        }

        return ids;
    }

    public ArrayList<Consulta> getConsultasByCPF(String cpf) {

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

    public ArrayList<String> getCPFsFromID(int id) {
        ArrayList<String> cpfs = new ArrayList<>();

        for (int i = 0; i < this.consultasTable.size(); i++) {
            List<String> linha = this.consultasTable.get(i);

            int actualID = Integer.parseInt(linha.get(2));
            if (actualID == id) {
                cpfs.add(linha.get(3));
            }
        }

        return cpfs;
    }

    public boolean isCpfValid(String cpf) {
        for (List<String> linha : this.consultasTable) {
            String actualCpf = linha.get(3);
            if (Objects.equals(actualCpf, cpf)) {
                return true;
            }
        }
        return false;
    }

    public boolean isIDValid(int id) {
        for (List<String> linha : this.consultasTable) {
            int actualID = Integer.parseInt(linha.get(2));
            if (actualID == id) {
                return true;
            }
        }
        return false;
    }

    public void readFile() { //aqui daria pra tentar usar throws
        try {
            CSVFile file = CSVFile.abrir("Consultas.ser");
            System.out.println("[[Consultas.ser]] recuperado com sucesso!");
            this.consultasTable = file.getTabela();

        } catch (IOException e) {
            System.out.println("Excecao de I/O - Consultas.ser");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Excecao de classe desconhecida");
            e.printStackTrace();
        }
    }
}
