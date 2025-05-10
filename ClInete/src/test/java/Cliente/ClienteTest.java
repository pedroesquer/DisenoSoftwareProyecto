/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Cliente;

import Exception.PagoException;
import Interfaz.ServidorInterface;
import itson.rutappdto.DetallesPagoDTO;
import itson.rutappdto.TarjetaCreditoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteTest {

    @Mock
    private ServidorInterface servidorRemotoMock;

    @Mock
    private Registry registryMock;

    private Cliente cliente;

    private DetallesPagoDTO detallesPagoEjemplo;
    private TarjetaCreditoDTO tarjetaClienteEjemplo;

    private static final String RMI_SERVICE_NAME_IN_TEST = "ServidorPagosRMI";

    private DetallesPagoDTO construirDetallesPago(Double monto, TarjetaCreditoDTO tarjetaCreditoDTO) {
        return new DetallesPagoDTO(
                1L,
                "TARJETA_CREDITO",
                monto,
                tarjetaCreditoDTO != null && tarjetaCreditoDTO.getNumeroTarjeta() != null && tarjetaCreditoDTO.getNumeroTarjeta().length() >= 4
                ? Integer.parseInt(tarjetaCreditoDTO.getNumeroTarjeta().substring(tarjetaCreditoDTO.getNumeroTarjeta().length() - 4)) : null,
                null,
                tarjetaCreditoDTO
        );
    }

    @BeforeEach
    void setUp() {
        tarjetaClienteEjemplo = new TarjetaCreditoDTO("1234567812345678", "Test User", "12/28", "123");
        detallesPagoEjemplo = construirDetallesPago(100.0, tarjetaClienteEjemplo);
    }

    @Test
    @DisplayName("Constructor de Cliente conecta exitosamente a RMI")
    void constructor_conexionExitosa() throws Exception {
        try (MockedStatic<LocateRegistry> mockedStaticLocateRegistry = Mockito.mockStatic(LocateRegistry.class)) {
            mockedStaticLocateRegistry.when(() -> LocateRegistry.getRegistry(anyString(), anyInt())).thenReturn(registryMock);
            when(registryMock.lookup(RMI_SERVICE_NAME_IN_TEST)).thenReturn(servidorRemotoMock);

            cliente = new Cliente("testhost", 1234);

            assertNotNull(cliente, "El cliente no debería ser nulo después de una conexión exitosa.");
            verify(registryMock).lookup(RMI_SERVICE_NAME_IN_TEST);
        }
    }

    @Test
    @DisplayName("Constructor de Cliente falla si lookup falla")
    void constructor_lookupFalla_lanzaExcepcion() throws RemoteException, NotBoundException {
        try (MockedStatic<LocateRegistry> mockedStaticLocateRegistry = Mockito.mockStatic(LocateRegistry.class)) {
            mockedStaticLocateRegistry.when(() -> LocateRegistry.getRegistry(anyString(), anyInt())).thenReturn(registryMock);
            when(registryMock.lookup(RMI_SERVICE_NAME_IN_TEST)).thenThrow(new NotBoundException("Servicio no encontrado en el registro RMI."));

            Exception exception = assertThrows(Exception.class, () -> {
                new Cliente("testhost", 1234);
            });

            assertTrue(exception instanceof NotBoundException || (exception.getCause() != null && exception.getCause() instanceof NotBoundException),
                    "La excepción debería ser o contener NotBoundException si el servicio no está vinculado.");

            if (exception instanceof NotBoundException) {
                assertEquals("Servicio no encontrado en el registro RMI.", exception.getMessage());
            } else if (exception.getCause() instanceof NotBoundException) {
                assertEquals("Servicio no encontrado en el registro RMI.", exception.getCause().getMessage());
            }
        }
    }

    @Test
    @DisplayName("Realizar pago exitoso cuando servidor confirma")
    void realizarPago_servidorConfirma_retornaTrue() throws Exception {
        try (MockedStatic<LocateRegistry> mockedStaticLocateRegistry = Mockito.mockStatic(LocateRegistry.class)) {
            mockedStaticLocateRegistry.when(() -> LocateRegistry.getRegistry(anyString(), anyInt())).thenReturn(registryMock);
            when(registryMock.lookup(RMI_SERVICE_NAME_IN_TEST)).thenReturn(servidorRemotoMock);

            cliente = new Cliente("testhost", 1234);

            when(servidorRemotoMock.procesarPagoConValidacion(detallesPagoEjemplo)).thenReturn(true);

            boolean resultado = cliente.realizarPago(detallesPagoEjemplo);

            assertTrue(resultado, "El resultado del pago debería ser verdadero para una transacción exitosa.");
            verify(servidorRemotoMock).procesarPagoConValidacion(detallesPagoEjemplo);
        }
    }

    @Test
    @DisplayName("Realizar pago falla cuando servidor lanza PagoException")
    void realizarPago_servidorLanzaPagoException_propagaExcepcion() throws Exception {
        try (MockedStatic<LocateRegistry> mockedStaticLocateRegistry = Mockito.mockStatic(LocateRegistry.class)) {
            mockedStaticLocateRegistry.when(() -> LocateRegistry.getRegistry(anyString(), anyInt())).thenReturn(registryMock);
            when(registryMock.lookup(RMI_SERVICE_NAME_IN_TEST)).thenReturn(servidorRemotoMock);

            cliente = new Cliente("testhost", 1234);

            PagoException excepcionEsperada = new PagoException("Saldo insuficiente en la tarjeta.");
            when(servidorRemotoMock.procesarPagoConValidacion(detallesPagoEjemplo)).thenThrow(excepcionEsperada);

            PagoException exceptionReal = assertThrows(PagoException.class, () -> {
                cliente.realizarPago(detallesPagoEjemplo);
            });

            assertEquals("Saldo insuficiente en la tarjeta.", exceptionReal.getMessage());
            verify(servidorRemotoMock).procesarPagoConValidacion(detallesPagoEjemplo);
        }
    }

    @Test
    @DisplayName("Realizar pago falla cuando servidor lanza RemoteException")
    void realizarPago_servidorLanzaRemoteException_propagaExcepcion() throws Exception {
        try (MockedStatic<LocateRegistry> mockedStaticLocateRegistry = Mockito.mockStatic(LocateRegistry.class)) {
            mockedStaticLocateRegistry.when(() -> LocateRegistry.getRegistry(anyString(), anyInt())).thenReturn(registryMock);
            when(registryMock.lookup(RMI_SERVICE_NAME_IN_TEST)).thenReturn(servidorRemotoMock);

            cliente = new Cliente("testhost", 1234);

            RemoteException excepcionEsperada = new RemoteException("Error de comunicación con el servidor.");
            when(servidorRemotoMock.procesarPagoConValidacion(detallesPagoEjemplo)).thenThrow(excepcionEsperada);

            RemoteException exceptionReal = assertThrows(RemoteException.class, () -> {
                cliente.realizarPago(detallesPagoEjemplo);
            });

            assertEquals("Error de comunicación con el servidor.", exceptionReal.getMessage());
            verify(servidorRemotoMock).procesarPagoConValidacion(detallesPagoEjemplo);
        }
    }
}
