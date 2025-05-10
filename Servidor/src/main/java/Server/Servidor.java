/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import Interfaz.ServidorInterface;
import itson.rutappdto.DetallesPagoDTO;
import itson.rutappdto.TarjetaCreditoDTO;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author mmax2
 */
/**
 * Implementación del servidor RMI. Implementa la interfaz remota y contiene la
 * lógica para procesar las solicitudes.
 */
public class Servidor extends UnicastRemoteObject implements ServidorInterface {

    protected Servidor() throws RemoteException {
        super();
    }

    /**
     * Implementación del método remoto para procesar pagos con tarjeta
     *
     * @param detallesPago DTO con los datos del pago.
     * @return true, false.
     * @throws RemoteException Por RMI.
     */
    @Override
    public boolean procesarPagoTarjeta(DetallesPagoDTO detallesPago) throws RemoteException {
        System.out.println("\n--- Servidor RMI: Solicitud de Pago con Tarjeta Recibida ---");
        System.out.println("Timestamp: " + java.time.LocalDateTime.now());
        System.out.println("Monto: " + detallesPago.getMonto());

        TarjetaCreditoDTO tarjeta = detallesPago.getDetallesTarjeta();
        if (tarjeta != null) {
            System.out.println("Tarjeta:");
            System.out.println("  Titular: " + tarjeta.getNombreTitular());
            System.out.println("  Número: **** **** **** " + (tarjeta.getNumeroTarjeta() != null && tarjeta.getNumeroTarjeta().length() > 4 ? tarjeta.getNumeroTarjeta().substring(tarjeta.getNumeroTarjeta().length() - 4) : "****"));
            System.out.println("  Expiración: " + tarjeta.getFechaExpiracion());
            // No loguear CVV
            System.out.println("  CVV: ***");
        } else {
            System.out.println("Tarjeta: Detalles no proporcionados.");
        }

        boolean pagoAprobado = true;
        if (detallesPago.getMonto() > 10000.00) {
            System.out.println("Pago RECHAZADO (Monto > 10000)");
            pagoAprobado = false;
        } else if (tarjeta != null && tarjeta.getNumeroTarjeta() != null && tarjeta.getNumeroTarjeta().endsWith("0000")) {
            System.out.println("Pago RECHAZADO ");
            pagoAprobado = false;
        } else {
            System.out.println(" Pago APROBADO");
        }

        System.out.println("--- Fin Solicitud de Pago ---");
        return pagoAprobado;
    }

    // --- Método main para iniciar el servidor RMI ---
    public static void main(String[] args) {
        try {
            // Crear una instancia del servidor
            Servidor servidor = new Servidor();

            Registry registry = LocateRegistry.createRegistry(5000);
            System.out.println("Registro RMI creado en el puerto 5000.");
            registry.rebind("ServidorPagosRutApp", servidor);

            System.out.println("Servidor de Pagos RutApp listo y esperando conexiones...");

        } catch (RemoteException e) {
            System.err.println("Error al iniciar el servidor RMI: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error general al iniciar el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
