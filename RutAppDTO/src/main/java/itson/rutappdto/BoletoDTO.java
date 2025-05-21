package itson.rutappdto;

import itson.rutappdto.AsientoDTO;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 *
 * @author pedro
 */
public class BoletoDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String origen;
    private String destino;
    private String hrSalida;
    private UsuarioDTO usuario;
    private Double precio;
    private String duracion;
    private CamionDTO camion;
    private List<AsientoBoletoDTO> listaAsiento;
    private Date fecha;
    private ViajeDTO viaje;



    public BoletoDTO() {
    }

    public BoletoDTO(String origen, String destino, String hrSalida, UsuarioDTO usuario, Double precio, String duracion, CamionDTO camion, List<AsientoBoletoDTO> listaAsiento) {
        this.origen = origen;
        this.destino = destino;
        this.hrSalida = hrSalida;
        this.usuario = usuario;
        this.precio = precio;
        this.duracion = duracion;
        this.camion = camion;
        this.listaAsiento = listaAsiento;
    }

    public BoletoDTO(String origen, String destino, Double precio, String duracion, CamionDTO camion, List<AsientoBoletoDTO> listaAsiento) {
        this.origen = origen;
        this.destino = destino;
        this.precio = precio;
        this.duracion = duracion;
        this.camion = camion;
        this.listaAsiento = listaAsiento;
    }

    public BoletoDTO(UsuarioDTO usuario, Double precio, List<AsientoBoletoDTO> listaAsiento, ViajeDTO viaje) {
        this.usuario = usuario;
        this.precio = precio;
        this.listaAsiento = listaAsiento;
        this.viaje = viaje;
    }
    

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getHrSalida() {
        return hrSalida;
    }

    public void setHrSalida(String hrSalida) {
        this.hrSalida = hrSalida;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public CamionDTO getCamion() {
        return camion;
    }

    public void setCamion(CamionDTO camion) {
        this.camion = camion;
    }

    public List<AsientoBoletoDTO> getListaAsiento() {
        return listaAsiento;
    }

    public void setListaAsiento(List<AsientoBoletoDTO> listaAsiento) {
        this.listaAsiento = listaAsiento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public ViajeDTO getViaje() {
        return viaje;
    }

    public void setViaje(ViajeDTO viaje) {
        this.viaje = viaje;
    }

    
    
    
}
