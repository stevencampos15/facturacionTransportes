
package com.app.modelo;

/**
 *
 * @author Steven
 */
public class Cliente {
    private int idCliente;
    private String nit;
    private String nombreCliente;
    private String telefonoCliente;
    private String descripcionCliente;
    private String registro;
    private String direccionCliente;
    private Departamento departamento;
    private Giro giro;

    public Cliente() {
    }

    public Cliente(int idCliente, String nit, String nombreCliente, String telefonoCliente, String descripcionCliente, String registro, String direccionCliente, Departamento departamento, Giro giro) {
        this.idCliente = idCliente;
        this.nit = nit;
        this.nombreCliente = nombreCliente;
        this.telefonoCliente = telefonoCliente;
        this.descripcionCliente = descripcionCliente;
        this.registro = registro;
        this.direccionCliente = direccionCliente;
        this.departamento = departamento;
        this.giro = giro;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getDescripcionCliente() {
        return descripcionCliente;
    }

    public void setDescripcionCliente(String descripcionCliente) {
        this.descripcionCliente = descripcionCliente;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Giro getGiro() {
        return giro;
    }

    public void setGiro(Giro giro) {
        this.giro = giro;
    }

    
    
}
