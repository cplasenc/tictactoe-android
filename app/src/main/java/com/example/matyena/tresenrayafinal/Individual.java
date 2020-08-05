package com.example.matyena.tresenrayafinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class Individual extends AppCompatActivity{

    int turno = 1;
    int win = 0;
    int gamov = 0;
    int marcaFinJuego =0;
    int marca;
    String mostrarTurno;
    GridLayout grid;
    Button tablero[][] = new Button[3][3];
    int tablero2[][] = new int[3][3];
    double probTablero[][] = new double[3][3];
    TextView turnoJugador;
    int partidas = 3;
    int mueve =1;
    int contador = 0;
    int ganaJ1 = 0, ganaJ2 = 0, empate = 0;
    int cambiaValor = 0;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablero);
        turnoJugador = findViewById(R.id.jugador);
        builder = new AlertDialog.Builder(this);
        grid = findViewById(R.id.grid);
        mostrarTurno ="Jugador 1(X)";
        turnoJugador.setText(mostrarTurno);

        //flecha atrás
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = (Button) grid.getChildAt(3 * i + j);
                tablero2[i][j]=0;
            }
        }
        if(cambiaValor ==1){
            juegaMaquina();
            turno =2;
        }
    }

    public void jugar(View view) {
        int index = grid.indexOfChild(view);
        int i = index / 3;
        int j = index % 3;
        marca = 0;
        if (turno == 1 && gamov == 0 && !(tablero[i][j].getText().toString().equals("X")) && !(tablero[i][j].getText().toString().equals("O"))) {


            if(cambiaValor ==0){
                mostrarTurno ="Jugador 2 (O)";
                turnoJugador.setText(mostrarTurno);
                tablero[i][j].setText("X");
                tablero2[i][j]=1;
                turno = 2;
                mueve++;
                juegaMaquina();
                turno = 1;
                mostrarTurno ="Jugador 1(X)";
                mueve++;
            }



        } else if (turno == 2 && gamov == 0 && !(tablero[i][j].getText().toString().equals("X")) && !(tablero[i][j].getText().toString().equals("O"))) {

            if(cambiaValor ==1){
                mostrarTurno ="Jugador 2(X)";
                turnoJugador.setText(mostrarTurno);
                tablero[i][j].setText("O");
                tablero2[i][j]=1;
                turno = 1;
                mueve++;
                juegaMaquina();
                mostrarTurno ="Jugador 1(O)";
                turno = 2;
                mueve++;

            }

        }

        compruebaGanador();
        if (gamov == 1) {
            if (win == 1) {
                builder.setMessage("Has ganado").setTitle(":D");
                if(marcaFinJuego ==0){
                    ganaJ1++;
                    contador++;
                }


            } else if (win == 2) {
                builder.setMessage("Has perdido").setTitle(":(");
                if(marcaFinJuego ==0){
                    ganaJ2++;
                    contador++;
                }

            }
            marcaFinJuego =1;
            builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id){
                    continuaPartida(new View(getApplicationContext()));
                    if (contador == partidas) {
                        Intent intent = new Intent(getApplicationContext(), GuardarDatos.class);
                        intent.putExtra("Gana J1", ganaJ1);
                        intent.putExtra("Gana J2", ganaJ2);
                        intent.putExtra("Partidas Jugadas Individual", partidas);
                        intent.putExtra("EmpateIndividual", empate);
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
        if (gamov == 0) {
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

                        if (contador == partidas) {
                                Intent intent = new Intent(getApplicationContext(), GuardarDatos.class);
                                intent.putExtra("Gana Jugador 1", ganaJ1);
                                intent.putExtra("Gana Jugador 2", ganaJ2);
                                intent.putExtra("Empates", empate);
                                if (intent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(intent);
                                    finish();
                                }
                        }
                        else {
                            continuaPartida(new View(getApplicationContext()));
                        }
                    }

                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }
    int nivel =0;
    public void randomPlay(){
        int random = (int)(Math.random()*9);
        int i=random/3;
        int j=random%3;
        tablero[i][j].setText("X");
        tablero2[i][j]=1;
    }

    public void juegaMaquina(){
        int turnoActual = turno;
        int tiradaActual = mueve;
        int i=0,j=0;
        int eligeTirada=0;
        int marca=0;
        int marcaJuegoSinTerminar=0;

        //int contador=0;
        //double sum=0;
        if(turno ==1){
            turno =2;
        }
        else{
            turno =1;
        }
        for(int c=0;c<9;c++){
            i=c/3;
            j=c%3;
            probTablero[i][j]=0;
        }

        for(int c=0; c<9;c++) {
            i = c / 3;
            j = c % 3;
            if (tablero2[i][j] == 0) {
                marcaJuegoSinTerminar=1;
                tablero2[i][j] = 1;
                if (cambiaValor == 1)
                    tablero[i][j].setText("X");
                else
                    tablero[i][j].setText("O");
                if (comprobarGanaMaquina() == 2 && cambiaValor == 0) {
                    marca=1;
                    tablero[i][j].setText(" ");
                    tablero2[i][j] = 0;
                    break;
                } else if (comprobarGanaMaquina() == 2 && cambiaValor == 1) {
                    marca=1;
                    tablero[i][j].setText(" ");
                    tablero2[i][j] = 0;
                    break;
                }
                if (comprobarGanaMaquina() == 1 && cambiaValor == 1) {
                    tablero[i][j].setText(" ");
                    tablero2[i][j] = 0;
                    continue;
                } else if (comprobarGanaMaquina() == 1 && cambiaValor == 0) {
                    tablero[i][j].setText(" ");
                    tablero2[i][j] = 0;
                    continue;

                } else {
                    nivel++;
                    probTablero[i][j]= AI();
                    nivel--;
                }
                tablero[i][j].setText(" ");
                tablero2[i][j] = 0;
            }
        }
        if(marcaJuegoSinTerminar==0){
            return;
        }
        double maxProb=0;
        if(marca==0){
            for(int p=0;p<3;p++){
                for(int q=0;q<3;q++){
                    if(maxProb< probTablero[p][q]){
                        maxProb= probTablero[p][q];
                    }
                }
            }
            for(int p=0;p<3;p++){
                for(int q=0;q<3;q++){
                    if(maxProb== probTablero[p][q] && tablero2[p][q]==0){
                        eligeTirada=3*p+q;
                        break;
                    }
                }
            }
        }
        else{
            eligeTirada=3*i+j;
        }
        turno = turnoActual;
        mueve = tiradaActual;
        int xCoord=eligeTirada/3;
        int yCoord=eligeTirada%3;
        tablero2[xCoord][yCoord]=1;
        if(cambiaValor ==0){
            tablero[xCoord][yCoord].setText("O");
            mostrarTurno ="Jugador 1(X)";
            turnoJugador.setText(mostrarTurno);
        }
        else{
            tablero[xCoord][yCoord].setText("X");
            mostrarTurno ="Jugador 1(O)";
            turnoJugador.setText(mostrarTurno);
        }
    }

    public double AI() {
        double sum=0;
        int contador=0;
        int comprobarJuegoSigue=0;
        for(int c=0;c<9;c++){
            int i=c/3;
            int j=c%3;

            if(tablero2[i][j]==0){
                comprobarJuegoSigue=1;
                tablero2[i][j]=1;

                if(turno ==1)
                    tablero[i][j].setText("X");
                else
                    tablero[i][j].setText("O");
                if(comprobarGanaMaquina()==2 && cambiaValor ==0){
                    sum=1;
                    tablero[i][j].setText(" ");
                    tablero2[i][j]=0;

                    return sum;
                }
                else if(comprobarGanaMaquina()==2 && cambiaValor ==1){
                    sum=1;
                    tablero[i][j].setText(" ");
                    tablero2[i][j]=0;

                    return sum;
                }
                else if(comprobarGanaMaquina()==1 && cambiaValor ==1){
                    sum=0;
                    tablero[i][j].setText(" ");
                    tablero2[i][j]=0;

                    return sum;
                }
                else if(comprobarGanaMaquina()==1 && cambiaValor ==0){
                    sum=0;
                    tablero[i][j].setText(" ");
                    tablero2[i][j]=0;

                    return sum;
                }
                else {
                    contador++;
                    if(turno ==1){
                        turno =2;
                    }
                    else{
                        turno =1;
                    }
                    nivel++;
                    double valor= AI();
                    nivel--;
                    sum+=valor;

                }
                tablero[i][j].setText(" ");
                tablero2[i][j]=0;
                if(turno ==1){
                    turno =2;
                }
                else{
                    turno =1;
                }
            }

        }
        if(comprobarJuegoSigue==0){
            return 0.5;
        }
        double average = ((double) sum)/ ((double) contador);
        return average;
    }

    public void continuaPartida(View view) {

        win = 0;
        gamov = 0;
        turno =1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j].setText(" ");
                //tablero[i][j].setTextColor(Color.WHITE);
                tablero2[i][j]=0;
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
        if(cambiaValor ==1){
            randomPlay();
            turno =2;
        }
    }

    public void compruebaGanador() {
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0].getText().toString().equals(tablero[i][1].getText().toString()) && tablero[i][0].getText().toString().equals(tablero[i][2].getText().toString())) {
                if (tablero[i][0].getText().toString().equals("X")) {
                    gamov = 1;
                    if(cambiaValor ==0)
                        win = 1;
                    else if(cambiaValor ==1)
                        win=2;
                } else if (tablero[i][0].getText().toString().equals("O")) {
                    gamov = 1;
                    if(cambiaValor ==0)
                        win = 2;
                    else if(cambiaValor ==1)
                        win=1;
                }
            }
            if (tablero[0][i].getText().toString().equals(tablero[1][i].getText().toString()) && tablero[0][i].getText().toString().equals(tablero[2][i].getText().toString())) {
                if (tablero[0][i].getText().toString().equals("X")) {
                    gamov = 1;
                    if(cambiaValor ==0)
                        win = 1;
                    else if(cambiaValor ==1)
                        win=2;
                } else if (tablero[0][i].getText().toString().equals("O")) {
                    gamov = 1;
                    if(cambiaValor ==0)
                        win = 2;
                    else if(cambiaValor ==1)
                        win=1;
                }
            }
        }
        if (tablero[0][0].getText().toString().equals(tablero[1][1].getText().toString()) && tablero[0][0].getText().toString().equals(tablero[2][2].getText().toString())) {
            if (tablero[0][0].getText().toString().equals("X")) {
                gamov = 1;
                if(cambiaValor ==0)
                    win = 1;
                else if(cambiaValor ==1)
                    win=2;
            } else if (tablero[0][0].getText().toString().equals("O")) {
                gamov = 1;
                if(cambiaValor ==0)
                    win = 2;
                else if(cambiaValor ==1)
                    win=1;
            }
        }
        if (tablero[0][2].getText().toString().equals(tablero[1][1].getText().toString()) && tablero[0][2].getText().toString().equals(tablero[2][0].getText().toString())) {
            if (tablero[0][2].getText().toString().equals("X")) {
                gamov = 1;
                if(cambiaValor ==0)
                    win = 1;
                else if(cambiaValor ==1)
                    win=2;
            } else if (tablero[0][2].getText().toString().equals("O")) {
                gamov = 1;
                if(cambiaValor ==0)
                    win = 2;
                else if(cambiaValor ==1)
                    win=1;
            }
        }
    }

    public int comprobarGanaMaquina() {
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0].getText().toString().equals(tablero[i][1].getText().toString()) && tablero[i][0].getText().toString().equals(tablero[i][2].getText().toString())) {
                if (tablero[i][0].getText().toString().equals("X")) {

                    if(cambiaValor ==0)
                        return 1;
                    else if(cambiaValor ==1)
                        return 2;


                } else if (tablero[i][0].getText().toString().equals("O")) {

                    if(cambiaValor ==0)
                        return 2;
                    else if(cambiaValor ==1)
                        return 1;
                }
            }
            if (tablero[0][i].getText().toString().equals(tablero[1][i].getText().toString()) && tablero[0][i].getText().toString().equals(tablero[2][i].getText().toString())) {
                if (tablero[0][i].getText().toString().equals("X")) {

                    if(cambiaValor ==0)
                        return 1;
                    else if(cambiaValor ==1)
                        return 2;


                } else if (tablero[0][i].getText().toString().equals("O")) {

                    if(cambiaValor ==0)
                        return 2;
                    else if(cambiaValor ==1)
                        return 1;
                }
            }
        }
        if (tablero[0][0].getText().toString().equals(tablero[1][1].getText().toString()) && tablero[0][0].getText().toString().equals(tablero[2][2].getText().toString())) {
            if (tablero[0][0].getText().toString().equals("X")) {

                if(cambiaValor ==0)
                    return 1;
                else if(cambiaValor ==1)
                    return 2;


            } else if (tablero[0][0].getText().toString().equals("O")) {

                if(cambiaValor ==0)
                    return 2;
                else if(cambiaValor ==1)
                    return 1;

            }
        }
        if (tablero[0][2].getText().toString().equals(tablero[1][1].getText().toString()) && tablero[0][2].getText().toString().equals(tablero[2][0].getText().toString())) {
            if (tablero[0][2].getText().toString().equals("X")) {

                if(cambiaValor ==0)
                    return 1;
                else if(cambiaValor ==1)
                    return 2;


            } else if (tablero[0][2].getText().toString().equals("O")) {

                if(cambiaValor ==0)
                    return 2;
                else if(cambiaValor ==1)
                    return 1;
            }
        }
        return 0;
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
