package com.arnaldo.malezapp.lista;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
    private RecyclerView rvPrincipal;
    private RecyclerView.Adapter adaptador;
    private LinearLayoutManager layoutManager;
    private AdView adView;
    private String consultaSQL;
    private TextView tvTituloLista;
    private FragmentListaBuscarNomCom fragmentListaBuscarNomCom;
    private String btnSeleccionado = "";
    public String dptoSeleccionado = "";
    public String familiaSeleccionado = "";
    private Bundle bundle;
    private ArrayList<ItemLista> listaFiltrada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Boton atras
        getSupportActionBar().setDisplayShowHomeEnabled(true); //Activar icono en actionbar
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_round); //Asignar icono

        rvPrincipal = findViewById(R.id.rvPrincipal);
        tvTituloLista = findViewById(R.id.tvTituloLista);
        fragmentListaBuscarNomCom = new FragmentListaBuscarNomCom();


        try { //Recibe el boton seleccionado
            btnSeleccionado = getIntent().getExtras().getString("btnSeleccionado");
            dptoSeleccionado = getIntent().getExtras().getString("dptoseleccionado");
            familiaSeleccionado = getIntent().getExtras().getString("familiaseleccionado");
        } catch (Exception e) { //Si no se seleccionó ningun boton
            e.getStackTrace();
        }


        try {
            consultaSQL = getIntent().getExtras().getString("consultaSQL").toString();
            tvTituloLista.setText("Malezas encontradas");
        } catch (Exception e) {
            tvTituloLista.setText("Lista completa de malezas");
            consultaSQL = "SELECT mal_codigo, mal_nombrecomun, mal_nombrecientifico " +
                    "FROM maleza, familia WHERE mal_familia=fam_codigo ORDER BY mal_nombrecomun";
        }

        CargarConsultaaRV(ConsultaBD(consultaSQL));
        tvTituloLista.setText(rvPrincipal.getAdapter().getItemCount() + " malezas encontradas");

        bundle = new Bundle();
        bundle.putSerializable("lista", listItems); //Cargamos en el paquete la listatotal
        switch (btnSeleccionado) {
            case "nombrecomun":
                FragmentListaBuscarNomCom fragmentListaBuscarNomCom = new FragmentListaBuscarNomCom();
                fragmentListaBuscarNomCom.setArguments(bundle);
                MostrarFragmentSeleccionado(fragmentListaBuscarNomCom, R.id.flFragments);
                break;
            case "nombrecientifico":
                FragmentListaBuscarNomCien fragmentListaBuscarNomCien = new FragmentListaBuscarNomCien();
                fragmentListaBuscarNomCien.setArguments(bundle);
                MostrarFragmentSeleccionado(fragmentListaBuscarNomCien, R.id.flFragments);
                break;
            case "familia":
                FragmentListaBuscarFamilia fragmentListaBuscarFamilia = new FragmentListaBuscarFamilia();
                fragmentListaBuscarFamilia.setArguments(bundle);
                MostrarFragmentSeleccionado(fragmentListaBuscarFamilia, R.id.flFragments);

                if (familiaSeleccionado.equals("TODOS") == false) {
                    listaFiltrada = (ConsultaBD("SELECT mal_codigo, mal_nombrecomun, mal_nombrecientifico " +
                            "FROM maleza, familia WHERE mal_familia = fam_codigo AND fam_descripcion = '" + familiaSeleccionado + "' ORDER BY mal_nombrecomun"));
                    System.out.println("listaFiltrada.size() " + listaFiltrada.size());
                } else {
                    listaFiltrada = (ConsultaBD("SELECT mal_codigo, mal_nombrecomun, mal_nombrecientifico " +
                            "FROM maleza ORDER BY mal_nombrecomun"));
                }
                CargarConsultaaRV(listaFiltrada);
                tvTituloLista.setText(listaFiltrada.size() + " Malezas encontradas");
                break;
            case "ciclo":
                FragmentListaBuscarCiclo fragmentListaBuscarCiclo = new FragmentListaBuscarCiclo();
                fragmentListaBuscarCiclo.setArguments(bundle);
                MostrarFragmentSeleccionado(fragmentListaBuscarCiclo, R.id.flFragments);
                break;
            case "tipoespecie":
                FragmentListaBuscarTipoEspecie fragmentListaBuscarTipoEspecie = new FragmentListaBuscarTipoEspecie();
                fragmentListaBuscarTipoEspecie.setArguments(bundle);
                MostrarFragmentSeleccionado(fragmentListaBuscarTipoEspecie, R.id.flFragments);
                break;
            case "distribucion":
                FragmentListaBuscarDistribucion fragmentListaBuscarDistribucion = new FragmentListaBuscarDistribucion();
                fragmentListaBuscarDistribucion.setArguments(bundle);
                if (dptoSeleccionado.equals("TODOS") == false) {
                    listaFiltrada = (ConsultaBD("SELECT mal_codigo, mal_nombrecomun, mal_nombrecientifico " +
                            "FROM maleza, departamento, maleza_departamento WHERE mal_codigo = maldep_maleza AND dep_codigo = maldep_departamento AND " +
                            "dep_descripcion = '" + dptoSeleccionado + "' GROUP BY mal_codigo ORDER BY mal_nombrecomun"));
                    System.out.println("listaFiltrada.size() " + listaFiltrada.size());
                } else {
                    listaFiltrada = (ConsultaBD("SELECT mal_codigo, mal_nombrecomun, mal_nombrecientifico " +
                            "FROM maleza ORDER BY mal_nombrecomun"));
                }
                CargarConsultaaRV(listaFiltrada);
                tvTituloLista.setText(listaFiltrada.size() + " Malezas encontradas");

                MostrarFragmentSeleccionado(fragmentListaBuscarDistribucion, R.id.flFragments);
                break;
            default:
                //JOptionPane.showMessageDialog(this, "No se seleccionó ninguno", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }

        Banner();
    }

    public void CargarConsultaaRV(ArrayList<ItemLista> laLista) {
        //Se crea el recyclerview y adaptador
        layoutManager = new LinearLayoutManager(this);
        rvPrincipal.setLayoutManager(layoutManager);
        //Adaptador
        adaptador = new AdaptadorLista(this, laLista);

        //OnClick
        ((AdaptadorLista) adaptador).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guardo el codigo del registro seleccionado
                String codSelect = laLista.get(rvPrincipal.getChildAdapterPosition(view)).getCodigo();

                Intent intent = new Intent(ActivityLista.this, ActivityDetalle.class);
                intent.putExtra("codigoSeleccionado", codSelect);
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

    Conexion conexion;
    ArrayList<ItemLista> listItems;

    public ArrayList<ItemLista> ConsultaBD(String consultaSQL) {
        conexion = new Conexion(this);
        conexion.Abrir();
        Cursor cursor = conexion.EjecutarSQL(consultaSQL);
        int cantnombres;
        listItems = new ArrayList<>();
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
                listItems.add(new ItemLista(codigo, R.drawable.imagen_0, nombrecomun, nombrecientifico));
            } else {
                listItems.add(new ItemLista(codigo, idimagen, nombrecomun, nombrecientifico));
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

    private void MostrarFragmentSeleccionado(Fragment fragment, int elContenedor) {
        this.getSupportFragmentManager().beginTransaction().replace(elContenedor, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
