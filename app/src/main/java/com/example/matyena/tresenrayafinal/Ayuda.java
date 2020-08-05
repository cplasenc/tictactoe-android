package com.example.matyena.tresenrayafinal;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class Ayuda extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);

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
