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
public class ControlComprarBoleto {


    

   //private final IPagoBoleto pagoBoleto = new PagoBoleto(); // se puede inyectar luego si quieres
    

    public boolean comprarBoleto(DetallesPagoDTO detallesPago, UsuarioDTO usuario) throws PagoBoletoException {
        
        IPagoBoleto pagoBoleto = new PagoBoleto();
        return pagoBoleto.procesarPago(detallesPago, usuario);
    }

    public void registrarCompra() {
        System.out.println("Compra registrada exitosamente.");
    }

}
