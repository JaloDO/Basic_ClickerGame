package com.example.diaz_obregon_alejandro_practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

public class InicioActivity extends AppCompatActivity {

    TextView tv;
    ImageView iv_gif;
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_inicio);
        tv=findViewById(R.id.textView);
        iv_gif = (ImageView)findViewById(R.id.iv_gif);
        tv.setText("Diaz_Obregon_Alejandro_PracticaPMDM\n\tv 1.1.27");

        iv_gif.setBackgroundResource(R.drawable.load_gif);

        final AnimationDrawable gifAnimation = (AnimationDrawable)iv_gif.getBackground();
        gifAnimation.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent menuIntent = new Intent(InicioActivity.this, MenuActivity.class);
                InicioActivity.this.startActivity(menuIntent);
                InicioActivity.this.finish();
                gifAnimation.stop();
            }
        },SPLASH_DISPLAY_LENGTH);
    }
}
