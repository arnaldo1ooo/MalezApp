package com.arnaldo.treeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.arnaldo.treeapp.basededatos.DatabaseAccess;

public class MainActivity extends AppCompatActivity {

    public EditText et_identificador;
    public Button btn_buscar;
    public TextView tv_identificador;
    public TextView tv_nombrecomun;
    public TextView tv_nombrecientifico;
    public ImageView iv_imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Activar icono en actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        et_identificador = findViewById(R.id.et_identificador);
        tv_identificador = findViewById(R.id.tv_identificador);
        tv_nombrecomun = findViewById(R.id.tv_nombrecomun);
        tv_nombrecientifico = findViewById(R.id.tv_nombrecientifico);
        iv_imagen = findViewById(R.id.iv_imagen);

        Ocultar(true);
    }

    public void Buscar(View view) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.abrir();

        Ocultar(false);
        OcultarTeclado();
        String identificador = et_identificador.getText().toString();

        String[] Array = databaseAccess.ConsultaUnicaEspecie(identificador);
        String codigo = Array[0];
        String nombrecomun = Array[1];
        String nombrecientifico = Array[2];

        if (Array.length == 0) { //Si array está vacio
            Toast.makeText(this, "No se encontró ningún registro", Toast.LENGTH_SHORT).show();
            Ocultar(true);
        } else {
            tv_identificador.setText(identificador);
            tv_nombrecomun.setText(nombrecomun);
            tv_nombrecientifico.setText(nombrecientifico);
            iv_imagen.setImageResource(getResources().getIdentifier("imagen_" + codigo, "drawable", getPackageName()));
        }
        databaseAccess.cerrar();
    }


    private void Ocultar(Boolean ocultar) {
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
        Intent intent = new Intent (view.getContext(), activity_lista.class);
        startActivityForResult(intent, 0);
    }


    public void OcultarTeclado() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
