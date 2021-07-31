package com.arnaldo.malezapp.tipohoja;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arnaldo.malezapp.R;
import com.arnaldo.malezapp.dao.DAO;
import com.arnaldo.malezapp.lista.ActivityLista;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ActivityBuscarTipoHoja extends AppCompatActivity {
    private RecyclerView rvPrincipal;
    private TextView tvTituloLista;
    private RecyclerView.Adapter adaptador;
    private LinearLayoutManager layoutManager;
    private AdView adView;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference(); //Abrir conexion con storage de firestore

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscar_tipohoja_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Boton atras
        getSupportActionBar().setDisplayShowHomeEnabled(true); //Activar icono en actionbar
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_round); //Asignar icono

        rvPrincipal = findViewById(R.id.rvPrincipal);
        tvTituloLista = findViewById(R.id.tvTituloLista);

        CargarConsultaaRV(ConsultaBD("SELECT th_codigo, th_descripcion, th_detalle FROM tipo_hoja ORDER BY th_descripcion"));

        Banner();
    }

    private void CargarConsultaaRV(ArrayList<ItemBuscarTipoHoja> laLista) {
        //Se crea el recyclerview y adaptador
        layoutManager = new LinearLayoutManager(this);
        rvPrincipal.setLayoutManager(layoutManager);
        adaptador = new AdaptadorBuscarTipoHoja(this, laLista);//Adaptador

        //OnClick
        ((AdaptadorBuscarTipoHoja) adaptador).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guardo el codigo del registro seleccionado
                String idTipoHojaSelect = laLista.get(rvPrincipal.getChildAdapterPosition(view)).getIdtipohoja();
                String tipoHojaSelect = laLista.get(rvPrincipal.getChildAdapterPosition(view)).getTituloTipoHoja();
                Intent intent = new Intent(ActivityBuscarTipoHoja.this, ActivityLista.class);
                intent.putExtra("btnSeleccionado", "tipohoja");
                intent.putExtra("tipoHojaSelect", tipoHojaSelect);
                intent.putExtra("consultaSQL", "SELECT mal_codigo, mal_nombrecomun, mal_nombrecientifico FROM maleza WHERE mal_tipohoja = '" + idTipoHojaSelect + "' ORDER BY mal_nombrecomun");
                startActivity(intent);
            }
        });
        rvPrincipal.setAdapter(adaptador);

        if (rvPrincipal.getItemDecorationCount() == 0) { //Si no tiene divisor
            //Linea divisor de RecyclerView
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
            rvPrincipal.addItemDecoration(dividerItemDecoration);
        }
    }

    private ArrayList<ItemBuscarTipoHoja> ConsultaBD(String consultaSQL) {
        DAO DAO = new DAO(this);
        DAO.Abrir();
        Cursor cursor = DAO.EjecutarSQL(consultaSQL);
        ArrayList<ItemBuscarTipoHoja> listItems = new ArrayList<>();

        while (cursor.moveToNext() == true) {
            String idtipohoja = cursor.getString(0); //Columna 0
            String descripcion = cursor.getString(1); //Columna 1
            String detalle = cursor.getString(2); //Columna 2

            listItems.add(new ItemBuscarTipoHoja(idtipohoja, descripcion, detalle));
        }
        cursor.close();
        DAO.Cerrar();

        return listItems;
    }

    public boolean onSupportNavigateUp() { //Para que retroceda al activity anterior
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
            public void onAdClosed() {
                // Código a ejecutar cuando el usuario está a punto de regresar
                // a la aplicación después de pulsar en un anuncio.
            }
        });
    }
}
