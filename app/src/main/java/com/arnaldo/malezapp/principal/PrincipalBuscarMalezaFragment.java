package com.arnaldo.malezapp.principal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.arnaldo.malezapp.familia.BuscarFamiliaActivity;
import com.arnaldo.malezapp.tipohoja.BuscarTipoHojaActivity;
import com.arnaldo.malezapp.R;
import com.arnaldo.malezapp.distribucion.areaimageclick.BuscarDistribucionActivity;
import com.arnaldo.malezapp.lista.ListaActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrincipalBuscarMalezaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrincipalBuscarMalezaFragment extends Fragment {
    private ImageView iv1,iv2,iv3,iv4,iv5,iv6,iv7,iv8;
    private View vistaFragment;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PrincipalBuscarMalezaFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PrincipalBuscarMalezaFragment newInstance(String param1, String param2) {
        PrincipalBuscarMalezaFragment fragment = new PrincipalBuscarMalezaFragment();
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
        vistaFragment = inflater.inflate(R.layout.principal_buscarmaleza_fragment, container, false);

        iv1 = vistaFragment.findViewById(R.id.iv1);
        iv2 = vistaFragment.findViewById(R.id.iv2);
        iv3 = vistaFragment.findViewById(R.id.iv3);
        iv4 = vistaFragment.findViewById(R.id.iv4);
        iv5 = vistaFragment.findViewById(R.id.iv5);
        iv6 = vistaFragment.findViewById(R.id.iv6);
        iv7 = vistaFragment.findViewById(R.id.iv7);
        iv8 = vistaFragment.findViewById(R.id.iv8);


        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ListaActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ListaActivity.class);
                intent.putExtra("btnSeleccionado","nombrecomun");
                startActivityForResult(intent, 0);
            }
        });

        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ListaActivity.class);
                intent.putExtra("btnSeleccionado","nombrecientifico");
                startActivityForResult(intent, 0);
            }
        });

        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BuscarFamiliaActivity.class);
                intent.putExtra("btnSeleccionado","familia");
                startActivityForResult(intent, 0);
            }
        });

        iv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ListaActivity.class);
                intent.putExtra("btnSeleccionado","ciclo");
                startActivityForResult(intent, 0);
            }
        });

        iv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ListaActivity.class);
                intent.putExtra("btnSeleccionado","tipoespecie");
                startActivityForResult(intent, 0);
            }
        });

        iv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BuscarDistribucionActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        iv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BuscarTipoHojaActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        return vistaFragment;
    }
}