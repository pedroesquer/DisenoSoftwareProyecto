/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frames;

import Control.ControlNegocio;
import Control.CordinadorPresentacion;
import itson.rutappdto.CompraDTO;
import itson.rutappdto.UsuarioDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/**
 *
 * @author multaslokas33
 */
public class Reseñas extends javax.swing.JFrame {

    private JPanel panelCamiones;

    /**
     * Creates new form Reseñas
     */
    public Reseñas() {
        initComponents();
        personalizarComponentes();
        cargarCamionesConCompras();
        setTitle("Reseñas");
    }

    private void cargarCamionesConCompras() {
        UsuarioDTO usuario = ControlNegocio.getInstancia().obtenerUsuarioActivo();
        List<CompraDTO> compras = ControlNegocio.getInstancia().obtenerComprasUsuario(usuario);

        Set<String> camionesUnicos = new HashSet<>();

        for (CompraDTO compra : compras) {
            String numCamion = compra.getViaje().getCamion().getNumeroCamion();
            camionesUnicos.add(numCamion);
        }

        panelCamiones.removeAll();

        if (camionesUnicos.isEmpty()) {
            JLabel lbl = new JLabel("No tienes camiones para reseñar.");
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelCamiones.add(lbl);
        } else {
            for (String camion : camionesUnicos) {
                JPanel panel = crearPanelCamion(camion);
                panelCamiones.add(panel);
                panelCamiones.add(Box.createVerticalStrut(10));
            }
        }

        panelCamiones.revalidate();
        panelCamiones.repaint();
    }

    private void personalizarComponentes() {
        this.setLayout(new BorderLayout());

        // Panel encabezado
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(255, 201, 98));
        panelHeader.setPreferredSize(new Dimension(getWidth(), 60));
        JLabel lblHeader = new JLabel("RUTAPP", SwingConstants.CENTER);
        lblHeader.setFont(new Font("SansSerif", Font.BOLD, 32));
        lblHeader.setForeground(Color.BLACK);
        panelHeader.setLayout(new BorderLayout());
        panelHeader.add(lblHeader, BorderLayout.CENTER);

        // Panel de camiones con scroll
        panelCamiones = new JPanel();
        panelCamiones.setLayout(new BoxLayout(panelCamiones, BoxLayout.Y_AXIS));
        panelCamiones.setBackground(Color.WHITE);
        panelCamiones.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JScrollPane scroll = new JScrollPane(panelCamiones);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        // Botón regresar
        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.setBackground(new Color(255, 201, 98));
        btnRegresar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnRegresar.setFocusPainted(false);
        btnRegresar.setPreferredSize(new Dimension(100, 35));

        btnRegresar.addActionListener(e -> {
            CordinadorPresentacion.getInstancia().abrirPantallaPrincipal();
            this.dispose();
        });

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setBackground(Color.WHITE);
        panelInferior.add(btnRegresar);

        // Añadir a frame
        this.add(panelHeader, BorderLayout.NORTH);
        this.add(scroll, BorderLayout.CENTER);
        this.add(panelInferior, BorderLayout.SOUTH);

        this.setSize(700, 500);
        this.setLocationRelativeTo(null);
    }

    private JPanel crearPanelCamion(String camion) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        panel.setBackground(new Color(230, 240, 255));
        panel.setPreferredSize(new Dimension(500, 80));

        Color baseColor = panel.getBackground();
        Color hoverColor = new Color(200, 220, 255);

        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                panel.setBackground(hoverColor);
                panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                panel.setBackground(baseColor);
                panel.setCursor(Cursor.getDefaultCursor());
            }
        });

        JLabel titulo = new JLabel("Autobús " + camion, SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        panel.add(titulo, BorderLayout.NORTH);

        JButton boton = new JButton("Reseñar");
        boton.addActionListener(e -> {
            new AgregarReseñas(camion).setVisible(true);
            dispose();
        });
        JPanel wrapper = new JPanel();
        wrapper.setOpaque(false);
        wrapper.add(boton);

        panel.add(wrapper, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
