package itson.persistenciarutapp.implementaciones;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import itson.persistenciarutapp.IUsuariosDAO;
import itson.rutappdto.AccesoUsuarioDTO;
import itson.rutappdto.UsuarioDTO;
import org.bson.Document;

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
    // Recuperando la base de datos y la colección de usuarios
    MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
    MongoCollection<Document> coleccion = baseDatos.getCollection(COLECCION);

    // Consulta para encontrar el usuario por número de teléfono
    Document usuarioDoc = coleccion.find(eq(CAMPO_TELEFONICO, numeroTel)).first();

    if (usuarioDoc != null) {
        // Obtenemos el saldoMonedero, y otros campos, si están en el documento
        String contrasena = usuarioDoc.getString("contrasenia");
        Double saldoMonedero = usuarioDoc.getDouble("saldoMonedero"); // Asegúrate que este campo esté en la base de datos

        // Crear el objeto Usuario con los datos obtenidos
        return new Usuario(numeroTel, contrasena, saldoMonedero);  // Retornar un Usuario
    }

    return null;
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
