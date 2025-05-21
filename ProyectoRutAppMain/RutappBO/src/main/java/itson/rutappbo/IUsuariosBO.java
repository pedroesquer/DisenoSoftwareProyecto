/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.rutappbo;

import itson.rutappdto.AccesoUsuarioDTO;
import itson.rutappdto.UsuarioDTO;

/**
 *
 * @author juanpheras
 */
public interface IUsuariosBO {

    String registrarUsuario(UsuarioDTO nuevoUsuario);

    String login(UsuarioDTO usuario);

    String autenticar(UsuarioDTO usuario);

    UsuarioDTO obtenerUsuario();
    
    boolean descontarSaldo(UsuarioDTO usuario);
    
}
