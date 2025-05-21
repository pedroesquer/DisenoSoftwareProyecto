package interfaz;

import excepciones.NegocioException;
import itson.rutappdto.AsientoDTO;
import itson.rutappdto.BoletoDTO;
import itson.rutappdto.CamionDTO;
import java.util.List;

/**
 * Metodo de interfaz que obtiene la lista de asientos disponibles para un camión dado.
 *
 */
public interface ISeleccionAsiento {

    List<AsientoDTO> obtenerAsientos(CamionDTO camion);
//    void iniciarTemporizador(Runnable reiniciarAsientosCallback);
//    void finalizarTemporizador();
    
    void ocuparAsientos(BoletoDTO boleto) throws NegocioException;
}
