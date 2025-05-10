/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Server;

import itson.rutappdto.BoletoDTO;
import itson.rutappdto.DetallesPagoDTO;
import itson.rutappdto.TarjetaCreditoDTO;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

/**
 *
 * @author mmax2
 */
public class ServidorTest {

    private Servidor servidor; // La instancia de la clase bajo prueba

    @BeforeEach
    void setUp() {
        try {
            servidor = new Servidor();
        } catch (RemoteException e) {
            fail("No se pudo instanciar el Servidor para pruebas: " + e.getMessage());
        }
    }

    /**
     * Crea un DTO de detalles de pago base para usar en las pruebas, adaptado a
     * la estructura de DetallesPagoDTO proporcionada por el usuario.
     *
     * @param monto El monto del pago.
     * @param numeroTarjeta El número de tarjeta completo (para simulación).
     * @param terminacion Los últimos 4 dígitos (Integer).
     * @return Un DetallesPagoDTO configurado.
     */
    private DetallesPagoDTO crearDetallesPago(double monto, String numeroTarjeta, Integer terminacion) {
        TarjetaCreditoDTO tarjeta = null;
        if (numeroTarjeta != null) {
            tarjeta = new TarjetaCreditoDTO(
                    numeroTarjeta,
                    "Nombre Titular Prueba",
                    "12/25", // Fecha válida de ejemplo
                    "123" // CVV de ejemplo
            );
        }

        // Crear instancia de BoletoDTO (puede ser null o un mock si es necesario)
        BoletoDTO boletoMock = null; // Usar null para simplicidad en esta prueba unitaria
        // Si necesitaras un BoletoDTO real o mock:
        // BoletoDTO boletoMock = Mockito.mock(BoletoDTO.class);
        // O: BoletoDTO boletoMock = new BoletoDTO(...); // Si tienes un constructor adecuado

        // *** Usar el constructor de la clase DetallesPagoDTO proporcionada ***
        return new DetallesPagoDTO(
                // Asignar un valor de ejemplo para idPago (puede ser diferente en cada llamada si es necesario)
                System.currentTimeMillis(), // Usar timestamp como ID único simple para prueba
                "TARJETA_CREDITO", // metodoPago
                monto,
                terminacion, // terminacionTarjeta
                boletoMock, // boleto (null o mock)
                tarjeta // detallesTarjeta (puede ser null si numeroTarjeta es null)
        );
    }

    @Test
    @DisplayName("Procesar pago exitoso con monto bajo y tarjeta válida")
    void procesarPagoTarjeta_Exitoso_MontoBajo() throws RemoteException {
        // Arrange: Crear DTO con monto <= 1000 y tarjeta no terminada en 0000
        DetallesPagoDTO detalles = crearDetallesPago(500.00, "1234567890123456", 3456);
        // Act: Llamar al método bajo prueba
        boolean resultado = servidor.procesarPagoTarjeta(detalles);
        // Assert: Verificar que el resultado es true (pago aprobado)
        // Si esta prueba sigue fallando, la lógica en Servidor.java es diferente.
        assertTrue(resultado, "El pago debería ser aprobado para montos bajos y tarjetas válidas.");
    }

    @Test
    @DisplayName("Procesar pago rechazado por monto alto")
    void procesarPagoTarjeta_Rechazado_MontoAlto() throws RemoteException {
        // Arrange: Crear DTO con monto > 1000
        DetallesPagoDTO detalles = crearDetallesPago(15000.00, "1234567890123456", 3456);
        // Act: Llamar al método bajo prueba
        boolean resultado = servidor.procesarPagoTarjeta(detalles);
        // Assert: Verificar que el resultado es false (pago rechazado)
        assertFalse(resultado, "El pago debería ser rechazado para montos altos (> 10000).");
    }

    @Test
    @DisplayName("Procesar pago rechazado por tarjeta terminada en 0000")
    void procesarPagoTarjeta_Rechazado_TarjetaInvalida() throws RemoteException {
        // Arrange: Crear DTO con monto bajo pero tarjeta terminada en 0000
        DetallesPagoDTO detalles = crearDetallesPago(100.00, "9876543210000000", 0); // Terminación es 0
        // Act: Llamar al método bajo prueba
        boolean resultado = servidor.procesarPagoTarjeta(detalles);
        // Assert: Verificar que el resultado es false (pago rechazado)
        assertFalse(resultado, "El pago debería ser rechazado para tarjetas terminadas en 0000.");
    }

    @Test
    @DisplayName("Procesar pago exitoso con monto límite (1000)")
    void procesarPagoTarjeta_Exitoso_MontoLimite() throws RemoteException {
        // Arrange: Crear DTO con monto exactamente 1000
        DetallesPagoDTO detalles = crearDetallesPago(1000.00, "1111222233334444", 4444);
        // Act: Llamar al método bajo prueba
        boolean resultado = servidor.procesarPagoTarjeta(detalles);
        // Assert: Verificar que el resultado es true (pago aprobado)
        // Si esta prueba sigue fallando, la lógica en Servidor.java es diferente (quizás usa >= 1000).
        assertTrue(resultado, "El pago debería ser aprobado para el monto límite de 1000.");
    }

    @Test
    @DisplayName("Procesar pago cuando DetallesPagoDTO es null (manejo interno)")
    void procesarPagoTarjeta_ManejoNull_DetallesPago() {
        // Arrange: detallesPago es null
        DetallesPagoDTO detalles = null;
        // Act & Assert: Verificar que lanza NullPointerException como se espera
        assertThrows(NullPointerException.class, () -> {
            servidor.procesarPagoTarjeta(detalles);
        }, "Debería lanzar NullPointerException si DetallesPagoDTO es null.");
    }

    @Test
    @DisplayName("Procesar pago cuando TarjetaCreditoDTO (detallesTarjeta) es null")
    void procesarPagoTarjeta_ManejoNull_DetallesTarjeta() throws RemoteException {
        // Arrange: Crear DetallesPagoDTO pero con detallesTarjeta null
        DetallesPagoDTO detalles = new DetallesPagoDTO(
                2L, "TARJETA_CREDITO", 200.00, null, null, null // detallesTarjeta es null
        );
        // Act: Llamar al método bajo prueba
        boolean resultado = servidor.procesarPagoTarjeta(detalles);
        // Assert: Verificar que el resultado es true (pago aprobado)
        assertTrue(resultado, "El pago debería ser aprobado si detallesTarjeta es null pero el monto es bajo.");
    }

    @Test
    @DisplayName("Procesar pago cuando número de tarjeta es null dentro de detallesTarjeta")
    void procesarPagoTarjeta_ManejoNull_NumeroTarjeta() throws RemoteException {
        // Arrange: Crear DTO con TarjetaCreditoDTO pero numeroTarjeta es null
        TarjetaCreditoDTO tarjetaSinNumero = new TarjetaCreditoDTO(null, "Titular", "12/26", "456");
        DetallesPagoDTO detalles = new DetallesPagoDTO(
                3L, "TARJETA_CREDITO", 300.00, null, null, tarjetaSinNumero
        );

        // Act: Llamar al método bajo prueba
        boolean resultado = servidor.procesarPagoTarjeta(detalles);

        // Assert: Verificar que el resultado es true.
        // El servidor NO debe lanzar NPE porque verifica si numeroTarjeta es null.
        // Como el número es null, la condición de tarjeta inválida es falsa.
        // Como el monto (300) es bajo, la condición de monto alto es falsa.
        // Por lo tanto, el pago debe ser aprobado.
        assertTrue(resultado, "El pago debería aprobarse si el número de tarjeta es null y el monto es bajo.");
    }

}
