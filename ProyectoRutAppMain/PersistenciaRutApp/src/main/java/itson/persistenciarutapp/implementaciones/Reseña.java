/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.persistenciarutapp.implementaciones;

import java.util.Date;
import org.bson.types.ObjectId;

/**
 *
 * @author multaslokas33
 */
public class Reseña {
    private ObjectId id;
    private ObjectId usuario;
    private ObjectId camion;
    private Date fecha;
    private double calificacion;
    private String comentario;

    public Reseña() {
    }
    
    public Reseña(ObjectId id, ObjectId usuario, ObjectId camion, Date fecha, double calificacion, String comentario) {
        this.id = id;
        this.usuario = usuario;
        this.camion = camion;
        this.fecha = fecha;
        this.calificacion = calificacion;
        this.comentario = comentario;
    }

    public Reseña(ObjectId usuario, ObjectId camion, Date fecha, double calificacion, String comentario) {
        this.usuario = usuario;
        this.camion = camion;
        this.fecha = fecha;
        this.calificacion = calificacion;
        this.comentario = comentario;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getUsuario() {
        return usuario;
    }

    public void setUsuario(ObjectId usuario) {
        this.usuario = usuario;
    }

    public ObjectId getCamion() {
        return camion;
    }

    public void setCamion(ObjectId camion) {
        this.camion = camion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "Rese\u00f1a{" + "id=" + id + ", usuario=" + usuario + ", camion=" + camion + ", fecha=" + fecha + ", calificacion=" + calificacion + ", comentario=" + comentario + '}';
    }
    
}
