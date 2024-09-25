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

        // Analisi della funzione f(n)
        FnComponents fnComponents = getFnComponents(fn);

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
                expansion.append("T(1) = T(1)<br>");
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
        solution.append("T(n) = n^{\\log_{").append(b).append("} ").append(a).append("} + ")
                .append(buildSummationWithLog(a, b, fn))
                .append("\n");
        solution.append("$$\n");

        // Espansione della sommatoria con sostituzione di k
        solution.append("<h3>Sviluppo della sommatoria:</h3>\n");
        solution.append(expandSummation(a, b, fn));

        // Semplificazione
        solution.append("<h3>Semplificazione della sommatoria:</h3>\n");
        solution.append("Risolvendo la sommatoria, otteniamo:<br>");
        solution.append("$$\n");
        solution.append("S = ").append(resolveSummation(a, b, fn, n))
                .append("\n$$\n");

        // Semplificazione finale
        solution.append("<h3>Semplificazione finale:</h3>\n");
        solution.append("Poiché \\( T(1) = \\Theta(1) \\):<br>");
        solution.append("$$\n");
        String degreeStr = formatExponent(fnComponents.degree);
        String logExpStr = formatExponent(fnComponents.logExponent);

        solution.append("T(n) = n^{\\log_{").append(b).append("} ").append(a).append("} + ")
                .append("n").append(degreeStr).append(" (\\log n)").append(logExpStr).append(" \\times ")
                .append("\\left( \\log_{").append(b).append("} n \\right)")
                .append("\n$$\n");

        // Determinazione della complessità
        solution.append("<h3>Conclusione:</h3>\n");
        String complexity = determineComplexity(a, b, fn);

        solution.append("La complessità asintotica è:<br>");
        solution.append("$$\n");
        solution.append("T(n) = \\Theta\\left(").append(complexity).append("\\right)\n");
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
        fn = fn.replaceAll("log\\(([^)]+)\\)", "\\\\log \\left($1\\right)");
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
        // Sostituisci 'n' con '\\left(\\dfrac{n}{b^{i}}\\right)' nel fn formattato
        formattedFn = formattedFn.replaceAll("n", "\\\\left(\\\\dfrac{n}{" + b + "^{i}}\\\\right)");

        summation.append(formattedFn);

        return summation.toString();
    }

    // Metodo per costruire la sommatoria con logaritmo
    private static String buildSummationWithLog(int a, int b, String fn) {
        StringBuilder summation = new StringBuilder();
        summation.append("\\sum_{i=0}^{\\log_{").append(b).append("} n -1} ").append(a).append("^{i} \\times ");

        String formattedFn = formatFn(fn);
        // Sostituisci 'n' con '\\left(\\dfrac{n}{" + b + "^{i}}\\right)' nel fn formattato
        formattedFn = formattedFn.replaceAll("n", "\\\\left(\\\\dfrac{n}{" + b + "^{i}}\\\\right)");

        summation.append(formattedFn);

        return summation.toString();
    }

    // Metodo per espandere la sommatoria con sostituzione di k
    private static String expandSummation(int a, int b, String fn) {
        StringBuilder expansion = new StringBuilder();
        expansion.append("Sostituiamo \\( k = \\log_{").append(b).append("} n \\) nella sommatoria:<br>");
        expansion.append("$$\n");
        expansion.append("S = ").append(buildSummationWithLog(a, b, fn));
        expansion.append("\n$$\n");

        return expansion.toString();
    }

    // Metodo per risolvere la sommatoria
    private static String resolveSummation(int a, int b, String fn, int n) {
        FnComponents fnComponents = getFnComponents(fn);
        double d = fnComponents.degree;
        double k = fnComponents.logExponent;

        // Calcolo del rapporto r = a / b^d
        double ratio = a / Math.pow(b, d);

        StringBuilder solution = new StringBuilder();

        // Caso in cui f(n) = Theta(n^d log^k n)
        solution.append("n");

        // Aggiungere l'esponente di n se degree != 1
        if (d != 1.0) {
            solution.append("^{").append(formatNumberForLatex(d)).append("}");
        }

        // Aggiungere il termine logaritmico se presente
        if (k != 0.0) {
            solution.append(" (\\log n)");
            if (k != 1.0) {
                solution.append("^{").append(formatNumberForLatex(k)).append("}");
            }
        }

        solution.append(" \\times ");

        if (Math.abs(ratio - 1) < 1e-6) {
            // Caso in cui il rapporto è circa 1
            solution.append("\\left( \\log_{").append(b).append("} n \\right)");
        } else {
            // Caso generale della serie
            double exponent = (Math.log(a) / Math.log(b)) - d;
            solution.append("\\dfrac{1 - n^{").append(formatNumberForLatex(exponent)).append("}}{1 - \\dfrac{").append(a).append("}{").append(b).append("^{").append(formatNumberForLatex(d)).append("}}}");
        }

        return solution.toString();
    }

    // Metodo per determinare la complessità asintotica
    private static String determineComplexity(int a, int b, String fn) {
        double logba = Math.log(a) / Math.log(b);

        FnComponents fnComponents = getFnComponents(fn);
        double d = fnComponents.degree;
        double k = fnComponents.logExponent;

        if (Math.abs(d - logba) < 1e-6) {
            return "n^{" + formatNumberForLatex(d) + "} (\\log n)^{" + formatNumberForLatex(k + 1) + "}";
        } else if (d > logba) {
            return "n^{" + formatNumberForLatex(d) + "} (\\log n)^{" + formatNumberForLatex(k) + "}";
        } else {
            return "n^{" + formatNumberForLatex(logba) + "}";
        }
    }

    // Classe per memorizzare il grado polinomiale e l'esponente logaritmico
    private static class FnComponents {
        double degree;
        double logExponent;

        FnComponents(double degree, double logExponent) {
            this.degree = degree;
            this.logExponent = logExponent;
        }
    }

    // Metodo per ottenere il grado di f(n) e l'esponente logaritmico
    private static FnComponents getFnComponents(String fn) {
        fn = fn.replaceAll("\\s+", "").toLowerCase();
        double degree = 0.0;
        double logExponent = 0.0;

        // Match per n^d
        Pattern polyPattern = Pattern.compile("n\\^(\\d+(\\.\\d+)?)");
        Matcher polyMatcher = polyPattern.matcher(fn);
        if (polyMatcher.find()) {
            degree = Double.parseDouble(polyMatcher.group(1));
        } else if (fn.contains("n")) {
            degree = 1.0;
        }

        // Match per log^k n
        Pattern logPattern = Pattern.compile("log\\^?(\\d+(\\.\\d+)?)?n");
        Matcher logMatcher = logPattern.matcher(fn);
        if (logMatcher.find()) {
            if (logMatcher.group(1) != null) {
                logExponent = Double.parseDouble(logMatcher.group(1));
            } else {
                logExponent = 1.0;
            }
        }

        // Convertire in interi se possibile
        if (degree == (int) degree) {
            degree = (int) degree;
        }

        if (logExponent == (int) logExponent) {
            logExponent = (int) logExponent;
        }

        return new FnComponents(degree, logExponent);
    }

    // Metodo per formattare un esponente (se è 1 o 0, omette l'esponente)
    private static String formatExponent(double exponent) {
        if (exponent == 0.0) {
            return "";
        } else if (exponent == 1.0) {
            return "";
        } else {
            return "^{"+formatNumberForLatex(exponent)+"}";
        }
    }

    // Metodo per formattare un numero per LaTeX (rimuove .0 se intero)
    private static String formatNumberForLatex(double number) {
        if (number == (int) number) {
            return String.valueOf((int) number);
        } else {
            return String.valueOf(number);
        }
    }
}
