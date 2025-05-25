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
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entre Compra y CompraDTO.
 *
 * @author chris
 */
public class CompraMapper {

    /**
     * Convierte un CompraDTO a una entidad Compra para la persistencia.
     *
     * @param dto El CompraDTO a convertir.
     * @return Una entidad Compra. Retorna null si el DTO de entrada es null.
     */
    public static Compra toEntity(CompraDTO dto) {
        if (dto == null) {
            return null;
        }
        Compra compra = new Compra();

        if (dto.getId() != null && !dto.getId().trim().isEmpty()) {
            compra.asignarIdDesdeString(dto.getId());
        }

        if (dto.getUsuario() != null && dto.getUsuario().getId() != null) {
            compra.asignarUsuarioDesdeString(dto.getUsuario().getId());
        }
        if (dto.getViaje() != null && dto.getViaje().getIdViaje() != null) {
            compra.asignarViajeDesdeString(dto.getViaje().getIdViaje());
        }

        compra.setFechaCompra(dto.getFecha() != null ? dto.getFecha() : new Date());

        if (dto.getListaAsiento() != null) {
            compra.setAsientosComprados(dto.getListaAsiento().stream()
                    .map(CompraMapper::asientoBoletoDTOToEntity)
                    .collect(Collectors.toList()));
        } else {
            compra.setAsientosComprados(new ArrayList<>());
        }

        return compra;
    }

    /**
     * Convierte una entidad Compra a un CompraDTO enriquecido.
     *
     * @param compra La entidad Compra desde la base de datos.
     * @param usuarioDTO El UsuarioDTO asociado a esta compra.
     * @param viajeDTO El ViajeDTO asociado a esta compra (debe incluir
     * CamionDTO).
     * @param camionDTO El CamionDTO del viaje (puede ser redundante si viajeDTO
     * ya lo tiene).
     * @return Un CompraDTO completo. Retorna null si la entidad Compra de
     * entrada es null.
     */
    public static CompraDTO toDTO(Compra compra, UsuarioDTO usuarioDTO, ViajeDTO viajeDTO, CamionDTO camionDTO) {
        if (compra == null) {
            return null;
        }
        CompraDTO dto = new CompraDTO();

        dto.setId(compra.obtenerIdComoString());
        dto.setUsuario(usuarioDTO);
        dto.setViaje(viajeDTO);

        if (viajeDTO != null && viajeDTO.getCamion() == null && camionDTO != null) {
            viajeDTO.setCamion(camionDTO);
        }
        dto.setCamion(viajeDTO != null && viajeDTO.getCamion() != null ? viajeDTO.getCamion() : camionDTO);

        dto.setFecha(compra.getFechaCompra());

        if (compra.getAsientosComprados() != null) {
            dto.setListaAsiento(compra.getAsientosComprados().stream()
                    .map(CompraMapper::asientoBoletoEntityToDTO)
                    .collect(Collectors.toList()));
        } else {
            dto.setListaAsiento(new ArrayList<>());
        }

        if (viajeDTO != null) {
            dto.setOrigen(viajeDTO.getOrigen());
            dto.setDestino(viajeDTO.getDestino());
            dto.setHrSalida(viajeDTO.getFecha() != null ? formatearHora(viajeDTO.getFecha()) : null);

            if (dto.getListaAsiento() != null && !dto.getListaAsiento().isEmpty() && viajeDTO.getPrecio() != null) {
                dto.setPrecio(viajeDTO.getPrecio() * dto.getListaAsiento().size());
            } else if (viajeDTO.getPrecio() != null) {
                dto.setPrecio(viajeDTO.getPrecio());
            } else {
                dto.setPrecio(0.0);
            }
            dto.setDuracion(viajeDTO.getFecha() != null ? "Aprox. 2h 30m" : null);
        } else {
            dto.setOrigen(null);
            dto.setDestino(null);
            dto.setHrSalida(null);
            dto.setPrecio(0.0);
            dto.setDuracion(null);
        }

        return dto;
    }

    /**
     * Formatea la hora desde un objeto Date.
     *
     * @param fecha La fecha de la cual extraer y formatear la hora.
     * @return Una cadena representando la hora en formato HH:mm. Retorna null
     * si la fecha es null.
     */
    private static String formatearHora(Date fecha) {
        if (fecha == null) {
            return null;
        }
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
        return formato.format(fecha);
    }

    /**
     * Convierte un AsientoBoletoDTO a una entidad AsientoBoleto.
     *
     * @param dto El AsientoBoletoDTO a convertir.
     * @return La entidad AsientoBoleto. Retorna null si el DTO de entrada es
     * null.
     */
    public static AsientoBoleto asientoBoletoDTOToEntity(AsientoBoletoDTO dto) {
        if (dto == null) {
            return null;
        }
        AsientoBoleto entidad = new AsientoBoleto();
        if (dto.getAsiento() != null && dto.getAsiento().getNumero() != null) {
            try {
                entidad.setNumero(Integer.parseInt(dto.getAsiento().getNumero()));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Número de asiento en DTO no es un entero válido: " + dto.getAsiento().getNumero(), e);
            }
            entidad.setEstado(dto.getAsiento().getEstado());
        }
        entidad.setNombrePasajero(dto.getNombreAsiento());
        return entidad;
    }

    /**
     * Convierte una entidad AsientoBoleto a AsientoBoletoDTO.
     *
     * @param entidad La entidad AsientoBoleto a convertir.
     * @return El AsientoBoletoDTO. Retorna null si la entidad de entrada es
     * null.
     */
    public static AsientoBoletoDTO asientoBoletoEntityToDTO(AsientoBoleto entidad) {
        if (entidad == null) {
            return null;
        }
        AsientoDTO asientoDTO = new AsientoDTO();
        asientoDTO.setNumero(String.valueOf(entidad.getNumero()));
        asientoDTO.setEstado(entidad.getEstado());

        AsientoBoletoDTO dto = new AsientoBoletoDTO();
        dto.setAsiento(asientoDTO);
        dto.setNombreAsiento(entidad.getNombrePasajero());
        dto.setPrecioUnitario(null);
        dto.setBoleto(null);

        return dto;
    }
}
