package com.arnaldo.treeapp.rv_lista;

public class itemLista {
    private int imagen;
    private String identificador;
    private String nombrecomun;
    private String nombrecientifico;

    public itemLista(int imagen, String identificador, String nombrecomun, String nombrecientifico){
        this.imagen = imagen;
        this.identificador =identificador;
        this.nombrecomun = nombrecomun;
        this.nombrecientifico = nombrecientifico;
    }

    public int getImagen() {
        return imagen;
    }

    public String getIdentificador() {
        return identificador;
    }

    public String getNombrecomun() {
        return nombrecomun;
    }

    public String getNombrecientifico() {
        return nombrecientifico;
    }
}
