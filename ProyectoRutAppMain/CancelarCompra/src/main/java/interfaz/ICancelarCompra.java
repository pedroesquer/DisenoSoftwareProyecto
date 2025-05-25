/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaz;

import itson.rutappdto.CompraDTO;
import itson.rutappdto.UsuarioDTO;
import java.util.List;

/**
 *
 * @author chris
 */
public interface ICancelarCompra {

    public void eliminarCompra(CompraDTO compra);

    List<CompraDTO> obtenerCompras(UsuarioDTO usuario);
    
}
