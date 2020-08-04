package com.arnaldo.malezapp.principal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.arnaldo.malezapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityPrincipal extends AppCompatActivity {
    BottomNavigationView btnNaView;

    @Override
    public void onBackPressed() {
        Log.d("BotonAtras", "Se oprimió el botón atrás");
        CerrarAplicacion();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        btnNaView = findViewById(R.id.btnNaView);

        MostrarFragmentSeleccionado(new FragmentHome()); //Mostrar el fragmento home por defecto

        BarraInferior();
    }

    private void BarraInferior() {
        btnNaView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_home:
                        MostrarFragmentSeleccionado(new FragmentHome());
                        break;
                    case R.id.menu_buscarmaleza:
                        MostrarFragmentSeleccionado(new FragmentBuscarMaleza());
                        break;
                    case R.id.boton3:
                        //showSelectedFragment(new FragmentHome());
                        break;
                    default:
                        //JOptionPane.showMessageDialog(this, "No se selecciono ningun menu", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                }

                return true;
            }
        });
    }



    private void CerrarAplicacion() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("¿Realmente desea cerrar la aplicación?")
                .setCancelable(false)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {// un listener que al pulsar, cierre la aplicacion
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        android.os.Process.killProcess(android.os.Process.myPid()); //Su funcion es algo similar a lo que se llama cuando se presiona el botón "Forzar Detención" o "Administrar aplicaciones", lo cuál mata la aplicación
                        //finish(); Si solo quiere mandar la aplicación a segundo plano
                    }
                }).show();
    }

    private void MostrarFragmentSeleccionado(Fragment fragment) {
        this.getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
