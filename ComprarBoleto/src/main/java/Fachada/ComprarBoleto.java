/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fachada;

import Control.ControlComprarBoleto;
import Ex.CompraBoletoException;
import Interfaz.IComprarBoleto;
import control.ControlPagoBoleto;
import excepciones.PagoBoletoException;
import fachada.PagoBoleto;
import interfaz.IPagoBoleto;
import itson.rutappdto.AsientoDTO;
import itson.rutappdto.CamionDTO;
import itson.rutappdto.DetallesPagoDTO;
import itson.rutappdto.UsuarioDTO;
import java.util.List;
import usuarioActivoManager.UsuarioActivoManager;

/**
 *
 * @author multaslokas33
 */
public class ComprarBoleto implements IComprarBoleto {

    private ControlComprarBoleto control;
    IPagoBoleto pagoBoleto = new PagoBoleto();

    public ComprarBoleto() {
        control = new ControlComprarBoleto();
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
    public boolean procesarCompra(DetallesPagoDTO detalles, UsuarioDTO usuario) throws CompraBoletoException, PagoBoletoException {
        try {
            // Aquí va el código que realiza el pago
            System.out.println("Pago en proceso...");
            boolean pagoExitoso = pagoBoleto.procesarPago(detalles, UsuarioActivoManager.getInstancia().getUsuario());
            System.out.println("Resultado del pago: " + pagoExitoso);
            return pagoExitoso;
        } catch (PagoBoletoException e) {
            System.out.println("Excepción durante el pago: " + e.getMessage());
            // Aquí puedes capturar y manejar excepciones para ver qué pasa
            
        }
        return false;
    }

    @Override
    public boolean procesarCompraDos(DetallesPagoDTO detalles, UsuarioDTO usuario) throws CompraBoletoException, PagoBoletoException {
        boolean pagoExitoso = pagoBoleto.procesarPago(detalles, usuario);
        return pagoExitoso;
    }
}
