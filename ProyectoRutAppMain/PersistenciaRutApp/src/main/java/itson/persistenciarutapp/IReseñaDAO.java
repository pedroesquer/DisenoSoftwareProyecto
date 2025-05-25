/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.persistenciarutapp;

import Entidades.Reseña;
import itson.rutappdto.ReseñaDTO;
import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author multaslokas33
 */
public interface IReseñaDAO {

    void agregarReseña(Reseña reseña);

    List<Reseña> obtenerReseñasPorCamion(String idCamion);

    boolean existeReseñaDeUsuarioPorViaje(ObjectId idUsuario);

    List<Reseña> obtenerReseñasRecientes(Date desde);

    void eliminarReseñasPorUsuario(ObjectId idUsuario);

    boolean eliminarReseñaPorId(String idReseña);

    int contarReseñasUsuarioPorCamion(String idUsuario, String idCamion);
    
    Reseña obtenerReseñaPorId(String idReseña);
}
