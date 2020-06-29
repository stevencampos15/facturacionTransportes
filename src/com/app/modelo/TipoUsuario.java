
package com.app.modelo;

/**
 *
 * @author Steven
 */
public class TipoUsuario {
    private int idTipoUsuario;
    private String nombreTU;
    private String descripcionTU;

    public TipoUsuario() {
    }

    public TipoUsuario(int idTipoUsuario, String nombreTU, String descripcionTU) {
        this.idTipoUsuario = idTipoUsuario;
        this.nombreTU = nombreTU;
        this.descripcionTU = descripcionTU;
    }

    public int getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(int idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public String getNombreTU() {
        return nombreTU;
    }

    public void setNombreTU(String nombreTU) {
        this.nombreTU = nombreTU;
    }

    public String getDescripcionTU() {
        return descripcionTU;
    }

    public void setDescripcionTU(String descripcionTU) {
        this.descripcionTU = descripcionTU;
    }
    
    
}
