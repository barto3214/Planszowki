package com.example.pierwsza_lekcja_room;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    Database_planszowki database_planszowki;
    /*Kroki:
    *1.zrobić klase
    *2.zmienić ją na encję
    *3.Zdefiniować dao (interface)(polecenia w tabeli)
    *4.zrobić nową klase, powiedziec że jest database i extends Roomdatabase
    *5.w main activity zrozumieć co znaczy room database calback
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RoomDatabase.Callback callback = new RoomDatabase.Callback(){
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }

            @Override
            public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
                super.onDestructiveMigration(db);
            }
        };
        database_planszowki = Room.databaseBuilder(
            MainActivity.this,
            Database_planszowki.class,
            "Planszowki_DB").addCallback(callback).allowMainThreadQueries().build();

        dodajgrydobazy();
    }   //zaczynamy prace w wątku niegłównym
    private void dodajgrydobazy(){ // podobno to jeden z wielu tutoriali w internecie więc sobie poszukaj
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        database_planszowki.zwroc_Dao_gra().wstawgre(new Gra_planszowa("Splender",5,7,1));
                        database_planszowki.zwroc_Dao_gra().wstawgre(new Gra_planszowa("D&D",5,300,3));
                        database_planszowki.zwroc_Dao_gra().wstawgre(new Gra_planszowa("Frostpunk",5,35,3));
                        database_planszowki.zwroc_Dao_gra().wstawgre(new Gra_planszowa("Pokemony",2,60,4));

                        handler.post(new Runnable() {
                                         @Override
                                         public void run() {
                                             Toast.makeText(MainActivity.this, "Dodano do bazy", Toast.LENGTH_SHORT);
                                         }
                                     }
                        );
                    }
                }
        );
    }
}