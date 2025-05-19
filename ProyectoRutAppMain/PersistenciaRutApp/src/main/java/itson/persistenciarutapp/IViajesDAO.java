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

    ViajeDTO agregarViaje(ViajeDTO viaje);

    List<ViajeDTO> consultarViajesPorOrigenDestinoYFecha(String origen, String destino, String fecha);

    ViajeDTO consultarViajePorId(String idViaje);

    List<ViajeDTO> obtenerTodosLosViajes();
}
