/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.rutappbo;

import itson.rutappdto.ViajeDTO;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author chris
 */
public interface IViajesBO {

    List<ViajeDTO> consultarViajesPorOrigenDestinoYFecha(String origen, String destino, LocalDateTime fecha);

    List<ViajeDTO> consultarTodos();

    void insertarViaje(ViajeDTO viaje);
}
