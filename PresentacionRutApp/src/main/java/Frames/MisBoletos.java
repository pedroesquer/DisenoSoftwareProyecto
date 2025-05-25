/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import usuarioActivoManager.UsuarioActivoManager;

/**
 *
 * @author mmax2
 */
public class MisBoletos extends javax.swing.JFrame{
    

    private JPanel panelContenido;
    private JScrollPane scrollPane; // Hacer scrollPane una variable de instancia

    /**
     * Constructor de la ventana MisBoletos.
     * Inicializa los componentes y carga los boletos del usuario.
     */
    public MisBoletos() {
        setTitle("Mis Boletos");
        setSize(650, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        ControlNegocio.getInstancia().resetearModoReagendamiento();

        panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(Color.WHITE);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        scrollPane = new JScrollPane(panelContenido);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        cargarBoletos();
    }

    /**
     * Carga y muestra los boletos del usuario en el panel de contenido.
     * Si no hay boletos, muestra un mensaje indicándolo.
     */
    private void cargarBoletos() {
        panelContenido.removeAll();

        List<CompraDTO> compras = ControlNegocio.getInstancia()
                .obtenerComprasUsuario(UsuarioActivoManager.getInstancia().getUsuario()); //

        if (compras == null || compras.isEmpty()) {
            JLabel sinBoletos = new JLabel("No tienes boletos comprados.", SwingConstants.CENTER);
            sinBoletos.setFont(new Font("SansSerif", Font.PLAIN, 18));
            sinBoletos.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JPanel panelCentrado = new JPanel(new BorderLayout());
            panelCentrado.setOpaque(false);
            panelCentrado.add(sinBoletos, BorderLayout.CENTER);
            
            panelContenido.setLayout(new BorderLayout()); // Cambiar layout para centrar
            panelContenido.add(panelCentrado, BorderLayout.CENTER);

        } else {
            panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS)); // Restaurar layout
            for (CompraDTO compra : compras) {
                if (compra.getId() == null || compra.getId().trim().isEmpty()){
                    // Opcional: loggear o manejar compras sin ID si esto no debería ocurrir.
                }
                JPanel panel = crearPanelCompra(compra);
                panelContenido.add(panel);
                panelContenido.add(Box.createVerticalStrut(15));
            }
        }

        JButton btnRegresar = new JButton("Regresar a Inicio");
        btnRegresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        personalizarBoton(btnRegresar, new Color(100, 149, 237), 14); // Usar método para personalizar
        btnRegresar.setMinimumSize(new Dimension(200, 40));
        btnRegresar.setPreferredSize(new Dimension(200, 40));
        btnRegresar.setMaximumSize(new Dimension(220, 45)); // Un poco más de flexibilidad
        
        btnRegresar.addActionListener(e -> {
            CordinadorPresentacion.getInstancia().abrirPantallaPrincipal(); //
            this.dispose();
        });

        panelContenido.add(Box.createVerticalStrut(20));
        panelContenido.add(btnRegresar);
        panelContenido.add(Box.createVerticalStrut(10));

        panelContenido.revalidate();
        panelContenido.repaint();
    }

    /**
     * Crea un panel individual para mostrar los detalles de una compra (boleto).
     * Incluye información del viaje, asientos, precio y botones para cancelar o reagendar.
     */
    private JPanel crearPanelCompra(CompraDTO compra) {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));
        Color colorNormal = new Color(248, 249, 250);
        Color colorHover = new Color(230, 240, 255);
        panelPrincipal.setBackground(colorNormal);
        panelPrincipal.setPreferredSize(new Dimension(580, 190));
        panelPrincipal.setMaximumSize(new Dimension(600, 195));
        panelPrincipal.setMinimumSize(new Dimension(570, 185));

        panelPrincipal.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelPrincipal.setBackground(colorHover);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelPrincipal.setBackground(colorNormal);
            }
        });
        
        panelPrincipal.add(Box.createVerticalStrut(10));

        JLabel titulo = new JLabel((compra.getOrigen() != null ? compra.getOrigen() : "N/A") + 
                                  "  ➔  " + 
                                  (compra.getDestino() != null ? compra.getDestino() : "N/A"), SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(titulo);
        panelPrincipal.add(Box.createVerticalStrut(8));

        JPanel detallesGrid = new JPanel(new GridLayout(0, 2, 15, 6));
        detallesGrid.setOpaque(false);
        detallesGrid.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 25));

        String fechaFormateada = (compra.getFecha() != null) ? new SimpleDateFormat("dd/MM/yyyy").format(compra.getFecha()) : "N/A";
        detallesGrid.add(new JLabel("<html><b>Fecha Compra:</b> " + fechaFormateada + "</html>"));
        detallesGrid.add(new JLabel("<html><b>Hora Salida Viaje:</b> " + (compra.getHrSalida() != null ? compra.getHrSalida() : "N/A") + "</html>"));
        
        if (compra.getCamion() != null) {
            detallesGrid.add(new JLabel("<html><b>Camión:</b> " + compra.getCamion().getNumeroCamion() + "</html>"));
        } else {
            detallesGrid.add(new JLabel("<html><b>Camión:</b> N/A</html>"));
        }
        detallesGrid.add(new JLabel("<html><b>Precio Total:</b> $" + String.format("%.2f", compra.getPrecio()) + "</html>"));
        
        StringBuilder asientosTexto = new StringBuilder("<html><b>Asiento(s):</b> ");
        if (compra.getListaAsiento() != null && !compra.getListaAsiento().isEmpty()) {
            for (int i = 0; i < compra.getListaAsiento().size(); i++) {
                AsientoBoletoDTO ab = compra.getListaAsiento().get(i);
                asientosTexto.append(ab.getAsiento().getNumero());
                if (i < compra.getListaAsiento().size() - 1) {
                    asientosTexto.append(", ");
                }
            }
        } else {
            asientosTexto.append("N/A");
        }
        asientosTexto.append("</html>");
        JLabel lblAsientos = new JLabel(asientosTexto.toString());
        detallesGrid.add(lblAsientos); 
        if (detallesGrid.getComponentCount() % 2 != 0) {
            detallesGrid.add(new JLabel(""));
        }

        panelPrincipal.add(detallesGrid);
        panelPrincipal.add(Box.createVerticalStrut(12));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelBotones.setOpaque(false);

        JButton btnCancelar = new JButton("Cancelar Boleto");
        personalizarBoton(btnCancelar, new Color(220, 53, 69), 13);
        btnCancelar.addActionListener((ActionEvent e) -> {
            if (compra.getId() == null || compra.getId().trim().isEmpty()) {
                JOptionPane.showMessageDialog(MisBoletos.this, "No se puede cancelar: ID de compra no disponible.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(MisBoletos.this, "¿Seguro que deseas cancelar esta compra?\nSe liberarán los asientos y se reembolsará a tu monedero.", "Confirmar Cancelación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    ControlNegocio.getInstancia().cancelarCompra(compra); //
                    JOptionPane.showMessageDialog(MisBoletos.this, "Compra cancelada con éxito. El monto ha sido reembolsado a tu monedero.");
                    recargarGUI();
                } catch (HeadlessException ex) {
                    JOptionPane.showMessageDialog(MisBoletos.this, "Error al cancelar la compra:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panelBotones.add(btnCancelar);

        JButton btnReagendar = new JButton("Reagendar Boleto");
        personalizarBoton(btnReagendar, new Color(0, 123, 255), 13); // Azul
        btnReagendar.addActionListener(e -> {
            if (compra.getId() == null || compra.getId().trim().isEmpty()){
                JOptionPane.showMessageDialog(this, "No se puede reagendar: ID de compra no disponible.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Vas a iniciar el proceso para reagendar este boleto.\n" +
                    "Se te guiará para seleccionar un nuevo viaje y asientos.\n" +
                    "La compra original se cancelará y se creará una nueva.",
                    "Confirmar Reagendamiento",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                ControlNegocio.getInstancia().iniciarProcesoReagendamiento(compra.getId(), compra.getUsuario()); //
                CordinadorPresentacion.getInstancia().abrirBusquedaViaje(); //
                this.dispose();
            }
        });
        panelBotones.add(btnReagendar);

        panelPrincipal.add(panelBotones);
        panelPrincipal.add(Box.createVerticalStrut(10));

        return panelPrincipal;
    }
    
    /**
     * Aplica un estilo personalizado a un JButton.
     */
    private void personalizarBoton(JButton boton, Color colorFondo, int fontSize) {
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, fontSize));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorFondo.darker(), 1),
            BorderFactory.createEmptyBorder(8, 18, 8, 18)
        ));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * Recarga la lista de boletos en la GUI.
     */
    private void recargarGUI() {
        cargarBoletos();
    }

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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

