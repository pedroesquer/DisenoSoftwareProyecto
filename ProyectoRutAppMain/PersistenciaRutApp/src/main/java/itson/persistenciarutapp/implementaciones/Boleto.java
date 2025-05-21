/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.persistenciarutapp.implementaciones;

import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author chris
 */
public class Boleto {

    private ObjectId id;
    
    private Viaje viaje;

    private List<AsientoBoleto> asientosComprados;

    private Usuario usuario;

    private Date fechaCompra;

    public Boleto() {
    }

    public Boleto(ObjectId id, Viaje viaje, List<AsientoBoleto> asientosComprados, Usuario usuario, Date fechaCompra) {
        this.id = id;
        this.viaje = viaje;
        this.asientosComprados = asientosComprados;
        this.usuario = usuario;
        this.fechaCompra = fechaCompra;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }

    public List<AsientoBoleto> getAsientosComprados() {
        return asientosComprados;
    }

    public void setAsientosComprados(List<AsientoBoleto> asientosComprados) {
        this.asientosComprados = asientosComprados;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    @Override
    public String toString() {
        return "Boleto{" + "id=" + id + ", viaje=" + viaje + ", asientosComprados=" + asientosComprados + ", usuario=" + usuario + ", fechaCompra=" + fechaCompra + '}';
    }

    
}
