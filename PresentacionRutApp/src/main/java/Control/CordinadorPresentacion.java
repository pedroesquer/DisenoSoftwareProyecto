/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import itson.rutappdto.CamionDTO;
import itson.rutappdto.ViajeDTO;
import Frames.AsientosDisponibles;
import Frames.BuscarViaje;
import Frames.ComprarBoleto;
import Frames.MainMenu;
import Frames.ResumenCompra;
import Frames.ViajesDisponibles;
import Interfaces.TemporizadorObserver;
import control.ControlSeleccionAsiento;
import fachada.SeleccionAsiento;
import interfaz.ISeleccionAsiento;
import itson.consultardisponibilidad.Interfaz.IConsultarDisponibilidad;
import itson.consultardisponibilidad.fachada.FachadaConsultarDisponibilidad;
import itson.rutappdto.BoletoContext;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author chris
 */
public class CordinadorPresentacion {

    private List<TemporizadorObserver> observadores = new ArrayList<>();

    private Timer temporizador; // Timer de swing
    private boolean contadorIniciado = false;
    private final int DURACION_CONTADOR = 5 * 60 * 1000;

    private IConsultarDisponibilidad consultarDisponibilidad = new FachadaConsultarDisponibilidad();
    private ISeleccionAsiento seleccionAsiento = new SeleccionAsiento();

    private static CordinadorPresentacion instancia;

    private ControlNegocio controlNegocio = ControlNegocio.getInstancia();

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
        AsientosDisponibles formAsientosDisponibles = new AsientosDisponibles(camion);
        formAsientosDisponibles.setVisible(true);

    }

    public List<String> buscarOrigenesDisponibles() {
        return ControlNegocio.getInstancia().obtenerOrigenesDisponibles();
    }

    public List<String> buscarDestinosDisponibles(String origen) {
        return ControlNegocio.getInstancia().obtenerDestinosDisponibles(origen);
    }

    public void mostrarViajesDisponibles(String origen, String destino, LocalDate fecha) {
        List<ViajeDTO> viajes = ControlNegocio.getInstancia().obtenerListaViajes(origen, destino, fecha);
        ViajesDisponibles ventana = new ViajesDisponibles(viajes);
        ventana.setVisible(true);
    }

    public void iniciarTemporizador(Runnable reiniciarAsientosCallback) {
        seleccionAsiento.iniciarTemporizador(reiniciarAsientosCallback);
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
        observadores.add(obs);
    }

    //TIMER
    private void notificarObservadores() {
        for (TemporizadorObserver obs : observadores) {
            obs.tiempoAgotado();
        }
    }
}
