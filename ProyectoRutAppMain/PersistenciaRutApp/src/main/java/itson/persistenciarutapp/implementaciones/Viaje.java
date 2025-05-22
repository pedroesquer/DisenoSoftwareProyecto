package itson.persistenciarutapp.implementaciones;

import itson.rutappdto.CamionDTO;
import java.util.Date;
import java.util.GregorianCalendar;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

/**
 *
 * @author pedro
 */
public class Viaje {
    private ObjectId id;
    private Double precio;
    private String origen;
    private String destino;
    private CamionDTO camion;
    
    @BsonProperty("fechaHora")
    private Date fecha;
    

    public Viaje() {
    }

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

    @Override
    public String toString() {
        return "Viaje{" + "id=" + id + ", precio=" + precio + ", origen=" + origen + ", destino=" + destino + ", camion=" + camion + ", fecha=" + fecha + '}';
    }



    
    
}
