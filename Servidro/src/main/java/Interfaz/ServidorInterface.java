/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaz;

import itson.rutappdto.DetallesPagoDTO;
import java.rmi.Remote;
import java.rmi.RemoteException;




/**
 *
 * @author mmax2
 */
public interface ServidorInterface extends Remote {

    /**
     * Método remoto para procesar una solicitud de pago con tarjeta.
     * 
     * @param detallesPago El DTO que contiene el monto y los detalles de la
     * tarjeta.
     * @return true si el pago es aprobado, false si es rechazado.
     * @throws RemoteException Si ocurre un error de comunicación RMI.
     */
    boolean procesarPagoTarjeta(DetallesPagoDTO detallesPago) throws RemoteException;
}
