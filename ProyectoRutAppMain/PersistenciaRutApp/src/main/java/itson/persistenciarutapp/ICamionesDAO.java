/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.persistenciarutapp;

import itson.persistenciarutapp.implementaciones.Camion;
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
}
