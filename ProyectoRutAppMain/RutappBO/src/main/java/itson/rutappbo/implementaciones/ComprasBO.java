package itson.rutappbo.implementaciones;

import enumm.estadoAsiento;
import itson.persistenciarutapp.ICamionesDAO;
import itson.persistenciarutapp.IViajesDAO;
import itson.persistenciarutapp.implementaciones.CamionesDAO;
import Entidades.Compra;
import itson.persistenciarutapp.implementaciones.ComprasDAO;
import Entidades.Usuario;
import itson.persistenciarutapp.implementaciones.UsuariosDAO;
import Entidades.Viaje;
import excepciones.NegocioException;
import itson.persistenciarutapp.implementaciones.ViajesDAO;
import itson.rutappbo.IComprasBO;
import itson.rutappdto.AsientoBoletoDTO;
import itson.rutappdto.CompraDTO;
import itson.rutappdto.UsuarioDTO;
import itson.rutappdto.ViajeDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import mappers.CompraMapper;
import mappers.UsuarioMapper;
import mappers.ViajeMapper;
import org.bson.types.ObjectId;

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
        CompraDTO compraDTO = new CompraDTO(usuarioDTO, viajeDTO.getPrecio(), asientosDTO, viajeDTO);
        compraDTO.setFecha(new Date());

        Compra compra = CompraMapper.toEntity(compraDTO);
        comprasDAO.agregarCompras(compra);
    }

    @Override
    public List<CompraDTO> obtenerComprasNoVencidasPorUsuario(UsuarioDTO usuarioDTO) {
        String idUsuario = usuarioDTO.getId();

        List<Compra> compras = comprasDAO.consultarComprasNoVencidasPorUsuario(idUsuario); // DAO ya convierte a ObjectId
        compras = compras.stream()
                .filter(compra -> compra.getAsientosComprados().stream()
                .anyMatch(a -> a.getEstado() == estadoAsiento.OCUPADO))
                .collect(Collectors.toList());

        List<CompraDTO> resultado = new ArrayList<>();

        for (Compra compra : compras) {
            Viaje viaje = viajesDAO.consultarViajePorId(compra.getViaje());
            ViajeDTO viajeDTO = ViajeMapper.toDTO(viaje);
            CompraDTO compraDTO = CompraMapper.toDTO(compra, usuarioDTO, viajeDTO, viajeDTO.getCamion());
            resultado.add(compraDTO);
        }

        return resultado;
    }

    @Override
    public void cancelarCompra(CompraDTO compraDTO) {
        String idUsuario = compraDTO.getUsuario().getId();
        Date fechaCompra = compraDTO.getFecha();

        String idCompra = comprasDAO.obtenerIdDeCompra(idUsuario, fechaCompra);
        if (idCompra == null) {
            throw new RuntimeException("No se pudo encontrar la compra para cancelación.");
        }

        comprasDAO.cancelarCompra(idCompra);

        Viaje viaje = viajesDAO.consultarViajePorId(compraDTO.getViaje().getIdViaje());
        camionesDAO.liberarAsientos(viaje.getCamion().getNumeroCamion(), compraDTO.getListaAsiento());

        double montoReembolsado = compraDTO.getPrecio();
        UsuarioDTO usuario = compraDTO.getUsuario();
        usuario.setSaldoMonedero(usuario.getSaldoMonedero() + montoReembolsado);

        Usuario usuarioEntidad = UsuarioMapper.toEntity(usuario);

        usuariosDAO.actualizarSaldo(usuarioEntidad);
    }

    @Override
    public CompraDTO obtenerCompraDTOPorId(String idCompraOriginal) throws NegocioException {
        if (idCompraOriginal == null || idCompraOriginal.trim().isEmpty()) {
            throw new NegocioException("El ID de la compra no puede ser nulo o vacío.");
        }
        ObjectId objectId;
        try {
            objectId = new ObjectId(idCompraOriginal);
        } catch (IllegalArgumentException e) {
            throw new NegocioException("Formato de ID de compra inválido.", e);
        }

        Compra compra = comprasDAO.consultarCompraPorId(objectId);
        if (compra == null) {
            return null;
        }

        Usuario usuarioEntidad = usuariosDAO.consultarUsuarioPorIdString(idCompraOriginal);
        Viaje viajeEntidad = viajesDAO.consultarViajePorId(compra.getViaje());

        if (usuarioEntidad == null || viajeEntidad == null) {
            throw new NegocioException("No se pudieron cargar los detalles completos de la compra (usuario o viaje no encontrado).");
        }

        UsuarioDTO usuarioDTO = UsuarioMapper.toDTO(usuarioEntidad);
        ViajeDTO viajeDTO = ViajeMapper.convertirAViajeDTO(viajeEntidad);

        return CompraMapper.toDTO(compra, usuarioDTO, viajeDTO, viajeDTO.getCamion());
    }

    @Override
    public boolean actualizarCompraParaReagenda(String idCompraOriginal, String nuevoViajeId, List<AsientoBoletoDTO> nuevosAsientosDTO, Date nuevaFechaCompra, double nuevoPrecioTotal) throws NegocioException {
        if (idCompraOriginal == null || idCompraOriginal.trim().isEmpty()
                || nuevoViajeId == null || nuevoViajeId.trim().isEmpty()
                || nuevosAsientosDTO == null || nuevosAsientosDTO.isEmpty()
                || nuevaFechaCompra == null) {
            throw new NegocioException("Parámetros inválidos para actualizar la compra.");
        }

        ObjectId objectIdCompra;
        ObjectId objectIdNuevoViaje;
        try {
            objectIdCompra = new ObjectId(idCompraOriginal);
            objectIdNuevoViaje = new ObjectId(nuevoViajeId);
        } catch (IllegalArgumentException e) {
            throw new NegocioException("Formato de ID inválido.", e);
        }

        List<Entidades.AsientoBoleto> nuevosAsientosEntities = nuevosAsientosDTO.stream()
                .map(dto -> {
                    Entidades.AsientoBoleto eb = new Entidades.AsientoBoleto();
                    eb.setNumero(Integer.parseInt(dto.getAsiento().getNumero()));
                    eb.setEstado(dto.getAsiento().getEstado());
                    eb.setNombrePasajero(dto.getNombreAsiento());
                    return eb;
                }).collect(Collectors.toList());

        boolean actualizado = comprasDAO.actualizarCompraParaReagenda(objectIdCompra, objectIdNuevoViaje, nuevosAsientosEntities, nuevaFechaCompra, nuevoPrecioTotal);
        if (!actualizado) {
            throw new NegocioException("No se pudo actualizar la compra en la base de datos.");
        }
        return true;
    }

}
