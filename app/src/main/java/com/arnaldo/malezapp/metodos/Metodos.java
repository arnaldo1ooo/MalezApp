package com.arnaldo.malezapp.metodos;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.arnaldo.malezapp.conexion.Conexion;

import java.text.Normalizer;
import java.util.ArrayList;

public class Metodos {
    Conexion conexion;

    public void EfectoBlur(int direccionImagen, ImageView elImageView, float radio, Context elContext) { //el radio puede ser de 0f a 25f
        Bitmap bitmap = BitmapFactory.decodeResource(elContext.getResources(), direccionImagen);
        Bitmap salidaBitMap = Bitmap.createBitmap(bitmap);
        final RenderScript renderScript = RenderScript.create(elContext);
        Allocation entradaTemp = Allocation.createFromBitmap(renderScript, bitmap);
        Allocation salidaTemp = Allocation.createFromBitmap(renderScript, salidaBitMap);

        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setRadius(radio);
        scriptIntrinsicBlur.setInput(entradaTemp);
        scriptIntrinsicBlur.forEach(salidaTemp);
        salidaTemp.copyTo(salidaBitMap);

        elImageView.setImageBitmap(salidaBitMap);
    }

    public ArrayAdapter PoblarSpinner(int numCol, Context context, int spinner_formato, String sentenciaSQL) {
        //Poblar spinner
        conexion = new Conexion(context);
        conexion.Abrir();
        Cursor cursor = conexion.EjecutarSQL(sentenciaSQL);

        ArrayList<String> listaArray = new ArrayList<>();
        listaArray.add("TODOS");
        while (cursor.moveToNext() == true) {
            listaArray.add(new String(cursor.getString(numCol)));
        }

        ArrayAdapter<String> adaptadorArray = new ArrayAdapter<String>(context, spinner_formato, listaArray); //spinner formato por defecto android.R.layout.simple_spinner_item

        cursor.close();
        conexion.Cerrar();

        return adaptadorArray;
    }

    public String SacarAcentos(String elTexto) {
        elTexto = Normalizer.normalize(elTexto, Normalizer.Form.NFD);
        elTexto = elTexto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return elTexto;
    }
}
