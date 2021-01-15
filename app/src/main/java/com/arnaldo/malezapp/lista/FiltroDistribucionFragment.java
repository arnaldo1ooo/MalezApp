package com.arnaldo.malezapp.lista;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.arnaldo.malezapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FiltroDistribucionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FiltroDistribucionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View vistaFragment;
    private TextView tvTituloDpto;

    public FiltroDistribucionFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FiltroDistribucionFragment newInstance(String param1, String param2) {
        FiltroDistribucionFragment fragment = new FiltroDistribucionFragment();
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
        vistaFragment = inflater.inflate(R.layout.filtro_distribucion_fragment, container, false);
        tvTituloDpto = vistaFragment.findViewById(R.id.tvTituloDpto);

        String dptoSeleccionado = ((ListaActivity) getActivity()).dptoSeleccionado;
        tvTituloDpto.setText(dptoSeleccionado);

        return vistaFragment;

    }
}