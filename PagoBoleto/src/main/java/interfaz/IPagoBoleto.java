package interfaz;

import excepciones.PagoBoletoException;
import itson.rutappdto.DetallesPagoDTO;
import itson.rutappdto.UsuarioDTO;

/**
 *
 * @author pedro
 */
public interface IPagoBoleto {
    void agregarSaldoMonedero(UsuarioDTO usuarioDTO, Double cantidad);
    boolean procesarPagoMonedero(UsuarioDTO usuarioDTO, Double cantidad);
    boolean procesarPagoTarjeta(String fecha, String numeroTarjeta) throws PagoBoletoException;
    boolean procesarPago(DetallesPagoDTO detalles, UsuarioDTO usuarioDTO) throws PagoBoletoException;
}
