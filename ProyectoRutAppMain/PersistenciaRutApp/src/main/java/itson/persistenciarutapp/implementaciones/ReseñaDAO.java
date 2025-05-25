package itson.persistenciarutapp.implementaciones;

import itson.persistenciarutapp.entidades.Reseña;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.result.DeleteResult;
import itson.persistenciarutapp.IReseñaDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;

/**
 * Implementación de la interfaz {@link IReseñaDAO} para la gestión de reseñas
 * en la base de datos MongoDB. Permite agregar, consultar y eliminar reseñas.
 */
public class ReseñaDAO implements IReseñaDAO {

    /**
     * Nombre de la colección que almacena las reseñas.
     */
    private final String COLECCION = "reseñas";

    /**
     * Referencia a la colección Mongo de reseñas.
     */
    private final MongoCollection<Reseña> coleccion;

    /**
     * Constructor que inicializa la conexión a la colección de reseñas.
     */
    public ReseñaDAO() {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        this.coleccion = baseDatos.getCollection(COLECCION, Reseña.class);
    }

    /**
     * Inserta una nueva reseña en la colección.
     *
     * @param reseña la reseña a agregar.
     */
    @Override
    public void agregarReseña(Reseña reseña) {
        coleccion.insertOne(reseña);
    }

    /**
     * Consulta todas las reseñas asociadas a un camión específico.
     *
     * @param idCamion ID del camión a consultar.
     * @return lista de reseñas realizadas al camión.
     */
    @Override
    public List<Reseña> obtenerReseñasPorCamion(String idCamion) {
        return coleccion.find(eq("camion", new ObjectId(idCamion))).into(new ArrayList<>());
    }

    /**
     * Verifica si un usuario ya ha dejado una reseña (por ejemplo, para evitar
     * duplicidad).
     *
     * @param idUsuario ID del usuario.
     * @return true si ya existe una reseña por ese usuario, false en caso
     * contrario.
     */
    @Override
    public boolean existeReseñaDeUsuarioPorViaje(ObjectId idUsuario) {
        return coleccion.find(eq("usuario", idUsuario)).first() != null;
    }

    /**
     * Obtiene todas las reseñas realizadas desde una fecha específica en
     * adelante.
     *
     * @param desde fecha mínima para filtrar reseñas.
     * @return lista de reseñas recientes.
     */
    @Override
    public List<Reseña> obtenerReseñasRecientes(Date desde) {
        return coleccion.find(Filters.gte("fecha", desde)).into(new ArrayList<>());
    }

    /**
     * Elimina todas las reseñas realizadas por un usuario específico.
     *
     * @param idUsuario ID del usuario del cual se eliminarán las reseñas.
     */
    @Override
    public void eliminarReseñasPorUsuario(ObjectId idUsuario) {
        coleccion.deleteMany(eq("usuario", idUsuario));
    }

    @Override
    public boolean eliminarReseñaPorId(String idReseña) {
        DeleteResult resultado = coleccion.deleteOne(eq("_id", new ObjectId(idReseña)));
        return resultado.getDeletedCount() > 0;
    }

    @Override
    public int contarReseñasUsuarioPorCamion(String idUsuario, String idCamion) {
        return (int) coleccion.countDocuments(Filters.and(
                Filters.eq("usuario", new ObjectId(idUsuario)),
                Filters.eq("camion", new ObjectId(idCamion))
        ));
    }

    @Override
    public Reseña obtenerReseñaPorId(String idReseña) {
        return coleccion.find(eq("_id", new ObjectId(idReseña))).first();
    }
}
