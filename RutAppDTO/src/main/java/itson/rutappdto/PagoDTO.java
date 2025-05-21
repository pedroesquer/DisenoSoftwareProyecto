/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.rutappdto;

import java.util.Date;

/**
 *
 * @author pedro
 */
public class PagoDTO {
    private Long idPago;
    private String metodoPago;
    private Double monto;
    private Date fecha;

    public PagoDTO() {
    }

    
    public PagoDTO(Long idPago, String metodoPago, Double monto, Date fecha) {
        this.idPago = idPago;
        this.metodoPago = metodoPago;
        this.monto = monto;
        this.fecha = fecha;
    }

    public Long getIdPago() {
        return idPago;
    }

    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "PagoDTO{" + "idPago=" + idPago + ", metodoPago=" + metodoPago + ", monto=" + monto + ", fecha=" + fecha + '}';
    }
    
    
}
