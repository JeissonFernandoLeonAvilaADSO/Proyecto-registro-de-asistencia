package main.util.models;

import main.util.API_Actions.API_AsistenciasApplications;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;


public class ButtonColumnHelper {

    // Clase para renderizar los botones
    public static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            ButtonStyler.applyPrimaryStyle(this); // Aplicar estilo al botón

        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Eliminar" : value.toString());
            return this;
        }
    }

    // Clase para editar los botones (permitir click)
    public static class ButtonEditor extends DefaultCellEditor {
        private String label;
        protected JButton button;
        private boolean isPushed;
        private JTable table; // Referencia a la tabla
        private int row;      // Fila seleccionada

        public ButtonEditor(JCheckBox checkBox, JTable table) {
            super(checkBox);
            this.table = table;
            button = new JButton();
            ButtonStyler.applyPrimaryStyle(button);  // Aplicar estilo al botón
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    if (row >= 0 && row < table.getRowCount()) {
                        ((DefaultTableModel) table.getModel()).removeRow(row);  // Eliminar la fila
                        imprimirContenidoTabla(table);  // Imprimir la tabla después de la eliminación
                    }
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.row = row;  // Capturar la fila actual
            label = (value == null) ? "Eliminar" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }

    // Método para imprimir el contenido de la tabla desde el ButtonEditor
    private static void imprimirContenidoTabla(JTable table) {
        DefaultTableModel modeloTabla = (DefaultTableModel) table.getModel();
        int filas = modeloTabla.getRowCount();
        int columnas = modeloTabla.getColumnCount();

        System.out.println("Contenido actual de la tabla:");

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(modeloTabla.getValueAt(i, j) + " ");
            }
            System.out.println(); // Salto de línea para cada fila
        }
    }


    // Método para imprimir el contenido de la tabla
    private static void imprimirContenidoTablaEditar(JTable table) {
        DefaultTableModel modeloTabla = (DefaultTableModel) table.getModel();
        int filas = modeloTabla.getRowCount();
        int columnas = modeloTabla.getColumnCount();

        System.out.println("Contenido actual de la tabla:");

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(modeloTabla.getValueAt(i, j) + " ");
            }
            System.out.println(); // Salto de línea para cada fila
        }
    }


    // Clase para renderizar los botones de Excel
    public static class ButtonRendererExcel extends JButton implements TableCellRenderer {
        public ButtonRendererExcel() {
            setOpaque(true);
            ButtonStyler.applyPrimaryStyle(this); // Aplicar estilo al botón
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Abrir Excel" : value.toString());
            return this;
        }
    }

    // Clase para editar los botones de Excel (permitir click)
    public static class ButtonEditorExcel extends DefaultCellEditor {
        private String label;
        protected JButton button;
        private boolean isPushed;
        private JTable table; // Referencia a la tabla
        private int row;      // Fila seleccionada
        private List<Map<String, Object>> asistencias; // Lista de asistencias con archivos Excel

        public ButtonEditorExcel(JCheckBox checkBox, JTable table, List<Map<String, Object>> asistencias) {
            super(checkBox);
            this.table = table;
            this.asistencias = asistencias; // Pasar la lista de asistencias
            button = new JButton();
            ButtonStyler.applyPrimaryStyle(button);  // Aplicar estilo al botón
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    if (row >= 0 && row < table.getRowCount()) {
                        JPasswordField passwordField = new JPasswordField();
                        Object[] message = {
                                "Ingrese la contraseña para abrir el archivo:", passwordField
                        };

                        int option = JOptionPane.showConfirmDialog(null, message, "Contraseña", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                        if (option == JOptionPane.OK_OPTION) {
                            String pass = new String(passwordField.getPassword());

                            if (pass.equals(UserSession.getInstance().getDocumento())) {
                                abrirExcelConContrasena();
                            } else {
                                JOptionPane.showMessageDialog(null, "Contraseña incorrecta", "Atención", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {
                            System.out.println("Acción cancelada por el usuario.");
                        }

                    }
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.row = row;  // Capturar la fila actual
            label = (value == null) ? "Abrir Excel" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        private void abrirExcelConContrasena() {
            try {
                // Obtener el archivo en Base64 de la fila seleccionada
                int selectedRow = table.getSelectedRow();
                Map<String, Object> asistencia = asistencias.get(selectedRow);
                String archivoBase64 = (String) asistencia.get("ArchivoExcel");
                String documentoInstructor = (String) asistencia.get("Instructor");

                // Decodificar el archivo Base64
                byte[] archivoBytes = Base64.getDecoder().decode(archivoBase64);

                // Guardar el archivo Excel en una ruta temporal
                Path archivoPath = Files.write(Files.createTempFile("asistencia_", ".xlsx"), archivoBytes);

                // Abrir el archivo con la contraseña (documento del instructor)
                API_AsistenciasApplications.abrirExcel(archivoPath.toString(), documentoInstructor);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // Método para agregar el botón "Abrir Excel" en una columna específica
    public static void agregarBotonAbrirExcel(JTable tabla, int columna, List<Map<String, Object>> asistencias) {
        TableColumn archivoColumn = tabla.getColumnModel().getColumn(columna);
        archivoColumn.setCellRenderer(new ButtonRendererExcel());
        archivoColumn.setCellEditor(new ButtonEditorExcel(new JCheckBox(), tabla, asistencias));
    }

}