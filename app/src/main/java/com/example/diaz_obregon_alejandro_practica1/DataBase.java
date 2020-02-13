package com.example.diaz_obregon_alejandro_practica1;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DataBase extends SQLiteOpenHelper {

    Context contexto;

    public DataBase(Context context) {
        super(context,"DB-Cons", null, 1);
        this.contexto=context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(
                    "CREATE TABLE partidas (" +
                            "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + "nombre VARCHAR, fecha VARCHAR,"
                            + " dificultad VARCHAR,"
                            + " puntos INTEGER)"
            );
        }catch(SQLException e){
            Toast.makeText(contexto,""+e,Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            db.execSQL("DROP TABLE IF EXISTS partidas");

            onCreate(db);
        }catch(SQLException e){
            Toast.makeText(contexto,""+e,Toast.LENGTH_SHORT).show();
        }
    }
}
