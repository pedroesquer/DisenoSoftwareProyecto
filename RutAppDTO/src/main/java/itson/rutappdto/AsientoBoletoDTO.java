package itson.rutappdto;

import java.io.Serializable;

/**
 *
 * @author BusSoft®
 */
public class AsientoBoletoDTO implements Serializable {

    public AsientoDTO asiento;

    public BoletoDTO boleto;

    public String nombreAsiento;

    public Double precioUnitario;

    public AsientoBoletoDTO() {
    }

    public AsientoBoletoDTO(AsientoDTO asiento, BoletoDTO boleto, String nombreAsiento, Double precioUnitario) {
        this.asiento = asiento;
        this.boleto = boleto;
        this.nombreAsiento = nombreAsiento;
        this.precioUnitario = precioUnitario;
    }

    public AsientoBoletoDTO(AsientoDTO asiento, String nombreAsiento) {
        this.asiento = asiento;
        this.nombreAsiento = nombreAsiento;
    }

    public AsientoDTO getAsiento() {
        return asiento;
    }

    public void setAsiento(AsientoDTO asiento) {
        this.asiento = asiento;
    }

    public BoletoDTO getBoleto() {
        return boleto;
    }

    public void setBoleto(BoletoDTO boleto) {
        this.boleto = boleto;
    }

    public String getNombreAsiento() {
        return nombreAsiento;
    }

    public void setNombreAsiento(String nombreAsiento) {
        this.nombreAsiento = nombreAsiento;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

}
