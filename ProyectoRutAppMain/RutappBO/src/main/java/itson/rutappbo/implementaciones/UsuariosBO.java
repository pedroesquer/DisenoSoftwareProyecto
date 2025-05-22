package itson.rutappbo.implementaciones;

import itson.persistenciarutapp.IUsuariosDAO;
import itson.persistenciarutapp.implementaciones.Usuario;
import itson.persistenciarutapp.implementaciones.UsuariosDAO;
import itson.rutappbo.IUsuariosBO;
import itson.rutappdto.UsuarioDTO;
import org.bson.types.ObjectId;
import usuarioActivoManager.UsuarioActivoManager;

public class UsuariosBO implements IUsuariosBO {

    private final IUsuariosDAO usuariosDAO;

    public UsuariosBO() {
        this.usuariosDAO = new UsuariosDAO();
    }

    @Override
    public String registrarUsuario(UsuarioDTO dto) {
        String validacion = validarDatos(dto);
        if (!validacion.equals("OK")) {
            return validacion;
        }

        Usuario existente = usuariosDAO.consultarUsuarioPorNumeroTelefonico(dto.getNumeroTelefonico());
        if (existente != null) {
            return "El número telefónico ya está registrado.";
        }

        Usuario nuevoUsuario = new Usuario(
            dto.getNumeroTelefonico(),
            dto.getNombre(),
            dto.getContrasena(),
            dto.getSaldoMonedero()
        );

        usuariosDAO.agregarUsuario(nuevoUsuario);
        return "Registro exitoso.";
    }

    @Override
    public String login(UsuarioDTO dtoLogin) {
        if (dtoLogin.getNumeroTelefonico() == null || dtoLogin.getNumeroTelefonico().trim().isEmpty()) {
            return "Número telefónico requerido.";
        }

        if (!dtoLogin.getNumeroTelefonico().matches("\\d{10}")) {
            return "El número telefónico debe contener 10 dígitos.";
        }

        if (dtoLogin.getContrasena() == null || dtoLogin.getContrasena().trim().isEmpty()) {
            return "Contraseña requerida.";
        }

        Usuario usuario = usuariosDAO.consultarUsuarioPorNumeroTelefonico(dtoLogin.getNumeroTelefonico());

        if (usuario == null || !usuario.getContrasenia().equals(dtoLogin.getContrasena())) {
            return "Número telefónico o contraseña incorrectos.";
        }

        UsuarioDTO dto = new UsuarioDTO(
            usuario.getId().toHexString(),
            usuario.getNombre(),
            usuario.getNumeroTelefonico(),
            usuario.getContrasenia(),
            usuario.getSaldoMonedero()
        );

        UsuarioActivoManager.getInstancia().iniciarSesion(dto);
        return "Inicio de sesión exitoso.";
    }

    @Override
    public UsuarioDTO obtenerUsuario() {
        return UsuarioActivoManager.getInstancia().getUsuario();
    }

    @Override
    public boolean descontarSaldo(UsuarioDTO dto) {
        if (dto == null || dto.getId() == null) return false;

        Usuario usuario = new Usuario(
            new ObjectId(dto.getId()),
            dto.getNumeroTelefonico(),
            dto.getNombre(),
            dto.getContrasena(),
            dto.getSaldoMonedero()
        );

        return usuariosDAO.actualizarSaldo(usuario);
    }

    @Override
    public boolean agregarSaldo(UsuarioDTO dto) {
        return descontarSaldo(dto); // lógica idéntica
    }

    /**
     * Valida los campos del DTO.
     */
    private String validarDatos(UsuarioDTO dto) {
        if (dto == null) return "Datos de usuario no proporcionados.";

        String numero = dto.getNumeroTelefonico();
        String pass = dto.getContrasena();

        if (numero == null || numero.trim().isEmpty()) return "Número telefónico requerido.";
        if (!numero.matches("\\d{10}")) return "El número telefónico debe contener 10 dígitos.";
        if (pass == null || pass.trim().isEmpty()) return "Contraseña requerida.";
        if (pass.length() < 6) return "La contraseña debe tener al menos 6 caracteres.";

        return "OK";
    }
}
