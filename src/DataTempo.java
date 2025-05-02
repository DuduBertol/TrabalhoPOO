import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataTempo {
    public static void main(String[] args)
    {
        LocalDateTime agora = LocalDateTime.now();
        System.out.println(agora);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String agora_formatado = agora.format(formatador);
        System.out.println(agora_formatado);

        String agora_formatado2 = agora.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        System.out.println(agora_formatado2);


        System.out.println(agora.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

        System.out.println(agora.format(DateTimeFormatter.ofPattern("d-M-y H:m:s")));
    }
}
