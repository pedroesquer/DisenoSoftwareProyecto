
package control;

import itson.rutappdto.UsuarioDTO;
import usuarioActivoManager.UsuarioActivoManager;

/**
 * Clase controladora del subsistema UsuarioActivoManager. Que se encarga de gestionar toda lógica de negocio del mismo.
 * @author BusSoft®
 */
public class ControlUsuarioActivo {
    
    /**
     * Instancia unica del usuario. Encargado de mantener la sesión activa, cerrarla o iniciarla.
     */
    private final UsuarioActivoManager usuarioActivo;
    
    
    //ACA IRIA FALTA LOS BO

    public ControlUsuarioActivo() {
        this.usuarioActivo = UsuarioActivoManager.getInstancia();
    }
    
    /**
     * Método para establecer la sesión del usuario como la que se acaba de ingresar.
     * Se usara para poder utilizarla como patron singleton en cualquier momento
     * @param usuario 
     */
    public void iniciarSesion(UsuarioDTO usuario){
        usuarioActivo.iniciarSesion(usuario);
    }
    
    /**
     * Método para obtener el usuario de la sesión activa.
     * @return regresa una instancia de UsuarioDTO.
     */
    public UsuarioDTO obtenerUsuario(){
        return usuarioActivo.getUsuario();
    }
    
    /**
     * Método que establece la instancia de usuario como null, para asi cerrar la sesión.
     */
    public void cerrarSesion(){
        usuarioActivo.cerrarSesion();
    }
    
    /**
     * Método para verificar si esta activa o no la sesión.
     * @return True si hay sesion iniciada, false si no.
     */
    public boolean haySesionActiva(){
        return usuarioActivo.haySesionActiva();
    }
            
    
    
    
}
