
package com.app.modelo;

/**
 *
 * @author Steven
 */
public class CondicionPago {
    private int idCP;
    private String nombreTipoCP;
    private String descripcionCP;

    public CondicionPago() {
    }

    public CondicionPago(int idCP, String nombreTipoCP, String descripcionCP) {
        this.idCP = idCP;
        this.nombreTipoCP = nombreTipoCP;
        this.descripcionCP = descripcionCP;
    }

    public int getIdCP() {
        return idCP;
    }

    public void setIdCP(int idCP) {
        this.idCP = idCP;
    }

    public String getNombreTipoCP() {
        return nombreTipoCP;
    }

    public void setNombreTipoCP(String nombreTipoCP) {
        this.nombreTipoCP = nombreTipoCP;
    }

    public String getDescripcionCP() {
        return descripcionCP;
    }

    public void setDescripcionCP(String descripcionCP) {
        this.descripcionCP = descripcionCP;
    }
    
    
}
