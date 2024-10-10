package main.util.models;

import javax.swing.*;
import java.awt.*;

public class EditarAsociacionDialog extends JDialog {

    private JComboBox<String> claseFormacionComboBox;
    private JComboBox<String> fichaComboBox;  // Añadido para seleccionar nueva ficha
    private JButton guardarButton;
    private JButton cancelarButton;
    private String nuevaClaseFormacion;
    private Integer nuevaFicha;

    public EditarAsociacionDialog(Frame parent, String claseActual, Integer fichaActual) {
        super(parent, "Editar Asociación", true);
        setLayout(new GridLayout(3, 2, 10, 10));  // Añadido espacio entre componentes

        // Inicializar ComboBoxModels
        ComboBoxModels cbm = new ComboBoxModels();

        // Clase de formación ComboBox
        claseFormacionComboBox = new JComboBox<>();
        claseFormacionComboBox.setModel(cbm.generarComboBoxModelPorTipo("ClaseFormacion"));
        claseFormacionComboBox.setSelectedItem(claseActual); // Preseleccionar la clase actual
        add(new JLabel("Clase de Formación:"));
        add(claseFormacionComboBox);

        // Ficha ComboBox
        fichaComboBox = new JComboBox<>();
        fichaComboBox.setModel(cbm.generarComboBoxModelPorTipo("Fichas"));
        fichaComboBox.setSelectedItem(fichaActual.toString()); // Preseleccionar la ficha actual
        add(new JLabel("Nueva Ficha:"));  // Etiqueta para nueva ficha
        add(fichaComboBox);

        // Botón Guardar
        guardarButton = new JButton("Guardar");
        guardarButton.addActionListener(e -> {
            nuevaClaseFormacion = (String) claseFormacionComboBox.getSelectedItem();
            nuevaFicha = Integer.parseInt(fichaComboBox.getSelectedItem().toString());
            dispose();  // Cerrar el diálogo
        });
        add(guardarButton);

        // Botón Cancelar
        cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(e -> dispose());  // Cerrar el diálogo sin hacer cambios
        add(cancelarButton);

        pack();  // Ajustar tamaño
        setLocationRelativeTo(parent);  // Centrar el diálogo en la ventana padre
    }

    // Método para obtener la nueva clase seleccionada
    public String getNuevaClaseFormacion() {
        return nuevaClaseFormacion;
    }

    // Método para obtener la nueva ficha seleccionada
    public Integer getNuevaFicha() {
        return nuevaFicha;
    }
}
