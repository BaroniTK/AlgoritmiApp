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

        // Titolo della ricorrenza
        solution.append("<h2>Ricorrenza:</h2>\n");
        solution.append("$$\n");
        solution.append("T(n) = ").append(a)
                .append("T\\left(\\dfrac{n}{").append(b)
                .append("}\\right) + ").append(formatFn(fn)).append(", \\quad n > 1\n");
        solution.append("$$\n");
        solution.append("$$\n");
        solution.append("T(1) = \\Theta(1)\n");
        solution.append("$$\n");

        // Espansione
        solution.append("<h2>Soluzione con Espansione Iterativa:</h2>\n");

        // Identificazione del pattern generale
        solution.append("<h3>Espansione generale:</h3>\n");
        solution.append("Espandiamo la ricorrenza iterativamente:\n");
        solution.append("$$\n");
        solution.append("\\begin{align*}\n");
        solution.append("T(n) &= ").append(a).append(" T\\left( \\dfrac{n}{").append(b).append("} \\right) + f(n) \\\\\n");
        solution.append("&= ").append(a).append(" \\left[ ").append(a).append(" T\\left( \\dfrac{n}{").append(b).append("^2} \\right) + f\\left( \\dfrac{n}{").append(b).append("} \\right) \\right ] + f(n) \\\\\n");
        solution.append("&= ").append(a).append("^2 T\\left( \\dfrac{n}{").append(b).append("^2} \\right) + ")
                .append(a).append(" f\\left( \\dfrac{n}{").append(b).append("} \\right) + f(n) \\\\\n");
        solution.append("&\\vdots \\\\\n");
        solution.append("&= ").append(a).append("^k T\\left( \\dfrac{n}{").append(b).append("^k} \\right) + \\sum_{i=0}^{k-1} ")
                .append(a).append("^i f\\left( \\dfrac{n}{").append(b).append("^i} \\right)\n");
        solution.append("\\end{align*}\n");
        solution.append("$$\n");

        // Calcolo di k quando si raggiunge il caso base
        solution.append("<h3>Caso base:</h3>\n");
        solution.append("Quando \\( \\dfrac{n}{").append(b).append("^k} = 1 \\), cioè quando \\( n = ").append(b).append("^k \\):\n");
        solution.append("$$\n");
        solution.append("k = \\log_{").append(b).append("} n\n");
        solution.append("$$\n");

        // Sostituzione nel pattern generale
        solution.append("<h3>Sostituzione nel pattern generale:</h3>\n");
        solution.append("Sostituendo \\( k \\) nell'espressione espansa, otteniamo:\n");
        solution.append("$$\n");
        solution.append("T(n) = ").append(a).append("^{\\log_{").append(b).append("} n} T(1) + \\sum_{i=0}^{\\log_{").append(b).append("} n -1} ")
                .append(a).append("^i f\\left( \\dfrac{n}{").append(b).append("^i} \\right)\n");
        solution.append("$$\n");

        // Semplificazione di a^{log_b n}
        solution.append("<h3>Semplificazione di \\( ").append(a).append("^{\\log_{").append(b).append("} n} \\):</h3>\n");
        solution.append("Utilizziamo la proprietà dei logaritmi:\n");
        solution.append("$$\n");
        solution.append(a).append("^{\\log_{").append(b).append("} n} = n^{\\log_{").append(b).append("} ").append(a).append("}\n");
        solution.append("$$\n");

        // Sostituzione nella formula
        solution.append("Quindi, l'espressione diventa:\n");
        solution.append("$$\n");
        solution.append("T(n) = n^{\\log_{").append(b).append("} ").append(a).append("} T(1) + \\sum_{i=0}^{\\log_{").append(b).append("} n -1} ")
                .append(a).append("^i f\\left( \\dfrac{n}{").append(b).append("^i} \\right)\n");
        solution.append("$$\n");

        // Analisi della sommatoria
        solution.append("<h3>Analisi della sommatoria:</h3>\n");

        // Otteniamo i componenti di f(n)
        FnComponents fnComponents = getFnComponents(fn);
        String d = fnComponents.degree;
        String kValue = fnComponents.logExponent;

        // Calcolo del rapporto r = a / b^d
        solution.append("Calcoliamo il rapporto:\n");
        solution.append("$$\n");
        solution.append("r = \\dfrac{a}{").append(b).append("^").append(d).append("}\n");
        solution.append("$$\n");

        // Determinazione della sommatoria
        solution.append("La sommatoria diventa:\n");
        solution.append("$$\n");
        solution.append("\\sum_{i=0}^{\\log_{").append(b).append("} n -1} ").append(a).append("^i \\left( \\dfrac{n}{").append(b).append("^i} \\right)^{").append(d).append("}");

        if (!kValue.equals("0")) {
            solution.append(" \\left( \\log \\dfrac{n}{").append(b).append("^i} \\right)^{").append(kValue).append("}");
        }

        solution.append("\n$$\n");

        // Semplificazione della sommatoria
        solution.append("<h3>Semplificazione della sommatoria:</h3>\n");
        solution.append("Semplifichiamo i termini nella sommatoria:\n");
        solution.append("$$\n");
        solution.append(a).append("^i \\left( \\dfrac{n}{").append(b).append("^i} \\right)^{").append(d).append("} = ")
                .append(a).append("^i \\times n^{").append(d).append("} \\times \\left( \\dfrac{1}{").append(b).append("^{i}} \\right)^{").append(d).append("}\n");
        solution.append("$$\n");
        solution.append("Poiché \\( a^i \\times \\left( \\dfrac{1}{").append(b).append("^{i}} \\right)^{").append(d).append("} = \\left( \\dfrac{a}{").append(b).append("^").append(d).append("} \\right)^{i} \\), otteniamo:\n");
        solution.append("$$\n");
        solution.append("\\left( \\dfrac{a}{").append(b).append("^").append(d).append("} \\right)^{i} n^{").append(d).append("}\n");
        solution.append("$$\n");

        // Riscrittura della sommatoria
        solution.append("Quindi, la sommatoria diventa:\n");
        solution.append("$$\n");
        solution.append("n^{").append(d).append("} \\sum_{i=0}^{\\log_{").append(b).append("} n -1} \\left( \\dfrac{a}{").append(b).append("^").append(d).append("} \\right)^{i}");

        if (!kValue.equals("0")) {
            solution.append(" \\left( \\log \\dfrac{n}{").append(b).append("^i} \\right)^{").append(kValue).append("}");
        }

        solution.append("\n$$\n");

        // Valutazione della sommatoria geometrica
        solution.append("<h3>Valutazione della sommatoria geometrica:</h3>\n");
        solution.append("Il termine \\( \\left( \\dfrac{a}{").append(b).append("^").append(d).append("} \\right) \\) è la ragione della progressione geometrica.\n");

        // Controllo del valore di r per determinare il comportamento della sommatoria
        double rValue = a / Math.pow(b, Double.parseDouble(d));
        solution.append("Calcoliamo il valore di \\( r \\):\n");
        solution.append("$$\n");
        solution.append("r = \\dfrac{").append(a).append("}{").append(b).append("^").append(d).append("} = ").append(formatDouble(rValue)).append("\n");
        solution.append("$$\n");

        if (rValue < 1) {
            solution.append("Poiché \\( r < 1 \\), la sommatoria converge e possiamo utilizzare la formula della somma di una progressione geometrica finita:\n");
            solution.append("$$\n");
            solution.append("\\sum_{i=0}^{k-1} r^{i} = \\dfrac{1 - r^{k}}{1 - r}\n");
            solution.append("$$\n");
        } else if (rValue == 1) {
            solution.append("Poiché \\( r = 1 \\), la sommatoria diventa:\n");
            solution.append("$$\n");
            solution.append("\\sum_{i=0}^{k-1} 1 = k\n");
            solution.append("$$\n");
        } else {
            solution.append("Poiché \\( r > 1 \\), la sommatoria cresce esponenzialmente.\n");
        }

        // Calcolo finale della sommatoria
        solution.append("<h3>Calcolo finale della sommatoria:</h3>\n");

        if (rValue != 1) {
            solution.append("Sostituendo i valori, otteniamo:\n");
            solution.append("$$\n");
            solution.append("\\sum_{i=0}^{\\log_{").append(b).append("} n -1} \\left( \\dfrac{a}{").append(b).append("^").append(d).append("} \\right)^{i} = \\dfrac{1 - \\left( \\dfrac{a}{").append(b).append("^").append(d).append("} \\right)^{\\log_{").append(b).append("} n}}{1 - \\dfrac{a}{").append(b).append("^").append(d).append("}}\n");
            solution.append("$$\n");

            // Semplificazione dell'esponente
            solution.append("Semplifichiamo l'esponente:\n");
            solution.append("$$\n");
            solution.append("\\left( \\dfrac{a}{").append(b).append("^").append(d).append("} \\right)^{\\log_{").append(b).append("} n} = n^{\\log_{").append(b).append("} \\left( \\dfrac{a}{").append(b).append("^").append(d).append("} \\right)}\n");
            solution.append("$$\n");

            // Calcolo dell'esponente
            double exponentValue = (Math.log(a) - Double.parseDouble(d) * Math.log(b)) / Math.log(b);
            String exponentStr = formatDouble(exponentValue);
            solution.append("Calcoliamo l'esponente:\n");
            solution.append("$$\n");
            solution.append("\\log_{").append(b).append("} \\left( \\dfrac{").append(a).append("}{").append(b).append("^").append(d).append("} \\right) = ").append(exponentStr).append("\n");
            solution.append("$$\n");

            // Sostituzione nell'espressione
            solution.append("Quindi, la sommatoria diventa:\n");
            solution.append("$$\n");
            solution.append("\\dfrac{1 - n^{").append(exponentStr).append("}}{1 - \\dfrac{").append(a).append("}{").append(b).append("^").append(d).append("}}\n");
            solution.append("$$\n");

            // Sostituzione nella formula di T(n)
            solution.append("Sostituendo tutto nell'espressione di \\( T(n) \\):\n");
            solution.append("$$\n");
            solution.append("T(n) = n^{\\log_{").append(b).append("} ").append(a).append("} T(1) + n^{").append(d).append("} \\times \\dfrac{1 - n^{").append(exponentStr).append("}}{1 - \\dfrac{").append(a).append("}{").append(b).append("^").append(d).append("}}\n");
            solution.append("$$\n");
        } else {
            // Caso r = 1
            solution.append("La sommatoria diventa:\n");
            solution.append("$$\n");
            solution.append("\\sum_{i=0}^{\\log_{").append(b).append("} n -1} 1 = \\log_{").append(b).append("} n\n");
            solution.append("$$\n");

            // Sostituzione nella formula di T(n)
            solution.append("Quindi, l'espressione di \\( T(n) \\) diventa:\n");
            solution.append("$$\n");
            solution.append("T(n) = n^{\\log_{").append(b).append("} ").append(a).append("} T(1) + n^{").append(d).append("} \\times \\log_{").append(b).append("} n\n");
            solution.append("$$\n");
        }

        // Determinazione della complessità asintotica
        solution.append("<h3>Determinazione della complessità asintotica:</h3>\n");

        double logba = Math.log(a) / Math.log(b);
        double exponentDifference = logba - Double.parseDouble(d);

        if (rValue < 1) {
            if (exponentDifference > 0) {
                solution.append("Poiché \\( \\log_{").append(b).append("} ").append(a).append("} > ").append(d).append(" \\), il termine dominante è \\( n^{\\log_{").append(b).append("} ").append(a).append("}} \\).\n");
                solution.append("Pertanto:\n");
                solution.append("$$\n");
                solution.append("T(n) = \\Theta\\left( n^{").append(formatDouble(logba)).append("} \\right)\n");
                solution.append("$$\n");
            } else {
                solution.append("Il termine della sommatoria domina.\n");
                solution.append("Pertanto:\n");
                solution.append("$$\n");
                solution.append("T(n) = \\Theta\\left( n^{").append(d).append("} \\right)\n");
                solution.append("$$\n");
            }
        } else if (rValue == 1) {
            solution.append("Poiché \\( r = 1 \\), il termine \\( n^{").append(d).append("} \\log n \\) domina.\n");
            solution.append("Pertanto:\n");
            solution.append("$$\n");
            solution.append("T(n) = \\Theta\\left( n^{").append(d).append("} \\log n \\right)\n");
            solution.append("$$\n");
        } else {
            if (exponentDifference < 0) {
                solution.append("Poiché \\( \\log_{").append(b).append("} ").append(a).append("} < ").append(d).append(" \\), il termine della sommatoria domina.\n");
                solution.append("Calcoliamo:\n");
                solution.append("$$\n");
                double v = Math.log(a / Math.pow(b, Double.parseDouble(d))) / Math.log(b);
                double exponentValue1 = v;
                double totalExponent = Double.parseDouble(d) + exponentValue1;
                solution.append("T(n) = \\Theta\\left( n^{").append(formatDouble(totalExponent)).append("} \\right)\n");
                solution.append("$$\n");
            } else {
                solution.append("Entrambi i termini crescono allo stesso ritmo.\n");
                solution.append("Pertanto:\n");
                solution.append("$$\n");
                solution.append("T(n) = \\Theta\\left( n^{").append(formatDouble(logba)).append("} \\right)\n");
                solution.append("$$\n");
            }
        }

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
        String logbaStr = formatDouble(logba);

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

    // Metodo per risolvere una ricorrenza utilizzando il Metodo di Sostituzione
    public static String solveRecurrenceWithSubstitution(RecurrenceParser.Recurrence recurrence) {
        StringBuilder solution = new StringBuilder();

        int a = recurrence.a;
        int b = recurrence.b;
        String fn = recurrence.fn;

        // Titolo
        solution.append("<h2>Ricorrenza:</h2>\n");
        solution.append("$$\n");
        solution.append("T(n) = ");

        if (a != 1) {
            solution.append(a);
        }
        solution.append("T\\left(\\dfrac{n}{").append(b).append("}\\right) + ").append(formatFn(fn)).append("\n");
        solution.append("$$\n");

        // Step 1: Ipotesi
        solution.append("<h3>Passo 1: Ipotesi</h3>\n");
        String hypothesis = makeHypothesis(a, b, fn);
        solution.append("Supponiamo che la soluzione sia:\n");
        solution.append("$$\n");
        solution.append("T(n) \\leq c \\cdot ").append(hypothesis).append("\n");
        solution.append("$$\n");

        // Step 2: Verifica dell'ipotesi
        solution.append("<h3>Passo 2: Verifica dell'ipotesi</h3>\n");
        solution.append("Sostituiamo l'ipotesi induttiva nella ricorrenza:\n");
        solution.append("$$\n");
        solution.append("T(n) \\leq ");

        if (a != 1) {
            solution.append(a).append(" \\times ");
        }
        solution.append("T\\left(\\dfrac{n}{").append(b).append("}\\right) + ").append(formatFn(fn)).append("\n");
        solution.append("$$\n");

        solution.append("Sostituendo l'ipotesi induttiva:\n");
        solution.append("$$\n");
        String substitutedHypothesis = substituteHypothesis(a, b, fn, hypothesis);
        solution.append(substitutedHypothesis).append("\n");
        solution.append("$$\n");

        // Semplificazione
        solution.append("Semplificando:\n");
        solution.append("$$\n");
        String simplified = simplifyExpression(a, b, fn, hypothesis);
        solution.append(simplified).append("\n");
        solution.append("$$\n");

        // Conclusione
        solution.append("Poiché l'ipotesi è confermata, la complessità è:\n");
        solution.append("$$\n");
        solution.append("T(n) = \\mathcal{O}\\left(").append(hypothesis).append("\\right)\n");
        solution.append("$$\n");

        return solution.toString();
    }

    // Metodo per formulare l'ipotesi basata sulla ricorrenza
    private static String makeHypothesis(int a, int b, String fn) {
        FnComponents fnComponents = getFnComponents(fn);
        String d = fnComponents.degree;
        String k = fnComponents.logExponent;

        double logba = Math.log(a) / Math.log(b);
        String logbaStr = formatDouble(logba);

        String hypothesis = "";

        if (Double.parseDouble(d) == logba) {
            if (!k.equals("0")) {
                int kPlusOne = Integer.parseInt(k) + 1;
                hypothesis = "n^{" + d + "} (\\log n)^{" + kPlusOne + "}";
            } else {
                hypothesis = "n^{" + d + "} \\log n";
            }
        } else if (Double.parseDouble(d) > logba) {
            if (!k.equals("0")) {
                hypothesis = "n^{" + d + "} (\\log n)^{" + k + "}";
            } else {
                hypothesis = "n^{" + d + "}";
            }
        } else {
            hypothesis = "n^{" + logbaStr + "}";
        }

        return hypothesis;
    }

    // Metodo per sostituire l'ipotesi nella ricorrenza
    private static String substituteHypothesis(int a, int b, String fn, String hypothesis) {
        String c = "c"; // Costante

        // Sostituiamo n con (n/b) nell'ipotesi
        String hypExpr = hypothesis.replaceAll("n", "\\\\left(\\\\dfrac{n}{" + b + "}\\\\right)");
        hypExpr = hypExpr.replaceAll("\\\\log n", "\\\\log \\\\left(\\\\dfrac{n}{" + b + "}\\\\right)");

        // Costruire l'espressione sostituita
        String expression = "";

        if (a != 1) {
            expression += a + " \\times \\left( " + c + " \\cdot " + hypExpr + " \\right ) + " + formatFn(fn);
        } else {
            expression += c + " \\cdot " + hypExpr + " + " + formatFn(fn);
        }

        return "T(n) \\leq " + expression;
    }

    // Metodo per semplificare l'espressione
    private static String simplifyExpression(int a, int b, String fn, String hypothesis) {
        String c = "c";

        // Semplificare l'espressione
        String simplified = "";

        FnComponents fnComponents = getFnComponents(fn);
        String d = fnComponents.degree;
        String k = fnComponents.logExponent;

        if (hypothesis.contains("n^{")) {
            // Estrai l'esponente
            Pattern pattern = Pattern.compile("n\\^\\{(\\d+(\\.\\d+)?)\\}");
            Matcher matcher = pattern.matcher(hypothesis);
            if (matcher.find()) {
                String exponent = matcher.group(1);

                // Calcola a * c * (n / b)^{exponent}
                simplified = a + " \\times " + c + " \\cdot \\left( \\dfrac{n}{" + b + "} \\right )^{" + exponent + "} + " + formatFn(fn);

                // Semplifica ulteriormente
                simplified += " = " + c + " \\cdot n^{" + exponent + "} \\left( \\dfrac{1}{" + b + "} \\right )^{" + exponent + "} + " + formatFn(fn);

                simplified += " = " + c + " \\cdot n^{" + exponent + "} \\dfrac{1}{" + b + "^{" + exponent + "}} + " + formatFn(fn);

                simplified += " = \\left( \\dfrac{" + c + "}{" + b + "^{" + exponent + "}} + 1 \\right ) n^{" + exponent + "}";
            }
        } else if (hypothesis.contains("n \\log n")) {
            // Gestisci il caso con logaritmo
            simplified = a + " \\times " + c + " \\cdot \\dfrac{n}{" + b + "} \\log \\left( \\dfrac{n}{" + b + "} \\right ) + " + formatFn(fn);

            // Espandi e semplifica il logaritmo
            simplified += " = " + c + " \\cdot \\dfrac{n}{" + b + "} \\left( \\log n - \\log " + b + " \\right ) + " + formatFn(fn);

            // Semplifica i termini
            simplified += " = \\dfrac{" + c + " n \\log n}{" + b + "} - \\dfrac{" + c + " n \\log " + b + "}{" + b + "} + " + formatFn(fn);

            // Somma i termini simili
            simplified += " = \\left( \\dfrac{" + c + "}{" + b + "} + 1 \\right ) n \\log n - \\dfrac{" + c + " n \\log " + b + "}{" + b + "}";
        }

        return simplified;
    }

    // Metodo per formattare i numeri decimali
    private static String formatDouble(double value) {
        if (value == (long) value) {
            return String.format("%d", (long) value);
        } else {
            return String.format("%.2f", value);
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

    // Metodo per formattare f(n) per l'output
    private static String formatFn(String fn) {
        // Rimuove gli spazi bianchi
        fn = fn.replaceAll("\\s+", "");
        // Formatta gli esponenti: n^k --> n^{k}
        fn = fn.replaceAll("n\\^(\\d+(\\.\\d+)?)", "n^{$1}");
        // Gestisce logaritmi se presenti
        fn = fn.replaceAll("logn", "\\\\log n");
        fn = fn.replaceAll("log\\(([^)]+)\\)", "\\\\log \\left($1\\right)");
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
        String logbaStr = formatDouble(logba);

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

}
