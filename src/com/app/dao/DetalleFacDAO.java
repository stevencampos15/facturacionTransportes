package com.app.dao;

import com.app.conexion.ConexionBD;
import static com.app.dao.PaisDAO.MYSQL_DUPLICATE_PK;
import com.app.modelo.DetalleFactura;
import com.app.modelo.FacturaEncabezado;
import com.app.modelo.Producto;
import com.app.modelo.TipoVenta;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Steven
 */
public class DetalleFacDAO implements Operaciones {

    private ConexionBD conexion = new ConexionBD();

    @Override
    public String insertar(Object obj) {
        String resp = null;
        String sql;
        try {
            //Casteamos en DetalleFactura el objeto que se reciba
            DetalleFactura df = (DetalleFactura) obj;
            Connection cn = this.conexion.conectar();
            sql = "INSERT INTO detalle_factura VALUES(?,?,?,?,?,?,?)";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, df.getNoDetalle());
            pst.setInt(2, df.getFactura().getNoFactura());
            pst.setInt(3, df.getProducto().getIdProducto());
            pst.setInt(4, df.getCantidad());
            pst.setFloat(5, df.getPrecioUnitario());
            pst.setString(6, df.getDescripcionFactura());
            pst.setInt(7, df.getTipoventa().getIdTipoVenta());

            //Capturamos el numero de filas que se ingresaran
            int nFilas = pst.executeUpdate();

            //Creamos la respuesta a devolver
            if (nFilas == 1) {
                resp = "Se ha ingresado " + nFilas + " registro.";
            } else {
                resp = "Se han ingresado " + nFilas + " registros.";
            }
            pst.close();
            cn.close();
        } catch (SQLException e) {
            if (e.getErrorCode() == MYSQL_DUPLICATE_PK) {
                //duplicate primary key 
                resp = "El numero de ID ya existe";
            } else {
                resp = e.getMessage();
            }
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
            //Casteamos en DetalleFactura el objeto que se reciba
            DetalleFactura df = (DetalleFactura) obj;
            Connection cn = this.conexion.conectar();
            sql = "UPDATE detalle_factura SET idProducto =?, cantidad =?, "
                    + "precioUnitario=?, descripcionFactura=?, idTipoVenta=? "
                    + "WHERE noDetalle =? AND noFactura =?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, df.getProducto().getIdProducto());
            pst.setInt(2, df.getCantidad());
            pst.setFloat(3, df.getPrecioUnitario());
            pst.setString(4, df.getDescripcionFactura());
            pst.setInt(5, df.getTipoventa().getIdTipoVenta());
            pst.setInt(6, df.getNoDetalle());
            pst.setInt(7, df.getFactura().getNoFactura());

            //Capturamos el numero de filas que se modificaran
            int nFilas = pst.executeUpdate();

            //Creamos la respuesta a devolver
            if (nFilas == 1) {
                resp = "Se ha modificado " + nFilas + " registro.";
            } else {
                resp = "Se han modificado " + nFilas + " registros.";
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
            //Casteamos en DetalleFactura el objeto que se reciba
            DetalleFactura df = (DetalleFactura) obj;
            Connection cn = this.conexion.conectar();
            sql = "DELETE FROM detalle_factura WHERE noDetalle =? AND noFactura=?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, df.getNoDetalle());
            pst.setInt(2, df.getFactura().getNoFactura());

            //Capturamos el numero de filas que se eliminaran
            int nFilas = pst.executeUpdate();

            //Creamos la respuesta a devolver
            if (nFilas == 1) {
                resp = "Se ha eliminado " + nFilas + " registro.";
            } else {
                resp = "Se han eliminado " + nFilas + " registros.";
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
        List<DetalleFactura> lst = new ArrayList();
        try {
            Connection cn = this.conexion.conectar();
            Statement sentencia = cn.createStatement();
            ResultSet rs = sentencia.executeQuery("SELECT * FROM detalle_factura");
            while (rs.next()) {
                FacturaEncabezado fe = new FacturaEncabezado();
                fe.setNoFactura(rs.getInt(2));

                Producto prod = new Producto();
                prod.setIdProducto(rs.getInt(3));
                
                TipoVenta tp = new TipoVenta();
                tp.setIdTipoVenta(rs.getInt(7));

                lst.add(new DetalleFactura(rs.getInt(1), fe, prod, rs.getInt(4), rs.getFloat(5), rs.getString(6), tp));
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
        List<DetalleFactura> lst = new ArrayList();
        String sql;
        try {
            //Casteamos en DetalleFactura el objeto que se reciba
            DetalleFactura df = (DetalleFactura) obj;
            Connection cn = this.conexion.conectar();
            sql = "SELECT * FROM detalle_factura WHERE noFactura =?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, df.getFactura().getNoFactura());

            //Capturamos el objeto encontrado
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                FacturaEncabezado fe = new FacturaEncabezado();
                fe.setNoFactura(rs.getInt(2));

                Producto prod = new Producto();
                prod.setIdProducto(rs.getInt(3));
                
                TipoVenta tp = new TipoVenta();
                tp.setIdTipoVenta(rs.getInt(7));

                lst.add(new DetalleFactura(rs.getInt(1), fe, prod, rs.getInt(4), rs.getFloat(5), rs.getString(6), tp));
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
