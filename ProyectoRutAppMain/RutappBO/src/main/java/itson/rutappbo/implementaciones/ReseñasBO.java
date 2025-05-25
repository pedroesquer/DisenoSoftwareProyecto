// En ReseñasBO.java actualizado sin uso de ObjectId en la lógica de negocio
package itson.rutappbo.implementaciones;

import itson.persistenciarutapp.IReseñaDAO;
import itson.persistenciarutapp.ICamionesDAO;
import itson.persistenciarutapp.IUsuariosDAO;
import Entidades.Reseña;
import itson.persistenciarutapp.implementaciones.ReseñaDAO;
import itson.persistenciarutapp.implementaciones.CamionesDAO;
import itson.persistenciarutapp.implementaciones.UsuariosDAO;
import itson.rutappbo.IReseñaBO;
import itson.rutappdto.ReseñaDTO;
import itson.rutappdto.UsuarioDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import mappers.ReseñaMapper;
//import org.bson.types.ObjectId; YA NOOO LO USAMOOOOOOS 😎😎😎😎😎😎😎😎😎😎
import usuarioActivoManager.UsuarioActivoManager;
import util.ValidadorReseñas;

public class ReseñasBO implements IReseñaBO {

    private final IReseñaDAO reseñasDAO = new ReseñaDAO();
    private final ICamionesDAO camionesDAO = new CamionesDAO();
    private final IUsuariosDAO usuariosDAO = new UsuariosDAO();

    public ReseñasBO() {
    }

    @Override
    public void agregarReseña(ReseñaDTO reseñaDTO) throws Exception {
        // Validar los datos de la reseña
        ValidadorReseñas.validar(reseñaDTO);

        // Obtener el usuario activo
        UsuarioDTO usuarioDTO = UsuarioActivoManager.getInstancia().getUsuario();
        String idUsuario = usuarioDTO.getId();

        // Obtener el ID del camión por su número
        String idCamion = camionesDAO.obtenerIdCamionPorNumero(reseñaDTO.getNumeroCamion());

        if (idCamion == null) {
            throw new Exception("El camión especificado no existe.");
        }

        // Verificar si el usuario ya hizo 3 reseñas para ese camión
        int conteo = reseñasDAO.contarReseñasUsuarioPorCamion(idUsuario, idCamion);
        if (conteo >= 3) {
            throw new Exception("Solo puedes dejar hasta 3 reseñas por camión.");
        }

        // Asignar la fecha actual
        reseñaDTO.setFecha(new Date());

        // Mapear a entidad
        Reseña reseña = ReseñaMapper.toEntity(reseñaDTO, idUsuario, idCamion);

        // Guardar la reseña en la base de datos
        reseñasDAO.agregarReseña(reseña);
    }

    @Override
    public List<ReseñaDTO> obtenerReseñasPorCamion(String numeroCamion) {
        String idCamion = camionesDAO.obtenerIdCamionPorNumero(numeroCamion);
        List<Reseña> reseñas = reseñasDAO.obtenerReseñasPorCamion(idCamion);

        return reseñas.stream()
                .map(r -> {
                    String nombreUsuario = usuariosDAO.obtenerNombrePorId(r.getUsuarioAsString());
                    return ReseñaMapper.toDTO(r, nombreUsuario, numeroCamion);
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean eliminarReseña(String idReseña) {
        Reseña reseña = reseñasDAO.obtenerReseñaPorId(idReseña);
        if (reseña == null) {
            return false;
        }

        String idUsuarioActivo = UsuarioActivoManager.getInstancia().getUsuario().getId();
        if (!reseña.getUsuarioAsString().equals(idUsuarioActivo)) {
            return false;
        }

        long ahora = System.currentTimeMillis();
        long tiempoReseña = reseña.getFecha().getTime();
        if ((ahora - tiempoReseña) > 10 * 60 * 1000) {
            return false;
        }

        return reseñasDAO.eliminarReseñaPorId(idReseña);
    }
}
