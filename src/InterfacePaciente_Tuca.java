import java.io.File;
import java.util.*;

//B. Interface do Paciente permite realizar as seguintes operações de pesquisa:
//1. Quais são todos os médicos de um paciente, isto é, médicos que o
//paciente já se consultou ou tem consulta agendada?
//2. Quando ocorreram todas as consultas de um determinado paciente com
//determinado médico? (Somente consultas realizadas no passado são
//consideradas.)
//3. Quais são todas as consultas agendadas que um determinado paciente
//possui? (Somente consultas agendadas para o futuro são consideradas.)

//3-
//Receber CPF, procurar paciente, acessar consultas, verficar data > data atual

public class InterfacePaciente_Tuca {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Digite seu CPF: ");
        String cpf = input.nextLine();
        Paciente paciente = GetPacienteFromCPF(cpf);

        System.out.println(paciente);
    }

    
    public static Paciente GetPacienteFromCPF(String cpf) {
        List<List<String>> tabelaPacientes = ReadCSVGetTable("CSVs/Pacientes.csv");

        Paciente paciente = new Paciente();

        for (int i = 0; i < tabelaPacientes.size(); i++) {
            List<String> linha = tabelaPacientes.get(i);

            String actualCPF = linha.get(1);
            if (actualCPF.equals(cpf)) {
                paciente.init(linha.get(0), Integer.parseInt(cpf), new ArrayList<Consulta>());
                System.out.println(paciente);
            }
        }

        return paciente;
    }

        //Funcao que le o .csv dos médicos
        public static List<List<String>> ReadCSVGetTable(String path){
            final String NOME_ARQUIVO = path;
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