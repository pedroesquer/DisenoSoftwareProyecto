package itson.rutappdto;

import java.io.Serializable;

/**
 *
 * @author pedro
 */
public class UsuarioDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String nombre;
    private String numeroTelefonico;
    private String contrasena;
    private Double saldoMonedero;

    public UsuarioDTO() {
    }

    public UsuarioDTO(String id, String numeroTelefonico, String contrasena) {
        this.id = id;
        this.numeroTelefonico = numeroTelefonico;
        this.contrasena = contrasena;
    }

    
    public UsuarioDTO(String numeroTelefonico, String contrasena) {
        this.numeroTelefonico = numeroTelefonico;
        this.contrasena = contrasena;
    }

    
    public UsuarioDTO(String nombre, String numeroTelefonico, String contrasena, Double saldoMonedero) {
        this.nombre = nombre;
        this.numeroTelefonico = numeroTelefonico;
        this.contrasena = contrasena;
        this.saldoMonedero = saldoMonedero;
    }

    public UsuarioDTO(String numeroTelefonico, String contrasena, Double saldoMonedero) {
        this.numeroTelefonico = numeroTelefonico;
        this.contrasena = contrasena;
        this.saldoMonedero = saldoMonedero;
    }

    public UsuarioDTO(String id, String nombre, String numeroTelefonico, String contrasena, Double saldoMonedero) {
        this.id = id;
        this.nombre = nombre;
        this.numeroTelefonico = numeroTelefonico;
        this.contrasena = contrasena;
        this.saldoMonedero = saldoMonedero;
    }
    
    

    public UsuarioDTO(String nombre) {
        this.nombre = nombre;
        this.saldoMonedero = saldoMonedero;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    

    public Double getSaldoMonedero() {
        return saldoMonedero;
    }

    public void setSaldoMonedero(Double saldoMonedero) {
        this.saldoMonedero = saldoMonedero;
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public void setNumeroTelefonico(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" + "nombre=" + nombre + ", numeroTelefonico=" + numeroTelefonico + ", contrasena=" + contrasena + ", saldoMonedero=" + saldoMonedero + '}';
    }

}
