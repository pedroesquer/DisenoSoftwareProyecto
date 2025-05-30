/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Frames.AgregarReseñas;
import itson.rutappdto.CamionDTO;
import itson.rutappdto.ViajeDTO;
import Frames.AsientosDisponibles;
import Frames.BuscarViaje;
import Frames.ComprarBoleto;
import Frames.InicioSesion;
import Frames.MainMenu;
import Frames.MisBoletos;
import Frames.Reseñas;
import Frames.ResumenCompra;
import Frames.ViajesDisponibles;
import Interfaces.TemporizadorObserver;
import control.ControlSeleccionAsiento;
import fachada.SeleccionAsiento;
import interfaz.ISeleccionAsiento;
import itson.consultardisponibilidad.Interfaz.IConsultarDisponibilidad;
import itson.consultardisponibilidad.fachada.FachadaConsultarDisponibilidad;
import itson.rutappdto.AsientoDTO;
import itson.rutappdto.BoletoContext;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author chris
 */
public class CordinadorPresentacion {

//    private List<TemporizadorObserver> observadores = new ArrayList<>();
//
//    private Timer temporizador; // Timer de swing
//    private boolean contadorIniciado = false;
//    private final int DURACION_CONTADOR = 5 * 60 * 1000;
    private static CordinadorPresentacion instancia;
    private IConsultarDisponibilidad consultarDisponibilidad = new FachadaConsultarDisponibilidad();
    private ISeleccionAsiento seleccionAsiento = new SeleccionAsiento();
    private ControlNegocio controlNegocio = ControlNegocio.getInstancia();
    private final ControlTimer controlTimer = new ControlTimer();

    public CordinadorPresentacion() {
    }

    public static CordinadorPresentacion getInstancia() {
        if (instancia == null) {
            instancia = new CordinadorPresentacion();
        }
        return instancia;
    }

    public void abrirPantallaPrincipal() {
        MainMenu forma = new MainMenu();
        forma.setVisible(true);
    }

    public void abrirBusquedaViaje() {
        BuscarViaje forma = new BuscarViaje();
        forma.setVisible(true);
    }

//    public void abrirViajesDisponibles(List<ViajeDTO> viajes) {
//        ViajesDisponibles forma = new ViajesDisponibles(viajes);
//        forma.setVisible(true);
//    }
    public void abrirAsientosDisponibles(CamionDTO camion) {
        // Cargar los asientos reales desde MongoDB usando la fachada
        List<AsientoDTO> asientos = consultarDisponibilidad.consultarAsientosDisponibles(camion);
        camion.setListaAsiento(asientos);

        // Mostrar la ventana con los asientos ya cargados
        AsientosDisponibles formAsientosDisponibles = new AsientosDisponibles(camion);
        formAsientosDisponibles.setVisible(true);
    }

    public List<String> buscarOrigenesDisponibles() {
        return ControlNegocio.getInstancia().obtenerOrigenesDisponibles();
    }

    public List<String> buscarDestinosDisponibles(String origen) {
        return ControlNegocio.getInstancia().obtenerDestinosDisponibles(origen);
    }

    public List<AsientoDTO> consultarAsientosPorCamion(CamionDTO camion) {
        return consultarDisponibilidad.consultarAsientosDisponibles(camion);
    }

    public void mostrarViajesDisponibles(ViajeDTO viaje) {
        List<ViajeDTO> viajes = ControlNegocio.getInstancia().obtenerListaViajes(viaje);
        ViajesDisponibles ventana = new ViajesDisponibles(viajes);
        ventana.setVisible(true);
    }

    public void iniciarTemporizador(Runnable reiniciarAsientosCallback) {
        controlTimer.iniciarTemporizador(reiniciarAsientosCallback);
    }

    public void finalizarTemporizador() {
        controlTimer.finalizarTemporizador();
    }

    public void abrirResumenCompra() {
        ResumenCompra forma = new ResumenCompra();
        forma.setVisible(true);
    }

    public void abrirMetodoPago() {
        ComprarBoleto forma = new ComprarBoleto();
        forma.setVisible(true);
    }

    //TIMER
    public void agregarObservador(TemporizadorObserver obs) {
        controlTimer.agregarObservador(obs);
    }

    public void abrirLogin() {
        InicioSesion inicioSesion = new InicioSesion();
        inicioSesion.setVisible(true);
    }
    
    public void abrirMisBoletos() {
        MisBoletos misboletos = new MisBoletos();
        misboletos.setVisible(true);
    }
    
    public void abrirReseñas() {
        Reseñas reseñas = new Reseñas();
        reseñas.setVisible(true);
    }
    
    public void abrirAgregarReseñas(String numeroCamion) {
        AgregarReseñas ar = new AgregarReseñas(numeroCamion);
        ar.setVisible(true);
    }
}
