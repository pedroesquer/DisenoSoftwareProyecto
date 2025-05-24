/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frames;

import Control.ControlNegocio;
import Control.CordinadorPresentacion;
import itson.rutappdto.ReseñaDTO;
import itson.rutappdto.UsuarioDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 *
 * @author multaslokas33
 */
public class AgregarReseñas extends javax.swing.JFrame {

    /**
     * Creates new form AgregarReseñas
     */
    private String numeroCamion;
    private JLabel lblTitulo;
    private JTextArea txtComentario;
    private JButton btnEnviarComentario;
    private JButton btnRegresar;
    private JLabel[] estrellasLabels = new JLabel[5];
    private int calificacionSeleccionada = 0;
    private JLabel lblPromedio;
    private JPanel panelResenas;

    public AgregarReseñas(String numeroCamion) {
        this.numeroCamion = numeroCamion;
        setTitle("Agregar Reseña - " + numeroCamion);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 550);
        setLocationRelativeTo(null);
        agregarComponentesManual();
        cargarReseñasExistentes();
    }

    private void agregarComponentesManual() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(Color.WHITE);

        JLabel lblEncabezado = new JLabel("RUTAPP", SwingConstants.CENTER);
        lblEncabezado.setFont(new Font("SansSerif", Font.BOLD, 32));
        lblEncabezado.setOpaque(true);
        lblEncabezado.setBackground(new Color(255, 201, 98));
        lblEncabezado.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblEncabezado.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        panelPrincipal.add(lblEncabezado);

        lblTitulo = new JLabel("¿Qué te pareció el camión " + numeroCamion + "?", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(Box.createVerticalStrut(15));
        panelPrincipal.add(lblTitulo);

        lblPromedio = new JLabel("Calificación promedio: -", SwingConstants.CENTER);
        lblPromedio.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblPromedio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblPromedio);

        JPanel panelEstrellas = new JPanel();
        panelEstrellas.setBackground(Color.WHITE);
        panelEstrellas.setAlignmentX(Component.CENTER_ALIGNMENT);
        for (int i = 0; i < 5; i++) {
            final int index = i + 1;
            estrellasLabels[i] = new JLabel();
            estrellasLabels[i].setIcon(new ImageIcon("src/main/java/img/estrella_vacia.png"));
            estrellasLabels[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            estrellasLabels[i].addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    calificacionSeleccionada = index;
                    actualizarEstrellas();
                }
            });
            panelEstrellas.add(estrellasLabels[i]);
        }
        actualizarEstrellas();
        panelPrincipal.add(panelEstrellas);

        panelPrincipal.add(Box.createVerticalStrut(10));
        JLabel lblComentario = new JLabel("Comentario:", SwingConstants.CENTER);
        lblComentario.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblComentario);

        txtComentario = new JTextArea(4, 40);
        JScrollPane scrollComentario = new JScrollPane(txtComentario);
        panelPrincipal.add(scrollComentario);

        btnEnviarComentario = new JButton("Añadir Comentario");
        btnEnviarComentario.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEnviarComentario.addActionListener(e -> enviarReseña());
        panelPrincipal.add(Box.createVerticalStrut(10));
        panelPrincipal.add(btnEnviarComentario);

        JLabel lblReseñas = new JLabel("Reseñas existentes:", SwingConstants.CENTER);
        lblReseñas.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(Box.createVerticalStrut(10));
        panelPrincipal.add(lblReseñas);

        panelResenas = new JPanel();
        panelResenas.setLayout(new BoxLayout(panelResenas, BoxLayout.Y_AXIS));
        panelResenas.setBackground(Color.WHITE);
        JScrollPane scrollReseñas = new JScrollPane(panelResenas);
        scrollReseñas.setPreferredSize(new Dimension(550, 150));
        panelPrincipal.add(scrollReseñas);

        btnRegresar = new JButton("Regresar");
        btnRegresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegresar.setBackground(new Color(255, 201, 98));
        btnRegresar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnRegresar.addActionListener(e -> {
            new Reseñas().setVisible(true);
            dispose();
        });
        panelPrincipal.add(Box.createVerticalStrut(10));
        panelPrincipal.add(btnRegresar);

        getContentPane().add(panelPrincipal);
    }

    private void actualizarEstrellas() {
        int ancho = 40, alto = 40;

        for (int i = 0; i < 5; i++) {
            String path = i < calificacionSeleccionada
                    ? "src/main/java/img/estrella_llena.png"
                    : "src/main/java/img/estrella_vacia.png";

            ImageIcon original = new ImageIcon(path);
            Image imagenEscalada = original.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            estrellasLabels[i].setIcon(new ImageIcon(imagenEscalada));
        }
    }

    private void enviarReseña() {
        try {
            // Construir el DTO con los datos ingresados
            ReseñaDTO reseña = new ReseñaDTO();
            reseña.setNumeroCamion(numeroCamion);
            reseña.setCalificacion(calificacionSeleccionada);
            reseña.setComentario(txtComentario.getText().trim());

            // Delegar validación y guardado al negocio
            ControlNegocio.getInstancia().agregarReseña(reseña);

            // Si no lanza excepción, todo fue bien:
            JOptionPane.showMessageDialog(this, "¡Gracias por tu reseña!");
            txtComentario.setText("");
            calificacionSeleccionada = 0;
            actualizarEstrellas();
            cargarReseñasExistentes();

        } catch (Exception e) {
            if (e.getMessage().contains("El usuario ya ha enviado 3 reseñas")) {
                JOptionPane.showMessageDialog(this, "Solo puedes dejar hasta 3 reseñas por camión.", "Límite alcanzado", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error al enviar reseña: " + e.getMessage());
            }
        }
    }

    private void cargarReseñasExistentes() {
        panelResenas.removeAll();
        List<ReseñaDTO> reseñas = ControlNegocio.getInstancia().obtenerReseñasPorCamion(numeroCamion);
        UsuarioDTO usuarioActual = ControlNegocio.getInstancia().obtenerUsuarioActivo();
        double suma = 0;

        for (ReseñaDTO r : reseñas) {
            suma += r.getCalificacion();

            JPanel tarjeta = new JPanel(new BorderLayout());
            tarjeta.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            tarjeta.setBackground(new Color(240, 240, 240));

            JTextArea contenido = new JTextArea("★".repeat((int) r.getCalificacion()) + "\n"
                    + r.getNombreUsuario() + ": " + r.getComentario());
            contenido.setEditable(false);
            contenido.setBackground(new Color(240, 240, 240));

            tarjeta.add(contenido, BorderLayout.CENTER);

            // Mostrar botón solo si el usuario es el autor
            if (usuarioActual.getNombre().equals(r.getNombreUsuario())) {
                long minutos = (new Date().getTime() - r.getFecha().getTime()) / (60 * 1000);

                if (minutos <= 10) {
                    JButton btnEliminar = new JButton("Eliminar");

                    btnEliminar.addActionListener(e -> {
                        boolean eliminado = ControlNegocio.getInstancia().eliminarReseña(r.getId());
                        if (eliminado) {
                            JOptionPane.showMessageDialog(this, "Reseña eliminada.");
                            cargarReseñasExistentes(); // Recargar
                        } else {
                            JOptionPane.showMessageDialog(this, "Solo puedes eliminar tus reseñas dentro de los primeros 10 minutos.");
                        }
                    });

                    tarjeta.add(btnEliminar, BorderLayout.EAST);
                }
            }

                panelResenas.add(Box.createVerticalStrut(5));
                panelResenas.add(tarjeta);
            }

            double promedio = reseñas.isEmpty() ? 0 : suma / reseñas.size();
            lblPromedio.setText(String.format("Calificación promedio: %.1f ★", promedio));

            panelResenas.revalidate();
            panelResenas.repaint();
        }

        /**
         * This method is called from within the constructor to initialize the
         * form. WARNING: Do NOT modify this code. The content of this method is
         * always regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 588, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 472, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
