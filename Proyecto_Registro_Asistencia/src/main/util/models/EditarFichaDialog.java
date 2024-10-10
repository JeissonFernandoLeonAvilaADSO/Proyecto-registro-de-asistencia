package main.util.models;

import main.util.API_Actions.API_Data.API_DataFichasApplications;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class EditarFichaDialog extends JDialog {
    private JTextField txtNumeroFicha;
    private JComboBox<String> cmbProgramaFormacion;
    private JComboBox<String> cmbJornadaFormacion;
    private JButton btnActualizar;
    private JButton btnCancelar;

    private int idFicha;
    private JTable tabla;
    private ComboBoxModels cmb;

    public EditarFichaDialog(Frame parent, int idFicha, int numeroFicha, String programaFormacion, String jornadaFormacion, JTable tabla) {
        super(parent, "Editar Ficha", true);
        this.idFicha = idFicha;
        this.tabla = tabla;
        this.cmb = new ComboBoxModels();

        initComponents(numeroFicha, programaFormacion, jornadaFormacion);
        setLocationRelativeTo(parent);
    }

    private void initComponents(int numeroFicha, String programaFormacion, String jornadaFormacion) {
        setLayout(new BorderLayout());

        // Panel de campos
        JPanel panelCampos = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Número de Ficha (JTextField)
        panelCampos.add(new JLabel("Número de Ficha:"));
        txtNumeroFicha = new JTextField(String.valueOf(numeroFicha));
        txtNumeroFicha.setEditable(false);
        txtNumeroFicha.setFocusable(false);
        panelCampos.add(txtNumeroFicha);

        // Programa de Formación (JComboBox)
        panelCampos.add(new JLabel("Programa de Formación:"));
        cmbProgramaFormacion = new JComboBox<>();
        cmbProgramaFormacion.setModel(cmb.generarComboBoxModelPorTipo("ProgramaFormacion"));
        cmbProgramaFormacion.setSelectedItem(programaFormacion);
        panelCampos.add(cmbProgramaFormacion);

        // Jornada de Formación (JComboBox)
        panelCampos.add(new JLabel("Jornada de Formación:"));
        cmbJornadaFormacion = new JComboBox<>();
        cmbJornadaFormacion.setModel(cmb.generarComboBoxModelPorTipo("JornadaFormacion"));
        cmbJornadaFormacion.setSelectedItem(jornadaFormacion);
        panelCampos.add(cmbJornadaFormacion);

        add(panelCampos, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        btnActualizar = new JButton("Actualizar");
        btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnActualizar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        // Acción del botón Actualizar
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarFicha();
            }
        });

        // Acción del botón Cancelar
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        pack();
    }

    private void actualizarFicha() {
        try {
            // Validar y convertir el número de ficha
            String numeroFichaStr = txtNumeroFicha.getText().trim();
            if (numeroFichaStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El número de ficha no puede estar vacío.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Integer nuevoNumeroFicha;
            try {
                nuevoNumeroFicha = Integer.parseInt(numeroFichaStr);
                if (nuevoNumeroFicha <= 0) {
                    throw new NumberFormatException("El número de ficha debe ser positivo.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un número de ficha válido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener los valores seleccionados en los ComboBoxes
            String nuevoProgramaFormacion = cmbProgramaFormacion.getSelectedItem() != null ? cmbProgramaFormacion.getSelectedItem().toString() : "";
            String nuevaJornadaFormacion = cmbJornadaFormacion.getSelectedItem() != null ? cmbJornadaFormacion.getSelectedItem().toString() : "";

            // Validar que no se hayan seleccionado valores por defecto
            if (nuevoProgramaFormacion.equals("Seleccionar...") || nuevaJornadaFormacion.equals("Seleccionar...")) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un programa y una jornada de formación válidos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Confirmar actualización
            String datosEnviar = String.format(
                    "ID Ficha: %d\nNúmero de Ficha: %d\nPrograma de Formación: %s\nJornada de Formación: %s",
                    idFicha,
                    nuevoNumeroFicha,
                    nuevoProgramaFormacion,
                    nuevaJornadaFormacion
            );

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Los siguientes datos se enviarán para actualizar la ficha:\n\n" + datosEnviar + "\n\n¿Desea continuar?",
                    "Confirmar Actualización",
                    JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            // Llamar al método de ClienteAPI para actualizar la ficha
            String respuesta = API_DataFichasApplications.actualizarFicha(
                    idFicha,
                    nuevoNumeroFicha,
                    nuevoProgramaFormacion,
                    nuevaJornadaFormacion
            );

            // Verificar respuesta
            if (respuesta != null && respuesta.contains("exitosamente")) {
                // Actualizar la fila en la tabla
                DefaultTableModel model = (DefaultTableModel) tabla.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    if (model.getValueAt(i, 0).equals(idFicha)) {
                        model.setValueAt(nuevoNumeroFicha, i, 1);
                        model.setValueAt(nuevoProgramaFormacion, i, 2);
                        model.setValueAt(nuevaJornadaFormacion, i, 3);
                        break;
                    }
                }
                JOptionPane.showMessageDialog(this, "Ficha actualizada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
            // Si hay un error, ya se muestra en los métodos de la API

        } catch (Exception e) {
            // Manejo de cualquier otro error inesperado
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}