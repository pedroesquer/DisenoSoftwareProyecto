/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.persistenciarutapp;

import Entidades.Asiento;
import Entidades.Camion;
import itson.rutappdto.AsientoBoletoDTO;
import itson.rutappdto.CamionDTO;
import java.util.List;

/**
 *
 * @author chris
 */
public interface ICamionesDAO {

    Camion consultarCamionPorId(String idCamion);

    List<Camion> consultarTodosLosCamiones();

    void actualizarCamion(Camion camion);
    
    void ocuparAsientos(String idCamion, List<AsientoBoletoDTO> asientos);
    
    public List<Asiento> obtenerAsientosDisponibles(String numeroDeCamion);
    
    void liberarAsientos(String numeroCamion, List<AsientoBoletoDTO> asientos);
    
    String obtenerIdCamionPorNumero(String numeroCamion);

}
