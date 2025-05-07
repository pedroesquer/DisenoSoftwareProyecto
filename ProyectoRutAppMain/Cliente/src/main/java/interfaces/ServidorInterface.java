/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import itson.rutappdto.DetallesPagoDTO;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author chris
 */
/**
 * Interfaz remota para el servidor de pagos (banco ficticio).
 * Define el método que puede ser llamado remotamente.
 */
public interface ServidorInterface extends Remote {
    
    /**
     * Procesa un pago con tarjeta.
     * 
     * @param detallesPago DTO con la información de la tarjeta y monto
     * @return true si el pago fue aprobado, false si fue rechazado
     * @throws RemoteException si ocurre un error en la conexión RMI
     */
    boolean procesarPagoTarjeta(DetallesPagoDTO detallesPago) throws RemoteException;
}
