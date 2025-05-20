package itson.rutappbo.implementaciones;

import itson.rutappbo.IViajesBO;
import itson.persistenciarutapp.IViajesDAO;
import itson.persistenciarutapp.implementaciones.Viaje;
import itson.rutappdto.ViajeDTO;
import java.util.List;

/**
 *
 * @author chris
 */
public class ViajesBO implements IViajesBO{
    
    private final IViajesDAO viajesDAO;

    public ViajesBO(IViajesDAO viajesDAO) {
        this.viajesDAO = viajesDAO;
    }

    
    @Override
    public List<ViajeDTO> consultarViajesPorOrigenDestinoYFecha(ViajeDTO viaje) {
        return viajesDAO.consultarViajesPorOrigenDestinoYFecha(viaje);
    }

    @Override
    public List<ViajeDTO> consultarTodos() {
        return viajesDAO.obtenerTodosLosViajes();
    }

    @Override
    public void insertarViaje(Viaje viaje) {

    }
}
