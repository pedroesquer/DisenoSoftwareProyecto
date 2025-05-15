/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.persistenciarutapp;

import itson.persistenciarutapp.implementaciones.Usuario;
import itson.rutappdto.AccesoUsuarioDTO;


/**
 *
 * @author pedro
 */
public interface IUsuariosDAO {
     Usuario agregarUsuario(AccesoUsuarioDTO nuevoUsuario);
     Usuario consultarUsuarioPorNumeroTelefonico(String numeroTel);
     boolean validarLogin(String numeroTelefonico, String contrasena);
     
     
}
