/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.persistenciarutapp;

import itson.persistenciarutapp.implementaciones.Usuario;
import itson.rutappdto.AccesoUsuarioDTO;
import itson.rutappdto.UsuarioDTO;

/**
 *
 * @author pedro
 */
public interface IUsuariosDAO {

    UsuarioDTO agregarUsuario(UsuarioDTO nuevoUsuario);

    UsuarioDTO consultarUsuarioPorNumeroTelefonico(String numeroTel);

    UsuarioDTO validarLogin(UsuarioDTO usuario);
    
    boolean actualizarSaldo(UsuarioDTO usuario);

}
