package com.arnaldo.malezapp;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.arnaldo.malezapp.dao.DAO;
import com.arnaldo.malezapp.principal.ActivityPrincipal;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class ActivitySplashScreen extends Activity {
    private TextView tvVersion;
    private String versionActualApp;
    private InterstitialAd mInterstitialAd;
    private String versionActualBD;


    //Para evitar que se cierre al oprimir boton atras
    @Override
    public void onBackPressed() {
        Log.d("BotonAtras", "Se oprimió el botón atrás");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        tvVersion = findViewById(R.id.tv_version);


        CargarInterstitial();
        ObtenerVersionAppBD();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Ejecutar el siguiente activity
                Intent intent = new Intent(ActivitySplashScreen.this, ActivityPrincipal.class);
                intent.putExtra("version", tvVersion.getText().toString());
                startActivity(intent);
            }
        }, 5000);
    }

    private void ObtenerVersionAppBD() {
        try {  //Obtener version app
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionActualApp = packageInfo.versionName;

            //Revisa si hay acutalizacioens de la bd y obtiene la version de la bd
            DAO dao = new DAO(this);
            dao = dao.getInstance(getApplicationContext());
            dao.Abrir();
            versionActualBD = dao.VersionBD();
            dao.Cerrar();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "No se puede obtener la version actual de la app o de la bd!", Toast.LENGTH_LONG).show();
        }
        tvVersion.setText("Versión de la app: " + versionActualApp + ", Versión de la BD: " + versionActualBD);

    }

    private void CargarInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, "ca-app-pub-8474453660271942/1150495372", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // un ad esta cargado.

                mInterstitialAd = interstitialAd;
                Log.i(TAG, "Se cargó Interstitial");

                if (mInterstitialAd != null) { //Mostrar el Interstitial
                    mInterstitialAd.show(ActivitySplashScreen.this);
                } else {
                    Log.d("TAG", "El anuncio intersticial aún no esta listo.");
                }
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.i(TAG, loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });
    }
}
