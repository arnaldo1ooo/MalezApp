package com.arnaldo.malezapp.lista;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arnaldo.malezapp.ActivityDetalle;
import com.arnaldo.malezapp.R;
import com.arnaldo.malezapp.conexion.Conexion;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ActivityLista extends AppCompatActivity {
    private RecyclerView recyclerview;
    private RecyclerView.Adapter adaptador;
    private LinearLayoutManager layoutManager;
    private AdView adView;
    private String consultaSQL;
    private TextView tvTituloLista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Boton atras
        getSupportActionBar().setDisplayShowHomeEnabled(true); //Activar icono en actionbar
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_round); //Asignar icono

        tvTituloLista = findViewById(R.id.tvTituloLista);


        try {
            consultaSQL = getIntent().getExtras().getString("consultaSQL").toString();
            tvTituloLista.setText("Malezas encontradas");
        } catch (Exception e) {
            tvTituloLista.setText("Lista completa de malezas");
            consultaSQL = "SELECT mal_codigo, mal_nombrecomun, mal_nombrecientifico FROM maleza, familia WHERE mal_familia=fam_codigo ORDER BY mal_nombrecomun";
        }

        CargarConsultaaRV(ConsultaBD(consultaSQL));


        Banner();
    }

    private void CargarConsultaaRV(ArrayList<itemLista> laLista) {
        //Se crea el recyclerview y adaptador
        recyclerview = (RecyclerView) findViewById(R.id.rv_principal);
        layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);

        //Adaptador
        adaptador = new rvLista(this, laLista);


        //OnClick
        ((rvLista) adaptador).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guardo el codigo del registro seleccionado
                String codSelect = laLista.get(recyclerview.getChildAdapterPosition(view)).getCodigo();

                Intent intent;
                intent = new Intent(ActivityLista.this, ActivityDetalle.class);
                intent.putExtra("codigoSeleccionado", codSelect);
                startActivity(intent);
            }
        });

        recyclerview.setAdapter(adaptador);

        //Linea divisor de RecyclerView
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerview.addItemDecoration(dividerItemDecoration);
    }


    private ArrayList<itemLista> ConsultaBD(String consultaSQL) {
        Conexion conexion = new Conexion(this);
        conexion.Abrir();
        Cursor cursor = conexion.EjecutarSQL(consultaSQL);
        int cantnombres;
        ArrayList<itemLista> listItems = new ArrayList<>();
        while (cursor.moveToNext() == true) {
            String codigo = cursor.getString(0); //Columna 0
            int idimagen = getResources().getIdentifier("imagen_" + codigo + "a", "drawable", getPackageName());

            String nombrecomun = cursor.getString(1); //Columna 1
            String separador = Pattern.quote(", "); //El caracter en dodne se cortara
            if (nombrecomun.contains(", ")) {
                String[] stringsplit = nombrecomun.split(separador);
                cantnombres = stringsplit.length - 2;
                if (cantnombres > 0) {
                    nombrecomun = stringsplit[0] + ", " + stringsplit[1] + " (más " + cantnombres + ")";
                } else {
                    nombrecomun = stringsplit[0] + ", " + stringsplit[1];
                }
            }

            String nombrecientifico = cursor.getString(2); //Columna 2

            if (idimagen == 0) { //Si imagen no existe poner imagen por default
                listItems.add(new itemLista(codigo, R.drawable.imagen_0, nombrecomun, nombrecientifico));
            } else {
                listItems.add(new itemLista(codigo, idimagen, nombrecomun, nombrecientifico));
            }
        }
        cursor.close();
        conexion.Cerrar();

        return listItems;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
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
