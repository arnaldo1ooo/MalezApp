package com.arnaldo.treeapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.arnaldo.treeapp.basededatos.DatabaseAccess;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

    private EditText et_identificador;
    private TextView tv_identificador;
    private TextView tv_nombrecomun;
    private TextView tv_nombrecientifico;
    private ImageView iv_imagen;
    private String identiseleccionado;
    private TextView tv_version;
    private AdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Activar icono en actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher); //Asignar icono

        et_identificador = findViewById(R.id.et_identificador);
        tv_identificador = findViewById(R.id.tv_identificador);
        tv_nombrecomun = findViewById(R.id.tv_nombrecomun);
        tv_nombrecientifico = findViewById(R.id.tv_nombrecientifico);
        iv_imagen = findViewById(R.id.iv_imagen);
        tv_version = findViewById(R.id.tv_version);

        String version = getIntent().getExtras().getString("version");
        tv_version.setText(version);
        OcultarObjetos(true);
        IdentiRecibido();
        Banner();
    }

    private void IdentiRecibido() {
        try {
            identiseleccionado = getIntent().getExtras().getString("identiseleccionado");
            et_identificador.setText(identiseleccionado);
            Buscar(null);
        } catch (Exception e) {
            Log.d("IdentiRecibido", "No se pasó ningun identificador: " + e);
        }
    }
    int idimagen;
    public void Buscar(View view) {
        String identificador = et_identificador.getText().toString();
        if (identificador.isEmpty() == true) {
            Toast.makeText(this, "Escriba el identificador de la especie", Toast.LENGTH_SHORT).show();
            Vaciar();
            OcultarObjetos(true);

        } else {
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
            databaseAccess.abrir();

            String[] Array = databaseAccess.ConsultaUnicaEspecie(identificador);

            if (Array[0] == null) { //Si array está vacio
                Toast.makeText(this, "No se encontró ningún registro", Toast.LENGTH_SHORT).show();
                OcultarObjetos(true);
            } else {
                String codigo = Array[0];
                String nombrecomun = Array[1];
                String nombrecientifico = Array[2];
                tv_identificador.setText(identificador);
                tv_nombrecomun.setText(nombrecomun);
                tv_nombrecientifico.setText(nombrecientifico);
                ObtenerImagen(codigo);
            }
            databaseAccess.cerrar();
            OcultarObjetos(false);
            OcultarTeclado();
        }
    }

    private void ObtenerImagen(String codigo) {
        idimagen = getResources().getIdentifier("imagen_" + codigo, "drawable", getPackageName());
        if (idimagen == 0) { //Si imagen no existe
            idimagen = getResources().getIdentifier("imagen_" + 0, "drawable", getPackageName());
            iv_imagen.setImageResource(idimagen);
        } else {
            iv_imagen.setImageResource(idimagen);
        }
    }

    private void Vaciar(){
        tv_identificador.setText("");
        tv_nombrecomun.setText("");
        tv_nombrecomun.setText("");
    }
    private void OcultarObjetos(Boolean ocultar) {
        if (ocultar == true) {
            tv_identificador.setVisibility(View.INVISIBLE);
            tv_nombrecomun.setVisibility(View.INVISIBLE);
            tv_nombrecientifico.setVisibility(View.INVISIBLE);
            iv_imagen.setVisibility(View.INVISIBLE);
        } else {
            tv_identificador.setVisibility(View.VISIBLE);
            tv_nombrecomun.setVisibility(View.VISIBLE);
            tv_nombrecientifico.setVisibility(View.VISIBLE);
            iv_imagen.setVisibility(View.VISIBLE);
        }
    }

    public void BotonTodos(View view) {
        Intent intent = new Intent(view.getContext(), ActivityLista.class);
        startActivityForResult(intent, 0);
    }


    public void OcultarTeclado() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
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
