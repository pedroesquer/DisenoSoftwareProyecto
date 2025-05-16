
package itson.rutappdto;

/**
 * Esta clase representa un objeto de acceso para un usuario, con los detalles de su número telefónico 
 * y su contraseña. Se utiliza para almacenar y gestionar los datos de acceso de un usuario.
 * 
 * @author BusSoft®
 */
public class AccesoUsuarioDTO {

    private String id;
    private String numeroTelefonico;
    private String contrasena;

    /**
     * Constructor que inicializa un objeto de tipo AccesoUsuarioDTO con los valores proporcionados.
     * 
     * @param id El identificador único del usuario.
     * @param numeroTelefonico El número telefónico asociado al usuario.
     * @param contrasena La contraseña asociada al usuario.
     */
    public AccesoUsuarioDTO(String id, String numeroTelefonico, String contrasena) {
        this.id = id;
        this.numeroTelefonico = numeroTelefonico;
        this.contrasena = contrasena;
    }

    public AccesoUsuarioDTO(String numeroTelefonico, String contrasena) {
        this.numeroTelefonico = numeroTelefonico;
        this.contrasena = contrasena;
    }

    /**
     * Obtiene el número telefónico del usuario.
     * 
     * @return El número telefónico del usuario.
     */
    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }

    /**
     * Obtiene la contraseña del usuario.
     * 
     * @return La contraseña del usuario.
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Establece el número telefónico del usuario.
     * 
     * @param numeroTelefonico El nuevo número telefónico del usuario.
     */
    public void setNumeroTelefonico(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

    /**
     * Establece la contraseña del usuario.
     * 
     * @param contrasena La nueva contraseña del usuario.
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
