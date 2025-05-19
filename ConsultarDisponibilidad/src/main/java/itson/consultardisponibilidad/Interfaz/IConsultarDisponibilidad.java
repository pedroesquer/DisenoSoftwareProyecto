package itson.consultardisponibilidad.Interfaz;

import itson.rutappdto.AsientoDTO;
import itson.rutappdto.CamionDTO;
import itson.rutappdto.ViajeDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author juanpheras
 */
public interface IConsultarDisponibilidad {

    public abstract List<ViajeDTO> consultarViajesDisponibles(String origen, String destino, LocalDateTime fecha);

    public abstract List<AsientoDTO> consultarAsientosDisponibles(CamionDTO camion);

    public abstract List<String> consultarDestinos(String origen);

}
