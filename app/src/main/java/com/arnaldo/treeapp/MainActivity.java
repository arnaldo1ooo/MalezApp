package com.arnaldo.treeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        btn_buscar = findViewById(R.id.btn_buscar);
        tv_identificador = findViewById(R.id.tv_identificador);
        tv_nombrecomun = findViewById(R.id.tv_nombrecomun);
        tv_nombrecientifico = findViewById(R.id.tv_nombrecientifico);
        iv_imagen = findViewById(R.id.iv_imagen);

        Ocultar(true);
    }

    public void Buscar(View view) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        Ocultar(false);
        String identificador = et_identificador.getText().toString();

        String[] Array = databaseAccess.getEspecies(identificador);
        String codigo = Array[0];
        String nombrecomun = Array[1];
        String nombrecientifico = Array[2];

        if (Array.length == 0) { //Si array está vacio
            Toast.makeText(this, "No se encontró ningún registro", Toast.LENGTH_SHORT).show();
            Ocultar(true);
        } else {
            tv_identificador.setText("Identificador: " + identificador);
            tv_nombrecomun.setText("Nombre común: " + nombrecomun);
            tv_nombrecientifico.setText("Nombre científico: " + nombrecientifico);
            iv_imagen.setImageResource(getResources().getIdentifier("imagen_" + identificador, "drawable", getPackageName()));
        }
        databaseAccess.close();
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
}
