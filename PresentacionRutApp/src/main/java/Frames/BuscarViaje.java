package Frames;

import Control.ControlNegocio;
import Control.CordinadorPresentacion;
import itson.rutappdto.BoletoContext;
import itson.rutappdto.ViajeDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * JFrame que permite al usuario buscar viajes seleccionando origen, destino y
 * fecha. Carga dinámicamente los destinos disponibles según el origen. Al
 * buscar, configura el BoletoContext y muestra los viajes disponibles.
 */
public class BuscarViaje extends javax.swing.JFrame {

    /**
     * Constructor. Inicializa componentes y carga los orígenes disponibles.
     */
    public BuscarViaje() {
        initComponents();
        cargarOrigenes();
        this.setLocationRelativeTo(null);
        setTitle("Búsqueda viaje");

    }

    /**
     * Carga los orígenes disponibles en el comboBox y desactiva el comboBox de
     * destino.
     */
    private void cargarOrigenes() {
        List<String> origenes = CordinadorPresentacion.getInstancia().buscarOrigenesDisponibles();

        BoxOrigen.removeAllItems();
        BoxOrigen.addItem("Selecciona tu origen");

        for (String origen : origenes) {
            BoxOrigen.addItem(origen);
        }

        BoxOrigen.setSelectedIndex(0);
        BoxDestino.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BackGround = new javax.swing.JPanel();
        Header = new javax.swing.JPanel();
        eName = new javax.swing.JLabel();
        Footer = new javax.swing.JPanel();
        btnVolver = new javax.swing.JButton();
        BoxDestino = new javax.swing.JComboBox<>();
        BoxOrigen = new javax.swing.JComboBox<>();
        Calendario = new com.toedter.calendar.JCalendar();
        btnBuscar = new javax.swing.JButton();
        Origen = new javax.swing.JLabel();
        Destino = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BackGround.setBackground(new java.awt.Color(255, 255, 255));
        BackGround.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Header.setBackground(new java.awt.Color(255, 201, 98));
        Header.setPreferredSize(new java.awt.Dimension(520, 60));

        eName.setFont(new java.awt.Font("Roboto Condensed Medium", 1, 48)); // NOI18N
        eName.setText("RUTAPP");

        javax.swing.GroupLayout HeaderLayout = new javax.swing.GroupLayout(Header);
        Header.setLayout(HeaderLayout);
        HeaderLayout.setHorizontalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderLayout.createSequentialGroup()
                .addGap(170, 170, 170)
                .addComponent(eName)
                .addContainerGap(180, Short.MAX_VALUE))
        );
        HeaderLayout.setVerticalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderLayout.createSequentialGroup()
                .addComponent(eName)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        BackGround.add(Header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        Footer.setBackground(new java.awt.Color(255, 201, 98));
        Footer.setPreferredSize(new java.awt.Dimension(520, 60));

        btnVolver.setBackground(new java.awt.Color(255, 201, 98));
        btnVolver.setFont(new java.awt.Font("Roboto Condensed Black", 1, 13)); // NOI18N
        btnVolver.setText("Volver\n");
        btnVolver.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnVolver.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnVolver.setPreferredSize(new java.awt.Dimension(128, 28));
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout FooterLayout = new javax.swing.GroupLayout(Footer);
        Footer.setLayout(FooterLayout);
        FooterLayout.setHorizontalGroup(
            FooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FooterLayout.createSequentialGroup()
                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 376, Short.MAX_VALUE))
        );
        FooterLayout.setVerticalGroup(
            FooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FooterLayout.createSequentialGroup()
                .addGap(0, 8, Short.MAX_VALUE)
                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BackGround.add(Footer, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 520, 60));

        BoxDestino.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        BoxDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoxDestinoActionPerformed(evt);
            }
        });
        BackGround.add(BoxDestino, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 110, 190, 40));

        BoxOrigen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        BoxOrigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoxOrigenActionPerformed(evt);
            }
        });
        BackGround.add(BoxOrigen, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 190, 40));
        BackGround.add(Calendario, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, 320, 190));

        btnBuscar.setBackground(new java.awt.Color(51, 51, 51));
        btnBuscar.setFont(new java.awt.Font("Roboto Condensed Black", 1, 13)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("BUSCAR");
        btnBuscar.setPreferredSize(new java.awt.Dimension(128, 28));
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        BackGround.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 410, 130, 50));

        Origen.setFont(new java.awt.Font("Roboto Condensed Black", 1, 24)); // NOI18N
        Origen.setText("ORIGEN");
        BackGround.add(Origen, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, -1, -1));

        Destino.setFont(new java.awt.Font("Roboto Condensed Black", 1, 24)); // NOI18N
        Destino.setText("DESTINO");
        BackGround.add(Destino, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, -1, -1));

        getContentPane().add(BackGround, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BoxDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoxDestinoActionPerformed


    }//GEN-LAST:event_BoxDestinoActionPerformed
    /**
     * Carga los destinos según el origen seleccionado.
     */
    private void BoxOrigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoxOrigenActionPerformed
        String origenSeleccionado = (String) BoxOrigen.getSelectedItem();

        if (origenSeleccionado != null && !origenSeleccionado.equals("Selecciona tu origen")) {
            List<String> destinos = ControlNegocio.getInstancia().obtenerDestinosDisponibles(origenSeleccionado);

            if (destinos != null && !destinos.isEmpty()) {
                BoxDestino.removeAllItems();
                BoxDestino.addItem("Selecciona tu destino");
                for (String destino : destinos) {
                    BoxDestino.addItem(destino);
                }
                BoxDestino.setEnabled(true);
                BoxDestino.setSelectedIndex(0);
            } else {
                BoxDestino.removeAllItems();
                BoxDestino.setEnabled(false);
            }
        } else {
            BoxDestino.removeAllItems();
            BoxDestino.setEnabled(false);
        }

    }//GEN-LAST:event_BoxOrigenActionPerformed
    /**
     * Valida los datos seleccionados (origen, destino y fecha), guarda la fecha
     * en el BoletoContext, y muestra los viajes disponibles.
     */
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String origen = BoxOrigen.getSelectedItem().toString();
        String destino = BoxDestino.getSelectedItem().toString();

        if (origen.equals("Selecciona tu origen")) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona un origen válido.");
            return;
        }

        if (destino.equals("Selecciona tu destino")) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona un destino válido.");
            return;
        }

        java.util.Calendar calendar = Calendario.getCalendar();
        if (calendar == null) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona una fecha.");
            return;
        }
        java.util.Date fecha = calendar.getTime();

        LocalDateTime fechaSeleccionada = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        LocalDateTime hoy = LocalDateTime.now();
        if (fechaSeleccionada.isBefore(hoy)) {
            JOptionPane.showMessageDialog(this, "La fecha seleccionada no puede ser anterior a hoy.");
            return;
        }

        BoletoContext.getBoleto().setFecha(fecha);

        ViajeDTO filtroBusqueda = new ViajeDTO();
        filtroBusqueda.setOrigen(origen);
        filtroBusqueda.setDestino(destino);
        filtroBusqueda.setFecha(BoletoContext.getBoleto().getFecha());

        ControlNegocio.getInstancia().obtenerListaViajes(filtroBusqueda);
        CordinadorPresentacion.getInstancia().mostrarViajesDisponibles(filtroBusqueda); // ✅ solo paso el filtro

        dispose();
    }//GEN-LAST:event_btnBuscarActionPerformed
    /**
     * Vuelve al menú principal.
     */
    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        MainMenu mainMenu = new MainMenu();
        mainMenu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BackGround;
    private javax.swing.JComboBox<String> BoxDestino;
    private javax.swing.JComboBox<String> BoxOrigen;
    private com.toedter.calendar.JCalendar Calendario;
    private javax.swing.JLabel Destino;
    private javax.swing.JPanel Footer;
    private javax.swing.JPanel Header;
    private javax.swing.JLabel Origen;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel eName;
    // End of variables declaration//GEN-END:variables
}
