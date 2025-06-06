package itson.persistenciarutapp.implementaciones;

import Entidades.AsientoBoleto;
import Entidades.Compra;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import enumm.estadoAsiento;
import itson.persistenciarutapp.IComprasDAO;
import itson.rutappdto.UsuarioDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 * Implementación de la interfaz {@link IComprasDAO} que proporciona operaciones
 * para manejar compras de boletos en la base de datos MongoDB.
 *
 * Gestiona la inserción de compras, así como la consulta de compras por usuario
 * y la obtención de compras que no han vencido.
 */
public class ComprasDAO implements IComprasDAO {

    /**
     * Nombre de la colección en MongoDB utilizada para almacenar las compras.
     */
    private final String COLECCION = "compras";

    /**
     * Inserta una nueva compra en la base de datos.
     *
     * @param nuevaCompra el objeto Compra a insertar.
     * @return la compra insertada.
     */
    @Override
    public Compra agregarCompras(Compra nuevaCompra) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Compra> coleccion = db.getCollection(COLECCION, Compra.class);
        coleccion.insertOne(nuevaCompra);
        return nuevaCompra;
    }

    /**
     * Consulta todas las compras realizadas por un usuario específico.
     *
     * @param usuario objeto UsuarioDTO que contiene el ID del usuario.
     * @return lista de compras realizadas por el usuario.
     */
    @Override
    public List<Compra> consultarCompraPorUsuario(UsuarioDTO usuario) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Compra> coleccion = db.getCollection(COLECCION, Compra.class);

        List<Compra> compras = new ArrayList<>();
        coleccion.find(Filters.eq("usuario", new ObjectId(usuario.getId()))).into(compras);

        return compras;
    }

    /**
     * Consulta las compras de un usuario que no han vencido. Se consideran no
     * vencidas aquellas cuyo viaje aún no ha ocurrido (basado en
     * `viaje.fechaHora`).
     *
     * Nota: El filtrado por fecha del viaje puede agregarse con:
     * {@code Aggregates.match(Filters.gt("viajeInfo.fechaHora", new Date()))}
     *
     * @param idUsuario ID del usuario como {@link ObjectId}.
     * @return lista de compras no vencidas.
     */
@Override
public List<Compra> consultarComprasNoVencidasPorUsuario(String idUsuario) {
    MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
    MongoCollection<Document> coleccion = db.getCollection("compras");
    Date fechaActual = new Date();

    ObjectId objectIdUsuario = new ObjectId(idUsuario);

    List<Bson> pipeline = Arrays.asList(
        Aggregates.match(Filters.eq("usuario", objectIdUsuario)),
        Aggregates.lookup("viajes", "viaje", "_id", "viajeInfo"),
        Aggregates.unwind("$viajeInfo"),
        Aggregates.match(Filters.gt("viajeInfo.fechaHora", fechaActual))  // ESTA es la fecha del viaje
    );

    List<Compra> compras = new ArrayList<>();
    for (Document doc : coleccion.aggregate(pipeline)) {
        Compra compra = new Compra();
        compra.setId(doc.getObjectId("_id"));
        compra.setUsuario(doc.getObjectId("usuario"));
        compra.setFechaCompra(doc.getDate("fechaCompra"));
        compra.setViaje(doc.getObjectId("viaje"));

        List<Document> asientosDocs = (List<Document>) doc.get("asientosComprados");
        List<AsientoBoleto> asientos = new ArrayList<>();
        for (Document asientoDoc : asientosDocs) {
            AsientoBoleto asiento = new AsientoBoleto();
            asiento.setNumero(asientoDoc.getInteger("numero"));
            asiento.setNombrePasajero(asientoDoc.getString("nombrePasajero"));
            asiento.setEstado(estadoAsiento.valueOf(asientoDoc.getString("estado")));
            asientos.add(asiento);
        }

        compra.setAsientosComprados(asientos);
        compras.add(compra);
    }

    return compras;
}


    @Override
    public void cancelarCompra(String idCompraStr) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = db.getCollection("compras");

        ObjectId idCompra = new ObjectId(idCompraStr); // conversión aquí, no en la BO

        coleccion.updateOne(
                Filters.eq("_id", idCompra),
                Updates.set("asientosComprados.$[].estado", "LIBRE") // aplica a todos los asientos
        );
    }

    @Override
    public String obtenerIdDeCompra(String idUsuarioStr, Date fechaCompra) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = db.getCollection("compras");

        ObjectId idUsuario = new ObjectId(idUsuarioStr); // conversión local

        Document doc = coleccion.find(Filters.and(
                Filters.eq("usuario", idUsuario),
                Filters.eq("fechaCompra", fechaCompra)
        )).first();

        return doc != null ? doc.getObjectId("_id").toHexString() : null;

    }

    @Override
    public Compra consultarCompraPorId(ObjectId idCompra) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Compra> coleccion = db.getCollection(COLECCION, Compra.class);
        return coleccion.find(Filters.eq("_id", idCompra)).first();
    }

    @Override
    public boolean actualizarCompraParaReagenda(ObjectId idCompra, ObjectId nuevoViajeId, List<AsientoBoleto> nuevosAsientosEntities, Date nuevaFechaCompra, Double nuevoPrecioTotal) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = db.getCollection(COLECCION);

        List<Document> nuevosAsientosDocument = nuevosAsientosEntities.stream()
                .map(asientoBoleto -> new Document()
                .append("numero", asientoBoleto.getNumero())
                .append("estado", asientoBoleto.getEstado().toString())
                .append("nombrePasajero", asientoBoleto.getNombrePasajero()))
                .collect(Collectors.toList());

        Bson filter = Filters.eq("_id", idCompra);
        Bson update = Updates.combine(
                Updates.set("viaje", nuevoViajeId),
                Updates.set("asientosComprados", nuevosAsientosDocument),
                Updates.set("fechaCompra", nuevaFechaCompra),
                Updates.set("precio", nuevoPrecioTotal)
        );

        UpdateResult result = coleccion.updateOne(filter, update);
        return result.getModifiedCount() > 0;
    }

}
