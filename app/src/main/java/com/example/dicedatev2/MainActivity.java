package com.example.dicedatev2;

import static androidx.core.content.IntentCompat.getSerializableExtra;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public LinearLayout eventListLayout; //Variable zur Darstellung der Events die noch kommen
    public LinearLayout pastEventLinearLayout; //Variable zur Darstellung der Events die vergangen sind

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Zuweisungen zum entsprechenden View-Element
        eventListLayout = findViewById(R.id.eventListLayout);
        pastEventLinearLayout = findViewById(R.id.pastEventLinearLayout);

        // Events mit denen das Programm startet (zum Ausprobieren)
        addEventUpcoming(new Event("11.12.2024", "Anna", "Apfel"));
        addEventUpcoming(new Event("01.01.2025", "Birte", "Brot"));
        addEventPast(new Event("01.03.2024", "Caro", "Cheddar"));
    }


    public void addEventUpcoming(Event event) { //Zum Hinzufügen eines neuen zukünftigen Events auf der Benutzer-Oberfläche
        //Hier wird das Layout aus "event_item" für ein neues Element geladen und dann in der Variable "eventView" gespeichert
        LayoutInflater inflater = LayoutInflater.from(this);
        View eventView = inflater.inflate(R.layout.event_item, eventListLayout, false);

        //Speichern der View-Elemente in Variablen
        TextView eventDateTextView = eventView.findViewById(R.id.eventDateTextView);
        TextView eventHostTextView = eventView.findViewById(R.id.eventHostTextView);
        TextView eventFoodTextView = eventView.findViewById(R.id.eventFoodTextView);

        //Befüllen der Elemente
        eventDateTextView.setText("Datum: " + event.getDatum());
        eventHostTextView.setText("Gastgeber: " + event.getHost());
        eventFoodTextView.setText("Essen: " + event.getEssen());

        //Dialog starten, wenn auf Eintrag geklickt wird
        eventView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEventDetailsDialog(event, v);
            }
        });

        //Den fertig befüllten und klickbaren "eventView" der Liste der zukünftigen Events hinzufügen
        eventListLayout.addView(eventView);
    }


    public void addEventPast(Event event) { //Zum Hinzufügen eines neuen vergangenen Events auf der Benutzer-Oberfläche
        //Funktioniert genau wie die obige Funktion, deshalb nicht noch mal erläutert
        LayoutInflater inflater = LayoutInflater.from(this);
        View eventView = inflater.inflate(R.layout.event_item, pastEventLinearLayout, false);

        TextView eventDateTextView = eventView.findViewById(R.id.eventDateTextView);
        TextView eventHostTextView = eventView.findViewById(R.id.eventHostTextView);
        TextView eventFoodTextView = eventView.findViewById(R.id.eventFoodTextView);

        eventView.setBackground( getResources().getDrawable(R.drawable.past_event_item_background));

        eventDateTextView.setText("Datum: " + event.getDatum());
        eventHostTextView.setText("Gastgeber: " + event.getHost());
        eventFoodTextView.setText("Essen: " + event.getEssen());

        eventView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateEventDialog(event, v);
            }
        });
        pastEventLinearLayout.addView(eventView);
    }


    public void addNew(View v) { //Wechsel in die "DetailActivity" mit einem neuen leeren Event-Objekt

        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("event", new Event("","",""));
        startActivityForResult(intent, 1);

    }

    public void showEventDetailsDialog(Event event, View v) { //Öffnen des Fensters beim Klicken auf ein Event, um Details anzuzeigen und bearbeiten zu ermöglichen
        // Erstelle einen Dialog-Builder   -   Code für Dialog-Builder hab ich aus dem Internet kopiert
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Spieleabend am "+event.getDatum());

        //Angezeigte Nachricht mit Event-Details
        String message = "\nDatum: " + event.getDatum() +
                "\n\nGastgeber: " + event.getHost() +
                "\n\nEssen: " + event.getEssen() +
                "\n\nTeilnehmende: " + event.getTeilnehmende() +
                "\n\nSpiele: " + event.getSpiele();
        builder.setMessage(message);

        //"Bearbeiten" Button, der zur "DetailActivity" wechselt
        builder.setPositiveButton("Bearbeiten", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Wechsel in die "DetailActivity"...
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                //...mit dem an die Funktion übergebenen Event...
                intent.putExtra("event", event);
                startActivityForResult(intent, 1);
                //...und anschließend Löschen des bisherigen Eintrags, da später das Event wieder neu hinzugefügt wird.
                eventListLayout.removeView(v);

// ANMERKUNG: Die Variante mit erst Löschen und dann wieder Hinzufügen ist ein work-around. Ein direktes Bearbeiten wäre sehr viel sinnvoller.
//            Ich hoffe das können wir besser Lösen, wenn wir persistente Daten außerhalb der Laufzeit speichern. Dann können wir idealerweise eine
//            Liste von Events anlegen, die durch eine Funktion in der Benutzeroberfläche sichtbar gemacht wird.

            }
        });
        builder.create().show();
    }



    public void rateEventDialog(Event event, View v) {//Öffnen des Fensters beim Klicken auf ein Event, um Bewertungen anzuzeigen und bewerten zu ermöglichen
        //Funktioniert genau wie die obige Funktion, deshalb nicht noch mal erläutert

        // Erstelle einen Dialog-Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Spieleabend am "+event.getDatum());

        String message = "\nBewertung: " + String.format("%.1f", event.getBewertung()) +" von 5" +
                "\n\nAnzahl Bewertungen: " + String.format("%.0f", event.getAnzahlBewertungen());

        builder.setMessage(message);

        builder.setPositiveButton("Bewerten", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, BewertungActivity.class);
                intent.putExtra("event", event);
                startActivityForResult(intent, 2);
                pastEventLinearLayout.removeView(v);
            }
        });
        builder.create().show();
    }


    @Override //Funktion die bestimmt, was beim zurückkehren in die MainActivity passieren soll, nachdem bearbeitet oder bewertet wurde.
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

   //     if (resultCode == RESULT_OK) {

        // Wenn aus Detail in Main gewechselt wird (Code 1)
            if (requestCode == 1) {
                //Füge das bearbeitete Element der Oberfläche hinzu
                Event bearbeitetesEvent = (Event) data.getSerializableExtra("event");
                addEventUpcoming(bearbeitetesEvent);
            }
        // Wenn aus Bewertung in Main gewechselt wird (Code 2)
            if (requestCode == 2) {
                //Füge das bewertete Element der Oberfläche hinzu
                Event bewertetesEvent = (Event) data.getSerializableExtra("event");
                addEventPast(bewertetesEvent);
            }
  //      }

    }


    public void smsVerschicken(View v){ //Funktion die einen Intent startet, der SMS-App öffnet mit fertigem Text
        String hostNumber = "+4915253488947";
        String verspaetung = "15";

        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.setData(Uri.parse("smsto:"+hostNumber));
        smsIntent.putExtra("sms_body", "Ich komme leider " +verspaetung+ " Minuten zu spät zum nächsten Spieleabend.");


        startActivity(Intent.createChooser(smsIntent, "Send sms via:"));

    }


}