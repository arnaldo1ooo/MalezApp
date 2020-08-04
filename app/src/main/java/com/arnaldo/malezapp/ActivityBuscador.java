package com.arnaldo.malezapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.arnaldo.malezapp.metodos.Metodos;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ActivityBuscador extends AppCompatActivity {
    private TextView tv_version;
    private AdView adView;
    private EditText etBuscarNomCom;
    private EditText etBuscarNomCien;
    private Spinner spFamilia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Activar boton atras

        //Activar icono en actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher); //Asignar icono

        etBuscarNomCom = findViewById(R.id.etBuscarNomCom);
        etBuscarNomCien = findViewById(R.id.etBuscarNomCien);
        spFamilia = findViewById(R.id.spFamilia);


        Metodos metodos = new Metodos();
        //Poblar spinner
        spFamilia.setAdapter(metodos.PoblarSpinner(this, R.layout.spinner_formato_item,
                "SELECT fam_codigo, fam_descripcion FROM familia ORDER BY fam_descripcion"));


        Banner();
    }


    public void Buscar(View view) {
        String elnombrecomun = etBuscarNomCom.getText().toString();
        String elnombrecienti = etBuscarNomCien.getText().toString();

        String familia = "";
        if (spFamilia.getSelectedItem() != "TODOS") {
            familia = spFamilia.getSelectedItem().toString();
        }

        Intent intent;
        intent = new Intent(ActivityBuscador.this, ActivityLista.class);
        intent.putExtra("consultaSQL", "SELECT mal_codigo, mal_nombrecomun, mal_nombrecientifico " +
                "FROM maleza, familia WHERE " +
                "mal_nombrecomun LIKE '%" + elnombrecomun + "%' AND " + //Busca por nombre comun
                "mal_nombrecientifico LIKE '%" + elnombrecienti + "%' AND " + //Busca por nombre cientifico
                "mal_familia = fam_codigo AND fam_descripcion LIKE '%" + familia + "%' " + //Busca por familia
                "ORDER BY mal_nombrecomun");
        startActivity(intent);
    }

    private void Banner() {
        adView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Código a ejecutar cuando un anuncio termina de cargarse.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Código a ejecutar cuando falla una solicitud de anuncio.
            }

            @Override
            public void onAdOpened() {
                // Código que se ejecutará cuando un anuncio abra una
                // superposición que cubre la pantalla.
            }

            @Override
            public void onAdClicked() {
                // Código que se ejecutará cuando el usuario
                // haga clic en un anuncio.
            }

            @Override
            public void onAdLeftApplication() {
                // Código a ejecutar cuando el usuario
                // ha abandonado la aplicación.
            }

            @Override
            public void onAdClosed() {
                // Código a ejecutar cuando el usuario está a punto de regresar
                // a la aplicación después de pulsar en un anuncio.
            }
        });
    }
}
