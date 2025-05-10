/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Server;

import Exception.PagoException;
import dto.TarjetaDTO;

import itson.rutappdto.DetallesPagoDTO;
import itson.rutappdto.TarjetaCreditoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.rmi.RemoteException;
import java.util.Map;
import java.lang.reflect.Field; 

import static org.junit.jupiter.api.Assertions.*;

class ServidorTest {

    private Servidor servidor;
    private TarjetaCreditoDTO tarjetaClienteValida;

   
    private DetallesPagoDTO construirDetallesPago(Double monto, TarjetaCreditoDTO tarjetaCreditoDTO) {
        return new DetallesPagoDTO(
                System.currentTimeMillis(), // idPago de ejemplo
                "TARJETA_CREDITO", // metodoPago
                monto,
                tarjetaCreditoDTO != null && tarjetaCreditoDTO.getNumeroTarjeta() != null && tarjetaCreditoDTO.getNumeroTarjeta().length() >= 4
                ? Integer.parseInt(tarjetaCreditoDTO.getNumeroTarjeta().substring(tarjetaCreditoDTO.getNumeroTarjeta().length() - 4)) : null,
                null, 
                tarjetaCreditoDTO
        );
    }

    @BeforeEach
    void setUp() throws RemoteException {
        servidor = new Servidor();
        tarjetaClienteValida = new TarjetaCreditoDTO("1111222233334444", "Juan Perez", "12/25", "123");
    }

    @Test
    @DisplayName("Procesar pago exitoso descuenta saldo correctamente")
    void procesarPagoConValidacion_pagoExitoso_descuentaSaldo() throws RemoteException, PagoException, NoSuchFieldException, IllegalAccessException {
        DetallesPagoDTO detalles = construirDetallesPago(100.0, tarjetaClienteValida);

        boolean resultado = servidor.procesarPagoConValidacion(detalles);
        assertTrue(resultado, "El pago debería ser exitoso.");

        Field tarjetasRegistradasField = Servidor.class.getDeclaredField("tarjetasRegistradas");
        tarjetasRegistradasField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<String, TarjetaDTO> tarjetasInternas = (Map<String, TarjetaDTO>) tarjetasRegistradasField.get(servidor);

        TarjetaDTO tarjetaInterna = tarjetasInternas.get("1111222233334444");
        assertNotNull(tarjetaInterna, "La tarjeta interna no debería ser nula.");
        assertEquals(4900.00, tarjetaInterna.getSaldo(), 0.001, "El saldo debería haberse reducido en 100.");
    }

    @Test
    @DisplayName("Falla el pago si DetallesPagoDTO es nulo")
    void procesarPagoConValidacion_detallesPagoNulo_lanzaExcepcion() {
        PagoException exception = assertThrows(PagoException.class, () -> {
            servidor.procesarPagoConValidacion(null);
        });
        assertEquals("Los detalles de pago o de la tarjeta (DTO externo) son nulos.", exception.getMessage());
    }

    @Test
    @DisplayName("Falla el pago si TarjetaCreditoDTO en DetallesPagoDTO es nulo")
    void procesarPagoConValidacion_tarjetaClienteNula_lanzaExcepcion() {
        DetallesPagoDTO detallesSinTarjeta = construirDetallesPago(100.0, null);

        PagoException exception = assertThrows(PagoException.class, () -> {
            servidor.procesarPagoConValidacion(detallesSinTarjeta);
        });
        assertEquals("Los detalles de pago o de la tarjeta (DTO externo) son nulos.", exception.getMessage());
    }

    @Test
    @DisplayName("Falla el pago si número de tarjeta es nulo")
    void procesarPagoConValidacion_numeroTarjetaNulo_lanzaExcepcion() {
        TarjetaCreditoDTO tarjetaSinNumero = new TarjetaCreditoDTO(null, "Test", "12/25", "123");
        DetallesPagoDTO detalles = construirDetallesPago(50.0, tarjetaSinNumero);
        PagoException exception = assertThrows(PagoException.class, () -> {
            servidor.procesarPagoConValidacion(detalles);
        });
        assertEquals("Número de tarjeta (DTO externo) no proporcionado.", exception.getMessage());
    }

    @Test
    @DisplayName("Falla el pago si CVV es nulo")
    void procesarPagoConValidacion_cvvNulo_lanzaExcepcion() {
        TarjetaCreditoDTO tarjetaSinCvv = new TarjetaCreditoDTO("1111222233334444", "Test", "12/25", null);
        DetallesPagoDTO detalles = construirDetallesPago(50.0, tarjetaSinCvv);
        PagoException exception = assertThrows(PagoException.class, () -> {
            servidor.procesarPagoConValidacion(detalles);
        });
        assertEquals("CVV (DTO externo) no proporcionado.", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({"0.0", "-100.0"})
    @DisplayName("Falla el pago si monto es cero o negativo")
    void procesarPagoConValidacion_montoInvalido_lanzaExcepcion(double monto) {
        DetallesPagoDTO detalles = construirDetallesPago(monto, tarjetaClienteValida);
        PagoException exception = assertThrows(PagoException.class, () -> {
            servidor.procesarPagoConValidacion(detalles);
        });
        assertEquals("El monto a pagar debe ser positivo.", exception.getMessage());
    }

    @Test
    @DisplayName("Falla el pago si tarjeta no está registrada")
    void procesarPagoConValidacion_tarjetaNoRegistrada_lanzaExcepcion() {
        TarjetaCreditoDTO tarjetaNoExistente = new TarjetaCreditoDTO("0000000000000000", "No Existe", "01/25", "999");
        DetallesPagoDTO detalles = construirDetallesPago(50.0, tarjetaNoExistente);
        PagoException exception = assertThrows(PagoException.class, () -> {
            servidor.procesarPagoConValidacion(detalles);
        });
        assertEquals("Tarjeta no registrada o número incorrecto.", exception.getMessage());
    }

    @Test
    @DisplayName("Falla el pago si CVV es incorrecto")
    void procesarPagoConValidacion_cvvIncorrecto_lanzaExcepcion() {
        TarjetaCreditoDTO tarjetaCvvErroneo = new TarjetaCreditoDTO("1111222233334444", "Juan Perez", "12/25", "999"); // CVV correcto es "123"
        DetallesPagoDTO detalles = construirDetallesPago(50.0, tarjetaCvvErroneo);
        PagoException exception = assertThrows(PagoException.class, () -> {
            servidor.procesarPagoConValidacion(detalles);
        });
        assertEquals("CVV incorrecto.", exception.getMessage());
    }

    @Test
    @DisplayName("Falla el pago si saldo es insuficiente")
    void procesarPagoConValidacion_saldoInsuficiente_lanzaExcepcion() {
        // Tarjeta "9999000011112222" tiene saldo 200.00 en la inicialización del Servidor
        TarjetaCreditoDTO tarjetaConPocoSaldo = new TarjetaCreditoDTO("9999000011112222", "Carlos Ruiz", "03/24", "789");
        DetallesPagoDTO detalles = construirDetallesPago(250.0, tarjetaConPocoSaldo);
        PagoException exception = assertThrows(PagoException.class, () -> {
            servidor.procesarPagoConValidacion(detalles);
        });
        assertEquals("Saldo insuficiente en la tarjeta.", exception.getMessage());
    }

    @Test
    @DisplayName("Pago exitoso con monto igual al saldo")
    void procesarPagoConValidacion_pagoExitoso_montoIgualSaldo() throws RemoteException, PagoException, NoSuchFieldException, IllegalAccessException {
        TarjetaCreditoDTO tarjetaConSaldoExacto = new TarjetaCreditoDTO("9999000011112222", "Carlos Ruiz", "03/24", "789");
        DetallesPagoDTO detalles = construirDetallesPago(200.0, tarjetaConSaldoExacto);

        boolean resultado = servidor.procesarPagoConValidacion(detalles);
        assertTrue(resultado, "El pago debería ser exitoso.");

        Field tarjetasRegistradasField = Servidor.class.getDeclaredField("tarjetasRegistradas");
        tarjetasRegistradasField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<String, TarjetaDTO> tarjetasInternas = (Map<String, TarjetaDTO>) tarjetasRegistradasField.get(servidor);

        TarjetaDTO tarjetaInterna = tarjetasInternas.get("9999000011112222");
        assertNotNull(tarjetaInterna);
        assertEquals(0.0, tarjetaInterna.getSaldo(), 0.001, "El saldo debería ser cero después del pago.");
    }
}
