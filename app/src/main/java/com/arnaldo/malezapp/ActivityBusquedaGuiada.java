package com.arnaldo.malezapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.arnaldo.malezapp.helpers.HelpersFragment;
import com.arnaldo.malezapp.helpers.helpersAreaimageclick.FragmentFiltroDistribucion;

public class ActivityBusquedaGuiada extends AppCompatActivity {
    private TextView tvTitulo, tvSubtitulo;
    private HelpersFragment helpersFragment = new HelpersFragment();

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
        helpersFragment.MostrarFragmentSeleccionado(this.getSupportFragmentManager(), FragmentTransaction.TRANSIT_FRAGMENT_OPEN,
                new FragmentFiltroDistribucion(), R.id.containerBusquedaGuiada); //Mostrar el fragmento por defecto

    }


    public boolean onSupportNavigateUp() { //Para que al oprimir en boton subir, retroceda al ultimo activity
        onBackPressed();
        return false;
    }
}