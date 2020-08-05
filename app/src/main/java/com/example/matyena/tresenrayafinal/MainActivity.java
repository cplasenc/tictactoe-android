package com.example.matyena.tresenrayafinal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static boolean shouldPlay = false;

    Button bJugar, bPuntuaciones, bAyuda;
    public static MediaPlayer mp;
    int pausa;
    static int length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bJugar = findViewById(R.id.bJugar);
        bPuntuaciones = findViewById(R.id.bPuntuaciones);
        bAyuda = findViewById(R.id.bAyuda);

        bJugar.setOnClickListener(this);
        bAyuda.setOnClickListener(this);
        bPuntuaciones.setOnClickListener(this);

        shouldPlay = false;

        mp = MediaPlayer.create(this, R.raw.musica);
        mp.start();
        //shouldPlay = false;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bJugar:

                Intent intent = new Intent(this, Partidas.class);
                startActivity(intent);
                break;

            case R.id.bAyuda:
                Intent intent2 = new Intent(this, Ayuda.class);
                    startActivity(intent2);
                break;

            case R.id.bPuntuaciones:
                Intent intent3 = new Intent(this, Puntuaciones.class);
                    startActivity(intent3);
                break;
        }
    }

    /*@Override
    protected void onStop() {
        super.onStop();
        if (!shouldPlay) { // it won't pause music if shouldPlay is true
            mp.pause();
            mp = null;
        }

    }*/


    @Override
    protected void onPause() {
        super.onPause();
        //if (!shouldPlay) {
            mp.pause();
            length=mp.getCurrentPosition();
            //mp = null;
        //}
    }

    @Override
    protected void onResume() {
        //if(mp != null && !mp.isPlaying())
          //  mp.start();
        super.onResume();
        //mp.seekTo(length);
        mp.start();
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

        if(id == R.id.play){
            mp.seekTo(length);
            mp.start();

        }

        if(id == R.id.pause){
            mp.pause();
            length=mp.getCurrentPosition();

        }
        return super.onOptionsItemSelected(opcion_menu);
    }


}
