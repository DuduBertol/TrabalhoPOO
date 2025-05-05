//A. Interface do Médico permite realizar as seguintes operações de pesquisa:
//1. Quais são todos os pacientes de um determinado médico?
//2. Quais são todas as consultas agendadas para um determinado médico
//em determinado período (definido por uma data inicial e uma data final),
//na ordem crescente dos horários? (O período pode cobrir tanto o passado
//como o futuro.)
//3. Quais são os pacientes de um determinado médico que não o consulta há
//mais que um determinado tempo (em meses)?


import java.time.format.DateTimeFormatter;
import java.util.*;


public class InterfaceMedico_Dudu {
    enum Options{
        VisualizarPacientes,
        VisualizarConsultasPeriodo,
        VisualizarPacientesSemSeConsultarPeriodo
    }

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


        System.out.println("Olá Médico. Digite seu ID: (10XX)");
        int id = input.nextInt();
        String nome = medicosDB.getNameFromID(id);

        ArrayList<String> cpfsPacientes = consultasDB.getCPFsFromID(id);
        ArrayList<Paciente> pacientes = new ArrayList<>();
        for (String cpf : cpfsPacientes) {
            pacientes.add(pacientesDB.createPacienteFromCPF(cpf, consultasDB.getConsultasByCPF(cpf)));
        }

        //Cria o Objeto do Médico dessa sessão
        Medico medico = new Medico(nome, id, pacientes);

        System.out.printf("Seja Bem Vindo - %s! \n", medico.nome);

        System.out.println("====================");
        System.out.println("> Nome: " + medico.nome);
        System.out.println("> ID: " + medico.id);
        System.out.println("====================");

        System.out.println("""
                    O que você deseja fazer?
                        [ 1 ] - Visualizar todos os seus pacientes (passados e futuros).
                        [ 2 ] - Visualizar consultas em determinado período de tempo. 
                        [ 3 ] - Visualizar pacientes que não se consultam há determinado tempo.""");

        System.out.print("> ");
        int choice = input.nextInt();
        Options option = Options.values()[choice-1];
        switch (option) {
            case VisualizarPacientes: {
                medicosDB.visualizarPacientes(medico);
                break;
            }
            case VisualizarConsultasPeriodo: {
                ArrayList<Consulta> consultasPeriodo = consultasDB.visualizaConsultarPeriodo(medico.id);
                System.out.println(String.format("Consultas: (%d)", consultasPeriodo.size()));
                for (Consulta consulta: consultasPeriodo){
                    System.out.print("Data: " + consulta.data.format(DateTimeFormatter.ofPattern("dd-MM-uuuu")));
                    System.out.print(" | Horario: " + consulta.horario);
                    System.out.print(" | Paciente: " + pacientesDB.createPacienteFromCPF(consulta.cpf, consultasDB.getConsultasByCPF(consulta.cpf)).getNome());
                    System.out.println();
                }
                break;
            }
            case VisualizarPacientesSemSeConsultarPeriodo: {
                //Code 03

                System.out.println("Tempo de checagem: A quantos meses seus pacientes nao se consultam?");
                int meses = input.nextInt();
                int dias = meses * 30;

                ArrayList<Consulta> consultasPeriodo = consultasDB.getConsultaPassadasMesesAtras(medico.id, dias);
                if (consultasPeriodo.isEmpty()){
                    System.out.println("Você não possui nenhum pacientes nesse período.");
                } else {
                    System.out.printf("Pacientes (%d)\n", consultasPeriodo.size());
                    for (Consulta consulta: consultasPeriodo){
                        System.out.print("  >> " + pacientesDB.createPacienteFromCPF(consulta.cpf, consultasDB.getConsultasByCPF(consulta.cpf)).getNome());
                        System.out.print(" | " + consulta.cpf);
                        System.out.print(" | " + consulta.data.format(DateTimeFormatter.ofPattern("dd-MM-uuuu")));
                        System.out.println();
                    }
                }

                //no consultas DB
                //pegar todas as consultas do médico
                //checar o dia delas
                //comparar com o dia atual
                    //se der mais de 30 dias de diferenca
                    //devolve as consulta

                //aqui no code
                    //pega a ArrayList de consulta - pega o nome do paciente pelo cpf
                break;
            }
        }
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
