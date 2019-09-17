package com.arnaldo.treeapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase bd;
    private static DatabaseAccess instance;
    Cursor cursor = null;

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);

    }

    //Para que devuelva una sola instancia de la bd
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    //Para abrir la bd
    public void open() {
        this.bd = openHelper.getWritableDatabase();
    }

    //Para cerrar la bd
    public void close() {
        if (bd != null) {
            this.bd.close();
        }
    }

    //Para buscar el registro por medio del indentificador
    public String getDescripcion(String identificador) {
        String sql = "SELECT es_descripcion FROM especies WHERE es_identificador = '" + identificador + "'";
        cursor = bd.rawQuery(sql, new String[]{});

        System.out.println("SQL ejecutado: " + sql);
        System.out.println("Cantidad resultados " + cursor.getCount());

        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext() == true) {
            String descripcion = cursor.getString(0);
            buffer.append("" + descripcion);
        }
        return buffer.toString();
    }
}
