package itson.consultardisponibilidad.control;

import itson.persistenciarutapp.IReseñaDAO;
import itson.rutappdto.AsientoDTO;
import itson.rutappdto.CamionDTO;

import java.util.ArrayList;

import java.util.List;
import itson.persistenciarutapp.IViajesDAO;
import itson.persistenciarutapp.implementaciones.ViajesDAO;
import itson.rutappbo.ICamionesBO;
import itson.rutappbo.IComprasBO;
import itson.rutappbo.IReseñaBO;
import itson.rutappbo.IUsuariosBO;
import itson.rutappbo.IViajesBO;
import itson.rutappbo.implementaciones.CamionesBO;
import itson.rutappbo.implementaciones.ComprasBO;
import itson.rutappbo.implementaciones.ReseñasBO;
import itson.rutappbo.implementaciones.UsuariosBO;
import itson.rutappbo.implementaciones.ViajesBO;
import itson.rutappdto.CompraDTO;
import itson.rutappdto.ReseñaDTO;
import itson.rutappdto.UsuarioDTO;
import itson.rutappdto.ViajeDTO;

/**
 *
 * @author Bussoft®
 */
public class ControlConsultarDisponibilidad {

//    private final IReseñaBO reseñaBO; 
    //private final CamionesDAO camionesDAO 
    private static ControlConsultarDisponibilidad instance;

    private final IViajesBO viajesBO;
    private final ICamionesBO camionesBO;
    private final IComprasBO compraBO;

    private ControlConsultarDisponibilidad() {
        this.viajesBO = new ViajesBO(new ViajesDAO());
        this.camionesBO = new CamionesBO();
        this.compraBO = new ComprasBO();                       

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

    public List<CompraDTO> obtenerCompras(UsuarioDTO usuario) {
        return compraBO.obtenerComprasNoVencidasPorUsuario(usuario);
    }

     public void cancelarCompra(CompraDTO compra) {
        compraBO.cancelarCompra(compra);
    }
}
