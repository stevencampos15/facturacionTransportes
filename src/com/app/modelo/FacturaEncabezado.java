
package com.app.modelo;

import java.sql.Date;

/**
 *
 * @author Steven
 */
public class FacturaEncabezado {
    private int noFactura;
    private Date fecha;
    private String fechaNotaRemision;
    private String aCuentaDe;
    private CondicionPago condicionPago;
    private Cliente cliente;

    public FacturaEncabezado() {
    }

    public FacturaEncabezado(int noFactura, Date fecha, String fechaNotaRemision, String aCuentaDe, CondicionPago condicionPago, Cliente cliente) {
        this.noFactura = noFactura;
        this.fecha = fecha;
        this.fechaNotaRemision = fechaNotaRemision;
        this.aCuentaDe = aCuentaDe;
        this.condicionPago = condicionPago;
        this.cliente = cliente;
    }

    public int getNoFactura() {
        return noFactura;
    }

    public void setNoFactura(int noFactura) {
        this.noFactura = noFactura;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFechaNotaRemision() {
        return fechaNotaRemision;
    }

    public void setFechaNotaRemision(String fechaNotaRemision) {
        this.fechaNotaRemision = fechaNotaRemision;
    }

    public String getaCuentaDe() {
        return aCuentaDe;
    }

    public void setaCuentaDe(String aCuentaDe) {
        this.aCuentaDe = aCuentaDe;
    }

    public CondicionPago getCondicionPago() {
        return condicionPago;
    }

    public void setCondicionPago(CondicionPago condicionPago) {
        this.condicionPago = condicionPago;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
}
