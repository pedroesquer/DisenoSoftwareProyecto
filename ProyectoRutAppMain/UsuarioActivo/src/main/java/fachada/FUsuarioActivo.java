package fachada;

import control.ControlUsuarioActivo;
import interfaz.IUsuarioActivo;
import itson.rutappdto.UsuarioDTO;

/**
 *
 * @author BusSoft®
 */
public class FUsuarioActivo implements IUsuarioActivo{

    private ControlUsuarioActivo controlUsuarioActivo;

    public FUsuarioActivo() {
        this.controlUsuarioActivo = new ControlUsuarioActivo();
    }
    
    @Override
    public void iniciarSesion(UsuarioDTO usuario) {
        controlUsuarioActivo.iniciarSesion(usuario);
    }

    @Override
    public void cerrarCesion() {
        controlUsuarioActivo.cerrarSesion();
    }

    @Override
    public UsuarioDTO obtenerUsuarioActual() {
        return controlUsuarioActivo.obtenerUsuario();
    }

    @Override
    public boolean haySesionActiva() {
        return controlUsuarioActivo.haySesionActiva();
    }
    
}
