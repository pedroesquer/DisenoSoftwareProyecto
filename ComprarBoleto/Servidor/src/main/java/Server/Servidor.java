/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import Exception.PagoException;
import Interfaz.ServidorInterface;
import dto.TarjetaDTO;
import itson.rutappdto.DetallesPagoDTO;
import itson.rutappdto.TarjetaCreditoDTO;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Clase renombrada de ServidorPagos a Servidor
public class Servidor extends UnicastRemoteObject implements ServidorInterface {

    private final Map<String, TarjetaDTO> tarjetasRegistradas;

    /**
     * Constructor del servidor. Inicializa la lista de tarjetas preguardadas.
     *
     * @throws RemoteException si ocurre un error durante la exportación del
     * objeto remoto.
     */
    public Servidor() throws RemoteException {
        super();
        tarjetasRegistradas = new ConcurrentHashMap<>();
        inicializarTarjetas();
    }

    private void inicializarTarjetas() {
        tarjetasRegistradas.put("1111222233334444", new TarjetaDTO("1111222233334444", "123", 5000.00, "Juan Perez"));
        tarjetasRegistradas.put("5555666677778888", new TarjetaDTO("5555666677778888", "456", 10000.00, "Maria Lopez"));
        tarjetasRegistradas.put("9999000011112222", new TarjetaDTO("9999000011112222", "789", 200.00, "Carlos Ruiz"));
        tarjetasRegistradas.put("1234123412341234", new TarjetaDTO("1234123412341234", "000", 1500.00, "Ana Gomez"));
        System.out.println("Servidor: Tarjetas de ejemplo (DTO internas) inicializadas.");
    }

    /**
     * Implementación del método remoto para procesar pagos con validación.
     * @throws Exception.PagoException
     */
    @Override
    public synchronized boolean procesarPagoConValidacion(DetallesPagoDTO detallesPago) throws RemoteException, PagoException {
        System.out.println("\nServidor: Solicitud de pago recibida.");
        System.out.println("Detalles del pago (DTO externo): " + detallesPago);

        if (detallesPago == null || detallesPago.getDetallesTarjeta() == null) {
            throw new PagoException("Los detalles de pago o de la tarjeta (DTO externo) son nulos.");
        }

        TarjetaCreditoDTO tarjetaCliente = detallesPago.getDetallesTarjeta();
        double montoAPagar = detallesPago.getMonto();

        if (tarjetaCliente.getNumeroTarjeta() == null || tarjetaCliente.getNumeroTarjeta().trim().isEmpty()) {
            throw new PagoException("Número de tarjeta (DTO externo) no proporcionado.");
        }
        if (tarjetaCliente.getCvv() == null || tarjetaCliente.getCvv().trim().isEmpty()) {
            throw new PagoException("CVV (DTO externo) no proporcionado.");
        }
        if (montoAPagar <= 0) {
            throw new PagoException("El monto a pagar debe ser positivo.");
        }

        TarjetaDTO tarjetaInterna = tarjetasRegistradas.get(tarjetaCliente.getNumeroTarjeta());

        if (tarjetaInterna == null) {
            System.out.println("Validación fallida: Tarjeta no encontrada en el servidor - " + tarjetaCliente.getNumeroTarjeta());
            throw new PagoException("Tarjeta no registrada o número incorrecto.");
        }

        System.out.println("Tarjeta encontrada en servidor (DTO interno): " + tarjetaInterna.getNombreTitular());

        if (!tarjetaInterna.getCvv().equals(tarjetaCliente.getCvv())) {
            System.out.println("Validación fallida: CVV incorrecto para tarjeta " + tarjetaInterna.getNumero());
            throw new PagoException("CVV incorrecto.");
        }

        if (tarjetaInterna.getSaldo() < montoAPagar) {
            System.out.println("Validación fallida: Saldo insuficiente. Saldo actual: " + tarjetaInterna.getSaldo() + ", Monto solicitado: " + montoAPagar);
            throw new PagoException("Saldo insuficiente en la tarjeta.");
        }

        double nuevoSaldo = tarjetaInterna.getSaldo() - montoAPagar;
        tarjetaInterna.setSaldo(nuevoSaldo);
        tarjetaCliente.setCvv(null);

        System.out.println("Pago procesado exitosamente para tarjeta " + tarjetaInterna.getNumero());
        System.out.println("Monto descontado: " + montoAPagar + ". Nuevo saldo: " + nuevoSaldo);
        return true;
    }

    /**
     * Método principal para iniciar el servidor RMI.
     */
    public static void main(String[] args) {
       try {
        ServidorInterface servidor = new Servidor();
        Registry registry = LocateRegistry.createRegistry(1100);
        registry.rebind("ServidorPagosRMI", servidor);
        System.out.println("ServidorPagosRMI listo en el puerto 1100.");

        // Agregar shutdown hook para limpiar antes de cerrar
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                registry.unbind("ServidorPagosRMI");
                UnicastRemoteObject.unexportObject(servidor, true);
                System.out.println("Servidor cerrado correctamente.");
            } catch (Exception e) {
                System.err.println("Error al cerrar servidor: " + e.getMessage());
            }
        }));

        // Mantener vivo el hilo principal para que el servidor siga corriendo
        Thread.currentThread().join();

    } catch (Exception e) {
        System.err.println("Excepción en Servidor (main): " + e.getMessage());
        e.printStackTrace();
    }
}
}
