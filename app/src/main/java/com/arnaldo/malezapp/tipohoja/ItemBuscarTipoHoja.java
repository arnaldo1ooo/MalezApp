package com.arnaldo.malezapp.tipohoja;

public class ItemBuscarTipoHoja {
    private String idtipohoja;
    private String tituloTipoHoja;
    private String detalleTipoHoja;

    public ItemBuscarTipoHoja(String idtipohoja, String tituloTipoHoja, String detalleTipoHoja) {
        this.idtipohoja = idtipohoja;
        this.tituloTipoHoja = tituloTipoHoja;
        this.detalleTipoHoja = detalleTipoHoja;
    }

    public String getIdtipohoja() {
        return idtipohoja;
    }

    public String getTituloTipoHoja() {
        return tituloTipoHoja;
    }

    public String getDetalleTipoHoja() {
        return detalleTipoHoja;
    }
}
