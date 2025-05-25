/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fachada;

import controlCC.ControlCancelarCompra;
import interfaz.ICancelarCompra;
import itson.rutappdto.CompraDTO;
import itson.rutappdto.UsuarioDTO;
import java.util.List;

/**
 *
 * @author chris
 */
public class FCancelarCompra implements ICancelarCompra {

    @Override
    public List<CompraDTO> obtenerCompras(UsuarioDTO usuario) {
        return ControlCancelarCompra.getInstancia().obtenerCompras(usuario);
    }

    @Override
    public void eliminarCompra(CompraDTO compra) {
        ControlCancelarCompra.getInstancia().cancelarCompra(compra);
    }

}
