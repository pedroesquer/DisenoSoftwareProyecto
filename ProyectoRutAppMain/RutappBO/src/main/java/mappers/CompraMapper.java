package mappers;

import Entidades.AsientoBoleto;
import Entidades.Compra;
import itson.rutappdto.AsientoBoletoDTO;
import itson.rutappdto.AsientoDTO;
import itson.rutappdto.CamionDTO;
import itson.rutappdto.CompraDTO;
import itson.rutappdto.UsuarioDTO;
import itson.rutappdto.ViajeDTO;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entre Compra y CompraDTO.
 *
 * @author chris
 */
public class CompraMapper {

    /**
     * Convierte un CompraDTO a una entidad Compra para persistencia.
     *
     * @param dto CompraDTO a convertir
     * @return entidad Compra lista para guardar
     */
    public static Compra toEntity(CompraDTO dto) {
        Compra compra = new Compra();
        compra.asignarUsuarioDesdeString(dto.getUsuario().getId()); // usa el String
        compra.asignarViajeDesdeString(dto.getViaje().getIdViaje()); // usa el String
        compra.setFechaCompra(dto.getFecha());
        compra.setAsientosComprados(dto.getListaAsiento().stream()
                .map(CompraMapper::aEntidad)
                .collect(Collectors.toList()));
        return compra;
    }

    /**
     * Convierte una entidad Compra a un CompraDTO enriquecido para mostrar en
     * GUI.
     *
     * @param compra entidad Compra desde la base de datos
     * @param usuario DTO del usuario relacionado
     * @param viaje DTO del viaje relacionado
     * @param camion DTO del camión relacionado
     * @return CompraDTO listo para usar en la interfaz
     */
    public static CompraDTO toDTO(Compra compra, UsuarioDTO usuario, ViajeDTO viaje, CamionDTO camion) {
        CompraDTO dto = new CompraDTO();
        dto.setUsuario(usuario);
        dto.setViaje(viaje);
        dto.setCamion(camion);
        dto.setFecha(compra.getFechaCompra());
        dto.setListaAsiento(compra.getAsientosComprados().stream()
                .map(CompraMapper::aDTO)
                .collect(Collectors.toList()));
        dto.setOrigen(viaje.getOrigen());
        dto.setDestino(viaje.getDestino());
        dto.setHrSalida(formatearHora(viaje.getFecha()));
        dto.setDuracion("2h 30min aprox"); // puedes calcular si tienes tiempos
        dto.setPrecio(viaje.getPrecio());
        return dto;
    }

    /**
     * Formatea la hora desde una fecha.
     *
     * @param fecha Fecha a formatear
     * @return String en formato HH:mm
     */
    private static String formatearHora(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
        return formato.format(fecha);
    }

    /**
     * Convierte un DTO de asiento a su entidad.
     */
    private static AsientoBoleto aEntidad(AsientoBoletoDTO dto) {
        AsientoBoleto entidad = new AsientoBoleto();

        if (dto.getAsiento() != null) {
            // Convertir String a int
            try {
                entidad.setNumero(Integer.parseInt(dto.getAsiento().getNumero()));
            } catch (NumberFormatException e) {
                entidad.setNumero(-1); // o maneja error como gustes
            }
            entidad.setEstado(dto.getAsiento().getEstado());
        }

        entidad.setNombrePasajero(dto.getNombreAsiento());
        return entidad;
    }

    private static AsientoBoletoDTO aDTO(AsientoBoleto entidad) {
        AsientoDTO asientoDTO = new AsientoDTO();
        asientoDTO.setNumero(String.valueOf(entidad.getNumero())); // Convertir int a String
        asientoDTO.setEstado(entidad.getEstado());

        AsientoBoletoDTO dto = new AsientoBoletoDTO();
        dto.setAsiento(asientoDTO);
        dto.setNombreAsiento(entidad.getNombrePasajero());
        dto.setPrecioUnitario(null); // no disponible en entidad

        return dto;
    }

}
