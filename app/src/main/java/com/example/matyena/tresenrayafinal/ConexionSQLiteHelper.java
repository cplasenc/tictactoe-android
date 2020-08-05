package com.example.matyena.tresenrayafinal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData(String jugador, String partidas, String victorias, String empates, byte[] imagen){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO jugadores2 VALUES (NULL, ?, ?, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, jugador);
        statement.bindString(2, partidas);
        statement.bindString(3, victorias);
        statement.bindString(4, empates);
        statement.bindBlob(5, imagen);

        statement.executeInsert();
    }

    public Cursor getData(String sql){

        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);

    }

    @Override
    public void onCreate(SQLiteDatabase db) { //genera las tablas

        //db.execSQL(Utilidades.CREAR_TABLA_JUGADORES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) { //verifica si ya existe la BBDD

        //db.execSQL("DROP TABLE IF EXISTS jugadores");
        //onCreate(db);

    }

}
