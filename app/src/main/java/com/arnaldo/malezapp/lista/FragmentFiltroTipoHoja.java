package com.arnaldo.malezapp.lista;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.arnaldo.malezapp.R;

public class FragmentFiltroTipoHoja extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private View vistaFragment;
    private TextView tvTituloDpto;

    public FragmentFiltroTipoHoja() {
        // Required empty public constructor
    }

    public static FragmentFiltroTipoHoja newInstance(String param1, String param2) {
        FragmentFiltroTipoHoja fragment = new FragmentFiltroTipoHoja();
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

            getArguments().clear();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el diseño de este fragmento
        vistaFragment = inflater.inflate(R.layout.fragment_filtro_distribucion2, container, false);
        tvTituloDpto = vistaFragment.findViewById(R.id.tvTituloDpto);

        String tipohojaSelect = ((ActivityLista) getActivity()).tipoHojaSelect;
        tvTituloDpto.setText(tipohojaSelect);

        return vistaFragment;

    }
}