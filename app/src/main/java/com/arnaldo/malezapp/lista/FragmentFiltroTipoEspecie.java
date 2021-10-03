package com.arnaldo.malezapp.lista;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.arnaldo.malezapp.R;
import com.arnaldo.malezapp.helpers.HelpersSpinner;

import java.util.ArrayList;

public class FragmentFiltroTipoEspecie extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View vistaFragment;
    private Spinner spTipoEspecie;
    private ArrayList<ItemMaleza> listaFiltrada;
    private TextView tvTitulo;
    private HelpersSpinner helpersSpinner = new HelpersSpinner();

    public FragmentFiltroTipoEspecie() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentFiltroTipoEspecie newInstance(String param1, String param2) {
        FragmentFiltroTipoEspecie fragment = new FragmentFiltroTipoEspecie();
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
        vistaFragment = inflater.inflate(R.layout.fragment_filtro_tipoespecie, container, false);

        spTipoEspecie = vistaFragment.findViewById(R.id.spTipoEspecie);
        tvTitulo = getActivity().findViewById(R.id.tvTituloLista); //Si el componente es del activity, usar getActivity.

        //Poblar spinner
        spTipoEspecie.setAdapter(helpersSpinner.PoblarSpinner(1, vistaFragment.getContext(), R.layout.spinner_formato_item,
                "SELECT te_codigo, te_descripcion FROM tipo_especie ORDER BY te_descripcion"));

        spTipoEspecie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                if (spTipoEspecie.getSelectedItem().equals("TODOS") == false) {
                    listaFiltrada = ((ActivityListaMalezas) getActivity()).ConsultaBD("SELECT mal_codigo, mal_nombrecomun, mal_nombrecientifico " +
                            "FROM maleza, tipo_especie WHERE mal_tipoespecie = te_codigo AND te_descripcion = '" + spTipoEspecie.getSelectedItem() + "' ORDER BY mal_nombrecomun");
                } else {
                    listaFiltrada = ((ActivityListaMalezas) getActivity()).ConsultaBD("SELECT mal_codigo, mal_nombrecomun, mal_nombrecientifico " +
                            "FROM maleza ORDER BY mal_nombrecomun");
                }
                ((ActivityListaMalezas) getActivity()).CargarConsultaaRV(listaFiltrada);
                tvTitulo.setText(listaFiltrada.size() + " Malezas encontradas");
            }

            public void onNothingSelected(AdapterView<?> spn) {

            }
        });

        return vistaFragment;
    }
}