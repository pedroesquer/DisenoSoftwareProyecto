/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.rutappbo;

import itson.persistenciarutapp.implementaciones.Viaje;
import itson.rutappdto.AsientoBoletoDTO;
import itson.rutappdto.UsuarioDTO;
import java.util.List;

/**
 *
 * @author pedro
 */
public interface IComprasBO {
    void agregarCompra(UsuarioDTO usuarioDTO, Viaje viaje, List<AsientoBoletoDTO> asientosDTO);
        
    
}
