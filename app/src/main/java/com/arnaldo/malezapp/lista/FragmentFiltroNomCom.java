package com.arnaldo.malezapp.lista;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.arnaldo.malezapp.R;
import com.arnaldo.malezapp.utilidades.metodos.Metodos;

import java.util.ArrayList;

public class FragmentFiltroNomCom extends Fragment {
        // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<ItemLista> listRecibido;
    private EditText etBuscarNomCom;
    private View vistaFragment;
    private TextView tvTitulo;

    public FragmentFiltroNomCom() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentFiltroNomCom newInstance(String param1, String param2) {
        FragmentFiltroNomCom fragment = new FragmentFiltroNomCom();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) { //Recibe argumentos si es distinto a vacio
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            listRecibido = (ArrayList<ItemLista>) getArguments().getSerializable("lista");

            getArguments().clear(); //Limpia los argumentos ya que ya se han guardado en las variables
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vistaFragment = inflater.inflate(R.layout.fragment_filtro_nomcom, container, false);

        etBuscarNomCom = vistaFragment.findViewById(R.id.etBuscarNomCom2); //Si el componente es del fragment, usar vistaFragment.
        tvTitulo = getActivity().findViewById(R.id.tvTituloLista); //Si el componente es del activity, usar getActivity.


        etBuscarNomCom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                FiltrarRV(s.toString());
            }
        });
        return vistaFragment;
    }

    ArrayList<ItemLista> listaFiltrada = new ArrayList<>();
    Metodos metodos = new Metodos();
    private void FiltrarRV(String elTexto) {
        listaFiltrada.clear();
        for (ItemLista itemlista : listRecibido) {
            if (metodos.SacarAcentos(itemlista.getNombrecomun().toLowerCase()).contains(metodos.SacarAcentos(elTexto.toLowerCase()))) {
                listaFiltrada.add(itemlista);
            }
        }
        ((ActivityLista) getActivity()).CargarConsultaaRV(listaFiltrada);
        tvTitulo.setText(listaFiltrada.size() + " Malezas encontradas");
    }
}