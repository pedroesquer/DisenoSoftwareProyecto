/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import itson.persistenciarutapp.entidades.Usuario;
import itson.rutappdto.UsuarioDTO;

/**
 *
 * @author chris
 */
public class UsuarioMapper {

    public static UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.obtenerIddComoString());
        dto.setNombre(usuario.getNombre());
        dto.setNumeroTelefonico(usuario.getNumeroTelefonico());
        dto.setContrasena(usuario.getContrasenia());
        dto.setSaldoMonedero(usuario.getSaldoMonedero());
        return dto;
    }

    public static Usuario toEntity(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.asignarIddDesdeString(dto.getId());
        usuario.setNombre(dto.getNombre());
        usuario.setNumeroTelefonico(dto.getNumeroTelefonico());
        usuario.setContrasenia(dto.getContrasena());
        usuario.setSaldoMonedero(dto.getSaldoMonedero());
        return usuario;
    }
}
