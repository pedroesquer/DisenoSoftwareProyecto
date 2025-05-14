package control;

//import itson.cliente.ClienteBancoRMI;
import Cliente.ClienteBancoRMI;
import excepciones.PagoBoletoException;
import itson.rutappdto.DetallesPagoDTO;
import itson.rutappdto.TarjetaCreditoDTO;
import itson.rutappdto.UsuarioDTO;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author pedro
 */
public class ControlPagoBoleto {

    private static ControlPagoBoleto instance;

    public ControlPagoBoleto() {
    }

    /**
     * Método para obtener la instancia del singleton.
     *
     * @return una instancia de tipo Control.
     */
    public static ControlPagoBoleto getInstancia() {
        if (instance == null) {
            instance = new ControlPagoBoleto();
        }
        return instance;
    }

    /**
     * Algoritmo de Luhn para validar números de tarjetas.
     *
     * @return Si la tarjeta es válida o no.
     */
    public boolean validarNumeroTarjeta(String numeroTarjeta) {
        int sum = 0;
        boolean alterno = false;

        for (int i = numeroTarjeta.length() - 1; i >= 0; i--) {
            int n = Character.getNumericValue(numeroTarjeta.charAt(i));

            if (alterno) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }
            sum += n;
            alterno = !alterno;
        }
        return sum % 10 == 0;
    }

    /**
     * Método que valida si la fecha de vencimiento de una tarjeta es válida
     *
     * @return Si la fecha de vencimiento es válida o no.
     */
    public boolean validarFechaVencimiento(String fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        YearMonth fechaActual = YearMonth.now();

        try {
            YearMonth fechaVencimiento = YearMonth.parse(fecha, formatter);
            return !fechaVencimiento.isBefore(fechaActual);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Método que valida si se cuenta con el suficiente saldo para cubrir el
     * importe
     *
     * @return Si hay saldo para cubrir el importe o no.
     */
    public boolean validarSaldoMonedero(UsuarioDTO usuarioDTO, Double importe) {
        Double saldoDisponible = usuarioDTO.getSaldoMonedero();

        if (saldoDisponible != null && saldoDisponible >= importe) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método que descuenta el saldo del monedero según la cantidad que
     * introduzcas.
     *
     * @param usuarioDTO
     * @param cantidad
     */
    public void descontarSaldoMonedero(UsuarioDTO usuarioDTO, Double cantidad) {
        if (validarSaldoMonedero(usuarioDTO, cantidad)) {
            usuarioDTO.setSaldoMonedero(usuarioDTO.getSaldoMonedero() - cantidad);
        }
    }

    /**
     * Método que agrega la cantidad que desees al monedero.
     *
     * @param usuarioDTO
     * @param cantidad
     */
    public void agregarSaldoMonedero(UsuarioDTO usuarioDTO, Double cantidad) {
        if (usuarioDTO.getSaldoMonedero() != null) {
            usuarioDTO.setSaldoMonedero(usuarioDTO.getSaldoMonedero() + cantidad);
        }
    }

    /**
     * Método el cual se encarga de procesar el pago.Si el pago es con monedero
 lo hace manual dentro del método y si es con tarjeta llama a
 Infraestructura.
     *
     * @param detalles detalles del pago.
     * @param usuarioDTO
     * @return
     * @throws excepciones.PagoBoletoException
     */
    public boolean procesarPago(DetallesPagoDTO detalles, UsuarioDTO usuarioDTO) throws PagoBoletoException {

        if (detalles == null) {
            throw new PagoBoletoException("Los detalles vienen incompletos");
        }

        double monto = detalles.getMonto();
        TarjetaCreditoDTO tarjeta = detalles.getDetallesTarjeta();

        if (tarjeta == null) {
            // Pago con monedero
            if (validarSaldoMonedero(usuarioDTO, monto)) {
                descontarSaldoMonedero(usuarioDTO, monto);
                System.out.println("Pago con monedero aprobado.");
                return true;
            } else {
                System.out.println("Saldo insuficiente en monedero.");
                return false;
            }
        } else {
            // Validaciones locales
            if (!validarNumeroTarjeta(tarjeta.getNumeroTarjeta())) {
                System.out.println("Número de tarjeta inválido.");
                return false;
            }

            //Enviar al banco ficticio (servidor RMI)
            try {
                System.out.println("PEPONGAAAS");
                ClienteBancoRMI cliente = new ClienteBancoRMI();
                boolean aprobado = cliente.realizarPago(detalles);

                return aprobado;

            } catch (Exception e) {
                System.err.println("Error al contactar al servidor de banco: " + e.getMessage());
                return false;
            }
        }
    }

}
