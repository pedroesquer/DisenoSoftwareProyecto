package itson.rutappdto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author pedro
 */
public class ViajeDTO implements Serializable{

    private Double precio;
    private String origen;
    private String destino;
    private CamionDTO camion;
    private Date fecha;
    private String idViaje;

    public ViajeDTO(Double precio, String origen, String destino, CamionDTO camion, Date fecha) {
        this.precio = precio;
        this.origen = origen;
        this.destino = destino;
        this.camion = camion;
        this.fecha = fecha;
    }

    public ViajeDTO() {
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

    public String getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(String idViaje) {
        this.idViaje = idViaje;
    }

}
