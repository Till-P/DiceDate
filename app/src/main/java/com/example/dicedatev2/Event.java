package com.example.dicedatev2;

import android.widget.Toast;

import java.util.Random;

import java.io.Serializable;
import java.util.ArrayList;

//Event Klasse mit allen notwendigen Attributen (mit setter und getter)
public class Event implements Serializable {

    private Float bewertung;
    private Float anzahlBewertungen;
    private Float bewertung_essen;
    private Float anzahlBewertungen_essen;
    private Float bewertung_spiele;
    private Float anzahlBewertungen_spiele;

    private String datum;
    private String host;
    private String essen;
    private ArrayList<String> teilnehmende;
    private ArrayList<String> spiele;

    public Event(String date, String host, String food) {
        this.bewertung = 0.0F;
        this.anzahlBewertungen = 0.0F;
        this.bewertung_essen = 0.0F;
        this.anzahlBewertungen_essen = 0.0F;
        this.bewertung_spiele = 0.0F;
        this.anzahlBewertungen_spiele = 0.0F;
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
    public Float getBewertung() {return bewertung;}
    public void setBewertung(Float bewertung) {this.bewertung = bewertung;}
    public Float getAnzahlBewertungen() {return anzahlBewertungen;}
    public void setAnzahlBewertungen(Float bewertung) {this.anzahlBewertungen = bewertung;}
    public Float getBewertung_essen() {return bewertung_essen;}
    public void setBewertung_essen(Float bewertung) {this.bewertung_essen = bewertung;}
    public Float getAnzahlBewertungen_essen() {return anzahlBewertungen_essen;}
    public void setAnzahlBewertungen_essen(Float bewertung) {this.anzahlBewertungen_essen = bewertung;}
    public Float getBewertung_spiele() {return bewertung_spiele;}
    public void setBewertung_spiele(Float bewertung) {this.bewertung_spiele = bewertung;}
    public Float getAnzahlBewertungen_spiele() {return anzahlBewertungen_spiele;}
    public void setAnzahlBewertungen_spiele(Float bewertung) {this.anzahlBewertungen_spiele = bewertung;}

}
