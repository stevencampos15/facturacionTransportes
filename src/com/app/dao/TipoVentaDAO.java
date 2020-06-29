package com.app.dao;

import com.app.conexion.ConexionBD;
import com.app.modelo.TipoVenta;
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
public class TipoVentaDAO implements Operaciones {

    private ConexionBD conexion = new ConexionBD();

    @Override
    public String insertar(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String modificar(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String eliminar(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<?> consultar() {
        List<TipoVenta> lst = new ArrayList();
        try {
            Connection cn = this.conexion.conectar();
            Statement sentencia = cn.createStatement();
            ResultSet rs = sentencia.executeQuery("SELECT * FROM tipoVenta");
            while (rs.next()) {
                lst.add(new TipoVenta(rs.getInt(1), rs.getString(2)));
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
        TipoVenta nObj = null;
        String sql;
        try {
            //Casteamos en CondicionPago el objeto que se reciba
            TipoVenta tp = (TipoVenta) obj;
            Connection cn = this.conexion.conectar();
            sql = "SELECT * FROM tipoVenta WHERE idTipoVenta =?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, tp.getIdTipoVenta());

            //Capturamos el objeto encontrado
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                nObj = (new TipoVenta(rs.getInt(1), rs.getString(2)));
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
