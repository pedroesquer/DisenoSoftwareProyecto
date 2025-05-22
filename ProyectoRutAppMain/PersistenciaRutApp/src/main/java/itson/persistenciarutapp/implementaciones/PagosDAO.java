package itson.persistenciarutapp.implementaciones;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import itson.persistenciarutapp.IPagosDAO;
import itson.rutappdto.PagoDTO;
import java.util.Date;

/**
 *
 * @author pedro
 */
public class PagosDAO implements IPagosDAO {

    private final String COLECCION = "pagos";

    @Override
    public Pago agregarPago(Pago pago) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Pago> coleccion = db.getCollection(COLECCION, Pago.class);

        coleccion.insertOne(pago);
        return pago;
    }

}
