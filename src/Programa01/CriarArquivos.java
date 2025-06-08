package Programa01;

import java.io.*;
import java.util.ArrayList;

public class CriarArquivos {
    public static void main(String[] args) {

        CSVFile medicosFile = new CSVFile("CSVs/Medicos.csv");
        CSVFile pacientesFile = new CSVFile("CSVs/Pacientes.csv");
        CSVFile consultasFile = new CSVFile("CSVs/Consultas.csv");

        try {
            medicosFile.salvar("Medicos.ser");
            System.out.println("[[Medicos]] criado e salvo com sucesso!");

            pacientesFile.salvar("Pacientes.ser");
            System.out.println("[[Pacientes]] criado e salvo com sucesso!");

            consultasFile.salvar("Consultas.ser");
            System.out.println("[[Consultas]] criado e salvo com sucesso!");
        }
        catch (IOException e) {
            System.out.println("Excecao de I/O");
            e.printStackTrace();
        }

    }
}
