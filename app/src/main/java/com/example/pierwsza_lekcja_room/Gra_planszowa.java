package com.example.pierwsza_lekcja_room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "gra_planszowa")
public class Gra_planszowa {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;
    private String nazwa;
    private int ilosc_graczy;

    @ColumnInfo(name ="czas_w_minnutach")
    private int czasMinuty;
    private double trudnosc;

    @Ignore
    public Gra_planszowa() {
    }

    public Gra_planszowa(String nazwa, int ilosc_graczy, int czasMinuty, double trudnosc) {
        id = 0;
        this.nazwa = nazwa;
        this.ilosc_graczy = ilosc_graczy;
        this.czasMinuty = czasMinuty;
        this.trudnosc = trudnosc;
    }


    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getIlosc_graczy() {
        return ilosc_graczy;
    }

    public void setIlosc_graczy(int ilosc_graczy) {
        this.ilosc_graczy = ilosc_graczy;
    }

    public int getCzasMinuty() {
        return czasMinuty;
    }

    public void setCzasMinuty(int czasMinuty) {
        this.czasMinuty = czasMinuty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTrudnosc() {
        return trudnosc;
    }

    public void setTrudnosc(double trudnosc) {
        this.trudnosc = trudnosc;
    }
}
