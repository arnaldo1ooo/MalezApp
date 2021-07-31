package com.arnaldo.malezapp.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class DAO {
    private android.database.sqlite.SQLiteOpenHelper openHelper;
    private SQLiteDatabase bd;
    private static DAO instance;

    public DAO(Context context) {
        this.openHelper = new MySQLiteAssetHelper(context);
    }

    //Para que devuelva una sola instancia de la db
    public static DAO getInstance(Context context) {
        if (instance == null) {
            instance = new DAO(context);
        }
        return instance;
    }

    //Para abrir la db
    public void Abrir() {
        try {
            this.bd = openHelper.getWritableDatabase();
        } catch (Exception e) {
            Log.d("Abrir bd", "Error al intentar abrir bd: " + e);
        }
    }

    //Para cerrar la db
    public void Cerrar() {
        if (bd != null) {
            this.bd.close();
        }
    }

    //Para obtener la version actual de la BD
    public String VersionBD() {
        String versionBd ="Error";
        try {
            if (bd != null) {
                versionBd = this.bd.getVersion() + "";
            } else {
                this.bd = openHelper.getWritableDatabase();
                versionBd = this.bd.getVersion() + "";
            }
        }catch (NullPointerException e){
            e.printStackTrace();
            Toast.makeText(null, "Error al obtener VersionBD " + versionBd, Toast.LENGTH_LONG).show();
        }

        return versionBd;
    }

    public Cursor EjecutarSQL(String sentencia) {
        if (sentencia != null){
            System.out.println("Sentencia a ejecutar: " + sentencia);
            Cursor cursor = bd.rawQuery(sentencia, new String[]{});
            return cursor;
        }
        else{
            System.out.println("Sentencia vacia " + sentencia);
            return null;
        }
    }
}
