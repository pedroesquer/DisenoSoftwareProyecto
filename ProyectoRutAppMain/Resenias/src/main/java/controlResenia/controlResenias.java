/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlResenia;

import itson.rutappbo.ICamionesBO;
import itson.rutappbo.IComprasBO;
import itson.rutappbo.IReseñaBO;
import itson.rutappbo.IUsuariosBO;
import itson.rutappbo.implementaciones.CamionesBO;
import itson.rutappbo.implementaciones.ComprasBO;
import itson.rutappbo.implementaciones.ReseñasBO;
import itson.rutappbo.implementaciones.UsuariosBO;
import itson.rutappdto.CompraDTO;
import itson.rutappdto.ReseñaDTO;
import java.util.List;

/**
 *
 * @author multaslokas33
 */
public class controlResenias {

    private static controlResenias instance;

    private final IReseñaBO reseñaBO;

    private controlResenias() {
// ------------------------RESEÑA----------------------------------                  
        this.reseñaBO = new ReseñasBO(); // |
//-----------------------------------------------------------------
    }

    public static controlResenias getInstancia() {
        if (instance == null) {
            instance = new controlResenias();
        }
        return instance;
    }

    /**
     * Agrega una reseña al camión actual del usuario logueado.
     *
     * @param reseñaDTO DTO con datos de la reseña
     * @throws java.lang.Exception
     */
    public void agregarReseña(ReseñaDTO reseñaDTO) throws Exception {
        util.ValidadorReseñas.validar(reseñaDTO);
        reseñaBO.agregarReseña(reseñaDTO);
    }

    /**
     * Obtiene todas las reseñas de un camión.
     *
     * @param numeroCamion número de camión
     * @return lista de reseñas
     */
    public List<ReseñaDTO> obtenerReseñasPorCamion(String numeroCamion) {
        return reseñaBO.obtenerReseñasPorCamion(numeroCamion);
    }

    /**
     * Elimina una reseña específica si cumple con los requisitos (ej. tiempo,
     * usuario).
     *
     * @param idReseña el ID de la reseña en formato String.
     * @return true si la reseña fue eliminada, false si no se eliminó.
     */
    public boolean eliminarReseña(String idReseña) {
        return reseñaBO.eliminarReseña(idReseña);
    }

}
