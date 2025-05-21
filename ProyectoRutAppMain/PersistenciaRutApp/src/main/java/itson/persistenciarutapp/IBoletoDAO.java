/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.persistenciarutapp;

import itson.persistenciarutapp.implementaciones.Boleto;



/**
 *
 * @author chris
 */
public interface IBoletoDAO {
    
    Boleto agregarBoleto(Boleto boletoNuevo);
    
    Boleto obtenerBoleto(String numero);
}
