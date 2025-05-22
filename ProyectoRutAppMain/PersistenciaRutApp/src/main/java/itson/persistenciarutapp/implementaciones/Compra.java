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
 * @author pedro
 */
public class Compra {
    private ObjectId id;
    
    private ObjectId viaje;

    private List<AsientoBoleto> asientosComprados;

    private ObjectId usuario;

    private Date fechaCompra;

    public Compra() {
    }

    public Compra(ObjectId id, ObjectId viaje, List<AsientoBoleto> asientosComprados, ObjectId usuario, Date fechaCompra) {
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

    public ObjectId getViaje() {
        return viaje;
    }

    public void setViaje(ObjectId viaje) {
        this.viaje = viaje;
    }

    public List<AsientoBoleto> getAsientosComprados() {
        return asientosComprados;
    }

    public void setAsientosComprados(List<AsientoBoleto> asientosComprados) {
        this.asientosComprados = asientosComprados;
    }

    public ObjectId getUsuario() {
        return usuario;
    }

    public void setUsuario(ObjectId usuario) {
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
        return "Compra{" + "id=" + id + ", viaje=" + viaje + ", asientosComprados=" + asientosComprados + ", usuario=" + usuario + ", fechaCompra=" + fechaCompra + '}';
    }



}
