package com.app.dao;

import com.app.conexion.ConexionBD;
import com.app.modelo.CondicionPago;
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
public class CondicionPagoDAO implements Operaciones {

    private ConexionBD conexion = new ConexionBD();

    @Override
    public String insertar(Object obj) {
        String resp = null;
        String sql;
        try {
            //Casteamos en CondicionPago el objeto que se reciba
            CondicionPago cp = (CondicionPago) obj;
            Connection cn = this.conexion.conectar();
            sql = "INSERT INTO condiciones_pago VALUES(?,?,?)";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, cp.getIdCP());
            pst.setString(2, cp.getNombreTipoCP());
            pst.setString(3, cp.getDescripcionCP());

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
            //Casteamos en CondicionPago el objeto que se reciba
            CondicionPago cp = (CondicionPago) obj;
            Connection cn = this.conexion.conectar();
            sql = "UPDATE condiciones_pago SET nombreTipoCP=?, descripcionCP=? WHERE idCP=?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, cp.getNombreTipoCP());
            pst.setString(2, cp.getDescripcionCP());
            pst.setInt(3, cp.getIdCP());

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
            //Casteamos en CondicionPago el objeto que se reciba
            CondicionPago cp = (CondicionPago) obj;
            Connection cn = this.conexion.conectar();
            sql = "DELETE FROM condiciones_pago WHERE idCP=?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, cp.getIdCP());

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
        List<CondicionPago> lst = new ArrayList();
        try {
            Connection cn = this.conexion.conectar();
            Statement sentencia = cn.createStatement();
            ResultSet rs = sentencia.executeQuery("SELECT * FROM condiciones_pago");
            while (rs.next()) {
                lst.add(new CondicionPago(rs.getInt(1), rs.getString(2), rs.getString(3)));
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
    public Object buscar(Object obj) {
        CondicionPago nObj = null;
        String sql;
        try {
            //Casteamos en CondicionPago el objeto que se reciba
            CondicionPago cp = (CondicionPago) obj;
            Connection cn = this.conexion.conectar();
            sql = "SELECT * FROM condiciones_pago WHERE idCP =?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, cp.getIdCP());

            //Capturamos el objeto encontrado
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                nObj = (new CondicionPago(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
            rs.close();
            pst.close();
            cn.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            this.conexion.desconectar();
        }

        return nObj;
    }

}
