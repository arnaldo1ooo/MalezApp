package com.arnaldo.treeapp.basededatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseAccess {
    private android.database.sqlite.SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;

    private DatabaseAccess(Context context) {
        this.openHelper = new MySQLiteAssetHelper(context);
    }

    //Para que devuelva una sola instancia de la db
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    //Para abrir la db
    public void abrir() {
        try {
            this.db = openHelper.getWritableDatabase();
        } catch (Exception e) {
            Log.d("Abrir bd", "Error al intentar abrir bd: " + e);
        }
    }

    //Para cerrar la db
    public void cerrar() {
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

    //Para buscar el registro por medio del indentificador
    public String[] ConsultaUnicaEspecie(String identificador) {
        String tabla = "especies";
        String columnas = "es_codigo, es_nombrecomun, es_nombrecientifico";
        String sql = "SELECT " + columnas + " FROM " + tabla + " WHERE es_identificador = '" + identificador + "'";
        Cursor cursor = db.rawQuery(sql, new String[]{});

        Log.d("Buscar registro", "SQL ejecutado: " + sql);
        Log.d("Buscar registros", "Cantidad resultados " + cursor.getCount());
        Log.d("Version de BD", db.getVersion() + "");

        String[] Array = new String[3];
        while (cursor.moveToNext() == true) {
            Array[0] = cursor.getString(0); //Columna 1
            Array[1] = cursor.getString(1); //Columna 2
            Array[2] = cursor.getString(2); //Columna 3
        }
        return Array;
    }


    public Cursor ConsultaAllEspecies() {
        String tabla = "especies";
        String columnas = "es_codigo, es_identificador, es_nombrecomun, es_nombrecientifico";
        String sql = "SELECT " + columnas + " FROM " + tabla;
        Cursor cursor = db.rawQuery(sql, new String[]{});

        Log.d("ConsultaAllEspecies", "SQL ejecutado: " + sql);
        Log.d("ConsultaAllEspecies", "Cantidad resultados: " + cursor.getCount());
        Log.d("ConsultaAllEspecies", "Version BD: " + db.getVersion() + "");

        return cursor;
    }
}
