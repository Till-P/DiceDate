package com.example.dicedatev2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BewertungActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bewertung);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Event was aus MainActivity übergeben wurde in Variable speichern
        Event uebergebenesEvent = (Event) getIntent().getSerializableExtra("event");

        //Anpassen der Textfelder anhand der Daten des Events
        TextView textBewertung = findViewById(R.id.textView8);
        textBewertung.setText(String.format("Bisherige Bewertung ist %.1f (%.0f Stimmen)", uebergebenesEvent.getBewertung(),uebergebenesEvent.getAnzahlBewertungen()));
        TextView textBewertung_essen = findViewById(R.id.textView11);
        textBewertung_essen.setText(String.format("Bisherige Bewertung ist %.1f (%.0f Stimmen)", uebergebenesEvent.getBewertung_essen(),uebergebenesEvent.getAnzahlBewertungen_essen()));
        TextView textBewertung_spiele = findViewById(R.id.textView13);
        textBewertung_spiele.setText(String.format("Bisherige Bewertung ist %.1f (%.0f Stimmen)", uebergebenesEvent.getBewertung_spiele(),uebergebenesEvent.getAnzahlBewertungen_spiele()));

        TextView datumText = findViewById(R.id.textView9);
        datumText.setText("Wie fandest du den Spieleabend am " + uebergebenesEvent.getDatum() + "?");
        TextView essenText = findViewById(R.id.textView10);
        essenText.setText(String.format("Wie gut hat das Essen (%s) geschmeckt?",uebergebenesEvent.getEssen()));
    }


    public void speichernBewertung(View v){ //Funktion zum Anpassen und Speichern des Event Objekts auf Basis der Bewertung
        Event uebergebenesEvent = (Event) getIntent().getSerializableExtra("event");

        //Neue Bewertungen aus RatingBars ablesen
        Float neueBewertung = ((RatingBar) findViewById(R.id.ratingBar)).getRating();
        Float neueBewertung_essen = ((RatingBar) findViewById(R.id.ratingBar2)).getRating();
        Float neueBewertung_spiele = ((RatingBar) findViewById(R.id.ratingBar3)).getRating();

        //Durschnittliche Bewertungen und Anzahl Bewertungen für alle 3 Kategorien aktualisieren
        uebergebenesEvent.setBewertung( (uebergebenesEvent.getBewertung()*uebergebenesEvent.getAnzahlBewertungen() + neueBewertung) / (uebergebenesEvent.getAnzahlBewertungen()+1) );
        uebergebenesEvent.setAnzahlBewertungen(uebergebenesEvent.getAnzahlBewertungen()+1);
        uebergebenesEvent.setBewertung_essen( (uebergebenesEvent.getBewertung_essen()*uebergebenesEvent.getAnzahlBewertungen_essen() + neueBewertung_essen) / (uebergebenesEvent.getAnzahlBewertungen_essen()+1) );
        uebergebenesEvent.setAnzahlBewertungen_essen(uebergebenesEvent.getAnzahlBewertungen_essen()+1);
        uebergebenesEvent.setBewertung_spiele( (uebergebenesEvent.getBewertung_spiele()*uebergebenesEvent.getAnzahlBewertungen_spiele() + neueBewertung_spiele) / (uebergebenesEvent.getAnzahlBewertungen_spiele()+1) );
        uebergebenesEvent.setAnzahlBewertungen_spiele(uebergebenesEvent.getAnzahlBewertungen_spiele()+1);

        //Event an MainActivity zurück geben
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("event", uebergebenesEvent);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void abbrechen(View v){ //Falls nicht gespeichert werden soll wird das ursprüngliche Event-Objekt zurück gegeben
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("event", (Event) getIntent().getSerializableExtra("event"));
        setResult(RESULT_CANCELED, intent);
        finish();
    }

}