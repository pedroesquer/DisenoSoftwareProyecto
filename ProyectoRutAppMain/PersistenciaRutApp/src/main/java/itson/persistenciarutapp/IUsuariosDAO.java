package itson.persistenciarutapp;

import itson.persistenciarutapp.implementaciones.Usuario;

public interface IUsuariosDAO {

    /**
     * Agrega un nuevo usuario a la base de datos.
     * @param usuario Usuario a agregar
     * @return Usuario agregado
     */
    Usuario agregarUsuario(Usuario usuario);

    /**
     * Consulta un usuario por su número telefónico.
     * @param numeroTel Número telefónico a buscar
     * @return Usuario encontrado, o null si no existe
     */
    Usuario consultarUsuarioPorNumeroTelefonico(String numeroTel);

    /**
     * Actualiza el saldo del usuario en base al número telefónico.
     * @param usuario Usuario con nuevo saldo
     * @return true si la actualización fue exitosa
     */
    boolean actualizarSaldo(Usuario usuario);
}
