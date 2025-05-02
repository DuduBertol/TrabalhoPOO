import java.time.LocalDate; // classe para representar uma data
import java.time.DayOfWeek; // enumerado com os dias da semana
import java.time.Month; // enumerado com os meses do ano
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class Data {
    public static <Period> void main(String[] args) {
        // cria um objeto para representar a data de hoje
        LocalDate hoje = LocalDate.now();
        System.out.println("Data de hoje: " + hoje);

        System.out.println("dia: " + hoje.getDayOfMonth());
        System.out.println("mes (numero): " + hoje.getMonthValue());
        System.out.println("mes (texto ): " + hoje.getMonth());
        System.out.println("ano: " + hoje.getYear());

        System.out.println("dia da semana: " + hoje.getDayOfWeek());
        System.out.println("dia do ano:    " + hoje.getDayOfYear());

        // cria um objeto para representar a data de amanhã
        LocalDate amanha = hoje.plusDays(1);
        System.out.println("Amanha: " + amanha);

        // cria um objeto para representar a data de ontem
        LocalDate ontem = hoje.minusDays(1);
        System.out.println("Ontem: " + ontem);

        // cria um objeto para representar a data 3/6/2003 a partir de três parâmetros
        LocalDate nascimento = LocalDate.of(2003, Month.JUNE, 3);
        System.out.println("Data de nascimento: " + nascimento);

        // cria um objeto para representar a data 4/10/2040 a partir de uma string
        LocalDate casamento = LocalDate.parse("2040-04-10");
        System.out.println("Data de casamento: " + casamento);

        // cria um objeto para representar a data da próxima quarta-feira (a partir de hoje)
        LocalDate proxima_quarta = hoje.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
        System.out.println("Data da proxima quarta-feira: " + proxima_quarta);

        // cria um objeto para representar a data da primeira segunda-feira do mês corrente
        LocalDate primeira_segunda_do_mes = hoje.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        System.out.println("Primeira segunda-feira do mes corrente: " + primeira_segunda_do_mes);

        // comparação de duas datas ***
        System.out.println("Comparando hoje com ontem : " + hoje.compareTo(ontem));  // 1
        System.out.println("Comparando hoje com amanhã: " + hoje.compareTo(amanha)); // -1
        System.out.println("Comparando hoje com hoje  : " + hoje.compareTo(hoje));   // 0
        System.out.println("Comparando hoje com hoje  : " + hoje.compareTo(LocalDate.of(2025, 04, 15)));   // 1

        // diferença entre duas datas (cálculo de idade)
        LocalDate nasc = LocalDate.of(1966, Month.DECEMBER, 2);
        System.out.println("Nascimento - dia da semana: " + nasc.getDayOfWeek());
        java.time.Period vida = nasc.until(hoje);
        System.out.println("Idade: " + vida.getYears());

        // formatação de data
        DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        String s = hoje.format(formatoBR);
        System.out.println("hoje em formato BR: " + s);

        // parse de data em formato não padrão
        String texto = "25-12-2000";
        LocalDate natal = LocalDate.parse(texto, formatoBR);
        System.out.println("natal em formato padrão: " + natal);
        System.out.println("natal em formato BR: " + natal.format(formatoBR));
    }
}
