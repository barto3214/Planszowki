package com.example.pierwsza_lekcja_room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {Gra_planszowa.class}, version = 1,exportSchema = false)
public abstract class Database_planszowki extends RoomDatabase {
    public abstract Dao_gra_planszowa zwroc_Dao_gra();
}
