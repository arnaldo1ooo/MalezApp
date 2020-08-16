package com.arnaldo.malezapp.principal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.arnaldo.malezapp.ActivityBuscarFamilia;
import com.arnaldo.malezapp.areaimageclick.ActivityBuscarDistribucion;
import com.arnaldo.malezapp.R;
import com.arnaldo.malezapp.lista.ActivityLista;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentPrincipalBuscarMaleza#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPrincipalBuscarMaleza extends Fragment {
    private ImageView iv1,iv2,iv3,iv4,iv5,iv6,iv7,iv8;
    private View vistaFragment;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentPrincipalBuscarMaleza() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentPrincipalBuscarMaleza newInstance(String param1, String param2) {
        FragmentPrincipalBuscarMaleza fragment = new FragmentPrincipalBuscarMaleza();
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
        vistaFragment = inflater.inflate(R.layout.fragment_principal_buscarmaleza, container, false);

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
                Intent intent = new Intent(v.getContext(), ActivityLista.class);
                startActivityForResult(intent, 0);
            }
        });

        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityLista.class);
                intent.putExtra("btnSeleccionado","nombrecomun");
                startActivityForResult(intent, 0);
            }
        });

        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityLista.class);
                intent.putExtra("btnSeleccionado","nombrecientifico");
                startActivityForResult(intent, 0);
            }
        });

        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityBuscarFamilia.class);
                intent.putExtra("btnSeleccionado","familia");
                startActivityForResult(intent, 0);
            }
        });

        iv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityLista.class);
                intent.putExtra("btnSeleccionado","ciclo");
                startActivityForResult(intent, 0);
            }
        });

        iv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityLista.class);
                intent.putExtra("btnSeleccionado","tipoespecie");
                startActivityForResult(intent, 0);
            }
        });

        iv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityBuscarDistribucion.class);
                startActivityForResult(intent, 0);
            }
        });

        iv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(v.getContext(), PruebaArea.class);
                startActivityForResult(intent, 0);*/
            }
        });

        return vistaFragment;
    }
}