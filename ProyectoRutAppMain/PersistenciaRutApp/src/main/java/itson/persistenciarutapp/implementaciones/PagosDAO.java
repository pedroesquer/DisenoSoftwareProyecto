package itson.persistenciarutapp.implementaciones;

import itson.persistenciarutapp.entidades.Pago;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import itson.persistenciarutapp.IPagosDAO;
import itson.rutappdto.PagoDTO;
import java.util.Date;

/**
 * Implementación de la interfaz {@link IPagosDAO} que proporciona operaciones
 * de persistencia para registrar pagos en la base de datos MongoDB.
 * 
 * Esta clase se encarga de insertar nuevos registros de pagos en la colección correspondiente.
 */
public class PagosDAO implements IPagosDAO {

    /** Nombre de la colección de pagos en la base de datos. */
    private final String COLECCION = "pagos";

    /**
     * Inserta un nuevo pago en la base de datos.
     *
     * @param pago el objeto {@link Pago} a registrar.
     * @return el objeto {@code Pago} que fue insertado.
     */
    @Override
    public Pago agregarPago(Pago pago) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Pago> coleccion = db.getCollection(COLECCION, Pago.class);

        coleccion.insertOne(pago);
        return pago;
    }
}
