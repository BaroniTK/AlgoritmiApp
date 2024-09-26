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
        solution.append("T(n) = ").append(a)
                .append("T\\left(\\dfrac{n}{").append(b)
                .append("}\\right) + ").append(formatFn(fn)).append("\n");
        solution.append("$$\n");

        // Espansione
        solution.append("<h2>Soluzione con Espansione Iterativa:</h2>\n");
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
        solution.append("T(n) = ").append(a).append("^{\\log_{").append(b).append("} n} \\times T(1) + ")
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
        solution.append("S = ").append(resolveSummation(a, b, fn))
                .append("\n$$\n");

        // Semplificazione finale
        solution.append("<h3>Semplificazione finale:</h3>\n");
        solution.append("Poiché \\( T(1) = \\Theta(1) \\):<br>");
        solution.append("$$\n");
        solution.append("T(n) = n^{\\log_{").append(b).append("} ").append(a).append("} + ").append(resolveSummation(a, b, fn))
                .append("\n$$\n");

        // Determinazione della complessità
        solution.append("<h3>Conclusione:</h3>\n");
        String complexity = determineComplexity(a, b, fn);

        solution.append("La complessità asintotica è:<br>");
        solution.append("$$\n");
        solution.append("T(n) = \\Theta(").append(complexity).append(")\n");
        solution.append("$$\n");

        return solution.toString();
    }

    // Metodo per risolvere una ricorrenza utilizzando il Teorema di Master
    public static String solveRecurrenceWithMasterTheorem(RecurrenceParser.Recurrence recurrence) {
        StringBuilder solution = new StringBuilder();

        int a = recurrence.a;
        int b = recurrence.b;
        String fn = recurrence.fn;

        // Titolo
        solution.append("<h2>Ricorrenza:</h2>\n");
        solution.append("$$\n");
        solution.append("T(n) = ").append(a)
                .append("T\\left(\\dfrac{n}{").append(b)
                .append("}\\right) + ").append(formatFn(fn)).append("\n");
        solution.append("$$\n");

        // Calcolo di log_b a
        double logba = Math.log(a) / Math.log(b);
        String logbaStr = String.format("%.2f", logba);

        solution.append("<h3>Calcolo di \\( \\log_{").append(b).append("} ").append(a).append(" = ").append(logbaStr).append(" \\):</h3>\n");

        // Analisi di f(n)
        FnComponents fnComponents = getFnComponents(fn);
        String d = fnComponents.degree;
        String k = fnComponents.logExponent;

        solution.append("<h3>Analisi di \\( f(n) \\):</h3>\n");
        solution.append("$$\n");
        solution.append("f(n) = ").append(formatFn(fn)).append(" = \\Theta\\left( n^{").append(d).append("}");

        if (!k.equals("0")) {
            solution.append(" (\\log n)^{").append(k).append("}");
        }

        solution.append(" \\right)\n$$\n");

        // Confronto tra f(n) e n^{log_b a}
        solution.append("<h3>Confronto tra \\( f(n) \\) e \\( n^{\\log_{").append(b).append("} ").append(a).append("} \\):</h3>\n");
        solution.append("Confrontiamo gli esponenti:\n");
        solution.append("$$\n");
        solution.append("d = ").append(d).append(" \\quad \\text{e} \\quad \\log_{").append(b).append("} ").append(a).append(" = ").append(logbaStr).append("\n");
        solution.append("$$\n");

        double dDouble = Double.parseDouble(d);
        int comparison = Double.compare(dDouble, logba);

        // Applicazione del caso appropriato
        if (comparison < 0) {
            // Caso 1
            solution.append("<h3>Applicazione del Teorema di Master - Caso 1:</h3>\n");
            solution.append("Poiché \\( d = ").append(d).append(" < \\log_{").append(b).append("} ").append(a).append(" = ").append(logbaStr).append(" \\),\n");
            solution.append("allora:\n");
            solution.append("$$\n");
            solution.append("T(n) = \\Theta\\left( n^{").append(logbaStr).append("} \\right)\n");
            solution.append("$$\n");
        } else if (comparison == 0) {
            // Caso 2
            solution.append("<h3>Applicazione del Teorema di Master - Caso 2:</h3>\n");
            solution.append("Poiché \\( d = ").append(d).append(" = \\log_{").append(b).append("} ").append(a).append(" = ").append(logbaStr).append(" \\),\n");
            solution.append("e \\( f(n) = \\Theta\\left( n^{").append(d).append("} (\\log n)^{").append(k).append("} \\right) \\),\n");
            solution.append("allora:\n");
            int kPlusOne = Integer.parseInt(k) + 1;
            solution.append("$$\n");
            solution.append("T(n) = \\Theta\\left( n^{").append(d).append("} (\\log n)^{").append(kPlusOne).append("} \\right)\n");
            solution.append("$$\n");
        } else {
            // Caso 3
            solution.append("<h3>Applicazione del Teorema di Master - Caso 3:</h3>\n");
            solution.append("Poiché \\( d = ").append(d).append(" > \\log_{").append(b).append("} ").append(a).append(" = ").append(logbaStr).append(" \\),\n");
            solution.append("e \\( f(n) = \\Omega\\left( n^{").append(d).append("} \\right) \\),\n");
            solution.append("e soddisfa la condizione di regolarità, allora:\n");
            solution.append("$$\n");
            solution.append("T(n) = \\Theta\\left( f(n) \\right) = \\Theta\\left( n^{").append(d).append("}");

            if (!k.equals("0")) {
                solution.append(" (\\log n)^{").append(k).append("}");
            }

            solution.append(" \\right)\n$$\n");
        }

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
        fn = fn.replaceAll("log\\((n)\\)", "\\\\log $1");
        fn = fn.replaceAll("log\\((n/[^)]+)\\)", "\\\\log \\left($1\\right)");
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
        expansion.append("S = \\sum_{i=0}^{\\log_{").append(b).append("} n -1} ").append(a).append("^{i} \\times ");

        String formattedFn = formatFn(fn);
        formattedFn = formattedFn.replaceAll("n", "\\\\left(\\\\dfrac{n}{" + b + "^{i}}\\\\right)");

        expansion.append(formattedFn);
        expansion.append("\n$$\n");

        return expansion.toString();
    }

    // Metodo per risolvere la sommatoria
    private static String resolveSummation(int a, int b, String fn) {
        FnComponents fnComponents = getFnComponents(fn);
        String d = fnComponents.degree;
        String k = fnComponents.logExponent;

        // Calcolo del rapporto r = a / b^d
        String ratio = "\\dfrac{" + a + "}{" + b + "^{" + d + "}}";

        StringBuilder solution = new StringBuilder();

        // Caso in cui f(n) = Theta(n^d log^k n)
        solution.append("n^{").append(d).append("} ");

        if (!k.equals("0")) {
            solution.append("(\\log n)^{").append(k).append("} ");
        }

        solution.append("\\times \\sum_{i=0}^{\\log_{").append(b).append("} n -1} \\left(")
                .append(ratio).append("\\right)^{i}");

        if (!k.equals("0")) {
            solution.append(" \\left( \\dfrac{\\log \\left( \\dfrac{n}{" + b + "^{i}} \\right)}{\\log n} \\right)^{").append(k).append("}");
        }

        // Semplificazione dell'esponente
        String exponent = "\\log_{" + b + "} " + a + " - " + d;

        // Aggiunge la semplificazione finale
        solution.append(" = n^{").append(d).append("} ");

        if (!k.equals("0")) {
            solution.append("(\\log n)^{").append(k).append("} ");
        }

        solution.append("\\times \\dfrac{1 - n^{").append(exponent).append("}}{1 - ").append(ratio).append("}");

        return solution.toString();
    }

    // Metodo per determinare la complessità asintotica
    private static String determineComplexity(int a, int b, String fn) {
        double logba = Math.log(a) / Math.log(b);
        String logbaStr = String.format("%.2f", logba);

        FnComponents fnComponents = getFnComponents(fn);
        String d = fnComponents.degree;
        String k = fnComponents.logExponent;

        double dDouble = Double.parseDouble(d);
        int comparison = Double.compare(dDouble, logba);

        if (comparison == 0) {
            int kPlusOne = Integer.parseInt(k) + 1;
            return "n^{" + d + "} (\\log n)^{" + kPlusOne + "}";
        } else if (comparison > 0) {
            if (!k.equals("0")) {
                return "n^{" + d + "} (\\log n)^{" + k + "}";
            } else {
                return "n^{" + d + "}";
            }
        } else {
            return "n^{" + logbaStr + "}";
        }
    }

    // Classe per memorizzare il grado polinomiale e l'esponente logaritmico
    private static class FnComponents {
        String degree;
        String logExponent;

        FnComponents(String degree, String logExponent) {
            this.degree = degree;
            this.logExponent = logExponent;
        }
    }

    // Metodo per ottenere il grado di f(n) e l'esponente logaritmico
    private static FnComponents getFnComponents(String fn) {
        fn = fn.replaceAll("\\s+", "").toLowerCase();
        String degree = "0";
        String logExponent = "0";

        // Match per n^d
        Pattern polyPattern = Pattern.compile("n\\^\\{?(\\d+(\\.\\d+)?)\\}?");
        Matcher polyMatcher = polyPattern.matcher(fn);
        if (polyMatcher.find()) {
            degree = polyMatcher.group(1);
        } else if (fn.contains("n")) {
            degree = "1";
        }

        // Match per log^k n
        Pattern logPattern = Pattern.compile("log\\^\\{?(\\d+(\\.\\d+)?)\\}?n");
        Matcher logMatcher = logPattern.matcher(fn);
        if (logMatcher.find()) {
            logExponent = logMatcher.group(1) != null ? logMatcher.group(1) : "1";
        } else if (fn.contains("logn") || fn.contains("log(n)")) {
            logExponent = "1";
        }

        return new FnComponents(degree, logExponent);
    }
}
