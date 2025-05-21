/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.persistenciarutapp.implementaciones;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import enumm.estadoAsiento;
import itson.rutappdto.CamionDTO;
import java.util.ArrayList;
import java.util.List;
import itson.persistenciarutapp.ICamionesDAO;
import itson.rutappdto.AsientoBoletoDTO;
import itson.rutappdto.AsientoDTO;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author chris
 */
public class CamionesDAO implements ICamionesDAO {

    private final String COLECCION = "camiones";
    private final String CAMPO_NUMERO = "numeroDeCamion";

    public CamionesDAO() {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Camion> coleccion = baseDatos.getCollection(COLECCION, Camion.class);

        // Crear índice único por _id si no existiera
//        coleccion.createIndex(
//                Indexes.ascending("numeroDeCamion"),
//                new IndexOptions().unique(true)
//        );
    }

    @Override
    public Camion consultarCamionPorId(String numeroDeCamion) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Camion> coleccion = baseDatos.getCollection(COLECCION, Camion.class);
        return coleccion.find(Filters.eq(CAMPO_NUMERO, numeroDeCamion)).first();
    }

    @Override
    public List<Camion> consultarTodosLosCamiones() {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Camion> coleccion = baseDatos.getCollection(COLECCION, Camion.class);
        return coleccion.find().into(new ArrayList<>());
    }

    @Override
    public void actualizarCamion(Camion camion) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Camion> coleccion = baseDatos.getCollection(COLECCION, Camion.class);

        coleccion.replaceOne(
                Filters.eq("numeroDeCamion", camion.getNumeroDeCamion()),
                camion
        );
    }

    public List<AsientoDTO> obtenerAsientosDisponibles(CamionDTO camion) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection("camiones");

        Document camionDoc = coleccion.find(eq("numeroDeCamion", camion.getNumeroCamion())).first();

        List<AsientoDTO> listaAsientos = new ArrayList<>();
        if (camionDoc != null && camionDoc.containsKey("asientos")) {
            List<Document> asientosDoc = (List<Document>) camionDoc.get("asientos");
            for (Document a : asientosDoc) {
                Long id = a.getInteger("numero").longValue();
                String estadoStr = a.getString("estado");
                estadoAsiento estado = estadoStr.equalsIgnoreCase("OCUPADO") ? estadoAsiento.OCUPADO : estadoAsiento.LIBRE;
                listaAsientos.add(new AsientoDTO(id, estado, String.valueOf(id)));
            }
        }

        return listaAsientos;
    }

    @Override
    public void ocuparAsientos(String numeroCamion, List<AsientoBoletoDTO> asientos) {

        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> camiones = db.getCollection("camiones");

        for (AsientoBoletoDTO asientoBoleto : asientos) {
            int numeroAsiento = Integer.parseInt(asientoBoleto.getAsiento().getNumero());

            // Actualiza el estado del asiento específico usando arrayFilters
            camiones.updateOne(
                    Filters.eq("numeroDeCamion", numeroCamion),
                    Updates.set("asientos.$[elem].estado", "OCUPADO"),
                    new UpdateOptions().arrayFilters(List.of(
                            Filters.eq("elem.numero", numeroAsiento)
                    ))
            );
        }
    }

}
