/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Ex.CompraBoletoException;
import Fachada.ComprarBoleto;
import Interfaz.IComprarBoleto;
import control.ControlSeleccionAsiento;
import excepciones.PagoBoletoException;
import excepciones.SeleccionAsientoException;
import itson.consultardisponibilidad.Interfaz.IConsultarDisponibilidad;
import itson.consultardisponibilidad.fachada.FachadaConsultarDisponibilidad;
import itson.rutappdto.AsientoAsignadoDTO;
import itson.rutappdto.AsientoBoletoDTO;
import itson.rutappdto.AsientoDTO;
import itson.rutappdto.CamionDTO;
import itson.rutappdto.DetallesPagoDTO;
import itson.rutappdto.UsuarioDTO;
import itson.rutappdto.ViajeDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author chris
 */
public class ControlNegocio {

    private List<AsientoAsignadoDTO> asientosAsignados = new ArrayList<>();

    IConsultarDisponibilidad consultarDisponibilidad = new FachadaConsultarDisponibilidad();
    IComprarBoleto comprarBoleto = new ComprarBoleto();

    //VARIABLES PARA GUARDAR LOS DATOS DEL BOLETO 
    private String origenSeleccionado;
    private String destinoSeleccionado;
    private String horaSalidaSeleccionada;
    private LocalDate fechaSeleccionada;
    private Double precioSeleccionado;
    private String duracionSeleccionada;
    private CamionDTO camionSeleccionado;
    //private UsuarioDTO usuarioActual;
    private List<AsientoBoletoDTO> asientosSeleccionados;

    private static ControlNegocio instancia;

    public ControlNegocio() {
    }

    public static ControlNegocio getInstancia() {
        if (instancia == null) {
            instancia = new ControlNegocio();
        }
        return instancia;
    }

    /**
     * Método que se comunica con el subsistmea y regresa la lista de destinos.
     *
     * @param origen De donde se parte.
     * @return
     */
    public List<String> obtenerDestinosDisponibles(String origen) {
        List<String> destinos = consultarDisponibilidad.consultarDestinos(origen);
        if (destinos.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No hay destinos para este parametro",
                    "Sin coincidencias", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return destinos;
    }

    public List<String> obtenerOrigenesDisponibles() {
        return Arrays.asList("Huatabampo", "Los mochis", "Tucson Az.");
    }

    public List<ViajeDTO> obtenerListaViajes(String origen, String Destino, LocalDate fecha) {
        List<ViajeDTO> viajes = consultarDisponibilidad.consultarViajesDisponibles(origen, Destino, fecha);
        if (viajes.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No se encontraron viajes para estos parametros",
                    "Sin coincidencias", JOptionPane.ERROR_MESSAGE);
            return null;
        } else {
            return viajes;
        }
    }

    public List<ViajeDTO> obtenerViajesDisponibles(String origen, String Destino, LocalDate fecha) {
        return null;
    }

    ;
    

    public void comprarBoleto() {

    }

    public void obtenerCamioneesDisponibles() {

    }

    public void obtenerBoleto() {

    }

    public void obtenerDatosCompra() {

    }

    public void validarFecha() {

    }

    public void validarPago() {

    }

    public void obtenerFechaDisponible() {

    }

    public void obtenerMetodoPago() {

    }

    public void obtenerDatos() {

    }

    public void obtenerMonedero() {

    }

    public void cancelarAsientos() {

    }

    public void apartarAsiento(AsientoDTO asiento) throws SeleccionAsientoException {
        if (asiento == null) {
            throw new SeleccionAsientoException("Error de asiento");
        }
        ControlSeleccionAsiento.getInstancia().apartarAsiento(asiento);
    }

    public void iniciarContador() {

    }

    public void registrarDetallesBoleto() {

    }

    public List<AsientoDTO> obtenerAsientos(CamionDTO camion) throws CompraBoletoException {
        return consultarDisponibilidad.consultarAsientosDisponibles(camion);

    }

    public void guardarAsientosAsignados(List<AsientoAsignadoDTO> lista) {
        this.asientosAsignados = lista;
    }

    public List<AsientoAsignadoDTO> obtenerAsientosAsignados() {
        return this.asientosAsignados;
    }

    //METODO PARA GUARDAR LOS ATRIBUTOS DEL VIAJE
    public void guardarBusqueda(String origen, String destino, LocalDate fecha) {
        this.origenSeleccionado = origen;
        this.destinoSeleccionado = destino;
        this.fechaSeleccionada = fecha;
    }

    public String getOrigenSeleccionado() {
        return origenSeleccionado;
    }

    public String getDestinoSeleccionado() {
        return destinoSeleccionado;
    }

    public LocalDate getFechaSeleccionada() {
        return fechaSeleccionada;
    }

    public List<AsientoAsignadoDTO> getAsientosAsignados() {
        return asientosAsignados;
    }

    public void setAsientosAsignados(List<AsientoAsignadoDTO> asientosAsignados) {
        this.asientosAsignados = asientosAsignados;
    }

    public String getHoraSalidaSeleccionada() {
        return horaSalidaSeleccionada;
    }

    public void setHoraSalidaSeleccionada(String horaSalidaSeleccionada) {
        this.horaSalidaSeleccionada = horaSalidaSeleccionada;
    }

    public Double getPrecioSeleccionado() {
        return precioSeleccionado;
    }

    public void setPrecioSeleccionado(Double precioSeleccionado) {
        this.precioSeleccionado = precioSeleccionado;
    }

    public String getDuracionSeleccionada() {
        return duracionSeleccionada;
    }

    public void setDuracionSeleccionada(String duracionSeleccionada) {
        this.duracionSeleccionada = duracionSeleccionada;
    }

    public CamionDTO getCamionSeleccionado() {
        return camionSeleccionado;
    }

    public void setCamionSeleccionado(CamionDTO camionSeleccionado) {
        this.camionSeleccionado = camionSeleccionado;
    }

    public List<AsientoBoletoDTO> getAsientosSeleccionados() {
        return asientosSeleccionados;
    }

    public void setAsientosSeleccionados(List<AsientoBoletoDTO> asientosSeleccionados) {
        this.asientosSeleccionados = asientosSeleccionados;
    }

public boolean comprarBoleto(DetallesPagoDTO detallesPago, UsuarioDTO usuarioDTO) throws CompraBoletoException, PagoBoletoException {
    System.out.println("Paso 1: Validando detalles de pago");

    if (detallesPago == null) {
        throw new CompraBoletoException("Detalles de pago no válidos.");
    }

    boolean pagoExitoso;

    System.out.println("Método de pago seleccionado: " + detallesPago.getMetodoPago());

    if ("Tarjeta".equals(detallesPago.getMetodoPago()) && detallesPago.getDetallesTarjeta() != null) {
        System.out.println("PAGO 1 LLEGO - Tarjeta");
        pagoExitoso = comprarBoleto.procesarCompraDos(detallesPago, usuarioDTO);
        System.out.println(pagoExitoso);
    } else if ("Monedero".equals(detallesPago.getMetodoPago())) {
        System.out.println("PAGO 1 LLEGO - Monedero");
        pagoExitoso = comprarBoleto.procesarCompra(detallesPago, usuarioDTO);
    } else {
        throw new CompraBoletoException("Método de pago no válido.");
    }

    if (pagoExitoso) {
        System.out.println("Compra exitosa.");
        return true;
    } else {
        throw new CompraBoletoException("El pago no fue aprobado.");
    }
}


}
