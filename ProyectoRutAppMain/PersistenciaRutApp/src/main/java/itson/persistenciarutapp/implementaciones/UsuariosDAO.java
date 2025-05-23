package itson.persistenciarutapp.implementaciones;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import itson.persistenciarutapp.IUsuariosDAO;
import java.util.Objects;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Implementación de IUsuariosDAO que se encarga de la persistencia de usuarios.
 */
public class UsuariosDAO implements IUsuariosDAO {

    private final String COLECCION = "usuarios";
    private final String CAMPO_TELEFONICO = "numeroTelefonico";

    public UsuariosDAO() {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = baseDatos.getCollection(COLECCION, Usuario.class);

        // Crear índice único para número telefónico (evita duplicados)
        coleccion.createIndex(
                Indexes.ascending(CAMPO_TELEFONICO),
                new IndexOptions().unique(true)
        );
    }

    /**
     * Agrega un nuevo usuario a la colección.
     *
     * @param usuario el usuario a insertar
     * @return el usuario insertado
     */
    @Override
    public Usuario agregarUsuario(Usuario usuario) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = baseDatos.getCollection(COLECCION, Usuario.class);
        coleccion.insertOne(usuario);
        return usuario;
    }

    /**
     * Consulta un usuario por número telefónico.
     *
     * @param numeroTel el número telefónico
     * @return el usuario encontrado o null si no existe
     */
    @Override
    public Usuario consultarUsuarioPorNumeroTelefonico(String numeroTel) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection(COLECCION);

        Document usuarioDoc = coleccion.find(eq(CAMPO_TELEFONICO, numeroTel)).first();

        if (usuarioDoc == null) {
            return null;
        }

        ObjectId id = usuarioDoc.getObjectId("_id");
        String nombre = usuarioDoc.getString("nombre");
        String contrasena = usuarioDoc.getString("contrasenia");
        Double saldo = usuarioDoc.getDouble("saldoMonedero");

        return new Usuario(id, numeroTel, nombre, contrasena, saldo);
    }

    /**
     * Actualiza el saldo del usuario con el número dado.
     *
     * @param usuario el usuario con saldo actualizado
     * @return true si la operación modificó el documento
     */
    @Override
    public boolean actualizarSaldo(Usuario usuario) {
        if (usuario == null || usuario.getNumeroTelefonico() == null) {
            return false;
        }

        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection(COLECCION);

        Document filtro = new Document("numeroTelefonico", usuario.getNumeroTelefonico());
        Document actualizacion = new Document("$set",
                new Document("saldoMonedero", usuario.getSaldoMonedero()));

        return coleccion.updateOne(filtro, actualizacion).getModifiedCount() > 0;
    }

    @Override
    public Usuario consultarUsuarioPorId(ObjectId id) {
        MongoCollection<Usuario> coleccion = ManejadorConexiones.obtenerBaseDatos()
                .getCollection("usuarios", Usuario.class);
        return coleccion.find(Filters.eq("_id", id)).first();
    }
}
