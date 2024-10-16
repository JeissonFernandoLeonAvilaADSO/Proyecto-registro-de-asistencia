package main.util.models;

import javax.swing.*;
import java.awt.*;

public class EditarAsociacionDialog extends JDialog {
    private JComboBox<String> claseFormacionCB;
    private JComboBox<String> jornadaFormacionCB;
    private JTextField documentoInstructorTF;
    private JButton actualizarButton, cancelarButton;
    private boolean actualizacionConfirmada = false;  // Nuevo flag para verificar si se confirma la actualización

    public EditarAsociacionDialog(JFrame parent, String claseActual, String jornadaActual, String documentoActual) {
        super(parent, "Editar Asociación", true);

        // Crear componentes
        claseFormacionCB = new JComboBox<>();
        jornadaFormacionCB = new JComboBox<>();
        documentoInstructorTF = new JTextField(15);

        // Llenar ComboBox con los métodos existentes
        ComboBoxModels cbm = new ComboBoxModels();
        claseFormacionCB.setModel(cbm.generarComboBoxModelPorTipo("ClaseFormacion"));
        jornadaFormacionCB.setModel(cbm.generarComboBoxModelPorTipo("JornadaFormacion"));

        // Seleccionar los valores actuales
        claseFormacionCB.setSelectedItem(claseActual);
        jornadaFormacionCB.setSelectedItem(jornadaActual);

        // Rellenar el campo del documento con el valor actual, el usuario puede cambiarlo si lo desea
        documentoInstructorTF.setText(documentoActual);

        // Crear botones
        actualizarButton = new JButton("Actualizar");
        cancelarButton = new JButton("Cancelar");

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Clase Formación: "), gbc);

        gbc.gridx = 1;
        add(claseFormacionCB, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Jornada Formación: "), gbc);

        gbc.gridx = 1;
        add(jornadaFormacionCB, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Documento Instructor: "), gbc);

        gbc.gridx = 1;
        add(documentoInstructorTF, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(actualizarButton, gbc);

        gbc.gridy = 4;
        add(cancelarButton, gbc);

        // Acción de cancelar
        cancelarButton.addActionListener(e -> {
            actualizacionConfirmada = false;  // Marcar que no se ha confirmado la actualización
            dispose();  // Cerrar el diálogo sin hacer nada
        });

        // Acción de actualizar
        actualizarButton.addActionListener(e -> {
            actualizacionConfirmada = true;  // Marcar que se ha confirmado la actualización
            dispose();  // Cerrar el diálogo para proceder con la actualización
        });

        // Tamaño y visibilidad
        pack();
        setLocationRelativeTo(parent);
    }

    // Getter para verificar si se confirmó la actualización
    public boolean isActualizacionConfirmada() {
        return actualizacionConfirmada;
    }

    // Getters para obtener los valores seleccionados
    public String getClaseFormacion() {
        return (String) claseFormacionCB.getSelectedItem();
    }

    public String getJornadaFormacion() {
        return (String) jornadaFormacionCB.getSelectedItem();
    }

    public String getDocumentoInstructor() {
        return documentoInstructorTF.getText();
    }
}

