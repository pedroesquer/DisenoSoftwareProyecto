/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.persistenciarutapp.implementaciones;

import enumm.estadoAsiento;

/**
 *
 * @author chris
 */
public class AsientoBoleto {

    private int numero;
    private estadoAsiento estado; //
    private String nombrePasajero;

    public AsientoBoleto() {
    }

    public AsientoBoleto(int numero, estadoAsiento estado, String nombrePasajero) {
        this.numero = numero;
        this.estado = estado;
        this.nombrePasajero = nombrePasajero;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public estadoAsiento getEstado() {
        return estado;
    }

    public void setEstado(estadoAsiento estado) {
        this.estado = estado;
    }

    public String getNombrePasajero() {
        return nombrePasajero;
    }

    public void setNombrePasajero(String nombrePasajero) {
        this.nombrePasajero = nombrePasajero;
    }
    
    
    
}
