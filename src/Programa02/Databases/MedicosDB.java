package Programa02.Databases;

import Programa01.CSVFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MedicosDB {
    private ArrayList<ArrayList<String>> medicosTable;

    public String getNameFromID(int id) {
        String name = "";

        for (int i = 0; i < this.medicosTable.size(); i++) {
            List<String> linha = this.medicosTable.get(i);

            int actualID = Integer.parseInt(linha.get(1));
            if (actualID == id) {
                name = linha.get(0);
            }
        }

        return name;
    }

    public void readFile() { //aqui daria pra tentar usar throws
        try {
            CSVFile file = CSVFile.abrir("Medicos.ser");
            System.out.println("[[Medicos.ser]] recuperado com sucesso!");
            this.medicosTable = file.getTabela();

        } catch (IOException e) {
            System.out.println("Excecao de I/O - Medicos.ser");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Excecao de classe desconhecida");
            e.printStackTrace();
        }
    }
}
