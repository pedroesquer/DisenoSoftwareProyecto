package itson.persistenciarutapp;

import itson.persistenciarutapp.implementaciones.Pago;
import itson.rutappdto.PagoDTO;

/**
 *
 * @author pedro
 */
public interface IPagosDAO {
    Pago agregarPago(PagoDTO pagoDTO);
    
    
    

}
