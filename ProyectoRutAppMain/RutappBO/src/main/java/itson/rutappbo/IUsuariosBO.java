package itson.rutappbo;

import itson.rutappdto.UsuarioDTO;
import org.bson.types.ObjectId;

public interface IUsuariosBO {

    /**
     * Intenta registrar un nuevo usuario.
     * @param nuevoUsuario DTO con datos del nuevo usuario
     * @return Mensaje de validación o éxito
     */
    String registrarUsuario(UsuarioDTO nuevoUsuario);

    /**
     * Intenta autenticar al usuario.
     * @param usuario DTO con número y contraseña
     * @return Mensaje de validación o éxito
     */
    String login(UsuarioDTO usuario);

    /**
     * Retorna el usuario actualmente autenticado.
     * @return UsuarioDTO activo
     */
    UsuarioDTO obtenerUsuario();

    /**
     * Descuenta saldo del usuario y actualiza en la base.
     * @param usuario Usuario con saldo actualizado
     * @return true si fue exitoso
     */
    boolean descontarSaldo(UsuarioDTO usuario);

    /**
     * Agrega saldo al usuario y actualiza en la base.
     * @param usuario Usuario con saldo actualizado
     * @return true si fue exitoso
     */
    boolean agregarSaldo(UsuarioDTO usuario);
    
    String obtenerNombrePorId(ObjectId id);
}
