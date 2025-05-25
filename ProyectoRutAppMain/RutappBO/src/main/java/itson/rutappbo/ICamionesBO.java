
package itson.rutappbo;

import excepciones.NegocioException;
import itson.rutappdto.AsientoBoletoDTO;
import itson.rutappdto.AsientoDTO;
import itson.rutappdto.CamionDTO;
import java.util.List;

public interface ICamionesBO {

    CamionDTO obtenerCamion(String numeroDeCamion);

    List<CamionDTO> obtenerTodos();

    int contarAsientosLibres(String numeroDeCamion);

    boolean cambiarEstadoAsiento(String numeroDeCamion, int numeroAsiento, String nuevoEstado);

    void ocuparAsientos(String idCamion, List<AsientoBoletoDTO> asientos) throws NegocioException;

    List<AsientoDTO> obtenerAsientosDisponibles(String numeroDeCamion);

    String obtenerIdPorNumero(String numeroDeCamion);
    
    public void liberarAsientos(String numeroCamion, List<AsientoBoletoDTO> asientos) throws NegocioException;
    
}
