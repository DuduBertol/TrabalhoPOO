package Programa02.Databases;

import Programa01.CSVFile;
import Programa02.Consulta;
import Programa02.Paciente;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PacientesDB {
    private ArrayList<ArrayList<String>> pacientesTable;

    public Paciente createPacienteFromCPF(String cpf, ArrayList<Consulta> consultas) {
        Paciente paciente = null;

        for (int i = 0; i < this.pacientesTable.size(); i++) {
            List<String> linha = this.pacientesTable.get(i);

            String actualCPF = linha.get(1);
            if (Objects.equals(actualCPF, cpf)) {
                paciente = new Paciente(linha.get(0), cpf, consultas);
            }
        }

        return paciente;
    }

    public String getNameFromCpf(String cpf) {
        String nome = null;

        for (int i = 0; i < this.pacientesTable.size(); i++) {
            List<String> linha = this.pacientesTable.get(i);

            String actualCPF = linha.get(1);
            if (Objects.equals(actualCPF, cpf)) {
                nome = linha.get(0);
            }
        }

        return nome;
    }


    public void readFile() { //aqui daria pra tentar usar throws
        try {
            CSVFile file = CSVFile.abrir("Pacientes.ser");
            System.out.println("[[Pacientes.ser]] recuperado com sucesso!");
            this.pacientesTable = file.getTabela();

        } catch (IOException e) {
            System.out.println("Excecao de I/O");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Excecao de classe desconhecida");
            e.printStackTrace();
        }
    }

}
