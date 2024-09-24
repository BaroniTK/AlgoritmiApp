package com.example.recurrenceresolver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IterativeRecurrenceSolver {

    // Metodo per risolvere una ricorrenza tramite espansione iterativa
    public static String solveRecurrenceIteratively(RecurrenceParser.Recurrence recurrence, int n) {
        StringBuilder solution = new StringBuilder();

        int a = recurrence.a;
        int b = recurrence.b;
        String fn = recurrence.fn;

        // Titolo
        solution.append("<h2>Ricorrenza:</h2>\n");
        solution.append("$$\n");
        solution.append("T(n) = ").append(a).append("T\\left(\\dfrac{n}{").append(b).append("}\\right) + ").append(formatFn(fn)).append("\n");
        solution.append("$$\n");

        // Espansione
        solution.append("<h2>Soluzione:</h2>\n");
        solution.append("<h3>Espansione:</h3>\n");

        // Inizializzazione per l'espansione
        int k = 0;
        int currentN = n;
        StringBuilder expansion = new StringBuilder();

        while (currentN >= 1 && k < 100) { // Evita loop infiniti
            // Calcolo del termine fn
            String currentFn = computeFn(fn, currentN);

            // Aggiunta del passo di espansione
            expansion.append("T(").append(currentN).append(") = ")
                    .append(a).append("T(").append(currentN).append("/").append(b).append(") + ").append(currentFn).append("<br>");

            // Preparazione per il prossimo passo
            currentN = currentN / b;
            k++;

            // Caso base raggiunto
            if (currentN < 1) {
                expansion.append("T(1) = 1<br>");
                break;
            }
        }

        solution.append(expansion.toString());

        // Identificazione del pattern generale
        solution.append("<h3>Osservazione del pattern:</h3>\n");
        solution.append("Dopo \\( k \\) passi, otteniamo:<br>");
        solution.append("$$\n");
        solution.append("T(n) = ").append(a).append("^{k}T\\left(\\dfrac{n}{").append(b).append("^{k}}\\right) + ")
                .append(buildSummation(a, b, fn, k))
                .append("\n");
        solution.append("$$\n");

        // Calcolo di k quando si raggiunge il caso base
        solution.append("<h3>Caso base:</h3>\n");
        solution.append("Quando \\( \\dfrac{n}{").append(b).append("^{k}} = 1 \\), cioè \\( n = ").append(b).append("^{k} \\):<br>");
        solution.append("$$\n");
        solution.append("k = \\log_{").append(b).append("} n\n");
        solution.append("$$\n");

        // Sostituzione nel pattern generale
        solution.append("<h3>Sostituzione nel pattern generale:</h3>\n");
        solution.append("Sostituendo il valore di \\( k \\):<br>");
        solution.append("$$\n");
        solution.append("T(n) = n^{\\log_{").append(b).append("} ").append(a).append("} \\times T(1) + ")
                .append(buildSummationWithLog(a, b, fn))
                .append("\n");
        solution.append("$$\n");

        // Espansione della sommatoria
        solution.append("<h3>Sviluppo della sommatoria:</h3>\n");
        solution.append(expandSummation(a, b, fn, k));

        // Semplificazione
        solution.append("<h3>Semplificazione:</h3>\n");
        solution.append("Poiché \\( T(1) = 1 \\):<br>");
        solution.append("$$\n");
        solution.append("T(n) = n^{\\log_{").append(b).append("} ").append(a).append("} + \\text{[Termine della sommatoria sviluppato]}\n");
        solution.append("$$\n");

        // Determinazione della complessità
        solution.append("<h3>Conclusione:</h3>\n");
        String complexity = determineComplexity(a, b, fn);

        solution.append("La complessità asintotica è:<br>");
        solution.append("$$\n");
        solution.append("T(n) = \\Theta(").append(complexity).append(")\n");
        solution.append("$$\n");

        return solution.toString();
    }

    // Metodo per formattare f(n) per l'output
    private static String formatFn(String fn) {
        // Rimuove gli spazi bianchi
        fn = fn.replaceAll("\\s+", "");
        // Formatta gli esponenti: n^k --> n^{k}
        fn = fn.replaceAll("n\\^(\\d+(\\.\\d+)?)", "n^{$1}");
        // Gestisce logaritmi se presenti
        fn = fn.replaceAll("logn", "\\\\log n");
        fn = fn.replaceAll("log\\(n\\)", "\\\\log n");
        return fn;
    }

    // Metodo per calcolare f(n) per un dato n (solo per visualizzazione)
    private static String computeFn(String fn, int n) {
        // Sostituzione di n in fn
        String result = fn.replaceAll("n", Integer.toString(n));
        // Sostituzione di "log" per evitare problemi
        result = result.replaceAll("log", "log ");
        return result;
    }

    // Metodo per costruire la sommatoria
    private static String buildSummation(int a, int b, String fn, int k) {
        StringBuilder summation = new StringBuilder();
        summation.append("\\sum_{i=0}^{").append(k - 1).append("} ").append(a).append("^{i} \\times ");

        String formattedFn = formatFn(fn);
        // Sostituisci 'n' con '\\left(\\dfrac{n}{" + b + "^{i}}\\right)' nel fn formattato
        formattedFn = formattedFn.replaceAll("n", "\\\\left(\\\\dfrac\\{n\\}{" + b + "^{i}}\\\\right)");

        summation.append(formattedFn);

        return summation.toString();
    }

    // Metodo per costruire la sommatoria con logaritmo
    private static String buildSummationWithLog(int a, int b, String fn) {
        StringBuilder summation = new StringBuilder();
        summation.append("\\sum_{i=0}^{\\log_{").append(b).append("} n -1} ").append(a).append("^{i} \\times ");

        String formattedFn = formatFn(fn);
        // Sostituisci 'n' con '\\left(\\dfrac{n}{" + b + "^{i}}\\right)' nel fn formattato
        formattedFn = formattedFn.replaceAll("n", "\\\\left(\\\\dfrac\\{n\\}{" + b + "^{i}}\\\\right)");

        summation.append(formattedFn);

        return summation.toString();
    }

    // Metodo per espandere la sommatoria
    private static String expandSummation(int a, int b, String fn, int k) {
        StringBuilder expansion = new StringBuilder();
        expansion.append("Espandendo la sommatoria:<br>");
        expansion.append("$$\n");
        expansion.append("\\begin{align*}\n");
        expansion.append("S &= ");

        // Costruzione dei termini della sommatoria
        for (int i = 0; i < k; i++) {
            if (i > 0) {
                expansion.append("+ ");
            }

            expansion.append(a).append("^{").append(i).append("} \\times ");
            String term = formatFn(fn).replaceAll("n", "\\\\left(\\\\dfrac\\{n\\}{" + b + "^{" + i + "}}\\\\right)");
            expansion.append(term);

            if (i < k - 1) {
                expansion.append(" \\\\\n& ");
            }
        }

        expansion.append("\n\\end{align*}\n");
        expansion.append("$$\n");

        return expansion.toString();
    }

    // Metodo per determinare la complessità asintotica
    private static String determineComplexity(int a, int b, String fn) {
        double logba = Math.log(a) / Math.log(b);

        // Determinazione del grado di f(n)
        double fnDegree = getFnDegree(fn);

        if (Math.abs(fnDegree - logba) < 1e-6) {
            return "n^{" + String.format("%.2f", logba) + "} \\log n";
        } else if (fnDegree > logba) {
            return formatFn(fn);
        } else {
            return "n^{" + String.format("%.2f", logba) + "}";
        }
    }

    // Metodo per ottenere il grado di f(n)
    private static double getFnDegree(String fn) {
        // Match per n^k
        Pattern pattern = Pattern.compile("n\\^(\\d+(\\.\\d+)?)");
        Matcher matcher = pattern.matcher(fn.replaceAll("\\s+", ""));
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        } else if (fn.trim().equals("n")) {
            return 1.0;
        } else if (fn.toLowerCase().contains("logn") || fn.toLowerCase().contains("log n")) {
            return 0.0; // Consideriamo log n come grado 0 per semplicità
        } else {
            return 0.0;
        }
    }
}
