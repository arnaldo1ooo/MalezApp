package com.arnaldo.malezapp.areaimageclick;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.arnaldo.malezapp.R;
import com.arnaldo.malezapp.conexion.Conexion;
import com.arnaldo.malezapp.lista.ActivityLista;
import com.arnaldo.malezapp.metodos.Metodos;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ActivityBuscarDistribucion extends AppCompatActivity implements OnClickableAreaClickedListener {

    private ImageView ivMapa;
    private ListView lvdpto;
    private PhotoViewAttacher attacher;
    private Metodos metodos = new Metodos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscardistribucion);

        ivMapa = findViewById(R.id.ivMapa);
        lvdpto = findViewById(R.id.lvDpto);

        Tab();
        CargarLista("SELECT dep_descripcion FROM departamento ORDER BY dep_descripcion", lvdpto);
        CrearImagenAreaClickable(ivMapa);


        lvdpto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(v.getContext(), ActivityLista.class);
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

    // Escucha los toques por la imagen y devuelve el dato de ese lugar
    @Override
    public void onClickableAreaTouched(Object item) {
        if (item instanceof ItemClickDptos) { //Compara
            String itemSelect = ((ItemClickDptos) item).getName();

            Toast.makeText(this, metodos.SacarAcentos(itemSelect.toUpperCase()), Toast.LENGTH_SHORT).show();
            System.out.println("itemSelect " + metodos.SacarAcentos(itemSelect.toUpperCase()));
        }
    }

    // Define your clickable area (pixel values: x coordinate, y coordinate, width, height) and assign an object to it
    @NonNull
    private List<ClickableArea> AreasClickables() {
        //Se elije un punto XY, a partir de ese punto se trasa una linea horizontal(w) y vertical(h) para formar un cuadrado clickable
        List<ClickableArea> areasClickables = new ArrayList<>();
        areasClickables.add(new ClickableArea(67, 215, 306, 424, new ItemClickDptos("Boquerón")));
        areasClickables.add(new ClickableArea(403, 64, 267, 372, new ItemClickDptos("Alto Paraguay")));
        areasClickables.add(new ClickableArea(383, 554, 321, 283, new ItemClickDptos("Presidente Hayes")));
        areasClickables.add(new ClickableArea(698, 461, 175, 180, new ItemClickDptos("Concepción")));
        areasClickables.add(new ClickableArea(934, 470, 89, 210, new ItemClickDptos("Amambay")));
        areasClickables.add(new ClickableArea(776, 652, 161, 211, new ItemClickDptos("San Pedro")));
        areasClickables.add(new ClickableArea(949, 742, 247, 70, new ItemClickDptos("Canindeyú")));
        areasClickables.add(new ClickableArea(772, 890, 95, 58, new ItemClickDptos("Cordillera")));
        areasClickables.add(new ClickableArea(884, 881, 165, 75, new ItemClickDptos("Caaguazú")));
        areasClickables.add(new ClickableArea(1060, 845, 116, 175, new ItemClickDptos("Alto Paraná")));
        areasClickables.add(new ClickableArea(1046, 959, 94, 123, new ItemClickDptos("Alto Paraná")));
        areasClickables.add(new ClickableArea(731, 917, 18, 118, new ItemClickDptos("Central")));
        areasClickables.add(new ClickableArea(749, 995, 97, 115, new ItemClickDptos("Paraguarí")));
        areasClickables.add(new ClickableArea(859, 994, 101, 44, new ItemClickDptos("Guairá")));
        areasClickables.add(new ClickableArea(850, 1078, 166, 34, new ItemClickDptos("Caazapá")));
        areasClickables.add(new ClickableArea(634, 1088, 70, 169, new ItemClickDptos("Ñeembucú")));
        areasClickables.add(new ClickableArea(757, 1139, 100, 145, new ItemClickDptos("Misiones")));
        areasClickables.add(new ClickableArea(865, 1168, 197, 73, new ItemClickDptos("Itapúa")));

        return areasClickables;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}