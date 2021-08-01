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
import com.arnaldo.malezapp.helpers.HelpersString;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentFiltroNomCien#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFiltroNomCien extends Fragment {
    private ArrayList<ItemLista> listRecibido;
    private ArrayList<ItemLista> listaFiltrada = new ArrayList<>();
    private EditText etBuscarNomCien;
    private View vistaFragment;
    private TextView tvTitulo;
    private HelpersString helpersString = new HelpersString();

    public FragmentFiltroNomCien() {
        // Required empty public constructor
    }

    public static FragmentFiltroNomCien newInstance(String param1, String param2) {
        FragmentFiltroNomCien fragment = new FragmentFiltroNomCien();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listRecibido = (ArrayList<ItemLista>) getArguments().getSerializable("lista"); //Recibe la lista total de malezas
            getArguments().clear(); //Limpia los argumentos ya que ya se han guardado en las variables
            System.out.println("Se recibio paquete");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vistaFragment = inflater.inflate(R.layout.fragment_filtro_nomcien, container, false);

        etBuscarNomCien = vistaFragment.findViewById(R.id.etBuscarNomCom2); //Si el componente es del fragment, usar vistaFragment.
        tvTitulo = getActivity().findViewById(R.id.tvTituloLista); //Si el componente es del activity, usar getActivity.

        etBuscarNomCien.addTextChangedListener(new TextWatcher() {
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

    private void FiltrarRV(String elTexto) {
        listaFiltrada.clear();
        String nomcien;
        if (listRecibido != null) {
            for (ItemLista listaItem : listRecibido) {
                nomcien = listaItem.getNombrecientifico().toLowerCase();
                nomcien = helpersString.SacarAcentos(nomcien);
                elTexto = elTexto.toLowerCase();
                elTexto = helpersString.SacarAcentos(elTexto);
                if (nomcien.contains(elTexto)) {
                    listaFiltrada.add(listaItem);
                }
            }
            ((ActivityLista) getActivity()).CargarConsultaaRV(listaFiltrada);
            tvTitulo.setText(listaFiltrada.size() + " Malezas encontradas");
        } else {
            System.out.println("listRecibido es null");
        }
    }
}