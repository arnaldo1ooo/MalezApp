package com.arnaldo.malezapp.familia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arnaldo.malezapp.R;
import com.arnaldo.malezapp.lista.ActivityLista;

import java.util.ArrayList;

public class ActivityBuscarFamilia extends AppCompatActivity {

    private RecyclerView rvRVG;
    private final String listanombres[] = {"Alismataceae", "Amaranthaceae", "Apiaceae", "Apocynaceae", "Asteraceae", "Brassicaceae", "Caryophyllaceae",
            "Cleomaceae", "Commelinaceae", "Convolvulaceae", "Cyperaceae", "Euphorbiaceae", "Fabaceae", "Lamiaceae", "Lythraceae", "Malvaceae", "Molluginaceae",
            "Nyctaginaceae", "Onagraceae", "Oxalidaceae", "Phyllanthaceae", "Plantaginaceae", "Poaceae", "Polygonaceae", "Portulaceae", "Rubiaceae",
            "Solanaceae", "Talinaceae", "Urticaceae", "Verbenaceae"
    };

    /*private final int listaimagenes[] = {
            R.drawable.flia_alismataceae, R.drawable.flia_amaranthaceae, R.drawable.flia_apiaceae, R.drawable.flia_apocynaceae, R.drawable.flia_asteraceae,
            R.drawable.flia_brassicaceae, R.drawable.flia_caryophyllaceae, R.drawable.flia_cleomaceae, R.drawable.flia_commelinaceae,
            R.drawable.flia_convolvulaceae, R.drawable.flia_cyperaceae, R.drawable.flia_euphorbiaceae, R.drawable.flia_fabaceae, R.drawable.flia_lamiaceae,
            R.drawable.flia_lythraceae, R.drawable.flia_malvaceae, R.drawable.flia_molluginaceae, R.drawable.flia_nyctaginaceae,
            R.drawable.flia_onagraceae, R.drawable.flia_oxalidaceae, R.drawable.flia_phyllanthaceae, R.drawable.flia_plantaginaceae, R.drawable.flia_poaceae,
            R.drawable.flia_polygonaceae, R.drawable.flia_portulaceae, R.drawable.flia_rubiaceae, R.drawable.flia_solanaceae, R.drawable.flia_talinaceae,
            R.drawable.flia_urticaceae, R.drawable.flia_verbenaceae
    };*/

    private final String listaimagenesURL[] = {
            "flia_alismataceae.png", "flia_amaranthaceae.png", "flia_apiaceae.png", "flia_apocynaceae.png", "flia_asteraceae.png",
            "flia_brassicaceae.png", "flia_caryophyllaceae.png", "flia_cleomaceae.png", "flia_commelinaceae.png",
            "flia_convolvulaceae.png", "flia_cyperaceae.png", "flia_euphorbiaceae.png", "flia_fabaceae.png", "flia_lamiaceae.png",
            "flia_lythraceae.png", "flia_malvaceae.png", "flia_molluginaceae.png", "flia_nyctaginaceae.png",
            "flia_onagraceae.png", "flia_oxalidaceae.png", "flia_phyllanthaceae.png", "flia_plantaginaceae.png", "flia_poaceae.png",
            "flia_polygonaceae.png", "flia_portulaceae.png", "flia_rubiaceae.png", "flia_solanaceae.png", "flia_talinaceae.png",
            "flia_urticaceae.png", "flia_verbenaceae.png"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_familia);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Activar boton atras
        getSupportActionBar().setDisplayShowHomeEnabled(true); //Activar icono en actionbar
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_round); //Asignar icono

        rvRVG = findViewById(R.id.rvBuscarFamilia);


        IniciarVista();
    }

    private void IniciarVista() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvBuscarFamilia);

        //Para que guarde en cache y imageview no esten recargando al hacer scroll
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        ArrayList<ItemBuscarFamilia> listaRVG = PrepararDatos();
        AdaptadorBuscarFamilia adaptadorFamilia = new AdaptadorBuscarFamilia(getApplicationContext(), listaRVG);

        //OnClick
        ((AdaptadorBuscarFamilia) adaptadorFamilia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guardo el codigo del registro seleccionado
                String familiaSelect = listaRVG.get(rvRVG.getChildAdapterPosition(v)).getNombre();
                familiaSelect = familiaSelect.toUpperCase();
                Intent intent = new Intent(v.getContext(), ActivityLista.class);
                intent.putExtra("btnSeleccionado","familia");
                intent.putExtra("familiaseleccionado",familiaSelect);
                startActivityForResult(intent, 0);
            }
        });

        recyclerView.setAdapter(adaptadorFamilia);
    }

    private ArrayList<ItemBuscarFamilia> PrepararDatos() {
        ArrayList<ItemBuscarFamilia> lista = new ArrayList<>();
        for (int i = 0; i < listanombres.length; i++) {
            ItemBuscarFamilia ItemBuscarFamilia = new ItemBuscarFamilia();
            ItemBuscarFamilia.setNombre(listanombres[i]);
            //itemRVG.setImagenLocal(listaimagenes[i]);
            ItemBuscarFamilia.setImagenURL(listaimagenesURL[i]);

            lista.add(ItemBuscarFamilia);
        }
        return lista;
    }

    //Para que al oprimir en boton subir, retroceda al ultimo activity
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}