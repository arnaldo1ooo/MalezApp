package com.arnaldo.malezapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.arnaldo.malezapp.metodos.Metodos;

public class ActivityPrincipal extends AppCompatActivity {
    private Button btnTodos;
    private ImageView ivFondo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        btnTodos= findViewById(R.id.btnPrincipal1);
        ivFondo = findViewById(R.id.ivFondo);

        Metodos metodos = new Metodos();
        metodos.EfectoBlur(ivFondo, 25f, this);
    }

    public void Boton1(View view) {
        Intent intent = new Intent(view.getContext(), ActivityLista.class);
        startActivityForResult(intent, 0);
    }

    public void Boton2(View view) {
        Intent intent = new Intent(view.getContext(), ActivityBuscador.class);
        startActivityForResult(intent, 0);
    }

}
