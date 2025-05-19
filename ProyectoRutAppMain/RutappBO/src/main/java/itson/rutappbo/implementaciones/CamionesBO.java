/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.rutappbo.implementaciones;

import itson.persistenciarutapp.ICamionesDAO;
import itson.persistenciarutapp.implementaciones.Asiento;
import itson.persistenciarutapp.implementaciones.Camion;
import itson.persistenciarutapp.implementaciones.CamionesDAO;
import itson.rutappbo.ICamionesBO;
import java.util.List;

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
    public Camion obtenerCamion(String numeroDeCamion) {
        return camionesDAO.consultarCamionPorId(numeroDeCamion);
    }

    @Override
    public List<Camion> obtenerTodos() {
        return camionesDAO.consultarTodosLosCamiones();
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
}

