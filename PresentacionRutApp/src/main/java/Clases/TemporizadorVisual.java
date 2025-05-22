/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author chris
 */
public class TemporizadorVisual {

    private static TemporizadorVisual instancia;
    private JLabel etiqueta;

    private TemporizadorVisual() {
    }

    public static TemporizadorVisual getInstancia() {
        if (instancia == null) {
            instancia = new TemporizadorVisual();
        }
        return instancia;
    }

    public void registrarEtiqueta(JLabel etiqueta) {
        this.etiqueta = etiqueta;
    }

    public void actualizarEtiqueta(int segundosRestantes) {
        if (etiqueta != null) {
            long minutos = segundosRestantes / 60;
            long segundos = segundosRestantes % 60;
            SwingUtilities.invokeLater(() -> {
                etiqueta.setText(String.format("Tiempo restante: %02d:%02d", minutos, segundos));
            });
        }
    }
}


