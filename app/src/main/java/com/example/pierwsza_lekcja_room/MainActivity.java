package com.example.pierwsza_lekcja_room;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private Button dodaj;
    private EditText nazwe;
    private EditText liczba_graczy;
    private Spinner wiek;
    private ListView listView;
    private ArrayAdapter<Gra_planszowa> array_adapter;
    private List<Gra_planszowa> gryplanszowki;
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
        dodaj = findViewById(R.id.button);
        nazwe = findViewById(R.id.editTextTextPersonName2);
        liczba_graczy = findViewById(R.id.editTextTextPersonName3);
        wiek = findViewById(R.id.spinner);
        listView = findViewById(R.id.listveiw);


        RoomDatabase.Callback callback = new RoomDatabase.Callback() {   // callback to chyba działanie w tle
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

        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nazwa = nazwe.getText().toString();
                int liczbagraczy = Integer.valueOf(liczba_graczy.getText().toString());
                int wiekspinner = Integer.parseInt(wiek.getSelectedItem().toString());
                Gra_planszowa graplanszowa = new Gra_planszowa(nazwa, liczbagraczy, wiekspinner, 2.5);
                dodajgrydobazy(graplanszowa);
            }
        });
        database_planszowki = Room.databaseBuilder(
                MainActivity.this,
                Database_planszowki.class,
                "Planszowki_DB").addCallback(callback).allowMainThreadQueries().build();

        //dodajgrydobazy();
    }   //zaczynamy prace w wątku niegłównym

    private void dodajgrydobazy(Gra_planszowa boardgame) { // podobno to jeden z wielu tutoriali w internecie więc sobie poszukaj
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        ///database_planszowki.zwroc_Dao_gra().wstawgre(new Gra_planszowa("Splender",5,7,1));
                                        ///database_planszowki.zwroc_Dao_gra().wstawgre(new Gra_planszowa("D&D",5,300,3));
                                        ///database_planszowki.zwroc_Dao_gra().wstawgre(new Gra_planszowa("Frostpunk",5,35,3));
                                        ///database_planszowki.zwroc_Dao_gra().wstawgre(new Gra_planszowa("Pokemony",2,60,4));

                                        database_planszowki.zwroc_Dao_gra().wstawgre(boardgame);
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

    private void wypiszgry() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                gryplanszowki = database_planszowki.zwroc_Dao_gra().zwrocgry();

                handler.post(new Runnable() {
                                 @Override
                                 public void run() {
                                     array_adapter = new ArrayAdapter<>(
                                             MainActivity.this,
                                             android.R.layout.simple_list_item_1,
                                             gryplanszowki
                                     );
                                     listView.setAdapter(array_adapter);
                                 }
                             }
                );
            }
        });

    }

}