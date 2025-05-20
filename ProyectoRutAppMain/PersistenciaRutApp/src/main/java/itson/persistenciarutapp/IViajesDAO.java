/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.persistenciarutapp;

import itson.rutappdto.ViajeDTO;
import java.util.List;

/**
 *
 * @author chris
 */
public interface IViajesDAO {

    List<ViajeDTO> consultarViajesPorOrigenDestinoYFecha(ViajeDTO viaje);

    ViajeDTO consultarViajePorId(String idViaje);

    List<ViajeDTO> obtenerTodosLosViajes();
    
    
}
