/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.rutappbo;

import itson.persistenciarutapp.implementaciones.Boleto;
import itson.persistenciarutapp.implementaciones.Viaje;
import itson.rutappdto.AsientoBoletoDTO;
import itson.rutappdto.UsuarioDTO;
import java.util.List;

/**
 *
 * @author chris
 */
public interface IBoletoBO {
    void guardarBoletoDesdeContexto(UsuarioDTO usuarioDTO, Viaje viaje, List<AsientoBoletoDTO> asientosDTO);
}
