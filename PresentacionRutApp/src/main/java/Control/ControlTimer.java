/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Interfaces.TemporizadorObserver;
import itson.rutappdto.BoletoContext;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author multaslokas33
 */
public class ControlTimer {

    private Timer temporizador;
    private boolean contadorIniciado = false;
    private final int DURACION_CONTADOR = 5 * 1000 * 60 ; // 5 minutos

    private List<TemporizadorObserver> observadores = new ArrayList<>();

    private static ControlTimer instancia;

    public static ControlTimer getInstancia() {
        if (instancia == null) {
            instancia = new ControlTimer();
        }
        return instancia;
    }

    public void iniciarTemporizador(Runnable reiniciarCallback) {
        if (contadorIniciado) {
            return;
        }

        JOptionPane.showMessageDialog(null, "Tienes 5 minutos para realizar la compra");
        contadorIniciado = true;

        temporizador = new Timer(DURACION_CONTADOR, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                temporizador.stop();
                contadorIniciado = false;

                //JOptionPane.showMessageDialog(null, "El tiempo se ha acabado. Inténtelo de nuevo.");
                BoletoContext.limpiarBoleto();
                if (reiniciarCallback != null) {
                    reiniciarCallback.run();
                }
                notificarObservadores();
            }
        });
        temporizador.setRepeats(false);
        temporizador.start();
    }

    public void finalizarTemporizador() {
        if (temporizador != null && temporizador.isRunning()) {
            temporizador.stop();
        }
        contadorIniciado = false;
    }

    public void agregarObservador(TemporizadorObserver obs) {
        observadores.add(obs);
    }

    private void notificarObservadores() {
        System.out.println("Notificando a " + observadores.size() + " observadores");
        for (TemporizadorObserver obs : observadores) {
            System.out.println("Notificando a instancia: " + obs.hashCode());
            obs.tiempoAgotado();
        }
    }

    public void limpiarObservadores() {
        observadores.clear();
    }

}
