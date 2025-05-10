/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente;

import Interfaz.ServidorInterface;
import itson.rutappdto.DetallesPagoDTO;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import Exception.PagoException;

/**
 *
 * @author mmax2
 */
public class Cliente {

    private ServidorInterface servidorRemoto; 
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 1100;
    private static final String RMI_SERVICE_NAME = "ServidorPagosRMI"; 

    /**
     * Constructor del cliente. Intenta conectarse al servidor RMI usando
     * valores por defecto.
     *
     * @throws Exception Si ocurre un error durante la conexión.
     */
    public Cliente() throws Exception {
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    /**
     * Constructor del cliente. Intenta conectarse al servidor RMI.
     *
     * @param host Nombre o IP del host donde corre el servidor RMI.
     * @param puerto Puerto del registro RMI.
     * @throws Exception Si ocurre un error durante la conexión.
     */
    public Cliente(String host, int puerto) throws Exception {
        Registry registry = LocateRegistry.getRegistry(host, puerto);
        this.servidorRemoto = (ServidorInterface) registry.lookup(RMI_SERVICE_NAME);
        System.out.println("Cliente: Conectado a " + RMI_SERVICE_NAME + " en " + host + ":" + puerto);
    }

    /**
     * Intenta procesar un pago llamando al método remoto del servidor.
     *
     * @param detallesPago DTO con la información del pago y la tarjeta.
     * @return true si el servidor confirma el pago.
     * @throws PagoException Si el servidor rechaza el pago por validación.
     * @throws RemoteException Si ocurre un error de comunicación RMI.
     * @throws Exception Si ocurre otro error inesperado.
     */
    public boolean realizarPago(DetallesPagoDTO detallesPago) throws PagoException, RemoteException, Exception {
        if (servidorRemoto == null) {
            throw new IllegalStateException("Cliente no conectado al servidor de pagos. Intente reconectar o verifique el servidor.");
        }
        return servidorRemoto.procesarPagoConValidacion(detallesPago);
    }
}
