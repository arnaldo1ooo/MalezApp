package com.arnaldo.malezapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ActivityBuscador extends AppCompatActivity {


    private String identiseleccionado;
    private TextView tv_version;
    private AdView adView;
    private EditText et_identificador;

    @Override
    public void onBackPressed() {
        Log.d("BotonAtras", "Se oprimió el botón atrás");
        CerrarAplicacion();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);

        //Activar icono en actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher); //Asignar icono





        IdentiRecibido();
        Banner();
    }

    private void IdentiRecibido() {
        try {
            identiseleccionado = getIntent().getExtras().getString("identiseleccionado");
            et_identificador.setText(identiseleccionado);
            //Buscar(null);
        } catch (Exception e) {
            Log.d("IdentiRecibido", "No se pasó ningun identificador: " + e);
        }
    }

    int idimagen;

    public void Buscar(View view) {
        /*String identificador = et_identificador.getText().toString();
        if (identificador.isEmpty() == true) {
            Toast.makeText(this, "Escriba el identificador de la especie", Toast.LENGTH_SHORT).show();
            Vaciar();
            OcultarObjetos(true);

        } else {
            Conexion conexion = Conexion.getInstance(getApplicationContext());
            conexion.Abrir();

            //String[] Array = conexion.ConsultaUnicaEspecie(identificador);

            if (Array[0] == null) { //Si array está vacio
                Toast.makeText(this, "No se encontró ningún registro", Toast.LENGTH_SHORT).show();
                OcultarObjetos(true);
            } else {
                String codigo = Array[0];
                String nombrecomun = Array[1];
                String nombrecientifico = Array[2];
                tv_nombrecomun.setText(nombrecomun);
                tv_nombrecientifico.setText(nombrecientifico);
                ObtenerImagen(codigo);
            }
            conexion.Cerrar();
            OcultarObjetos(false);
            OcultarTeclado();
        }*/
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


    private void CerrarAplicacion() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("¿Realmente desea cerrar la aplicación?")
                .setCancelable(false)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {// un listener que al pulsar, cierre la aplicacion
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        android.os.Process.killProcess(android.os.Process.myPid()); //Su funcion es algo similar a lo que se llama cuando se presiona el botón "Forzar Detención" o "Administrar aplicaciones", lo cuál mata la aplicación
                        //finish(); Si solo quiere mandar la aplicación a segundo plano
                    }
                }).show();
    }
}
