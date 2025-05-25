/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaz;

import Exception.ReagendaBoletoException;
import itson.rutappdto.CompraDTO;

/**
 *
 * @author mmax2
 */
public interface IReagendaBoleto {
    /**
     * Procesa la solicitud de reagendar un boleto.
     * Este método realizará validaciones y luego invocará la lógica de negocio
     * necesaria en los módulos existentes para actualizar la compra.
     *
     * @param idCompraOriginal El ID de la compra original que se desea reagendar.
     * @param compraActualizada DTO que contiene toda la nueva información del boleto,
     * incluyendo el nuevo ViajeDTO (con su ID y CamionDTO),
     * la nueva lista de AsientoBoletoDTO, el UsuarioDTO,
     * y el precio final actualizado.
     * @return true si el boleto fue reagendado exitosamente.
     * @throws ReagendaBoletoException si ocurre un error durante la validación
     * o el proceso de reagendamiento.
     */
    boolean reagendarBoleto(String idCompraOriginal, CompraDTO compraActualizada) throws ReagendaBoletoException;
    
}
