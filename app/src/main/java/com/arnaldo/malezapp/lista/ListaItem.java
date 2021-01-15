package com.arnaldo.malezapp.lista;

public class ListaItem {
    private String codigo;
    private int imagen;
    private String nombrecomun;
    private String nombrecientifico;

    public ListaItem(String codigo, int imagen, String nombrecomun, String nombrecientifico){
        this.codigo = codigo;
        this.imagen = imagen;
        this.nombrecomun = nombrecomun;
        this.nombrecientifico = nombrecientifico;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getImagen() {
        return imagen;
    }

    public String getNombrecomun() {
        return nombrecomun;
    }

    public String getNombrecientifico() {
        return nombrecientifico;
    }
}
