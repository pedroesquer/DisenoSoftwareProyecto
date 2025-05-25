// En ReseñasBO.java actualizado sin uso de ObjectId en la lógica de negocio
package itson.rutappbo.implementaciones;

import itson.persistenciarutapp.IReseñaDAO;
import itson.persistenciarutapp.ICamionesDAO;
import itson.persistenciarutapp.IUsuariosDAO;
import itson.persistenciarutapp.entidades.Reseña;
import itson.persistenciarutapp.implementaciones.ReseñaDAO;
import itson.persistenciarutapp.implementaciones.CamionesDAO;
import itson.persistenciarutapp.implementaciones.UsuariosDAO;
import itson.rutappbo.IReseñaBO;
import itson.rutappdto.ReseñaDTO;
import itson.rutappdto.UsuarioDTO;
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
        ValidadorReseñas.validar(reseñaDTO);

        UsuarioDTO usuarioDTO = UsuarioActivoManager.getInstancia().getUsuario();
        String idUsuario = usuarioDTO.getId();

        String idCamion = camionesDAO.obtenerIdCamionPorNumero(reseñaDTO.getNumeroCamion());

        if (idCamion == null) {
            throw new Exception("El camión especificado no existe.");
        }

        int conteo = reseñasDAO.contarReseñasUsuarioPorCamion(idUsuario, idCamion);
        if (conteo >= 3) {
            throw new Exception("Solo puedes dejar hasta 3 reseñas por camión.");
        }

        reseñaDTO.setFecha(new Date());

        Reseña reseña = ReseñaMapper.toEntity(reseñaDTO, idUsuario, idCamion);

        reseñasDAO.agregarReseña(reseña);
    }

    @Override
    public List<ReseñaDTO> obtenerReseñasPorCamion(String numeroCamion) {
        String idCamion = camionesDAO.obtenerIdCamionPorNumero(numeroCamion);
        List<Reseña> reseñas = reseñasDAO.obtenerReseñasPorCamion(idCamion);

        return reseñas.stream()
                .map(r -> {
                    String nombreUsuario = usuariosDAO.obtenerNombrePorId(r.obtenerIdUsuarioComoString());
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
        if (!reseña.obtenerIdUsuarioComoString().equals(idUsuarioActivo)) {
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
