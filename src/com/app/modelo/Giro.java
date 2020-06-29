
package com.app.modelo;

/**
 *
 * @author Steven
 */
public class Giro {
    private int idGiro;
    private String nombreGiro;
    private String descripcionGiro;

    public Giro() {
    }

    public Giro(int idGiro, String nombreGiro, String descripcionGiro) {
        this.idGiro = idGiro;
        this.nombreGiro = nombreGiro;
        this.descripcionGiro = descripcionGiro;
    }

    public int getIdGiro() {
        return idGiro;
    }

    public void setIdGiro(int idGiro) {
        this.idGiro = idGiro;
    }

    public String getNombreGiro() {
        return nombreGiro;
    }

    public void setNombreGiro(String nombreGiro) {
        this.nombreGiro = nombreGiro;
    }

    public String getDescripcionGiro() {
        return descripcionGiro;
    }

    public void setDescripcionGiro(String descripcionGiro) {
        this.descripcionGiro = descripcionGiro;
    }
    
    
}
