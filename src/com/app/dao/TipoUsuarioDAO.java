/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.dao;

import com.app.conexion.ConexionBD;
import com.app.modelo.TipoUsuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Steven
 */
public class TipoUsuarioDAO implements Operaciones {

    private ConexionBD conexion = new ConexionBD();

    @Override
    public String insertar(Object obj) {
        String resp = null;
        String sql;
        try {
            //Casteamos en Tipo de Usuario el objeto que se reciba
            TipoUsuario tu = (TipoUsuario) obj;
            Connection cn = this.conexion.conectar();
            sql = "INSERT INTO tipos_usuarios VALUES(?,?,?)";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, tu.getIdTipoUsuario());
            pst.setString(2, tu.getNombreTU());
            pst.setString(3, tu.getDescripcionTU());

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
            //Casteamos en Tipo de Usuario el objeto que se reciba
            TipoUsuario tu = (TipoUsuario) obj;
            Connection cn = this.conexion.conectar();
            sql = "UPDATE tipos_usuarios SET nombreTU=?, descripcionTU=? WHERE idTipoUsuario =?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, tu.getNombreTU());
            pst.setString(2, tu.getDescripcionTU());
            pst.setInt(3, tu.getIdTipoUsuario());

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
            //Casteamos en Tipo de Usuario el objeto que se reciba
            TipoUsuario tu = (TipoUsuario) obj;
            Connection cn = this.conexion.conectar();
            sql = "DELETE FROM tipos_usuarios WHERE idTipoUsuario =?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, tu.getIdTipoUsuario());

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
        List<TipoUsuario> lst = new ArrayList();
        try {
            Connection cn = this.conexion.conectar();
            Statement sentencia = cn.createStatement();
            ResultSet rs = sentencia.executeQuery("SELECT * FROM tipos_usuarios");
            while (rs.next()) {
                lst.add(new TipoUsuario(rs.getInt(1), rs.getString(2), rs.getString(3)));
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


    //Busca Objeto en Especifico
    @Override
    public Object buscar(Object obj) {
        TipoUsuario tuObj = null;
        String sql;
        try {
            //Casteamos en Tipo de Usuario el objeto que se reciba
            TipoUsuario tu = (TipoUsuario) obj;
            Connection cn = this.conexion.conectar();
            sql = "SELECT * FROM tipos_usuarios WHERE idTipoUsuario =?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, tu.getIdTipoUsuario());

            //Capturamos el objeto encontrado
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                tuObj = (new TipoUsuario(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
            rs.close();
            pst.close();
            cn.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            this.conexion.desconectar();
        }

        return tuObj;
    }

}
