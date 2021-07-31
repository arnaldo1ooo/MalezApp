package com.arnaldo.malezapp;

import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.arnaldo.malezapp.dao.DAO;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.regex.Pattern;

public class ActivityDetalle extends AppCompatActivity {
    private ImageView ivImagen1;
    private ImageView ivImagen2;
    private ImageView ivImagen3;
    private ImageView ivImagen4;
    private TextView tvTituloMaleza;
    private TextView tvNombreComun2;
    private TextView tvSinonimo2;
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

    private String idSelectMaleza;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference(); //Abrir conexion con storage de firestore

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
        ivImagen4 = findViewById(R.id.ivImagen4);
        tvTituloMaleza = findViewById(R.id.tvTituloMaleza);
        tvNombreComun2 = findViewById(R.id.tvNombreComun2);
        tvSinonimo2 = findViewById(R.id.tvSinonimo2);
        tvNombreCientifico2 = findViewById(R.id.tvNombreCientifico2);
        tvFamilia2 = findViewById(R.id.tvFamilia2);
        tvReconocidoPor2 = findViewById(R.id.tvReconocidoPor2);
        tvDescripcion2 = findViewById(R.id.tvDescripcion2);
        tvCiclo2 = findViewById(R.id.tvCiclo2);
        tvEcologia2 = findViewById(R.id.tvEcologia2);
        tvDistribucion2 = findViewById(R.id.tvDistribucion2);
        tvEspeciesSimi2 = findViewById(R.id.tvEspeciesSimi2);
        tvTipoHoja2 = findViewById(R.id.tvTipoHoja2);
        tvResistencia = findViewById(R.id.tvResistencia);
        tvResistencia2 = findViewById(R.id.tvResistencia2);

        idSelectMaleza = getIntent().getExtras().getString("codigoSeleccionado"); //Codigo del registro seleccionado en la lista
        ObtenerDetalles();
        Banner();


        ivImagen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagenPopupPicasso(v, ivImagen1);
            }
        });

        ivImagen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagenPopupPicasso(v, ivImagen2);
            }
        });

        ivImagen3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagenPopupPicasso(v, ivImagen3);
            }
        });


    }

    private void ObtenerDetalles() {
        DAO DAO = new DAO(this);
        DAO.Abrir();
        try {
            Cursor cursor = DAO.EjecutarSQL("SELECT mal_codigo, mal_nombrecomun, mal_sinonimo, mal_nombrecientifico, fam_descripcion, mal_reconocidapor, mal_descripcion, mal_ciclos, mal_ecologia, \n" +
                    "(SELECT GROUP_CONCAT(dep_descripcion, ', ') FROM maleza_departamento, departamento WHERE maldep_maleza=" + idSelectMaleza + " AND maldep_departamento=dep_codigo) AS distribucion, mal_especiessimilares, th_descripcion, " +
                    "(SELECT GROUP_CONCAT(pa_descripcion, ', ') FROM maleza_resistencia, principio_activo WHERE mare_maleza=" + idSelectMaleza + " AND mare_principioactivo=pa_codigo) AS resistencia \n" +
                    "FROM maleza, familia, tipo_hoja \n" +
                    "WHERE fam_codigo=mal_familia AND th_codigo=mal_tipohoja AND mal_codigo=" + idSelectMaleza);

            if (cursor.moveToNext()) {
                ObtenerImagenes(cursor);
                tvNombreComun2.setText(cursor.getString(1));
                tvSinonimo2.setText(cursor.getString(2));
                tvNombreCientifico2.setText(cursor.getString(3));
                tvFamilia2.setText(cursor.getString(4));
                tvReconocidoPor2.setText(cursor.getString(5));
                tvDescripcion2.setText(cursor.getString(6));
                tvCiclo2.setText(cursor.getString(7));
                tvEcologia2.setText(cursor.getString(8));
                tvDistribucion2.setText(cursor.getString(9));
                tvEspeciesSimi2.setText(cursor.getString(10));
                tvTipoHoja2.setText(cursor.getString(11));
                tvResistencia2.setText(cursor.getString(12));
                if (tvResistencia2.getText().equals("")){
                    tvResistencia.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
                    tvResistencia2.setText("-");
                }else{ //Si hay resistencia poner en rojo titulo
                    tvResistencia.setBackgroundColor(Color.parseColor("#910031"));
                }

                String elstring = tvNombreComun2.getText().toString();
                String separador = Pattern.quote(", "); //El caracter en dodne se cortara
                String[] stringsplit = elstring.split(separador);
                tvTituloMaleza.setText(stringsplit[0]);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        DAO.Cerrar();
    }

    private void ObtenerImagenes(Cursor cursor) {
        int idmaleza = cursor.getInt(0);
        //Obtener imagen1 desde internet
        String rutaImagen1 = "imagenes_malezas/imagen_" + idmaleza + "a.jpg";
        System.out.println("rutaImagen1: " + rutaImagen1);
        storageReference.child(rutaImagen1).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri laUri) {
                System.out.println("Suceso " + rutaImagen1);
                Picasso.get() //Cargar desde internet
                        .load(laUri) //Link de la imagen
                        .placeholder(R.drawable.cargando) //La imagen que aparecera mientras se carga la imagen del link
                        .error(R.drawable.imagen_0) //La imagen que aparecera en caso de error
                        .into(ivImagen1); //El ImageView que recibira la imagen
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                System.out.println("Fallo " + rutaImagen1);
            }
        });

        //Obtener imagen2
        String rutaImagen2 = "imagenes_malezas/imagen_" + idmaleza + "b.jpg";
        System.out.println("rutaImagen2: " + rutaImagen2);
        storageReference.child(rutaImagen2).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri laUri) {
                System.out.println("Suceso " + rutaImagen2);
                Picasso.get() //Cargar desde internet
                        .load(laUri) //Link de la imagen
                        .placeholder(R.drawable.cargando) //La imagen que aparecera mientras se carga la imagen del link
                        .error(R.drawable.imagen_0) //La imagen que aparecera en caso de error
                        .into(ivImagen2); //El ImageView que recibira la imagen
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                System.out.println("Fallo " + rutaImagen2);
            }
        });

        //Obtener imagen3
        String rutaImagen3 = "imagenes_malezas/imagen_" + idmaleza + "c.jpg";
        System.out.println("rutaImagen3: " + rutaImagen3);
        storageReference.child(rutaImagen3).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri laUri) {
                System.out.println("Suceso " + rutaImagen3);
                Picasso.get() //Cargar desde internet
                        .load(laUri) //Link de la imagen
                        .placeholder(R.drawable.cargando) //La imagen que aparecera mientras se carga la imagen del link
                        .error(R.drawable.imagen_0) //La imagen que aparecera en caso de error
                        .into(ivImagen3); //El ImageView que recibira la imagen
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                System.out.println("Fallo " + rutaImagen3);
            }
        });

        //Obtener imagen4
        String rutaImagen4 = "imagenes_malezas/imagen_" + idmaleza + "d.jpg";
        System.out.println("rutaImagen4: " + rutaImagen4);
        storageReference.child(rutaImagen4).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri laUri) {
                System.out.println("Suceso " + rutaImagen4);
                Picasso.get() //Cargar desde internet
                        .load(laUri) //Link de la imagen
                        .placeholder(R.drawable.cargando) //La imagen que aparecera mientras se carga la imagen del link
                        .error(R.drawable.imagen_0) //La imagen que aparecera en caso de error
                        .into(ivImagen4); //El ImageView que recibira la imagen
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                System.out.println("Fallo " + rutaImagen4);
            }
        });
    }


    public void ImagenPopupPicasso(View view, ImageView laImagen) {
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

    //Para que al oprimir en boton subir, retroceda al ultimo activity
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
