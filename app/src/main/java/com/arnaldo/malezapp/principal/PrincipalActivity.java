package com.arnaldo.malezapp.principal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.arnaldo.malezapp.PoliticasActivity;
import com.arnaldo.malezapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PrincipalActivity extends AppCompatActivity {
    BottomNavigationView btnNaView;

    @Override
    public void onBackPressed() {
        Log.d("BotonAtras", "Se oprimió el botón atrás");
        CerrarAplicacion();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_activity);
        getSupportActionBar().setDisplayShowHomeEnabled(true); //Activar icono en actionbar
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_round); //Asignar icono

        btnNaView = findViewById(R.id.btnNaView);

        MostrarFragmentSeleccionado(new PrincipalBuscarMalezaFragment(), R.id.container); //Mostrar el fragmento por defecto
        btnNaView.setSelectedItemId(R.id.menu_buscarmaleza); //Seleccionar el item del boton
        BarraInferior();
    }

    //METODOS
    private void BarraInferior() {
        btnNaView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    /*case R.id.menu_agroquimico:
                        MostrarFragmentSeleccionado(new PrincipalHomeFragment(), R.id.container);
                        break;*/
                    case R.id.menu_buscarmaleza:
                        MostrarFragmentSeleccionado(new PrincipalBuscarMalezaFragment(), R.id.container);
                        break;
                    case R.id.info_menu:
                        MostrarFragmentSeleccionado(new PrincipalInfoFragment(), R.id.container);
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
                .setIcon(android.R.drawable.ic_dialog_info)
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

    private void MostrarFragmentSeleccionado(Fragment fragment, int elContenedor) {
        this.getSupportFragmentManager().beginTransaction()
                .replace(elContenedor, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }


    //Creamos el menu del actionbar
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu); //Cargamos el menu

        return super.onCreateOptionsMenu(menu);
    }

    //Items del menu del actionbar
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_politicas: {
                Intent i = new Intent(this, PoliticasActivity.class);
                startActivity(i);
                return true;
            }

            default: {
                System.out.println("Error en menu actionbar "+ item.getItemId());
                return super.onOptionsItemSelected(item);
            }
        }

    }
}
