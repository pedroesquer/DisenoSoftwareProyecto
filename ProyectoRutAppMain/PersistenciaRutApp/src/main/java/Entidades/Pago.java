package Entidades;

import enumm.MetodoPago;
import java.util.Date;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

/**
 * Representa un pago realizado por un usuario en el sistema.
 * Contiene información sobre el método de pago, la cantidad pagada y la fecha del pago.
 * Esta clase es usada para persistir información de pagos en la base de datos MongoDB.
 */
public class Pago {

    /** Identificador único del pago. */
    private ObjectId id;

    /** Método de pago utilizado (por ejemplo: TARJETA, MONEDERO, etc.). */
    @BsonProperty("metodoPago")
    private String metodoPago;

    /** Cantidad pagada. */
    private Double cantidad;

    /** Fecha y hora en que se realizó el pago. */
    private Date fechaHora;

    /**
     * Constructor vacío requerido para la serialización y deserialización.
     */
    public Pago() {
    }

    /**
     * Constructor que inicializa todos los campos del objeto Pago.
     *
     * @param id identificador único del pago.
     * @param metodoPago método utilizado para realizar el pago.
     * @param cantidad monto total pagado.
     * @param fechaHora fecha y hora del pago.
     */
    public Pago(ObjectId id, String metodoPago, Double cantidad, Date fechaHora) {
        this.id = id;
        this.metodoPago = metodoPago;
        this.cantidad = cantidad;
        this.fechaHora = fechaHora;
    }

    /**
     * Obtiene el ID del pago.
     *
     * @return el identificador del pago.
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Establece el ID del pago.
     *
     * @param id nuevo identificador del pago.
     */
    public void setId(ObjectId id) {
        this.id = id;
    }

    /**
     * Obtiene el método de pago utilizado.
     *
     * @return el método de pago.
     */
    public String getMetodoPago() {
        return metodoPago;
    }

    /**
     * Establece el método de pago utilizado.
     *
     * @param metodoPago nuevo método de pago.
     */
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    /**
     * Obtiene la cantidad pagada.
     *
     * @return la cantidad pagada.
     */
    public Double getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad pagada.
     *
     * @param cantidad nuevo monto pagado.
     */
    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene la fecha y hora del pago.
     *
     * @return fecha y hora del pago.
     */
    public Date getFechaHora() {
        return fechaHora;
    }

    /**
     * Establece la fecha y hora del pago.
     *
     * @param fechaHora nueva fecha y hora del pago.
     */
    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     * Devuelve una representación textual del objeto Pago.
     *
     * @return una cadena con los datos del pago.
     */
    @Override
    public String toString() {
        return "Pago{" +
               "id=" + id +
               ", metodoPago=" + metodoPago +
               ", cantidad=" + cantidad +
               ", fechaHora=" + fechaHora +
               '}';
    }
}
