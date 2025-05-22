package itson.rutappdto;

import enumm.estadoAsiento;
import java.io.Serializable;

/**
 *
 * @author pedro
 */
public class AsientoDTO implements Serializable {

    private Long idAsiento;
    private estadoAsiento estado;
    private String numero;

    public AsientoDTO() {
    }

    public AsientoDTO(Long idAsiento, estadoAsiento estado, String numero) {
        this.idAsiento = idAsiento;
        this.estado = estado;
        this.numero = numero;
    }

    public Long getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(Long idAsiento) {
        this.idAsiento = idAsiento;
    }

    public estadoAsiento getEstado() {
        return estado;
    }

    public void setEstado(estadoAsiento estado) {
        this.estado = estado;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

}
