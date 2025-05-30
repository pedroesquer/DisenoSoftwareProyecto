package itson.consultardisponibilidad.Interfaz;

import Entidades.Viaje;
import itson.rutappdto.AsientoDTO;
import itson.rutappdto.CamionDTO;
import itson.rutappdto.CompraDTO;
import itson.rutappdto.ReseñaDTO;
import itson.rutappdto.UsuarioDTO;
import itson.rutappdto.ViajeDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author juanpheras
 */
public interface IConsultarDisponibilidad {

    public abstract List<ViajeDTO> consultarViajesDisponibles(ViajeDTO viaje);

    public abstract List<AsientoDTO> consultarAsientosDisponibles(CamionDTO camion);

    public abstract List<String> consultarDestinos(String origen);

   // public abstract List<CompraDTO> consultarComprasPorUsuario(UsuarioDTO usuario);

}
