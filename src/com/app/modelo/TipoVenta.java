
package com.app.modelo;

/**
 *
 * @author Steven
 */
public class TipoVenta {
    int idTipoVenta;
    String nombreTipoVenta;

    public TipoVenta() {
    }

    public TipoVenta(int idTipoVenta, String nombreTipoVenta) {
        this.idTipoVenta = idTipoVenta;
        this.nombreTipoVenta = nombreTipoVenta;
    }

    public int getIdTipoVenta() {
        return idTipoVenta;
    }

    public void setIdTipoVenta(int idTipoVenta) {
        this.idTipoVenta = idTipoVenta;
    }

    public String getNombreTipoVenta() {
        return nombreTipoVenta;
    }

    public void setNombreTipoVenta(String nombreTipoVenta) {
        this.nombreTipoVenta = nombreTipoVenta;
    }
     
}
