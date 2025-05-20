package itson.persistenciarutapp.implementaciones;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;

public class ManejadorConexiones {

    private static final String BASE_DATOS = "RutAppBD";
    
    // Reemplaza <db_password> con tu contraseña codificada si tiene caracteres especiales
    private static final String URL = "mongodb+srv://rutapp:rutappdiseno@clusterrutapp.fjy5ye9.mongodb.net/?retryWrites=true&w=majority";

    public static MongoDatabase obtenerBaseDatos() {
        // Configurar Codec para soportar POJOs automáticamente
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );

        // Configurar cliente con URI y Codec
        MongoClientSettings configuraciones = MongoClientSettings.builder()
            .applyConnectionString(new com.mongodb.ConnectionString(URL))
            .codecRegistry(pojoCodecRegistry)
            .build();

        // Crear cliente
        MongoClient cliente = MongoClients.create(configuraciones);

        // Obtener base de datos
        return cliente.getDatabase(BASE_DATOS);
    }
}
