package itson.persistenciarutapp.implementaciones;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class Usuario {

    @BsonProperty("numeroTelefonico")
    private String numeroTelefonico;
    
    @BsonProperty("nombre")
    private String nombre;

    @BsonProperty("contrasenia")  // Mapea la propiedad 'contrasenia' en MongoDB
    private String contrasenia;

    @BsonProperty("saldo")
    private Double saldoMonedero;

    // Constructor por defecto
    public Usuario() {
    }

    // Constructor con parámetros
    public Usuario(String numeroTelefonico, String contrasenia) {
        this.numeroTelefonico = numeroTelefonico;
        this.contrasenia = contrasenia;
       
    }

    public Usuario(String numeroTelefonico, String nombre, String contrasenia) {
        this.numeroTelefonico = numeroTelefonico;
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.saldoMonedero = 0d;
    }

    public Usuario(String numeroTelefonico, String nombre, String contrasenia, Double saldoMonedero) {
        this.numeroTelefonico = numeroTelefonico;
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.saldoMonedero = saldoMonedero;
    }
    
    

    public Usuario(String numeroTelefonico, String contrasenia, Double saldoMonedero) {
        this.numeroTelefonico = numeroTelefonico;
        this.contrasenia = contrasenia;
        this.saldoMonedero = saldoMonedero;
      
    }
    
    public String getNombre() {
        return nombre;
    }

    // Getters y Setters
    public void setNombre(String nombre) {    
        this.nombre = nombre;
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public void setNumeroTelefonico(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Double getSaldoMonedero() {
        return saldoMonedero;
    }

    public void setSaldoMonedero(Double saldoMonedero) {
        this.saldoMonedero = saldoMonedero;
    }

    @Override
    public String toString() {
        return "Usuario{" + "numeroTelefonico=" + numeroTelefonico + ", contrasenia=" + contrasenia + ", saldoMonedero=" + saldoMonedero + '}';
    }


}
