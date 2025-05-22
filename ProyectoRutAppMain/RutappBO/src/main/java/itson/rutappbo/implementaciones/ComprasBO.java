/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.rutappbo.implementaciones;

import enumm.estadoAsiento;
import itson.persistenciarutapp.implementaciones.AsientoBoleto;
import itson.persistenciarutapp.implementaciones.Compra;
import itson.persistenciarutapp.implementaciones.ComprasDAO;
import itson.persistenciarutapp.implementaciones.Viaje;
import itson.rutappbo.IComprasBO;
import itson.rutappdto.AsientoBoletoDTO;
import itson.rutappdto.UsuarioDTO;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;

/**
 *
 * @author pedro
 */
public class ComprasBO implements IComprasBO {

    @Override
    public void agregarCompra(UsuarioDTO usuarioDTO, Viaje viaje, List<AsientoBoletoDTO> asientosDTO) {
        // Convertir DTOs a entidad AsientoBoleto
        List<AsientoBoleto> asientos = asientosDTO.stream()
                .map(dto -> {
                    int numero = 0;
                    if (dto.getAsiento() != null && dto.getAsiento().getNumero() != null) {
                        numero = Integer.parseInt(dto.getAsiento().getNumero());
                    }
                    return new AsientoBoleto(numero, estadoAsiento.OCUPADO, dto.getNombreAsiento());
                })
                .collect(Collectors.toList());

        // Construir Compra
        Compra compra = new Compra();
        compra.setId(new ObjectId());
        compra.setUsuario(new ObjectId(usuarioDTO.getId()));  // ✅ referencia al usuario por ObjectId
        compra.setViaje(viaje.getId());         // ✅ referencia al viaje por ObjectId
        compra.setAsientosComprados(asientos);
        compra.setFechaCompra(new Date());

        // Persistir
        new ComprasDAO().agregarCompras(compra);
    }

}
