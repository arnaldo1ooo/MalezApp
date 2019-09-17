package com.arnaldo.treeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public EditText et_identificador;
    public Button btn_buscar;
    public TextView tv_resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_identificador = findViewById(R.id.et_identificador);
        btn_buscar = findViewById(R.id.btn_buscar);
        tv_resultado = findViewById(R.id.tv_resultado);
    }

    public void Buscar(View view){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        String identi = et_identificador.getText().toString();
        String descripcion = databaseAccess.getDescripcion(identi);

        tv_resultado.setText(descripcion);

        databaseAccess.close();
    }
}
