package com.arnaldo.malezapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.arnaldo.malezapp.utilidades.areaimageclick.FragmentFiltroDistribucion;

public class ActivityBusquedaGuiada extends AppCompatActivity {
    private TextView tvTitulo, tvSubtitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_guiada);
        getSupportActionBar().setTitle("Búsqueda guiada"); //Titulo del actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Activar boton atras
        getSupportActionBar().setDisplayShowHomeEnabled(true); //Activar icono en actionbar
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_round); //Asignar icono

        tvTitulo=findViewById(R.id.tvTituloBusquedaGuiada);
        tvSubtitulo=findViewById(R.id.tvSubtituloBusquedaGuiada);


        tvTitulo.setText("Titulo");
        tvSubtitulo.setText(R.string.tvDistribucion);
        MostrarFragmentSeleccionado(new FragmentFiltroDistribucion(), R.id.containerBusquedaGuiada); //Mostrar el fragmento por defecto

    }

    private void MostrarFragmentSeleccionado(Fragment elFragment, int elContenedor) {
        this.getSupportFragmentManager().beginTransaction()
                .addToBackStack(null) //Agrega a la pila para que el fragment anterior no se pierda
                .replace(elContenedor, elFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }


    public boolean onSupportNavigateUp() { //Para que al oprimir en boton subir, retroceda al ultimo activity
        onBackPressed();
        return false;
    }
}