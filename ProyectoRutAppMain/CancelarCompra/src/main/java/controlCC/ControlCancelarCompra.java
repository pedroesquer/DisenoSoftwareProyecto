/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package controlCC;

import itson.rutappbo.IComprasBO;
import itson.rutappbo.implementaciones.ComprasBO;
import itson.rutappdto.CompraDTO;
import itson.rutappdto.UsuarioDTO;
import java.util.List;

/**
 *
 * @author chris
 */
public class ControlCancelarCompra {

    private static ControlCancelarCompra instance;

    private final IComprasBO compraBO;

    public ControlCancelarCompra() {
        this.compraBO = new ComprasBO();
    }

    /**
     * Método para obtener la instancia del singleton.
     *
     * @return una instancia de tipo Control.
     */
    public static ControlCancelarCompra getInstancia() {
        if (instance == null) {
            instance = new ControlCancelarCompra();
        }
        return instance;
    }

    public List<CompraDTO> obtenerCompras(UsuarioDTO usuario) {
        return compraBO.obtenerComprasNoVencidasPorUsuario(usuario);
    }

    public void cancelarCompra(CompraDTO compra) {
        compraBO.cancelarCompra(compra);
    }

}
