package itson.persistenciarutapp.implementaciones;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import itson.persistenciarutapp.IUsuariosDAO;
import itson.rutappdto.AccesoUsuarioDTO;
import itson.rutappdto.UsuarioDTO;
import itson.rutappdto.ViajeDTO;
import java.util.LinkedList;
import java.util.List;
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
    public UsuarioDTO agregarUsuario(UsuarioDTO nuevoUsuario) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Usuario> coleccion = baseDatos.getCollection(COLECCION, Usuario.class);
        Usuario usuario = new Usuario(nuevoUsuario.getNumeroTelefonico(), nuevoUsuario.getNombre(), nuevoUsuario.getContrasena());
        coleccion.insertOne(usuario);
        return parsearUsuariosDTO(usuario);
    }

    @Override
    public UsuarioDTO consultarUsuarioPorNumeroTelefonico(String numeroTel) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection(COLECCION);

        Document usuarioDoc = coleccion.find(eq(CAMPO_TELEFONICO, numeroTel)).first();

        if (usuarioDoc != null) {
            String nombre = usuarioDoc.getString("nombre");
            String contrasena = usuarioDoc.getString("contrasenia");
            Double saldoMonedero = usuarioDoc.getDouble("saldoMonedero");

            Usuario usuario = new Usuario(numeroTel, nombre, contrasena, saldoMonedero); // ✅ orden correcto
            return parsearUsuariosDTO(usuario);
        }

        return null;
    }

    @Override
    public UsuarioDTO validarLogin(UsuarioDTO usuario) {
        UsuarioDTO usuarioConsultado = consultarUsuarioPorNumeroTelefonico(usuario.getNumeroTelefonico());
        if (usuarioConsultado != null && usuarioConsultado.getContrasena().equals(usuario.getContrasena())) {
            return usuarioConsultado;
        }
        return null;
    }

    public UsuarioDTO parsearUsuariosDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO(
                usuario.getNombre(), usuario.getNumeroTelefonico(), usuario.getContrasenia(), usuario.getSaldoMonedero());
        usuarioDTO.setSaldoMonedero(usuario.getSaldoMonedero());

        return usuarioDTO;
    }

    public List<UsuarioDTO> parsearListaUsuariosDTO(List<Usuario> listaUsuarios) {
        List<UsuarioDTO> listaDTO = new LinkedList<>();

        for (Usuario v : listaUsuarios) {
            listaDTO.add(parsearUsuariosDTO(v));
        }
        return listaDTO;
    }

    @Override
    public boolean actualizarSaldo(UsuarioDTO usuario) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection(COLECCION);

        Document filtro = new Document("numeroTelefonico", usuario.getNumeroTelefonico());
        Document actualizacion = new Document("$set", new Document("saldoMonedero", usuario.getSaldoMonedero()));

        return coleccion.updateOne(filtro, actualizacion).getModifiedCount() > 0;    }

    @Override
    public boolean agregarSaldo(UsuarioDTO usuario) {
        MongoDatabase baseDatos = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Document> coleccion = baseDatos.getCollection(COLECCION);

        Document filtro = new Document("numeroTelefonico", usuario.getNumeroTelefonico());
        Document actualizacion = new Document("$set", new Document("saldoMonedero", usuario.getSaldoMonedero()));

        return coleccion.updateOne(filtro, actualizacion).getModifiedCount() > 0; 
    }

}
