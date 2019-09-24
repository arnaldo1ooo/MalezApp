package com.arnaldo.treeapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arnaldo.treeapp.basededatos.DatabaseAccess;
import com.arnaldo.treeapp.rv_lista.itemLista;
import com.arnaldo.treeapp.rv_lista.rvLista;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class ActivityLista extends AppCompatActivity {
    private RecyclerView recyclerview;
    private RecyclerView.Adapter adaptador;
    private LinearLayoutManager layoutManager;
    private AdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Boton atras
        getSupportActionBar().setDisplayShowHomeEnabled(true); //Activar icono en actionbar
        getSupportActionBar().setIcon(R.mipmap.ic_launcher); //Asignar icono
        CargarConsultaaRV();
        Banner();
    }

    private void CargarConsultaaRV() {
        //Se crea el recyclerview y adaptador
        recyclerview = (RecyclerView) findViewById(R.id.rv_principal);
        layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);

        //Adaptador
        adaptador = new rvLista(this, MetodoListItem());


        //OnClick
        ((rvLista) adaptador).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guardo el identificador seleccionado
                String identiseleccionado = MetodoListItem().get(
                        recyclerview.getChildAdapterPosition(view)).getIdentificador();

                Intent intent;
                intent = new Intent(ActivityLista.this, MainActivity.class);
                intent.putExtra("identiseleccionado", identiseleccionado);
                startActivity(intent);

                //Imprime el titulo del item seleccionado
                Toast.makeText(getApplicationContext(), "Identificador seleccionado: " + MetodoListItem().get(
                        recyclerview.getChildAdapterPosition(view)).getIdentificador(), Toast.LENGTH_SHORT).show();
            }
        });



        recyclerview.setAdapter(adaptador);

        //Linea divisor de RecyclerView
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerview.addItemDecoration(dividerItemDecoration);
    }


    private ArrayList<itemLista> MetodoListItem() {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.abrir();
        Cursor cursor = databaseAccess.ConsultaAllEspecies();
        ArrayList<itemLista> listItems = new ArrayList<>();

        String identificador;
        String nombrecomun;
        String nombrecientifico;

        while (cursor.moveToNext() == true) {
            String codigo = cursor.getString(0); //Columna 0
            int idimagen = getResources().getIdentifier("imagen_" + codigo, "drawable", getPackageName());
            identificador = cursor.getString(1); //Columna 1
            nombrecomun = cursor.getString(2); //Columna 2
            nombrecientifico = cursor.getString(3); //Columna 3
            //Log.d("SQL", "Codigo: " + codigo + ", Id: " + identificador + ", nomrecom: " + nombrecomun + ", nombrecie: " + nombrecientifico);

            if (idimagen == 0) { //Si imagen no existe
                listItems.add(new itemLista(R.drawable.imagen_0, identificador, nombrecomun, nombrecientifico));
            } else {
                listItems.add(new itemLista(idimagen, identificador, nombrecomun, nombrecientifico));
            }
        }
        Log.d("CargarConsultaaRV", "Se cargó todos los registros de la tabla especie al Recycler View");
        databaseAccess.cerrar();
        return listItems;
    }

    private void Banner() {
        adView = findViewById(R.id.adView2);
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
}
