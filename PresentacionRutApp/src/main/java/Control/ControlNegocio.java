/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Ex.CompraBoletoException;
import Fachada.ComprarBoleto;
import Interfaz.IComprarBoleto;
import control.ControlSeleccionAsiento;
import excepciones.NegocioException;
import excepciones.PagoBoletoException;
import excepciones.SeleccionAsientoException;
import fachada.FCancelarCompra;
import fachada.FUsuarioActivo;
import fachada.SeleccionAsiento;
import fachada.fachadaResenias;
import interfaz.ICancelarCompra;
import interfaz.IResenias;
import interfaz.ISeleccionAsiento;
import interfaz.IUsuarioActivo;
import itson.consultardisponibilidad.Interfaz.IConsultarDisponibilidad;
import itson.consultardisponibilidad.fachada.FachadaConsultarDisponibilidad;
import Exception.ReagendaBoletoException;
import Fachada.FacahdaReagendaBoleto;
import Interfaz.IReagendaBoleto;
import itson.rutappbo.implementaciones.ComprasBO;
import itson.rutappdto.AsientoAsignadoDTO;
import itson.rutappdto.AsientoBoletoDTO;
import itson.rutappdto.AsientoDTO;
import itson.rutappdto.BoletoContext;
import itson.rutappdto.CamionDTO;
import itson.rutappdto.CompraDTO;
import itson.rutappdto.DetallesPagoDTO;
import itson.rutappdto.ReseñaDTO;
import itson.rutappdto.UsuarioDTO;
import itson.rutappdto.ViajeDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author chris
 */
public class ControlNegocio {

    private List<AsientoAsignadoDTO> asientosAsignados = new ArrayList<>();
    IConsultarDisponibilidad consultarDisponibilidad;
    ICancelarCompra cancelarCompra;
    IComprarBoleto comprarBoleto;
    ISeleccionAsiento seleccionAsiento;
    IResenias reseñaa;
    private final IUsuarioActivo fachadaUsuarioActivo;
    private final IReagendaBoleto fachadaReagendaBoleto;

    private String origenSeleccionado;
    private String destinoSeleccionado;
    private String horaSalidaSeleccionada;
    private LocalDateTime fechaSeleccionada;
    private Double precioSeleccionado;
    private String duracionSeleccionada;
    private CamionDTO camionSeleccionado;
    private List<AsientoBoletoDTO> asientosSeleccionados;

    public static boolean MODO_REAGENDAMIENTO = false;
    public static String ID_COMPRA_PARA_REAGENDAR = null;
    public static UsuarioDTO USUARIO_REAGENDANDO = null;

    private static ControlNegocio instancia;

    /**
     * Constructor privado para el patrón Singleton. Inicializa las fachadas.
     */
    public ControlNegocio() {
        this.fachadaUsuarioActivo = new FUsuarioActivo(); //
        this.consultarDisponibilidad = new FachadaConsultarDisponibilidad(); //
        this.cancelarCompra = new FCancelarCompra(); //
        this.comprarBoleto = new ComprarBoleto(); //
        this.seleccionAsiento = new SeleccionAsiento(); //
        this.reseñaa = new fachadaResenias(); //
        this.fachadaReagendaBoleto = new FacahdaReagendaBoleto();
    }

    /**
     * Obtiene la instancia única de ControlNegocio (Patrón Singleton).
     * @return 
     */
    public static ControlNegocio getInstancia() {
        if (instancia == null) {
            instancia = new ControlNegocio();
        }
        return instancia;
    }

    /**
     * Obtiene la lista de destinos disponibles para un origen dado.
     * @param origen
     * @return 
     */
    public List<String> obtenerDestinosDisponibles(String origen) {
        List<String> destinos = consultarDisponibilidad.consultarDestinos(origen); //
        if (destinos.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No hay destinos para este parametro",
                    "Sin coincidencias", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return destinos;
    }

    /**
     * Obtiene una lista predefinida de orígenes disponibles.
     * @return 
     */
    public List<String> obtenerOrigenesDisponibles() {
        return Arrays.asList("Ciudad Obregon", "Hermosillo", "Guaymas", "Navojoa", "Nogales");
    }

    /**
     * Obtiene la lista de viajes disponibles según los criterios de búsqueda.
     * @param viaje
     * @return 
     */
    public List<ViajeDTO> obtenerListaViajes(ViajeDTO viaje) {
        List<ViajeDTO> viajes = consultarDisponibilidad.consultarViajesDisponibles(viaje); //
        if (viajes.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No se encontraron viajes para estos parametros",
                    "Sin coincidencias", JOptionPane.ERROR_MESSAGE);
            return null;
        } else {
            return viajes;
        }
    }

    /**
     * Aparta un asiento, cambiando su estado a ocupado (lógica en
     * SeleccionAsiento).
     * @param asiento
     * @throws excepciones.SeleccionAsientoException
     */
    public void apartarAsiento(AsientoDTO asiento) throws SeleccionAsientoException { //
        if (asiento == null) {
            throw new SeleccionAsientoException("Error de asiento");
        }
        ControlSeleccionAsiento.getInstancia().apartarAsiento(asiento); //
    }

    /**
     * Obtiene la lista de asientos para un camión dado.
     * @param camion
     * @return 
     * @throws Ex.CompraBoletoException 
     */
    public List<AsientoDTO> obtenerAsientos(CamionDTO camion) throws CompraBoletoException { //
        return consultarDisponibilidad.consultarAsientosDisponibles(camion); //
    }

    /**
     * Guarda la lista de asientos asignados (nombre de pasajero y número de
     * asiento).
     * @param lista
     */
    public void guardarAsientosAsignados(List<AsientoAsignadoDTO> lista) {
        this.asientosAsignados = lista;
    }

    /**
     * Obtiene la lista de asientos asignados actualmente.
     * @return 
     */
    public List<AsientoAsignadoDTO> obtenerAsientosAsignados() {
        return this.asientosAsignados;
    }

    /**
     * Guarda los detalles de la búsqueda de viaje actual (origen, destino,
     * fecha).
     * @param origen
     * @param destino
     * @param fecha
     */
    public void guardarBusqueda(String origen, String destino, LocalDateTime fecha) {
        this.origenSeleccionado = origen;
        this.destinoSeleccionado = destino;
        this.fechaSeleccionada = fecha;
    }

    /**
     * Obtiene el origen seleccionado en la búsqueda actual.
     * @return 
     */
    public String getOrigenSeleccionado() {
        return origenSeleccionado;
    }

    /**
     * Obtiene el destino seleccionado en la búsqueda actual.
     * @return 
     */
    public String getDestinoSeleccionado() {
        return destinoSeleccionado;
    }

    /**
     * Obtiene la fecha seleccionada en la búsqueda actual.
     * @return 
     */
    public LocalDateTime getFechaSeleccionada() {
        return fechaSeleccionada;
    }

    /**
     * Obtiene la lista de asientos asignados actualmente.
     * @return 
     */
    public List<AsientoAsignadoDTO> getAsientosAsignados() {
        return asientosAsignados;
    }

    /**
     * Establece la lista de asientos asignados.
     * @param asientosAsignados
     */
    public void setAsientosAsignados(List<AsientoAsignadoDTO> asientosAsignados) {
        this.asientosAsignados = asientosAsignados;
    }

    /**
     * Obtiene la hora de salida seleccionada.
     * @return 
     */
    public String getHoraSalidaSeleccionada() {
        return horaSalidaSeleccionada;
    }

    /**
     * Establece la hora de salida seleccionada.
     * @param horaSalidaSeleccionada
     */
    public void setHoraSalidaSeleccionada(String horaSalidaSeleccionada) {
        this.horaSalidaSeleccionada = horaSalidaSeleccionada;
    }

    /**
     * Obtiene el precio seleccionado.
     * @return 
     */
    public Double getPrecioSeleccionado() {
        return precioSeleccionado;
    }

    /**
     * Establece el precio seleccionado.
     * @param precioSeleccionado
     */
    public void setPrecioSeleccionado(Double precioSeleccionado) {
        this.precioSeleccionado = precioSeleccionado;
    }

    /**
     * Obtiene la duración seleccionada.
     * @return 
     */
    public String getDuracionSeleccionada() {
        return duracionSeleccionada;
    }

    /**
     * Establece la duración seleccionada.
     * @param duracionSeleccionada
     */
    public void setDuracionSeleccionada(String duracionSeleccionada) {
        this.duracionSeleccionada = duracionSeleccionada;
    }

    /**
     * Obtiene el camión seleccionado.
     * @return 
     */
    public CamionDTO getCamionSeleccionado() {
        return camionSeleccionado;
    }

    /**
     * Establece el camión seleccionado.
     * @param camionSeleccionado
     */
    public void setCamionSeleccionado(CamionDTO camionSeleccionado) {
        this.camionSeleccionado = camionSeleccionado;
    }

    /**
     * Obtiene la lista de asientos de boleto seleccionados.
     * @return 
     */
    public List<AsientoBoletoDTO> getAsientosSeleccionados() {
        return asientosSeleccionados;
    }

    /**
     * Establece la lista de asientos de boleto seleccionados.
     * @param asientosSeleccionados
     */
    public void setAsientosSeleccionados(List<AsientoBoletoDTO> asientosSeleccionados) {
        this.asientosSeleccionados = asientosSeleccionados;
    }

    /**
     * Procesa la compra de un boleto, incluyendo el pago y el registro de la
     * compra.
     * @param detallesPago
     * @param usuarioDTO
     * @return 
     * @throws Ex.CompraBoletoException
     * @throws excepciones.PagoBoletoException
     */
    public boolean comprarBoleto(DetallesPagoDTO detallesPago, UsuarioDTO usuarioDTO) throws CompraBoletoException, PagoBoletoException { //
        boolean pagoExitoso = comprarBoleto.procesarCompra(detallesPago, usuarioDTO); //
        if (pagoExitoso) {
            try {
                seleccionAsiento.ocuparAsientos(BoletoContext.getBoleto()); //
                new ComprasBO().agregarCompra(usuarioDTO, BoletoContext.getBoleto().getViaje(), BoletoContext.getBoleto().getListaAsiento()); //
            } catch (NegocioException ex) { //
                Logger.getLogger(ControlNegocio.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Error al apartar los asientos, revisar código");
                return false; // Indicar que la compra falló en este punto
            }
            return true;
        } else {
            // La fachada de comprarBoleto debería haber lanzado una excepción si el pago no fue aprobado.
            // Si devuelve false sin excepción, se podría lanzar una aquí.
            throw new CompraBoletoException("El pago no fue aprobado.");
        }
    }

    /**
     * Inicia la sesión del usuario en el sistema.
     * @param usuarioDTO
     */
    public void iniciarSesion(UsuarioDTO usuarioDTO) {
        fachadaUsuarioActivo.iniciarSesion(usuarioDTO); //
    }

    /**
     * Obtiene el usuario actualmente activo en la sesión.
     * @return 
     */
    public UsuarioDTO obtenerUsuarioActivo() {
        return fachadaUsuarioActivo.obtenerUsuarioActual(); //
    }

    /**
     * Verifica si hay una sesión de usuario activa.
     * @return 
     */
    public boolean haySesionActiva() {
        return fachadaUsuarioActivo.haySesionActiva(); //
    }

    /**
     * Cierra la sesión del usuario actual.
     */
    public void cerrarSesion() {
        fachadaUsuarioActivo.cerrarSesion(); //
    }

    /**
     * Obtiene la lista de compras no vencidas para el usuario especificado.
     * @param usuario
     * @return 
     */
    public List<CompraDTO> obtenerComprasUsuario(UsuarioDTO usuario) {
        return cancelarCompra.obtenerCompras(usuario); //
    }

    /**
     * Agrega una nueva reseña al sistema.
     * @param reseña
     * @throws java.lang.Exception
     */
    public void agregarReseña(ReseñaDTO reseña) throws Exception {
        reseñaa.agregarReseña(reseña); //
    }

    /**
     * Obtiene todas las reseñas asociadas a un número de camión.
     * @param numeroCamion
     * @return 
     */
    public List<ReseñaDTO> obtenerReseñasPorCamion(String numeroCamion) {
        return reseñaa.obtenerReseñasPorCamion(numeroCamion); //
    }

    /**
     * Elimina una reseña del sistema.
     * @param idReseña
     * @return 
     */
    public boolean eliminarReseña(String idReseña) {
        return reseñaa.eliminarReseña(idReseña); //
    }

    /**
     * Cancela una compra utilizando la fachada del subsistema CancelarCompra.
     * @param compra
     */
    public void cancelarCompra(CompraDTO compra) { //
        cancelarCompra.eliminarCompra(compra); //
    }

    /**
     * Inicia el proceso de reagendamiento guardando el ID de la compra original
     * y el usuario, y activando el modo de reagendamiento.
     * @param idCompraOriginal
     * @param usuarioOriginal
     */
    public void iniciarProcesoReagendamiento(String idCompraOriginal, UsuarioDTO usuarioOriginal) {
        MODO_REAGENDAMIENTO = true;
        ID_COMPRA_PARA_REAGENDAR = idCompraOriginal;
        USUARIO_REAGENDANDO = usuarioOriginal;
    }

    /**
     * Finaliza el proceso de reagendamiento llamando a la lógica del módulo
     * ReagendaBoleto. Construye el CompraDTO necesario a partir del nuevo viaje
     * y asientos seleccionados.
     * @param nuevoViaje
     * @param nuevosAsientos
     * @return 
     */
    public boolean finalizarProcesoReagendamiento(ViajeDTO nuevoViaje, List<AsientoBoletoDTO> nuevosAsientos) {
        if (!MODO_REAGENDAMIENTO || ID_COMPRA_PARA_REAGENDAR == null || USUARIO_REAGENDANDO == null) {
            JOptionPane.showMessageDialog(null, "Error: No se está en modo de reagendamiento o falta información original.", "Error de Flujo", JOptionPane.ERROR_MESSAGE);
            resetearModoReagendamiento();
            return false;
        }

        double precioTotalNuevoBoleto = nuevoViaje.getPrecio() * nuevosAsientos.size();
        CompraDTO compraActualizada = new CompraDTO(USUARIO_REAGENDANDO,precioTotalNuevoBoleto,nuevosAsientos, nuevoViaje );

        boolean resultado = false;
        try {
            resultado = fachadaReagendaBoleto.reagendarBoleto(ID_COMPRA_PARA_REAGENDAR, compraActualizada);
            if (resultado) {
                JOptionPane.showMessageDialog(null, "¡Boleto reagendado exitosamente!", "Reagendamiento Exitoso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo completar el reagendamiento.", "Fallo en Reagendamiento", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ReagendaBoletoException e) {
            JOptionPane.showMessageDialog(null, "Error al reagendar: " + e.getMessage(), "Error de Reagendamiento", JOptionPane.ERROR_MESSAGE);
            resultado = false;
        } finally {
            resetearModoReagendamiento();
        }
        return resultado;
    }

    /**
     * Resetea las variables de estado utilizadas para el modo de
     * reagendamiento.
     */
    public void resetearModoReagendamiento() {
        MODO_REAGENDAMIENTO = false;
        ID_COMPRA_PARA_REAGENDAR = null;
        USUARIO_REAGENDANDO = null;
    }
}
