package Programa02;

import java.util.*;

public class Medico{
    private String nome;
    private int id;
    private ArrayList<Paciente> pacientes;

    public Medico(String nome, int id, ArrayList<Paciente> pacientes){
        this.nome = nome;
        this.id = id;
        this.pacientes = pacientes;
    }

    public String getNome(){
        return nome;
    }

    public int getId(){
        return id;
    }

    public ArrayList<Paciente> getPacientes(){ return pacientes; }

}


