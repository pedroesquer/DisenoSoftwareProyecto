package itson.persistenciarutapp.implementaciones;

import itson.persistenciarutapp.entidades.Viaje;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.include;
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
 * Implementación de la interfaz {@link IViajesDAO} que gestiona las operaciones de
 * acceso a datos para la colección de viajes en MongoDB.
 * 
 * Proporciona métodos para consultar viajes por origen, destino, fecha e ID.
 */
public class ViajesDAO implements IViajesDAO {

    /** Nombre de la colección que contiene los viajes. */
    private final String COLECCION = "viajes";

    /**
     * Consulta viajes que coincidan con un origen, destino y una fecha específica.
     * La búsqueda abarca todos los viajes del día dado.
     *
     * @param origen ciudad de origen.
     * @param destino ciudad de destino.
     * @param fecha fecha a consultar (se consideran todos los viajes de ese día).
     * @return lista de viajes que cumplen los criterios.
     */
    @Override
    public List<Viaje> consultarViajesPorOrigenDestinoYFecha(String origen, String destino, Date fecha) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Viaje> coleccion = baseDatos.getCollection(COLECCION, Viaje.class);

        // Convertir fecha a rango de día completo (desde - hasta)
        LocalDate localDate = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Date desde = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date hasta = Date.from(localDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        return coleccion.find(and(
                eq("origen", origen),
                eq("destino", destino),
                gte("fechaHora", desde),
                lt("fechaHora", hasta)
        )).into(new ArrayList<>());
    }

    /**
     * Consulta un viaje por su ID como cadena.
     *
     * @param idViaje ID del viaje en formato de texto.
     * @return el viaje correspondiente o {@code null} si no se encuentra.
     */
    @Override
    public Viaje consultarViajePorId(String idViaje) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Viaje> coleccion = baseDatos.getCollection(COLECCION, Viaje.class);
        Document filtro = new Document("_id", new ObjectId(idViaje));
        return coleccion.find(filtro).first();
    }

    /**
     * Consulta un viaje por su ID como {@link ObjectId}.
     *
     * @param idViaje ID del viaje como ObjectId.
     * @return el objeto {@link Viaje} correspondiente o {@code null} si no existe.
     */
    public Viaje consultarViajePorId(ObjectId idViaje) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Viaje> coleccion = db.getCollection(COLECCION, Viaje.class);
        return coleccion.find(Filters.eq("_id", idViaje)).first();
    }

    /**
     * Obtiene todos los viajes registrados en la base de datos.
     *
     * @return lista de todos los objetos {@link Viaje}.
     */
    @Override
    public List<Viaje> obtenerTodosLosViajes() {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Viaje> coleccion = baseDatos.getCollection(COLECCION, Viaje.class);
        return coleccion.find().into(new ArrayList<>());
    }

    /**
     * Obtiene una lista de destinos disponibles a partir de una ciudad de origen.
     *
     * @param origen ciudad de origen.
     * @return lista de destinos únicos disponibles desde la ciudad de origen.
     */
    @Override
    public List<String> obtenerDestinosPorOrigen(String origen) {
        List<String> destinos = new ArrayList<>();

        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection(COLECCION);

        for (Document doc : coleccion.find(eq("origen", origen)).projection(include("destino"))) {
            String destino = doc.getString("destino");
            if (destino != null && !destinos.contains(destino)) {
                destinos.add(destino);
            }
        }

        return destinos;
    }
}
