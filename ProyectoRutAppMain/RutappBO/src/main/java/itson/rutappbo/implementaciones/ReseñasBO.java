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

    private final IReseñaDAO reseñasDAO = new ReseñaDAO(); // <-- DAO instanciado aquí
    private final ICamionesBO camionesBO;
    private final IUsuariosBO usuariosBO;

    public ReseñasBO(IUsuariosBO usuariosBO, ICamionesBO camionesBO) {
        this.usuariosBO = usuariosBO;
        this.camionesBO = camionesBO;
    }

    @Override
    public void agregarReseña(ReseñaDTO reseñaDTO) throws Exception {
        UsuarioDTO usuarioDTO = UsuarioActivoManager.getInstancia().getUsuario();
        if (usuarioDTO == null || usuarioDTO.getId() == null) {
            throw new IllegalStateException("No hay sesión activa o el usuario no tiene ID");
        }

        ObjectId idUsuario = new ObjectId(usuarioDTO.getId());
        ObjectId idCamion = camionesBO.obtenerIdPorNumero(reseñaDTO.getNumeroCamion());

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
            //dto.setNombreUsuario(usuariosBO.obtenerNombrePorId(r.getUsuario())); // 💥 AQUI LA CORRECCIÓN
            dto.setNumeroCamion(numeroCamion);
            dto.setComentario(r.getComentario());
            dto.setCalificacion(r.getCalificacion());
            dto.setFecha(r.getFecha());
            dtos.add(dto);
        }
        return dtos;
    }
}
