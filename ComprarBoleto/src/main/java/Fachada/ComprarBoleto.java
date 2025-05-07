/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fachada;

import Control.Control;
import Ex.CompraBoletoException;
import Interfaz.IComprarBoleto;
import control.ControlPagoBoleto;
import itson.rutappdto.AsientoDTO;
import itson.rutappdto.CamionDTO;
import itson.rutappdto.DetallesPagoDTO;
import itson.rutappdto.UsuarioDTO;
import java.util.List;

/**
 *
 * @author multaslokas33
 */
public class ComprarBoleto implements IComprarBoleto {

    private Control control;

    public ComprarBoleto() {
        control = new Control();
    }

//    @Override
//    public void mostrarPantallaPago() {
//        control.mostrarPantallaPago();
//    }
    @Override
    public void comprarBoleto() {
        control.registrarCompra();
    }

    @Override
    public boolean procesarCompra(DetallesPagoDTO detalles, UsuarioDTO usuario) throws CompraBoletoException {
        // Aquí podrías validar asientos si fuera necesario (más adelante)

        // Paso 1: Procesar el pago
        boolean pagoExitoso = ControlPagoBoleto.getInstancia().procesarPago(detalles, usuario);

        // Paso 2: Registrar compra si el pago fue exitoso
        if (pagoExitoso) {
            control.registrarCompra();
            return true;
        } else {
            throw new CompraBoletoException("El pago no fue aprobado.");
        }
    }
}
