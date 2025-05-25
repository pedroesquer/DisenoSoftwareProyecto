package itson.persistenciarutapp.entidades;

import java.util.Date;
import java.util.List;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;

/**
 * Representa una compra realizada por un usuario, incluyendo el viaje
 * seleccionado, los asientos comprados, la fecha de compra y la referencia al
 * usuario.
 *
 * Esta clase es utilizada en la capa de persistencia para mapear documentos de
 * compras en la base de datos MongoDB.
 */
public class Compra {

    /**
     * Identificador único de la compra.
     */
    private ObjectId id;

    /**
     * Referencia al ID del viaje comprado.
     */
    private ObjectId viaje;

    /**
     * Lista de asientos comprados en esta transacción.
     */
    private List<AsientoBoleto> asientosComprados;

    /**
     * Referencia al ID del usuario que realizó la compra.
     */
    private ObjectId usuario;

    /**
     * Fecha y hora en que se realizó la compra.
     */
    private Date fechaCompra;

    /**
     * Constructor vacío requerido para serialización y frameworks de
     * persistencia.
     */
    public Compra() {
    }

    /**
     * Constructor que inicializa todos los atributos de la clase Compra.
     *
     * @param id identificador de la compra.
     * @param viaje ID del viaje comprado.
     * @param asientosComprados lista de asientos comprados.
     * @param usuario ID del usuario que realiza la compra.
     * @param fechaCompra fecha y hora de la compra.
     */
    public Compra(ObjectId id, ObjectId viaje, List<AsientoBoleto> asientosComprados, ObjectId usuario, Date fechaCompra) {
        this.id = id;
        this.viaje = viaje;
        this.asientosComprados = asientosComprados;
        this.usuario = usuario;
        this.fechaCompra = fechaCompra;
    }

    /**
     * Obtiene el ID de la compra.
     *
     * @return ID de la compra.
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Establece el ID de la compra.
     *
     * @param id nuevo ID de la compra.
     */
    public void setId(ObjectId id) {
        this.id = id;
    }

    /**
     * Obtiene el ID del viaje relacionado con esta compra.
     *
     * @return ID del viaje.
     */
    public ObjectId getViaje() {
        return viaje;
    }

    /**
     * Establece el ID del viaje para esta compra.
     *
     * @param viaje nuevo ID del viaje.
     */
    public void setViaje(ObjectId viaje) {
        this.viaje = viaje;
    }

    /**
     * Obtiene la lista de asientos comprados.
     *
     * @return lista de {@link AsientoBoleto}.
     */
    public List<AsientoBoleto> getAsientosComprados() {
        return asientosComprados;
    }

    /**
     * Establece la lista de asientos comprados.
     *
     * @param asientosComprados nueva lista de asientos.
     */
    public void setAsientosComprados(List<AsientoBoleto> asientosComprados) {
        this.asientosComprados = asientosComprados;
    }

    /**
     * Obtiene el ID del usuario que realizó la compra.
     *
     * @return ID del usuario.
     */
    public ObjectId getUsuario() {
        return usuario;
    }

    /**
     * Establece el ID del usuario que realiza la compra.
     *
     * @param usuario nuevo ID del usuario.
     */
    public void setUsuario(ObjectId usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene la fecha en que se realizó la compra.
     *
     * @return fecha de compra.
     */
    public Date getFechaCompra() {
        return fechaCompra;
    }

    /**
     * Establece la fecha de la compra.
     *
     * @param fechaCompra nueva fecha de compra.
     */
    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
    
    
    @BsonIgnore
    public String obtenerIdComoString() {
        return id != null ? id.toHexString() : null;
    }

    @BsonIgnore
    public void asignarIdDesdeString(String id) {
        this.id = (id != null && !id.isBlank()) ? new ObjectId(id) : null;
    }

    @BsonIgnore
    public String obtenerUsuarioComoString() {
        return usuario != null ? usuario.toHexString() : null;
    }

    @BsonIgnore
    public void asignarUsuarioDesdeString(String usuarioId) {
        this.usuario = (usuarioId != null && !usuarioId.isBlank()) ? new ObjectId(usuarioId) : null;
    }

    @BsonIgnore
    public String obtenerViajeComoString() {
        return viaje != null ? viaje.toHexString() : null;
    }

    @BsonIgnore
    public void asignarViajeDesdeString(String viajeId) {
        this.viaje = (viajeId != null && !viajeId.isBlank()) ? new ObjectId(viajeId) : null;
    }

    /**
     * Devuelve una representación textual de la compra.
     *
     * @return cadena con los datos de la compra.
     */
    @Override
    public String toString() {
        return "Compra{"
                + "id=" + id
                + ", viaje=" + viaje
                + ", asientosComprados=" + asientosComprados
                + ", usuario=" + usuario
                + ", fechaCompra=" + fechaCompra
                + '}';
    }
}
