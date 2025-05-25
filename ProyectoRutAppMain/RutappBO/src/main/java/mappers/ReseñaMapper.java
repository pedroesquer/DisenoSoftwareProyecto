/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import Entidades.Reseña;
import itson.rutappdto.ReseñaDTO;

/**
 *
 * @author multaslokas33
 */
public class ReseñaMapper {

    public static Reseña toEntity(ReseñaDTO dto, String idUsuario, String idCamion) {
        Reseña reseña = new Reseña();
        reseña.setUsuarioFromString(idUsuario);
        reseña.setCamionFromString(idCamion);
        reseña.setComentario(dto.getComentario());
        reseña.setCalificacion(dto.getCalificacion());
        reseña.setFecha(dto.getFecha());
        return reseña;
    }

    public static ReseñaDTO toDTO(Reseña reseña, String nombreUsuario, String numeroCamion) {
        ReseñaDTO dto = new ReseñaDTO();
        dto.setId(reseña.getIdAsString());
        dto.setNombreUsuario(nombreUsuario);
        dto.setNumeroCamion(numeroCamion);
        dto.setComentario(reseña.getComentario());
        dto.setCalificacion(reseña.getCalificacion());
        dto.setFecha(reseña.getFecha());
        return dto;
    }
}
