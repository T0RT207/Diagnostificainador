
package com.mycompany.ventanas;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class VentanasBienvenida extends JFrame {

    public VentanasBienvenida() {
        setTitle("Clínica Diagnóstica Integral");
        setBounds(300, 150, 600, 400);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel header = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(0, 102, 204));
            }
        };
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Clínica Diagnóstica Integral", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.add(Box.createVerticalStrut(40));
        header.add(titulo);
        header.add(Box.createVerticalStrut(20));

        JPanel centro = new JPanel();
        centro.setBackground(Color.WHITE);
        centro.setLayout(new FlowLayout(FlowLayout.CENTER));
        try {
            ImageIcon logo = new ImageIcon("resources/logo.jpeg");
            Image imagen = logo.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(imagen));
            centro.add(logoLabel);
        } catch (Exception e) {
            centro.add(new JLabel("Logo no encontrado"));
        }

        JButton continuar = new JButton("Iniciar diagnóstico");
        continuar.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        continuar.setBackground(new Color(0, 102, 204));
        continuar.setForeground(Color.WHITE);
        continuar.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                VentanaDiagnostico ventana = new VentanaDiagnostico();
                ventana.setVisible(true);
            });
        });

        JPanel pie = new JPanel();
        pie.setBackground(new Color(240, 240, 255));
        pie.add(continuar);

        add(header, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        add(pie, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanasBienvenida ventana = new VentanasBienvenida();
            ventana.setVisible(true);
        });
    }
}

class VentanaDiagnostico extends JFrame {

    private JTextField campoNombre, campoEdad, campoID, campoSexo;
    private JCheckBox[] casillasSintomas;

    private static final String[] sintomas = {
        "Temperatura", "Dolor de cabeza", "Dolor estomacal", "Dolor de articulaciones",
        "Nauseas", "Escurrimiento nasal", "Dolor de garganta", "Ojos llorosos",
        "Cuerpo cortado", "Reflujo", "Inflamación", "Vómito", "Diarrea", "Deshidratación"
    };

    public VentanaDiagnostico() {
        setTitle("Diagnóstico Médico");
        setBounds(300, 100, 700, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelDatos = new JPanel(new GridLayout(4, 2, 10, 10));
        panelDatos.setBackground(Color.WHITE);
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos del paciente"));

        campoNombre = new JTextField();
        campoEdad = new JTextField();
        campoMatricula = new JTextField();
        campoSexo = new JTextField();

        panelDatos.add(new JLabel("Nombre:"));
        panelDatos.add(campoNombre);
        panelDatos.add(new JLabel("Edad:"));
        panelDatos.add(campoEdad);
        panelDatos.add(new JLabel("Matricula:"));
        panelDatos.add(campoMatricula);
        panelDatos.add(new JLabel("Sexo (M/F/Otro):"));
        panelDatos.add(campoSexo);

        JPanel panelSintomas = new JPanel(new GridLayout(7, 2));
        panelSintomas.setBackground(new Color(220, 240, 255));
        panelSintomas.setBorder(BorderFactory.createTitledBorder("Seleccione sus síntomas"));

        casillasSintomas = new JCheckBox[sintomas.length];
        for (int i = 0; i < sintomas.length; i++) {
            casillasSintomas[i] = new JCheckBox(sintomas[i]);
            casillasSintomas[i].setBackground(new Color(220, 240, 255));
            panelSintomas.add(casillasSintomas[i]);
        }

        JButton botonDiagnosticar = new JButton("Diagnosticar");
        botonDiagnosticar.setBackground(new Color(0, 102, 204));
        botonDiagnosticar.setForeground(Color.WHITE);
        botonDiagnosticar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        botonDiagnosticar.addActionListener(e -> procesarDiagnostico());

        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(Color.WHITE);
        panelInferior.add(botonDiagnosticar);

        add(panelDatos, BorderLayout.NORTH);
        add(panelSintomas, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void procesarDiagnostico() {
        String nombre = campoNombre.getText().trim();
        String edad = campoEdad.getText().trim();
        String matricula = campoMatricula.generarMatricula().trim();
        String sexo = campoSexo.getText().trim();

        if (nombre.isEmpty() || edad.isEmpty() || matricula.isEmpty() || sexo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar todos los campos para continuar.");
            return;
        }

        Set<Integer> seleccionados = new HashSet<>();
        for (int i = 0; i < casillasSintomas.length; i++) {
            if (casillasSintomas[i].isSelected()) {
                seleccionados.add(i);
            }
        }

        if (seleccionados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar al menos un síntoma.");
            return;
        }

        mostrarDiagnostico(seleccionados, nombre, edad, matricula, sexo);
    }

    public static String generarMatricula() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder matricula = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int indice = (int) (Math.random() * caracteres.length());
            matricula.append(caracteres.charAt(indice));
        }
        return matricula.toString();
    }


    private void mostrarDiagnostico(Set<Integer> sintomasIngresados, String nombre, String edad, String id, String sexo) {
        Map<String, java.util.List<Integer>> enfermedades = new HashMap<>();
        enfermedades.put("Gripa", Arrays.asList(5, 7, 8));
        enfermedades.put("Covid", Arrays.asList(1, 5, 7, 8, 6));
        enfermedades.put("Influenza", Arrays.asList(1, 3, 8, 7, 6));
        enfermedades.put("Indigestión", Arrays.asList(10, 2));
        enfermedades.put("Infección", Arrays.asList(12, 13, 2, 4, 11, 0));
        enfermedades.put("Gastritis", Arrays.asList(9, 2, 11));

        StringBuilder resultado = new StringBuilder();
        resultado.append("---- Datos del paciente ----\n");
        resultado.append("Nombre: ").append(nombre).append("\n");
        resultado.append("Edad: ").append(edad).append("\n");
        resultado.append("ID: ").append(id).append("\n");
        resultado.append("Sexo: ").append(sexo).append("\n\n");
        resultado.append("Diagnóstico probable:\n");

        for (Map.Entry<String, java.util.List<Integer>> entry : enfermedades.entrySet()) {
            String enfermedad = entry.getKey();
            java.util.List<Integer> sintomasAsociados = entry.getValue();
            int coincidencias = 0;

            for (int s : sintomasAsociados) {
                if (sintomasIngresados.contains(s)) {
                    coincidencias++;
                }
            }

            switch (coincidencias) {
                case 5:
                case 4:
                case 3:
                    resultado.append("- ").append(enfermedad).append(" (Alta coincidencia)\n");
                    break;
                case 2:
                    resultado.append("- ").append(enfermedad).append(" (Coincidencia moderada)\n");
                    break;
                case 1:
                    resultado.append("- ").append(enfermedad).append(" (Baja coincidencia)\n");
                    break;
                default:
                    break;
            }
        }

        JTextArea area = new JTextArea(resultado.toString(), 15, 40);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));
        area.setWrapStyleWord(true);
        area.setLineWrap(true);
        area.setEditable(false);

        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Resultado", JOptionPane.INFORMATION_MESSAGE);
    }
}
