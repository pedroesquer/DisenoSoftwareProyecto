package usuarioActivoManager;

import itson.rutappdto.UsuarioDTO;

/**
 *
 * @author Juan Heras
 */
public class UsuarioActivoManager {
    
    
    /**
     * Instancia unica de la clase (patrón Singleton). Se asegura que solo haya una instancia activa.
     */
    private static UsuarioActivoManager instancia;
    
    
    private UsuarioDTO usuario;
    
    
    private UsuarioActivoManager(){}
    
    
    /**
     * Método para obtener la unica instancia de usuario.
     * Utilizamos el patrón singleton para garantizar la inicialización segura en entornos concurrentes.
     * @return la instancia unica de UsuarioActivoManager
     */
    public static UsuarioActivoManager getInstancia(){
         if (instancia == null) {
            instancia = new UsuarioActivoManager();
        }
        return instancia;
    }
    
    /**
     * Método para iniciar sesión al usuario, si hubo coincidencia de credenciales se le asigna el usuario a la clase actual.
     * @param usuario 
     */
    public void iniciarSesion(UsuarioDTO usuario) {
        this.usuario = usuario;
    }
    
    public UsuarioDTO getUsuario(){
        return usuario;
    }
    
    /**
     * Método para cerrar la sesión del usuario, establece el usuarioDTO como null. 
     */
    public void cerrarSesion() {
        this.usuario = null;
    }
    
    /**
     * Método para verificar si hay una sesión iniciada o si esta cerrad.
     * @return true si si esta iniciada, false de lo contrario.
     */
    public boolean haySesionActiva() {
        return usuario != null;
    }
    
    
}
