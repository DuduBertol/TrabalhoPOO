//A. Interface do Médico permite realizar as seguintes operações de pesquisa:
//1. Quais são todos os pacientes de um determinado médico?
//2. Quais são todas as consultas agendadas para um determinado médico
//em determinado período (definido por uma data inicial e uma data final),
//na ordem crescente dos horários? (O período pode cobrir tanto o passado
//como o futuro.)
//3. Quais são os pacientes de um determinado médico que não o consulta há
//mais que um determinado tempo (em meses)?


import java.io.*;
import java.util.*;

public class InterfaceMedico_Dudu {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        //Medicos Database - Load CSVs
        MedicosDB medicosDB = new MedicosDB();
        medicosDB.readCSV();

        //Pacientes Database - Load CSVs
        PacientesDB pacientesDB = new PacientesDB();
        pacientesDB.readCSV();

        //Consultas Database - Load CSVs
        ConsultasDB consultasDB = new ConsultasDB();
        consultasDB.readCSV();


        System.out.println("Olá Médico. Digite seu ID: ");
        int id = input.nextInt();
        String nome = medicosDB.getNameFromID(id);

        ArrayList<String> cpfsPacientes = consultasDB.getCPFsFromID(id);
        ArrayList<Paciente> pacientes = new ArrayList<>();
        for (String cpf : cpfsPacientes) {
            pacientes.add(pacientesDB.getPacienteFromCPF(cpf, consultasDB.getConsultasByCPF(cpf)));
        }

        //Cria o Objeto do Médico dessa sessão
        Medico medico = new Medico(nome, id, pacientes);

        System.out.println(medico);
        System.out.println(medico.nome);
        System.out.println(medico.id);
        System.out.println(medico.pacientes);
    }
}

/* ITERADOR DE LINHA-COLUNA
for (int i = 0; i < tabela.size(); i++) {
    List<String> linha = tabela.get(i);
    for (int j = 0; j < linha.size(); j++) {
        String valor = linha.get(j);
        System.out.println("Linha " + i + ", Coluna " + j + ": " + valor);
    }
}
*/
