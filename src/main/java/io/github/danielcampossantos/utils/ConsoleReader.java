package io.github.danielcampossantos.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Scanner;

public class ConsoleReader {
    private final Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter formatoData =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    public String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    public int lerInteiro(String mensagem) {
        return Integer.parseInt(lerTexto(mensagem));
    }

    public long lerLong(String mensagem) {
        return Long.parseLong(lerTexto(mensagem));
    }

    public LocalDate lerData(String mensagem) {
        String texto = lerTexto(mensagem);
        return texto.isEmpty() ? null : LocalDate.parse(texto, formatoData);
    }
}