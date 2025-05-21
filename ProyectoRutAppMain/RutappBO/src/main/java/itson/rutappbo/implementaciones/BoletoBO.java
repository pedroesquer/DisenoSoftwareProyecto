/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.rutappbo.implementaciones;

import enumm.estadoAsiento;
import itson.persistenciarutapp.implementaciones.AsientoBoleto;
import itson.persistenciarutapp.implementaciones.Boleto;
import itson.persistenciarutapp.implementaciones.BoletosDAO;
import itson.persistenciarutapp.implementaciones.Usuario;
import itson.persistenciarutapp.implementaciones.Viaje;
import itson.rutappbo.IBoletoBO;
import itson.rutappdto.AsientoBoletoDTO;
import itson.rutappdto.CamionDTO;
import itson.rutappdto.UsuarioDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;

/**
 *
 * @author chris
 */
public class BoletoBO implements IBoletoBO {

    @Override

    public void guardarBoletoDesdeContexto(UsuarioDTO usuarioDTO, Viaje viaje, List<AsientoBoletoDTO> asientosDTO) {
        List<AsientoBoleto> asientos = asientosDTO.stream()
                .map(dto -> {
                    int numero = 0;
                    if (dto.getAsiento() != null && dto.getAsiento().getNumero() != null) {
                        numero = Integer.parseInt(dto.getAsiento().getNumero());
                    }
                    return new AsientoBoleto(numero, estadoAsiento.OCUPADO, dto.getNombreAsiento()); // ✅ usa getNombre(), no getNombreAsiento()
                })
                .collect(Collectors.toList());

        Usuario usuario = new Usuario();
        usuario.setNumeroTelefonico(usuarioDTO.getNumeroTelefonico());
        
        CamionDTO camionSoloNumero = new CamionDTO();
        camionSoloNumero.setNumeroCamion(viaje.getCamion().getNumeroCamion());

        Viaje viajeLigero = new Viaje(
                viaje.getPrecio(),
                viaje.getOrigen(),
                viaje.getDestino(),
                camionSoloNumero,
                viaje.getFecha(),
                viaje.getIdViaje()
        );

        Boleto boleto = new Boleto();
        boleto.setId(new ObjectId());
        boleto.setUsuario(usuario);                    
        boleto.setAsientosComprados(asientos);         
        boleto.setFechaCompra(new Date());             
        boleto.setViaje(viajeLigero);                  

        new BoletosDAO().agregarBoleto(boleto);
    }

}
