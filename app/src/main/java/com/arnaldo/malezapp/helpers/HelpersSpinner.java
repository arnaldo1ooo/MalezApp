package com.arnaldo.malezapp.helpers;

import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;

import com.arnaldo.malezapp.dao.DAO;

import java.util.ArrayList;

public class HelpersSpinner {

    public ArrayAdapter PoblarSpinner(int numCol, Context context, int spinner_formato, String sentenciaSQL) {
        //Poblar spinner
        DAO dao = new DAO(context);
        dao.Abrir();
        Cursor cursor = dao.EjecutarSQL(sentenciaSQL);

        ArrayList<String> listaArray = new ArrayList<>();
        listaArray.add("TODOS");
        while (cursor.moveToNext() == true) {
            listaArray.add(new String(cursor.getString(numCol)));
        }

        ArrayAdapter<String> adaptadorArray = new ArrayAdapter<String>(context, spinner_formato, listaArray); //spinner formato por defecto android.R.layout.simple_spinner_item

        cursor.close();
        dao.Cerrar();

        return adaptadorArray;
    }

}
