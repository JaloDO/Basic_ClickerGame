package com.example.diaz_obregon_alejandro_practica1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import java.util.ArrayList;

public class AdapterDB {

    SQLiteDatabase myAdapter;
    DataBase database;
    Context contexto;
    ContentValues valores;

    public AdapterDB(Context contexto){
        this.contexto = contexto;
        database = new DataBase(this.contexto);
        myAdapter = database.getWritableDatabase();
    }


    public long insertar(String nombre, String fecha, String dificultad, int puntos){
        valores = new ContentValues();
        valores.put("nombre",nombre);
        valores.put("fecha",fecha);
        valores.put("dificultad",dificultad);
        valores.put("puntos",puntos);

        return myAdapter.insert("partidas",null,valores);
    }

    public Cursor recuperarPartidas(){
        return myAdapter.rawQuery("select * from partidas",null);
    }

    public void borrar(int position, ArrayList<Integer> id_partida) {
        int id = id_partida.get(position);
        myAdapter.delete("partidas","_id = "+id, null);
    }

    /*
    public void borrar(View v){
        String table = "partidas";
        //String whereClause = "";
        //String[]whereArgs = new String[]{};
        myAdapter.delete(table,, null);
    }
    */

}
