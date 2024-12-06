package com.example.dicedatev2;

import android.widget.Toast;

import java.util.Random;

import java.io.Serializable;
import java.util.ArrayList;

public class Event implements Serializable {

    private Float bewertung;
    private Float anzahlBewertungen;
    private String datum;
    private String host;
    private String essen;
    private ArrayList<String> teilnehmende;
    private ArrayList<String> spiele;

    public Event(String date, String host, String food) {
        this.bewertung = 0.0F;
        this.anzahlBewertungen = 0.0F;
        this.datum = date;
        this.host = host;
        this.essen = food;
        this.teilnehmende = new ArrayList<>();
        this.spiele = new ArrayList<>();
    }

    public String getDatum() {return datum;}
    public String getHost() {return host;}
    public String getEssen() {return essen;}

    public void setDatum(String datum) { this.datum = datum; }
    public void setHost(String host) { this.host = host ;}
    public void setEssen(String essen) { this.essen = essen; }

    public ArrayList<String> getSpiele() {
        return spiele;    }
    public void setSpiele(ArrayList<String> spiele) {
        this.spiele = spiele;    }
    public ArrayList<String> getTeilnehmende() {
        return teilnehmende;    }
    public void setTeilnehmende(ArrayList<String> teilnehmende) {
        this.teilnehmende = teilnehmende;    }
    public Float getBewertung() {
        return bewertung;    }
    public void setBewertung(Float bewertung) {
        this.bewertung = bewertung;    }
    public Float getAnzahlBewertungen() {
        return anzahlBewertungen;    }
    public void setAnzahlBewertungen(Float bewertung) {
        this.anzahlBewertungen = bewertung;    }

}
