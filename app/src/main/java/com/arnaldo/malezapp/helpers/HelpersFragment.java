package com.arnaldo.malezapp.helpers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HelpersFragment {

    public void MostrarFragmentSeleccionado(FragmentManager elFragmentManager, int tipoTransaction,Fragment elFragment, int elContenedor) {
        elFragmentManager.beginTransaction()
                .addToBackStack(null) //Agrega a la pila para que el fragment anterior no se pierda
                .replace(elContenedor, elFragment)
                .setTransition(tipoTransaction)
                .commit();
    }
}
