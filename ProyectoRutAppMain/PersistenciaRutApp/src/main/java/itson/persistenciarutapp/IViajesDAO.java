/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.persistenciarutapp;

import itson.persistenciarutapp.implementaciones.Viaje;
import itson.rutappdto.ViajeDTO;
import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author chris
 */
public interface IViajesDAO {

   public List<Viaje> consultarViajesPorOrigenDestinoYFecha(String origen, String destino, Date fecha);

    public Viaje consultarViajePorId(String idViaje);

    public List<Viaje> obtenerTodosLosViajes();
    
    List<String> obtenerDestinosPorOrigen(String origen);
    
    Viaje consultarViajePorId(ObjectId idViaje);
    
}
