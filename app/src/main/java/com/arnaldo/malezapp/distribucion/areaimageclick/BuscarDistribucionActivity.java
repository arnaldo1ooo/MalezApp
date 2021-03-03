package com.arnaldo.malezapp.distribucion.areaimageclick;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.arnaldo.malezapp.R;
import com.arnaldo.malezapp.conexion.Conexion;
import com.arnaldo.malezapp.lista.ListaActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

public class BuscarDistribucionActivity extends AppCompatActivity implements OnClickableAreaClickedListener {

    private ImageView ivMapa;
    private ListView lvdpto;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference(); //Abrir conexion con storage de firestore

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscar_distribucion_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Activar boton atras
        getSupportActionBar().setDisplayShowHomeEnabled(true); //Activar icono en actionbar
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_round); //Asignar icono


        ivMapa = findViewById(R.id.ivMapa);
        lvdpto = findViewById(R.id.lvDpto);

        Tab();
        CargarLista("SELECT dep_descripcion FROM departamento ORDER BY dep_descripcion", lvdpto);
        CrearImagenAreaClickable(ivMapa);

              lvdpto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(v.getContext(), ListaActivity.class);
                intent.putExtra("btnSeleccionado", "distribucion");
                intent.putExtra("dptoseleccionado", lvdpto.getItemAtPosition(position).toString());
                startActivityForResult(intent, 0);
            }
        });
    }

    private void Tab() {
        Resources res = getResources();
        TabHost tabs = findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec tbSpec = tabs.newTabSpec("mitab1");
        tbSpec.setContent(R.id.tab1);
        tbSpec.setIndicator("Mapa", null/*res.getDrawable(android.R.drawable.ic_btn_speak_now)*/); //El titulo y icono
        tabs.addTab(tbSpec);

        tbSpec = tabs.newTabSpec("mitab2");
        tbSpec.setContent(R.id.tab2);
        tbSpec.setIndicator("Lista", null/*res.getDrawable(android.R.drawable.ic_dialog_map)*/);
        tabs.addTab(tbSpec);
        tabs.setCurrentTab(0);
    }

    public void CargarLista(String consultaSQL, ListView laLista) {
        Conexion conexion = new Conexion(this);
        conexion.Abrir();
        ArrayList<String> lista = new ArrayList<>();
        Cursor cursor = conexion.EjecutarSQL(consultaSQL);
        laLista.removeAllViews();
        lista.add("TODOS");
        while (cursor.moveToNext() == true) {
            lista.add(cursor.getString(0));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        laLista.setAdapter(adapter);
        cursor.close();
        conexion.Cerrar();
    }


    private void CrearImagenAreaClickable(ImageView laImagen) {
        ClickableAreasImage clickableAreasImage = new ClickableAreasImage(new PhotoViewAttacher(laImagen), this);
        List<ClickableArea> clickableAreas = AreasClickables();
        clickableAreasImage.setClickableAreas(clickableAreas);
    }

    // Define your clickable area (pixel values: x coordinate, y coordinate, width, height) and assign an object to it
    @NonNull
    private List<ClickableArea> AreasClickables() {
        //Se elije un punto XY, a partir de ese punto se trasa una linea horizontal(w) y vertical(h) para formar un cuadrado clickable
        List<ClickableArea> areasClickables = new ArrayList<>();
        areasClickables.add(new ClickableArea(67, 215, 306, 424, new ItemClickDptos("BOQUERÓN")));
        areasClickables.add(new ClickableArea(403, 64, 267, 372, new ItemClickDptos("ALTO PARAGUAY")));
        areasClickables.add(new ClickableArea(383, 554, 321, 283, new ItemClickDptos("PRESIDENTE HAYES")));
        areasClickables.add(new ClickableArea(698, 461, 175, 180, new ItemClickDptos("CONCEPCIÓN")));
        areasClickables.add(new ClickableArea(934, 470, 89, 210, new ItemClickDptos("AMAMBAY")));
        areasClickables.add(new ClickableArea(776, 652, 161, 211, new ItemClickDptos("SAN PEDRO")));
        areasClickables.add(new ClickableArea(949, 742, 247, 70, new ItemClickDptos("CANINDEYÚ")));
        areasClickables.add(new ClickableArea(772, 890, 95, 58, new ItemClickDptos("CORDILLERA")));
        areasClickables.add(new ClickableArea(884, 881, 165, 75, new ItemClickDptos("CAAGUAZÚ")));
        areasClickables.add(new ClickableArea(1060, 845, 116, 175, new ItemClickDptos("ALTO PARANÁ")));
        areasClickables.add(new ClickableArea(1046, 959, 94, 123, new ItemClickDptos("ALTO PARANÁ")));
        areasClickables.add(new ClickableArea(731, 917, 18, 118, new ItemClickDptos("CENTRAL")));
        areasClickables.add(new ClickableArea(749, 995, 97, 115, new ItemClickDptos("PARAGUARÍ")));
        areasClickables.add(new ClickableArea(859, 994, 101, 44, new ItemClickDptos("GUAIRÁ")));
        areasClickables.add(new ClickableArea(850, 1078, 166, 34, new ItemClickDptos("CAAZAPÁ")));
        areasClickables.add(new ClickableArea(634, 1088, 70, 169, new ItemClickDptos("ÑEEMBUCÚ")));
        areasClickables.add(new ClickableArea(757, 1139, 100, 145, new ItemClickDptos("MISIONES")));
        areasClickables.add(new ClickableArea(865, 1168, 197, 73, new ItemClickDptos("ITAPÚA")));

        return areasClickables;
    }

    // Escucha los toques por la imagen y devuelve el dato de ese lugar
    @Override
    public void onClickableAreaTouched(Object item) {
        if (item instanceof ItemClickDptos) { //Compara
            String itemSelect = ((ItemClickDptos) item).getName();
            switch (itemSelect){
                case "ALTO PARANÁ":{
                    CargarLista("SELECT dis_descripcion FROM distrito, departamento WHERE dis_departamento=dep_codigo ORDER BY dis_descripcion", lvdpto);

                    //Obtener imagen1 desde internet
                    String rutaImagen1 = "mapas/mapa_dpto_altoparana.png";
                    System.out.println("rutaImagen1: " + rutaImagen1);
                    storageReference.child(rutaImagen1).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri laUri) {
                            System.out.println("Suceso " + rutaImagen1);
                            Picasso.get() //Cargar desde internet
                                    .load(laUri) //Link de la imagen
                                    .placeholder(R.drawable.cargando) //La imagen que aparecera mientras se carga la imagen del link
                                    .error(R.drawable.imagen_0) //La imagen que aparecera en caso de error
                                    .into(ivMapa); //El ImageView que recibira la imagen
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            System.out.println("Fallo " + rutaImagen1);
                        }
                    });
                    break;
                }

                default:{
                    System.out.println("No se encontro Switch " + itemSelect);
                    break;
                }

            }
            /*Intent intent = new Intent(getApplicationContext(), ListaActivity.class);
            intent.putExtra("btnSeleccionado", "distribucion");
            intent.putExtra("dptoseleccionado", itemSelect);
            startActivityForResult(intent, 0);*/
            //Toast.makeText(this, metodos.SacarAcentos(itemSelect.toUpperCase()), Toast.LENGTH_SHORT).show();
        }
    }

    //Para que al oprimir en boton subir, retroceda al ultimo activity
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}