
package com.app.modelo;

/**
 *
 * @author Steven
 */
public class Departamento {
    private int idDepartamento;
    private String nombreDepartamento;
    private Pais pais;

    public Departamento() {
    }

    public Departamento(int idDepartamento, String nombreDepartamento, Pais pais) {
        this.idDepartamento = idDepartamento;
        this.nombreDepartamento = nombreDepartamento;
        this.pais = pais;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }
    
}
