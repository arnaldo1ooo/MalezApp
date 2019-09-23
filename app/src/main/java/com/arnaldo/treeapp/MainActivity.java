package com.arnaldo.treeapp;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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

public class MainActivity extends AppCompatActivity {

    private EditText et_identificador;
    private TextView tv_identificador;
    private TextView tv_nombrecomun;
    private TextView tv_nombrecientifico;
    private ImageView iv_imagen;
    private String identiseleccionado;
    private TextView tv_version;

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

        OcultarObjetos(true);
        IdentiRecibido();
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

    public void Buscar(View view) {
        String identificador = et_identificador.getText().toString();
        if (identificador.isEmpty() == true) {
            Toast.makeText(this, "Escriba el identificador de la especie", Toast.LENGTH_SHORT).show();
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
                iv_imagen.setImageResource(getResources().getIdentifier("imagen_" + codigo, "drawable", getPackageName()));
            }
            databaseAccess.cerrar();
            OcultarObjetos(false);
            OcultarTeclado();
        }
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


    private String ObtenerVersionApp() {
        //Obtener version actual de la app
        String versionApp = "null";
        try {
            PackageInfo packageInfo;
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionApp = String.valueOf(packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "No se puede cargar la version actual!", Toast.LENGTH_LONG).show();
        }
        return versionApp;
    }
}
