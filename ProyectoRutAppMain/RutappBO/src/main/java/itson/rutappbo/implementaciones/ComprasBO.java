package itson.rutappbo.implementaciones;

import enumm.estadoAsiento;
import itson.persistenciarutapp.implementaciones.AsientoBoleto;
import itson.persistenciarutapp.implementaciones.Compra;
import itson.persistenciarutapp.implementaciones.ComprasDAO;
import itson.rutappbo.IComprasBO;
import itson.rutappdto.AsientoBoletoDTO;
import itson.rutappdto.UsuarioDTO;
import itson.rutappdto.ViajeDTO;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;

/**
 * Implementación de la lógica de negocio para registrar compras de boletos.
 */
public class ComprasBO implements IComprasBO {

    private final ComprasDAO comprasDAO = new ComprasDAO();

    /**
     * Registra una compra en la base de datos usando los datos del contexto del viaje y del usuario autenticado.
     *
     * @param usuarioDTO El usuario que realiza la compra
     * @param viajeDTO   El viaje seleccionado
     * @param asientosDTO Lista de asientos seleccionados
     */
    @Override
    public void agregarCompra(UsuarioDTO usuarioDTO, ViajeDTO viajeDTO, List<AsientoBoletoDTO> asientosDTO) {

        // Validación de ID de usuario
        if (usuarioDTO.getId() == null || !ObjectId.isValid(usuarioDTO.getId())) {
            throw new IllegalArgumentException("ID de usuario no válido: " + usuarioDTO.getId());
        }

        // Validación de ID de viaje
        if (viajeDTO.getIdViaje() == null || !ObjectId.isValid(viajeDTO.getIdViaje())) {
            throw new IllegalArgumentException("ID de viaje no válido: " + viajeDTO.getIdViaje());
        }

        // Convertir los asientos DTO a entidad
        List<AsientoBoleto> asientos = asientosDTO.stream()
            .map(dto -> {
                int numero = 0;
                if (dto.getAsiento() != null && dto.getAsiento().getNumero() != null) {
                    numero = Integer.parseInt(dto.getAsiento().getNumero());
                }
                return new AsientoBoleto(numero, estadoAsiento.OCUPADO, dto.getNombreAsiento());
            })
            .collect(Collectors.toList());

        // Construcción del objeto Compra
        Compra compra = new Compra();
        compra.setId(new ObjectId());
        compra.setUsuario(new ObjectId(usuarioDTO.getId()));
        compra.setViaje(new ObjectId(viajeDTO.getIdViaje()));
        compra.setAsientosComprados(asientos);
        compra.setFechaCompra(new Date());

        System.out.println("✅ Compra persistida con ID de viaje: " + viajeDTO.getIdViaje());

        // Persistencia en base de datos
        comprasDAO.agregarCompras(compra);
    }
}
