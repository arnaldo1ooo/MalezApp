package com.arnaldo.malezapp.lista;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arnaldo.malezapp.DetalleActivity;
import com.arnaldo.malezapp.R;
import com.arnaldo.malezapp.conexion.Conexion;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ListaActivity extends AppCompatActivity {
    private RecyclerView rvPrincipal;
    private String consultaSQL;
    private TextView tvTituloLista;
    private String activitySelect = "allmalezas";
    public String tipoHojaSelect = "";
    public String dptoSeleccionado = "";
    public String familiaSeleccionado = "";
    private Bundle elBundle;
    private ArrayList<ListaItem> listItems;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Boton atras
        getSupportActionBar().setDisplayShowHomeEnabled(true); //Activar icono en actionbar
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_round); //Asignar icono

        rvPrincipal = findViewById(R.id.rvPrincipal);
        tvTituloLista = findViewById(R.id.tvTituloLista);

        CargarAllMalezas();

        try { //Recibe el boton seleccionado
            activitySelect = getIntent().getExtras().getString("btnSeleccionado");

            ArrayList<ListaItem> listaFiltrada;
            switch (activitySelect) {
                case "nombrecomun": {
                    //Enviar la lista a los fragment
                    elBundle = new Bundle(); //Paquete
                    elBundle.putSerializable("lista", listItems); //Cargamos en el paquete la listatotal, este se envia a los fragments, recibe la lista total de malezas para luego filtrarlo

                    FiltroNomComFragment filtroNomComFragment = new FiltroNomComFragment();
                    filtroNomComFragment.setArguments(elBundle);
                    MostrarFragmentSeleccionado(filtroNomComFragment, R.id.flFragments);
                    break;
                }
                case "nombrecientifico": {
                    elBundle = new Bundle(); //Paquete
                    elBundle.putSerializable("lista", listItems);

                    FiltroNomCienFragment filtroNomCienFragment = new FiltroNomCienFragment();
                    filtroNomCienFragment.setArguments(elBundle);
                    MostrarFragmentSeleccionado(filtroNomCienFragment, R.id.flFragments);
                    break;
                }
                case "ciclo": {
                    elBundle = new Bundle(); //Paquete
                    elBundle.putSerializable("lista", listItems);

                    FiltroCicloFragment filtroCicloFragment = new FiltroCicloFragment();
                    filtroCicloFragment.setArguments(elBundle);
                    MostrarFragmentSeleccionado(filtroCicloFragment, R.id.flFragments);
                    break;
                }
                case "tipoespecie": {
                    elBundle = new Bundle(); //Paquete
                    elBundle.putSerializable("lista", listItems);

                    FiltroTipoEspecieFragment filtroTipoEspecieFragment = new FiltroTipoEspecieFragment();
                    filtroTipoEspecieFragment.setArguments(elBundle);
                    MostrarFragmentSeleccionado(filtroTipoEspecieFragment, R.id.flFragments);
                    break;
                }
                case "familia": {
                    FiltroFamiliaFragment filtroFamiliaFragment = new FiltroFamiliaFragment();
                    filtroFamiliaFragment.setArguments(elBundle);
                    MostrarFragmentSeleccionado(filtroFamiliaFragment, R.id.flFragments);

                    familiaSeleccionado = getIntent().getExtras().getString("familiaseleccionado");
                    if (!familiaSeleccionado.equals("TODOS")) {
                        listaFiltrada = (ConsultaBD("SELECT mal_codigo, mal_nombrecomun, mal_nombrecientifico " +
                                "FROM maleza, familia WHERE mal_familia = fam_codigo AND fam_descripcion = '" + familiaSeleccionado + "' ORDER BY mal_nombrecomun"));
                    } else {
                        listaFiltrada = (ConsultaBD("SELECT mal_codigo, mal_nombrecomun, mal_nombrecientifico " +
                                "FROM maleza ORDER BY mal_nombrecomun"));
                    }
                    CargarConsultaaRV(listaFiltrada);
                    tvTituloLista.setText(listaFiltrada.size() + " Malezas encontradas");
                    break;
                }
                case "distribucion": {
                    dptoSeleccionado = getIntent().getExtras().getString("dptoseleccionado"); //Obtenemos el dpto seleccionado
                    FiltroDistribucionFragment filtroDistribucionFragment = new FiltroDistribucionFragment();
                    filtroDistribucionFragment.setArguments(elBundle);
                    MostrarFragmentSeleccionado(filtroDistribucionFragment, R.id.flFragments);

                    if (!dptoSeleccionado.equals("TODOS")) {
                        listaFiltrada = (ConsultaBD("SELECT mal_codigo, mal_nombrecomun, mal_nombrecientifico " +
                                "FROM maleza, departamento, maleza_departamento WHERE mal_codigo = maldep_maleza AND dep_codigo = maldep_departamento AND " +
                                "dep_descripcion = '" + dptoSeleccionado + "' GROUP BY mal_codigo ORDER BY mal_nombrecomun"));
                    } else {
                        listaFiltrada = (ConsultaBD("SELECT mal_codigo, mal_nombrecomun, mal_nombrecientifico " +
                                "FROM maleza ORDER BY mal_nombrecomun"));
                    }
                    CargarConsultaaRV(listaFiltrada);
                    tvTituloLista.setText(listaFiltrada.size() + " Malezas encontradas");
                    break;
                }
                case "tipohoja": {
                    tipoHojaSelect = getIntent().getExtras().getString("tipoHojaSelect"); //Recibir el id seleccionado
                    consultaSQL = getIntent().getExtras().getString("consultaSQL");
                    elBundle = new Bundle(); //Paquete
                    elBundle.putSerializable("tipoHojaSelect", tipoHojaSelect); //Enviar a Fragment

                    FiltroTipoHojaFragment filtroTipoHojaFragment = new FiltroTipoHojaFragment();
                    filtroTipoHojaFragment.setArguments(elBundle);
                    MostrarFragmentSeleccionado(filtroTipoHojaFragment, R.id.flFragments);

                    listaFiltrada = (ConsultaBD(consultaSQL));
                    CargarConsultaaRV(listaFiltrada);
                    tvTituloLista.setText(listaFiltrada.size() + " Malezas encontradas");
                    break;
                }
                default: {
                    System.out.println("Error switch, activitySelect: " + activitySelect);
                    Toast.makeText(getApplicationContext(), "Error switch, activitySelect: " + activitySelect, Toast.LENGTH_LONG).show();
                    break;
                }
            }
        } catch (Exception e) { //Si no se seleccionó ningun boton
            e.getStackTrace();
        }

        Banner();
    }

    private void CargarAllMalezas() {
        consultaSQL = "SELECT mal_codigo, mal_nombrecomun, mal_nombrecientifico FROM maleza, familia WHERE mal_familia=fam_codigo ORDER BY mal_nombrecomun"; //Por defecto
        CargarConsultaaRV(ConsultaBD(consultaSQL));
        tvTituloLista.setText(rvPrincipal.getAdapter().getItemCount() + " malezas encontradas");
    }

    public void CargarConsultaaRV(ArrayList<ListaItem> laLista) {
        //Se crea el recyclerview y adaptador
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvPrincipal.setLayoutManager(layoutManager);
        //Adaptador
        RecyclerView.Adapter adaptador = new ListaAdaptador(this, laLista);

        //OnClick
        ((ListaAdaptador) adaptador).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guardo el codigo del registro seleccionado
                String codSelect = laLista.get(rvPrincipal.getChildAdapterPosition(view)).getCodigo();

                Intent intent = new Intent(ListaActivity.this, DetalleActivity.class);
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


    public ArrayList<ListaItem> ConsultaBD(String consultaSQL) {
        Conexion conexion = new Conexion(this);
        conexion.Abrir();
        Cursor cursor = conexion.EjecutarSQL(consultaSQL);
        int cantnombres;
        listItems = new ArrayList<>();

        while (cursor.moveToNext()) {
            String idmaleza = cursor.getString(0); //Columna 0

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


            listItems.add(new ListaItem(idmaleza, R.drawable.imagen_0, nombrecomun, nombrecientifico));
        }
        cursor.close();
        conexion.Cerrar();

        return listItems;
    }


    public boolean onSupportNavigateUp() {//Que retroceda al ultimo activity
        onBackPressed();
        return false;
    }

    private void Banner() {
        AdView adView = findViewById(R.id.adView2);
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

    private void MostrarFragmentSeleccionado(Fragment fragment, int elContenedor) {
        this.getSupportFragmentManager().beginTransaction().replace(elContenedor, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }


    //Creamos el menu del actionbar
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista, menu); //Cargamos el menu

        return super.onCreateOptionsMenu(menu);
    }

    //Items del menu del actionbar
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.buscador_menu: {


                return true;
            }

            case R.id.info_menu: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                Spanned elMensaje = Html.fromHtml("");
                switch (activitySelect) {
                    case "allmalezas": {
                        elMensaje = Html.fromHtml("La maleza, mala hierba, hierba mala, yuyo, monte o planta indeseable es cualquier vegetal que crece de forma silvestre en una " +
                                "zona cultivada o controlada por el ser humano como cultivos agrícolas o jardines.");
                        break;
                    }

                    case "nombrecomun": {
                        elMensaje = Html.fromHtml("El nombre común, popular o vulgar es aquel que es asignado por la tradición popular. " +
                                "Puede variar de un país a otro, entre regiones e incluso entre localidades cercanas. Por este motivo, a veces es muy difícil saber de qué planta se " +
                                "está hablando usando sólo el nombre común.<br><br>" +
                                "Ejemplo: a la Chorisia speciosa se la llama con los nombres comunes de Palo Borracho, Árbol botella, Árbol de la lana, Palo rosado y Samohu.");
                        break;
                    }

                    case "nombrecientifico": {
                        elMensaje = Html.fromHtml("El nombre científico. botánico o técnico es aquel que está normalmente compuesto por dos palabras latinas (procedentes del latín)." +
                                "Es universal, esto es, es el mismo en todos los lugares del mundo y no suele cambiar a menos que se descubran, tras haber realizado estudios minuciosos, " +
                                "que realmente esa planta en concreto pertenece a un género distinto o que es incluso una especie nueva.<br><br>" +
                                "Ejemplos: Cycas revoluta, Phoenix canariensis, Rebutia minuscula.");
                        break;
                    }

                    case "familia": {
                        elMensaje = Html.fromHtml("El Reino Vegetal está compuesto por 400.000 especies conocidas y aceptadas. Cada una de ellas tiene sus propias características que " +
                                "la hacen única; sin embargo, al igual que pasa con las familias de los animales, ellas también tienen »primos» con los que comparten parte de su genética. " +
                                "Así pues, para diferenciarlas de las demás los botánicos las agrupan en familias. Sus miembros puede que tengan el mismo tipo de flor, o las mismas hojas " +
                                "o el mismo fruto, pero se diferencian del resto de plantas por todo lo demás");
                        break;
                    }

                    case "ciclo": {
                        Conexion conexion = new Conexion(this);
                        conexion.Abrir();
                        String consultaSQL = "SELECT ci_descripcion, ci_detalle FROM ciclo ORDER BY ci_descripcion";
                        Cursor cursor = conexion.EjecutarSQL(consultaSQL);
                        String descri = "";
                        String detalle = "";
                        String elMensajeString = "";
                        while (cursor.moveToNext()) {
                            descri = cursor.getString(0); //Columna 0
                            detalle = cursor.getString(1); //Columna 1

                            elMensajeString = elMensajeString + " <b>" + descri + ":</b> " + detalle + "<br><br> ";
                        }
                        elMensaje = Html.fromHtml(elMensajeString);
                        conexion.Cerrar();
                        break;

                    }

                    case "tipoespecie": {
                        Conexion conexion = new Conexion(this);
                        conexion.Abrir();
                        String consultaSQL = "SELECT te_descripcion, te_detalle FROM tipo_especie ORDER BY te_descripcion";
                        Cursor cursor = conexion.EjecutarSQL(consultaSQL);
                        String descri = "";
                        String detalle = "";
                        String elMensajeString = "";
                        while (cursor.moveToNext()) {
                            descri = cursor.getString(0); //Columna 0
                            detalle = cursor.getString(1); //Columna 1

                            elMensajeString = elMensajeString + " <b>" + descri + ":</b> " + detalle + "<br><br> ";
                        }
                        elMensaje = Html.fromHtml(elMensajeString);
                        conexion.Cerrar();
                        break;
                    }

                    case "distribucion": {
                        elMensaje = Html.fromHtml("La maleza, mala hierba, hierba mala, yuyo, monte o planta indeseable es cualquier vegetal que crece de forma silvestre en una " +
                                "zona cultivada o controlada por el ser humano como cultivos agrícolas o jardines.");
                        break;
                    }

                    case "tipohoja": {
                        Conexion conexion = new Conexion(this);
                        conexion.Abrir();
                        String consultaSQL = "SELECT th_descripcion, th_detalle FROM tipo_hoja WHERE th_descripcion='" + tipoHojaSelect + "'";
                        Cursor cursor = conexion.EjecutarSQL(consultaSQL);
                        String descri = "";
                        String detalle = "";
                        if (cursor.moveToNext()) {
                            descri = cursor.getString(0); //Columna 0
                            detalle = cursor.getString(1); //Columna 1
                        }
                        conexion.Cerrar();
                        elMensaje = Html.fromHtml("<b>" + descri + ":</b> " + detalle);
                        break;
                    }

                    default: {
                        System.out.println("Error switch " + activitySelect);
                        builder.setTitle("Error");
                        builder.setMessage("Error");
                        break;
                    }
                }
                builder.setTitle("Guía de malezas");
                builder.setMessage(elMensaje);
                builder.setIcon(R.drawable.ic_info_black_50);
                builder.setPositiveButton("Aceptar", null);
                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            }

            default: {
                System.out.println("Error en menu actionbar " + item.getItemId());
                return super.onOptionsItemSelected(item);
            }
        }
    }
}
