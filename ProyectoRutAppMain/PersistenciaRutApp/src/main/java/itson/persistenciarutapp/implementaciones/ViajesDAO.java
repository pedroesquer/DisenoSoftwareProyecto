/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.persistenciarutapp.implementaciones;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import itson.persistenciarutapp.IViajesDAO;
import itson.rutappdto.ViajeDTO;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
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
    public List<ViajeDTO> consultarViajesPorOrigenDestinoYFecha(ViajeDTO viaje) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Viaje> coleccion = baseDatos.getCollection(COLECCION, Viaje.class);

        Date fecha = viaje.getFecha();
        LocalDate localDate = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Date desde = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date hasta = Date.from(localDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<Viaje> resultados = coleccion.find(and(
                eq("origen", viaje.getOrigen()),
                eq("destino", viaje.getDestino()),
                gte("fechaHora", desde),
                lt("fechaHora", hasta)
        )).into(new ArrayList<>());

        // Mapear de entidad a DTO
        List<ViajeDTO> listaDTO = new ArrayList<>();
        for (Viaje v : resultados) {
            ViajeDTO viajeDTO = new ViajeDTO(v.getPrecio(), v.getOrigen(), v.getDestino(), v.getCamion(), v.getFecha());
            listaDTO.add(viajeDTO);
        }

        return listaDTO;

    }

    @Override
    public ViajeDTO consultarViajePorId(String idViaje) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Viaje> coleccion = baseDatos.getCollection(COLECCION, Viaje.class);
        Document filtros = new Document();
        filtros.append("_id", new ObjectId(idViaje));
        FindIterable<Viaje> resultados = coleccion.find(filtros);
        Viaje viaje = resultados.first();
        return parsearViajeDTO(viaje);
    }

    @Override
    public List<ViajeDTO> obtenerTodosLosViajes() {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Viaje> coleccion = baseDatos.getCollection(COLECCION, Viaje.class);
        List<ViajeDTO> listaDTO = new LinkedList<>();
        FindIterable<Viaje> resultados = coleccion.find();
        List<Viaje> listaResultados = new LinkedList<>();
        return parsearListaViajeDTO(listaResultados);

    }

    public ViajeDTO parsearViajeDTO(Viaje viaje) {
        return new ViajeDTO(
                viaje.getPrecio(), viaje.getOrigen(), viaje.getDestino(), viaje.getCamion(), viaje.getFecha());
    }

    public List<ViajeDTO> parsearListaViajeDTO(List<Viaje> listaViajes) {
        List<ViajeDTO> listaDTO = new LinkedList<>();

        for (Viaje v : listaViajes) {
            listaDTO.add(parsearViajeDTO(v));
        }
        return listaDTO;
    }
}
