package itson.persistenciarutapp.entidades;

/**
 * Representa un asiento dentro de un camión. Cada asiento tiene un número
 * identificador y un estado (por ejemplo, disponible, ocupado, reservado).
 * 
 * Esta clase forma parte de la capa de persistencia del sistema de venta de boletos.
 */
public class Asiento {

    /** Número identificador del asiento. */
    private int numero;

    /** Estado actual del asiento (por ejemplo: "OCUPADO", "DISPONIBLE"). */
    private String estado;

    /**
     * Constructor vacío requerido para ciertas operaciones como serialización o frameworks.
     */
    public Asiento() {
    }

    /**
     * Constructor que inicializa un asiento con su número y estado.
     *
     * @param numero el número del asiento.
     * @param estado el estado del asiento.
     */
    public Asiento(int numero, String estado) {
        this.numero = numero;
        this.estado = estado;
    }

    /**
     * Obtiene el número del asiento.
     *
     * @return el número del asiento.
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Establece el número del asiento.
     *
     * @param numero el nuevo número del asiento.
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * Obtiene el estado actual del asiento.
     *
     * @return el estado del asiento.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado del asiento.
     *
     * @param estado el nuevo estado del asiento.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Devuelve una representación en cadena del asiento.
     *
     * @return una cadena que representa el asiento.
     */
    @Override
    public String toString() {
        return "Asiento{" + "numero=" + numero + ", estado=" + estado + '}';
    }
}
