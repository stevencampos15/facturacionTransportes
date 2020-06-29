package com.app.dao;

import com.app.conexion.ConexionBD;
import static com.app.dao.PaisDAO.MYSQL_DUPLICATE_PK;
import com.app.modelo.Producto;
import java.sql.Connection;
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
public class ProductoDAO implements Operaciones {

    private ConexionBD conexion = new ConexionBD();

    @Override
    public String insertar(Object obj) {
        String resp = null;
        String sql;
        try {
            //Casteamos en Producto el objeto que se reciba
            Producto prod = (Producto) obj;
            Connection cn = this.conexion.conectar();
            sql = "INSERT INTO productos VALUES(?,?,?)";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, prod.getIdProducto());
            pst.setString(2, prod.getNombreProducto());
            pst.setString(3, prod.getDescripcionProducto());

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
            //Casteamos en Producto el objeto que se reciba
            Producto prod = (Producto) obj;
            Connection cn = this.conexion.conectar();
            sql = "UPDATE productos SET nombreProducto=?, descripcionProducto=? WHERE idProducto =?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, prod.getNombreProducto());
            pst.setString(2, prod.getDescripcionProducto());
            pst.setInt(3, prod.getIdProducto());

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
            //Casteamos en Producto el objeto que se reciba
            Producto prod = (Producto) obj;
            Connection cn = this.conexion.conectar();
            sql = "DELETE FROM productos WHERE idProducto  =?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, prod.getIdProducto());

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
        List<Producto> lst = new ArrayList();
        try {
            Connection cn = this.conexion.conectar();
            Statement sentencia = cn.createStatement();
            ResultSet rs = sentencia.executeQuery("SELECT * FROM productos");
            while (rs.next()) {
                lst.add(new Producto(rs.getInt(1), rs.getString(2), rs.getString(3)));
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
        Producto nObj = null;
        String sql;
        try {
            //Casteamos en Producto el objeto que se reciba
            Producto prod = (Producto) obj;
            Connection cn = this.conexion.conectar();
            sql = "SELECT * FROM productos WHERE idProducto=?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, prod.getIdProducto());

            //Capturamos el objeto encontrado
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                nObj = (new Producto(rs.getInt(1), rs.getString(2), rs.getString(3)));
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
    
    public int siguienteId() {
        int id = 0;
        try {
            Connection cn = this.conexion.conectar();
            String sql = "SELECT MAX(idProducto) FROM productos;";
            PreparedStatement pst = cn.prepareStatement(sql);

            //Capturamos el objeto encontrado
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1) + 1;
            }
            rs.close();
            pst.close();
            cn.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            this.conexion.desconectar();
        }
        return id;
    }

}
