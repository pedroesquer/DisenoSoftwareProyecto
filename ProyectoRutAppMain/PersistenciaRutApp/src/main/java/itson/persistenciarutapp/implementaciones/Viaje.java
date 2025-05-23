package itson.persistenciarutapp.implementaciones;

import itson.rutappdto.CamionDTO;
import java.util.Date;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

/**
 * Representa un viaje programado entre dos ciudades. Contiene información
 * sobre el precio del viaje, origen, destino, camión asignado y la fecha
 * en la que se realizará.
 * 
 * Esta clase se utiliza en la capa de persistencia y se mapea directamente
 * a documentos almacenados en la colección de viajes en MongoDB.
 */
public class Viaje {

    /** Identificador único del viaje. */
    private ObjectId id;

    /** Precio del viaje. */
    private Double precio;

    /** Ciudad de origen del viaje. */
    private String origen;

    /** Ciudad de destino del viaje. */
    private String destino;

    /** Camión asignado para realizar el viaje. */
    private CamionDTO camion;

    /** Fecha y hora de salida del viaje. */
    @BsonProperty("fechaHora")
    private Date fecha;

    /**
     * Constructor vacío necesario para serialización y frameworks de persistencia.
     */
    public Viaje() {
    }

    /**
     * Constructor completo que inicializa todos los atributos del viaje.
     *
     * @param id identificador del viaje.
     * @param precio precio del boleto.
     * @param origen ciudad de origen.
     * @param destino ciudad de destino.
     * @param camion camión asignado al viaje.
     * @param fecha fecha y hora del viaje.
     */
    public Viaje(ObjectId id, Double precio, String origen, String destino, CamionDTO camion, Date fecha) {
        this.id = id;
        this.precio = precio;
        this.origen = origen;
        this.destino = destino;
        this.camion = camion;
        this.fecha = fecha;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public CamionDTO getCamion() {
        return camion;
    }

    public void setCamion(CamionDTO camion) {
        this.camion = camion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Devuelve una representación en cadena del objeto Viaje.
     *
     * @return cadena que representa el viaje con todos sus atributos.
     */
    @Override
    public String toString() {
        return "Viaje{" +
               "id=" + id +
               ", precio=" + precio +
               ", origen='" + origen + '\'' +
               ", destino='" + destino + '\'' +
               ", camion=" + camion +
               ", fecha=" + fecha +
               '}';
    }
}
