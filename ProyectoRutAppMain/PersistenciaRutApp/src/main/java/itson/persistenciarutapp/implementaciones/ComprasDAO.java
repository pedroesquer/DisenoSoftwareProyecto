/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.persistenciarutapp.implementaciones;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import enumm.estadoAsiento;
import itson.persistenciarutapp.IComprasDAO;
import itson.rutappdto.UsuarioDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 *
 * @author pedro
 */
public class ComprasDAO implements IComprasDAO {

    private final String COLECCION = "compras";

    @Override
    public Compra agregarCompras(Compra nuevaCompra) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Compra> coleccion = db.getCollection(COLECCION, Compra.class);

        coleccion.insertOne(nuevaCompra);
        return nuevaCompra;
    }

    /**
     * Método para consultar las compras de un usuario.
     *
     * @param usuario usuario el cual se le va a consultar sus compra.
     * @return la lista de compras hechas por el usuario.
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
     * Método para consultar las compras que aun no hayan vencido de un usuario.
     *
     * @param usuario usuario el cual se le va a consultar sus compras.
     * @return la lista de compras del usuario que aun no hayan vencido.
     */
    @Override
    public List<Compra> consultarComprasNoVencidasPorUsuario(ObjectId idUsuario) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = db.getCollection("compras");

        Date ahora = new Date();

        List<Bson> pipeline = Arrays.asList(
                Aggregates.match(Filters.eq("usuario", idUsuario)),
                Aggregates.lookup("viajes", "viaje", "_id", "viajeInfo"),
                Aggregates.unwind("$viajeInfo"),
               Aggregates.match(Filters.gt("viajeInfo.fecha", new Date())) // ← filtra con la fecha del viaje
        );

        List<Compra> compras = new ArrayList<>();

        for (Document doc : coleccion.aggregate(pipeline)) {
            Compra compra = new Compra();

            compra.setId(doc.getObjectId("_id"));
            compra.setUsuario(doc.getObjectId("usuario"));
            compra.setFechaCompra(doc.getDate("fechaCompra"));
            compra.setViaje(doc.getObjectId("viaje")); // Sigue siendo solo el ID (como en tu clase)

            // Mapeo de asientos
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

}
