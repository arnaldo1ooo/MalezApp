package com.arnaldo.malezapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class PoliticasActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.politicas_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Politicas de privacidad");
    }

    public boolean onSupportNavigateUp() { //Que retroceda al ultimo activity
        onBackPressed();
        return false;
    }
}