/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.persistenciarutapp.implementaciones;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import itson.persistenciarutapp.IReseñaDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author multaslokas33
 */
public class ReseñaDAO implements IReseñaDAO {

    private final String COLECCION = "reseñas";
    private final MongoCollection<Reseña> coleccion;

    public ReseñaDAO() {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        this.coleccion = baseDatos.getCollection(COLECCION, Reseña.class);
    }

    @Override
    public void agregarReseña(Reseña reseña) {
        coleccion.insertOne(reseña);
    }

    @Override
    public List<Reseña> obtenerReseñasPorCamion(ObjectId idCamion) {
        return coleccion.find(eq("camion", idCamion)).into(new ArrayList<>());
    }

    @Override
    public boolean existeReseñaDeUsuarioPorViaje(ObjectId idUsuario) {
        return coleccion.find(and(
                eq("usuario", idUsuario)
        )).first() != null;
    }

    @Override
    public List<Reseña> obtenerReseñasRecientes(Date desde) {
        return coleccion.find(Filters.gte("fecha", desde)).into(new ArrayList<>());
    }

    @Override
    public void eliminarReseñasPorUsuario(ObjectId idUsuario) {
        coleccion.deleteMany(eq("usuario", idUsuario));
    }
}
