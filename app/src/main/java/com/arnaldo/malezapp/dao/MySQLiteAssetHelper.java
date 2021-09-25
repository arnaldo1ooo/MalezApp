package com.arnaldo.malezapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MySQLiteAssetHelper extends com.readystatesoftware.sqliteasset.SQLiteAssetHelper {
    private static final String BD_NOMBRE = "malezapp.db";
    private static final int BD_VERSION = 30; //Incrementar el numero para que vuelva a cargar la bd
    private static String DB_PATH = "";
    private final Context myContext;

    public MySQLiteAssetHelper(Context context) {
        super(context, BD_NOMBRE, null, BD_VERSION);
        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.myContext = context;

        //setForcedUpgrade(); //Metodo para forzar una sobrescritura de la base de datos cada vez que se incremente el número de versión
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(myContext.getApplicationContext(), "La BD se actualizó a la versión: " + newVersion, Toast.LENGTH_LONG).show();
        Log.d("Actualizacion SQLite", "Direccion BD: " + db.getPath() + ", Version antigua: " + oldVersion + ", Version nueva: " + newVersion);
        myContext.deleteDatabase(BD_NOMBRE); //Elimina la bd antigua
        CrearDB(); //Crea la nueva bd
    }


    public void CrearDB() {
        boolean dbExiste = ComprobarDB();
        Log.d("CrearDB", "dbExiste: " + dbExiste);
        if (dbExiste == false) {
            SQLiteDatabase estadoDB = getReadableDatabase();
            estadoDB.close();
            try {
                CopiarDB();
                Log.d("CrearBD", "Se copió la BD");
            } catch (IOException e) {
                throw new Error("Error al intentar copiar DB");
            }
        } else {
            Log.d("CrearDB", "No se puede crear DB, DB ya existe");
        }
    }


    private boolean ComprobarDB() {
        SQLiteDatabase estadoDB = null;
        try {
            String myPath = DB_PATH + BD_NOMBRE;
            File outFile = new File(Environment.getDataDirectory(), myPath);
            outFile.setWritable(true);
            SQLiteDatabase.openDatabase(outFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
            estadoDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            Log.d("ComprobarDB", "La DB aun no existe");
        }

        if (estadoDB != null) {
            estadoDB.close();
        }
        return estadoDB != null;
    }


    private void CopiarDB() throws IOException {
        //Abre tu DB local en el input stream
        InputStream myInput = myContext.getAssets().open(BD_NOMBRE);
        // Path to the just created empty db
        String outFileName = DB_PATH + BD_NOMBRE;
        //Abre la DB vacia en el output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //Transfiere bytes de un inputfile a un outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
            Log.d("CopiarDB", "" + buffer);
        }
        Log.d("CopiarDB", "Copia de DB exitosa");
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
}
