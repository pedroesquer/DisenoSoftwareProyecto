/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.rutappbo.implementaciones;

import itson.persistenciarutapp.IReseñaDAO;
import itson.persistenciarutapp.implementaciones.Reseña;
import itson.persistenciarutapp.implementaciones.ReseñaDAO;
import itson.rutappbo.ICamionesBO;
import itson.rutappbo.IReseñaBO;
import itson.rutappbo.IUsuariosBO;
import itson.rutappdto.ReseñaDTO;
import itson.rutappdto.UsuarioDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;
import usuarioActivoManager.UsuarioActivoManager;

/**
 *
 * @author multaslokas33
 */
public class ReseñasBO implements IReseñaBO {

    private final IReseñaDAO reseñasDAO = new ReseñaDAO();
    private final ICamionesBO camionesBO;
    private final IUsuariosBO usuariosBO;

    public ReseñasBO(IUsuariosBO usuariosBO, ICamionesBO camionesBO) {
        this.usuariosBO = usuariosBO;
        this.camionesBO = camionesBO;
    }

    @Override
    public void agregarReseña(ReseñaDTO reseñaDTO) throws Exception {
        UsuarioDTO usuarioDTO = UsuarioActivoManager.getInstancia().getUsuario();
        ObjectId idUsuario = new ObjectId(usuarioDTO.getId());
        ObjectId idCamion = camionesBO.obtenerIdPorNumero(reseñaDTO.getNumeroCamion());

        // Validación para limitar a 3 reseñas por camión por usuario
        int conteo = reseñasDAO.contarReseñasUsuarioPorCamion(idUsuario, idCamion);
        if (conteo >= 3) {
            throw new Exception("Solo puedes dejar hasta 3 reseñas por camión.");
        }

        Reseña reseña = new Reseña();
        reseña.setUsuario(idUsuario);
        reseña.setCamion(idCamion);
        reseña.setComentario(reseñaDTO.getComentario());
        reseña.setCalificacion(reseñaDTO.getCalificacion());
        reseña.setFecha(new Date());

        reseñasDAO.agregarReseña(reseña);
    }

    @Override
    public List<ReseñaDTO> obtenerReseñasPorCamion(String numeroCamion) {
        ObjectId idCamion = camionesBO.obtenerIdPorNumero(numeroCamion);
        List<Reseña> reseñas = reseñasDAO.obtenerReseñasPorCamion(idCamion);

        List<ReseñaDTO> dtos = new ArrayList<>();
        for (Reseña r : reseñas) {
            ReseñaDTO dto = new ReseñaDTO();
            dto.setId(r.getId().toString());
            dto.setNombreUsuario(usuariosBO.obtenerNombrePorId(r.getUsuario()));
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
        ObjectId id = new ObjectId(idReseña);
        return reseñasDAO.eliminarReseñaPorId(id);
    }
}
