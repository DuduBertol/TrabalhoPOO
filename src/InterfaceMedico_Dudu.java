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

        System.out.println("Olá Médico. Digite seu ID: ");
        int id = input.nextInt();
        //System.out.println(id);

        String nome = IdentificarMedico(id);
        //System.out.println(nome);

        ArrayList<Paciente> pacientes = new ArrayList<>();

        Medico medico = new Medico();
        medico.init(nome, id, pacientes);

        System.out.println("Seja bem vindo(a) - " + medico.nome + " !");
        System.out.println("**Medico: " + medico + "\nNome: " + medico.nome + "\nid: " + medico.id + "\npacientes: " + medico.pacientes);

    }

    public static String IdentificarMedico(int id){
        List<List<String>> tabelaMedicos = ReadCSVMedicosTable();
        String medico = "";


        //TRY-CATCH de ID - Tratamento de erro
        for(int i = 0; i < tabelaMedicos.size(); i++) {
            List<String> linha = tabelaMedicos.get(i);

            int actualID = Integer.parseInt(linha.get(1));
            if(actualID == id) {
                medico = linha.get(0);
            }
        }

        return medico;
    }

    //Funcao que le o .csv dos médicos
    public static List<List<String>> ReadCSVMedicosTable(){
        final String NOME_ARQUIVO = "CSVs/Medicos.csv";
        final String SEPARADOR = ",";

        List<List<String>> tabela = new ArrayList<>();

        try
        {
            File arquivo = new File(NOME_ARQUIVO);
            Scanner scanner_arquivo = new Scanner(arquivo);

            String cabecalho = scanner_arquivo.nextLine();
            //System.out.println(cabecalho);

            while (scanner_arquivo.hasNextLine())
            {
                String linha = scanner_arquivo.nextLine();
                Scanner scanner_linha = new Scanner(linha);
                scanner_linha.useDelimiter(SEPARADOR);
                List<String> registro = new ArrayList<>();
                while (scanner_linha.hasNext())
                {
                    String campo = scanner_linha.next();
                    registro.add(campo);
                }
                tabela.add(registro);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return tabela;
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
