package itson.rutappbo.implementaciones;

import enumm.estadoAsiento;
import itson.persistenciarutapp.ICamionesDAO;
import itson.persistenciarutapp.IViajesDAO;
import itson.persistenciarutapp.implementaciones.CamionesDAO;
import itson.persistenciarutapp.implementaciones.Compra;
import itson.persistenciarutapp.implementaciones.ComprasDAO;
import itson.persistenciarutapp.implementaciones.Usuario;
import itson.persistenciarutapp.implementaciones.UsuariosDAO;
import itson.persistenciarutapp.implementaciones.Viaje;
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
        String idUsuario = compraDTO.getUsuario().getId();         // String
        Date fechaCompra = compraDTO.getFecha();                   // Date

        // El DAO ya se encarga de convertir String a ObjectId internamente
        String idCompra = comprasDAO.obtenerIdDeCompra(idUsuario, fechaCompra);
        if (idCompra == null) {
            throw new RuntimeException("No se pudo encontrar la compra para cancelación.");
        }

        comprasDAO.cancelarCompra(idCompra); // también recibe String ahora

        // Obtener datos del viaje para liberar asientos
        Viaje viaje = viajesDAO.consultarViajePorId(compraDTO.getViaje().getIdViaje());
        camionesDAO.liberarAsientos(viaje.getCamion().getNumeroCamion(), compraDTO.getListaAsiento());

        // Reembolso al monedero del usuario
        double montoReembolsado = compraDTO.getPrecio();
        UsuarioDTO usuario = compraDTO.getUsuario();
        usuario.setSaldoMonedero(usuario.getSaldoMonedero() + montoReembolsado);

        // Convertir DTO a entidad usando setIdFromString
        Usuario usuarioEntidad = UsuarioMapper.toEntity(usuario);

        usuariosDAO.actualizarSaldo(usuarioEntidad);
    }

}
