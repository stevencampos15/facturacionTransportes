
package com.app.dao;

import java.util.List;

/**
 *
 * @author Steven
 */
public interface Operaciones {
    //Metodos
    public String insertar(Object obj);
    public String modificar(Object obj);
    public String eliminar(Object obj);
    public List<?> consultar();
    public Object buscar(Object obj);
}
