package com.arnaldo.malezapp.helpers.areaimageclick;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.arnaldo.malezapp.R;
import com.arnaldo.malezapp.dao.DAO;
import com.arnaldo.malezapp.lista.ActivityLista;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentFiltroDistribucion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFiltroDistribucion extends Fragment implements OnClickableAreaClickedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView ivMapa;
    private ListView lvdpto;
    private TextView tvTitulo;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference(); //Abrir conexion con storage de firestore
    private View vista;

    public FragmentFiltroDistribucion() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentFiltroDistribucion newInstance(String param1, String param2) {
        FragmentFiltroDistribucion fragment = new FragmentFiltroDistribucion();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout para este Fragmento
        vista = inflater.inflate(R.layout.fragment_filtro_distribucion, container, false);

        ivMapa = vista.findViewById(R.id.ivMapaDistribucion);
        lvdpto = vista.findViewById(R.id.lvDptoDistribucion);
        tvTitulo = vista.findViewById(R.id.tvTituloDistribucion);

        Tab();

        //Cargar país
        tvTitulo.setText("PARAGUAY");
        CargarLista("SELECT dep_descripcion FROM departamento ORDER BY dep_descripcion", lvdpto);
        CrearImagenAreaClickable(ivMapa, AreasClickParaguay());

        lvdpto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(v.getContext(), ActivityLista.class);
                intent.putExtra("btnSeleccionado", "distribucion");
                intent.putExtra("dptoseleccionado", lvdpto.getItemAtPosition(position).toString());
                startActivityForResult(intent, 0);
            }
        });

        return vista;
    }

    private void Tab() {
        TabHost tabs = vista.findViewById(R.id.tabhostDistri);
        tabs.setup();

        TabHost.TabSpec tbSpec = tabs.newTabSpec("mitab1");
        tbSpec.setContent(R.id.tab1Distribucion);
        tbSpec.setIndicator("Mapa", null/*res.getDrawable(android.R.drawable.ic_btn_speak_now)*/); //El titulo y icono
        tabs.addTab(tbSpec);

        tbSpec = tabs.newTabSpec("mitab2");
        tbSpec.setContent(R.id.tab2Distribucion);
        tbSpec.setIndicator("Lista", null/*res.getDrawable(android.R.drawable.ic_dialog_map)*/);
        tabs.addTab(tbSpec);
        tabs.setCurrentTab(0);
    }

    public void CargarLista(String consultaSQL, ListView laLista) {
        DAO DAO = new DAO(getActivity());
        ArrayList<String> lista = new ArrayList<>();
        try {
            DAO.Abrir();
            Cursor cursor = DAO.EjecutarSQL(consultaSQL);
            //laLista.removeAllViews();
            //lista.add("TODOS");
            while (cursor.moveToNext() == true) {
                lista.add(cursor.getString(0));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lista);
            laLista.setAdapter(adapter);
            cursor.close();
        } catch (UnsupportedOperationException e) {
            System.out.println("Error " + e);
        }
        DAO.Cerrar();
    }

    // Escucha los toques por la imagen y devuelve el dato de ese lugar
    public void onClickableAreaTouched(Object item) {
        if (item instanceof ItemClickDptos) { //Compara
            String itemSelect = ((ItemClickDptos) item).getName();
            String rutaImagen = "";
            String titulo = tvTitulo.getText() + "";

            if (titulo.equals("PARAGUAY")) {
                switch (itemSelect) {
                    case "ALTO PARANÁ": {
                        tvTitulo.setText("ALTO PARANÁ");
                        CargarLista("SELECT dis_descripcion FROM distrito, departamento WHERE dis_departamento=dep_codigo ORDER BY dis_descripcion", lvdpto);
                        rutaImagen = rutaImagen + "mapas/mapa_paraguay_altoparana.png";
                        ObtenerImagen(rutaImagen);
                        CrearImagenAreaClickable(ivMapa, AreasClickAltoParana());
                        break;
                    }

                    default: {
                        Toast.makeText(getActivity(), itemSelect + " Aún no disponible", Toast.LENGTH_SHORT).show();
                        System.out.println("No se encontro Switch " + itemSelect);
                        break;
                    }
                }
                return;
            }

            if (titulo.equals("ALTO PARANÁ")) {
                switch (itemSelect) {
                    case "MINGA GUAZÚ": {
                        tvTitulo.setText("MINGA GUAZÚ");
                        //CargarLista("SELECT dis_descripcion FROM distrito, departamento WHERE dis_departamento=dep_codigo ORDER BY dis_descripcion", lvdpto);
                        rutaImagen = rutaImagen + "mapas/mapa_altoparana_mingaguazu.png";
                        ObtenerImagen(rutaImagen);
                        //CrearImagenAreaClickable(ivMapa, AreasClickAltoParana());
                        break;
                    }

                    default: {
                        //Toast.makeText(this, itemSelect + " Aún no disponible", Toast.LENGTH_SHORT).show();
                        System.out.println("No se encontro Switch " + itemSelect);
                        break;
                    }
                }
                return;
            }
        }
    }


    private void CrearImagenAreaClickable(ImageView laImagen, List<ClickableArea> listaAreaClicks) {
        ClickableAreasImage clickableAreasImage = new ClickableAreasImage(new PhotoViewAttacher(laImagen), this);
        List<ClickableArea> clickableAreas = listaAreaClicks;
        clickableAreasImage.setClickableAreas(clickableAreas);
    }

    private List<ClickableArea> AreasClickParaguay() {
        //Se elije un punto XY, a partir de ese punto se trasa una linea horizontal(w) y vertical(h) para formar un cuadrado clickable
        ArrayList<ClickableArea> areasClickables = new ArrayList<>();
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

    private List<ClickableArea> AreasClickAltoParana() {
        //Se elije un punto XY, a partir de ese punto se trasa una linea horizontal(w) y vertical(h) para formar un cuadrado clickable
        ArrayList<ClickableArea> areasClickables = new ArrayList<>();
        areasClickables.add(new ClickableArea(629, 688, 744, 803, new ItemClickDptos("MINGA GUAZÚ")));

        return areasClickables;
    }


    private void ObtenerImagen(String rutaImagen) {
        //Obtener imagen1 desde internet
        System.out.println("rutaImagen: " + rutaImagen);
        storageReference.child(rutaImagen).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri laUri) {
                System.out.println("Se obtuvo imagen con exito: " + rutaImagen);
                Picasso.get() //Cargar desde internet
                        .load(laUri) //Link de la imagen
                        //.fit()
                        //.centerInside() //Para centrar imagen
                        .placeholder(R.drawable.cargando) //La imagen que aparecera mientras se carga la imagen del link
                        .error(R.drawable.imagen_0) //La imagen que aparecera en caso de error
                        .into(ivMapa); //El ImageView que recibira la imagen
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                System.out.println("Ocurrio un Fallo al obtener imagen: " + rutaImagen);
            }
        });
    }
}