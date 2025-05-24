package itson.rutappbo.implementaciones;

import enumm.estadoAsiento;
import itson.persistenciarutapp.ICamionesDAO;
import itson.persistenciarutapp.IViajesDAO;
import itson.persistenciarutapp.implementaciones.AsientoBoleto;
import itson.persistenciarutapp.implementaciones.CamionesDAO;
import itson.persistenciarutapp.implementaciones.Compra;
import itson.persistenciarutapp.implementaciones.ComprasDAO;
import itson.persistenciarutapp.implementaciones.Usuario;
import itson.persistenciarutapp.implementaciones.UsuariosDAO;
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
import java.util.stream.Collectors;

/**
 * Implementación de la lógica de negocio para registrar compras de boletos.
 */
public class ComprasBO implements IComprasBO {

    private final ComprasDAO comprasDAO = new ComprasDAO();
    private final IViajesDAO viajesDAO = new ViajesDAO();
    private final ICamionesDAO camionesDAO = new CamionesDAO();
    private final UsuariosDAO usuariosDAO = new UsuariosDAO();

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
        compras = compras.stream()
                .filter(compra -> compra.getAsientosComprados().stream()
                .anyMatch(a -> a.getEstado() == estadoAsiento.OCUPADO))
                .collect(Collectors.toList());
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

    @Override
    public void cancelarCompra(CompraDTO compraDTO) {
        ObjectId idUsuario = new ObjectId(compraDTO.getUsuario().getId());
        Date fechaCompra = compraDTO.getFecha();

        ObjectId idCompra = comprasDAO.obtenerIdDeCompra(idUsuario, fechaCompra);
        if (idCompra == null) {
            throw new RuntimeException("No se pudo encontrar la compra para cancelación.");
        }
        // Cancelar la compra (libera los asientos en la colección "compras")
        comprasDAO.cancelarCompra(idCompra);

        // Liberar los asientos en el camión
        Viaje viaje = viajesDAO.consultarViajePorId(compraDTO.getViaje().getIdViaje());
        String numeroCamion = viaje.getCamion().getNumeroCamion();
        camionesDAO.liberarAsientos(numeroCamion, compraDTO.getListaAsiento());

        // Reembolso al monedero
        double montoReembolsado = compraDTO.getPrecio();
        UsuarioDTO usuario = compraDTO.getUsuario();

        // Sumar el monto al saldo actual
        double saldoActual = usuario.getSaldoMonedero();
        usuario.setSaldoMonedero(saldoActual + montoReembolsado);

        Usuario usuarioEntidad = new Usuario();
        usuarioEntidad.setId(new ObjectId(usuario.getId()));
        usuarioEntidad.setNumeroTelefonico(usuario.getNumeroTelefonico());
        usuarioEntidad.setNombre(usuario.getNombre());
        usuarioEntidad.setContrasenia(usuario.getContrasena());
        usuarioEntidad.setSaldoMonedero(usuario.getSaldoMonedero());

        // Actualizar saldo en la base de datos
        usuariosDAO.actualizarSaldo(usuarioEntidad);
    }

}
