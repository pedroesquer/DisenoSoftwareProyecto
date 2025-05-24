/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaz;

import itson.rutappdto.CompraDTO;
import itson.rutappdto.ReseñaDTO;
import java.util.List;

/**
 *
 * @author multaslokas33
 */
public interface IResenias {
    void agregarReseña(ReseñaDTO reseña) throws Exception;

    List<ReseñaDTO> obtenerReseñasPorCamion(String numeroCamion);

    boolean eliminarReseña(String idReseña);

}
