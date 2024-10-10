package main.util.models;

import main.util.API_Actions.API_Data.API_DataClaseFormacionApplications;
import main.util.API_Actions.API_Data.API_DataProgramaFormacionApplications;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class EditarProgramaFormacionDialog extends JDialog {
    private JTextField txtNombrePrograma;
    private JComboBox<String> cmbNivelFormacion;
    private JComboBox<String> cmbSedeFormacion;
    private JComboBox<String> cmbAreaFormacion;
    private JButton btnActualizar;
    private JButton btnCancelar;

    private int idPrograma;
    private JTable tabla;
    private ComboBoxModels cmb;

    public EditarProgramaFormacionDialog(Frame parent, int idPrograma, String nombrePrograma,
                                         String nivelFormacion, String sedeFormacion, String areaFormacion, JTable tabla) {
        super(parent, "Editar Programa de Formación", true);
        this.idPrograma = idPrograma;
        this.tabla = tabla;
        this.cmb = new ComboBoxModels();

        initComponents(nombrePrograma, nivelFormacion, sedeFormacion, areaFormacion);
        setLocationRelativeTo(parent);
    }

    private void initComponents(String nombrePrograma, String nivelFormacion,
                                String sedeFormacion, String areaFormacion) {
        setLayout(new BorderLayout());

        // Panel de campos
        JPanel panelCampos = new JPanel(new GridLayout(4, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Nombre del Programa (JTextField)
        panelCampos.add(new JLabel("Programa de Formación:"));
        txtNombrePrograma = new JTextField(nombrePrograma);
        panelCampos.add(txtNombrePrograma);

        // Nivel de Formación (JComboBox)
        panelCampos.add(new JLabel("Nivel de Formación:"));
        cmbNivelFormacion = new JComboBox<>();
        cmbNivelFormacion.setModel(cmb.generarComboBoxModelPorTipo("NivelFormacion"));
        cmbNivelFormacion.setSelectedItem(nivelFormacion);
        panelCampos.add(cmbNivelFormacion);

        // Sede de Formación (JComboBox)
        panelCampos.add(new JLabel("Centro de Formación:"));
        cmbSedeFormacion = new JComboBox<>();
        cmbSedeFormacion.setModel(cmb.generarComboBoxModelPorTipo("Sede"));
        cmbSedeFormacion.setSelectedItem(sedeFormacion);
        panelCampos.add(cmbSedeFormacion);

        // Área (JComboBox)
        panelCampos.add(new JLabel("Área:"));
        cmbAreaFormacion = new JComboBox<>();
        cmbAreaFormacion.setModel(cmb.generarComboBoxModelPorTipo("Areas"));
        cmbAreaFormacion.setSelectedItem(areaFormacion);
        panelCampos.add(cmbAreaFormacion);

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
                actualizarPrograma();
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

    private void actualizarPrograma() {
        try {
            String nuevoNombrePrograma = txtNombrePrograma.getText().trim();
            String nuevoNivelFormacion = (String) cmbNivelFormacion.getSelectedItem();
            String nuevoSedeFormacion = (String) cmbSedeFormacion.getSelectedItem();
            String nuevaAreaFormacion = (String) cmbAreaFormacion.getSelectedItem();

            // Validar campos
            if (nuevoNombrePrograma.isEmpty() || nuevoNivelFormacion == null ||
                    nuevoSedeFormacion == null || nuevaAreaFormacion == null) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Mostrar los datos que se enviarán para confirmar
            String datosEnviar = String.format(
                    "ID Programa: %d\nPrograma de Formación: %s\nCentro de Formación: %s\nNivel de Formación: %s\nÁrea: %s",
                    idPrograma,
                    nuevoNombrePrograma,
                    nuevoSedeFormacion,
                    nuevoNivelFormacion,
                    nuevaAreaFormacion
            );

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Los siguientes datos se enviarán para actualizar el Programa de Formación:\n\n" + datosEnviar + "\n\n¿Desea continuar?",
                    "Confirmar Actualización",
                    JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            // Llamar al método de ClienteAPI para actualizar
            String respuesta = API_DataProgramaFormacionApplications.actualizarProgramaFormacion(
                    idPrograma,
                    nuevoNombrePrograma,
                    nuevoSedeFormacion,
                    nuevoNivelFormacion,
                    nuevaAreaFormacion
            );

            // Verificar respuesta
            if (respuesta != null && respuesta.contains("exitosamente")) {
                // Actualizar la fila en la tabla
                DefaultTableModel model = (DefaultTableModel) tabla.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    if (model.getValueAt(i, 0).equals(idPrograma)) {
                        model.setValueAt(nuevoNombrePrograma, i, 1);
                        model.setValueAt(nuevoSedeFormacion, i, 2);
                        model.setValueAt(nuevoNivelFormacion, i, 3);
                        model.setValueAt(nuevaAreaFormacion, i, 4);
                        break;
                    }
                }
                JOptionPane.showMessageDialog(this, "Programa de Formación actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
            // Si hay un error, ya se muestra en los métodos de la API
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar el Programa de Formación:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
