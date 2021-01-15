package com.arnaldo.malezapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.arnaldo.malezapp.conexion.Conexion;
import com.arnaldo.malezapp.principal.PrincipalActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class SplashScreenActivity extends Activity {
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
        Interstitial();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen_activity);

        tvVersion = findViewById(R.id.tv_version);

        try {  //Obtener version app
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionActualApp = packageInfo.versionName;

            //Revisa si hay acutalizacioens de la bd y obtiene la version de la bd
            Conexion conexion = Conexion.getInstance(getApplicationContext());
            conexion.Abrir();
            versionActualBD = conexion.VersionBD();
            conexion.Cerrar();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "No se puede obtener la version actual de la app o de la bd!", Toast.LENGTH_LONG).show();
        }

        tvVersion.setText("Versión de la app: " + versionActualApp + ", Versión de la BD: " + versionActualBD);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (seAbrióNextActivity == false) {
                    AbrirNextActivity();
                }
            }
        }, 4000);
    }

    Boolean seAbrióNextActivity = false;
    private void AbrirNextActivity() {
        Intent intent = new Intent(SplashScreenActivity.this, PrincipalActivity.class);
        intent.putExtra("version", tvVersion.getText().toString());
        startActivity(intent);
        seAbrióNextActivity = true;
        finish();
    }

    private void Interstitial() {
        //MobileAds.initialize(this, "ca-app-pub-8474453660271942/1150495372"); //Este es el id para pruebas
        MobileAds.initialize(this, initializationStatus -> { });
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8474453660271942/1150495372");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (mInterstitialAd.isLoaded()) {// Código que se ejecutará cuando un anuncio termine de cargarse.
                    mInterstitialAd.show(); //Mostrar el Interstittial luego de crearlo
                }
            }

            @Override
            public void onAdOpened() {// Código que se ejecutará cuando se muestre el anuncio.

            }

            @Override
            public void onAdClosed() {// Código que se ejecutará cuando el anuncio intersticial esté cerrado.
                Log.d("Interstititial", "Se cerró interstitial");
                if (seAbrióNextActivity == false) {
                    AbrirNextActivity();
                }
            }
        });
    }
}
