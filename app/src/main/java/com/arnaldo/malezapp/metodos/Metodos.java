package com.arnaldo.malezapp.metodos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;

import com.arnaldo.malezapp.R;

public class Metodos {
    public void EfectoBlur(ImageView elImageView, float radio, Context elContext){ //el radio puede ser de 0f a 25f
        Bitmap bitmap = BitmapFactory.decodeResource(elContext.getResources(), R.drawable.fondo_splash);
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
}
