package fachada;

import control.ControlPagoBoleto;
import excepciones.PagoBoletoException;
import interfaz.IPagoBoleto;
import itson.rutappdto.DetallesPagoDTO;
import itson.rutappdto.UsuarioDTO;

/**
 *
 * @author pedro
 */
public class PagoBoleto implements IPagoBoleto {

    @Override
    public boolean procesarPagoMonedero(UsuarioDTO usuarioDTO, Double cantidad) throws PagoBoletoException{
        if (usuarioDTO != null) {
            ControlPagoBoleto.getInstancia().descontarSaldoMonedero(usuarioDTO, cantidad);
            return true;
        }
        return false;
    }

    @Override
    public boolean procesarPagoTarjeta(String fecha, String numeroTarjeta) throws PagoBoletoException {
        if (!ControlPagoBoleto.getInstancia().validarFechaVencimiento(fecha)) {
            throw new PagoBoletoException("Fecha de vencimiento invalida.");
        }
        if (!ControlPagoBoleto.getInstancia().validarNumeroTarjeta(fecha)) {
            throw new PagoBoletoException("Número de tarjeta invalido.");
        }
        return true;
    }

    @Override
    public void agregarSaldoMonedero(UsuarioDTO usuarioDTO, Double cantidad) {
        if (usuarioDTO != null) {
            ControlPagoBoleto.getInstancia().agregarSaldoMonedero(usuarioDTO, cantidad);
        }
    }

    @Override
    public boolean procesarPago(DetallesPagoDTO detalles, UsuarioDTO usuarioDTO) throws PagoBoletoException {
        if (detalles != null) {
            return ControlPagoBoleto.getInstancia().procesarPago(detalles, usuarioDTO);
        }
        return false;
    }

}
