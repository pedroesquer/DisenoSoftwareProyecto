/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.rutappbo;

import itson.persistenciarutapp.entidades.Viaje;
import itson.rutappdto.ViajeDTO;
import java.util.List;

/**
 *
 * @author chris
 */
public interface IViajesBO {

    List<ViajeDTO> consultarViajesPorOrigenDestinoYFecha(ViajeDTO viaje);

    List<ViajeDTO> consultarTodos();

    List<String> obtenerDestinos(String origen);

}
