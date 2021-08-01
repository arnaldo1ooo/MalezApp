package com.arnaldo.malezapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.arnaldo.malezapp.familia.ActivityBuscarFamilia;
import com.arnaldo.malezapp.helpers.helpersAreaimageclick.FragmentFiltroDistribucion;
import com.arnaldo.malezapp.lista.ActivityLista;

public class ActivityBusquedaAbierta extends AppCompatActivity {
    private ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_abierta);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Activar boton atras
        getSupportActionBar().setDisplayShowHomeEnabled(true); //Activar icono en actionbar
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_round); //Asignar icono

        iv1 = findViewById(R.id.ivDistribucion);
        iv2 = findViewById(R.id.ivCultivo);
        iv3 = findViewById(R.id.ivTipoHoja);
        iv4 = findViewById(R.id.ivFamilia);
        iv5 = findViewById(R.id.iv5);
        iv6 = findViewById(R.id.iv6);
        iv7 = findViewById(R.id.iv2);
        iv8 = findViewById(R.id.iv3);




        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MostrarFragmentSeleccionado(new FragmentFiltroDistribucion(), R.id.flContainer);
            }
        });

        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityLista.class);
                intent.putExtra("btnSeleccionado", "cultivo");
                startActivityForResult(intent, 0);
            }
        });

        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityLista.class);
                intent.putExtra("btnSeleccionado", "tipohoja");
                startActivityForResult(intent, 0);
            }
        });

        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityBuscarFamilia.class);
                intent.putExtra("btnSeleccionado", "familia");
                startActivityForResult(intent, 0);
            }
        });

        iv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityLista.class);
                intent.putExtra("btnSeleccionado", "ciclo");
                startActivityForResult(intent, 0);
            }
        });

        iv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityLista.class);
                intent.putExtra("btnSeleccionado", "tipoespecie");
                startActivityForResult(intent, 0);
            }
        });

        iv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityLista.class);
                intent.putExtra("btnSeleccionado", "nombrecomun");
                startActivityForResult(intent, 0);
            }
        });

        iv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityLista.class);
                intent.putExtra("btnSeleccionado", "nombrecientifico");
                startActivityForResult(intent, 0);
            }
        });
    }

    private void MostrarFragmentSeleccionado(Fragment fragment, int elContenedor) {
        this.getSupportFragmentManager().beginTransaction()
                .replace(elContenedor, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    //Para que al oprimir en boton subir, retroceda al ultimo activity
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
