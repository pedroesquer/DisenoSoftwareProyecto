/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fachada;

import controlResenia.controlResenias;
import interfaz.IResenias;
import itson.rutappdto.CompraDTO;
import itson.rutappdto.ReseñaDTO;
import java.util.List;

/**
 *
 * @author multaslokas33
 */
public class fachadaResenias implements IResenias {
    @Override
    public void agregarReseña(ReseñaDTO reseña) throws Exception {
        controlResenias.getInstancia().agregarReseña(reseña);
    }

    @Override
    public List<ReseñaDTO> obtenerReseñasPorCamion(String numeroCamion) {
        return controlResenias.getInstancia().obtenerReseñasPorCamion(numeroCamion);
    }

    @Override
    public boolean eliminarReseña(String idReseña) {
        return controlResenias.getInstancia().eliminarReseña(idReseña);
    }
    
}
