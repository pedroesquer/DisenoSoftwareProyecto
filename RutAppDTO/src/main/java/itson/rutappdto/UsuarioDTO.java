package itson.rutappdto;

import java.io.Serializable;

/**
 *
 * @author pedro
 */
public class UsuarioDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private String numeroTelefonico;
    private String contrasena;
    private Double saldoMonedero;

    public UsuarioDTO() {
    }

    public UsuarioDTO(String nombre, String numeroTelefonico, String contrasena, Double saldoMonedero) {
        this.nombre = nombre;
        this.numeroTelefonico = numeroTelefonico;
        this.contrasena = contrasena;
        this.saldoMonedero = 0d;
    }

    public UsuarioDTO(String numeroTelefonico, String contrasena, Double saldoMonedero) {
        this.numeroTelefonico = numeroTelefonico;
        this.contrasena = contrasena;
        this.saldoMonedero = 0d;
    }
    
    

    public UsuarioDTO(String nombre) {
        this.nombre = nombre;
        this.saldoMonedero = 0.0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    

    // Getters y setters
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
