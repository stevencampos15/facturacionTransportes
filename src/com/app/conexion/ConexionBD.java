package com.app.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Steven
 */
public class ConexionBD {
    private Connection cn;
    public ConexionBD(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(ClassNotFoundException ex){
            System.out.println("Error");
        }
    }
    public Connection conectar(){
        String usr="root";
        String password="1995";
        String url="jdbc:mysql://localhost:3306/BDTransporteVillalta";
        try{
            this.cn = DriverManager.getConnection(url, usr, password);
        }
        catch (SQLException e){
            System.out.println("Error");
        }
        return this.cn;
    }
    public void desconectar(){
        try {
            this.cn.close();
        }
        catch (SQLException ex) {
            System.out.println("Error");
        }
    }
}
