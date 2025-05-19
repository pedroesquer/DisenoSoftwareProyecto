/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.rutappbo.implementaciones;

import itson.rutappbo.IViajesBO;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import itson.persistenciarutapp.IViajesDAO;
import itson.persistenciarutapp.implementaciones.ManejadorConexiones;
import itson.rutappdto.ViajeDTO;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author chris
 */
public class ViajesBO implements IViajesBO{
    
    private final String COLECCION = "viajes";

    @Override
    public List<ViajeDTO> consultarViajesPorOrigenDestinoYFecha(String origen, String destino, LocalDateTime fecha) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<ViajeDTO> coleccion = baseDatos.getCollection(COLECCION, ViajeDTO.class);

        // Convertir LocalDateTime a rango de fechas
        Date inicioDia = Date.from(fecha.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date finDia = Date.from(fecha.toLocalDate().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<ViajeDTO> resultados = coleccion.find(and(
                eq("origen", origen),
                eq("destino", destino),
                gte("fechaHora", inicioDia),
                lt("fechaHora", finDia)
        )).into(new ArrayList<>());

        return resultados;
    }

    @Override
    public List<ViajeDTO> consultarTodos() {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<ViajeDTO> coleccion = baseDatos.getCollection(COLECCION, ViajeDTO.class);
        return coleccion.find().into(new ArrayList<>());
    }

    @Override
    public void insertarViaje(ViajeDTO viaje) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<ViajeDTO> coleccion = baseDatos.getCollection(COLECCION, ViajeDTO.class);
        coleccion.insertOne(viaje);
    }
}
