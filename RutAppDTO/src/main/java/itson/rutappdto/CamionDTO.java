package itson.rutappdto;

import itson.rutappdto.AsientoDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author chris
 */
public class CamionDTO implements Serializable {

    private String id;
    private String numeroCamion;
    private List<AsientoDTO> listaAsiento;

    public CamionDTO() {
    }

    public CamionDTO(String numeroCamion, List<AsientoDTO> listaAsiento) {
        this.numeroCamion = numeroCamion;
        this.listaAsiento = listaAsiento;
    }

    public CamionDTO(String id, String numeroCamion, List<AsientoDTO> listaAsiento) {
        this.id = id;
        this.numeroCamion = numeroCamion;
        this.listaAsiento = listaAsiento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumeroCamion() {
        return numeroCamion;
    }

    public List<AsientoDTO> getListaAsiento() {
        return listaAsiento;
    }

    public void setNumeroCamion(String numeroCamion) {
        this.numeroCamion = numeroCamion;
    }

    public void setListaAsiento(List<AsientoDTO> listaAsiento) {
        this.listaAsiento = listaAsiento;
    }

}
