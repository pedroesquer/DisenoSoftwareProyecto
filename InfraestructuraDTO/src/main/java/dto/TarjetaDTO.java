/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author mmax2
 */
public class TarjetaDTO {
    private String numero;
    private String cvv;
    private double saldo;
    private String nombreTitular;

    public TarjetaDTO(String numero, String cvv, double saldo, String nombreTitular) {
        this.numero = numero;
        this.cvv = cvv;
        this.saldo = saldo;
        this.nombreTitular = nombreTitular;
    }

    public String getNumero() {
        return numero;
    }

    public String getCvv() {
        return cvv;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getNombreTitular() {
        return nombreTitular;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "TarjetaDTO{" +
               "numero='****" + (numero != null && numero.length() > 4 ? numero.substring(numero.length() - 4) : "****") + '\'' +
               ", saldo=" + saldo +
               ", nombreTitular='" + nombreTitular + '\'' +
               '}';
    }
}
