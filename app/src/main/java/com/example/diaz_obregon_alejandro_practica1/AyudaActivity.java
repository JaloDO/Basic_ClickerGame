package com.example.diaz_obregon_alejandro_practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AyudaActivity extends AppCompatActivity {
Button volver;
TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ayuda);
        volver=findViewById(R.id.btnVolver);
        texto = findViewById(R.id.txtAyuda);

        texto.setText("Mecanica y finalidad del juego:\n\tSe trata de un clicker con el objetivo de hacer el maximo de puntos posible." +
                "\n\tLa dificultad del juego se selecciona en el menú de configuración." +
                "\n\tSegún la dificultad seleccionada se añaden progresivas restricciones que dificultan la obtencion de puntos." +
                "\n\nRestricciones:" +
                "\n\t->Reducción del tiempo de juego." +
                "\n\t->Reducción del rango de pulsación." +
                "\n\t\tEl rango determina los clicks que se darán por buenos; esto es el rango de tiempo en segundos que debes tardar desde que das un click hasta que das el siguiente." +
                "\n\t\tEl rango de cada partida aparecerá en la esquina inf. derecha de la pantalla." +
                "\n\t\tLa diferencia en segundos entre cada click aparecerá en la esquina inf. izquierda." +
                "\n\nDificultad:" +
                "\n\tFacil:\n\t\t30 segundos, rango de 0 a 100 segundos (simbólico)." +
                "\n\tMedio:\n\t\t20 segundos, rango de 0.8 a 1.2 segundos." +
                "\n\tDificil:\n\t\t10 segundos, rando de 0.9 a 1.1 segundos."
        );

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
