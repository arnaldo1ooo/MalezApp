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
import com.arnaldo.malezapp.metodos.Metodos;

import java.util.ArrayList;

public class FragmentListaBuscarCiclo extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View vistaFragment;
    private Spinner spCiclo;
    private ArrayList<ItemLista> listaFiltrada;
    private TextView tvTitulo;

    public FragmentListaBuscarCiclo() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentListaBuscarCiclo newInstance(String param1, String param2) {
        FragmentListaBuscarCiclo fragment = new FragmentListaBuscarCiclo();
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
        vistaFragment = inflater.inflate(R.layout.fragment_lista_buscarciclo, container, false);

        spCiclo = vistaFragment.findViewById(R.id.spCiclo);
        tvTitulo = getActivity().findViewById(R.id.tvTituloLista); //Si el componente es del activity, usar getActivity.

        //Poblar spinner
        Metodos metodos = new Metodos();
        spCiclo.setAdapter(metodos.PoblarSpinner(1, vistaFragment.getContext(), R.layout.spinner_formato_item,
                "SELECT ci_codigo, ci_descripcion FROM ciclo ORDER BY ci_descripcion"));

        spCiclo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                if (spCiclo.getSelectedItem().equals("TODOS") == false) {
                    listaFiltrada = ((ActivityLista) getActivity()).ConsultaBD("SELECT mal_codigo, mal_nombrecomun, mal_nombrecientifico " +
                            "FROM maleza,ciclo WHERE mal_ciclo = ci_codigo AND ci_descripcion = '" + spCiclo.getSelectedItem() + "' ORDER BY mal_nombrecomun");
                } else {
                    listaFiltrada = ((ActivityLista) getActivity()).ConsultaBD("SELECT mal_codigo, mal_nombrecomun, mal_nombrecientifico " +
                            "FROM maleza ORDER BY mal_nombrecomun");
                }
                ((ActivityLista) getActivity()).CargarConsultaaRV(listaFiltrada);
                tvTitulo.setText(listaFiltrada.size() + " Malezas encontradas");
            }

            public void onNothingSelected(AdapterView<?> spn) {

            }
        });

        return vistaFragment;
    }
}