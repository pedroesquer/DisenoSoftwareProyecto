/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.rutappbo;

import itson.persistenciarutapp.implementaciones.Camion;
import java.util.List;

/**
 *
 * @author chris
 */
public interface ICamionesBO {

    Camion obtenerCamion(String numeroDeCamion);

    List<Camion> obtenerTodos();

    int contarAsientosLibres(String numeroDeCamion);

    boolean cambiarEstadoAsiento(String numeroDeCamion, int numeroAsiento, String nuevoEstado);
}
