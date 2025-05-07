/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.cliente;

import interfaces.ServidorInterface;
import itson.rutappdto.DetallesPagoDTO;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author chris
 */
public class ClienteBancoRMI {

    public static boolean procesarPagoConTarjeta(DetallesPagoDTO detalles) {
        try {
            // Conectarse al registro RMI del servidor en localhost, puerto 5000
            Registry registro = LocateRegistry.getRegistry("localhost", 5000);

            // Buscar el stub remoto usando el nombre de referencia
            ServidorInterface stub = (ServidorInterface) registro.lookup("ServidorPagosRutApp");

            // Llamar al método remoto y retornar el resultado
            System.out.println("Enviando solicitud de pago al banco ficticio...");
            boolean aprobado = stub.procesarPagoTarjeta(detalles);
            System.out.println("Respuesta del banco: " + (aprobado ? "APROBADO" : "RECHAZADO"));
            return aprobado;

        } catch (Exception e) {
            System.err.println("[ERROR Cliente RMI] No se pudo conectar con el banco: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
