package com.example.dicedatev2;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Event das in Main angeklickt (und durch Intent übergeben) wurde wird in Variable gespeichert
        Event uebergebenesEvent = (Event) getIntent().getSerializableExtra("event");

        //Views aus der Oberfläche werden in Variablen gespeichert
        TextView datumEditText = findViewById(R.id.datumEditText);
        TextView hostEdit = findViewById(R.id.hostEdit);
        TextView essenEdit = findViewById(R.id.essenEdit);
        TextView teilnehmendeEdit = findViewById(R.id.teilnehmendeEdit);
        TextView spieleEdit = findViewById(R.id.spieleEdit);

        //Daten aus dem Event-Objekt werden in die entsprechenden Felder der Benutzeroberfläche eingefügt
        datumEditText.setText(uebergebenesEvent.getDatum());
        hostEdit.setText(uebergebenesEvent.getHost());
        essenEdit.setText(uebergebenesEvent.getEssen());

        //Schleifen für die Attribute, die mehrere Einträge haben können
        for (int i = 0; i<(uebergebenesEvent.getTeilnehmende().size()); i++){
            teilnehmendeEdit.append("\n"+uebergebenesEvent.getTeilnehmende().get(i));
        }
        for (int i = 0; i<(uebergebenesEvent.getSpiele().size()); i++){
            spieleEdit.append("\n"+uebergebenesEvent.getSpiele().get(i));
        }

    }

    public void speichern(View v){ // Funktion zum zurückgeben der geänderten Daten an MainActivity
        //Speichern der TextViews aus der Benutzeroberfläche in Variablen
        TextView datumEditText = findViewById(R.id.datumEditText);
        TextView hostEdit = findViewById(R.id.hostEdit);
        TextView essenEdit = findViewById(R.id.essenEdit);

        //Auslesen der mehrzeiligen Textfelder und speichern der einzelnen Elemente in ArrayList
        TextView teilnehmendeEdit = findViewById(R.id.teilnehmendeEdit);
        ArrayList<String> teilnehmendeListe = new ArrayList<>();
        for (int i = 0; i<(teilnehmendeEdit.getText().toString().split("\n").length); i++){
            teilnehmendeListe.add(teilnehmendeEdit.getText().toString().split("\n")[i]);
        }

        //Auslesen der mehrzeiligen Textfelder und speichern der einzelnen Elemente in ArrayList
        TextView spieleEdit = findViewById(R.id.spieleEdit);
        ArrayList<String> spieleListe = new ArrayList<>();
        for (int i = 0; i<(spieleEdit.getText().toString().split("\n").length); i++){
            spieleListe.add(spieleEdit.getText().toString().split("\n")[i]);
        }

        //Überprüfen, ob Felder leer sind, wenn ja Errornachricht in Textfeld
        if (!TextUtils.isEmpty(datumEditText.getText().toString())) {
            if (!TextUtils.isEmpty(hostEdit.getText().toString())) {
                if (!TextUtils.isEmpty(essenEdit.getText().toString())) {

                    //Wenn alle nötigen Felder nicht leer: Daten des Events werden anhand der Eingaben aktualisiert
                    Event uebergebenesEvent = (Event) getIntent().getSerializableExtra("event");

                    uebergebenesEvent.setHost(hostEdit.getText().toString());
                    uebergebenesEvent.setDatum(datumEditText.getText().toString());
                    uebergebenesEvent.setEssen(essenEdit.getText().toString());
                    uebergebenesEvent.setTeilnehmende(teilnehmendeListe);
                    uebergebenesEvent.setSpiele(spieleListe);

                    //Aktualisiertes Event-Objekt wird an MainActivity zurück gegeben
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("event", uebergebenesEvent);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else {essenEdit.setError("Essen fehlt!");}
            }
            else {hostEdit.setError("Host fehlt!");}
        }
        else {datumEditText.setError("Datum fehlt!");}

    }

    public void abbrechen(View v){ //Falls nicht gespeichert werden soll wird das ursprüngliche Event-Objekt zurück gegeben
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("event", (Event) getIntent().getSerializableExtra("event"));
        setResult(RESULT_CANCELED, intent);
        finish();
    }



}