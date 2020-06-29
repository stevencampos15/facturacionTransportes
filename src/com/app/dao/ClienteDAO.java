package com.app.dao;

import com.app.conexion.ConexionBD;
import com.app.modelo.Cliente;
import com.app.modelo.Departamento;
import com.app.modelo.Giro;
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
public class ClienteDAO implements Operaciones {

    private ConexionBD conexion = new ConexionBD();

    @Override
    public String insertar(Object obj) {
        String resp = null;
        String sql;
        try {
            //Casteamos en Cliente el objeto que se reciba
            Cliente cli = (Cliente) obj;
            Connection cn = this.conexion.conectar();
            sql = "INSERT INTO clientes VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, cli.getIdCliente());
            pst.setString(2, cli.getNit());
            pst.setString(3, cli.getNombreCliente());
            pst.setString(4, cli.getTelefonoCliente());
            pst.setString(5, cli.getDescripcionCliente());
            pst.setString(6, cli.getRegistro());
            pst.setString(7, cli.getDireccionCliente());
            pst.setInt(8, cli.getDepartamento().getIdDepartamento());
            pst.setInt(9, cli.getGiro().getIdGiro());

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
            //Casteamos en  Cliente el objeto que se reciba
            Cliente cli = (Cliente) obj;
            Connection cn = this.conexion.conectar();
            sql = "UPDATE clientes SET nit=?, nombreCliente=?, telefonoCliente=? ,descripcionCliente=?,"
                    + "registro =?, direccionCliente =?, idDepartamento =?, idGiro =? "
                    + "WHERE idCliente =?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, cli.getNit());
            pst.setString(2, cli.getNombreCliente());
            pst.setString(3, cli.getTelefonoCliente());
            pst.setString(4, cli.getDescripcionCliente());
            pst.setString(5, cli.getRegistro());
            pst.setString(6, cli.getDireccionCliente());
            pst.setInt(7, cli.getDepartamento().getIdDepartamento());
            pst.setInt(8, cli.getGiro().getIdGiro());
            pst.setInt(9, cli.getIdCliente());

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
            //Casteamos en  Cliente el objeto que se reciba
            Cliente cli = (Cliente) obj;
            Connection cn = this.conexion.conectar();
            sql = "DELETE FROM clientes WHERE idCliente=?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, cli.getIdCliente());

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
        List<Cliente> lst = new ArrayList();
        try {
            Connection cn = this.conexion.conectar();
            Statement sentencia = cn.createStatement();
            ResultSet rs = sentencia.executeQuery("SELECT * FROM clientes");
            while (rs.next()) {
                Departamento dep = new Departamento();
                dep.setIdDepartamento(rs.getInt(8));

                Giro giro = new Giro();
                giro.setIdGiro(rs.getInt(9));

                lst.add(new Cliente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), dep, giro));
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
        Cliente tuObj = null;
        String sql;
        try {
            //Casteamos en Cliente el objeto que se reciba
            Cliente cli = (Cliente) obj;
            Connection cn = this.conexion.conectar();
            sql = "SELECT * FROM clientes WHERE idCliente=?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, cli.getIdCliente());

            //Capturamos el objeto encontrado
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Departamento dep = new Departamento();
                dep.setIdDepartamento(rs.getInt(8));

                Giro giro = new Giro();
                giro.setIdGiro(rs.getInt(9));

                tuObj = new Cliente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), dep, giro);
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
