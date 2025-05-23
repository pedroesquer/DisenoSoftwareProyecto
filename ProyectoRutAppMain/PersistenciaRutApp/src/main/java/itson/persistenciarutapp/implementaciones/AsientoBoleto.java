package itson.persistenciarutapp.implementaciones;

import enumm.estadoAsiento;

/**
 * Representa la información de un asiento comprado dentro de un boleto.
 * Incluye el número de asiento, el estado actual del asiento y el nombre del pasajero.
 * Esta clase se utiliza para persistir los datos del asiento específico reservado por el usuario.
 */
public class AsientoBoleto {

    /** Número identificador del asiento dentro del camión. */
    private int numero;

    /** Estado actual del asiento (por ejemplo: OCUPADO, RESERVADO, DISPONIBLE). */
    private estadoAsiento estado;

    /** Nombre del pasajero que ha comprado el boleto para este asiento. */
    private String nombrePasajero;

    /**
     * Constructor vacío requerido para serialización o frameworks de persistencia.
     */
    public AsientoBoleto() {
    }

    /**
     * Constructor que inicializa un objeto AsientoBoleto con todos sus atributos.
     *
     * @param numero el número del asiento.
     * @param estado el estado del asiento.
     * @param nombrePasajero el nombre del pasajero asignado al asiento.
     */
    public AsientoBoleto(int numero, estadoAsiento estado, String nombrePasajero) {
        this.numero = numero;
        this.estado = estado;
        this.nombrePasajero = nombrePasajero;
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
     * Obtiene el estado del asiento.
     *
     * @return el estado del asiento.
     */
    public estadoAsiento getEstado() {
        return estado;
    }

    /**
     * Establece el estado del asiento.
     *
     * @param estado el nuevo estado del asiento.
     */
    public void setEstado(estadoAsiento estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el nombre del pasajero que ocupa este asiento.
     *
     * @return el nombre del pasajero.
     */
    public String getNombrePasajero() {
        return nombrePasajero;
    }

    /**
     * Establece el nombre del pasajero para este asiento.
     *
     * @param nombrePasajero el nuevo nombre del pasajero.
     */
    public void setNombrePasajero(String nombrePasajero) {
        this.nombrePasajero = nombrePasajero;
    }
}
