
package com.app.modelo;

/**
 *
 * @author Steven
 */
public class DetalleFactura {
    private int noDetalle;
    private FacturaEncabezado factura;
    private Producto producto;
    private int cantidad;
    private float precioUnitario;
    private String descripcionFactura;
    private TipoVenta tipoventa;
    
    public DetalleFactura() {
    }

    public DetalleFactura(int noDetalle, FacturaEncabezado factura, Producto producto, int cantidad, float precioUnitario, String descripcionFactura, TipoVenta tipoventa) {
        this.noDetalle = noDetalle;
        this.factura = factura;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descripcionFactura = descripcionFactura;
        this.tipoventa = tipoventa;
    }

    public int getNoDetalle() {
        return noDetalle;
    }

    public void setNoDetalle(int noDetalle) {
        this.noDetalle = noDetalle;
    }

    public FacturaEncabezado getFactura() {
        return factura;
    }

    public void setFactura(FacturaEncabezado factura) {
        this.factura = factura;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getDescripcionFactura() {
        return descripcionFactura;
    }

    public void setDescripcionFactura(String descripcionFactura) {
        this.descripcionFactura = descripcionFactura;
    }

    public TipoVenta getTipoventa() {
        return tipoventa;
    }

    public void setTipoventa(TipoVenta tipoventa) {
        this.tipoventa = tipoventa;
    }
    
    
    
    
}
