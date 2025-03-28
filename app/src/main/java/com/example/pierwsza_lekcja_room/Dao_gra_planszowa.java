package com.example.pierwsza_lekcja_room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Dao_gra_planszowa {
    @Insert
    public void wstawgre(Gra_planszowa gra);

    @Delete
    public void usungre(Gra_planszowa gra);

    @Update
    public void zaktualizujgre(Gra_planszowa gra);

    @Query("SELECT * FROM gra_planszowa")
    public List<Gra_planszowa> zwrocgry();

    @Query("SELECT * FROM gra_planszowa WHERE ilosc_graczy>= :liczbaprzystole")
    public List<Gra_planszowa> zwrocGryDlaLiczbyGraczy(int liczbaprzystole);

}
