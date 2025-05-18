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

    Usuario agregarUsuario(UsuarioDTO nuevoUsuario);

    Usuario consultarUsuarioPorNumeroTelefonico(String numeroTel);

    Usuario validarLogin(String numeroTelefonico, String contrasena);

}
