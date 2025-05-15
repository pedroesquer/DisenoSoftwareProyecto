
package itson.persistenciarutapp.implementaciones;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;


/**
 *
 * @author pedro
 */
public class ManejadorConexiones {
    private static final String BASE_DATOS = "RutAppDB";
    
        public static MongoDatabase obtenerBaseDatos() {
        //Configuraciones del mapeador automático
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        
        //asignar la confirmacion del mapeador con la conexion para que nuestras clases POJO sean reconocidas en automático
        MongoClientSettings configuraciones = MongoClientSettings.builder()
                                                                .codecRegistry(pojoCodecRegistry)
                                                                .build();
        //Objeto que representa una conexión a la base de datos
        MongoClient cliente = MongoClients.create(configuraciones);
        //Base de datos
        MongoDatabase baseDatos = cliente.getDatabase(BASE_DATOS);
        return baseDatos;
        }
}
