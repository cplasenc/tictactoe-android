package com.example.matyena.tresenrayafinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class Multijugador extends AppCompatActivity{

    int turno = 1;
    int win = 0;
    int gameover = 0;
    int marcaFinJuego =0;
    int marca;
    String mostrarTurno;
    GridLayout grid;
    Button tablero[][] = new Button[3][3];
    TextView turnoJugador;
    int contador = 0;
    int ganaJ1 = 0, ganaJ2 = 0, empate = 0;
    int cambiaValor =0;
    AlertDialog.Builder builder;

    int partidas;
    String partidasText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablero);
        turnoJugador = findViewById(R.id.jugador);
        builder = new AlertDialog.Builder(this);
        grid = findViewById(R.id.grid);
        mostrarTurno ="Jugador 1 (X)";
        turnoJugador.setText(mostrarTurno);

        Intent intent2 = getIntent();

        partidasText = intent2.getExtras().getString("Partidas");
        Log.d("PARTIDAS", partidasText);
        try{
            partidas = Integer.parseInt(partidasText);
        }catch (NumberFormatException nfe){

        }

        //flecha atrás
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = (Button) grid.getChildAt(3 * i + j);
            }
        }
    }

    public void jugar(View view) {
        int index = grid.indexOfChild(view);
        int i = index / 3;
        int j = index % 3;
        marca = 0;
        if (turno == 1 && gameover == 0 && !(tablero[i][j].getText().toString().equals("X")) && !(tablero[i][j].getText().toString().equals("O"))) {


            if(cambiaValor ==0){
                mostrarTurno ="Jugador 2 (O)";
                turnoJugador.setText(mostrarTurno);
            }
            else if(cambiaValor ==1){
                mostrarTurno ="Jugador 1 (O)";
                turnoJugador.setText(mostrarTurno);
            }
            tablero[i][j].setText("X");
            turno = 2;

        } else if (turno == 2 && gameover == 0 && !(tablero[i][j].getText().toString().equals("X")) && !(tablero[i][j].getText().toString().equals("O"))) {
            if(cambiaValor ==0){
                mostrarTurno ="Jugador 1 (X)";
                turnoJugador.setText(mostrarTurno);
            }
            else if(cambiaValor ==1){
                mostrarTurno ="Jugador 2 (X)";
                turnoJugador.setText(mostrarTurno);
            }
            tablero[i][j].setText("O");
            turno = 1;

        }
        comprobarGanador();
        if (gameover == 1) {
            if (win == 1) {
                builder.setMessage("Gana Jugador 1").setTitle(":D");
                if(marcaFinJuego ==0){
                    ganaJ1++;
                    contador++;
                }

            } else if (win == 2) {
                builder.setMessage("Gana Jugador 2").setTitle(":D");
                if(marcaFinJuego ==0){
                    ganaJ2++;
                    contador++;
                }
            }
            marcaFinJuego =1;
            builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id){
                    seguirJugando(new View(getApplicationContext()));
                    if (contador == partidas) {
                        Intent intent = new Intent(getApplicationContext(), GuardarDatos.class);
                        intent.putExtra("Gana Jugador 1", ganaJ1);
                        intent.putExtra("Gana Jugador 2", ganaJ2);
                        intent.putExtra("Partidas Jugadas", partidas);
                        intent.putExtra("Empates", empate);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                            finish();
                        }

                    }
                }

            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        if (gameover == 0) {
            for (i = 0; i < 3; i++) {
                for (j = 0; j < 3; j++) {
                    if (!tablero[i][j].getText().toString().equals("X") && !tablero[i][j].getText().toString().equals("O")) {
                        marca = 1;
                        break;

                    }
                }
            }
            if (marca == 0) {
                builder.setMessage("Empate").setTitle(":|");
                if(marcaFinJuego ==0){
                    contador++;
                    empate++;
                }
                marcaFinJuego =1;
                builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        seguirJugando(new View(getApplicationContext()));
                        if (contador == partidas) {
                            Intent intent = new Intent(getApplicationContext(), GuardarDatos.class);
                            intent.putExtra("Gana Jugador 1", ganaJ1);
                            intent.putExtra("Gana Jugador 2", ganaJ2);
                            intent.putExtra("Partidas Jugadas", partidas);
                            intent.putExtra("Empates", empate);
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivity(intent);
                                finish();
                            }

                        }
                    }

                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        }
    }

    public void seguirJugando(View view) {

        win = 0;
        gameover = 0;
        turno =1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j].setText(" ");
                tablero[i][j].setTextColor(Color.WHITE);
            }
        }

        if(cambiaValor ==0){
            if(marcaFinJuego ==1){
                cambiaValor =1;
                mostrarTurno ="Jugador 2 (X)";
                turnoJugador.setText(mostrarTurno);
            }
            else{
                mostrarTurno ="Jugador 1 (X)";
                turnoJugador.setText(mostrarTurno);
            }


        }
        else if(cambiaValor ==1 ){
            if(marcaFinJuego ==1){
                cambiaValor =0;
                mostrarTurno ="Jugador 1 (X)";
                turnoJugador.setText(mostrarTurno);
            }
            else{
                mostrarTurno ="Jugador 2 (X)";
                turnoJugador.setText(mostrarTurno);
            }
        }
        marcaFinJuego =0;
    }

    public void comprobarGanador() {
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0].getText().toString().equals(tablero[i][1].getText().toString()) && tablero[i][0].getText().toString().equals(tablero[i][2].getText().toString())) {
                if (tablero[i][0].getText().toString().equals("X")) {
                    gameover = 1;
                    if(cambiaValor ==0)
                        win = 1;
                    else if(cambiaValor ==1)
                        win=2;


                } else if (tablero[i][0].getText().toString().equals("O")) {
                    gameover = 1;
                    if(cambiaValor ==0)
                        win = 2;
                    else if(cambiaValor ==1)
                        win=1;
                }
            }
            if (tablero[0][i].getText().toString().equals(tablero[1][i].getText().toString()) && tablero[0][i].getText().toString().equals(tablero[2][i].getText().toString())) {
                if (tablero[0][i].getText().toString().equals("X")) {
                    gameover = 1;
                    if(cambiaValor ==0)
                        win = 1;
                    else if(cambiaValor ==1)
                        win=2;


                } else if (tablero[0][i].getText().toString().equals("O")) {
                    gameover = 1;
                    if(cambiaValor ==0)
                        win = 2;
                    else if(cambiaValor ==1)
                        win=1;

                }


            }


        }
        if (tablero[0][0].getText().toString().equals(tablero[1][1].getText().toString()) && tablero[0][0].getText().toString().equals(tablero[2][2].getText().toString())) {
            if (tablero[0][0].getText().toString().equals("X")) {
                gameover = 1;
                if(cambiaValor ==0)
                    win = 1;
                else if(cambiaValor ==1)
                    win=2;


            } else if (tablero[0][0].getText().toString().equals("O")) {
                gameover = 1;
                if(cambiaValor ==0)
                    win = 2;
                else if(cambiaValor ==1)
                    win=1;

            }



        }
        if (tablero[0][2].getText().toString().equals(tablero[1][1].getText().toString()) && tablero[0][2].getText().toString().equals(tablero[2][0].getText().toString())) {
            if (tablero[0][2].getText().toString().equals("X")) {
                gameover = 1;
                if(cambiaValor ==0)
                    win = 1;
                else if(cambiaValor ==1)
                    win=2;


            } else if (tablero[0][2].getText().toString().equals("O")) {
                gameover = 1;
                if(cambiaValor ==0)
                    win = 2;
                else if(cambiaValor ==1)
                    win=1;

            }



        }
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

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();

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

