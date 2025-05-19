/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.persistenciarutapp.implementaciones;

import java.util.List;
import org.bson.codecs.pojo.annotations.BsonProperty;

/**
 *
 * @author chris
 */
public class Camion {

    @BsonProperty("numeroDeCamion")
    private String numeroDeCamion;

    @BsonProperty("asientos")
    private List<Asiento> asientos;

    public Camion() {}

    public Camion(String numeroDeCamion, List<Asiento> asientos) {
        this.numeroDeCamion = numeroDeCamion;
        this.asientos = asientos;
    }

    public String getNumeroDeCamion() {
        return numeroDeCamion;
    }

    public void setNumeroDeCamion(String numeroDeCamion) {
        this.numeroDeCamion = numeroDeCamion;
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
    }

    @Override
    public String toString() {
        return "Camion{" + "numeroDeCamion=" + numeroDeCamion + ", asientos=" + asientos + '}';
    }
}
