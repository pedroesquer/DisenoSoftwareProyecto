package itson.rutappbo.implementaciones;

import itson.persistenciarutapp.IUsuariosDAO;
import itson.persistenciarutapp.implementaciones.Usuario;
import itson.persistenciarutapp.implementaciones.UsuariosDAO;
import itson.rutappbo.IUsuariosBO;
import itson.rutappdto.AccesoUsuarioDTO;

/**
 * ClaseBO que se comunica con Usuarios
 *
 * @author BusSoft®
 */
public class UsuariosBO implements IUsuariosBO{

    private final IUsuariosDAO usuariosDAO;

    
    public UsuariosBO() {
        this.usuariosDAO = new UsuariosDAO();
    }

    /**
     * Intenta registrar un usuario nuevo.
     * @param nuevoUsuario DTO con datos de registro
     * @return Mensaje de éxito o error
     */
    public String registrarUsuario(AccesoUsuarioDTO nuevoUsuario) {
        String validacion = validarDatos(nuevoUsuario);
        if (!validacion.equals("OK")) {
            return validacion;
        }

        if (usuariosDAO.consultarUsuarioPorNumeroTelefonico(nuevoUsuario.getNumeroTelefonico()) != null) {
            return "El número telefónico ya está registrado.";
        }

        usuariosDAO.agregarUsuario(nuevoUsuario);
        return "Registro exitoso.";
    }

    /**
     * Intenta iniciar sesión.
     * @param numeroTelefonico número telefónico
     * @param contrasena contraseña
     * @return mensaje según resultado
     */
    public String login(String numeroTelefonico, String contrasena) {
        if (numeroTelefonico == null || numeroTelefonico.trim().isEmpty()) {
            return "Número telefónico requerido.";
        }

        if (!numeroTelefonico.matches("\\d{10}")) {
            return "El número telefónico debe contener 10 dígitos.";
        }

        if (contrasena == null || contrasena.trim().isEmpty()) {
            return "Contraseña requerida.";
        }

        Usuario usuario = usuariosDAO.validarLogin(numeroTelefonico, contrasena);
        if (usuario == null) {
            return "Número telefónico o contraseña incorrectos.";
        }

        return "Inicio de sesión exitoso.";
    }

    public String autenticar(AccesoUsuarioDTO usuario) {
        return login(usuario.getNumeroTelefonico(), usuario.getContrasena());
    }

    /**
     * Valida los campos del DTO
     * @param usuario DTO
     * @return "OK" si es válido, mensaje de error si no
     */
    private String validarDatos(AccesoUsuarioDTO usuario) {
        if (usuario == null) return "Datos de usuario no proporcionados.";

        String numero = usuario.getNumeroTelefonico();
        String pass = usuario.getContrasena();

        if (numero == null || numero.trim().isEmpty()) {
            return "Número telefónico requerido.";
        }

        if (!numero.matches("\\d{10}")) {
            return "El número telefónico debe contener exactamente 10 dígitos.";
        }

        if (pass == null || pass.trim().isEmpty()) {
            return "Contraseña requerida.";
        }

        if (pass.length() < 6) {
            return "La contraseña debe tener al menos 6 caracteres.";
        }

        return "OK";
    }
}
