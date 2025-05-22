/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.persistenciarutapp;

import itson.persistenciarutapp.implementaciones.Compra;
import itson.rutappdto.UsuarioDTO;
import java.util.List;

/**
 *
 * @author pedro
 */
public interface IComprasDAO {

    Compra agregarCompras(Compra nuevaCompra);
    
    List<Compra> consultarCompraPorUsuario(UsuarioDTO usuario);
    
//    List<Compra> consultarComprasNoVencidasPorUsuario(UsuarioDTO usuario);
    
    
    
}
