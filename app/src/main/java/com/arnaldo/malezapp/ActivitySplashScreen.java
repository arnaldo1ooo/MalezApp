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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class ActivitySplashScreen extends Activity {
    private TextView tv_version;
    private String versionApp;
    private InterstitialAd mInterstitialAd;
    String versionBD;


    //Para evitar que se cierre al oprimir boton atras
    @Override
    public void onBackPressed() {
        Log.d("BotonAtras", "Se oprimió el botón atrás");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Interstitial();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        tv_version = findViewById(R.id.tv_version);

        try {
            //Obtener version app
            PackageInfo packageInfo;
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionApp = packageInfo.versionName;

            //Revisa si hay acutalizacioens de la bd y obtiene la version de la bd
            Conexion conexion = Conexion.getInstance(getApplicationContext());
            conexion.Abrir();
            versionBD = conexion.VersionBD();
            conexion.Cerrar();
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "No se puede cargar la version actual!", Toast.LENGTH_LONG).show();
        }

        tv_version.setText("Versión de la app: " + versionApp + ", Versión de la BD: " + versionBD);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (seAbrióNextActivity == false) {
                    AbrirNextActivity();
                }
            }
        }, 3000);
    }

    Boolean seAbrióNextActivity = false;
    private void AbrirNextActivity() {
        Intent intent = new Intent(ActivitySplashScreen.this, ActivityPrincipal.class);
        intent.putExtra("version", tv_version.getText().toString());
        startActivity(intent);
        seAbrióNextActivity = true;
        finish();
    }

    private void Interstitial() {
        MobileAds.initialize(this, "ca-app-pub-8474453660271942/1150495372"); //Este es el id para pruebas

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
            public void onAdFailedToLoad(int errorCode) {// Código que se ejecutará cuando una solicitud de anuncio falle.

            }

            @Override
            public void onAdLeftApplication() {// Código que se ejecutará cuando el usuario haya abandonado la aplicación.
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
