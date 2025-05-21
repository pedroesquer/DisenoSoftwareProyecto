package itson.rutappbo;

import itson.persistenciarutapp.implementaciones.Pago;
import itson.rutappdto.DetallesPagoDTO;
import itson.rutappdto.PagoDTO;

/**
 *
 * @author pedro
 */
public interface IPagosBO {
    Pago agregarPago(PagoDTO pagoDTO);
}
