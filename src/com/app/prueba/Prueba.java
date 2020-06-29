
package com.app.prueba;

import com.app.dao.*;
import com.app.modelo.*;
import java.util.List;

/**
 *
 * @author Steven
 */
public class Prueba {
    public static void main(String[] args) {
        Usuario us = new Usuario();
        TipoUsuario tu2 = new TipoUsuario();
        tu2.setIdTipoUsuario(2);
        tu2.setNombreTU("Bue");
        UsuarioDAO usdao = new UsuarioDAO();
        TipoUsuarioDAO tudao = new TipoUsuarioDAO();
        //Insertar
        us.setIdUsuario(3);
        us.setUsername("Administrador");
        us.setPsw("123");
        us.setTipoUsuario(tu2);
        System.out.println(usdao.insertar(us));
        
        //Buscar
        Usuario usu2 = new Usuario();
        usu2.setIdUsuario(2);
        List<Usuario> lstBuscar = (List<Usuario>)usdao.buscar(usu2);
        for (Usuario tipoUsuario : lstBuscar) {
            System.out.println(tipoUsuario.getIdUsuario()+ " " + tipoUsuario.getUsername());
        }
        System.out.println(" ");
        
        //Consultar
//        System.out.println("Lista completa:");
//        List<Usuario> lst = (List<Usuario>) usdao.consultar();
//        Object pruebaObj;
//        for(Usuario tusu : lst){
//            System.out.println(tusu.getIdUsuario() +" "+
//                    tusu.getUsername() + " " +
//                    tusu.getPsw() + " " +
//                    tusu.getTipoUsuario().getIdTipoUsuario()+ " "
//                    + ((TipoUsuario) (pruebaObj = tudao.buscarObj(tusu.getTipoUsuario()))).getNombreTU());
//        }
        
    }
}
