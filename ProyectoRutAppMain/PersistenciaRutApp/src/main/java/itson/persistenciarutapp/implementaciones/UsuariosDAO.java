package itson.persistenciarutapp.implementaciones;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import itson.persistenciarutapp.IUsuariosDAO;
import itson.rutappdto.AccesoUsuarioDTO;

/**
 *
 * @author pedro
 */
public class UsuariosDAO implements IUsuariosDAO {

    private final String COLECCION = "usuarios";
    private final String CAMPO_TELEFONICO = "numeroTelefonico";

    // Constructor donde se crea el índice único
    public UsuariosDAO() {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = coleccion = baseDatos.getCollection(COLECCION, Usuario.class);

        // Crear índice único para numeroTelefonico
        coleccion.createIndex(
                Indexes.ascending(CAMPO_TELEFONICO),
                new IndexOptions().unique(true)
        );
    }

    @Override
    public Usuario agregarUsuario(AccesoUsuarioDTO nuevoUsuario) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = baseDatos.getCollection(COLECCION, Usuario.class);
        Usuario usuario = new Usuario(nuevoUsuario.getNumeroTelefonico(), nuevoUsuario.getContrasena());
        coleccion.insertOne(usuario);
        return usuario;
    }

    @Override
    public Usuario consultarUsuarioPorNumeroTelefonico(String numeroTel) {
        //Base de datos
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        //Colección
        MongoCollection<Usuario> coleccion = baseDatos.getCollection(COLECCION, Usuario.class);
        return coleccion.find(eq("numeroTelefonico", numeroTel)).first();
    }

    @Override
    public Usuario validarLogin(String numeroTelefonico, String contrasena) {
        Usuario usuario = consultarUsuarioPorNumeroTelefonico(numeroTelefonico);
        if (usuario != null && usuario.getContrasenia().equals(contrasena)) {
            return usuario; // No existe el usuario
        }
        return null;
    }

    @Override
    public Usuario autenticarUsuario(AccesoUsuarioDTO accesoUsuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
