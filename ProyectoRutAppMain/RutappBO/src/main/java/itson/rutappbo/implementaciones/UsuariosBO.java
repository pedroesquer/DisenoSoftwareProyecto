package itson.rutappbo.implementaciones;

import itson.persistenciarutapp.IUsuariosDAO;
import itson.persistenciarutapp.implementaciones.UsuariosDAO;
import itson.rutappbo.IUsuariosBO;
import itson.rutappdto.AccesoUsuarioDTO;

/**
 * ClaseBO que se comunica con Usuarios
 *
 * @author BusSoft®
 */
public class UsuariosBO implements IUsuariosBO {

    IUsuariosDAO usuariosDAO = new UsuariosDAO();

    public Boolean autenticar(AccesoUsuarioDTO usuario) {
        if (usuario != null) {
            usuariosDAO.validarLogin(usuario.getNumeroTelefonico(), usuario.getContrasena());
        }
        return null;

    }
}
