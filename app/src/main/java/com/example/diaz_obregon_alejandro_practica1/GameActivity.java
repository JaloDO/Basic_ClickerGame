package com.example.diaz_obregon_alejandro_practica1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameActivity extends AppCompatActivity {
    TextView txtCountDown, txtPreGameSpeech, txtPuntos, txtToque, txtRango;
    int contador_tick,puntos=0;

    double tiempo_pulsacion=0,tiempo_diferencia=0;
    //parametros de configuracion de partida
    int limite_tiempo = 30;
    double precision = 100;

    AdapterDB adapterDB;
    //DecimalFormat formato_numero = new DecimalFormat("#.000");
    SimpleDateFormat formato_fecha = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");

    ImageButton imgBoton;
    Chronometer crono;

    SoundPool sp = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
    int click,acierto,fallo;
    boolean flag;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_game);


        GameActivity.this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        click= sp.load(getApplicationContext(),R.raw.beepsbonksboinks_3,1);
        acierto= sp.load(getApplicationContext(),R.raw.analog_fx_4,1);
        fallo = sp.load(getApplicationContext(),R.raw.whoosh,1);



        txtCountDown=findViewById(R.id.txtCountDown);
        txtPreGameSpeech = findViewById(R.id.txtPreGameSpeech);

        txtPuntos = findViewById(R.id.txtPuntos);
        imgBoton = findViewById(R.id.imageButton);
        txtToque = findViewById(R.id.txtToque);
        txtRango = findViewById(R.id.txtRango);

        crono = findViewById(R.id.crnTiempo);

        adapterDB = new AdapterDB(getApplicationContext());


        flag=comprobarSonido();
        //no funciona el timezone
        //formato_fecha.setTimeZone(TimeZone.getTimeZone("GTM+2:00"));

        //encapsulamos el proceso de obtener el tiempo de partida para cada dificultad
        limite_tiempo = obtenerTiempo();
        contador_tick = 0;

        ///////START PRE-GAME
        cuentaAtras();


        //////////////////////////////////////
        //////////IMAGEBUTTON CLICKER/////////
        //////////////////////////////////////
        imgBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //musica al pulsar el imgButton
                if(flag){ sp.play(click,1,1,0,0,1.5f); }

                //el tiempo de pulsacion recoge el long desde 1970, a eso le vamos restando el tiempo de la pulsacion anterior,
                //por lo que obtenemos en tiempo_diferencia es el espacio entre pulsaciones
               tiempo_pulsacion = System.currentTimeMillis();
               tiempo_diferencia = (tiempo_pulsacion-tiempo_diferencia);

               txtToque.setText(""+tiempo_diferencia/1000);


               //esta condicion comprueba que el tiempo que tarda el usuario entre dos pulsaciones
               //está dentro de un rango de tiempo, que es 1 segundo + la precisión dependiendo de la dificultad
               //es decir, sumará puntos si pulsa aproximadamente 1 vez por segundo
               if(tiempo_diferencia/1000>1-precision && (tiempo_diferencia/1000<1+precision)){

                   /*Toast.makeText(
                           getApplicationContext(),
                           ""+tiempo_diferencia/1000
                                   +"\nRango: "+formato.format(1-precision)
                                   +" - "+formato.format(1+precision),
                           Toast.LENGTH_SHORT).show();*/
                   puntos++;
                   txtPuntos.setText(""+puntos);
                   txtToque.setBackgroundResource(R.color.alsoGreen);
                   if(flag){sp.play(acierto,1,1,0,0,1);}
               }else{
                   if(flag){sp.play(fallo,1,1,0,0,1);}
                   txtToque.setBackgroundResource(R.color.alsoRed);
               }

               //actualizamos el tiempo_diferencia al valor de tiempo_pulsacion
                // para que almacene el tiempo en el que se dio el ultimo toque
               tiempo_diferencia = tiempo_pulsacion;
            }
        });

        /////////////////////////////////////////
        ////////////CHRONOMETER TICK/////////////
        /////////////////////////////////////////
        crono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                contador_tick++;

                if (contador_tick>=limite_tiempo) {
                    crono.stop();
                    almacenarPartida();
                    generarDialogo();
                }
            }
        });
    }



    ///////////////////////////////////
    /////////PRE-GAME METHOD///////////
    ///////////////////////////////////
    private void cuentaAtras() {



        //txtPreGameSpeech.setEnabled(true);
        txtPreGameSpeech.setVisibility(View.VISIBLE);
        //txtCountDown.setEnabled(true);
        txtCountDown.setVisibility(View.VISIBLE);


        txtPuntos.setVisibility(View.INVISIBLE);
        imgBoton.setVisibility(View.INVISIBLE);
        imgBoton.setEnabled(false);
        crono.setVisibility(View.INVISIBLE);
        crono.setEnabled(false);

        new CountDownTimer(4000, 1000) {

            //Cuenta atrás de 10 segundos con actualización de 1 segundo

            public void onTick(long millisUntilFinished) {


                //Cuando cambia el contador
                //Actualizamos la etiqueta que muestra el contador
                contador_tick ++;
                switch(contador_tick){
                    case 1:
                        txtPreGameSpeech.setTextSize(28);
                        txtPreGameSpeech.setText("PREPARADO...");
                        txtCountDown.setText(""+millisUntilFinished / 1000);
                        break;
                    case 2:
                        txtPreGameSpeech.setText("¿LISTO?");
                        txtCountDown.setText(""+millisUntilFinished / 1000);
                        break;
                    case 3:
                        txtCountDown.setText(""+millisUntilFinished / 1000);
                        break;
                    case 4:
                        txtPreGameSpeech.setWidth(200);
                        txtPreGameSpeech.setHeight(200);
                        txtPreGameSpeech.setTextSize(56);
                        txtPreGameSpeech.setText("YAAA!!!!");
                        txtCountDown.setText("");
                        break;
                    default:
                        break;
                }

            }
            //Evento que se lanza cuando acaba la cuenta atrás
            public void onFinish() {
                //warm up del layout
                //escondemos el prejuego
                //txtPreGameSpeech.setEnabled(false);
                txtPreGameSpeech.setVisibility(View.INVISIBLE);
                //txtCountDown.setEnabled(false);
                txtCountDown.setVisibility(View.INVISIBLE);

                //mostramos los elementos necesarios
                txtToque.setVisibility(View.VISIBLE);
                txtRango.setVisibility(View.VISIBLE);
                txtPuntos.setVisibility(View.VISIBLE);
                imgBoton.setVisibility(View.VISIBLE);
                imgBoton.setEnabled(true);
                crono.setVisibility(View.VISIBLE);
                crono.setEnabled(true);



                //AQUÍ COMIENZA COMO TAL EL JUEGO DE LA APP
                crono.setBase(SystemClock.elapsedRealtime());
                crono.start();
                tiempo_diferencia=System.currentTimeMillis();

            }
        }.start();
    }
    //////////////////////////////////////////
    /////DIFFICULTY LEVEL SELECTION METHOD////
    //////////////////////////////////////////
    private int obtenerTiempo() {
        //obtener los datos del shared preferences
        SharedPreferences miShared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String sp = miShared.getString("list_dificultad","Facil");
        Toast.makeText(GameActivity.this, "dificultad: "+sp, Toast.LENGTH_LONG).show();
        //switch de clasificacion
        switch(sp){
            case "Facil":
                txtRango.setText("Rango: 0 - 100");
                return 36;
            case "Medio":
                txtRango.setText("Rango: 0.8 - 1.2");
                precision = (float) 0.2;
                return 26;
            case "Dificil":
                txtRango.setText("Rango: 0.9 - 1.1");
                precision = (float) 0.1;
                return 16;
            default:
                txtRango.setText("Rango: 0 - 100");
                return 36;
        }
    }

    private void generarDialogo() {
        //ESTE DIALOGO DARÁ LA OPCIÓN DE HACER UN INTENT AL MENÚ, O DE VOLVERA JUGAR.
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);

        builder.setMessage(null).setTitle("Fin de juego");
        builder.setPositiveButton("Jugar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                defaultValores();
                cuentaAtras();
            }
        });
        builder.setNegativeButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });
        AlertDialog dialogo = builder.create();
        dialogo.show();
    }

    private void defaultValores() {
        contador_tick = 0;
        puntos=0;
        txtPuntos.setText("0");
        imgBoton.setEnabled(true);
        txtToque.setText("");
    }

    /////////////////////////////////
    /////PUSH BACK-BUTTON METHOD/////
    /////////////////////////////////
    @Override
    public void onBackPressed(){
        crono.stop();
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);

        builder.setMessage("¿Estás seguro de que quieres salir?").setTitle("Atención");
        builder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tiempo_diferencia = System.currentTimeMillis();
                crono.start();

            }
        });
        AlertDialog dialogo = builder.create();
        dialogo.show();
    }

    /////////////////////////////
    ////DB STORE GAME METHOD/////
    /////////////////////////////
    private void almacenarPartida() {
        SharedPreferences miShared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String dificultad = miShared.getString("list_dificultad","Facil");
        String nombre = miShared.getString("name","default");
        String fecha = formato_fecha.format(new Date(System.currentTimeMillis()));

        long abc = adapterDB.insertar(nombre, fecha, dificultad, puntos);

        //Toast.makeText(getApplicationContext(),""+abc, Toast.LENGTH_LONG).show();
    }


    private Boolean comprobarSonido() {
        SharedPreferences miShared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean resultado = miShared.getBoolean("sonido",false);
        Log.d("sonido",""+resultado);
        return resultado;
    }

}
