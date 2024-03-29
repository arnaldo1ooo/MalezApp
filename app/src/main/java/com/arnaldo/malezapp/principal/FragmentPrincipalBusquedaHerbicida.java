package com.arnaldo.malezapp.principal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.arnaldo.malezapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentPrincipalBusquedaHerbicida#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPrincipalBusquedaHerbicida extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private View vista;
    //private Button btnPrincipal1;


    public FragmentPrincipalBusquedaHerbicida() {
        // Required empty public constructor
    }

    public static FragmentPrincipalBusquedaHerbicida newInstance(String param1, String param2) {
        FragmentPrincipalBusquedaHerbicida fragment = new FragmentPrincipalBusquedaHerbicida();
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
        vista = inflater.inflate(R.layout.fragment_principal_busquedaherbicida, container, false);

        //btnPrincipal1 = vista.findViewById(R.id.btnPrincipal1);

        /*btnPrincipal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BuscadorActivity.class);
                startActivityForResult(intent, 0);
            }
        });*/

        return vista;
    }
}