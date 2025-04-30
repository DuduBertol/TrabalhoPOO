    //3. Cada consulta possui os seguintes dados:
    //a. Data
    //b. Horário
    //c. Referência para um paciente (por exemplo, o CPF do paciente)
    //d. Referência para um médico (por exemplo, o código do médico)
    //
    import java.io.*;
import java.util.*;

public class Consulta{
    public String nome;
    public int cpf;
    public ArrayList<Consulta> consultas;

    public void init(String nome, int id, ArrayList<Consulta> consultas){
        this.nome = nome;
        this.cpf = cpf;
        this.consultas = consultas;
    }
}