package com.example.matyena.tresenrayafinal;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class GuardarDatos extends AppCompatActivity {

    //Multi
    String ganaJ1Multi;
    String ganaJ2Multi;
    String empateMulti;
    String partidas;

    //Individual
    String ganaJ1Individual;
    String ganaJ2Individual;
    String empateIndividual;
    String partidasIndividual;

    EditText nombreJugador;
    ImageView imagenMuestra;
    Button bAdd;
    String nombreJ;


    private GestureDetectorCompat gestureObject;
    public static ConexionSQLiteHelper sqLiteHelper;
    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardar_datos);
        Intent intent = getIntent();
        ganaJ1Multi = String.valueOf(intent.getExtras().getInt("Gana Jugador 1"));
        ganaJ2Multi = String.valueOf(intent.getExtras().getInt("Gana Jugador 2"));
        empateMulti = String.valueOf(intent.getExtras().getInt("Empates"));
        partidas = String.valueOf(intent.getExtras().getInt("Partidas Jugadas"));

        ganaJ1Individual = String.valueOf(intent.getExtras().getInt("Gana J1"));
        ganaJ2Individual = String.valueOf(intent.getExtras().getInt("Gana J2"));
        empateIndividual = String.valueOf(intent.getExtras().getInt("EmpateIndividual"));
        partidasIndividual = String.valueOf(intent.getExtras().getInt("Partidas Jugadas Individual"));

        nombreJugador = findViewById(R.id.nombreJugador);
        imagenMuestra = findViewById(R.id.imageView);
        bAdd = findViewById(R.id.bAdd);

        //flecha atrás
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gestureObject = new GestureDetectorCompat(this, new LearnGesture());

        sqLiteHelper = new ConexionSQLiteHelper(this, "bdjugadores", null, 1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS jugadores2 (id INTEGER PRIMARY KEY, jugador TEXT, partidas TEXT, victorias TEXT, empates TEXT, imagen BLOB)");
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        GuardarDatos.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent (Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else{
                Toast.makeText(getApplicationContext(), "No", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data !=null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imagenMuestra.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    /**
     * Método que registra los datos en la Base de Datos SQLite (sólo el ganador)
     */

    public void registrarDatos(){
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_jugadores", null, 1);

        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();

        nombreJ = nombreJugador.getText().toString();

        if(nombreJ.equals("")){
            nombreJ="Anonimo";
            nombreJugador.setText("Anonimo");
        }

        if(Integer.parseInt(ganaJ1Multi)>Integer.parseInt(ganaJ2Multi)){

            try{
                sqLiteHelper.insertData(
                        //nombreJugador.getText().toString().trim(),
                        nombreJ,
                        partidas,
                        ganaJ1Multi,
                        empateMulti,
                        imageViewToByte(imagenMuestra)
                );
                Toast.makeText(getApplicationContext(), "Añadido correctamente M", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
            }

            /*values.put(Utilidades.CAMPO_JUGADOR, jugador);
            values.put(Utilidades.CAMPO_IMAGEN, imagen);
            values.put(Utilidades.CAMPO_VICTORIAS, ganaJ1Multi);
            values.put(Utilidades.CAMPO_EMPATES, empateMulti);
            values.put(Utilidades.CAMPO_PARTIDAS, partidas);

            Long idResultante = db.insert(Utilidades.TABLA_JUGADORES, Utilidades.CAMPO_JUGADOR, values);
            Toast.makeText(getApplicationContext(), "Datos guardados1 " + idResultante, Toast.LENGTH_LONG).show();
            db.close();*/
        }
        else if(Integer.parseInt(ganaJ1Multi)<Integer.parseInt(ganaJ2Multi)){

            try{
                sqLiteHelper.insertData(
                        //nombreJugador.getText().toString().trim(),
                        nombreJ,
                        partidas,
                        ganaJ2Multi,
                        empateMulti,
                        imageViewToByte(imagenMuestra)
            );
                Toast.makeText(getApplicationContext(), "Añadido correctamente M", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
            }

            /*values.put(Utilidades.CAMPO_JUGADOR, jugador);
            values.put(Utilidades.CAMPO_VICTORIAS, ganaJ2Multi);
            values.put(Utilidades.CAMPO_EMPATES, empateMulti);
            values.put(Utilidades.CAMPO_PARTIDAS, partidas);

            Long idResultante = db.insert(Utilidades.TABLA_JUGADORES, Utilidades.CAMPO_JUGADOR, values);
            Toast.makeText(getApplicationContext(), "Datos guardados2 " + idResultante, Toast.LENGTH_LONG).show();
            db.close();*/

        }

        //Toast.makeText(getApplicationContext(), "Datos guardados " + idResultante, Toast.LENGTH_LONG).show();
        //db.close();

        if(Integer.parseInt(ganaJ1Individual)>Integer.parseInt(ganaJ2Individual)){

            try{
                sqLiteHelper.insertData(
                        //nombreJugador.getText().toString().trim(),
                        nombreJ,
                        partidasIndividual,
                        ganaJ1Individual,
                        empateIndividual,
                        imageViewToByte(imagenMuestra)
                );
                Toast.makeText(getApplicationContext(), "Añadido correctamente I", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
            }

            /*values.put(Utilidades.CAMPO_JUGADOR, jugador);
            values.put(Utilidades.CAMPO_IMAGEN, imagen);
            values.put(Utilidades.CAMPO_VICTORIAS, ganaJ1Multi);
            values.put(Utilidades.CAMPO_EMPATES, empateMulti);
            values.put(Utilidades.CAMPO_PARTIDAS, partidas);

            Long idResultante = db.insert(Utilidades.TABLA_JUGADORES, Utilidades.CAMPO_JUGADOR, values);
            Toast.makeText(getApplicationContext(), "Datos guardados1 " + idResultante, Toast.LENGTH_LONG).show();
            db.close();*/
        }
        else if(Integer.parseInt(ganaJ1Individual)<Integer.parseInt(ganaJ2Individual)){

            //No se añaden datos porque significa que ha ganado la máquina y te devuelve a inicio

            try{
                sqLiteHelper.insertData(
                        //nombreJugador.getText().toString().trim(),
                        nombreJ,
                        partidasIndividual,
                        ganaJ2Individual,
                        empateIndividual,
                        imageViewToByte(imagenMuestra)
                );
                Toast.makeText(getApplicationContext(), "Añadido correctamente I", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
            }

            /*values.put(Utilidades.CAMPO_JUGADOR, jugador);
            values.put(Utilidades.CAMPO_VICTORIAS, ganaJ2Multi);
            values.put(Utilidades.CAMPO_EMPATES, empateMulti);
            values.put(Utilidades.CAMPO_PARTIDAS, partidas);

            Long idResultante = db.insert(Utilidades.TABLA_JUGADORES, Utilidades.CAMPO_JUGADOR, values);
            Toast.makeText(getApplicationContext(), "Datos guardados2 " + idResultante, Toast.LENGTH_LONG).show();
            db.close();*/

        }

    }

    private byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    /*@Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.guardarBTN:
                registrarDatos();
                break;
        }

    }*/

    class LearnGesture extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            if (e2.getX() > e1.getX()){
                //IZQUIERDA

                registrarDatos();

                //Intent intent = new Intent(GuardarDatos.this, Puntuaciones.class);
                //finish();
                //startActivity(intent);
            }
            else if (e2.getX() < e1.getX()){
                //DERECHA

                Intent intent = new Intent(GuardarDatos.this, Puntuaciones.class);
                finish();
                startActivity(intent);

            }
            return true;
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
            //para que vuelva a la parent activity
            //NavUtils.navigateUpFromSameTask(this);
            //return true;
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
