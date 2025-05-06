import java.time.LocalDate;
import java.util.ArrayList;

//3-
//Precisa fazer ->
// Obtem consulta, lista armazenar consultas, acessar consultas, verificar data > data atual

public class InterfacePaciente_Tuca {
    
    public static void mostrarConsultasFuturas(Paciente paciente) {
        ArrayList<Consulta> consultasDoPaciente = paciente.getConsultas();
        ArrayList<Consulta> consultasFuturas = new ArrayList<>();

        for (Consulta consulta : consultasDoPaciente) {
            if (consulta.getData().isAfter(LocalDate.now())) {
                consultasFuturas.add(consulta);
            }
        }

        if (consultasFuturas.isEmpty()) {
            System.out.println("Sem consultas futuras");
        }
        else {
            System.out.println("======= Consultas Futuras =======");
            for (Consulta consulta : consultasFuturas) {
                exibirConsulta(consulta);
            }
        }
    }


    private static void exibirConsulta(Consulta consulta) {
        System.out.printf("Data: %s | Horário: %s | Médico ID: %d\n",
                consulta.getData(), consulta.getHorario(), consulta.getId());
    }

}