package Control;

import Ex.CompraBoletoException;
import enumm.estadoAsiento;
import excepciones.PagoBoletoException;
import fachada.PagoBoleto;
import interfaz.IPagoBoleto;
import itson.rutappdto.AsientoDTO;
import itson.rutappdto.CamionDTO;
import itson.rutappdto.DetallesPagoDTO;
import itson.rutappdto.UsuarioDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BusSoft®
 */
public class Control {


    

   private final IPagoBoleto pagoBoleto = new PagoBoleto(); // se puede inyectar luego si quieres

    public boolean comprarBoleto(DetallesPagoDTO detallesPago, UsuarioDTO usuario) {
        try {
            if (detallesPago.getDetallesTarjeta() == null) {
                // Pago con monedero
                boolean aprobado = pagoBoleto.procesarPagoMonedero(usuario, detallesPago.getMonto());

                if (aprobado) {
                    ControlPagoBoleto.getInstancia().descontarSaldoMonedero(usuario, detallesPago.getMonto());
                    registrarCompra();
                    return true;
                } else {
                    System.out.println("Saldo insuficiente.");
                    return false;
                }

            } else {
                // Pago con tarjeta
                boolean aprobado = pagoBoleto.procesarPagoTarjeta(
                    detallesPago.getDetallesTarjeta().getFechaExpiracion(),
                    detallesPago.getDetallesTarjeta().getNumeroTarjeta()
                );

                if (aprobado) {
                    registrarCompra();
                    return true;
                } else {
                    System.out.println("Pago con tarjeta rechazado.");
                    return false;
                }
            }

        } catch (PagoBoletoException e) {
            System.err.println("Error en compra: " + e.getMessage());
            return false;
        }
    }

    public void registrarCompra() {
        System.out.println("Compra registrada exitosamente.");
    }

}
