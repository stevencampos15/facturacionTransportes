package com.app.dao;

import com.app.conexion.ConexionBD;
import com.app.modelo.Cliente;
import com.app.modelo.CondicionPago;
import com.app.modelo.FacturaEncabezado;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Steven
 */
public class FacturaEncDAO implements Operaciones {

    private ConexionBD conexion = new ConexionBD();

    @Override
    public String insertar(Object obj) {
        String resp = null;
        String sql;
        try {
            //Casteamos en FacturaEncabezado el objeto que se reciba
            FacturaEncabezado fe = (FacturaEncabezado) obj;
            Connection cn = this.conexion.conectar();
            sql = "INSERT INTO factura_enc VALUES(?,?,?,?,?,?)";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, fe.getNoFactura());
            pst.setDate(2, (Date) fe.getFecha());
            pst.setString(3, fe.getFechaNotaRemision());
            pst.setString(4, fe.getaCuentaDe());
            pst.setInt(5, fe.getCondicionPago().getIdCP());
            pst.setInt(6, fe.getCliente().getIdCliente());

            //Capturamos el numero de filas que se ingresaran
            int nFilas = pst.executeUpdate();

            //Creamos la respuesta a devolver
            if (nFilas == 1) {
                resp = "Se ha ingresado " + nFilas + " registro.";
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
            //Casteamos en FacturaEncabezado el objeto que se reciba
            FacturaEncabezado fe = (FacturaEncabezado) obj;
            Connection cn = this.conexion.conectar();
            sql = "UPDATE factura_enc SET fecha=?, fechaNotaRemision=?, aCuentaDe=?,"
                    + "idCP  =?, idCliente =? "
                    + "WHERE noFactura =?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setDate(1, (Date) fe.getFecha());
            pst.setString(2, fe.getFechaNotaRemision());
            pst.setString(3, fe.getaCuentaDe());
            pst.setInt(4, fe.getCondicionPago().getIdCP());
            pst.setInt(5, fe.getCliente().getIdCliente());
            pst.setInt(6, fe.getNoFactura());

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
            //Casteamos en FacturaEncabezado el objeto que se reciba
            FacturaEncabezado fe = (FacturaEncabezado) obj;
            Connection cn = this.conexion.conectar();
            sql = "DELETE FROM factura_enc WHERE noFactura=?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, fe.getNoFactura());

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
        List<FacturaEncabezado> lst = new ArrayList();
        try {
            Connection cn = this.conexion.conectar();
            Statement sentencia = cn.createStatement();
            ResultSet rs = sentencia.executeQuery("SELECT * FROM vista_listaFacturas");
            while (rs.next()) {
                FacturaEncabezado fe = new FacturaEncabezado();
                fe.setNoFactura(rs.getInt(1));
                fe.setFecha(rs.getDate(2));
                
                Cliente cli = new Cliente();
                cli.setIdCliente(rs.getInt(3));
                cli.setNit(rs.getString(4));
                cli.setNombreCliente(rs.getString(5));
                
                fe.setCliente(cli);
                
                CondicionPago cp = new CondicionPago();
                cp.setIdCP(rs.getInt(6));
                cp.setNombreTipoCP(rs.getString(7));
                
                fe.setCondicionPago(cp);
                
                lst.add(fe);
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
        FacturaEncabezado nObj = null;
        String sql;
        try {
            //Casteamos en FacturaEncabezado el objeto que se reciba
            FacturaEncabezado fe = (FacturaEncabezado) obj;
            Connection cn = this.conexion.conectar();
            sql = "SELECT * FROM factura_enc WHERE noFactura =?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, fe.getNoFactura());

            //Capturamos el objeto encontrado
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                CondicionPago cp = new CondicionPago();
                cp.setIdCP(rs.getInt(5));

                Cliente cli = new Cliente();
                cli.setIdCliente(rs.getInt(6));

                nObj = new FacturaEncabezado(rs.getInt(1), rs.getDate(2), rs.getString(3), rs.getString(4), cp, cli);
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
