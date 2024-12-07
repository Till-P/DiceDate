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

    public LinearLayout eventListLayout;
    public LinearLayout pastEventLinearLayout;

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

        eventListLayout = findViewById(R.id.eventListLayout);
        pastEventLinearLayout = findViewById(R.id.pastEventLinearLayout);

        addEventUpcoming(new Event("11.12.2024", "Anna", "Apfel"));
        addEventUpcoming(new Event("01.01.2025", "Birte", "Brot"));
        addEventPast(new Event("01.03.2024", "Caro", "Cheddar"));
    }


    public void addEventUpcoming(Event event) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View eventView = inflater.inflate(R.layout.event_item, eventListLayout, false);

        TextView eventDateTextView = eventView.findViewById(R.id.eventDateTextView);
        TextView eventHostTextView = eventView.findViewById(R.id.eventHostTextView);
        TextView eventFoodTextView = eventView.findViewById(R.id.eventFoodTextView);

        eventDateTextView.setText("Datum: " + event.getDatum());
        eventHostTextView.setText("Gastgeber: " + event.getHost());
        eventFoodTextView.setText("Essen: " + event.getEssen());

        eventView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEventDetailsDialog(event, v);
            }
        });
        eventListLayout.addView(eventView);
    }

    public void addEventPast(Event event) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View eventView = inflater.inflate(R.layout.event_item, eventListLayout, false);

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


    public void addNew(View v) {

        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("event", new Event("","",""));
        startActivityForResult(intent, 1);

    }

    public void showEventDetailsDialog(Event event, View v) {
        // Erstelle einen Dialog-Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Spieleabend am "+event.getDatum());

        String message = "\nDatum: " + event.getDatum() +
                "\n\nGastgeber: " + event.getHost() +
                "\n\nEssen: " + event.getEssen() +
                "\n\nTeilnehmende: " + event.getTeilnehmende() +
                "\n\nSpiele: " + event.getSpiele();

        builder.setMessage(message);

        builder.setPositiveButton("Bearbeiten", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("event", event);
                startActivityForResult(intent, 1);
                eventListLayout.removeView(v);
            }
        });
        builder.create().show();
    }



    public void rateEventDialog(Event event, View v) {
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

   //     if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Event bearbeitetesEvent = (Event) data.getSerializableExtra("event");
                addEventUpcoming(bearbeitetesEvent);
            }
            if (requestCode == 2) {
                Event bearbeitetesEvent = (Event) data.getSerializableExtra("event");
                addEventPast(bearbeitetesEvent);
            }
  //      }

    }


    public void smsVerschicken(View v){
        String hostNumber = "+4915253488947";
        String verspaetung = "15";

        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.setData(Uri.parse("smsto:"+hostNumber));
        smsIntent.putExtra("sms_body", "Ich komme leider " +verspaetung+ " Minuten zu spät zum nächsten Spieleabend.");


        startActivity(Intent.createChooser(smsIntent, "Send sms via:"));

    }


}