package itson.persistenciarutapp.implementaciones;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 * Clase utilitaria que se encarga de configurar y proporcionar la conexión
 * a la base de datos MongoDB para la aplicación RutApp.
 * 
 * Utiliza codec registry para mapear automáticamente los documentos de MongoDB
 * a objetos POJO en Java.
 */
public class ManejadorConexiones {

    /** Nombre de la base de datos utilizada por la aplicación. */
    private static final String BASE_DATOS = "RutAppBD";

    /** URL de conexión al clúster de MongoDB. */
    private static final String URL = "mongodb+srv://rutapp:rutappdiseno@clusterrutapp.fjy5ye9.mongodb.net/?retryWrites=true&w=majority";

    /**
     * Establece y devuelve una conexión a la base de datos configurando codecs
     * para permitir el mapeo automático entre documentos y POJOs.
     *
     * @return una instancia de {@link MongoDatabase} conectada a RutAppBD.
     */
    public static MongoDatabase obtenerBaseDatos() {
        // Crear un codec registry que soporte POJOs automáticamente
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );

        // Configurar cliente Mongo con URI y codec registry
        MongoClientSettings configuraciones = MongoClientSettings.builder()
            .applyConnectionString(new com.mongodb.ConnectionString(URL))
            .codecRegistry(pojoCodecRegistry)
            .build();

        // Crear el cliente MongoDB
        MongoClient cliente = MongoClients.create(configuraciones);

        // Obtener y retornar la base de datos
        return cliente.getDatabase(BASE_DATOS);
    }
}
