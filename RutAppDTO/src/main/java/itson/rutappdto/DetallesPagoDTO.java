package itson.rutappdto;

import itson.rutappdto.BoletoDTO;
import java.io.Serializable;

/**
 *
 * @author pedro
 */
public class DetallesPagoDTO implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private Long idPago;
    private String metodoPago;
    private Double monto;
    private Integer terminacionTarjeta;
    private BoletoDTO boleto;
    private TarjetaCreditoDTO detallesTarjeta;

    public DetallesPagoDTO() {
    }

    public DetallesPagoDTO(Long idPago, String metodoPago, Double monto, Integer terminacionTarjeta, BoletoDTO boleto, TarjetaCreditoDTO detallesTarjeta) {
        this.idPago = idPago;
        this.metodoPago = metodoPago;
        this.monto = monto;
        this.terminacionTarjeta = terminacionTarjeta;
        this.boleto = boleto;
        this.detallesTarjeta = detallesTarjeta;
    }

    public DetallesPagoDTO(String metodoPago, Double monto, BoletoDTO boleto) {
        this.metodoPago = metodoPago;
        this.monto = monto;
        this.boleto = boleto;
        this.detallesTarjeta = null; // No hay detalles de tarjeta cuando es pago con monedero
    }

    public DetallesPagoDTO(String metodoPago, Double monto, BoletoDTO boleto, TarjetaCreditoDTO detallesTarjeta) {
        this.metodoPago = metodoPago;
        this.monto = monto;
        this.boleto = boleto;
        this.detallesTarjeta = detallesTarjeta;
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

    public Integer getTerminacionTarjeta() {
        return terminacionTarjeta;
    }

    public void setTerminacionTarjeta(Integer terminacionTarjeta) {
        this.terminacionTarjeta = terminacionTarjeta;
    }

    public BoletoDTO getBoleto() {
        return boleto;
    }

    public void setBoleto(BoletoDTO boleto) {
        this.boleto = boleto;
    }

    public TarjetaCreditoDTO getDetallesTarjeta() {
        return detallesTarjeta;
    }

    public void setDetallesTarjeta(TarjetaCreditoDTO detallesTarjeta) {
        this.detallesTarjeta = detallesTarjeta;
    }

    @Override
    public String toString() {
        return "DetallesPagoDTO{" + "idPago=" + idPago + ", metodoPago=" + metodoPago + ", monto=" + monto + ", terminacionTarjeta=" + terminacionTarjeta + ", boleto=" + boleto + ", detallesTarjeta=" + detallesTarjeta + '}';
    }

}
