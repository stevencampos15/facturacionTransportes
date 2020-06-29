package com.app.dao;

import com.app.conexion.ConexionBD;
import com.app.modelo.TipoUsuario;
import com.app.modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Steven
 */
public class UsuarioDAO implements Operaciones {

    private ConexionBD conexion = new ConexionBD();

    @Override
    public String insertar(Object obj) {
        String resp = null;
        String sql;
        try {
            //Casteamos en Usuario el objeto que se reciba
            Usuario usu = (Usuario) obj;
            Connection cn = this.conexion.conectar();
            sql = "INSERT INTO usuarios VALUES(?,?,SHA1(?),?)";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, usu.getIdUsuario());
            pst.setString(2, usu.getUsername());
            pst.setString(3, usu.getPsw());
            pst.setInt(4, usu.getTipoUsuario().getIdTipoUsuario());
            //Capturamos el numero de filas que se ingresaran
            int nFilas = pst.executeUpdate();

            //Creamos la respuesta a devolver
            if (nFilas == 1) {
                resp = "Se ha ingresado " + nFilas + " regitro.";
            } else {
                resp = "Se han ingresado " + nFilas + " regitros.";
            }
            pst.close();
            cn.close();
        } catch (Exception e) {
            resp = e.toString();
        } finally {
            this.conexion.desconectar();
        }
        return resp;
    }

    @Override
    public String modificar(Object obj) {
        String resp = null;
        String sql;
        try {
            //Casteamos en  Usuario el objeto que se reciba
            Usuario usu = (Usuario) obj;
            Connection cn = this.conexion.conectar();
            sql = "UPDATE usuarios SET username=?, idTipoUsuario=? WHERE idUsuario  =?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, usu.getUsername());
            pst.setString(2, usu.getPsw());
            pst.setInt(3, usu.getTipoUsuario().getIdTipoUsuario());
            pst.setInt(4, usu.getIdUsuario());

            //Capturamos el numero de filas que se modificaran
            int nFilas = pst.executeUpdate();

            //Creamos la respuesta a devolver
            if (nFilas == 1) {
                resp = "Se ha modificado " + nFilas + " regitro.";
            } else {
                resp = "Se han modificado " + nFilas + " regitros.";
            }
            pst.close();
            cn.close();
        } catch (Exception e) {
            resp = e.toString();
        } finally {
            this.conexion.desconectar();
        }
        return resp;
    }

    //Se crea un metodo en caso que se quiera cambiar la contrasena de algun usuario
    public String modificarPsw(Object obj) {
        String resp = null;
        String sql;
        try {
            //Casteamos en  Usuario el objeto que se reciba
            Usuario usu = (Usuario) obj;
            Connection cn = this.conexion.conectar();
            sql = "UPDATE usuarios SET username=?, psw=SHA1(?),idTipoUsuario=? WHERE idUsuario  =?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, usu.getUsername());
            pst.setString(2, usu.getPsw());
            pst.setInt(3, usu.getTipoUsuario().getIdTipoUsuario());
            pst.setInt(4, usu.getIdUsuario());

            //Capturamos el numero de filas que se modificaran
            int nFilas = pst.executeUpdate();

            //Creamos la respuesta a devolver
            if (nFilas == 1) {
                resp = "Se ha modificado " + nFilas + " regitro.";
            } else {
                resp = "Se han modificado " + nFilas + " regitros.";
            }
            pst.close();
            cn.close();
        } catch (Exception e) {
            resp = e.toString();
        } finally {
            this.conexion.desconectar();
        }
        return resp;
    }

    @Override
    public String eliminar(Object obj) {
        String resp = null;
        String sql;
        try {
            //Casteamos en  Usuario el objeto que se reciba
            Usuario usu = (Usuario) obj;
            Connection cn = this.conexion.conectar();
            sql = "DELETE FROM usuarios WHERE idUsuario=?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, usu.getIdUsuario());

            //Capturamos el numero de filas que se eliminaran
            int nFilas = pst.executeUpdate();

            //Creamos la respuesta a devolver
            if (nFilas == 1) {
                resp = "Se ha eliminado " + nFilas + " regitro.";
            } else {
                resp = "Se han eliminado " + nFilas + " regitros.";
            }
            pst.close();
            cn.close();
        } catch (Exception e) {
            resp = e.toString();
        } finally {
            this.conexion.desconectar();
        }
        return resp;
    }

    @Override
    public List<?> consultar() {
        List<Usuario> lst = new ArrayList();
        try {
            Connection cn = this.conexion.conectar();
            Statement sentencia = cn.createStatement();
            ResultSet rs = sentencia.executeQuery("SELECT * FROM usuarios");
            while (rs.next()) {
                TipoUsuario tusu = new TipoUsuario();
                tusu.setIdTipoUsuario(rs.getInt(4));
                lst.add(new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), tusu));
            }
            rs.close();
            sentencia.close();
            cn.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            this.conexion.desconectar();
        }

        return lst;
    }

    @Override
    public List<?> buscar(Object obj) {
        List<Usuario> lst = new ArrayList();
        String sql;
        try {
            //Casteamos en Tipo de Usuario el objeto que se reciba
            Usuario usu = (Usuario) obj;
            Connection cn = this.conexion.conectar();
            sql = "SELECT * FROM usuarios WHERE idUsuario=?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, usu.getIdUsuario());

            //Capturamos el objeto encontrado
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                TipoUsuario tusu = new TipoUsuario();
                tusu.setIdTipoUsuario(rs.getInt(4));
                lst.add(new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), tusu));
            }
            rs.close();
            pst.close();
            cn.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            this.conexion.desconectar();
        }

        return lst;
    }

}
