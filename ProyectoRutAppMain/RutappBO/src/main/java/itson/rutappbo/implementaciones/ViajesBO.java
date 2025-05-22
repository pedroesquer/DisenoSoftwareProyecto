package itson.rutappbo.implementaciones;

import itson.rutappbo.IViajesBO;
import itson.persistenciarutapp.IViajesDAO;
import itson.persistenciarutapp.implementaciones.Viaje;
import itson.rutappdto.ViajeDTO;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author chris
 */
public class ViajesBO implements IViajesBO {

    private final IViajesDAO viajesDAO;

    public ViajesBO(IViajesDAO viajesDAO) {
        this.viajesDAO = viajesDAO;
    }

    @Override
    public List<ViajeDTO> consultarViajesPorOrigenDestinoYFecha(ViajeDTO viajeDTO) {
        List<Viaje> resultados = viajesDAO.consultarViajesPorOrigenDestinoYFecha(
                viajeDTO.getOrigen(),
                viajeDTO.getDestino(),
                viajeDTO.getFecha()
        );

        return resultados.stream()
                .map(this::convertirAViajeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ViajeDTO> consultarTodos() {
        return viajesDAO.obtenerTodosLosViajes().stream()
                .map(this::convertirAViajeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void insertarViaje(Viaje viaje) {
        // lógica de inserción si la implementas después
    }

    private ViajeDTO convertirAViajeDTO(Viaje viaje) {
        ViajeDTO dto = new ViajeDTO(
                viaje.getPrecio(),
                viaje.getOrigen(),
                viaje.getDestino(),
                viaje.getCamion(),
                viaje.getFecha()
        );
        if (viaje.getId() != null) {
            dto.setIdViaje(viaje.getId().toHexString());
        }
        return dto;
    }

    @Override
    public List<String> obtenerDestinos(String origen) {
        return viajesDAO.obtenerDestinosPorOrigen(origen);
    }
}
