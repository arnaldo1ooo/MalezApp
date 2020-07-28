package com.arnaldo.malezapp.conexion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Conexion {
    private android.database.sqlite.SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static Conexion instance;

    public Conexion(Context context) {
        this.openHelper = new MySQLiteAssetHelper(context);
    }

    //Para que devuelva una sola instancia de la db
    public static Conexion getInstance(Context context) {
        if (instance == null) {
            instance = new Conexion(context);
        }
        return instance;
    }

    //Para abrir la db
    public void Abrir() {
        try {
            this.db = openHelper.getWritableDatabase();
        } catch (Exception e) {
            Log.d("Abrir bd", "Error al intentar abrir bd: " + e);
        }
    }

    //Para cerrar la db
    public void Cerrar() {
        if (db != null) {
            this.db.close();
        }
    }

    //Para obtener la version actual de la BD
    public String VersionBD() {
        String version;
        if (db != null) {
            version = this.db.getVersion() + "";
        } else {
            this.db = openHelper.getWritableDatabase();
            version = this.db.getVersion() + "";
        }
        return version;
    }

    public Cursor EjecutarSQL(String sentencia) {
        Cursor cursor = db.rawQuery(sentencia, new String[]{});
        System.out.println("Sentencia ejecutada: " + sentencia);
        return cursor;
    }
}
