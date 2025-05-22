package itson.rutappbo.implementaciones;

import itson.persistenciarutapp.IPagosDAO;
import itson.persistenciarutapp.implementaciones.Pago;
import itson.persistenciarutapp.implementaciones.PagosDAO;
import itson.rutappbo.IPagosBO;
import itson.rutappdto.DetallesPagoDTO;
import itson.rutappdto.PagoDTO;
import java.util.Date;

/**
 *
 * @author pedro
 */
public class PagosBO implements IPagosBO {

    IPagosDAO pagosDAO = new PagosDAO();

    @Override
    public PagoDTO agregarPago(PagoDTO pagoDTO) {
        if (pagoDTO != null) {
            Pago pago = new Pago();
            pago.setMetodoPago(pagoDTO.getMetodoPago());
            pago.setCantidad(pagoDTO.getMonto());
            pago.setFechaHora(new Date());

            Pago persistido = pagosDAO.agregarPago(pago);

            // Convertir de nuevo a DTO (opcional)
            PagoDTO dto = new PagoDTO();
            dto.setMetodoPago(persistido.getMetodoPago());
            dto.setMonto(persistido.getCantidad());
            dto.setFecha(persistido.getFechaHora());

            return dto;
        }
        return null;
    }

}
