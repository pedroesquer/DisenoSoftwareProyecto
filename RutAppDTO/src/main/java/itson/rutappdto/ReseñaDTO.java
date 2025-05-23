/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.rutappdto;

import java.util.Date;

/**
 *
 * @author multaslokas33
 */
public class ReseñaDTO {
    private String nombreUsuario;     
    private String numeroCamion;
    private String comentario;
    private double calificacion;
    private Date fecha;

    public ReseñaDTO() {
    }

    public ReseñaDTO(String nombreUsuario, String numeroCamion, String comentario, double calificacion, Date fecha) {
        this.nombreUsuario = nombreUsuario;
        this.numeroCamion = numeroCamion;
        this.comentario = comentario;
        this.calificacion = calificacion;
        this.fecha = fecha;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNumeroCamion() {
        return numeroCamion;
    }

    public void setNumeroCamion(String numeroCamion) {
        this.numeroCamion = numeroCamion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
}
