package com.app.dao;

import com.app.conexion.ConexionBD;
import com.app.modelo.Pais;
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
public class PaisDAO implements Operaciones {

    private ConexionBD conexion = new ConexionBD();
    public static final int MYSQL_DUPLICATE_PK = 1062;

    @Override
    public String insertar(Object obj) {
        String resp = null;
        String sql;
        try {
            //Casteamos en Pais el objeto que se reciba
            Pais pais = (Pais) obj;
            Connection cn = this.conexion.conectar();
            sql = "INSERT INTO paises VALUES(?,?)";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, pais.getIdPais());
            pst.setString(2, pais.getNombrePais());

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
            //Casteamos en Pais el objeto que se reciba
            Pais pais = (Pais) obj;
            Connection cn = this.conexion.conectar();
            sql = "UPDATE paises SET nombrePais=? WHERE idPais=?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, pais.getNombrePais());
            pst.setInt(2, pais.getIdPais());

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
            //Casteamos en Pais el objeto que se reciba
            Pais pais = (Pais) obj;
            Connection cn = this.conexion.conectar();
            sql = "DELETE FROM paises WHERE idPais=?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, pais.getIdPais());

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
        List<Pais> lst = new ArrayList();
        try {
            Connection cn = this.conexion.conectar();
            Statement sentencia = cn.createStatement();
            ResultSet rs = sentencia.executeQuery("SELECT * FROM paises");
            while (rs.next()) {
                lst.add(new Pais(rs.getInt(1), rs.getString(2)));
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
        Pais paisObj = null;
        String sql;
        try {
            //Casteamos en Pais el objeto que se reciba
            Pais pais = (Pais) obj;
            Connection cn = this.conexion.conectar();
            sql = "SELECT * FROM paises WHERE idPais=?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, pais.getIdPais());

            //Capturamos el objeto encontrado
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                paisObj = new Pais(rs.getInt(1), rs.getString(2));
            }
            rs.close();
            pst.close();
            cn.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            this.conexion.desconectar();
        }

        return paisObj;
    }

    public int siguienteId() {
        int id = 0;
        try {
            Connection cn = this.conexion.conectar();
            String sql = "SELECT MAX(idPais) FROM paises;";
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
