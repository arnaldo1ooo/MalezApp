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
import com.arnaldo.malezapp.metodos.Metodos;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FiltroNomCienFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FiltroNomCienFragment extends Fragment {
    private ArrayList<ListaItem> listRecibido;
    private ArrayList<ListaItem> listaFiltrada = new ArrayList<>();
    private EditText etBuscarNomCien;
    private View vistaFragment;
    private TextView tvTitulo;
    private Metodos metodos = new Metodos();

    public FiltroNomCienFragment() {
        // Required empty public constructor
    }

    public static FiltroNomCienFragment newInstance(String param1, String param2) {
        FiltroNomCienFragment fragment = new FiltroNomCienFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listRecibido = (ArrayList<ListaItem>) getArguments().getSerializable("lista"); //Recibe la lista total de malezas
            getArguments().clear(); //Limpia los argumentos ya que ya se han guardado en las variables
            System.out.println("Se recibio paquete");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vistaFragment = inflater.inflate(R.layout.filtro_nomcien_fragment, container, false);

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
            for (ListaItem listaItem : listRecibido) {
                nomcien = listaItem.getNombrecientifico().toLowerCase();
                nomcien = metodos.SacarAcentos(nomcien);
                elTexto = elTexto.toLowerCase();
                elTexto = metodos.SacarAcentos(elTexto);
                if (nomcien.contains(elTexto)) {
                    listaFiltrada.add(listaItem);
                }
            }
            ((ListaActivity) getActivity()).CargarConsultaaRV(listaFiltrada);
            tvTitulo.setText(listaFiltrada.size() + " Malezas encontradas");
        } else {
            System.out.println("listRecibido es null");
        }
    }
}