package Programa02;    //3. Cada consulta possui os seguintes dados:
    //a. Data
    //b. Horário
    //c. Referência para um paciente (por exemplo, o CPF do paciente)
    //d. Referência para um médico (por exemplo, o código do médico)
    //
    import java.time.LocalDate;

public class Consulta{
    private LocalDate data;
    private String horario;
    private int id;
    private String cpf;

    public Consulta(LocalDate data, String horario, int id, String cpf){
        this.data = data;
        this.horario = horario;
        this.id = id;
        this.cpf = cpf;
    }

    public LocalDate getData(){
        return data;
    }

    public String getHorario(){
        return horario;
    }

    public int getId(){
        return id;
    }
    public String getCpf(){
        return cpf;
    }
}