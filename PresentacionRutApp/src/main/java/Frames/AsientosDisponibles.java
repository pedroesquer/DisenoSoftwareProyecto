package Frames;

import Control.ControlNegocio;
import Control.CordinadorPresentacion;
import enumm.estadoAsiento;
import itson.rutappdto.AsientoAsignadoDTO;
import itson.rutappdto.AsientoBoletoDTO;
import itson.rutappdto.AsientoDTO;
import itson.rutappdto.BoletoContext;
import itson.rutappdto.CamionDTO;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author BusSoft
 */
public class AsientosDisponibles extends javax.swing.JFrame {
//

    CamionDTO camion;

    public AsientosDisponibles() {
    }

    // Definir el Enum para los estados de los asientos
    public enum EstadoAsiento {
        LIBRE, SELECCIONADO, OCUPADO
    }

    /**
     * Método para obtener los nombres que estaran relacionados con el asiento.
     *
     * @return La lista de asientoBoleto con los nombres que pertenecen al
     * asiento.
     */
    public List<AsientoBoletoDTO> obtenerAsientosYPasajeros() {
        List<AsientoBoletoDTO> lista = new ArrayList<>();

        for (Map.Entry<JPanel, String> entry : mapaNombresPasajeros.entrySet()) {
            String nombrePasajero = entry.getValue();
            JPanel panel = entry.getKey();

            // Buscar número de asiento correspondiente al panel
            String numeroAsiento = mapaAsientos.entrySet().stream()
                    .filter(e -> e.getValue().equals(panel))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null);

            if (numeroAsiento != null) {
                // Crear AsientoDTO
                AsientoDTO asiento = new AsientoDTO(null, estadoAsiento.OCUPADO, numeroAsiento);

                // Crear AsientoBoletoDTO
                AsientoBoletoDTO asientoBoleto = new AsientoBoletoDTO(asiento, nombrePasajero);
                asientoBoleto.setBoleto(BoletoContext.getBoleto());

                lista.add(asientoBoleto);
            }
        }

        return lista;
    }

    // Crear un HashMap que relacione cada panel con su estado
    private Map<JPanel, EstadoAsiento> mapaEstadosAsientos = new HashMap<>();
    private Map<JPanel, String> mapaNombresPasajeros = new HashMap<>();

    /**
     * Creates new form ComprarViaje
     *
     * @param camion
     */
    public AsientosDisponibles(CamionDTO camion) {
        initComponents();
        this.camion = camion;
        // Lista de paneles
        JPanel[] paneles = {
            botonAsientoNueve, botonAsientoDiez, botonAsientoDiesciseis, botonAsientoQuince, botonAsientoCatorce,
            botonAsientoTrece, botonAsientoDiescinueve, botonAsientoVeinte, botonAsientoDiesciocho, botonAsientoDiescisiete,
            botonAsiento2, botonAsientoVeintitres, botonAsientoVeinticuatro, botonAsientoVeintiuno, botonAsientoVeintidos,
            botonAsiento4, botonAsientoOcho, botonAsientoSiete, botonAsientoCinco, botonAsientoSeis,
            botonAsientoDoce, botonAsientoOnce, botonAsientoUno, botonAsiento3
        };

        for (JPanel panel : paneles) {
            mapaEstadosAsientos.put(panel, EstadoAsiento.LIBRE);
            panel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    seleccionarAsiento(panel);
                }
            });
        }

        inicializarMapaAsientos();
        marcarAsientosOcupados(camion.getListaAsiento());
        mapaEstadosAsientos.put(botonAsientoUno, EstadoAsiento.OCUPADO);
    }

    // Mapear los números de asiento a los paneles correspondientes
    private Map<String, JPanel> mapaAsientos = new HashMap<>();

    public void cargarCamion(CamionDTO camionDTO) {
        marcarAsientosOcupados(camionDTO.getListaAsiento());
    }

    private void reiniciarAsientosSeleccionados() {
        for (Map.Entry<JPanel, EstadoAsiento> entry : mapaEstadosAsientos.entrySet()) {
            if (entry.getValue() == EstadoAsiento.SELECCIONADO) {
                JPanel panel = entry.getKey();
                panel.setBackground(new Color(242, 242, 242)); // Gris claro
                mapaEstadosAsientos.put(panel, EstadoAsiento.LIBRE);
            }
        }
        mapaNombresPasajeros.clear();
        resumenTextArea.setText("");
    }

// Inicializar el HashMap de asientos con sus números y paneles
    private void inicializarMapaAsientos() {
        mapaAsientos.put("9", botonAsientoNueve);
        mapaAsientos.put("10", botonAsientoDiez);
        mapaAsientos.put("16", botonAsientoDiesciseis);
        mapaAsientos.put("15", botonAsientoQuince);
        mapaAsientos.put("14", botonAsientoCatorce);
        mapaAsientos.put("13", botonAsientoTrece);
        mapaAsientos.put("19", botonAsientoDiescinueve);
        mapaAsientos.put("20", botonAsientoVeinte);
        mapaAsientos.put("18", botonAsientoDiesciocho);
        mapaAsientos.put("17", botonAsientoDiescisiete);
        mapaAsientos.put("2", botonAsiento2);
        mapaAsientos.put("23", botonAsientoVeintitres);
        mapaAsientos.put("24", botonAsientoVeinticuatro);
        mapaAsientos.put("21", botonAsientoVeintiuno);
        mapaAsientos.put("22", botonAsientoVeintidos);
        mapaAsientos.put("3", botonAsiento3);
        mapaAsientos.put("8", botonAsientoOcho);
        mapaAsientos.put("7", botonAsientoSiete);
        mapaAsientos.put("5", botonAsientoCinco);
        mapaAsientos.put("6", botonAsientoSeis);
        mapaAsientos.put("12", botonAsientoDoce);
        mapaAsientos.put("11", botonAsientoOnce);
        mapaAsientos.put("1", botonAsientoUno);
        mapaAsientos.put("4", botonAsiento4);
    }

    /**
     * Método para rellenar los asientos ocupados de otro color y dejarlos sin
     * poder seleccionar.
     *
     * @param listaAsientos
     */
    private void marcarAsientosOcupados(List<AsientoDTO> listaAsientos) {
        for (AsientoDTO asiento : listaAsientos) {
            // Verificamos si el estado es "OCUPADO"
            if (asiento.getEstado() == estadoAsiento.OCUPADO) {
                String numeroAsiento = asiento.getNumero();  // Número de asiento (String)
                JPanel panelAsiento = mapaAsientos.get(numeroAsiento);  // Obtener el panel correspondiente al número
                if (panelAsiento != null) {
                    panelAsiento.setBackground(Color.RED);  // Pintamos el panel de rojo si está ocupado
                    mapaEstadosAsientos.put(panelAsiento, EstadoAsiento.OCUPADO);
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BackGround = new javax.swing.JPanel();
        Header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Footer = new javax.swing.JPanel();
        contenedorAsientos = new javax.swing.JPanel();
        botonAsientoUno = new javax.swing.JPanel();
        numeroAsientoUno = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        botonAsiento2 = new javax.swing.JPanel();
        numeroAsientoDos = new javax.swing.JLabel();
        botonAsiento4 = new javax.swing.JPanel();
        numeroAsientoCuatro = new javax.swing.JLabel();
        botonAsiento3 = new javax.swing.JPanel();
        numeroAsientoTres = new javax.swing.JLabel();
        botonAsientoOcho = new javax.swing.JPanel();
        numeroAsiento7 = new javax.swing.JLabel();
        botonAsientoSiete = new javax.swing.JPanel();
        numeroAsiento6 = new javax.swing.JLabel();
        botonAsientoCinco = new javax.swing.JPanel();
        numeroAsiento5 = new javax.swing.JLabel();
        botonAsientoSeis = new javax.swing.JPanel();
        numeroAsientoSeis = new javax.swing.JLabel();
        botonAsientoOnce = new javax.swing.JPanel();
        numeroAsiento10 = new javax.swing.JLabel();
        botonAsientoDoce = new javax.swing.JPanel();
        numeroAsiento11 = new javax.swing.JLabel();
        botonAsientoNueve = new javax.swing.JPanel();
        numeroAsiento8 = new javax.swing.JLabel();
        botonAsientoDiez = new javax.swing.JPanel();
        numeroAsiento9 = new javax.swing.JLabel();
        botonAsientoDiesciseis = new javax.swing.JPanel();
        numeroAsiento15 = new javax.swing.JLabel();
        botonAsientoQuince = new javax.swing.JPanel();
        numeroAsiento14 = new javax.swing.JLabel();
        botonAsientoCatorce = new javax.swing.JPanel();
        numeroAsiento13 = new javax.swing.JLabel();
        botonAsientoTrece = new javax.swing.JPanel();
        numeroAsiento12 = new javax.swing.JLabel();
        botonAsientoVeinte = new javax.swing.JPanel();
        numeroAsiento19 = new javax.swing.JLabel();
        botonAsientoDiescinueve = new javax.swing.JPanel();
        numeroAsiento18 = new javax.swing.JLabel();
        botonAsientoDiescisiete = new javax.swing.JPanel();
        numeroAsiento16 = new javax.swing.JLabel();
        botonAsientoDiesciocho = new javax.swing.JPanel();
        numeroAsiento17 = new javax.swing.JLabel();
        botonAsientoVeintitres = new javax.swing.JPanel();
        numeroAsiento22 = new javax.swing.JLabel();
        botonAsientoVeinticuatro = new javax.swing.JPanel();
        numeroAsiento23 = new javax.swing.JLabel();
        botonAsientoVeintiuno = new javax.swing.JPanel();
        numeroAsiento20 = new javax.swing.JLabel();
        botonAsientoVeintidos = new javax.swing.JPanel();
        numeroAsiento21 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        resumenTextArea = new javax.swing.JTextArea();
        btnCompraViaje = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        BackGround.setBackground(new java.awt.Color(255, 255, 255));
        BackGround.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Header.setBackground(new java.awt.Color(255, 201, 98));
        Header.setPreferredSize(new java.awt.Dimension(520, 60));

        jLabel1.setFont(new java.awt.Font("Roboto Condensed Medium", 1, 48)); // NOI18N
        jLabel1.setText("RUTAPP");

        javax.swing.GroupLayout HeaderLayout = new javax.swing.GroupLayout(Header);
        Header.setLayout(HeaderLayout);
        HeaderLayout.setHorizontalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HeaderLayout.createSequentialGroup()
                .addContainerGap(166, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(164, 164, 164))
        );
        HeaderLayout.setVerticalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        BackGround.add(Header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, -1));

        Footer.setBackground(new java.awt.Color(255, 201, 98));
        Footer.setPreferredSize(new java.awt.Dimension(520, 60));

        javax.swing.GroupLayout FooterLayout = new javax.swing.GroupLayout(Footer);
        Footer.setLayout(FooterLayout);
        FooterLayout.setHorizontalGroup(
            FooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        FooterLayout.setVerticalGroup(
            FooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        BackGround.add(Footer, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 540, 500, 60));

        contenedorAsientos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        botonAsientoUno.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        numeroAsientoUno.setForeground(new java.awt.Color(255, 255, 255));
        numeroAsientoUno.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numeroAsientoUno.setText("1");

        javax.swing.GroupLayout botonAsientoUnoLayout = new javax.swing.GroupLayout(botonAsientoUno);
        botonAsientoUno.setLayout(botonAsientoUnoLayout);
        botonAsientoUnoLayout.setHorizontalGroup(
            botonAsientoUnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(numeroAsientoUno, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );
        botonAsientoUnoLayout.setVerticalGroup(
            botonAsientoUnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(numeroAsientoUno, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
        );

        contenedorAsientos.add(botonAsientoUno, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 40, -1));

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("P A S I L L O");
        contenedorAsientos.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 270, 20));

        botonAsiento2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        botonAsiento2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        numeroAsientoDos.setForeground(new java.awt.Color(255, 255, 255));
        numeroAsientoDos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numeroAsientoDos.setText("2");
        botonAsiento2.add(numeroAsientoDos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 40, 30));

        contenedorAsientos.add(botonAsiento2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 40, 30));

        botonAsiento4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        botonAsiento4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        numeroAsientoCuatro.setForeground(new java.awt.Color(255, 255, 255));
        numeroAsientoCuatro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numeroAsientoCuatro.setText("4");

        javax.swing.GroupLayout botonAsiento4Layout = new javax.swing.GroupLayout(botonAsiento4);
        botonAsiento4.setLayout(botonAsiento4Layout);
        botonAsiento4Layout.setHorizontalGroup(
            botonAsiento4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(numeroAsientoCuatro, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );
        botonAsiento4Layout.setVerticalGroup(
            botonAsiento4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(numeroAsientoCuatro, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        contenedorAsientos.add(botonAsiento4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 40, -1));

        botonAsiento3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        numeroAsientoTres.setForeground(new java.awt.Color(255, 255, 255));
        numeroAsientoTres.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numeroAsientoTres.setText("3");

        javax.swing.GroupLayout botonAsiento3Layout = new javax.swing.GroupLayout(botonAsiento3);
        botonAsiento3.setLayout(botonAsiento3Layout);
        botonAsiento3Layout.setHorizontalGroup(
            botonAsiento3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(numeroAsientoTres, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );
        botonAsiento3Layout.setVerticalGroup(
            botonAsiento3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(numeroAsientoTres, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        contenedorAsientos.add(botonAsiento3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        botonAsientoOcho.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        numeroAsiento7.setForeground(new java.awt.Color(255, 255, 255));
        numeroAsiento7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numeroAsiento7.setText("8");

        javax.swing.GroupLayout botonAsientoOchoLayout = new javax.swing.GroupLayout(botonAsientoOcho);
        botonAsientoOcho.setLayout(botonAsientoOchoLayout);
        botonAsientoOchoLayout.setHorizontalGroup(
            botonAsientoOchoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
            .addGroup(botonAsientoOchoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoOchoLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento7)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        botonAsientoOchoLayout.setVerticalGroup(
            botonAsientoOchoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoOchoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoOchoLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento7)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        contenedorAsientos.add(botonAsientoOcho, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, 40, -1));

        botonAsientoSiete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        numeroAsiento6.setForeground(new java.awt.Color(255, 255, 255));
        numeroAsiento6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numeroAsiento6.setText("7");

        javax.swing.GroupLayout botonAsientoSieteLayout = new javax.swing.GroupLayout(botonAsientoSiete);
        botonAsientoSiete.setLayout(botonAsientoSieteLayout);
        botonAsientoSieteLayout.setHorizontalGroup(
            botonAsientoSieteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
            .addGroup(botonAsientoSieteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoSieteLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento6)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        botonAsientoSieteLayout.setVerticalGroup(
            botonAsientoSieteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoSieteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoSieteLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento6)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        contenedorAsientos.add(botonAsientoSiete, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, 40, 30));

        botonAsientoCinco.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        botonAsientoCinco.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonAsientoCincoMouseClicked(evt);
            }
        });

        numeroAsiento5.setForeground(new java.awt.Color(255, 255, 255));
        numeroAsiento5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numeroAsiento5.setText("5");

        javax.swing.GroupLayout botonAsientoCincoLayout = new javax.swing.GroupLayout(botonAsientoCinco);
        botonAsientoCinco.setLayout(botonAsientoCincoLayout);
        botonAsientoCincoLayout.setHorizontalGroup(
            botonAsientoCincoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
            .addGroup(botonAsientoCincoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoCincoLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento5)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        botonAsientoCincoLayout.setVerticalGroup(
            botonAsientoCincoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoCincoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoCincoLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento5)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        contenedorAsientos.add(botonAsientoCinco, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 40, -1));

        botonAsientoSeis.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        numeroAsientoSeis.setForeground(new java.awt.Color(255, 255, 255));
        numeroAsientoSeis.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numeroAsientoSeis.setText("6");

        javax.swing.GroupLayout botonAsientoSeisLayout = new javax.swing.GroupLayout(botonAsientoSeis);
        botonAsientoSeis.setLayout(botonAsientoSeisLayout);
        botonAsientoSeisLayout.setHorizontalGroup(
            botonAsientoSeisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
            .addGroup(botonAsientoSeisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoSeisLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsientoSeis)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        botonAsientoSeisLayout.setVerticalGroup(
            botonAsientoSeisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoSeisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoSeisLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsientoSeis)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        contenedorAsientos.add(botonAsientoSeis, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 40, 30));

        botonAsientoOnce.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        numeroAsiento10.setForeground(new java.awt.Color(255, 255, 255));
        numeroAsiento10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numeroAsiento10.setText("11");

        javax.swing.GroupLayout botonAsientoOnceLayout = new javax.swing.GroupLayout(botonAsientoOnce);
        botonAsientoOnce.setLayout(botonAsientoOnceLayout);
        botonAsientoOnceLayout.setHorizontalGroup(
            botonAsientoOnceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
            .addGroup(botonAsientoOnceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoOnceLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento10)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        botonAsientoOnceLayout.setVerticalGroup(
            botonAsientoOnceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoOnceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoOnceLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento10)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        contenedorAsientos.add(botonAsientoOnce, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 40, 30));

        botonAsientoDoce.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        numeroAsiento11.setForeground(new java.awt.Color(255, 255, 255));
        numeroAsiento11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numeroAsiento11.setText("12");

        javax.swing.GroupLayout botonAsientoDoceLayout = new javax.swing.GroupLayout(botonAsientoDoce);
        botonAsientoDoce.setLayout(botonAsientoDoceLayout);
        botonAsientoDoceLayout.setHorizontalGroup(
            botonAsientoDoceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
            .addGroup(botonAsientoDoceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoDoceLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento11)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        botonAsientoDoceLayout.setVerticalGroup(
            botonAsientoDoceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoDoceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoDoceLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento11)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        contenedorAsientos.add(botonAsientoDoce, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, 40, -1));

        botonAsientoNueve.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        numeroAsiento8.setForeground(new java.awt.Color(255, 255, 255));
        numeroAsiento8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numeroAsiento8.setText("9");

        javax.swing.GroupLayout botonAsientoNueveLayout = new javax.swing.GroupLayout(botonAsientoNueve);
        botonAsientoNueve.setLayout(botonAsientoNueveLayout);
        botonAsientoNueveLayout.setHorizontalGroup(
            botonAsientoNueveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
            .addGroup(botonAsientoNueveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoNueveLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento8)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        botonAsientoNueveLayout.setVerticalGroup(
            botonAsientoNueveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoNueveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoNueveLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento8)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        contenedorAsientos.add(botonAsientoNueve, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 40, 30));

        botonAsientoDiez.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        numeroAsiento9.setForeground(new java.awt.Color(255, 255, 255));
        numeroAsiento9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numeroAsiento9.setText("10");

        javax.swing.GroupLayout botonAsientoDiezLayout = new javax.swing.GroupLayout(botonAsientoDiez);
        botonAsientoDiez.setLayout(botonAsientoDiezLayout);
        botonAsientoDiezLayout.setHorizontalGroup(
            botonAsientoDiezLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
            .addGroup(botonAsientoDiezLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoDiezLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento9)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        botonAsientoDiezLayout.setVerticalGroup(
            botonAsientoDiezLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoDiezLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoDiezLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento9)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        contenedorAsientos.add(botonAsientoDiez, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 40, 30));

        botonAsientoDiesciseis.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        numeroAsiento15.setForeground(new java.awt.Color(255, 255, 255));
        numeroAsiento15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numeroAsiento15.setText("16");

        javax.swing.GroupLayout botonAsientoDiesciseisLayout = new javax.swing.GroupLayout(botonAsientoDiesciseis);
        botonAsientoDiesciseis.setLayout(botonAsientoDiesciseisLayout);
        botonAsientoDiesciseisLayout.setHorizontalGroup(
            botonAsientoDiesciseisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoDiesciseisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoDiesciseisLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento15)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        botonAsientoDiesciseisLayout.setVerticalGroup(
            botonAsientoDiesciseisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoDiesciseisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoDiesciseisLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento15)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        contenedorAsientos.add(botonAsientoDiesciseis, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 160, -1, -1));

        botonAsientoQuince.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        numeroAsiento14.setForeground(new java.awt.Color(255, 255, 255));
        numeroAsiento14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numeroAsiento14.setText("15");

        javax.swing.GroupLayout botonAsientoQuinceLayout = new javax.swing.GroupLayout(botonAsientoQuince);
        botonAsientoQuince.setLayout(botonAsientoQuinceLayout);
        botonAsientoQuinceLayout.setHorizontalGroup(
            botonAsientoQuinceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoQuinceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoQuinceLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento14)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        botonAsientoQuinceLayout.setVerticalGroup(
            botonAsientoQuinceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoQuinceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoQuinceLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento14)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        contenedorAsientos.add(botonAsientoQuince, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, -1, -1));

        botonAsientoCatorce.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        numeroAsiento13.setForeground(new java.awt.Color(255, 255, 255));
        numeroAsiento13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numeroAsiento13.setText("14");

        javax.swing.GroupLayout botonAsientoCatorceLayout = new javax.swing.GroupLayout(botonAsientoCatorce);
        botonAsientoCatorce.setLayout(botonAsientoCatorceLayout);
        botonAsientoCatorceLayout.setHorizontalGroup(
            botonAsientoCatorceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
            .addGroup(botonAsientoCatorceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoCatorceLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento13)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        botonAsientoCatorceLayout.setVerticalGroup(
            botonAsientoCatorceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoCatorceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoCatorceLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento13)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        contenedorAsientos.add(botonAsientoCatorce, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, -1, -1));

        botonAsientoTrece.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        numeroAsiento12.setForeground(new java.awt.Color(255, 255, 255));
        numeroAsiento12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numeroAsiento12.setText("13");

        javax.swing.GroupLayout botonAsientoTreceLayout = new javax.swing.GroupLayout(botonAsientoTrece);
        botonAsientoTrece.setLayout(botonAsientoTreceLayout);
        botonAsientoTreceLayout.setHorizontalGroup(
            botonAsientoTreceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoTreceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoTreceLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento12)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        botonAsientoTreceLayout.setVerticalGroup(
            botonAsientoTreceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoTreceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoTreceLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento12)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        contenedorAsientos.add(botonAsientoTrece, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, -1, -1));

        botonAsientoVeinte.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        numeroAsiento19.setForeground(new java.awt.Color(255, 255, 255));
        numeroAsiento19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numeroAsiento19.setText("20");

        javax.swing.GroupLayout botonAsientoVeinteLayout = new javax.swing.GroupLayout(botonAsientoVeinte);
        botonAsientoVeinte.setLayout(botonAsientoVeinteLayout);
        botonAsientoVeinteLayout.setHorizontalGroup(
            botonAsientoVeinteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
            .addGroup(botonAsientoVeinteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoVeinteLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento19)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        botonAsientoVeinteLayout.setVerticalGroup(
            botonAsientoVeinteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoVeinteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoVeinteLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento19)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        contenedorAsientos.add(botonAsientoVeinte, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 160, -1, 30));

        botonAsientoDiescinueve.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        numeroAsiento18.setForeground(new java.awt.Color(255, 255, 255));
        numeroAsiento18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numeroAsiento18.setText("19");

        javax.swing.GroupLayout botonAsientoDiescinueveLayout = new javax.swing.GroupLayout(botonAsientoDiescinueve);
        botonAsientoDiescinueve.setLayout(botonAsientoDiescinueveLayout);
        botonAsientoDiescinueveLayout.setHorizontalGroup(
            botonAsientoDiescinueveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoDiescinueveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoDiescinueveLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento18)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        botonAsientoDiescinueveLayout.setVerticalGroup(
            botonAsientoDiescinueveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoDiescinueveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoDiescinueveLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento18)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        contenedorAsientos.add(botonAsientoDiescinueve, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, -1, -1));

        botonAsientoDiescisiete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        numeroAsiento16.setForeground(new java.awt.Color(255, 255, 255));
        numeroAsiento16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numeroAsiento16.setText("17");

        javax.swing.GroupLayout botonAsientoDiescisieteLayout = new javax.swing.GroupLayout(botonAsientoDiescisiete);
        botonAsientoDiescisiete.setLayout(botonAsientoDiescisieteLayout);
        botonAsientoDiescisieteLayout.setHorizontalGroup(
            botonAsientoDiescisieteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
            .addGroup(botonAsientoDiescisieteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoDiescisieteLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento16)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        botonAsientoDiescisieteLayout.setVerticalGroup(
            botonAsientoDiescisieteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoDiescisieteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoDiescisieteLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento16)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        contenedorAsientos.add(botonAsientoDiescisiete, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, -1, 30));

        botonAsientoDiesciocho.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        numeroAsiento17.setForeground(new java.awt.Color(255, 255, 255));
        numeroAsiento17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numeroAsiento17.setText("18");

        javax.swing.GroupLayout botonAsientoDiesciochoLayout = new javax.swing.GroupLayout(botonAsientoDiesciocho);
        botonAsientoDiesciocho.setLayout(botonAsientoDiesciochoLayout);
        botonAsientoDiesciochoLayout.setHorizontalGroup(
            botonAsientoDiesciochoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoDiesciochoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoDiesciochoLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento17)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        botonAsientoDiesciochoLayout.setVerticalGroup(
            botonAsientoDiesciochoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoDiesciochoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoDiesciochoLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento17)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        contenedorAsientos.add(botonAsientoDiesciocho, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 50, -1, -1));

        botonAsientoVeintitres.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        numeroAsiento22.setForeground(new java.awt.Color(255, 255, 255));
        numeroAsiento22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numeroAsiento22.setText("23");

        javax.swing.GroupLayout botonAsientoVeintitresLayout = new javax.swing.GroupLayout(botonAsientoVeintitres);
        botonAsientoVeintitres.setLayout(botonAsientoVeintitresLayout);
        botonAsientoVeintitresLayout.setHorizontalGroup(
            botonAsientoVeintitresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
            .addGroup(botonAsientoVeintitresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoVeintitresLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento22)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        botonAsientoVeintitresLayout.setVerticalGroup(
            botonAsientoVeintitresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoVeintitresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoVeintitresLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento22)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        contenedorAsientos.add(botonAsientoVeintitres, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 120, -1, 30));

        botonAsientoVeinticuatro.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        numeroAsiento23.setForeground(new java.awt.Color(255, 255, 255));
        numeroAsiento23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numeroAsiento23.setText("24");

        javax.swing.GroupLayout botonAsientoVeinticuatroLayout = new javax.swing.GroupLayout(botonAsientoVeinticuatro);
        botonAsientoVeinticuatro.setLayout(botonAsientoVeinticuatroLayout);
        botonAsientoVeinticuatroLayout.setHorizontalGroup(
            botonAsientoVeinticuatroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoVeinticuatroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoVeinticuatroLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento23)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        botonAsientoVeinticuatroLayout.setVerticalGroup(
            botonAsientoVeinticuatroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoVeinticuatroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoVeinticuatroLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento23)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        contenedorAsientos.add(botonAsientoVeinticuatro, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 160, -1, -1));

        botonAsientoVeintiuno.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        numeroAsiento20.setForeground(new java.awt.Color(255, 255, 255));
        numeroAsiento20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numeroAsiento20.setText("21");

        javax.swing.GroupLayout botonAsientoVeintiunoLayout = new javax.swing.GroupLayout(botonAsientoVeintiuno);
        botonAsientoVeintiuno.setLayout(botonAsientoVeintiunoLayout);
        botonAsientoVeintiunoLayout.setHorizontalGroup(
            botonAsientoVeintiunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoVeintiunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoVeintiunoLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento20)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        botonAsientoVeintiunoLayout.setVerticalGroup(
            botonAsientoVeintiunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoVeintiunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoVeintiunoLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento20)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        contenedorAsientos.add(botonAsientoVeintiuno, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, -1, -1));

        botonAsientoVeintidos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        numeroAsiento21.setForeground(new java.awt.Color(255, 255, 255));
        numeroAsiento21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numeroAsiento21.setText("22");

        javax.swing.GroupLayout botonAsientoVeintidosLayout = new javax.swing.GroupLayout(botonAsientoVeintidos);
        botonAsientoVeintidos.setLayout(botonAsientoVeintidosLayout);
        botonAsientoVeintidosLayout.setHorizontalGroup(
            botonAsientoVeintidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoVeintidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoVeintidosLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento21)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        botonAsientoVeintidosLayout.setVerticalGroup(
            botonAsientoVeintidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
            .addGroup(botonAsientoVeintidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(botonAsientoVeintidosLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(numeroAsiento21)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        contenedorAsientos.add(botonAsientoVeintidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, -1, -1));

        BackGround.add(contenedorAsientos, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 350, 200));

        resumenTextArea.setEditable(false);
        resumenTextArea.setColumns(20);
        resumenTextArea.setLineWrap(true);
        resumenTextArea.setRows(5);
        resumenTextArea.setWrapStyleWord(true);
        jScrollPane2.setViewportView(resumenTextArea);

        BackGround.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 330, 360, 130));

        btnCompraViaje.setBackground(new java.awt.Color(47, 40, 34));
        btnCompraViaje.setFont(new java.awt.Font("Roboto Condensed Black", 1, 13)); // NOI18N
        btnCompraViaje.setForeground(new java.awt.Color(255, 255, 255));
        btnCompraViaje.setText("COMPRAR");
        btnCompraViaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompraViajeActionPerformed(evt);
            }
        });
        BackGround.add(btnCompraViaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 480, 110, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BackGround, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BackGround, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonAsientoCincoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonAsientoCincoMouseClicked
        seleccionarAsiento(botonAsientoCinco);
    }//GEN-LAST:event_botonAsientoCincoMouseClicked

    private void btnCompraViajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompraViajeActionPerformed
        List<AsientoBoletoDTO> lista = obtenerAsientosYPasajeros(); // tu método original
        BoletoContext.getBoleto().setListaAsiento(lista);
        CordinadorPresentacion.getInstancia().abrirMetodoPago();
        this.dispose();
    }//GEN-LAST:event_btnCompraViajeActionPerformed

    /**
     *
     * @param panel
     */
    private void seleccionarAsiento(JPanel panel) {

        // Obtener el estado actual del asiento desde el HashMap
        EstadoAsiento estadoActual = mapaEstadosAsientos.get(panel);

        // Comprobar el estado y realizar las acciones correspondientes
        switch (estadoActual) {
            case LIBRE:
                // Mostrar cuadro de diálogo para ingresar el nombre del pasajero
                String nombrePasajero = JOptionPane.showInputDialog(
                        this,
                        "Ingresa el nombre del pasajero:",
                        "Asignar Asiento",
                        JOptionPane.PLAIN_MESSAGE
                );

                // Si el usuario ingresó un nombre válido
                if (nombrePasajero != null && !nombrePasajero.trim().isEmpty()) {
                    panel.setBackground(new Color(51, 204, 255)); // Azul
                    mapaEstadosAsientos.put(panel, EstadoAsiento.SELECCIONADO); // Actualizar el estado
                    mapaNombresPasajeros.put(panel, nombrePasajero.trim());// Guardar el nombre
                    actualizarResumenAsientos();
                }

                CordinadorPresentacion.getInstancia().iniciarTemporizador(() -> reiniciarAsientosSeleccionados());
                break;

            case SELECCIONADO:
                // Si está seleccionado, cambiar a libre
                panel.setBackground(new Color(242, 242, 242)); // Gris
                mapaEstadosAsientos.put(panel, EstadoAsiento.LIBRE); // Actualizar el estado
                mapaNombresPasajeros.remove(panel); // Eliminar el nombre del pasajero
                break;

            case OCUPADO:
                JOptionPane.showMessageDialog(
                        this,
                        "El asiento que seleccionaste ya está ocupado.",
                        "Asiento Ocupado",
                        JOptionPane.WARNING_MESSAGE
                );
                break;
        }

        // Forzar la actualización visual
        panel.revalidate();
        panel.repaint();
    }

    /**
     *
     */
    private void actualizarResumenAsientos() {
        StringBuilder resumen = new StringBuilder();

        for (Map.Entry<JPanel, String> entry : mapaNombresPasajeros.entrySet()) {
            String nombre = entry.getValue();
            // Buscamos el número de asiento basado en el panel
            String numeroAsiento = mapaAsientos.entrySet().stream()
                    .filter(e -> e.getValue().equals(entry.getKey()))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse("Desconocido");

            resumen.append("Asiento ").append(numeroAsiento)
                    .append(" ha sido asignado a: ")
                    .append(nombre).append("\n");
        }

        resumenTextArea.setText(resumen.toString());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BackGround;
    private javax.swing.JPanel Footer;
    private javax.swing.JPanel Header;
    private javax.swing.JPanel botonAsiento2;
    private javax.swing.JPanel botonAsiento3;
    private javax.swing.JPanel botonAsiento4;
    private javax.swing.JPanel botonAsientoCatorce;
    private javax.swing.JPanel botonAsientoCinco;
    private javax.swing.JPanel botonAsientoDiescinueve;
    private javax.swing.JPanel botonAsientoDiesciocho;
    private javax.swing.JPanel botonAsientoDiesciseis;
    private javax.swing.JPanel botonAsientoDiescisiete;
    private javax.swing.JPanel botonAsientoDiez;
    private javax.swing.JPanel botonAsientoDoce;
    private javax.swing.JPanel botonAsientoNueve;
    private javax.swing.JPanel botonAsientoOcho;
    private javax.swing.JPanel botonAsientoOnce;
    private javax.swing.JPanel botonAsientoQuince;
    private javax.swing.JPanel botonAsientoSeis;
    private javax.swing.JPanel botonAsientoSiete;
    private javax.swing.JPanel botonAsientoTrece;
    private javax.swing.JPanel botonAsientoUno;
    private javax.swing.JPanel botonAsientoVeinte;
    private javax.swing.JPanel botonAsientoVeinticuatro;
    private javax.swing.JPanel botonAsientoVeintidos;
    private javax.swing.JPanel botonAsientoVeintitres;
    private javax.swing.JPanel botonAsientoVeintiuno;
    private javax.swing.JButton btnCompraViaje;
    private javax.swing.JPanel contenedorAsientos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel numeroAsiento10;
    private javax.swing.JLabel numeroAsiento11;
    private javax.swing.JLabel numeroAsiento12;
    private javax.swing.JLabel numeroAsiento13;
    private javax.swing.JLabel numeroAsiento14;
    private javax.swing.JLabel numeroAsiento15;
    private javax.swing.JLabel numeroAsiento16;
    private javax.swing.JLabel numeroAsiento17;
    private javax.swing.JLabel numeroAsiento18;
    private javax.swing.JLabel numeroAsiento19;
    private javax.swing.JLabel numeroAsiento20;
    private javax.swing.JLabel numeroAsiento21;
    private javax.swing.JLabel numeroAsiento22;
    private javax.swing.JLabel numeroAsiento23;
    private javax.swing.JLabel numeroAsiento5;
    private javax.swing.JLabel numeroAsiento6;
    private javax.swing.JLabel numeroAsiento7;
    private javax.swing.JLabel numeroAsiento8;
    private javax.swing.JLabel numeroAsiento9;
    private javax.swing.JLabel numeroAsientoCuatro;
    private javax.swing.JLabel numeroAsientoDos;
    private javax.swing.JLabel numeroAsientoSeis;
    private javax.swing.JLabel numeroAsientoTres;
    private javax.swing.JLabel numeroAsientoUno;
    private javax.swing.JTextArea resumenTextArea;
    // End of variables declaration//GEN-END:variables

}
