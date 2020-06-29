
package com.app.modelo;

/**
 *
 * @author Steven
 */
public class Usuario {
    private int idUsuario;
    private String username;
    private String psw;
    private TipoUsuario tipoUsuario;

    public Usuario() {
    }

    public Usuario(int idUsuario, String username, String psw, TipoUsuario tiposUsuarios) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.psw = psw;
        this.tipoUsuario = tiposUsuarios;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    
}
