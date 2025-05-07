package Interfaz;

import Ex.CompraBoletoException;
import itson.rutappdto.AsientoDTO;
import itson.rutappdto.CamionDTO;
import itson.rutappdto.DetallesPagoDTO;
import itson.rutappdto.UsuarioDTO;
import java.util.List;

/**
 *
 * @author multaslokas33
 */
public interface IComprarBoleto {
//    void mostrarPantallaPago();
    void comprarBoleto();
     public boolean procesarCompra(DetallesPagoDTO detalles, UsuarioDTO usuario) throws CompraBoletoException;
}
