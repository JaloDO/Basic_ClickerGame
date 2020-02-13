package com.example.diaz_obregon_alejandro_practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ScoreBoardActivity extends AppCompatActivity {

    ListView listaMarcadores;
    AdapterDB adapterDB;

    ArrayList<String>ranking1,ranking2;
    ArrayList<Integer>id_partida;
    ArrayAdapter<String>adaptador;
    Cursor marcadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_scoreboard);
        listaMarcadores = (ListView) findViewById(R.id.listaMarcadores);
        adapterDB = new AdapterDB(getApplicationContext());


        cargar();


        listaMarcadores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), ranking2.get(position),Toast.LENGTH_LONG).show();
            }
        });

        listaMarcadores.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                adapterDB.borrar(position, id_partida);
                cargar();
                return false;
            }
        });
    }

    public void cargar(){
        ranking1 = new ArrayList<String>();
        ranking2 = new ArrayList<String>();
        id_partida = new ArrayList<Integer>();

        marcadores = adapterDB.recuperarPartidas();
        if(marcadores.moveToFirst()){
            do{
                ranking1.add(marcadores.getString(1));
                ranking2.add("Nombre: "+marcadores.getString(1)+"\nFecha: "+marcadores.getString(2)
                        +"\nDificultad: "+marcadores.getString(3)+"\tPuntos: "+marcadores.getInt(4));
                id_partida.add(marcadores.getInt(0));
            }while(marcadores.moveToNext());
        }

        adaptador = new ArrayAdapter<String>(ScoreBoardActivity.this,android.R.layout.simple_list_item_1, ranking1);
        listaMarcadores.setAdapter(adaptador);
    }
}
