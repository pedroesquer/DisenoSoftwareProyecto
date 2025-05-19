/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.persistenciarutapp.implementaciones;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import itson.rutappdto.CamionDTO;
import java.util.ArrayList;
import java.util.List;
import itson.persistenciarutapp.ICamionesDAO;

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
        coleccion.createIndex(
                Indexes.ascending("_id"),
                new IndexOptions().unique(true)
        );
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

}
