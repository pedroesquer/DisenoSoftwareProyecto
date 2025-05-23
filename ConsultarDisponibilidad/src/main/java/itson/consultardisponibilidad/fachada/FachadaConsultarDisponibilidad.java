/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.consultardisponibilidad.fachada;

import itson.consultardisponibilidad.Interfaz.IConsultarDisponibilidad;
import itson.consultardisponibilidad.control.ControlConsultarDisponibilidad;
import itson.rutappdto.AsientoDTO;
import itson.rutappdto.CamionDTO;
import itson.rutappdto.CompraDTO;
import itson.rutappdto.ReseñaDTO;
import itson.rutappdto.UsuarioDTO;
import itson.rutappdto.ViajeDTO;
import java.util.List;

/**
 *
 * @author Bussoft®
 */
public class FachadaConsultarDisponibilidad implements IConsultarDisponibilidad {

    @Override
    public List<ViajeDTO> consultarViajesDisponibles(ViajeDTO viaje) {
        return ControlConsultarDisponibilidad.getInstancia().obtenerViajesDisponibles(viaje);
    }

    @Override
    public List<AsientoDTO> consultarAsientosDisponibles(CamionDTO camion) {
        return ControlConsultarDisponibilidad.getInstancia().obtenerAsientosDisponibles(camion);
    }

    @Override
    public List<String> consultarDestinos(String origen) {
        return ControlConsultarDisponibilidad.getInstancia().obtenerDestinos(origen);
    }

    @Override
    public List<CompraDTO> consultarComprasPorUsuario(UsuarioDTO usuario) {
        return ControlConsultarDisponibilidad.getInstancia().obtenerCompras(usuario);
    }

    @Override
    public List<CompraDTO> obtenerCompras(UsuarioDTO usuario) {
        return ControlConsultarDisponibilidad.getInstancia().obtenerCompras(usuario);
    }

    @Override
    public void agregarReseña(ReseñaDTO reseña) throws Exception {
        ControlConsultarDisponibilidad.getInstancia().agregarReseña(reseña);
    }

    @Override
    public List<ReseñaDTO> obtenerReseñasPorCamion(String numeroCamion) {
        return ControlConsultarDisponibilidad.getInstancia().obtenerReseñasPorCamion(numeroCamion);
    }

    @Override
    public boolean eliminarReseña(String idReseña) {
        return ControlConsultarDisponibilidad.getInstancia().eliminarReseña(idReseña);
    }
    
    public void eliminarCompra(CompraDTO compra){
        ControlConsultarDisponibilidad.getInstancia().cancelarCompra(compra);
    }
}
