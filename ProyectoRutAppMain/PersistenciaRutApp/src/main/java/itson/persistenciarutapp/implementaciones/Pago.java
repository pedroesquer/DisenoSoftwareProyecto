package itson.persistenciarutapp.implementaciones;

import enumm.MetodoPago;
import java.util.Date;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

/**
 *
 * @author pedro
 */
public class Pago {
    private ObjectId id;
    @BsonProperty("metodoPago")
    private String metodoPago;
    private Double cantidad;
    private Date fechaHora;

    public Pago() {
    }

    public Pago(ObjectId id, String metodoPago, Double cantidad, Date fechaHora) {
        this.id = id;
        this.metodoPago = metodoPago;
        this.cantidad = cantidad;
        this.fechaHora = fechaHora;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {
        return "Pago{" + "id=" + id + ", metodoPago=" + metodoPago + ", cantidad=" + cantidad + ", fechaHora=" + fechaHora + '}';
    }
    
    
    
    
}
