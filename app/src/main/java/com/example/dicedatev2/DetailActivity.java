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

//    private Event uebergebenesEvent = (Event) getIntent().getSerializableExtra("event");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Event uebergebenesEvent = (Event) getIntent().getSerializableExtra("event");

        TextView idText = findViewById(R.id.textId);
        TextView datumEditText = findViewById(R.id.datumEditText);
        TextView hostEdit = findViewById(R.id.hostEdit);
        TextView essenEdit = findViewById(R.id.essenEdit);
        TextView teilnehmendeEdit = findViewById(R.id.teilnehmendeEdit);
        TextView spieleEdit = findViewById(R.id.spieleEdit);

        idText.setText("Bewertung FOR DEBUG " + uebergebenesEvent.getBewertung());
        datumEditText.setText(uebergebenesEvent.getDatum());
        hostEdit.setText(uebergebenesEvent.getHost());
        essenEdit.setText(uebergebenesEvent.getEssen());

        for (int i = 0; i<(uebergebenesEvent.getTeilnehmende().size()); i++){
            teilnehmendeEdit.append("\n"+uebergebenesEvent.getTeilnehmende().get(i));
        }
        for (int i = 0; i<(uebergebenesEvent.getSpiele().size()); i++){
            spieleEdit.append("\n"+uebergebenesEvent.getSpiele().get(i));
        }

    }

    public void speichern(View v){
        TextView datumEditText = findViewById(R.id.datumEditText);
        TextView hostEdit = findViewById(R.id.hostEdit);
        TextView essenEdit = findViewById(R.id.essenEdit);

        TextView teilnehmendeEdit = findViewById(R.id.teilnehmendeEdit);
        ArrayList<String> teilnehmendeListe = new ArrayList<>();
        for (int i = 0; i<(teilnehmendeEdit.getText().toString().split("\n").length); i++){
            teilnehmendeListe.add(teilnehmendeEdit.getText().toString().split("\n")[i]);
        }

        TextView spieleEdit = findViewById(R.id.spieleEdit);
        ArrayList<String> spieleListe = new ArrayList<>();
        for (int i = 0; i<(spieleEdit.getText().toString().split("\n").length); i++){
            spieleListe.add(spieleEdit.getText().toString().split("\n")[i]);
        }


        if (!TextUtils.isEmpty(datumEditText.getText().toString())) {
            if (!TextUtils.isEmpty(hostEdit.getText().toString())) {
                if (!TextUtils.isEmpty(essenEdit.getText().toString())) {
                    Event uebergebenesEvent = (Event) getIntent().getSerializableExtra("event");

                    uebergebenesEvent.setHost(hostEdit.getText().toString());
                    uebergebenesEvent.setDatum(datumEditText.getText().toString());
                    uebergebenesEvent.setEssen(essenEdit.getText().toString());
                    uebergebenesEvent.setTeilnehmende(teilnehmendeListe);
                    uebergebenesEvent.setSpiele(spieleListe);


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
}