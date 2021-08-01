package com.arnaldo.malezapp.helpers;

import java.text.Normalizer;

public class HelpersString {

    public String SacarAcentos(String elTexto) {
        elTexto = Normalizer.normalize(elTexto, Normalizer.Form.NFD);
        elTexto = elTexto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return elTexto;
    }
}
