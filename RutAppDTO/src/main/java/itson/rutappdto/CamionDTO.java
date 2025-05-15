package itson.rutappdto;

import itson.rutappdto.AsientoDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author chris
 */
public class CamionDTO implements Serializable{
    private Long idCamion;
    private String numeroCamion;
    private List<AsientoDTO> listaAsiento;

    public CamionDTO(Long idCamion, String matricula, List<AsientoDTO> listaAsiento) {
        this.idCamion = idCamion;
        this.numeroCamion = matricula;
        this.listaAsiento = listaAsiento;
    }

    public String getNumeroCamion() {
        return numeroCamion;
    }

    public List<AsientoDTO> getListaAsiento() {
        return listaAsiento;
    }

    public Long getIdCamion() {
        return idCamion;
    }
    
    

}
