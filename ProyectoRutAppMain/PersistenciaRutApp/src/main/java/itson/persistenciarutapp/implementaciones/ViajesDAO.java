package itson.persistenciarutapp.implementaciones;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.include;
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

public class ViajesDAO implements IViajesDAO {

    private final String COLECCION = "viajes";

    @Override
    public List<Viaje> consultarViajesPorOrigenDestinoYFecha(String origen, String destino, Date fecha) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Viaje> coleccion = baseDatos.getCollection(COLECCION, Viaje.class);

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

    @Override
    public Viaje consultarViajePorId(String idViaje) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Viaje> coleccion = baseDatos.getCollection(COLECCION, Viaje.class);
        Document filtro = new Document("_id", new ObjectId(idViaje));
        return coleccion.find(filtro).first();
    }

    @Override
    public List<Viaje> obtenerTodosLosViajes() {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Viaje> coleccion = baseDatos.getCollection(COLECCION, Viaje.class);
        return coleccion.find().into(new ArrayList<>());
    }

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
