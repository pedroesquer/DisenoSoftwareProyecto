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
 * Implementación de la interfaz {@link IUsuariosDAO} que maneja las operaciones
 * CRUD para usuarios en la base de datos MongoDB.
 * 
 * Esta clase permite agregar nuevos usuarios, buscar usuarios por número telefónico
 * o ID, y actualizar su saldo en el monedero.
 */
public class UsuariosDAO implements IUsuariosDAO {

    /** Nombre de la colección que almacena los usuarios. */
    private final String COLECCION = "usuarios";

    /** Campo utilizado para indexar el número telefónico. */
    private final String CAMPO_TELEFONICO = "numeroTelefonico";

    /**
     * Constructor que inicializa la colección de usuarios y crea un índice único
     * sobre el campo número telefónico para evitar duplicados.
     */
    public UsuariosDAO() {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = baseDatos.getCollection(COLECCION, Usuario.class);

        // Crear índice único para el campo número telefónico
        coleccion.createIndex(
                Indexes.ascending(CAMPO_TELEFONICO),
                new IndexOptions().unique(true)
        );
    }

    /**
     * Inserta un nuevo usuario en la colección.
     *
     * @param usuario el objeto {@link Usuario} a insertar.
     * @return el usuario insertado.
     */
    @Override
    public Usuario agregarUsuario(Usuario usuario) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = baseDatos.getCollection(COLECCION, Usuario.class);
        coleccion.insertOne(usuario);
        return usuario;
    }

    /**
     * Busca un usuario por su número telefónico.
     *
     * @param numeroTel número telefónico del usuario.
     * @return el objeto {@link Usuario} si se encuentra, o {@code null} si no.
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
     * Actualiza el saldo del monedero de un usuario.
     *
     * @param usuario el usuario con el nuevo saldo.
     * @return {@code true} si el documento fue modificado, {@code false} en caso contrario.
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

    /**
     * Consulta un usuario por su identificador único.
     *
     * @param id el ID del usuario como {@link ObjectId}.
     * @return el objeto {@link Usuario} encontrado o {@code null} si no existe.
     */
    @Override
    public Usuario consultarUsuarioPorId(ObjectId id) {
        MongoCollection<Usuario> coleccion = ManejadorConexiones.obtenerBaseDatos()
                .getCollection(COLECCION, Usuario.class);
        return coleccion.find(Filters.eq("_id", id)).first();
    }
}
