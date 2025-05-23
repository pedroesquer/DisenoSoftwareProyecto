/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.rutappbo;

import itson.rutappdto.ReseñaDTO;
import java.util.List;

/**
 *
 * @author multaslokas33
 */
public interface IReseñaBO {

    void agregarReseña(ReseñaDTO reseñaDTO) throws Exception;

    List<ReseñaDTO> obtenerReseñasPorCamion(String numeroCamion);
}
