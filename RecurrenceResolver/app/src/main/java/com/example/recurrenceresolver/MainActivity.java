package com.example.recurrenceresolver;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText inputEquation;
    private MaterialButton btnCalcola;
    private WebView outputWebView;
    private Spinner spinnerMetodo;

    private String metodoSelezionato = "Espansione Iterativa"; // Valore di default

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Imposta la Toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputEquation = findViewById(R.id.input_equation);
        btnCalcola = findViewById(R.id.btn_calcola);
        outputWebView = findViewById(R.id.output_webview);
        spinnerMetodo = findViewById(R.id.spinner_metodo);

        // Pre-inserimento di "T(n) = " e posizionamento del cursore
        inputEquation.setText("T(n) = ");
        inputEquation.setSelection(inputEquation.getText().length());

        // Abilita il supporto JavaScript per la WebView (necessario per MathJax)
        WebSettings webSettings = outputWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Listener per lo Spinner
        spinnerMetodo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                metodoSelezionato = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Niente da fare
            }
        });

        // Listener per il pulsante Calcola
        btnCalcola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcolaRicorrenza();
            }
        });
    }

    // Metodo per calcolare la ricorrenza in base al metodo selezionato
    private void calcolaRicorrenza() {
        // Ottieni l'input dall'utente
        String userInput = inputEquation.getText().toString();

        try {
            // Parsare l'input
            RecurrenceParser.Recurrence recurrence = RecurrenceParser.parseRecurrence(userInput);
            String result;

            if (metodoSelezionato.equals("Espansione Iterativa")) {
                int n = 16; // Puoi rendere 'n' un input dinamico se necessario
                result = IterativeRecurrenceSolver.solveRecurrenceIteratively(recurrence, n);
            } else if (metodoSelezionato.equals("Teorema di Master")) {
                result = IterativeRecurrenceSolver.solveRecurrenceWithMasterTheorem(recurrence);
            } else if (metodoSelezionato.equals("Metodo di Sostituzione")) {
                result = IterativeRecurrenceSolver.solveRecurrenceWithSubstitution(recurrence);
            } else {
                throw new IllegalArgumentException("Metodo di risoluzione non valido.");
            }

            // Formattazione del risultato per la WebView con HTML e MathJax
            String htmlContent = formatResultForWebView(result);
            outputWebView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);

        } catch (IllegalArgumentException e) {
            // Gestisci input non valido
            String errorHtml = "<html><body><p style=\"color:red;\">Errore: " + e.getMessage() + "</p></body></html>";
            outputWebView.loadDataWithBaseURL(null, errorHtml, "text/html", "UTF-8", null);
        }
    }

    // Metodo per formattare il risultato con HTML e MathJax
    private String formatResultForWebView(String result) {
        return "<html>" +
                "<head>" +
                "<script type=\"text/javascript\" async " +
                " src=\"https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.7/MathJax.js?config=TeX-MML-AM_CHTML\">" +
                "</script>" +
                "<style>" +
                "body { font-family: 'Roboto', sans-serif; padding: 10px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                result + // Il risultato è già formattato in HTML con MathJax
                "</body>" +
                "</html>";
    }
}
