/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import itson.persistenciarutapp.entidades.Camion;
import itson.rutappdto.CamionDTO;

/**
 *
 * @author multaslokas33
 */
public class CamionMapper {

    public static CamionDTO toDTO(Camion camion) {
        CamionDTO dto = new CamionDTO();
        dto.setId(camion.obtenerIdComooString());
        dto.setNumeroCamion(camion.getNumeroDeCamion());
        return dto;
    }

    public static Camion toEntity(CamionDTO dto) {
        Camion camion = new Camion();
        camion.asignarIdDesdeeString(dto.getId());
        camion.setNumeroDeCamion(dto.getNumeroCamion());
        return camion;
    }
}
