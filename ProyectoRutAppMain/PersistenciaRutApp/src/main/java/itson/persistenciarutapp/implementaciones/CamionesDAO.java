package itson.persistenciarutapp.implementaciones;

import Entidades.Asiento;
import Entidades.Camion;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import java.util.List;
import itson.persistenciarutapp.ICamionesDAO;
import itson.rutappdto.AsientoBoletoDTO;
import org.bson.Document;

/**
 * Implementación de la interfaz {@link ICamionesDAO} que gestiona las
 * operaciones CRUD sobre la colección de camiones en la base de datos MongoDB.
 *
 * Esta clase permite consultar camiones, obtener asientos disponibles y
 * actualizar estados de asientos.
 */
public class CamionesDAO implements ICamionesDAO {

    /**
     * Nombre de la colección en MongoDB.
     */
    private final String COLECCION = "camiones";

    /**
     * Nombre del campo utilizado para buscar camiones por número.
     */
    private final String CAMPO_NUMERO = "numeroDeCamion";

    /**
     * Constructor por defecto. Inicializa la conexión a la colección, aunque la
     * conexión efectiva se realiza dentro de cada método.
     */
    public CamionesDAO() {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Camion> coleccion = baseDatos.getCollection(COLECCION, Camion.class);
    }

    /**
     * Consulta un camión por su número.
     *
     * @param numeroDeCamion número identificador del camión.
     * @return el camión correspondiente, o null si no se encuentra.
     */
    @Override
    public Camion consultarCamionPorId(String numeroDeCamion) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Camion> coleccion = baseDatos.getCollection(COLECCION, Camion.class);
        return coleccion.find(Filters.eq(CAMPO_NUMERO, numeroDeCamion)).first();
    }

    /**
     * Consulta todos los camiones existentes en la base de datos.
     *
     * @return una lista con todos los camiones.
     */
    @Override
    public List<Camion> consultarTodosLosCamiones() {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Camion> coleccion = baseDatos.getCollection(COLECCION, Camion.class);
        return coleccion.find().into(new ArrayList<>());
    }

    /**
     * Actualiza los datos de un camión en la base de datos.
     *
     * @param camion el camión actualizado que se desea persistir.
     */
    @Override
    public void actualizarCamion(Camion camion) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Camion> coleccion = baseDatos.getCollection(COLECCION, Camion.class);
        coleccion.replaceOne(
                Filters.eq("numeroDeCamion", camion.getNumeroDeCamion()),
                camion
        );
    }

    /**
     * Obtiene la lista de asientos disponibles de un camión específico.
     *
     * @param numeroDeCamion número del camión a consultar.
     * @return una lista de objetos {@link Asiento} con los datos actuales del
     * camión.
     */
    @Override
    public List<Asiento> obtenerAsientosDisponibles(String numeroDeCamion) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection("camiones");

        Document camionDoc = coleccion.find(eq("numeroDeCamion", numeroDeCamion)).first();

        List<Asiento> listaAsientos = new ArrayList<>();
        if (camionDoc != null && camionDoc.containsKey("asientos")) {
            List<Document> asientosDoc = (List<Document>) camionDoc.get("asientos");
            for (Document a : asientosDoc) {
                int numero = a.getInteger("numero");
                String estado = a.getString("estado");
                listaAsientos.add(new Asiento(numero, estado));
            }
        }

        return listaAsientos;
    }

    /**
     * Actualiza el estado de los asientos a "OCUPADO" para un camión dado.
     *
     * Utiliza un filtro sobre elementos del array para actualizar solo los
     * asientos indicados.
     *
     * @param numeroCamion número del camión.
     * @param asientos lista de asientos a marcar como ocupados.
     */
    @Override
    public void ocuparAsientos(String numeroCamion, List<AsientoBoletoDTO> asientos) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> camiones = db.getCollection("camiones");

        for (AsientoBoletoDTO asientoBoleto : asientos) {
            int numeroAsiento = Integer.parseInt(asientoBoleto.getAsiento().getNumero());

            camiones.updateOne(
                    Filters.eq("numeroDeCamion", numeroCamion),
                    Updates.set("asientos.$[elem].estado", "OCUPADO"),
                    new UpdateOptions().arrayFilters(List.of(
                            Filters.eq("elem.numero", numeroAsiento)
                    ))
            );
        }
    }

    @Override
    public void liberarAsientos(String numeroCamion, List<AsientoBoletoDTO> asientos) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> camiones = db.getCollection("camiones");

        for (AsientoBoletoDTO asientoBoleto : asientos) {
            int numeroAsiento = Integer.parseInt(asientoBoleto.getAsiento().getNumero());

            camiones.updateOne(
                    Filters.eq("numeroDeCamion", numeroCamion),
                    Updates.set("asientos.$[elem].estado", "LIBRE"),
                    new UpdateOptions().arrayFilters(List.of(
                            Filters.eq("elem.numero", numeroAsiento)
                    ))
            );
        }
    }

    @Override
    public String obtenerIdCamionPorNumero(String numeroCamion) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = db.getCollection("camiones");
        Document doc = coleccion.find(eq("numeroDeCamion", numeroCamion)).first();
        return doc != null ? doc.getObjectId("_id").toHexString() : null;
    }

}
