package itson.persistenciarutapp.entidades;

import java.util.List;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

/**
 * Representa un camión dentro del sistema. Cada camión tiene un identificador único,
 * un número de camión visible para los usuarios y una lista de asientos.
 * 
 * Esta clase es utilizada en la capa de persistencia para mapear los documentos
 * almacenados en la base de datos MongoDB.
 */
public class Camion {

    /** Identificador único del camión en MongoDB. */
    @BsonId
    private ObjectId id;
    
    /** Número identificador del camión (por ejemplo: CAM001, CAM002). */
    @BsonProperty("numeroDeCamion")
    private String numeroDeCamion;

    /** Lista de asientos que pertenecen al camión. */
    @BsonProperty("asientos")
    private List<Asiento> asientos;

    /**
     * Constructor vacío necesario para frameworks de serialización/deserialización.
     */
    public Camion() {}

    /**
     * Constructor para crear un camión sin ID, útil para inserciones nuevas.
     *
     * @param numeroDeCamion número del camión.
     * @param asientos lista de asientos asignados al camión.
     */
    public Camion(String numeroDeCamion, List<Asiento> asientos) {
        this.numeroDeCamion = numeroDeCamion;
        this.asientos = asientos;
    }

    /**
     * Constructor completo del camión, incluyendo el ID.
     *
     * @param id identificador único del camión.
     * @param numeroDeCamion número del camión.
     * @param asientos lista de asientos del camión.
     */
    public Camion(ObjectId id, String numeroDeCamion, List<Asiento> asientos) {
        this.id = id;
        this.numeroDeCamion = numeroDeCamion;
        this.asientos = asientos;
    }

    /**
     * Obtiene el ID del camión.
     *
     * @return el ID como ObjectId.
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Establece el ID del camión.
     *
     * @param id nuevo ID del camión.
     */
    public void setId(ObjectId id) {
        this.id = id;
    }

    /**
     * Obtiene el número del camión.
     *
     * @return número del camión.
     */
    public String getNumeroDeCamion() {
        return numeroDeCamion;
    }

    /**
     * Establece el número del camión.
     *
     * @param numeroDeCamion nuevo número del camión.
     */
    public void setNumeroDeCamion(String numeroDeCamion) {
        this.numeroDeCamion = numeroDeCamion;
    }

    /**
     * Obtiene la lista de asientos del camión.
     *
     * @return lista de objetos Asiento.
     */
    public List<Asiento> getAsientos() {
        return asientos;
    }

    /**
     * Establece la lista de asientos del camión.
     *
     * @param asientos nueva lista de asientos.
     */
    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
    }
    
    @BsonIgnore
    public String obtenerIdComooString() {
        return id != null ? id.toHexString() : null;
    }

    @BsonIgnore
    public void asignarIdDesdeeString(String idStr) {
        this.id = (idStr != null && !idStr.isBlank()) ? new ObjectId(idStr) : null;
    }

    /**
     * Devuelve una representación textual del camión.
     *
     * @return una cadena con los datos del camión.
     */
    @Override
    public String toString() {
        return "Camion{" + "id=" + id + ", numeroDeCamion=" + numeroDeCamion + ", asientos=" + asientos + '}';
    }
}
