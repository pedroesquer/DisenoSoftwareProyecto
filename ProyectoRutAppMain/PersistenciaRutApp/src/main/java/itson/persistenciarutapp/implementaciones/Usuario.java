package itson.persistenciarutapp.implementaciones;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

/**
 * Representa un usuario del sistema. Incluye información básica como nombre,
 * número telefónico, contraseña y saldo en el monedero digital.
 *
 * Esta clase es utilizada para mapear documentos de usuarios desde y hacia
 * MongoDB.
 */
public class Usuario {

    /**
     * Identificador único del usuario en la base de datos.
     */
    private ObjectId id;

    /**
     * Número telefónico del usuario (actúa como identificador único al
     * registrarse).
     */
    @BsonProperty("numeroTelefonico")
    private String numeroTelefonico;

    /**
     * Nombre completo del usuario.
     */
    @BsonProperty("nombre")
    private String nombre;

    /**
     * Contraseña del usuario (debe ser almacenada de forma segura).
     */
    @BsonProperty("contrasenia")
    private String contrasenia;

    /**
     * Saldo disponible en el monedero del usuario.
     */
    @BsonProperty("saldoMonedero")
    private Double saldoMonedero;

    /**
     * Constructor vacío necesario para frameworks de persistencia.
     */
    public Usuario() {
    }

    /**
     * Constructor completo que inicializa todos los atributos.
     *
     * @param id ID del usuario.
     * @param numeroTelefonico número telefónico del usuario.
     * @param nombre nombre del usuario.
     * @param contrasenia contraseña del usuario.
     * @param saldoMonedero saldo del monedero del usuario.
     */
    public Usuario(ObjectId id, String numeroTelefonico, String nombre, String contrasenia, Double saldoMonedero) {
        this.id = id;
        this.numeroTelefonico = numeroTelefonico;
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.saldoMonedero = saldoMonedero;
    }

    /**
     * Constructor para autenticación.
     *
     * @param numeroTelefonico número telefónico del usuario.
     * @param contrasenia contraseña del usuario.
     */
    public Usuario(String numeroTelefonico, String contrasenia) {
        this.numeroTelefonico = numeroTelefonico;
        this.contrasenia = contrasenia;
    }

    /**
     * Constructor utilizado para registro de usuario con saldo por defecto en
     * 0.
     *
     * @param numeroTelefonico número telefónico.
     * @param nombre nombre del usuario.
     * @param contrasenia contraseña.
     */
    public Usuario(String numeroTelefonico, String nombre, String contrasenia) {
        this.numeroTelefonico = numeroTelefonico;
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.saldoMonedero = 0d;
    }

    /**
     * Constructor alternativo con todos los campos excepto ID.
     *
     * @param numeroTelefonico número del usuario.
     * @param nombre nombre del usuario.
     * @param contrasenia contraseña.
     * @param saldoMonedero saldo del monedero.
     */
    public Usuario(String numeroTelefonico, String nombre, String contrasenia, Double saldoMonedero) {
        this.numeroTelefonico = numeroTelefonico;
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.saldoMonedero = saldoMonedero;
    }

    /**
     * Constructor utilizado para cargar saldo al monedero.
     *
     * @param numeroTelefonico número del usuario.
     * @param contrasenia contraseña del usuario.
     * @param saldoMonedero saldo a establecer.
     */
    public Usuario(String numeroTelefonico, String contrasenia, Double saldoMonedero) {
        this.numeroTelefonico = numeroTelefonico;
        this.contrasenia = contrasenia;
        this.saldoMonedero = saldoMonedero;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public void setNumeroTelefonico(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    /**
     * Obtiene el ID como cadena hexadecimal.
     *
     * @return ID como String, o null si no está inicializado.
     */
    public String getIdAsString() {
        return id != null ? id.toHexString() : null;
    }

    /**
     * Establece el ID a partir de una cadena.
     *
     * @param idStr ID en formato String.
     */
    public void setIdFromString(String idStr) {
        this.id = (idStr != null && !idStr.isBlank()) ? new ObjectId(idStr) : null;
    }

    /**
     * Devuelve una representación textual del usuario, sin incluir el ID.
     *
     * @return cadena con los datos visibles del usuario.
     */
    @Override
    public String toString() {
        return "Usuario{"
                + "numeroTelefonico=" + numeroTelefonico
                + ", contrasenia=" + contrasenia
                + ", saldoMonedero=" + saldoMonedero
                + '}';
    }
}
