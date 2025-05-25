/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fachada;

import Control.ControlReagendaBoleto;
import Exception.ReagendaBoletoException;
import Interfaz.IReagendaBoleto;
import itson.rutappdto.CompraDTO;

/**
 *
 * @author mmax2
 */
public class FacahdaReagendaBoleto implements IReagendaBoleto {

    private final ControlReagendaBoleto controlReagendaBoleto;

    public FacahdaReagendaBoleto () {
        this.controlReagendaBoleto = new ControlReagendaBoleto();
    }

    @Override
    public boolean reagendarBoleto(String idCompraOriginal, CompraDTO compraActualizada) throws ReagendaBoletoException {
        return this.controlReagendaBoleto.procesarReagendaBoleto(idCompraOriginal, compraActualizada);
    }
}
