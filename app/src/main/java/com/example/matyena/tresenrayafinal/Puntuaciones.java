package com.example.matyena.tresenrayafinal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.matyena.tresenrayafinal.entidades.Jugador;

import java.util.ArrayList;

public class Puntuaciones extends AppCompatActivity{

    ListView listView;
    ArrayList<Jugador> listaInformacion;
    Adaptador adaptador = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuaciones);

        listView = findViewById(R.id.listView);
        listaInformacion = new ArrayList<>();
        adaptador = new Adaptador(this, R.layout.item, listaInformacion);
        listView.setAdapter(adaptador);

        ConexionSQLiteHelper sqLiteHelper = new ConexionSQLiteHelper(getApplicationContext(), "bdjugadores", null, 1);

        Cursor cursor = sqLiteHelper.getData("SELECT * FROM jugadores2");
        listaInformacion.clear();

        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String jugador = cursor.getString(1);
            String partidas = cursor.getString(2);
            String victorias = cursor.getString(3);
            String empates = cursor.getString(4);
            byte[] imagen = cursor.getBlob(5);

            listaInformacion.add(new Jugador(id, jugador, partidas, victorias, empates, imagen));
        }
        adaptador.notifyDataSetChanged();

        //flecha atrás
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onPause() {
        super.onPause();
        //if (!shouldPlay) {
        MainActivity.mp.pause();
        MainActivity.length=MainActivity.mp.getCurrentPosition();
        //mp = null;
        //}
    }

    @Override
    protected void onResume() {
        //if(mp != null && !mp.isPlaying())
        //  mp.start();
        super.onResume();
        //mp.seekTo(length);
        MainActivity.mp.start();
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu mimenu) {
        getMenuInflater().inflate(R.menu.menu_main, mimenu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem opcion_menu){
        int id = opcion_menu.getItemId();

        if (id == android.R.id.home){
            this.finish();
        }

        if(id == R.id.play){
            MainActivity.mp.seekTo(MainActivity.length);
            MainActivity.mp.start();
        }

        if(id == R.id.pause){
            MainActivity.mp.pause();
            MainActivity.length=MainActivity.mp.getCurrentPosition();
        }
        return super.onOptionsItemSelected(opcion_menu);
    }
}
