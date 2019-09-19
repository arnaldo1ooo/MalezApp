package com.arnaldo.treeapp.basededatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


public class SQLiteOpenHelper extends SQLiteAssetHelper {

    private static final String BD_NOMBRE = "treeapp.db";
    private static final int BD_VERSION = 1;

    public SQLiteOpenHelper(Context context) {
        super(context, BD_NOMBRE, null, BD_VERSION);
    }

    public void onUpgrade(Context context, SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("Actualizacion SQLite", "BD: " + db.getPath() + ", Version antigua: " + oldVersion + ", Version nueva: " + newVersion);

    }
}
