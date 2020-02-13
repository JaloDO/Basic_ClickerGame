package com.example.diaz_obregon_alejandro_practica1;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import androidx.annotation.Nullable;


public class ConfigActivity extends PreferenceActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.configuracion);
    }
}
