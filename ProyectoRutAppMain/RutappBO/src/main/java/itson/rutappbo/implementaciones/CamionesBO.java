/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.rutappbo.implementaciones;

import enumm.estadoAsiento;
import excepciones.NegocioException;
import itson.persistenciarutapp.ICamionesDAO;
import Entidades.Asiento;
import Entidades.Camion;
import itson.persistenciarutapp.implementaciones.CamionesDAO;
import itson.rutappbo.ICamionesBO;
import itson.rutappdto.AsientoBoletoDTO;
import itson.rutappdto.AsientoDTO;
import itson.rutappdto.CamionDTO;
import java.util.List;
import java.util.stream.Collectors;
import mappers.CamionMapper;
//import org.bson.types.ObjectId;

/**
 *
 * @author chris
 */
public class CamionesBO implements ICamionesBO {

    private final ICamionesDAO camionesDAO;

    public CamionesBO() {
        this.camionesDAO = new CamionesDAO();
    }

    @Override
    public CamionDTO obtenerCamion(String numeroDeCamion) {
        Camion camion = camionesDAO.consultarCamionPorId(numeroDeCamion);
        return camion != null ? CamionMapper.toDTO(camion) : null;
    }

    @Override
    public List<CamionDTO> obtenerTodos() {
        return camionesDAO.consultarTodosLosCamiones().stream()
                .map(CamionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public int contarAsientosLibres(String numeroDeCamion) {
        Camion camion = camionesDAO.consultarCamionPorId(numeroDeCamion);
        if (camion == null) {
            return 0;
        }

        return (int) camion.getAsientos().stream()
                .filter(a -> "LIBRE".equalsIgnoreCase(a.getEstado()))
                .count();
    }

    @Override
    public boolean cambiarEstadoAsiento(String numeroDeCamion, int numeroAsiento, String nuevoEstado) {
        Camion camion = camionesDAO.consultarCamionPorId(numeroDeCamion);
        if (camion == null) {
            return false;
        }

        for (Asiento a : camion.getAsientos()) {
            if (a.getNumero() == numeroAsiento) {
                a.setEstado(nuevoEstado);
                camionesDAO.actualizarCamion(camion);
                return true;
            }
        }
        return false;
    }

    @Override
    public void ocuparAsientos(String idCamion, List<AsientoBoletoDTO> asientos) throws NegocioException {
        if (asientos.isEmpty()) {
            throw new NegocioException("No hay asientos seleccionados");
        }
        camionesDAO.ocuparAsientos(idCamion, asientos);
    }

    @Override
    public List<AsientoDTO> obtenerAsientosDisponibles(String numeroDeCamion) {
        List<Asiento> asientos = camionesDAO.obtenerAsientosDisponibles(numeroDeCamion);

        return asientos.stream().map(a -> new AsientoDTO(
                (long) a.getNumero(),
                "OCUPADO".equalsIgnoreCase(a.getEstado()) ? estadoAsiento.OCUPADO : estadoAsiento.LIBRE,
                String.valueOf(a.getNumero())
        )).collect(Collectors.toList());
    }

    @Override
    public String obtenerIdPorNumero(String numeroDeCamion) {
        Camion camion = camionesDAO.consultarCamionPorId(numeroDeCamion);
        return camion != null ? camion.getIdAsString() : null;
    }
}
