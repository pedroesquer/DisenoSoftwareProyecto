package itson.rutappdto;

import java.io.Serializable;

/**
 *
 * @author pedro
 */
public class UsuarioDTO implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private String nombre;
    private Double saldoMonedero;
    public UsuarioDTO(String nombre) {
        this.nombre = nombre;
        this.saldoMonedero = 0.0;
    }

    public String getNombre() {
        return nombre;
    }

    public Double getSaldoMonedero() {
        return saldoMonedero;
    }

    public void setSaldoMonedero(Double saldoMonedero) {
        this.saldoMonedero = saldoMonedero;
    }
    
    
    
    

}
