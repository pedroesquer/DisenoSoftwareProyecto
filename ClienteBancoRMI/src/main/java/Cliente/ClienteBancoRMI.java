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
public class ClienteBancoRMI {

    private ServidorInterface servidorRemoto; 
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 1100;
    private static final String RMI_SERVICE_NAME = "ServidorPagosRMI"; 

    public ClienteBancoRMI() throws Exception {
        Registry registry = LocateRegistry.getRegistry(DEFAULT_HOST, DEFAULT_PORT);
        this.servidorRemoto = (ServidorInterface) registry.lookup(RMI_SERVICE_NAME);
        System.out.println("ClienteBancoRMI: Conectado a " + RMI_SERVICE_NAME + " en " + DEFAULT_HOST + ":" + DEFAULT_PORT);
    }

    public boolean realizarPago(DetallesPagoDTO detallesPago) throws RemoteException, PagoException {
        if (servidorRemoto == null) {
            throw new IllegalStateException("No conectado al servidor de pagos.");
        }
        System.out.println("pago 4");
        return servidorRemoto.procesarPagoConValidacion(detallesPago);
    }
}
