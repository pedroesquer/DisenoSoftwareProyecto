/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.rutappbo;

import excepciones.NegocioException;
import Entidades.Camion;
import itson.rutappdto.AsientoBoletoDTO;
import itson.rutappdto.AsientoDTO;
import itson.rutappdto.CamionDTO;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author chris
 */
public interface ICamionesBO {

    CamionDTO obtenerCamion(String numeroDeCamion);

    List<CamionDTO> obtenerTodos();

    int contarAsientosLibres(String numeroDeCamion);

    boolean cambiarEstadoAsiento(String numeroDeCamion, int numeroAsiento, String nuevoEstado);

    void ocuparAsientos(String idCamion, List<AsientoBoletoDTO> asientos) throws NegocioException;

    List<AsientoDTO> obtenerAsientosDisponibles(String numeroDeCamion);

    String obtenerIdPorNumero(String numeroDeCamion);
    
}
