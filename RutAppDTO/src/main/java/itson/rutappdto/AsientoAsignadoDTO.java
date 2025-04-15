/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.rutappdto;

/**
 *
 * @author chris
 */
public class AsientoAsignadoDTO {

    private String numeroAsiento;
    private String nombrePasajero;

    public AsientoAsignadoDTO(String numeroAsiento, String nombrePasajero) {
        this.numeroAsiento = numeroAsiento;
        this.nombrePasajero = nombrePasajero;
    }

    public String getNumeroAsiento() {
        return numeroAsiento;
    }

    public void setNumeroAsiento(String numeroAsiento) {
        this.numeroAsiento = numeroAsiento;
    }

    public String getNombrePasajero() {
        return nombrePasajero;
    }

    public void setNombrePasajero(String nombrePasajero) {
        this.nombrePasajero = nombrePasajero;
    }

    @Override
    public String toString() {
        return numeroAsiento + " - " + nombrePasajero;
    }
}
