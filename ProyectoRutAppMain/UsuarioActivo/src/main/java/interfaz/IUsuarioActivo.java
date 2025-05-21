package interfaz;

import itson.rutappdto.UsuarioDTO;

/**
 * Interfaz que estipula los métodos para la sesión del usuario.
 * @author BusSoft®
 */
public interface IUsuarioActivo {
    
    
    
    public abstract void iniciarSesion(UsuarioDTO usuario);
    
    public abstract void cerrarSesion();
   
    public UsuarioDTO obtenerUsuarioActual();
    
    public boolean haySesionActiva();
    
    
    
}
