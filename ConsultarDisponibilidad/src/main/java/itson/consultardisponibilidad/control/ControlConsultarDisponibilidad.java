package itson.consultardisponibilidad.control;

import itson.rutappdto.AsientoDTO;
import itson.rutappdto.CamionDTO;

import java.util.ArrayList;

import java.util.List;
import itson.persistenciarutapp.IViajesDAO;
import itson.persistenciarutapp.implementaciones.ViajesDAO;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.include;
import itson.persistenciarutapp.implementaciones.ManejadorConexiones;
import org.bson.Document;
import com.mongodb.Block;
import itson.persistenciarutapp.implementaciones.CamionesDAO;
import itson.rutappbo.ICamionesBO;
import itson.rutappbo.IViajesBO;
import itson.rutappbo.implementaciones.CamionesBO;
import itson.rutappbo.implementaciones.ViajesBO;
import itson.rutappdto.ViajeDTO;

/**
 *
 * @author Bussoft®
 */
public class ControlConsultarDisponibilidad {

    private final IViajesDAO viajesDAO = new ViajesDAO();

    //private final CamionesDAO camionesDAO 
    
    private static ControlConsultarDisponibilidad instance;

    private final IViajesBO viajesBO;
    private final ICamionesBO camionesBO;

    private ControlConsultarDisponibilidad() {
        this.viajesBO = new ViajesBO(new ViajesDAO());
        this.camionesBO = new CamionesBO();
    }

    /**
     * Método para obtener la instancia del singleton.
     *
     * @return una instancia de tipo Control.
     */
    public static ControlConsultarDisponibilidad getInstancia() {
        if (instance == null) {
            instance = new ControlConsultarDisponibilidad();
        }
        return instance;
    }
    
    /**
     * Método que devuelve la lista de asientos de un camión para colorear los
     * asientos.
     *
     * @param camion Camion del cual se desean obtener los asientos.
     * @return lista de asientos del viaje del camión que se seleccionó.
     */
    public List<AsientoDTO> obtenerAsientosDisponibles(CamionDTO camion) {
        try {
            return camionesBO.obtenerAsientosDisponibles(camion.getNumeroCamion());
        } catch (Exception e) {
            System.err.println("Error al consultar asientos de MongoDB: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Método que devuelve la lista de viajes disponibles segun el origen, el
     * destino y la fecha.
     *
     * @param origen el origen seleccionado por el usuario.
     * @param destino Hacia donde desea ir el usuario.
     * @param fecha La fecha en la que el usuario desea ir
     * @return la lista de viajes encontrada con la coincidencia.
     */
    public List<ViajeDTO> obtenerViajesDisponibles(ViajeDTO viaje) {
        try {
            String fechaStr = viaje.getFecha().toString();
            return viajesBO.consultarViajesPorOrigenDestinoYFecha(viaje);
        } catch (Exception e) {
            System.err.println("Error al consultar los viajes desde MongoDB: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Método que devuelve la lista de destinos disponibles.
     *
     * @param origen Desde donde se quiere ver los destinos.
     * @return la lista de destinos para un origen.
     */
    public List<String> obtenerDestinos(String origen) {
        return viajesBO.obtenerDestinos(origen); 
    }
}
