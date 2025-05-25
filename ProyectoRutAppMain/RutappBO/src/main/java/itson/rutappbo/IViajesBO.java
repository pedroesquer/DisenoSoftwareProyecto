/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.rutappbo;

import Entidades.Viaje;
import itson.rutappdto.ViajeDTO;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author chris
 */
public interface IViajesBO {

    List<ViajeDTO> consultarViajesPorOrigenDestinoYFecha(ViajeDTO viaje);

    List<ViajeDTO> consultarTodos();

    void insertarViaje(Viaje viaje);
    
    List<String> obtenerDestinos(String origen);

}
