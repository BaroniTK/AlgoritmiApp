package com.example.recurrenceresolver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecurrenceParser {

    // Classe per rappresentare una ricorrenza generica
    public static class Recurrence {
        int a;       // Numero di sottoproblemi (ad es., 4 in 4T(n/2))
        int b;       // Fattore di divisione per n (ad es., 2 in n/2)
        String fn;   // Funzione di crescita (ad es., n, n^2, n log n)

        public Recurrence(int a, int b, String fn) {
            this.a = a;
            this.b = b;
            this.fn = fn;
        }
    }

    // Metodo per parsare la ricorrenza dal formato semplificato e validare l'input
    public static Recurrence parseRecurrence(String input) throws IllegalArgumentException {
        // Espressione regolare per catturare la forma T(n) = aT(n/b) + f(n)
        String regex = "\\s*T\\(n\\)\\s*=\\s*(\\d+)\\s*T\\(n\\s*/\\s*(\\d+)\\)\\s*\\+\\s*(.+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            int a = Integer.parseInt(matcher.group(1));  // Coefficiente a
            int b = Integer.parseInt(matcher.group(2));  // Divisore b
            String fn = matcher.group(3).trim();         // Funzione f(n)
            // Validazione della funzione f(n)
            if (!isValidFn(fn)) {
                throw new IllegalArgumentException("Funzione f(n) non valida. Usa n, n^2, n^k, ecc.");
            }
            return new Recurrence(a, b, fn);
        } else {
            throw new IllegalArgumentException("Formato ricorrenza non valido. Usa il formato T(n) = aT(n/b) + f(n)");
        }
    }

    // Metodo per validare la funzione f(n)
    private static boolean isValidFn(String fn) {
        // Espressione regolare per riconoscere funzioni valide di n (ad esempio n, n^2, n log n)
        String validFnRegex = "(n(\\^\\d+(\\.\\d+)?)?(\\s*log\\s*n(\\^\\d+(\\.\\d+)?)?)?)";
        Pattern pattern = Pattern.compile(validFnRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(fn.replaceAll("\\s+", ""));
        return matcher.matches();
    }
}
