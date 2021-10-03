package com.arnaldo.malezapp.principal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.arnaldo.malezapp.ActivityBusquedaAbierta;
import com.arnaldo.malezapp.ActivityBusquedaGuiada;
import com.arnaldo.malezapp.R;
import com.arnaldo.malezapp.lista.ActivityListaMalezas;

public class FragmentPrincipalBusquedaMaleza extends Fragment {
    private ImageView iv1,iv2,iv3;
    private View vistaFragment;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentPrincipalBusquedaMaleza() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentPrincipalBusquedaMaleza newInstance(String param1, String param2) {
        FragmentPrincipalBusquedaMaleza fragment = new FragmentPrincipalBusquedaMaleza();
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
        vistaFragment = inflater.inflate(R.layout.fragment_principal_busquedamaleza, container, false);

        iv1 = vistaFragment.findViewById(R.id.ivBusquedaGuiada);
        iv2 = vistaFragment.findViewById(R.id.ivBusquedaAbierta);
        iv3 = vistaFragment.findViewById(R.id.ivListadoMalezas);



        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityBusquedaGuiada.class);
                intent.putExtra("btnSeleccionado","BusquedaGuiada");
                startActivityForResult(intent, 0);
            }
        });

        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityBusquedaAbierta.class);
                intent.putExtra("btnSeleccionado","BusquedaAbierta");
                startActivityForResult(intent, 0);
            }
        });

        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityListaMalezas.class);
                intent.putExtra("btnSeleccionado","allmalezas");
                startActivityForResult(intent, 0);
            }
        });

        return vistaFragment;
    }
}