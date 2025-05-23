/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frames;

import Control.ControlNegocio;
import Control.CordinadorPresentacion;
import itson.rutappdto.AsientoBoletoDTO;
import itson.rutappdto.CompraDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import usuarioActivoManager.UsuarioActivoManager;

/**
 *
 * @author multaslokas33
 */
public class MisBoletos extends javax.swing.JFrame {

    private JPanel panelContenido;

    public MisBoletos() {
        setTitle("Mis Boletos");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(panelContenido);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        cargarBoletos();
    }

    private void cargarBoletos() {
        List<CompraDTO> compras = ControlNegocio.getInstancia()
                .obtenerComprasUsuario(UsuarioActivoManager.getInstancia().getUsuario());

        if (compras.isEmpty()) {
            JLabel sinBoletos = new JLabel("No tienes boletos comprados.", SwingConstants.CENTER);
            sinBoletos.setFont(new Font("SansSerif", Font.PLAIN, 16));
            panelContenido.add(sinBoletos);
            return;
        }

        for (CompraDTO compra : compras) {
            JPanel panel = crearPanelCompra(compra);
            panelContenido.add(panel);
            panelContenido.add(Box.createVerticalStrut(15));
        }
    }

    private JPanel crearPanelCompra(CompraDTO compra) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        Color colorNormal = new Color(230, 240, 255);
        Color colorHover = new Color(200, 220, 255); 
        panel.setBackground(colorNormal);
        panel.setPreferredSize(new Dimension(550, 160));
        panel.setMaximumSize(new Dimension(550, 160));

        // Mouse listener para efecto hover
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                panel.setBackground(colorHover);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                panel.setBackground(colorNormal);
            }
        });

        // Título centrado
        JLabel titulo = new JLabel(compra.getOrigen() + " ➡ " + compra.getDestino(), SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);

        // Detalles en dos columnas
        JPanel detalles = new JPanel(new GridLayout(2, 2));
        detalles.setOpaque(false);
        detalles.setMaximumSize(new Dimension(500, 50));
        detalles.add(new JLabel("Fecha: " + new SimpleDateFormat("yyyy-MM-dd").format(compra.getFecha())));
        detalles.add(new JLabel("Hora: " + compra.getHrSalida()));
        detalles.add(new JLabel("Camión: " + compra.getCamion().getNumeroCamion()));
        detalles.add(new JLabel("Precio: $" + compra.getPrecio()));
        panel.add(detalles);

        // Asientos + botón
        JPanel abajo = new JPanel(new BorderLayout());
        abajo.setOpaque(false);
        abajo.setMaximumSize(new Dimension(500, 30));

        StringBuilder asientosTexto = new StringBuilder("Asiento(s): ");
        for (AsientoBoletoDTO ab : compra.getListaAsiento()) {
            asientosTexto.append(ab.getAsiento().getNumero()).append(" ");
        }

        JLabel asientos = new JLabel(asientosTexto.toString());
        abajo.add(asientos, BorderLayout.WEST);

        JButton btnCancelar = new JButton("Cancelar Boleto");
        btnCancelar.setBackground(Color.RED);
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        // Aún sin lógica de cancelación, pero aquí quedaría
        abajo.add(btnCancelar, BorderLayout.EAST);

        panel.add(abajo);

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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaBoletos = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        listaBoletos.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listaBoletos);

        jButton1.setText("Regresar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        CordinadorPresentacion.getInstancia().abrirPantallaPrincipal();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> listaBoletos;
    // End of variables declaration//GEN-END:variables
}
