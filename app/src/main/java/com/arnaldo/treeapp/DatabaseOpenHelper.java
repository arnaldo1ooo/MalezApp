package com.arnaldo.treeapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelper extends SQLiteAssetHelper {

    private static final String DB_NAME = "treeapp.db";
    private static final int DB_VERSION = 2;

    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
