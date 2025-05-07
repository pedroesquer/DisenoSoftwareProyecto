package Control;

import Ex.CompraBoletoException;
import enumm.estadoAsiento;
import itson.rutappdto.AsientoDTO;
import itson.rutappdto.CamionDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BusSoft®
 */
public class Control {


    public List<AsientoDTO> mostrarListaAsientos(CamionDTO camion) throws CompraBoletoException {
        if (camion.getListaAsiento().isEmpty()) {
            return null;
        }
        return camion.getListaAsiento();
    }

    public List<AsientoDTO> mostrarListaAsientos() {
        List<AsientoDTO> listaAsientos = new ArrayList<>();

        listaAsientos.add(new AsientoDTO(1L, estadoAsiento.DISPONIBLE, "A1"));
        listaAsientos.add(new AsientoDTO(2L, estadoAsiento.OCUPADO, "A2"));
        listaAsientos.add(new AsientoDTO(3L, estadoAsiento.DISPONIBLE, "A3"));
        listaAsientos.add(new AsientoDTO(4L, estadoAsiento.DISPONIBLE, "A4"));
        listaAsientos.add(new AsientoDTO(5L, estadoAsiento.DISPONIBLE, "A5"));
        

        return listaAsientos;

    }

    public void registrarCompra() {
        System.out.println("Compra registrada exitosamente.");
    }
    
}
