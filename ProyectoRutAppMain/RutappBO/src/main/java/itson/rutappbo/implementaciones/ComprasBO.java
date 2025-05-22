package itson.rutappbo.implementaciones;

import enumm.estadoAsiento;
import itson.persistenciarutapp.IViajesDAO;
import itson.persistenciarutapp.implementaciones.AsientoBoleto;
import itson.persistenciarutapp.implementaciones.Compra;
import itson.persistenciarutapp.implementaciones.ComprasDAO;
import itson.persistenciarutapp.implementaciones.Viaje;
import itson.persistenciarutapp.implementaciones.ViajesDAO;
import itson.rutappbo.IComprasBO;
import itson.rutappdto.AsientoBoletoDTO;
import itson.rutappdto.AsientoDTO;
import itson.rutappdto.CamionDTO;
import itson.rutappdto.CompraDTO;
import itson.rutappdto.UsuarioDTO;
import itson.rutappdto.ViajeDTO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;

/**
 * Implementación de la lógica de negocio para registrar compras de boletos.
 */
public class ComprasBO implements IComprasBO {

    private final ComprasDAO comprasDAO = new ComprasDAO();
    private final IViajesDAO viajesDAO = new ViajesDAO();

    public ComprasBO() {

    }

    /**
     * Registra una compra en la base de datos usando los datos del contexto del
     * viaje y del usuario autenticado.
     *
     * @param usuarioDTO El usuario que realiza la compra
     * @param viajeDTO El viaje seleccionado
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

        System.out.println(" Compra persistida con ID de viaje: " + viajeDTO.getIdViaje());

        // Persistencia en base de datos
        comprasDAO.agregarCompras(compra);
    }

    private String formatearHora(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(fecha);
    }

    @Override
    public List<CompraDTO> obtenerComprasNoVencidasPorUsuario(UsuarioDTO usuarioDTO) {
        ObjectId idUsuario = new ObjectId(usuarioDTO.getId());

        List<Compra> compras = comprasDAO.consultarComprasNoVencidasPorUsuario(idUsuario);
        List<CompraDTO> resultado = new ArrayList<>();

        for (Compra compra : compras) {
            // Obtener el viaje desde la referencia
            Viaje viaje = viajesDAO.consultarViajePorId(compra.getViaje());

            // Mapear CamionDTO
            CamionDTO camionDTO = new CamionDTO();
            camionDTO.setNumeroCamion(viaje.getCamion().getNumeroCamion());

            // Mapear ViajeDTO
            ViajeDTO viajeDTO = new ViajeDTO();
            viajeDTO.setIdViaje(viaje.getId().toHexString());
            viajeDTO.setOrigen(viaje.getOrigen());
            viajeDTO.setDestino(viaje.getDestino());
            viajeDTO.setFecha(viaje.getFecha());
            viajeDTO.setPrecio(viaje.getPrecio());
            viajeDTO.setCamion(camionDTO);

            // Mapear lista de asientos
            List<AsientoBoletoDTO> listaAsientosDTO = new ArrayList<>();
            for (AsientoBoleto ab : compra.getAsientosComprados()) {
                // Crear AsientoDTO desde AsientoBoleto
                AsientoDTO asientoDTO = new AsientoDTO();
                asientoDTO.setNumero(String.valueOf(ab.getNumero())); // Asumiendo que el campo es String
                asientoDTO.setEstado(ab.getEstado());

                AsientoBoletoDTO abDTO = new AsientoBoletoDTO();
                abDTO.setAsiento(asientoDTO);
                abDTO.setNombreAsiento(ab.getNombrePasajero()); // o ab.getNombreAsiento() si tuvieras ese campo
                abDTO.setPrecioUnitario(viaje.getPrecio()); // asumido el mismo precio para todos

                listaAsientosDTO.add(abDTO);
            }
            // Armar CompraDTO
            CompraDTO compraDTO = new CompraDTO();
            compraDTO.setUsuario(usuarioDTO);
            compraDTO.setPrecio(viaje.getPrecio());
            compraDTO.setListaAsiento(listaAsientosDTO);
            compraDTO.setFecha(compra.getFechaCompra());
            compraDTO.setOrigen(viaje.getOrigen());
            compraDTO.setDestino(viaje.getDestino());
            compraDTO.setCamion(camionDTO);
            compraDTO.setViaje(viajeDTO);

            // Opcionales
            compraDTO.setHrSalida(formatearHora(viaje.getFecha()));
            compraDTO.setDuracion("2h 30m"); // Aquí puedes calcular duración si tienes más datos

            resultado.add(compraDTO);
        }

        return resultado;
    }

}
