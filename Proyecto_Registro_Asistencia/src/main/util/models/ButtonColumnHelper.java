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

/**
 * La clase {@code ButtonColumnHelper} proporciona utilidades para agregar botones interactivos
 * dentro de columnas específicas de un {@code JTable}. Permite renderizar botones y manejar
 * eventos de edición para acciones como eliminar filas o abrir archivos Excel asociados a una fila.
 */
public class ButtonColumnHelper {

    /**
     * Renderer para botones en las celdas de una columna de un JTable.
     * Extiende {@code JButton} e implementa {@code TableCellRenderer} para personalizar la
     * apariencia de los botones dentro de la tabla.
     */
    public static class ButtonRenderer extends JButton implements TableCellRenderer {
        /**
         * Constructor que configura el botón para su uso como renderer en una tabla.
         */
        public ButtonRenderer() {
            setOpaque(true);
            // Aplica un estilo predefinido al botón usando la clase ButtonStyler
            ButtonStyler.applyPrimaryStyle(this);

        }

        /**
         * Configura el componente renderizado para la celda específica de la tabla.
         *
         * @param table      La tabla que utiliza este renderer.
         * @param value      El valor de la celda a renderizar.
         * @param isSelected Indica si la celda está seleccionada.
         * @param hasFocus   Indica si la celda tiene el foco.
         * @param row        El número de fila de la celda.
         * @param column     El número de columna de la celda.
         * @return El componente {@code JButton} configurado para renderizar la celda.
         */
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Eliminar" : value.toString());
            return this;
        }
    }

    /**
     * Editor para botones en las celdas de una columna de un JTable.
     * Extiende {@code DefaultCellEditor} para manejar eventos de clic en los botones.
     * Permite acciones como eliminar una fila seleccionada.
     */
    public static class ButtonEditor extends DefaultCellEditor {
        private String label;              // Texto del botón
        protected JButton button;          // Botón utilizado como editor
        private boolean isPushed;          // Indicador de si el botón ha sido presionado
        private JTable table;              // Referencia a la tabla que contiene el botón
        private int row;                   // Número de la fila seleccionada

        /**
         * Constructor que configura el editor del botón para una columna específica.
         *
         * @param checkBox Un {@code JCheckBox} que se pasa al constructor de {@code DefaultCellEditor}, este es un dummy component o un componente base
         *                 porque el DefaultCellEditor no ofrece un componente para botones entonces se usa un CheckBox para hacer la herencia de funcionalidad
         *                 y continuar con las funcionalidades asigando un boton adentro del metodo
         * @param table    La tabla que contiene el botón.
         */

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

        /**
         * Configura el componente editor para la celda específica de la tabla.
         *
         * @param table      La tabla que utiliza este editor.
         * @param value      El valor de la celda a editar.
         * @param isSelected Indica si la celda está seleccionada.
         * @param row        El número de fila de la celda.
         * @param column     El número de columna de la celda.
         * @return El componente {@code JButton} configurado para editar la celda.
         */
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.row = row;  // Capturar la fila actual
            label = (value == null) ? "Eliminar" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        /**
         * Retorna el valor del editor, en este caso, el texto del botón.
         *
         * @return El texto del botón.
         */
        @Override
        public Object getCellEditorValue() {
            isPushed = false;
            return label;
        }

        /**
         * Indica si la edición del botón debe detenerse.
         *
         * @return {@code true} si la edición puede detenerse; {@code false} en caso contrario.
         */
        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }

    // Método para imprimir el contenido de la tabla desde el ButtonEditor
    /**
     * Editor para botones de acción "Abrir Excel" en las celdas de una columna de un JTable.
     * Extiende {@code DefaultCellEditor} para manejar eventos de clic en los botones.
     * Permite acciones como abrir un archivo Excel asociado a la fila seleccionada.
     */
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


    // Clase para renderizar los botones de Excel
    /**
     * Renderer para botones de acción "Abrir Excel" en las celdas de una columna de un JTable.
     * Extiende {@code JButton} e implementa {@code TableCellRenderer} para personalizar
     * la apariencia de los botones de Excel dentro de la tabla.
     */
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

        /**
         * Configura el componente editor para la celda específica de la tabla.
         *
         * @param table      La tabla que utiliza este editor.
         * @param value      El valor de la celda a editar.
         * @param isSelected Indica si la celda está seleccionada.
         * @param row        El número de fila de la celda.
         * @param column     El número de columna de la celda.
         * @return El componente {@code JButton} configurado para editar la celda.
         */
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

        /**
         * Método privado que maneja la apertura del archivo Excel con la contraseña proporcionada.
         */
        private void abrirExcelConContrasena() {
            try {
                // Obtiene la fila seleccionada en la tabla
                int selectedRow = table.getSelectedRow();
                // Obtiene el mapa de asistencia correspondiente a la fila seleccionada
                Map<String, Object> asistencia = asistencias.get(selectedRow);
                // Obtiene el archivo Excel codificado en Base64
                String archivoBase64 = (String) asistencia.get("ArchivoExcel");
                // Obtiene el documento del instructor para usar como contraseña
                String documentoInstructor = (String) asistencia.get("Instructor");

                // Decodifica el archivo Base64 a un arreglo de bytes
                byte[] archivoBytes = Base64.getDecoder().decode(archivoBase64);

                // Crea un archivo temporal para guardar el archivo Excel decodificado
                Path archivoPath = Files.write(Files.createTempFile("asistencia_", ".xlsx"), archivoBytes);

                // Abre el archivo Excel con la contraseña utilizando la clase API_AsistenciasApplications
                API_AsistenciasApplications.abrirExcel(archivoPath.toString(), documentoInstructor);

            } catch (Exception ex) {
                // Imprime la traza de la excepción para depuración
                ex.printStackTrace();
                // Muestra un mensaje de error al usuario
                JOptionPane.showMessageDialog(null, "Error al abrir el archivo Excel: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

}