/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.rutappbo;

import itson.rutappdto.AccesoUsuarioDTO;

/**
 *
 * @author juanpheras
 */
public interface IUsuariosBO {
    Boolean autenticar(AccesoUsuarioDTO acceso);
}
