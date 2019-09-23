package com.arnaldo.treeapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.arnaldo.treeapp.basededatos.DatabaseAccess;

public class ActivitySplashScreen extends Activity {
    private TextView tv_version;
    private String versionApp;
    String versionBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        tv_version = findViewById(R.id.tv_version);

        try {
            //Obtener version app
            PackageInfo packageInfo;
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionApp = packageInfo.versionName;

            //Revisa si hay acutalizacioens de la bd y obtiene la version de la bd
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
            databaseAccess.abrir();
            versionBD = databaseAccess.VersionBD();
            databaseAccess.cerrar();
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "No se puede cargar la version actual!", Toast.LENGTH_LONG).show();
        }

        tv_version.setText("Versión de la app: " + versionApp + ", Versión de la BD: " + versionBD);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ActivitySplashScreen.this, MainActivity.class);
                startActivity(intent);
            }
        }, 2000);
    }

}
