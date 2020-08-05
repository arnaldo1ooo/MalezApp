package com.arnaldo.malezapp;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.arnaldo.malezapp.conexion.Conexion;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.regex.Pattern;

public class ActivityDetalle extends AppCompatActivity {
    private ImageView ivImagen1;
    private ImageView ivImagen2;
    private ImageView ivImagen3;
    private TextView tvTituloMaleza;
    private TextView tvNombreComun2;
    private TextView tvNombreCientifico2;
    private TextView tvFamilia2;
    private TextView tvReconocidoPor2;
    private TextView tvDescripcion2;
    private TextView tvCiclo2;
    private TextView tvEcologia2;
    private TextView tvDistribucion2;
    private TextView tvEspeciesSimi2;
    private TextView tvTipoHoja2;
    private TextView tvResistencia;
    private TextView tvResistencia2;
    private AdView adView;

    private String codigoSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Activar boton atras
        getSupportActionBar().setDisplayShowHomeEnabled(true); //Activar icono en actionbar
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_round); //Asignar icono

        ivImagen1 = findViewById(R.id.ivImagen1);
        ivImagen2 = findViewById(R.id.ivImagen2);
        ivImagen3 = findViewById(R.id.ivImagen3);
        tvTituloMaleza = findViewById(R.id.tvTituloMaleza);
        tvNombreComun2 = findViewById(R.id.tvNombreComun2);
        tvNombreCientifico2 = findViewById(R.id.tvNombreCientifico2);
        tvFamilia2 = findViewById(R.id.tvFamilia2);
        tvReconocidoPor2 = findViewById(R.id.tvReconocidoPor2);
        tvDescripcion2 = findViewById(R.id.tvDescripcion2);
        tvCiclo2 = findViewById(R.id.tvCiclo2);
        tvEcologia2 = findViewById(R.id.tvEcologia2);
        tvDistribucion2 = findViewById(R.id.tvDistribucion2);
        tvEspeciesSimi2 = findViewById(R.id.tvEspeciesSimi2);
        tvTipoHoja2 = findViewById(R.id.tvTipoHoja2);
        tvResistencia =findViewById(R.id.tvResistencia);
        tvResistencia2 =findViewById(R.id.tvResistencia2);

        codigoSeleccionado = getIntent().getExtras().getString("codigoSeleccionado"); //Codigo del registro seleccionado en la lista

        ObtenerDetalles();

        if (tvResistencia2.getText().equals("-") == false){ //Poner rojo si tiene resistencia
            tvResistencia.setBackgroundColor(Color.parseColor("#910031"));
        }

        Banner();


        ivImagen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagenPopup(v, ivImagen1);
            }
        });

        ivImagen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagenPopup(v, ivImagen2);
            }
        });

        ivImagen3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagenPopup(v, ivImagen3);
            }
        });


    }

    private void ObtenerDetalles() {
        Conexion conexion = new Conexion(this);
        conexion.Abrir();
        Cursor cursor = conexion.EjecutarSQL("SELECT mal_codigo, mal_nombrecomun, mal_nombrecientifico, fam_descripcion, mal_reconocidapor, " +
                "mal_descripcion, mal_ciclos, mal_ecologia, mal_distribucion, mal_especiessimilares, th_descripcion, mal_resistencia " +
                "FROM maleza, familia, tipo_hoja " +
                "WHERE mal_familia=fam_codigo AND mal_tipohoja = th_codigo AND mal_codigo=" + codigoSeleccionado);

        int idimagen1;
        int idimagen2;
        int idimagen3;
        while (cursor.moveToNext()) {
            idimagen1 = getResources().getIdentifier("imagen_" + cursor.getString(0)+"a", "drawable", getPackageName());
            System.out.println(idimagen1);
            idimagen2 = getResources().getIdentifier("imagen_" + cursor.getString(0)+"b", "drawable", getPackageName());
            System.out.println(idimagen2);
            idimagen3 = getResources().getIdentifier("imagen_" + cursor.getString(0)+"c", "drawable", getPackageName());
            System.out.println(idimagen3);

            if (idimagen1 == 0) { //Si imagen no existe
                idimagen1 = getResources().getIdentifier("imagen_" + 0, "drawable", getPackageName());
                ivImagen1.setImageResource(idimagen1);
            } else {
                ivImagen1.setImageResource(idimagen1);
            }

            if (idimagen2 == 0) { //Si imagen no existe
                idimagen2 = getResources().getIdentifier("imagen_" + 0, "drawable", getPackageName());
                ivImagen2.setImageResource(idimagen2);
            } else {
                ivImagen2.setImageResource(idimagen2);
            }

            if (idimagen3 == 0) { //Si imagen no existe
                idimagen3 = getResources().getIdentifier("imagen_" + 0, "drawable", getPackageName());
                ivImagen3.setImageResource(idimagen3);
            } else {
                ivImagen3.setImageResource(idimagen3);
            }


            /*Picasso.get() //Cargar desde internet
                    .load("https://i.pinimg.com/originals/c3/2e/fd/c32efd34d386c758da328d225265a91f.png") //Link de la imagen
                    .placeholder(R.id.Laimagen) //La imagen que aparecera mientras se carga la imagen del link
                    .error(R.id.Laimagen) //La imagen que aparecera en caso de error
                    .into(ElImageView); //El ImageView que recibira la imagen*/



            tvNombreComun2.setText(cursor.getString(1));
            tvNombreCientifico2.setText(cursor.getString(2));
            tvFamilia2.setText(cursor.getString(3));
            tvReconocidoPor2.setText(cursor.getString(4));
            tvDescripcion2.setText(cursor.getString(5));
            tvCiclo2.setText(cursor.getString(6));
            tvEcologia2.setText(cursor.getString(7));
            tvDistribucion2.setText(cursor.getString(8));
            tvEspeciesSimi2.setText(cursor.getString(9));
            tvTipoHoja2.setText(cursor.getString(10));
            tvResistencia2.setText(cursor.getString(11));

            String elstring = tvNombreComun2.getText().toString();
            String separador = Pattern.quote(", "); //El caracter en dodne se cortara
            String[] stringsplit = elstring.split(separador);
            tvTituloMaleza.setText(stringsplit[0]);
        }
        cursor.close();
        conexion.Cerrar();
    }


    public void ImagenPopup(View view, ImageView laImagen){
        final ImagePopup imagePopup = new ImagePopup(this);
        imagePopup.setWindowHeight(800); // Optional
        imagePopup.setWindowWidth(800); // Optional
        imagePopup.setBackgroundColor(Color.BLACK);  // Color del fondo
        imagePopup.setFullScreen(true); // Pantalla completa
        imagePopup.setHideCloseIcon(true);  // Ocultar Boton cerrar
        imagePopup.setImageOnClickClose(false);  //Cerrar al tocar imagen
        imagePopup.initiatePopup(laImagen.getDrawable()); // Cargar la imagen
        imagePopup.viewPopup(); //Iniciar Popup
    }

    private void Banner() {
        adView = findViewById(R.id.adView1);
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
