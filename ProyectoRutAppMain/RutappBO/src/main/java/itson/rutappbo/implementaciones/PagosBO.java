package itson.rutappbo.implementaciones;

import itson.persistenciarutapp.IPagosDAO;
import itson.persistenciarutapp.implementaciones.Pago;
import itson.persistenciarutapp.implementaciones.PagosDAO;
import itson.rutappbo.IPagosBO;
import itson.rutappdto.DetallesPagoDTO;
import itson.rutappdto.PagoDTO;

/**
 *
 * @author pedro
 */
public class PagosBO implements IPagosBO {

    IPagosDAO pagosDAO = new PagosDAO();

    @Override
    public Pago agregarPago(PagoDTO pagoDTO) {
        if (pagoDTO != null) {
            return pagosDAO.agregarPago(pagoDTO);
        }
        return null;
    }

}
