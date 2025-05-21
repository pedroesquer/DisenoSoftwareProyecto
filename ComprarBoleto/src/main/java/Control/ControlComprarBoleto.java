package Control;

import Ex.CompraBoletoException;
import enumm.estadoAsiento;
import excepciones.PagoBoletoException;
import fachada.PagoBoleto;
import interfaz.IPagoBoleto;
import itson.rutappbo.IPagosBO;
import itson.rutappbo.implementaciones.PagosBO;
import itson.rutappdto.AsientoDTO;
import itson.rutappdto.CamionDTO;
import itson.rutappdto.DetallesPagoDTO;
import itson.rutappdto.PagoDTO;
import itson.rutappdto.UsuarioDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author BusSoft®
 */
public class ControlComprarBoleto {

    //private final IPagoBoleto pagoBoleto = new PagoBoleto(); // se puede inyectar luego si quieres
    public boolean comprarBoleto(DetallesPagoDTO detallesPago, UsuarioDTO usuario) throws PagoBoletoException {

        IPagoBoleto pagoBoleto = new PagoBoleto();
        boolean pagoProcesado = pagoBoleto.procesarPago(detallesPago, usuario);
        System.out.println("----------RESULTADO DEL PAGO PROCESADO: " + pagoProcesado + "--------------------");
        if (pagoProcesado) {
            System.out.println("El pago procesado fue " + pagoProcesado);
            Date fecha = new Date();
            PagoDTO pagoDTO = new PagoDTO();
            pagoDTO.setMetodoPago(detallesPago.getMetodoPago());
            pagoDTO.setMonto(detallesPago.getMonto());
            pagoDTO.setFecha(fecha);
            System.out.println(pagoDTO.toString());
            IPagosBO pagosBO = new PagosBO();
            pagosBO.agregarPago(pagoDTO);

        }
        return pagoProcesado;
    }

    public void registrarCompra() {
        System.out.println("Compra registrada exitosamente.");
    }

}
