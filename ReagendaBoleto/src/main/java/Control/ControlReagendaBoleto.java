/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;


import Ex.CompraBoletoException;
import Exception.ReagendaBoletoException;
import Fachada.ComprarBoleto;
import Interfaz.IComprarBoleto;
import itson.rutappdto.CompraDTO;
import itson.rutappdto.ViajeDTO;
import itson.rutappdto.AsientoBoletoDTO;
import itson.rutappdto.UsuarioDTO;
import itson.rutappbo.IComprasBO;
import itson.rutappbo.ICamionesBO;
import itson.rutappbo.IViajesBO;
import itson.rutappbo.implementaciones.ComprasBO;
import itson.rutappbo.implementaciones.CamionesBO;
import itson.rutappbo.implementaciones.ViajesBO;
import excepciones.NegocioException; 
import excepciones.PagoBoletoException;
import fachada.FCancelarCompra;
import interfaz.ICancelarCompra;
import itson.rutappdto.BoletoContext;
import itson.rutappdto.BoletoDTO;
import itson.rutappdto.DetallesPagoDTO;

import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;
import org.bson.types.ObjectId;

/**
 *
 * @author mmax2
 */
public class ControlReagendaBoleto {

   

    private final ICancelarCompra cancelarCompraFachada;
    private final IComprarBoleto comprarBoletoFachada;
    private final IComprasBO comprasBO;

    /**
     * Constructor que inicializa las fachadas de los subsistemas
     * de la aplicación principal que se utilizarán para el reagendamiento.
     */
    public ControlReagendaBoleto() {
        this.cancelarCompraFachada = new FCancelarCompra();
        this.comprarBoletoFachada = new ComprarBoleto();
        this.comprasBO = new ComprasBO();
    }

    /**
     * Procesa la solicitud de reagendar un boleto.
     * Primero, cancela la compra original utilizando el subsistema existente.
     * Luego, procede a realizar una nueva compra con los detalles del viaje actualizado,
     * utilizando el saldo del monedero.
     *
     * @param idCompraOriginal ID de la compra original (formato String de ObjectId).
     * @param compraActualizada DTO que contiene la información para la nueva compra.
     * @return true si el reagendamiento (cancelación + nueva compra) fue exitoso.
     * @throws ReagendaBoletoException Si ocurre un error durante la validación,
     * cancelación o el proceso de nueva compra.
     */
    public boolean procesarReagendaBoleto(String idCompraOriginal, CompraDTO compraActualizada) throws ReagendaBoletoException {
        validarEntradas(idCompraOriginal, compraActualizada);

        UsuarioDTO usuario = compraActualizada.getUsuario();
        ViajeDTO nuevoViaje = compraActualizada.getViaje();
        List<AsientoBoletoDTO> nuevosAsientos = compraActualizada.getListaAsiento();
        double precioNuevoBoleto = nuevoViaje.getPrecio() * nuevosAsientos.size();

        try {
            CompraDTO compraOriginalDTO = comprasBO.obtenerCompraDTOPorId(idCompraOriginal);
            if (compraOriginalDTO == null) {
                throw new ReagendaBoletoException("No se encontró la compra original con ID: " + idCompraOriginal + " para cancelar.");
            }
            compraOriginalDTO.setUsuario(usuario);

            cancelarCompraFachada.eliminarCompra(compraOriginalDTO);

            BoletoContext.limpiarBoleto();
            BoletoDTO boletoParaNuevaCompra = BoletoContext.getBoleto();
            boletoParaNuevaCompra.setViaje(nuevoViaje);
            boletoParaNuevaCompra.setListaAsiento(nuevosAsientos);
            boletoParaNuevaCompra.setPrecio(nuevoViaje.getPrecio());

            DetallesPagoDTO detallesNuevaCompra = new DetallesPagoDTO("Monedero", precioNuevoBoleto, boletoParaNuevaCompra);

            boolean nuevaCompraExitosa = comprarBoletoFachada.procesarCompra(detallesNuevaCompra, usuario);

            if (!nuevaCompraExitosa) {
                throw new ReagendaBoletoException("La compra original fue cancelada, pero falló la creación del nuevo boleto.");
            }

            return true;

        } catch (NegocioException | CompraBoletoException | PagoBoletoException e) {
            throw new ReagendaBoletoException("Error al procesar el reagendamiento: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ReagendaBoletoException("Ocurrió un error inesperado durante el reagendamiento: " + e.getMessage(), e);
        }
    }

    /**
     * Valida los datos de entrada para el proceso de reagendamiento.
     * Verifica que los IDs y DTOs necesarios no sean nulos o vacíos,
     * que los formatos de ID sean correctos, y que la información
     * esencial como asientos y precios sea válida. La fecha del nuevo viaje
     * también es validada para no ser anterior al día actual.
     *
     * @param idCompraOriginal ID de la compra a reagendar.
     * @param compraActualizada DTO con los nuevos datos del boleto.
     * @throws ReagendaBoletoException Si alguna validación falla.
     */
    private void validarEntradas(String idCompraOriginal, CompraDTO compraActualizada) throws ReagendaBoletoException {
        if (idCompraOriginal == null || idCompraOriginal.trim().isEmpty()) {
            throw new ReagendaBoletoException("El ID de la compra original no puede ser nulo o vacío.");
        }
        try {
            new ObjectId(idCompraOriginal);
        } catch (IllegalArgumentException e) {
             throw new ReagendaBoletoException("El ID de la compra original no tiene un formato válido: " + idCompraOriginal);
        }

        if (compraActualizada == null) {
            throw new ReagendaBoletoException("La información de la compra actualizada no puede ser nula.");
        }
        if (compraActualizada.getUsuario() == null || compraActualizada.getUsuario().getId() == null) {
            throw new ReagendaBoletoException("El usuario en la compra actualizada es inválido.");
        }
        ViajeDTO nuevoViaje = compraActualizada.getViaje();
        if (nuevoViaje == null || nuevoViaje.getIdViaje() == null) {
            throw new ReagendaBoletoException("El nuevo viaje seleccionado es inválido o no tiene ID.");
        }
         try {
            new ObjectId(nuevoViaje.getIdViaje());
        } catch (IllegalArgumentException e) {
             throw new ReagendaBoletoException("El ID del nuevo viaje no tiene un formato válido: " + nuevoViaje.getIdViaje());
        }

        if (nuevoViaje.getCamion() == null || nuevoViaje.getCamion().getNumeroCamion() == null ||
            nuevoViaje.getCamion().getNumeroCamion().trim().isEmpty()) {
            throw new ReagendaBoletoException("El camión del nuevo viaje es inválido o no tiene número.");
        }
        if (nuevoViaje.getFecha() == null) {
            throw new ReagendaBoletoException("La fecha del nuevo viaje no puede ser nula.");
        }
         if (nuevoViaje.getFecha().before(new Date())) {
            LocalDate fechaNuevoViajeLocal = nuevoViaje.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate hoy = LocalDate.now();
            if (fechaNuevoViajeLocal.isBefore(hoy)) {
                throw new ReagendaBoletoException("La nueva fecha del viaje no puede ser anterior al día de hoy.");
            }
        }

        if (compraActualizada.getListaAsiento() == null || compraActualizada.getListaAsiento().isEmpty()) {
            throw new ReagendaBoletoException("La lista de nuevos asientos no puede estar vacía.");
        }
        for (AsientoBoletoDTO abDTO : compraActualizada.getListaAsiento()) {
            if (abDTO.getAsiento() == null || abDTO.getAsiento().getNumero() == null || abDTO.getAsiento().getNumero().trim().isEmpty() ||
                abDTO.getNombreAsiento() == null || abDTO.getNombreAsiento().trim().isEmpty()) {
                throw new ReagendaBoletoException("Uno o más asientos seleccionados tienen información inválida (número o nombre de pasajero).");
            }
        }
        if (nuevoViaje.getPrecio() == null || nuevoViaje.getPrecio() <=0) {
             throw new ReagendaBoletoException("El precio unitario del nuevo viaje debe ser un valor positivo.");
        }
    }
}
