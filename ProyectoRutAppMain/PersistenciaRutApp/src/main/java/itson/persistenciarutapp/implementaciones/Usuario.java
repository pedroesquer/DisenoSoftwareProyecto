package itson.persistenciarutapp.implementaciones;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;


public class Usuario {

    private String numeroTelefonico;

    @BsonProperty("contrasenia")  // Mapea la propiedad 'contrasenia' en MongoDB
    private String contrasenia;
    
    private Double saldo;

    // Constructor por defecto
    public Usuario() {
    }

    // Constructor con parámetros

    public Usuario(String numeroTelefonico, String contrasenia) {
        this.numeroTelefonico = numeroTelefonico;
        this.contrasenia = contrasenia;
        this.saldo = 0d;
    }
    

    // Getters y Setters
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

    public Double getSaldo() {
        return saldo;
    }

    public Usuario(Double saldo) {
        this.saldo = saldo;
    }
    
    

    @Override
    public String toString() {
        return "Usuario{" + "numeroTelefonico=" + numeroTelefonico + ", contrasenia=" + contrasenia + ", saldo=" + saldo + '}';
    }
    
    
}

