/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import Entidades.Usuario;
import itson.rutappdto.UsuarioDTO;

/**
 *
 * @author chris
 */
public class UsuarioMapper {
    
    public static Usuario toEntity(UsuarioDTO dto) {
        Usuario usuario = new Usuario();

        usuario.asignarIddDesdeString(dto.getId()); // Usa método que convierte a ObjectId
        usuario.setNumeroTelefonico(dto.getNumeroTelefonico());
        usuario.setNombre(dto.getNombre());
        usuario.setContrasenia(dto.getContrasena());
        usuario.setSaldoMonedero(dto.getSaldoMonedero());

        return usuario;
    }

    public static UsuarioDTO toDTO(Usuario entidad) {
        UsuarioDTO dto = new UsuarioDTO();

        dto.setId(entidad.obtenerIddComoString()); // Usa método que convierte de ObjectId a String
        dto.setNumeroTelefonico(entidad.getNumeroTelefonico());
        dto.setNombre(entidad.getNombre());
        dto.setContrasena(entidad.getContrasenia());
        dto.setSaldoMonedero(entidad.getSaldoMonedero());

        return dto;
    }
}
