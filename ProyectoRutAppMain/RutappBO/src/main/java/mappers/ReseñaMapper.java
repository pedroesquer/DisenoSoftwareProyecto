/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import itson.persistenciarutapp.entidades.Reseña;
import itson.rutappdto.ReseñaDTO;

/**
 *
 * @author multaslokas33
 */
public class ReseñaMapper {

    public static Reseña toEntity(ReseñaDTO dto, String idUsuario, String idCamion) {
        Reseña reseña = new Reseña();
        reseña.asignarUsuarioDesdeString(idUsuario);
        reseña.asignarCamionDesdeString(idCamion);
        reseña.setComentario(dto.getComentario());
        reseña.setCalificacion(dto.getCalificacion());
        reseña.setFecha(dto.getFecha());
        return reseña;
    }

    public static ReseñaDTO toDTO(Reseña reseña, String nombreUsuario, String numeroCamion) {
        ReseñaDTO dto = new ReseñaDTO();
        dto.setId(reseña.obtenerIdComoString());
        dto.setNombreUsuario(nombreUsuario);
        dto.setNumeroCamion(numeroCamion);
        dto.setComentario(reseña.getComentario());
        dto.setCalificacion(reseña.getCalificacion());
        dto.setFecha(reseña.getFecha());
        return dto;
    }
}
