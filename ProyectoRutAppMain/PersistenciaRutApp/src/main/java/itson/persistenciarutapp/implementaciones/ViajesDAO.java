/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.persistenciarutapp.implementaciones;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import itson.persistenciarutapp.IViajesDAO;
import itson.rutappdto.ViajeDTO;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author chris
 */
public class ViajesDAO implements IViajesDAO {
    
    private final String COLECCION = "viajes";

    @Override
    public ViajeDTO agregarViaje(ViajeDTO viaje) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<ViajeDTO> coleccion = baseDatos.getCollection(COLECCION, ViajeDTO.class);
        coleccion.insertOne(viaje);
        return viaje;
    }

    @Override
    public List<ViajeDTO> consultarViajesPorOrigenDestinoYFecha(String origen, String destino, String fechaStr) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<ViajeDTO> coleccion = baseDatos.getCollection(COLECCION, ViajeDTO.class);

        LocalDate fecha = LocalDate.parse(fechaStr); // formato: "yyyy-MM-dd"
        Date desde = Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date hasta = Date.from(fecha.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        return coleccion.find(and(
            eq("origen", origen),
            eq("destino", destino),
            gte("fechaHora", desde),
            lt("fechaHora", hasta)
        )).into(new ArrayList<>());
    }

    @Override
    public ViajeDTO consultarViajePorId(String idViaje) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<ViajeDTO> coleccion = baseDatos.getCollection(COLECCION, ViajeDTO.class);
        return coleccion.find(eq("_id", new ObjectId(idViaje))).first();
    }

    @Override
    public List<ViajeDTO> obtenerTodosLosViajes() {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<ViajeDTO> coleccion = baseDatos.getCollection(COLECCION, ViajeDTO.class);
        return coleccion.find().into(new ArrayList<>());
    }
}
