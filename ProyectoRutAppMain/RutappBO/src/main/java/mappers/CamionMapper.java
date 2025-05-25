/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import Entidades.Camion;
import itson.rutappdto.CamionDTO;

/**
 *
 * @author multaslokas33
 */
public class CamionMapper {

    public static CamionDTO toDTO(Camion camion) {
        CamionDTO dto = new CamionDTO();
        dto.setId(camion.getIdAsString());
        dto.setNumeroCamion(camion.getNumeroDeCamion());
        return dto;
    }

    public static Camion toEntity(CamionDTO dto) {
        Camion camion = new Camion();
        camion.setIdFromString(dto.getId());
        camion.setNumeroDeCamion(dto.getNumeroCamion());
        return camion;
    }
}
