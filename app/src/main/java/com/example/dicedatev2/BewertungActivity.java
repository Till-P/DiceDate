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

        Event uebergebenesEvent = (Event) getIntent().getSerializableExtra("event");

        TextView textBewertung = findViewById(R.id.textView8);
        textBewertung.setText(String.format("Bisherige Bewertung ist %.1f (%.0f Stimmen)", uebergebenesEvent.getBewertung(),uebergebenesEvent.getAnzahlBewertungen())); //+ " nach " + String.format("%.0f", uebergebenesEvent.getAnzahlBewertungen()) + " Votes");

        TextView datumText = findViewById(R.id.textView9);
        datumText.setText("Wie fandest du den Spieleabend am " + uebergebenesEvent.getDatum() + "?");

    }


    public void speichernBewertung(View v){
        Event uebergebenesEvent = (Event) getIntent().getSerializableExtra("event");

        Float neueBewertung = ((RatingBar) findViewById(R.id.ratingBar)).getRating();

        uebergebenesEvent.setBewertung( (uebergebenesEvent.getBewertung()*uebergebenesEvent.getAnzahlBewertungen() + neueBewertung) / (uebergebenesEvent.getAnzahlBewertungen()+1) );
        uebergebenesEvent.setAnzahlBewertungen(uebergebenesEvent.getAnzahlBewertungen()+1);


        Toast.makeText(this, uebergebenesEvent.getAnzahlBewertungen().toString(), 1).show();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("event", uebergebenesEvent);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void abbrechen(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("event", (Event) getIntent().getSerializableExtra("event"));
        setResult(RESULT_CANCELED, intent);
        finish();
    }

}