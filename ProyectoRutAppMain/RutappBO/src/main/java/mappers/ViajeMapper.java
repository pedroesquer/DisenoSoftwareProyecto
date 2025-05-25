/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import Entidades.Viaje;
import itson.rutappdto.CamionDTO;
import itson.rutappdto.ViajeDTO;


/**
 *
 * @author chris
 */
public class ViajeMapper {

    public static ViajeDTO toDTO(Viaje viaje) {
        ViajeDTO dto = new ViajeDTO();
        dto.setIdViaje(viaje.getId().toHexString());
        dto.setOrigen(viaje.getOrigen());
        dto.setDestino(viaje.getDestino());
        dto.setFecha(viaje.getFecha());
        dto.setPrecio(viaje.getPrecio());

        CamionDTO camionDTO = new CamionDTO();
        camionDTO.setNumeroCamion(viaje.getCamion().getNumeroCamion());
        dto.setCamion(camionDTO);

        return dto;
    }

    public static ViajeDTO convertirAViajeDTO(Viaje viaje) {
        ViajeDTO dto = new ViajeDTO(
                viaje.getPrecio(),
                viaje.getOrigen(),
                viaje.getDestino(),
                viaje.getCamion(),
                viaje.getFecha()
        );
        if (viaje.getId() != null) {
            dto.setIdViaje(viaje.getId().toHexString());
        }
        return dto;
    }

}
