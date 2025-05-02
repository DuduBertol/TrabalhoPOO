    //3. Cada consulta possui os seguintes dados:
    //a. Data
    //b. Horário
    //c. Referência para um paciente (por exemplo, o CPF do paciente)
    //d. Referência para um médico (por exemplo, o código do médico)
    //
    import java.io.*;
    import java.time.LocalDate;
    import java.util.*;

public class Consulta{
    public LocalDate data;
    public String horario;
    public int id;
    public String cpf;

    public Consulta(LocalDate data, String horario, int id, String cpf){
        this.data = data;
        this.horario = horario;
        this.id = id;
        this.cpf = cpf;
    }

    public int getId(){
        return id;
    }
    public String getCpf(){
        return cpf;
    }
}