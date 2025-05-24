// En ReseñasBO.java actualizado sin uso de ObjectId en la lógica de negocio
package itson.rutappbo.implementaciones;

import itson.persistenciarutapp.IReseñaDAO;
import itson.persistenciarutapp.ICamionesDAO;
import itson.persistenciarutapp.IUsuariosDAO;
import itson.persistenciarutapp.implementaciones.Reseña;
import itson.persistenciarutapp.implementaciones.ReseñaDAO;
import itson.persistenciarutapp.implementaciones.CamionesDAO;
import itson.persistenciarutapp.implementaciones.UsuariosDAO;
import itson.rutappbo.IReseñaBO;
import itson.rutappdto.ReseñaDTO;
import itson.rutappdto.UsuarioDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;
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

        // Construir la entidad Reseña
        Reseña reseña = new Reseña();
        reseña.setUsuario(new ObjectId(idUsuario));
        reseña.setCamion(new ObjectId(idCamion));
        reseña.setComentario(reseñaDTO.getComentario());
        reseña.setCalificacion(reseñaDTO.getCalificacion());
        reseña.setFecha(new Date());

        // Guardar la reseña en la base de datos
        reseñasDAO.agregarReseña(reseña);
    }

    @Override
    public List<ReseñaDTO> obtenerReseñasPorCamion(String numeroCamion) {
        String idCamion = camionesDAO.obtenerIdCamionPorNumero(numeroCamion);
        List<Reseña> reseñas = reseñasDAO.obtenerReseñasPorCamion(idCamion);

        List<ReseñaDTO> dtos = new ArrayList<>();
        for (Reseña r : reseñas) {
            ReseñaDTO dto = new ReseñaDTO();
            dto.setId(r.getId().toHexString());
            dto.setNombreUsuario(usuariosDAO.obtenerNombrePorId(r.getUsuario().toHexString()));
            dto.setNumeroCamion(numeroCamion);
            dto.setComentario(r.getComentario());
            dto.setCalificacion(r.getCalificacion());
            dto.setFecha(r.getFecha());
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public boolean eliminarReseña(String idReseña) {
        return reseñasDAO.eliminarReseñaPorId(idReseña);
    }

}
