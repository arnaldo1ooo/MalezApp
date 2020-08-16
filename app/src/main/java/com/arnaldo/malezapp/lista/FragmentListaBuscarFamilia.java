package com.arnaldo.malezapp.lista;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.arnaldo.malezapp.R;


public class FragmentListaBuscarFamilia extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView tvTituloFamilia;
    private View vistaFragment;

    public FragmentListaBuscarFamilia() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentListaBuscarFamilia newInstance(String param1, String param2) {
        FragmentListaBuscarFamilia fragment = new FragmentListaBuscarFamilia();
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
        // Inflate the layout for this fragment
        vistaFragment = inflater.inflate(R.layout.fragment_lista_buscarfamilia, container, false);

        tvTituloFamilia = vistaFragment.findViewById(R.id.tvTituloFamilia);

        String familiaSeleccionado = ((ActivityLista) getActivity()).familiaSeleccionado;
        tvTituloFamilia.setText("FAMILIA " + familiaSeleccionado);

        return vistaFragment;
    }
}