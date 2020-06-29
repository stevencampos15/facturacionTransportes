package com.app.dao;

import com.app.conexion.ConexionBD;
import static com.app.dao.PaisDAO.MYSQL_DUPLICATE_PK;
import com.app.modelo.Departamento;
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
public class DepartamentoDAO implements Operaciones {

    private ConexionBD conexion = new ConexionBD();

    @Override
    public String insertar(Object obj) {
        String resp = null;
        String sql;
        try {
            //Casteamos en Departamento el objeto que se reciba
            Departamento dep = (Departamento) obj;
            Connection cn = this.conexion.conectar();
            sql = "INSERT INTO departamentos VALUES(?,?,?)";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, dep.getIdDepartamento());
            pst.setString(2, dep.getNombreDepartamento());
            pst.setInt(3, dep.getPais().getIdPais());

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
            //Casteamos en  Departamento el objeto que se reciba
            Departamento dep = (Departamento) obj;
            Connection cn = this.conexion.conectar();
            sql = "UPDATE departamentos SET nombreDepartamento=?, idPais=? WHERE idDepartamento=?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, dep.getNombreDepartamento());
            pst.setInt(2, dep.getPais().getIdPais());
            pst.setInt(3, dep.getIdDepartamento());

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
            //Casteamos en  Departamento el objeto que se reciba
            Departamento dep = (Departamento) obj;
            Connection cn = this.conexion.conectar();
            sql = "DELETE FROM departamentos WHERE idDepartamento=?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, dep.getIdDepartamento());

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
        List<Departamento> lst = new ArrayList();
        try {
            Connection cn = this.conexion.conectar();
            Statement sentencia = cn.createStatement();
            ResultSet rs = sentencia.executeQuery("SELECT * FROM departamentos");
            while (rs.next()) {
                Pais pais = new Pais();
                pais.setIdPais(rs.getInt(3));
                lst.add(new Departamento(rs.getInt(1), rs.getString(2), pais));
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
        Departamento tuObj = null;
        String sql;
        try {
            //Casteamos en Departamento el objeto que se reciba
            Departamento dep = (Departamento) obj;
            Connection cn = this.conexion.conectar();
            sql = "SELECT * FROM departamentos WHERE idDepartamento=?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, dep.getIdDepartamento());

            //Capturamos el objeto encontrado
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Pais pais = new Pais();
                pais.setIdPais(rs.getInt(3));
                tuObj = new Departamento(rs.getInt(1), rs.getString(2), pais);
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
    
    public int siguienteId() {
        int id = 0;
        try {
            Connection cn = this.conexion.conectar();
            String sql = "SELECT MAX(idDepartamento) FROM departamentos;";
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
