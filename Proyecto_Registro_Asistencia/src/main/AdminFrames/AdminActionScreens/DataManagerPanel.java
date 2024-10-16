/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main.AdminFrames.AdminActionScreens;

import main.util.API_Actions.API_BuscarUsuario;
import main.util.API_Actions.API_Data.*;
import main.util.models.*;
import main.util.models.UsersModels.InstructorModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Propietario
 */
public class DataManagerPanel extends javax.swing.JPanel {

    /**
     * Creates new form DataManagerPanel
     */

    DataTables dt = new DataTables();
    public DataManagerPanel() {
        initComponents();
        aditionalConfig();

    }
    public void aditionalConfig(){
        TabPanelStyler.applyPrimaryStyle(PrincipalTabPanel);
        TabPanelStyler.applyPrimaryStyle(ProgramasFormacionTabPanel);
        TabPanelStyler.applyPrimaryStyle(FichasTabPanel);
        ButtonStyler.applyPrimaryStyle(AgregarTipoDoc);
        try {
            ComboBoxModels cbm = new ComboBoxModels();
            actualizarTipoDocTable();
            actualizarTablaRoles();
            actualizarTablaGeneros();
            actualizarTablaAmbientes();
            actualizarTablaActividades();
            actualizarTablaFichas();
            actualizarTablaAreas();
            actualizarTablaJornadas();
            actualizarTablaNivelesFormacion();
            actualizarTablaSedesFormacion();
            actualizarTablaClaseFormacion();
            AsociarClaseInstructorFichaDataFichasCB.setModel(cbm.generarComboBoxModelPorTipo("Fichas"));
            AsociarInstructorClaseFichaDataProgramaFormacionCB.setModel(cbm.generarComboBoxModelPorTipo("ClaseFormacion"));
            FichaDataProgramaFormacionCB.setModel(cbm.generarComboBoxModelPorTipo("ProgramaFormacion"));
            agregarModeloProgramaFormacion(ProgramaFormacionDataTable, API_DataProgramaFormacionApplications.obtenerProgramasFormacion());
            FichaDataJornadaFormacionCB.setModel(cbm.generarComboBoxModelPorTipo("JornadaFormacion"));
            DocumentoInstructorClaseFormacionCB.setModel(cbm.generarComboBoxModelPorTipo("JornadaFormacion"));
            NivelFormacionProgramaCB.setModel(cbm.generarComboBoxModelPorTipo("NivelFormacion"));
            SedeFormacionCB.setModel(cbm.generarComboBoxModelPorTipo("Sede"));
            AreaFormacionCB.setModel(cbm.generarComboBoxModelPorTipo("Areas"));


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void agregarModelo(JTable tabla, Map<Integer, String> datos) {
        // Crear las columnas del DefaultTableModel (agregando "Editar" y "Eliminar")
        DefaultTableModel modeloTabla = new DefaultTableModel(new Object[]{"ID", "Valor", "Editar", "Eliminar"}, 0);

        // Recorrer los datos y agregar cada fila con ID, valor principal, y botones de editar/eliminar
        for (Map.Entry<Integer, String> entry : datos.entrySet()) {
            Integer id = entry.getKey();
            String valorPrincipal = entry.getValue();
            modeloTabla.addRow(new Object[]{id, valorPrincipal, "Editar", "Eliminar"}); // Agregar ID, valor, y texto para botones
        }

        // Setear el modelo a la JTable
        tabla.setModel(modeloTabla);

        // Asignar el ButtonRenderer y ButtonEditor para las columnas de "Editar" y "Eliminar"
        TableColumn editarColumna = tabla.getColumnModel().getColumn(2); // Columna de "Editar"
        editarColumna.setCellRenderer(new ButtonColumnHelper.ButtonRenderer());
        editarColumna.setCellEditor(new ButtonColumnHelper.ButtonEditor(new JCheckBox(), tabla) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                String valor = (String) table.getValueAt(row, 1);  // Obtener el valor de la segunda columna
                System.out.println("Editando: " + valor);  // Imprimir el valor de la segunda columna
                return super.getTableCellEditorComponent(table, value, isSelected, row, column);
            }
        });

        TableColumn eliminarColumna = tabla.getColumnModel().getColumn(3); // Columna de "Eliminar"
        eliminarColumna.setCellRenderer(new ButtonColumnHelper.ButtonRenderer());
        eliminarColumna.setCellEditor(new ButtonColumnHelper.ButtonEditor(new JCheckBox(), tabla)); // Reutiliza el comportamiento ya definido para eliminar
    }



    /**
     * Método para agregar modelos de Fichas a una JTable.
     *
     * @param tabla   La JTable donde se mostrarán las fichas.
     * @param fichas  Lista de mapas que contienen los datos de las fichas.
     */
    public void agregarModeloAdicional(JTable tabla, List<Map<String, Object>> fichas) {
        // Crear el DefaultTableModel con columnas: ID, Número de Ficha, Programa, Jornada, Editar, Eliminar
        DefaultTableModel modeloTabla = new DefaultTableModel(new Object[]{
                "ID", "Número de Ficha", "Programa de Formación", "Jornada de Formación", "Editar", "Eliminar"
        }, 0) {
            // Hacer que las celdas de botones no sean editables directamente
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4 || column == 5; // Solo "Editar" y "Eliminar" son editables
            }
        };

        // Rellenar el modelo con los datos de las fichas
        for (Map<String, Object> ficha : fichas) {
            Integer idFicha = (Integer) ficha.get("ID");
            Integer numeroFicha = (Integer) ficha.get("NumeroFicha");
            String programaFormacion = (String) ficha.get("ProgramaFormacion");
            String jornadaFormacion = (String) ficha.get("JornadaFormacion");

            // Añadir una fila al modelo con los datos de la ficha y los botones
            modeloTabla.addRow(new Object[]{
                    idFicha, numeroFicha, programaFormacion, jornadaFormacion, "Editar", "Eliminar"
            });
        }

        // Setear el modelo a la JTable
        tabla.setModel(modeloTabla);

        // Configurar el tamaño de las columnas si es necesario
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        tabla.getColumnModel().getColumn(1).setPreferredWidth(100); // Número de Ficha
        tabla.getColumnModel().getColumn(2).setPreferredWidth(200); // Programa de Formación
        tabla.getColumnModel().getColumn(3).setPreferredWidth(150); // Jornada de Formación
        tabla.getColumnModel().getColumn(4).setPreferredWidth(100); // Editar
        tabla.getColumnModel().getColumn(5).setPreferredWidth(100); // Eliminar

        // Asignar el ButtonRenderer para ambas columnas "Editar" y "Eliminar"
        ButtonColumnHelper.ButtonRenderer buttonRenderer = new ButtonColumnHelper.ButtonRenderer();
        TableColumn editarColumna = tabla.getColumnModel().getColumn(4); // Columna de "Editar"
        editarColumna.setCellRenderer(buttonRenderer);

        TableColumn eliminarColumna = tabla.getColumnModel().getColumn(5); // Columna de "Eliminar"
        eliminarColumna.setCellRenderer(buttonRenderer);

        // Asignar el ButtonEditor para la columna "Editar"
        editarColumna.setCellEditor(new ButtonColumnHelper.ButtonEditor(new JCheckBox(), tabla) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                super.getTableCellEditorComponent(table, value, isSelected, row, column);
                // Configurar el botón
                button.setText((value == null) ? "Editar" : value.toString());

                // Remover ActionListeners anteriores para evitar múltiples acciones
                for (ActionListener al : button.getActionListeners()) {
                    button.removeActionListener(al);
                }

                // Agregar el ActionListener específico para "Editar"
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Obtener los datos de la fila seleccionada
                        Integer id = (Integer) table.getValueAt(row, 0);
                        Integer numeroFicha = (Integer) table.getValueAt(row, 1);
                        String programaFormacion = (String) table.getValueAt(row, 2);
                        String jornadaFormacion = (String) table.getValueAt(row, 3);

                        // Mostrar el diálogo para editar la ficha
                        EditarFichaDialog dialog = new EditarFichaDialog(
                                JOptionPane.getFrameForComponent(table),
                                id,
                                numeroFicha,
                                programaFormacion,
                                jornadaFormacion,
                                tabla
                        );
                        dialog.setVisible(true);

                        // Detener la edición para que el botón vuelva a su estado normal
                        fireEditingStopped();
                    }
                });

                return button;
            }
        });

        // Asignar el ButtonEditor para la columna "Eliminar"
        eliminarColumna.setCellEditor(new ButtonColumnHelper.ButtonEditor(new JCheckBox(), tabla) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                super.getTableCellEditorComponent(table, value, isSelected, row, column);
                // Configurar el botón
                button.setText((value == null) ? "Eliminar" : value.toString());

                // Remover ActionListeners anteriores para evitar múltiples acciones
                for (ActionListener al : button.getActionListeners()) {
                    button.removeActionListener(al);
                }

                // Agregar el ActionListener específico para "Eliminar"
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Obtener el ID de la ficha seleccionada
                        Integer idFicha = (Integer) table.getValueAt(row, 0);

                        // Confirmar eliminación
                        int confirm = JOptionPane.showConfirmDialog(
                                table,
                                "¿Está seguro de que desea eliminar la ficha con ID " + idFicha + "?",
                                "Confirmar Eliminación",
                                JOptionPane.YES_NO_OPTION
                        );

                        if (confirm == JOptionPane.YES_OPTION) {
                            // Llamar al método de ClienteAPI para eliminar la ficha
                            String respuesta = API_DataFichasApplications.eliminarFicha(idFicha);

                            if (respuesta != null && respuesta.contains("exitosamente")) {
                                // Eliminar la fila del modelo de la tabla
                                ((DefaultTableModel) table.getModel()).removeRow(row);
                                JOptionPane.showMessageDialog(table, "Ficha eliminada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            }
                            // El manejo de errores ya está gestionado en ClienteAPI
                        }

                        // Detener la edición para que el botón vuelva a su estado normal
                        fireEditingStopped();
                    }
                });

                return button;
            }
        });
    }



    public void agregarModeloClaseFormacion(JTable tabla, List<Map<String, Object>> clases) {
        // Crear el DefaultTableModel con columnas: ID, Nombre de la Clase, Jornada de formación, Editar, Eliminar
        DefaultTableModel modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre de Clase", "Jornada de Formación", "Editar", "Eliminar"}, 0) {
            // Hacer que las columnas Editar y Eliminar no sean editables directamente
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3 || column == 4;
            }
        };

        // Rellenar el modelo con los datos de las clases
        for (Map<String, Object> clase : clases) {
            Integer idClase = (Integer) clase.get("ID");
            String nombreClase = (String) clase.get("NombreClase");
            String jornadaFormacion = (String) clase.get("JornadasFormacion");

            // Añadir una fila al modelo con los datos de la clase
            modeloTabla.addRow(new Object[]{idClase, nombreClase, jornadaFormacion, "Editar", "Eliminar"});
        }

        // Setear el modelo a la JTable
        tabla.setModel(modeloTabla);

        // Asignar el ButtonRenderer y ButtonEditor para las columnas de "Editar" y "Eliminar"
        // Columna "Editar" (índice 3)
        TableColumn editarColumna = tabla.getColumnModel().getColumn(3);
        editarColumna.setCellRenderer(new ButtonColumnHelper.ButtonRenderer());
        editarColumna.setCellEditor(new ButtonColumnHelper.ButtonEditor(new JCheckBox(), tabla) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                super.getTableCellEditorComponent(table, value, isSelected, row, column);

                // Obtener los valores de la fila seleccionada
                int idClase = Integer.parseInt(table.getValueAt(row, 0).toString());
                String nombreClase = table.getValueAt(row, 1).toString();
                String jornadaFormacion = table.getValueAt(row, 2).toString();

                // Solicitar nuevos valores al usuario
                String nuevoNombreClase = JOptionPane.showInputDialog(null, "Actualizar Nombre de la Clase:", nombreClase);
                if (nuevoNombreClase == null || nuevoNombreClase.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El nombre de la clase no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                    return this.getComponent();
                }

                String nuevaJornadaFormacion = JOptionPane.showInputDialog(null, "Actualizar Jornada de Formación:", jornadaFormacion);
                if (nuevaJornadaFormacion == null || nuevaJornadaFormacion.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "La jornada de formación no puede estar vacía.", "Error", JOptionPane.ERROR_MESSAGE);
                    return this.getComponent();
                }

                // Mostrar el cuadro de diálogo de confirmación
                int opcion = JOptionPane.showConfirmDialog(
                        this.getComponent(),
                        "¿Desea actualizar la clase con los nuevos datos?\n" +
                                "Nombre de la Clase: " + nuevoNombreClase + "\n" +
                                "Jornada de Formación: " + nuevaJornadaFormacion,
                        "Confirmar actualización",
                        JOptionPane.YES_NO_OPTION
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    // Proceder a actualizar la clase
                    String respuesta = API_DataClaseFormacionApplications.actualizarClase(idClase, nuevoNombreClase, nuevaJornadaFormacion);
                    if (respuesta.contains("exitosamente")) {
                        // Actualizar la tabla con los nuevos valores
                        table.setValueAt(nuevoNombreClase, row, 1);
                        table.setValueAt(nuevaJornadaFormacion, row, 2);
                        JOptionPane.showMessageDialog(this.getComponent(), "Clase actualizada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        // Opcional: actualizar la tabla completa si es necesario
                        // actualizarTablaClaseFormacion();
                    } else {
                        JOptionPane.showMessageDialog(this.getComponent(), "No se pudo actualizar la clase.\n" + respuesta, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    // El usuario canceló la operación
                    JOptionPane.showMessageDialog(this.getComponent(), "Actualización de la clase cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                }

                return this.getComponent();
            }
        });

        // Columna "Eliminar" (índice 4)
        TableColumn eliminarColumna = tabla.getColumnModel().getColumn(4);
        eliminarColumna.setCellRenderer(new ButtonColumnHelper.ButtonRenderer());
        eliminarColumna.setCellEditor(new ButtonColumnHelper.ButtonEditor(new JCheckBox(), tabla) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                super.getTableCellEditorComponent(table, value, isSelected, row, column);

                // Obtener el ID de la clase de la fila seleccionada
                int idClase = Integer.parseInt(table.getValueAt(row, 0).toString());
                String nombreClase = table.getValueAt(row, 1).toString();

                // Confirmación de eliminación
                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "¿Está seguro de que desea eliminar la clase \"" + nombreClase + "\" con ID " + idClase + "?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    // Llamar al método para eliminar la clase
                    String respuesta = API_DataClaseFormacionApplications.eliminarClase(idClase);
                    if (respuesta.contains("exitosamente")) {
                        // Eliminar la fila del modelo de la tabla
                        ((DefaultTableModel) table.getModel()).removeRow(row);
                        JOptionPane.showMessageDialog(null, "Clase eliminada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        // Opcional: actualizar la tabla completa si es necesario
                        // actualizarTablaClaseFormacion();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo eliminar la clase.\n" + respuesta, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                return this.getComponent();
            }
        });
    }

    /**
     * Método para agregar modelos de Programa de Formación a una JTable.
     *
     * @param tabla     La JTable donde se mostrarán los programas de formación.
     * @param programas Lista de mapas que contienen los datos de los programas de formación.
     */
    public void agregarModeloProgramaFormacion(JTable tabla, List<Map<String, Object>> programas) {
        // Crear el DefaultTableModel con columnas: ID, Programa, Centro, Nivel, Área, Editar, Eliminar
        DefaultTableModel modeloTabla = new DefaultTableModel(new Object[]{
                "ID", "Programa", "Centro", "Nivel", "Área", "Editar", "Eliminar"
        }, 0) {
            // Hacer que las celdas de botones no sean editables directamente
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5 || column == 6; // Solo "Editar" y "Eliminar" son editables (para interactuar con los botones)
            }
        };

        // Rellenar el modelo con los datos de los programas
        for (Map<String, Object> programa : programas) {
            Integer idPrograma = (Integer) programa.get("ID");
            String nombrePrograma = (String) programa.get("ProgramaFormacion");
            String centroFormacion = (String) programa.get("CentroFormacion");
            String nivelFormacion = (String) programa.get("NivelFormacion");
            String area = (String) programa.get("Area");

            // Añadir una fila al modelo con los datos del programa y los botones
            modeloTabla.addRow(new Object[]{
                    idPrograma, nombrePrograma, centroFormacion,
                    nivelFormacion, area, "Editar", "Eliminar"
            });
        }

        // Setear el modelo a la JTable
        tabla.setModel(modeloTabla);

        // Configurar el tamaño de las columnas si es necesario
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        tabla.getColumnModel().getColumn(1).setPreferredWidth(200); // Programa
        tabla.getColumnModel().getColumn(2).setPreferredWidth(150); // Centro
        tabla.getColumnModel().getColumn(3).setPreferredWidth(100); // Nivel
        tabla.getColumnModel().getColumn(4).setPreferredWidth(100); // Área
        tabla.getColumnModel().getColumn(5).setPreferredWidth(100); // Editar
        tabla.getColumnModel().getColumn(6).setPreferredWidth(100); // Eliminar

        // Asignar el ButtonRenderer para ambas columnas "Editar" y "Eliminar"
        ButtonColumnHelper.ButtonRenderer buttonRenderer = new ButtonColumnHelper.ButtonRenderer();
        TableColumn editarColumna = tabla.getColumnModel().getColumn(5); // Columna de "Editar"
        editarColumna.setCellRenderer(buttonRenderer);

        TableColumn eliminarColumna = tabla.getColumnModel().getColumn(6); // Columna de "Eliminar"
        eliminarColumna.setCellRenderer(buttonRenderer);

        // Asignar el ButtonEditor para la columna "Editar"
        editarColumna.setCellEditor(new ButtonColumnHelper.ButtonEditor(new JCheckBox(), tabla) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                super.getTableCellEditorComponent(table, value, isSelected, row, column);
                // Configurar el botón
                button.setText((value == null) ? "Editar" : value.toString());

                // Remover ActionListeners anteriores para evitar múltiples acciones
                for (ActionListener al : button.getActionListeners()) {
                    button.removeActionListener(al);
                }

                // Agregar el ActionListener específico para "Editar"
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Obtener los datos de la fila seleccionada
                        Integer id = (Integer) table.getValueAt(row, 0);
                        String nombrePrograma = (String) table.getValueAt(row, 1);
                        String centroFormacion = (String) table.getValueAt(row, 2);
                        String nivelFormacion = (String) table.getValueAt(row, 3);
                        String area = (String) table.getValueAt(row, 4);

                        // Mostrar un diálogo para editar los datos
                        EditarProgramaFormacionDialog dialog = new EditarProgramaFormacionDialog(
                                JOptionPane.getFrameForComponent(table),
                                id,
                                nombrePrograma,
                                centroFormacion,
                                nivelFormacion,
                                area,
                                tabla
                        );
                        dialog.setVisible(true);

                        // Detener la edición para que el botón vuelva a su estado normal
                        fireEditingStopped();
                    }
                });

                return button;
            }
        });

        // Asignar el ButtonEditor para la columna "Eliminar"
        eliminarColumna.setCellEditor(new ButtonColumnHelper.ButtonEditor(new JCheckBox(), tabla) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                super.getTableCellEditorComponent(table, value, isSelected, row, column);
                // Configurar el botón
                button.setText((value == null) ? "Eliminar" : value.toString());

                // Remover ActionListeners anteriores para evitar múltiples acciones
                for (ActionListener al : button.getActionListeners()) {
                    button.removeActionListener(al);
                }

                // Agregar el ActionListener específico para "Eliminar"
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Obtener el ID del programa de formación seleccionado
                        Integer idProgramaFormacion = (Integer) table.getValueAt(row, 0);

                        // Confirmar eliminación
                        int confirm = JOptionPane.showConfirmDialog(
                                table,
                                "¿Está seguro de que desea eliminar el Programa de Formación con ID " + idProgramaFormacion + "?",
                                "Confirmar Eliminación",
                                JOptionPane.YES_NO_OPTION
                        );

                        if (confirm == JOptionPane.YES_OPTION) {
                            // Llamar al método de ClienteAPI para eliminar el programa de formación
                            String respuesta = API_DataProgramaFormacionApplications.eliminarProgramaFormacion(idProgramaFormacion);

                            if (respuesta != null && respuesta.contains("exitosamente")) {
                                // Eliminar la fila del modelo de la tabla
                                ((DefaultTableModel) table.getModel()).removeRow(row);
                                JOptionPane.showMessageDialog(table, "Programa de Formación eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            }
                            // El manejo de errores ya está gestionado en ClienteAPI
                        }

                        // Detener la edición para que el botón vuelva a su estado normal
                        fireEditingStopped();
                    }
                });

                return button;
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        PrincipalTabPanel = new javax.swing.JTabbedPane();
        TiposDocPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TipoDocTableData = new javax.swing.JTable();
        AgregarTipoDoc = new javax.swing.JButton();
        TipoDocumentoHolder = new javax.swing.JTextField();
        RefrescarTablaTipoDoc = new javax.swing.JButton();
        RolesPanel = new javax.swing.JPanel();
        RolHolder = new javax.swing.JTextField();
        AgregarRol = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        RolesTableData = new javax.swing.JTable();
        GenerosPanel = new javax.swing.JPanel();
        GeneroHolder = new javax.swing.JTextField();
        AgregarGenero = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        GenerosTableData = new javax.swing.JTable();
        FichasPanel = new javax.swing.JPanel();
        FichasTabPanel = new javax.swing.JTabbedPane();
        CrearFichasSubPanel = new javax.swing.JPanel();
        RefrescarTablaFichas = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        FichasDataTable = new javax.swing.JTable();
        AgregarFicha = new javax.swing.JButton();
        FichaDataProgramaFormacionCB = new javax.swing.JComboBox<>();
        FichaHolder = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        FichaDataJornadaFormacionCB = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        AsignarFIchasSubPanel = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        ClaseInstructorFichasAsociadasDataTable = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        AsociarInstructorClaseFichaDataProgramaFormacionCB = new javax.swing.JComboBox<>();
        AsociarClaseInstructorFIcha = new javax.swing.JButton();
        RefrescarTablaAsociarFichas = new javax.swing.JButton();
        AsociarClaseInstructorFichaDataFichasCB = new javax.swing.JComboBox<>();
        AsociarClaseInstructorFichaDocumentoHolder = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        ClasesFormacionPanel = new javax.swing.JPanel();
        ClaseFormacionHolder = new javax.swing.JTextField();
        AgregarClaseFormacion = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        ClaseFormacionDataTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        RefrescarTablaClaseFormacion = new javax.swing.JButton();
        DocumentoInstructorClaseFormacionCB = new javax.swing.JComboBox<>();
        AmbientesPanel = new javax.swing.JPanel();
        AmbienteHolder = new javax.swing.JTextField();
        AgregarAmbiente = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        AmbientesDataTable = new javax.swing.JTable();
        ActividadesPanel = new javax.swing.JPanel();
        ActividadHolder = new javax.swing.JTextField();
        AgregarActividad = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        ActividadesDataTable = new javax.swing.JTable();
        ProgramasFormacionPanel = new javax.swing.JPanel();
        ProgramasFormacionTabPanel = new javax.swing.JTabbedPane();
        AreasPanel = new javax.swing.JPanel();
        AreaHolder = new javax.swing.JTextField();
        AgregarArea = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        AreasDataTable = new javax.swing.JTable();
        JornadasPanel = new javax.swing.JPanel();
        JornadaHolder = new javax.swing.JTextField();
        AgregarJornadaFormacion = new javax.swing.JButton();
        jScrollPane12 = new javax.swing.JScrollPane();
        JornadasDataTable = new javax.swing.JTable();
        NivelesPanel = new javax.swing.JPanel();
        NivelFormacionHolder = new javax.swing.JTextField();
        AgregarNivelFormacion = new javax.swing.JButton();
        jScrollPane13 = new javax.swing.JScrollPane();
        NivelesDataTable = new javax.swing.JTable();
        ProgramasFormacionSubPanel = new javax.swing.JPanel();
        ProgramaFormacionHolder = new javax.swing.JTextField();
        AgregarProgramaFormacion = new javax.swing.JButton();
        jScrollPane14 = new javax.swing.JScrollPane();
        ProgramaFormacionDataTable = new javax.swing.JTable();
        NivelFormacionProgramaCB = new javax.swing.JComboBox<>();
        AreaFormacionCB = new javax.swing.JComboBox<>();
        SedeFormacionCB = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        SedesPanel = new javax.swing.JPanel();
        SedeHolder = new javax.swing.JTextField();
        AgregarSede = new javax.swing.JButton();
        jScrollPane15 = new javax.swing.JScrollPane();
        SedesDataTables = new javax.swing.JTable();
        RefrescarCombos = new javax.swing.JButton();

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        PrincipalTabPanel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        TipoDocTableData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        TipoDocTableData.setRowHeight(30);
        jScrollPane1.setViewportView(TipoDocTableData);
        if (TipoDocTableData.getColumnModel().getColumnCount() > 0) {
            TipoDocTableData.getColumnModel().getColumn(1).setPreferredWidth(150);
            TipoDocTableData.getColumnModel().getColumn(1).setMaxWidth(150);
            TipoDocTableData.getColumnModel().getColumn(2).setPreferredWidth(150);
            TipoDocTableData.getColumnModel().getColumn(2).setMaxWidth(150);
        }

        AgregarTipoDoc.setBackground(new java.awt.Color(57, 169, 0));
        AgregarTipoDoc.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        AgregarTipoDoc.setForeground(new java.awt.Color(255, 255, 255));
        AgregarTipoDoc.setText("Agregar Tipo de documento");
        AgregarTipoDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarTipoDocActionPerformed(evt);
            }
        });

        TipoDocumentoHolder.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        TipoDocumentoHolder.setForeground(new java.awt.Color(0, 0, 0));
        TipoDocumentoHolder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TipoDocumentoHolderKeyTyped(evt);
            }
        });

        RefrescarTablaTipoDoc.setBackground(new java.awt.Color(57, 169, 0));
        RefrescarTablaTipoDoc.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        RefrescarTablaTipoDoc.setForeground(new java.awt.Color(255, 255, 255));
        RefrescarTablaTipoDoc.setText("Refrescar tabla");
        RefrescarTablaTipoDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefrescarTablaTipoDocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout TiposDocPanelLayout = new javax.swing.GroupLayout(TiposDocPanel);
        TiposDocPanel.setLayout(TiposDocPanelLayout);
        TiposDocPanelLayout.setHorizontalGroup(
            TiposDocPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TiposDocPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(TiposDocPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(TiposDocPanelLayout.createSequentialGroup()
                        .addComponent(TipoDocumentoHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AgregarTipoDoc)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 159, Short.MAX_VALUE)
                .addComponent(RefrescarTablaTipoDoc)
                .addGap(18, 18, 18))
        );
        TiposDocPanelLayout.setVerticalGroup(
            TiposDocPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TiposDocPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(TiposDocPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AgregarTipoDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TipoDocumentoHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RefrescarTablaTipoDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
                .addContainerGap())
        );

        PrincipalTabPanel.addTab("Tipos de Documentos", TiposDocPanel);

        RolHolder.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        RolHolder.setForeground(new java.awt.Color(0, 0, 0));
        RolHolder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                RolHolderKeyTyped(evt);
            }
        });

        AgregarRol.setBackground(new java.awt.Color(57, 169, 0));
        AgregarRol.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        AgregarRol.setForeground(new java.awt.Color(255, 255, 255));
        AgregarRol.setText("Agregar Rol");
        AgregarRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarRolActionPerformed(evt);
            }
        });

        RolesTableData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        RolesTableData.setRowHeight(30);
        jScrollPane2.setViewportView(RolesTableData);
        if (RolesTableData.getColumnModel().getColumnCount() > 0) {
            RolesTableData.getColumnModel().getColumn(1).setPreferredWidth(150);
            RolesTableData.getColumnModel().getColumn(1).setMaxWidth(150);
            RolesTableData.getColumnModel().getColumn(2).setPreferredWidth(150);
            RolesTableData.getColumnModel().getColumn(2).setMaxWidth(150);
        }

        javax.swing.GroupLayout RolesPanelLayout = new javax.swing.GroupLayout(RolesPanel);
        RolesPanel.setLayout(RolesPanelLayout);
        RolesPanelLayout.setHorizontalGroup(
            RolesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RolesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(RolesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2)
                    .addGroup(RolesPanelLayout.createSequentialGroup()
                        .addComponent(RolHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AgregarRol)))
                .addContainerGap(445, Short.MAX_VALUE))
        );
        RolesPanelLayout.setVerticalGroup(
            RolesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RolesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(RolesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AgregarRol, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RolHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(108, Short.MAX_VALUE))
        );

        PrincipalTabPanel.addTab("Roles", RolesPanel);

        GeneroHolder.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        GeneroHolder.setForeground(new java.awt.Color(0, 0, 0));
        GeneroHolder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                GeneroHolderKeyTyped(evt);
            }
        });

        AgregarGenero.setBackground(new java.awt.Color(57, 169, 0));
        AgregarGenero.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        AgregarGenero.setForeground(new java.awt.Color(255, 255, 255));
        AgregarGenero.setText("Agregar Genero");
        AgregarGenero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarGeneroActionPerformed(evt);
            }
        });

        GenerosTableData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        GenerosTableData.setRowHeight(30);
        jScrollPane3.setViewportView(GenerosTableData);
        if (GenerosTableData.getColumnModel().getColumnCount() > 0) {
            GenerosTableData.getColumnModel().getColumn(1).setPreferredWidth(150);
            GenerosTableData.getColumnModel().getColumn(1).setMaxWidth(150);
            GenerosTableData.getColumnModel().getColumn(2).setPreferredWidth(150);
            GenerosTableData.getColumnModel().getColumn(2).setMaxWidth(150);
        }

        javax.swing.GroupLayout GenerosPanelLayout = new javax.swing.GroupLayout(GenerosPanel);
        GenerosPanel.setLayout(GenerosPanelLayout);
        GenerosPanelLayout.setHorizontalGroup(
            GenerosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GenerosPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(GenerosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane3)
                    .addGroup(GenerosPanelLayout.createSequentialGroup()
                        .addComponent(GeneroHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AgregarGenero)))
                .addContainerGap(417, Short.MAX_VALUE))
        );
        GenerosPanelLayout.setVerticalGroup(
            GenerosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GenerosPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(GenerosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AgregarGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GeneroHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(108, Short.MAX_VALUE))
        );

        PrincipalTabPanel.addTab("Generos", GenerosPanel);

        FichasTabPanel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        RefrescarTablaFichas.setBackground(new java.awt.Color(57, 169, 0));
        RefrescarTablaFichas.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        RefrescarTablaFichas.setForeground(new java.awt.Color(255, 255, 255));
        RefrescarTablaFichas.setText("Refrescar tabla");
        RefrescarTablaFichas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefrescarTablaFichasActionPerformed(evt);
            }
        });

        FichasDataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        FichasDataTable.setRowHeight(30);
        jScrollPane4.setViewportView(FichasDataTable);
        if (FichasDataTable.getColumnModel().getColumnCount() > 0) {
            FichasDataTable.getColumnModel().getColumn(1).setPreferredWidth(150);
            FichasDataTable.getColumnModel().getColumn(1).setMaxWidth(150);
            FichasDataTable.getColumnModel().getColumn(2).setPreferredWidth(150);
            FichasDataTable.getColumnModel().getColumn(2).setMaxWidth(150);
        }

        AgregarFicha.setBackground(new java.awt.Color(57, 169, 0));
        AgregarFicha.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        AgregarFicha.setForeground(new java.awt.Color(255, 255, 255));
        AgregarFicha.setText("Agregar Ficha");
        AgregarFicha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarFichaActionPerformed(evt);
            }
        });

        FichaDataProgramaFormacionCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        FichaHolder.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        FichaHolder.setForeground(new java.awt.Color(0, 0, 0));
        FichaHolder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FichaHolderKeyTyped(evt);
            }
        });

        jLabel2.setText("Ficha Nueva");

        jLabel1.setText("Programa de Formacion");

        FichaDataJornadaFormacionCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setText("Jornada de formacion");

        javax.swing.GroupLayout CrearFichasSubPanelLayout = new javax.swing.GroupLayout(CrearFichasSubPanel);
        CrearFichasSubPanel.setLayout(CrearFichasSubPanelLayout);
        CrearFichasSubPanelLayout.setHorizontalGroup(
            CrearFichasSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CrearFichasSubPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CrearFichasSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4)
                    .addGroup(CrearFichasSubPanelLayout.createSequentialGroup()
                        .addGroup(CrearFichasSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(FichaHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CrearFichasSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(FichaDataJornadaFormacionCB, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CrearFichasSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(CrearFichasSubPanelLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(RefrescarTablaFichas))
                            .addGroup(CrearFichasSubPanelLayout.createSequentialGroup()
                                .addComponent(FichaDataProgramaFormacionCB, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(AgregarFicha)))))
                .addContainerGap(167, Short.MAX_VALUE))
        );
        CrearFichasSubPanelLayout.setVerticalGroup(
            CrearFichasSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CrearFichasSubPanelLayout.createSequentialGroup()
                .addGroup(CrearFichasSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CrearFichasSubPanelLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(CrearFichasSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel6)
                            .addComponent(jLabel1)))
                    .addGroup(CrearFichasSubPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(RefrescarTablaFichas)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CrearFichasSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FichaHolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FichaDataProgramaFormacionCB, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FichaDataJornadaFormacionCB, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AgregarFicha, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(251, Short.MAX_VALUE))
        );

        FichasTabPanel.addTab("Crear FIcha", CrearFichasSubPanel);

        jLabel10.setText("Ficha");

        ClaseInstructorFichasAsociadasDataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "NombreClase", "Editar", "Eliminar"
            }
        ));
        ClaseInstructorFichasAsociadasDataTable.setRowHeight(30);
        jScrollPane8.setViewportView(ClaseInstructorFichasAsociadasDataTable);
        if (ClaseInstructorFichasAsociadasDataTable.getColumnModel().getColumnCount() > 0) {
            ClaseInstructorFichasAsociadasDataTable.getColumnModel().getColumn(1).setPreferredWidth(150);
            ClaseInstructorFichasAsociadasDataTable.getColumnModel().getColumn(1).setMaxWidth(150);
        }

        jLabel11.setText("Clase de Formacion");

        AsociarInstructorClaseFichaDataProgramaFormacionCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        AsociarClaseInstructorFIcha.setBackground(new java.awt.Color(57, 169, 0));
        AsociarClaseInstructorFIcha.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        AsociarClaseInstructorFIcha.setForeground(new java.awt.Color(255, 255, 255));
        AsociarClaseInstructorFIcha.setText("Asociar Ficha");
        AsociarClaseInstructorFIcha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AsociarClaseInstructorFIchaActionPerformed(evt);
            }
        });

        RefrescarTablaAsociarFichas.setBackground(new java.awt.Color(57, 169, 0));
        RefrescarTablaAsociarFichas.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        RefrescarTablaAsociarFichas.setForeground(new java.awt.Color(255, 255, 255));
        RefrescarTablaAsociarFichas.setText("Refrescar tabla");
        RefrescarTablaAsociarFichas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefrescarTablaAsociarFichasActionPerformed(evt);
            }
        });

        AsociarClaseInstructorFichaDataFichasCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        AsociarClaseInstructorFichaDataFichasCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AsociarClaseInstructorFichaDataFichasCBActionPerformed(evt);
            }
        });

        AsociarClaseInstructorFichaDocumentoHolder.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        AsociarClaseInstructorFichaDocumentoHolder.setForeground(new java.awt.Color(0, 0, 0));

        jLabel12.setText("Documento Instructor");

        javax.swing.GroupLayout AsignarFIchasSubPanelLayout = new javax.swing.GroupLayout(AsignarFIchasSubPanel);
        AsignarFIchasSubPanel.setLayout(AsignarFIchasSubPanelLayout);
        AsignarFIchasSubPanelLayout.setHorizontalGroup(
            AsignarFIchasSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AsignarFIchasSubPanelLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(AsignarFIchasSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AsignarFIchasSubPanelLayout.createSequentialGroup()
                        .addGroup(AsignarFIchasSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(AsignarFIchasSubPanelLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(171, 171, 171)
                                .addComponent(jLabel12)
                                .addGap(193, 193, 193))
                            .addGroup(AsignarFIchasSubPanelLayout.createSequentialGroup()
                                .addComponent(AsociarClaseInstructorFichaDataFichasCB, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(AsociarClaseInstructorFichaDocumentoHolder)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(AsignarFIchasSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(AsociarInstructorClaseFichaDataProgramaFormacionCB, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(AsignarFIchasSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(AsociarClaseInstructorFIcha, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(RefrescarTablaAsociarFichas)))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 935, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(92, Short.MAX_VALUE))
        );
        AsignarFIchasSubPanelLayout.setVerticalGroup(
            AsignarFIchasSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AsignarFIchasSubPanelLayout.createSequentialGroup()
                .addGroup(AsignarFIchasSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(AsignarFIchasSubPanelLayout.createSequentialGroup()
                        .addGroup(AsignarFIchasSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(AsignarFIchasSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel12)
                                .addComponent(jLabel10))
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(AsignarFIchasSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AsociarClaseInstructorFichaDocumentoHolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AsociarClaseInstructorFichaDataFichasCB, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AsociarInstructorClaseFichaDataProgramaFormacionCB, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(AsignarFIchasSubPanelLayout.createSequentialGroup()
                        .addComponent(RefrescarTablaAsociarFichas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AsociarClaseInstructorFIcha, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(222, Short.MAX_VALUE))
        );

        FichasTabPanel.addTab("Asociar Ficha", AsignarFIchasSubPanel);

        javax.swing.GroupLayout FichasPanelLayout = new javax.swing.GroupLayout(FichasPanel);
        FichasPanel.setLayout(FichasPanelLayout);
        FichasPanelLayout.setHorizontalGroup(
            FichasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FichasPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(FichasTabPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1061, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        FichasPanelLayout.setVerticalGroup(
            FichasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FichasPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(FichasTabPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 627, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PrincipalTabPanel.addTab("Fichas", FichasPanel);

        ClaseFormacionHolder.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ClaseFormacionHolder.setForeground(new java.awt.Color(0, 0, 0));
        ClaseFormacionHolder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ClaseFormacionHolderKeyTyped(evt);
            }
        });

        AgregarClaseFormacion.setBackground(new java.awt.Color(57, 169, 0));
        AgregarClaseFormacion.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        AgregarClaseFormacion.setForeground(new java.awt.Color(255, 255, 255));
        AgregarClaseFormacion.setText("Agregar Clase de Formacion");
        AgregarClaseFormacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarClaseFormacionActionPerformed(evt);
            }
        });

        ClaseFormacionDataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        ClaseFormacionDataTable.setRowHeight(30);
        jScrollPane5.setViewportView(ClaseFormacionDataTable);
        if (ClaseFormacionDataTable.getColumnModel().getColumnCount() > 0) {
            ClaseFormacionDataTable.getColumnModel().getColumn(1).setPreferredWidth(150);
            ClaseFormacionDataTable.getColumnModel().getColumn(1).setMaxWidth(150);
            ClaseFormacionDataTable.getColumnModel().getColumn(2).setPreferredWidth(150);
            ClaseFormacionDataTable.getColumnModel().getColumn(2).setMaxWidth(150);
        }

        jLabel3.setText("Nombre de la nueva clase de formacion");

        jLabel4.setText("Jornada de formacion");

        RefrescarTablaClaseFormacion.setBackground(new java.awt.Color(57, 169, 0));
        RefrescarTablaClaseFormacion.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        RefrescarTablaClaseFormacion.setForeground(new java.awt.Color(255, 255, 255));
        RefrescarTablaClaseFormacion.setText("Refrescar tabla");
        RefrescarTablaClaseFormacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefrescarTablaClaseFormacionActionPerformed(evt);
            }
        });

        DocumentoInstructorClaseFormacionCB.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        DocumentoInstructorClaseFormacionCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout ClasesFormacionPanelLayout = new javax.swing.GroupLayout(ClasesFormacionPanel);
        ClasesFormacionPanel.setLayout(ClasesFormacionPanelLayout);
        ClasesFormacionPanelLayout.setHorizontalGroup(
            ClasesFormacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ClasesFormacionPanelLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(ClasesFormacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(ClasesFormacionPanelLayout.createSequentialGroup()
                        .addGroup(ClasesFormacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ClaseFormacionHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ClasesFormacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ClasesFormacionPanelLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(RefrescarTablaClaseFormacion, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ClasesFormacionPanelLayout.createSequentialGroup()
                                .addComponent(DocumentoInstructorClaseFormacionCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(AgregarClaseFormacion))))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 995, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );
        ClasesFormacionPanelLayout.setVerticalGroup(
            ClasesFormacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ClasesFormacionPanelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(ClasesFormacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ClasesFormacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4))
                    .addComponent(RefrescarTablaClaseFormacion, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ClasesFormacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ClaseFormacionHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AgregarClaseFormacion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DocumentoInstructorClaseFormacionCB, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        PrincipalTabPanel.addTab("Clases de Formacion", ClasesFormacionPanel);

        AmbienteHolder.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        AmbienteHolder.setForeground(new java.awt.Color(0, 0, 0));
        AmbienteHolder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                AmbienteHolderKeyTyped(evt);
            }
        });

        AgregarAmbiente.setBackground(new java.awt.Color(57, 169, 0));
        AgregarAmbiente.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        AgregarAmbiente.setForeground(new java.awt.Color(255, 255, 255));
        AgregarAmbiente.setText("Agregar Ambiente");
        AgregarAmbiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarAmbienteActionPerformed(evt);
            }
        });

        AmbientesDataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        AmbientesDataTable.setRowHeight(30);
        jScrollPane6.setViewportView(AmbientesDataTable);
        if (AmbientesDataTable.getColumnModel().getColumnCount() > 0) {
            AmbientesDataTable.getColumnModel().getColumn(1).setPreferredWidth(150);
            AmbientesDataTable.getColumnModel().getColumn(1).setMaxWidth(150);
            AmbientesDataTable.getColumnModel().getColumn(2).setPreferredWidth(150);
            AmbientesDataTable.getColumnModel().getColumn(2).setMaxWidth(150);
        }

        javax.swing.GroupLayout AmbientesPanelLayout = new javax.swing.GroupLayout(AmbientesPanel);
        AmbientesPanel.setLayout(AmbientesPanelLayout);
        AmbientesPanelLayout.setHorizontalGroup(
            AmbientesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AmbientesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AmbientesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane6)
                    .addGroup(AmbientesPanelLayout.createSequentialGroup()
                        .addComponent(AmbienteHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AgregarAmbiente)))
                .addContainerGap(401, Short.MAX_VALUE))
        );
        AmbientesPanelLayout.setVerticalGroup(
            AmbientesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AmbientesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AmbientesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AgregarAmbiente, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AmbienteHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(108, Short.MAX_VALUE))
        );

        PrincipalTabPanel.addTab("Ambientes", AmbientesPanel);

        ActividadHolder.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ActividadHolder.setForeground(new java.awt.Color(0, 0, 0));
        ActividadHolder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ActividadHolderKeyTyped(evt);
            }
        });

        AgregarActividad.setBackground(new java.awt.Color(57, 169, 0));
        AgregarActividad.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        AgregarActividad.setForeground(new java.awt.Color(255, 255, 255));
        AgregarActividad.setText("Agregar Actividad");
        AgregarActividad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarActividadActionPerformed(evt);
            }
        });

        ActividadesDataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        ActividadesDataTable.setRowHeight(30);
        jScrollPane7.setViewportView(ActividadesDataTable);
        if (ActividadesDataTable.getColumnModel().getColumnCount() > 0) {
            ActividadesDataTable.getColumnModel().getColumn(1).setPreferredWidth(150);
            ActividadesDataTable.getColumnModel().getColumn(1).setMaxWidth(150);
            ActividadesDataTable.getColumnModel().getColumn(2).setPreferredWidth(150);
            ActividadesDataTable.getColumnModel().getColumn(2).setMaxWidth(150);
        }

        javax.swing.GroupLayout ActividadesPanelLayout = new javax.swing.GroupLayout(ActividadesPanel);
        ActividadesPanel.setLayout(ActividadesPanelLayout);
        ActividadesPanelLayout.setHorizontalGroup(
            ActividadesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ActividadesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ActividadesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane7)
                    .addGroup(ActividadesPanelLayout.createSequentialGroup()
                        .addComponent(ActividadHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AgregarActividad)))
                .addContainerGap(404, Short.MAX_VALUE))
        );
        ActividadesPanelLayout.setVerticalGroup(
            ActividadesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ActividadesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ActividadesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AgregarActividad, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ActividadHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(108, Short.MAX_VALUE))
        );

        PrincipalTabPanel.addTab("Actividades", ActividadesPanel);

        ProgramasFormacionTabPanel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        AreaHolder.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        AreaHolder.setForeground(new java.awt.Color(0, 0, 0));
        AreaHolder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                AreaHolderKeyTyped(evt);
            }
        });

        AgregarArea.setBackground(new java.awt.Color(57, 169, 0));
        AgregarArea.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        AgregarArea.setForeground(new java.awt.Color(255, 255, 255));
        AgregarArea.setText("Agregar Area");
        AgregarArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarAreaActionPerformed(evt);
            }
        });

        AreasDataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        AreasDataTable.setRowHeight(30);
        jScrollPane11.setViewportView(AreasDataTable);
        if (AreasDataTable.getColumnModel().getColumnCount() > 0) {
            AreasDataTable.getColumnModel().getColumn(1).setPreferredWidth(150);
            AreasDataTable.getColumnModel().getColumn(1).setMaxWidth(150);
            AreasDataTable.getColumnModel().getColumn(2).setPreferredWidth(150);
            AreasDataTable.getColumnModel().getColumn(2).setMaxWidth(150);
        }

        javax.swing.GroupLayout AreasPanelLayout = new javax.swing.GroupLayout(AreasPanel);
        AreasPanel.setLayout(AreasPanelLayout);
        AreasPanelLayout.setHorizontalGroup(
            AreasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AreasPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AreasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane11)
                    .addGroup(AreasPanelLayout.createSequentialGroup()
                        .addComponent(AreaHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AgregarArea)))
                .addContainerGap(424, Short.MAX_VALUE))
        );
        AreasPanelLayout.setVerticalGroup(
            AreasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AreasPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AreasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AgregarArea, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AreaHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(182, Short.MAX_VALUE))
        );

        ProgramasFormacionTabPanel.addTab("Areas", AreasPanel);

        JornadaHolder.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        JornadaHolder.setForeground(new java.awt.Color(0, 0, 0));
        JornadaHolder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                JornadaHolderKeyTyped(evt);
            }
        });

        AgregarJornadaFormacion.setBackground(new java.awt.Color(57, 169, 0));
        AgregarJornadaFormacion.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        AgregarJornadaFormacion.setForeground(new java.awt.Color(255, 255, 255));
        AgregarJornadaFormacion.setText("Agregar Jornada de Formacion");
        AgregarJornadaFormacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarJornadaFormacionActionPerformed(evt);
            }
        });

        JornadasDataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        JornadasDataTable.setRowHeight(30);
        jScrollPane12.setViewportView(JornadasDataTable);
        if (JornadasDataTable.getColumnModel().getColumnCount() > 0) {
            JornadasDataTable.getColumnModel().getColumn(1).setPreferredWidth(150);
            JornadasDataTable.getColumnModel().getColumn(1).setMaxWidth(150);
            JornadasDataTable.getColumnModel().getColumn(2).setPreferredWidth(150);
            JornadasDataTable.getColumnModel().getColumn(2).setMaxWidth(150);
        }

        javax.swing.GroupLayout JornadasPanelLayout = new javax.swing.GroupLayout(JornadasPanel);
        JornadasPanel.setLayout(JornadasPanelLayout);
        JornadasPanelLayout.setHorizontalGroup(
            JornadasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JornadasPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JornadasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane12)
                    .addGroup(JornadasPanelLayout.createSequentialGroup()
                        .addComponent(JornadaHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AgregarJornadaFormacion)))
                .addContainerGap(302, Short.MAX_VALUE))
        );
        JornadasPanelLayout.setVerticalGroup(
            JornadasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JornadasPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JornadasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AgregarJornadaFormacion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JornadaHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(182, Short.MAX_VALUE))
        );

        ProgramasFormacionTabPanel.addTab("Jornadas de Formacion", JornadasPanel);

        NivelFormacionHolder.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        NivelFormacionHolder.setForeground(new java.awt.Color(0, 0, 0));
        NivelFormacionHolder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                NivelFormacionHolderKeyTyped(evt);
            }
        });

        AgregarNivelFormacion.setBackground(new java.awt.Color(57, 169, 0));
        AgregarNivelFormacion.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        AgregarNivelFormacion.setForeground(new java.awt.Color(255, 255, 255));
        AgregarNivelFormacion.setText("Agregar Nivel de Formacion");
        AgregarNivelFormacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarNivelFormacionActionPerformed(evt);
            }
        });

        NivelesDataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        NivelesDataTable.setRowHeight(30);
        jScrollPane13.setViewportView(NivelesDataTable);
        if (NivelesDataTable.getColumnModel().getColumnCount() > 0) {
            NivelesDataTable.getColumnModel().getColumn(1).setPreferredWidth(150);
            NivelesDataTable.getColumnModel().getColumn(1).setMaxWidth(150);
            NivelesDataTable.getColumnModel().getColumn(2).setPreferredWidth(150);
            NivelesDataTable.getColumnModel().getColumn(2).setMaxWidth(150);
        }

        javax.swing.GroupLayout NivelesPanelLayout = new javax.swing.GroupLayout(NivelesPanel);
        NivelesPanel.setLayout(NivelesPanelLayout);
        NivelesPanelLayout.setHorizontalGroup(
            NivelesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NivelesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(NivelesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane13)
                    .addGroup(NivelesPanelLayout.createSequentialGroup()
                        .addComponent(NivelFormacionHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AgregarNivelFormacion)))
                .addContainerGap(321, Short.MAX_VALUE))
        );
        NivelesPanelLayout.setVerticalGroup(
            NivelesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NivelesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(NivelesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AgregarNivelFormacion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NivelFormacionHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(182, Short.MAX_VALUE))
        );

        ProgramasFormacionTabPanel.addTab("Niveles de Formacion", NivelesPanel);

        ProgramaFormacionHolder.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ProgramaFormacionHolder.setForeground(new java.awt.Color(0, 0, 0));
        ProgramaFormacionHolder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ProgramaFormacionHolderKeyTyped(evt);
            }
        });

        AgregarProgramaFormacion.setBackground(new java.awt.Color(57, 169, 0));
        AgregarProgramaFormacion.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        AgregarProgramaFormacion.setForeground(new java.awt.Color(255, 255, 255));
        AgregarProgramaFormacion.setText("Agregar Programa de Formacion");
        AgregarProgramaFormacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarProgramaFormacionActionPerformed(evt);
            }
        });

        ProgramaFormacionDataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        ProgramaFormacionDataTable.setRowHeight(30);
        jScrollPane14.setViewportView(ProgramaFormacionDataTable);
        if (ProgramaFormacionDataTable.getColumnModel().getColumnCount() > 0) {
            ProgramaFormacionDataTable.getColumnModel().getColumn(1).setPreferredWidth(150);
            ProgramaFormacionDataTable.getColumnModel().getColumn(1).setMaxWidth(150);
            ProgramaFormacionDataTable.getColumnModel().getColumn(2).setPreferredWidth(150);
            ProgramaFormacionDataTable.getColumnModel().getColumn(2).setMaxWidth(150);
        }

        NivelFormacionProgramaCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        AreaFormacionCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        SedeFormacionCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("Nuevo programa de formacion");

        jLabel7.setText("Nivel de formacion");

        jLabel8.setText("Centro de formacion");

        jLabel9.setText("Area de formacion");

        javax.swing.GroupLayout ProgramasFormacionSubPanelLayout = new javax.swing.GroupLayout(ProgramasFormacionSubPanel);
        ProgramasFormacionSubPanel.setLayout(ProgramasFormacionSubPanelLayout);
        ProgramasFormacionSubPanelLayout.setHorizontalGroup(
            ProgramasFormacionSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProgramasFormacionSubPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ProgramasFormacionSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane14)
                    .addGroup(ProgramasFormacionSubPanelLayout.createSequentialGroup()
                        .addGroup(ProgramasFormacionSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(ProgramaFormacionHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(ProgramasFormacionSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ProgramasFormacionSubPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(NivelFormacionProgramaCB, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ProgramasFormacionSubPanelLayout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel7)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ProgramasFormacionSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SedeFormacionCB, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ProgramasFormacionSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(ProgramasFormacionSubPanelLayout.createSequentialGroup()
                                .addComponent(AreaFormacionCB, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(AgregarProgramaFormacion)))
                        .addGap(0, 1, Short.MAX_VALUE)))
                .addContainerGap())
        );
        ProgramasFormacionSubPanelLayout.setVerticalGroup(
            ProgramasFormacionSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ProgramasFormacionSubPanelLayout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addGroup(ProgramasFormacionSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ProgramasFormacionSubPanelLayout.createSequentialGroup()
                        .addGroup(ProgramasFormacionSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addComponent(ProgramaFormacionHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ProgramasFormacionSubPanelLayout.createSequentialGroup()
                        .addGroup(ProgramasFormacionSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(ProgramasFormacionSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(NivelFormacionProgramaCB, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ProgramasFormacionSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(SedeFormacionCB, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(AreaFormacionCB, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(AgregarProgramaFormacion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        ProgramasFormacionTabPanel.addTab("Programas de Formacion", ProgramasFormacionSubPanel);

        SedeHolder.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        SedeHolder.setForeground(new java.awt.Color(0, 0, 0));
        SedeHolder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                SedeHolderKeyTyped(evt);
            }
        });

        AgregarSede.setBackground(new java.awt.Color(57, 169, 0));
        AgregarSede.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        AgregarSede.setForeground(new java.awt.Color(255, 255, 255));
        AgregarSede.setText("Agregar Sede");
        AgregarSede.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarSedeActionPerformed(evt);
            }
        });

        SedesDataTables.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        SedesDataTables.setRowHeight(30);
        jScrollPane15.setViewportView(SedesDataTables);
        if (SedesDataTables.getColumnModel().getColumnCount() > 0) {
            SedesDataTables.getColumnModel().getColumn(1).setPreferredWidth(150);
            SedesDataTables.getColumnModel().getColumn(1).setMaxWidth(150);
            SedesDataTables.getColumnModel().getColumn(2).setPreferredWidth(150);
            SedesDataTables.getColumnModel().getColumn(2).setMaxWidth(150);
        }

        javax.swing.GroupLayout SedesPanelLayout = new javax.swing.GroupLayout(SedesPanel);
        SedesPanel.setLayout(SedesPanelLayout);
        SedesPanelLayout.setHorizontalGroup(
            SedesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SedesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SedesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane15)
                    .addGroup(SedesPanelLayout.createSequentialGroup()
                        .addComponent(SedeHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AgregarSede)))
                .addContainerGap(422, Short.MAX_VALUE))
        );
        SedesPanelLayout.setVerticalGroup(
            SedesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SedesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SedesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AgregarSede, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SedeHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(182, Short.MAX_VALUE))
        );

        ProgramasFormacionTabPanel.addTab("Sedes", SedesPanel);

        javax.swing.GroupLayout ProgramasFormacionPanelLayout = new javax.swing.GroupLayout(ProgramasFormacionPanel);
        ProgramasFormacionPanel.setLayout(ProgramasFormacionPanelLayout);
        ProgramasFormacionPanelLayout.setHorizontalGroup(
            ProgramasFormacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProgramasFormacionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ProgramasFormacionTabPanel)
                .addContainerGap())
        );
        ProgramasFormacionPanelLayout.setVerticalGroup(
            ProgramasFormacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProgramasFormacionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ProgramasFormacionTabPanel)
                .addContainerGap())
        );

        PrincipalTabPanel.addTab("Programas de Formacion", ProgramasFormacionPanel);

        RefrescarCombos.setBackground(new java.awt.Color(57, 169, 0));
        RefrescarCombos.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        RefrescarCombos.setForeground(new java.awt.Color(255, 255, 255));
        RefrescarCombos.setText("Refrescar comboBox");
        RefrescarCombos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefrescarCombosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(PrincipalTabPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1022, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RefrescarCombos, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(RefrescarCombos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PrincipalTabPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    public void refrescarTablaClaseFormacion() {
        try {
            DataTables modClaseFormacion = new DataTables();
            // Llamada para obtener las clases actualizadas
            List<Map<String, Object>> clasesActualizadas = modClaseFormacion.obtenerClases();

            // Actualizar la tabla con los nuevos datos
//            agregarModeloClaseFormacion(ClaseFormacionDataTable, API_DataClaseFormacionApplications.obtenerClasesConInstructor());
        } catch (Exception e) {
            System.out.println("Error al refrescar la tabla: " + e.getMessage());
        }
    }

    private void FichaNuevaHolderKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FichaNuevaHolderKeyTyped

    }//GEN-LAST:event_FichaNuevaHolderKeyTyped

    private void AgregarSedeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarSedeActionPerformed
        API_DataSedeApplications SedeApplications = new API_DataSedeApplications();
        String resultadoCrear = SedeApplications.crearSede(SedeHolder.getText());
        actualizarTablaSedesFormacion();

        JOptionPane.showMessageDialog(null, resultadoCrear, "Resultado", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_AgregarSedeActionPerformed

    private void AgregarProgramaFormacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarProgramaFormacionActionPerformed
        // 1. Recopilar los datos de los campos de texto
        String programaFormacion = ProgramaFormacionHolder.getText().trim();
        String centroFormacion = SedeFormacionCB.getSelectedItem().toString().trim();
        String nivelFormacion = NivelFormacionProgramaCB.getSelectedItem().toString().trim();
        String area = AreaFormacionCB.getSelectedItem().toString().trim();

        // 2. Validar que todos los campos estén llenos
        if (programaFormacion.isEmpty()
                || centroFormacion.isEmpty()
                || nivelFormacion.isEmpty()
                || area.isEmpty()
                || ProgramaFormacionHolder.getText().trim().equals("Seleccionar...")
                || SedeFormacionCB.getSelectedItem().toString().trim().equals("Seleccionar...")
                || NivelFormacionProgramaCB.getSelectedItem().toString().trim().equals("Seleccionar...")
                || AreaFormacionCB.getSelectedItem().toString().trim().equals("Seleccionar...")){
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 3. Confirmar la creación del Programa de Formación
        String mensajeConfirmacion = String.format(
                "¿Desea crear el Programa de Formación con los siguientes datos?\n\n" +
                        "Programa: %s\n" +
                        "Centro de Formación: %s\n" +
                        "Nivel: %s\n" +
                        "Área: %s",
                programaFormacion, centroFormacion, nivelFormacion, area
        );

        int confirm = JOptionPane.showConfirmDialog(this,
                mensajeConfirmacion,
                "Confirmar Creación",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            // El usuario canceló la operación
            return;
        }

        // 4. Llamar al método para crear el Programa de Formación
        String respuesta = API_DataProgramaFormacionApplications.crearProgramaFormacion(programaFormacion, centroFormacion, nivelFormacion, area);

        // 5. Manejar la respuesta (esto ya se hace dentro del método ClienteAPI, pero puedes añadir lógica adicional si es necesario)
        // Por ejemplo, si la creación fue exitosa, podrías refrescar una tabla o limpiar los campos de entrada

        // 6. Opcional: Limpiar los campos después de una creación exitosa
        if (respuesta != null && respuesta.contains("exitosamente")) {
            ProgramaFormacionHolder.setText("");
            SedeFormacionCB.setSelectedIndex(0);
            NivelFormacionProgramaCB.setSelectedIndex(0);
            AreaFormacionCB.setSelectedIndex(0);
            actualizarTablaProgramaFormacion();
        }
    }//GEN-LAST:event_AgregarProgramaFormacionActionPerformed

    private void AgregarNivelFormacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarNivelFormacionActionPerformed
        API_DataNivelFormacionApplications NivelFormacion = new API_DataNivelFormacionApplications();
        String resultadoCrear = NivelFormacion.crearNivelFormacion(NivelFormacionHolder.getText());
        actualizarTablaNivelesFormacion();
        NivelFormacionHolder.setText("");
        JOptionPane.showMessageDialog(null, resultadoCrear, "Resultado", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_AgregarNivelFormacionActionPerformed

    private void AgregarJornadaFormacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarJornadaFormacionActionPerformed
        API_DataJornadaApplications JornadaApplications = new API_DataJornadaApplications();
        String resultadoCrear = JornadaApplications.crearJornada(JornadaHolder.getText());
        actualizarTablaJornadas();
        JornadaHolder.setText("");
        JOptionPane.showMessageDialog(null, resultadoCrear, "Resultado", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_AgregarJornadaFormacionActionPerformed

    private void AgregarAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarAreaActionPerformed
        API_DataAreasApplications AreasApplications = new API_DataAreasApplications();
        String resultadoCrear = AreasApplications.crearArea(AreaHolder.getText());
        actualizarTablaAreas();
        AreaHolder.setText("");
        JOptionPane.showMessageDialog(null, resultadoCrear, "Resultado", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_AgregarAreaActionPerformed

    private void AgregarActividadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarActividadActionPerformed
        API_DataActividadApplications ActividadApplications = new API_DataActividadApplications();
        String resultadoCrear = ActividadApplications.crearActividad(ActividadHolder.getText());
        actualizarTablaActividades();
        ActividadHolder.setText("");
        JOptionPane.showMessageDialog(null, resultadoCrear, "Resultado", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_AgregarActividadActionPerformed

    private void AgregarAmbienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarAmbienteActionPerformed
        API_DataAmbientesApplications AmbientesApplications = new API_DataAmbientesApplications(); // Instancia de la API
        String resultadoCrear = AmbientesApplications.crearAmbiente(AmbienteHolder.getText());  // Llamar a la API para actualizar el valor
        actualizarTablaAmbientes();
        AmbienteHolder.setText("");
        JOptionPane.showMessageDialog(null, resultadoCrear, "Resultado", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_AgregarAmbienteActionPerformed

    private void AgregarClaseFormacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarClaseFormacionActionPerformed
        String jornadaFormacion = (String)DocumentoInstructorClaseFormacionCB.getSelectedItem();
        String nombreClase = ClaseFormacionHolder.getText();

        if (jornadaFormacion == null) {
            JOptionPane.showMessageDialog(this, "No se encontró ningún instructor con el documento proporcionado.", "Instructor no encontrado", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Formatear la información del instructor
        String infoInstructor = STR."""
        Información del Instructor:
        Nueva clase de formacion: \{nombreClase}
        Jornada de formacion: \{jornadaFormacion}""";

        // Mostrar el cuadro de diálogo de confirmación
        int opcion = JOptionPane.showConfirmDialog(this, infoInstructor + "\n\n¿Desea agregar la clase a este instructor?", "Confirmar agregar clase", JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            // Proceder a agregar la clase al instructor
            try {
                // Asumiendo que tienes un campo para el nombre de la clase

                API_DataClaseFormacionApplications claseFormacionApplications = new API_DataClaseFormacionApplications();
                claseFormacionApplications.crearClaseFormacion(nombreClase, jornadaFormacion);

                JOptionPane.showMessageDialog(this, "La clase se ha agregado exitosamente al instructor.", "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
                actualizarTablaClaseFormacion();
                ClaseFormacionHolder.setText("");
                DocumentoInstructorClaseFormacionCB.setSelectedIndex(0);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Ocurrió un error al agregar la clase: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(this, "No se agregó la clase al instructor.", "Operación cancelada", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_AgregarClaseFormacionActionPerformed

    private void FichaHolderKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FichaHolderKeyTyped
        char caracter = evt.getKeyChar();

        // Permitir solo números y la tecla de retroceso
        if (!Character.isDigit(caracter) && caracter != KeyEvent.VK_BACK_SPACE) {
            evt.consume();  // Evitar que se ingrese el carácter no válido
            JOptionPane.showMessageDialog(this, "Solo se permiten números.");
        }
    }//GEN-LAST:event_FichaHolderKeyTyped

    private void AgregarFichaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarFichaActionPerformed
        DefaultTableModel modelofichasData = (DefaultTableModel) FichasDataTable.getModel();
        String fichaEntrante = FichaHolder.getText().trim();  // Eliminar espacios en blanco

        // Verificar si la ficha está vacía
        if (fichaEntrante.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La ficha no puede estar vacía.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar si se ha seleccionado un programa de formación
        if (FichaDataProgramaFormacionCB.getSelectedItem() == null ||
                FichaDataProgramaFormacionCB.getSelectedItem().toString().equals("Seleccionar...")) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un programa de formación.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar si se ha seleccionado una jornada de formación
        if (FichaDataJornadaFormacionCB.getSelectedItem() == null ||
                FichaDataJornadaFormacionCB.getSelectedItem().toString().equals("Seleccionar...")) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una jornada de formación.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Convertir la ficha entrante a Integer
            int fichaNumero = Integer.parseInt(fichaEntrante);

            boolean perteneceAFicha = false;
            // Recorrer las filas de la tabla para verificar si la ficha ya existe
            for (int i = 0; i < modelofichasData.getRowCount(); i++) {
                String fichaTablaString = modelofichasData.getValueAt(i, 1).toString(); // Obtener el número de ficha de la tabla
                Integer fichaTabla = Integer.parseInt(fichaTablaString); // Convertir el String a Integer

                if (fichaTabla.equals(fichaNumero)) {
                    perteneceAFicha = true;
                    break;
                }
            }

            if (perteneceAFicha) {
                JOptionPane.showMessageDialog(this, "La ficha " + fichaNumero + " ya está registrada.", "Ficha Existente", JOptionPane.WARNING_MESSAGE);
            } else {
                // Obtener el programa de formación seleccionado
                String programaFormacion = FichaDataProgramaFormacionCB.getSelectedItem().toString();

                // Obtener la jornada de formación seleccionada
                String jornadaFormacion = FichaDataJornadaFormacionCB.getSelectedItem().toString();

                // Confirmar la creación de la ficha
                int confirm = JOptionPane.showConfirmDialog(this,
                        "¿Desea crear la ficha con los siguientes datos?\n\n" +
                                "Número de Ficha: " + fichaNumero + "\n" +
                                "Programa de Formación: " + programaFormacion + "\n" +
                                "Jornada de Formación: " + jornadaFormacion,
                        "Confirmar Creación",
                        JOptionPane.YES_NO_OPTION);

                if (confirm != JOptionPane.YES_OPTION) {
                    // El usuario canceló la operación
                    return;
                }

                // Llamar al método de ClienteAPI para crear la ficha
                String respuesta = API_DataFichasApplications.crearFicha(fichaNumero, programaFormacion, jornadaFormacion);

                // Limpiar campos y reiniciar ComboBox después de una creación exitosa
                if (respuesta != null && respuesta.contains("exitosamente")) {
                    FichaHolder.setText(""); // Limpiar el campo de entrada de ficha
                    FichaDataProgramaFormacionCB.setSelectedIndex(0); // Reiniciar ComboBox
                    FichaDataJornadaFormacionCB.setSelectedIndex(0); // Reiniciar ComboBox
                    actualizarTablaFichas(); // Actualizar la tabla
                }
                // Si hay un error, ya se muestra en ClienteAPI
            }

        } catch (NumberFormatException e) {
            // Manejo de error si el texto no es un número válido
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un número de ficha válido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            // Manejo de cualquier otro error inesperado
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_AgregarFichaActionPerformed

    private void AgregarGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarGeneroActionPerformed
        API_DataGenerosApplications GenerosApplications = new API_DataGenerosApplications(); // Instancia de la API
        String resultadoCrear = GenerosApplications.crearGenero(GeneroHolder.getText());  // Llamar a la API para actualizar el valor
        actualizarTablaGeneros();
        JOptionPane.showMessageDialog(null, resultadoCrear, "Resultado", JOptionPane.INFORMATION_MESSAGE);
        GeneroHolder.setText("");
    }//GEN-LAST:event_AgregarGeneroActionPerformed

    private void AgregarRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarRolActionPerformed
        API_DataRolesApplications RolApplications = new API_DataRolesApplications();
        String resultadoCrear = RolApplications.crearRol(RolHolder.getText());
        actualizarTablaRoles();
        JOptionPane.showMessageDialog(null, resultadoCrear, "Resultado", JOptionPane.INFORMATION_MESSAGE);
        RolHolder.setText("");
    }//GEN-LAST:event_AgregarRolActionPerformed

    private void RefrescarTablaTipoDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefrescarTablaTipoDocActionPerformed
        actualizarTipoDocTable();
    }//GEN-LAST:event_RefrescarTablaTipoDocActionPerformed

    private void AgregarTipoDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarTipoDocActionPerformed
        API_DataTipoDocApplications tipoDocApplications = new API_DataTipoDocApplications();
        String resultadoCrear = tipoDocApplications.crearTipoDocumento(TipoDocumentoHolder.getText());
        actualizarTipoDocTable();
        JOptionPane.showMessageDialog(null, resultadoCrear, "Resultado", JOptionPane.INFORMATION_MESSAGE);
        TipoDocumentoHolder.setText("");
    }//GEN-LAST:event_AgregarTipoDocActionPerformed

    private void TipoDocumentoHolderKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TipoDocumentoHolderKeyTyped
        char caracter = evt.getKeyChar();

        // Permitir solo letras, la barra espaciadora y las teclas de control (como backspace)
        if (!Character.isLetter(caracter) && caracter != '\b' && caracter != ' ') {
            evt.consume();  // Evitar que se ingrese el carácter no válido
            JOptionPane.showMessageDialog(this, "Solo se permiten letras y espacios.");
        }
    }//GEN-LAST:event_TipoDocumentoHolderKeyTyped

    private void RolHolderKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RolHolderKeyTyped
        char caracter = evt.getKeyChar();

        // Permitir solo letras, la barra espaciadora y las teclas de control (como backspace)
        if (!Character.isLetter(caracter) && caracter != '\b' && caracter != ' ') {
            evt.consume();  // Evitar que se ingrese el carácter no válido
            JOptionPane.showMessageDialog(this, "Solo se permiten letras y espacios.");
        }
    }//GEN-LAST:event_RolHolderKeyTyped

    private void GeneroHolderKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GeneroHolderKeyTyped
        char caracter = evt.getKeyChar();

        // Permitir solo letras, la barra espaciadora y las teclas de control (como backspace)
        if (!Character.isLetter(caracter) && caracter != '\b' && caracter != ' ') {
            evt.consume();  // Evitar que se ingrese el carácter no válido
            JOptionPane.showMessageDialog(this, "Solo se permiten letras y espacios.");
        }
    }//GEN-LAST:event_GeneroHolderKeyTyped

    private void ClaseFormacionHolderKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ClaseFormacionHolderKeyTyped
        char caracter = evt.getKeyChar();

        // Permitir solo letras, la barra espaciadora y las teclas de control (como backspace)
        if (!Character.isLetter(caracter) && caracter != '\b' && caracter != ' ') {
            evt.consume();  // Evitar que se ingrese el carácter no válido
            JOptionPane.showMessageDialog(this, "Solo se permiten letras y espacios.");
        }
    }//GEN-LAST:event_ClaseFormacionHolderKeyTyped

    private void AmbienteHolderKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AmbienteHolderKeyTyped
        char caracter = evt.getKeyChar();

        // Permitir solo letras, la barra espaciadora y las teclas de control (como backspace)
        if (!Character.isLetter(caracter) && caracter != '\b' && caracter != ' ') {
            evt.consume();  // Evitar que se ingrese el carácter no válido
            JOptionPane.showMessageDialog(this, "Solo se permiten letras y espacios.");
        }
    }//GEN-LAST:event_AmbienteHolderKeyTyped

    private void ActividadHolderKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ActividadHolderKeyTyped
        char caracter = evt.getKeyChar();

        // Permitir solo letras, la barra espaciadora y las teclas de control (como backspace)
        if (!Character.isLetter(caracter) && caracter != '\b' && caracter != ' ') {
            evt.consume();  // Evitar que se ingrese el carácter no válido
            JOptionPane.showMessageDialog(this, "Solo se permiten letras y espacios.");
        }
    }//GEN-LAST:event_ActividadHolderKeyTyped

    private void AreaHolderKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AreaHolderKeyTyped
        char caracter = evt.getKeyChar();

        // Permitir solo letras, la barra espaciadora y las teclas de control (como backspace)
        if (!Character.isLetter(caracter) && caracter != '\b' && caracter != ' ') {
            evt.consume();  // Evitar que se ingrese el carácter no válido
            JOptionPane.showMessageDialog(this, "Solo se permiten letras y espacios.");
        }
    }//GEN-LAST:event_AreaHolderKeyTyped

    private void JornadaHolderKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JornadaHolderKeyTyped
        char caracter = evt.getKeyChar();

        // Permitir solo letras, la barra espaciadora y las teclas de control (como backspace)
        if (!Character.isLetter(caracter) && caracter != '\b' && caracter != ' ') {
            evt.consume();  // Evitar que se ingrese el carácter no válido
            JOptionPane.showMessageDialog(this, "Solo se permiten letras y espacios.");
        }
    }//GEN-LAST:event_JornadaHolderKeyTyped

    private void NivelFormacionHolderKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NivelFormacionHolderKeyTyped
        char caracter = evt.getKeyChar();

        // Permitir solo letras, la barra espaciadora y las teclas de control (como backspace)
        if (!Character.isLetter(caracter) && caracter != '\b' && caracter != ' ') {
            evt.consume();  // Evitar que se ingrese el carácter no válido
            JOptionPane.showMessageDialog(this, "Solo se permiten letras y espacios.");
        }
    }//GEN-LAST:event_NivelFormacionHolderKeyTyped

    private void ProgramaFormacionHolderKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProgramaFormacionHolderKeyTyped
        char caracter = evt.getKeyChar();

        // Permitir solo letras, la barra espaciadora y las teclas de control (como backspace)
        if (!Character.isLetter(caracter) && caracter != '\b' && caracter != ' ') {
            evt.consume();  // Evitar que se ingrese el carácter no válido
            JOptionPane.showMessageDialog(this, "Solo se permiten letras y espacios.");
        }
    }//GEN-LAST:event_ProgramaFormacionHolderKeyTyped

    private void SedeHolderKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SedeHolderKeyTyped
        char caracter = evt.getKeyChar();

        // Permitir solo letras, la barra espaciadora y las teclas de control (como backspace)
        if (!Character.isLetter(caracter) && caracter != '\b' && caracter != ' ') {
            evt.consume();  // Evitar que se ingrese el carácter no válido
            JOptionPane.showMessageDialog(this, "Solo se permiten letras y espacios.");
        }
    }//GEN-LAST:event_SedeHolderKeyTyped

    private void RefrescarTablaFichasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefrescarTablaFichasActionPerformed
       actualizarTablaFichas();
    }//GEN-LAST:event_RefrescarTablaFichasActionPerformed

    private void RefrescarTablaClaseFormacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefrescarTablaClaseFormacionActionPerformed
       actualizarTablaClaseFormacion();
    }//GEN-LAST:event_RefrescarTablaClaseFormacionActionPerformed

    private void AsociarClaseInstructorFIchaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AsociarClaseInstructorFIchaActionPerformed
// Obtener los valores seleccionados y ingresados
        String numeroFichaStr = (String) AsociarClaseInstructorFichaDataFichasCB.getSelectedItem();
        String documentoInstructor = AsociarClaseInstructorFichaDocumentoHolder.getText().trim();
        String nombreClase = (String) AsociarInstructorClaseFichaDataProgramaFormacionCB.getSelectedItem();

        // Validar que los campos no estén en su estado por defecto o vacíos
        if (numeroFichaStr.equals("Seleccionar...")) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un número de ficha.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (nombreClase.equals("Seleccionar...")) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una clase de formación.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (documentoInstructor.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese el documento del instructor.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convertir numeroFicha a Integer
        Integer numeroFicha;
        try {
            numeroFicha = Integer.parseInt(numeroFichaStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Número de ficha inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Buscar el instructor usando API_BuscarUsuario
        API_BuscarUsuario buscarInstructor = new API_BuscarUsuario();
        InstructorModel instructor = buscarInstructor.buscarInstructorPorDocumento(documentoInstructor);
        System.out.println(instructor);
        if (instructor == null) {
            JOptionPane.showMessageDialog(this, "Instructor no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Mostrar los datos del instructor
        String instructorInfo = "Nombre: " + instructor.getNombres() + " " + instructor.getApellidos() + "\n" +
                "Documento: " + instructor.getDocumento() + "\n" +
                "Tipo de Documento: " + instructor.getTipoDocumento() + "\n" +
                "Correo: " + instructor.getCorreo();
        JOptionPane.showMessageDialog(this, instructorInfo, "Datos del Instructor", JOptionPane.INFORMATION_MESSAGE);

        // Crear y configurar el ComboBox para seleccionar la jornadaClase
        JComboBox<String> jornadaComboBox = new JComboBox<>();
        ComboBoxModels cbm = new ComboBoxModels();
        ComboBoxModel<String> jornadaModel = cbm.generarComboBoxModelPorTipo("JornadaFormacion");
        jornadaComboBox.setModel(jornadaModel);
        jornadaComboBox.setSelectedIndex(0); // Seleccionar el primer elemento por defecto

        // Crear un panel para el diálogo
        JPanel panel = new JPanel();
        panel.add(new JLabel("Seleccione la jornada de la clase:"));
        panel.add(jornadaComboBox);

        // Mostrar el diálogo para seleccionar la jornadaClase
        int result = JOptionPane.showConfirmDialog(this, panel, "Seleccionar Jornada", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION) {
            // El usuario canceló la operación
            return;
        }

        String jornadaClase = (String) jornadaComboBox.getSelectedItem();

        if (jornadaClase == null || jornadaClase.equals("Seleccionar...")) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una jornada válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar si la clase con la jornada seleccionada existe
        boolean claseExiste = false;
        List<Map<String, Object>> clasesRegistradas = DataTables.obtenerClases();
        for (Map<String, Object> clase : clasesRegistradas) {
            if (clase.get("NombreClase").toString().equalsIgnoreCase(nombreClase) && clase.get("JornadasFormacion").toString().equalsIgnoreCase(jornadaClase)) {
                claseExiste = true;
                break;
            }
        }

        if (!claseExiste) {
            JOptionPane.showMessageDialog(this, "No existe una clase de formación con el nombre y jornada seleccionados.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirmar la asociación con el usuario
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Desea asociar la ficha número " + numeroFicha + " con el instructor " + instructor.getNombres() +
                        " " + instructor.getApellidos() + " y la clase de formación '" + nombreClase + "' en la jornada '" + jornadaClase + "'?",
                "Confirmar Asociación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion != JOptionPane.YES_OPTION) {
            return; // El usuario canceló la operación
        }

        // Llamar al método del cliente para crear la asociación
        API_DataInstructorFichaClaseApplications.asociarFichaInstructorClase(nombreClase, numeroFicha, documentoInstructor, jornadaClase);
        AsociarClaseInstructorFichaDocumentoHolder.setText("");
        actualizarTablaAsociarFichas();
    }//GEN-LAST:event_AsociarClaseInstructorFIchaActionPerformed

    private void RefrescarTablaAsociarFichasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefrescarTablaAsociarFichasActionPerformed
        actualizarTablaAsociarFichas();
    }//GEN-LAST:event_RefrescarTablaAsociarFichasActionPerformed

    private void AsociarClaseInstructorFichaDataFichasCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AsociarClaseInstructorFichaDataFichasCBActionPerformed
        String fichaSeleccionada = (String) AsociarClaseInstructorFichaDataFichasCB.getSelectedItem();

        if (fichaSeleccionada != null && !fichaSeleccionada.equals("Seleccionar...")) {
            try {
                // Convertir el valor seleccionado a un número de ficha
                int ficha = Integer.parseInt(fichaSeleccionada);
                System.out.println("Ficha seleccionada: " + ficha);

                // Crear una instancia de DataTables para realizar la consulta
                DataTables dataTables = new DataTables();
                List<Map<String, Object>> vinculaciones = dataTables.obtenerVinculacionesPorFicha(ficha);

                if (vinculaciones != null && !vinculaciones.isEmpty()) {

                    // Limpiar la tabla antes de llenarla con nuevas vinculaciones
                    DefaultTableModel model = new DefaultTableModel(
                            new Object[]{"Ficha", "Area", "Sede", "Clase de Formacion", "Jornada de Formacion", "Documento del Instructor", "Nivel de Formacion", "Programa de Formacion", "Editar", "Eliminar"},
                            0  // Número de filas iniciales
                    );

                    // Llenar la tabla AprendizVinculacionesTB con los datos de las vinculaciones

                    for (Map<String, Object> vinculacion : vinculaciones) {
                        String area = (String) vinculacion.get("Area");
                        String sede = (String) vinculacion.get("Sede");
                        String nombreClase = (String) vinculacion.get("ClaseFormacion");
                        String jornada = (String) vinculacion.get("JornadaFormacion");
                        String documentoInstructor = (String) vinculacion.get("DocumentoInstructor");
                        String nivelFormacion = (String) vinculacion.get("NivelFormacion");
                        String programaFormacion = (String) vinculacion.get("ProgramaFormacion");


                        // Añadir una nueva fila a la tabla
                        model.addRow(new Object[]{ficha, area, sede, nombreClase, jornada, documentoInstructor, nivelFormacion, programaFormacion, "Editar", "Eliminar"});
                    }


                    // Asignar el modelo a la tabla
                    ClaseInstructorFichasAsociadasDataTable.setModel(model);

                    ClaseInstructorFichasAsociadasDataTable.getColumnModel().getColumn(8).setCellRenderer(new ButtonColumnHelper.ButtonRenderer()); // Columna "Editar"
                    ClaseInstructorFichasAsociadasDataTable.getColumnModel().getColumn(8).setCellEditor(new ButtonColumnHelper.ButtonEditor(new JCheckBox(), ClaseInstructorFichasAsociadasDataTable) {
                        @Override
                        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                            super.getTableCellEditorComponent(table, value, isSelected, row, column);

                            // Obtener los valores actuales de la fila seleccionada
                            String nombreClase = table.getValueAt(row, 3).toString();
                            int numeroFicha = Integer.parseInt(table.getValueAt(row, 0).toString());
                            String documentoInstructor = table.getValueAt(row, 5).toString();
                            String jornadaClase = table.getValueAt(row, 4).toString();

                            // Abrir el diálogo personalizado con el documento actual
                            EditarAsociacionDialog dialog = new EditarAsociacionDialog(null, nombreClase, jornadaClase, documentoInstructor);
                            dialog.setVisible(true);

                            // Verificar si se confirmó la actualización
                            if (dialog.isActualizacionConfirmada()) {
                                // Obtener los nuevos valores después de cerrar el diálogo
                                String nuevaClase = dialog.getClaseFormacion();
                                String nuevaJornada = dialog.getJornadaFormacion();
                                String nuevoDocumentoInstructor = dialog.getDocumentoInstructor();

                                // Confirmar la actualización
                                int opcion = JOptionPane.showConfirmDialog(this.getComponent(),
                                        "¿Actualizar la clase a: " + nuevaClase + " y la jornada a: " + nuevaJornada + "?",
                                        "Confirmar Actualización",
                                        JOptionPane.YES_NO_OPTION);

                                if (opcion == JOptionPane.YES_OPTION) {
                                    // Llamar al método para actualizar la asociación
                                    API_DataInstructorFichaClaseApplications.editarAsociacionFichaInstructorClase(
                                            nombreClase, nuevaClase, numeroFicha, numeroFicha, documentoInstructor, nuevoDocumentoInstructor, nuevaJornada
                                    );

                                    // Actualizar la tabla con los nuevos valores
                                    table.setValueAt(nuevaClase, row, 3);
                                    table.setValueAt(nuevaJornada, row, 4);
                                    table.setValueAt(nuevoDocumentoInstructor, row, 5);
                                    JOptionPane.showMessageDialog(null, "Asociación actualizada correctamente.");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Actualización cancelada.");
                                }
                            } else {
                                // Se presionó cancelar, no realizar ninguna acción
                                JOptionPane.showMessageDialog(null, "Actualización cancelada.");
                            }

                            return this.getComponent();
                        }
                    });

// Botón de "Eliminar"
                    ClaseInstructorFichasAsociadasDataTable.getColumnModel().getColumn(9).setCellRenderer(new ButtonColumnHelper.ButtonRenderer()); // Columna "Eliminar"
                    ClaseInstructorFichasAsociadasDataTable.getColumnModel().getColumn(9).setCellEditor(new ButtonColumnHelper.ButtonEditor(new JCheckBox(), ClaseInstructorFichasAsociadasDataTable) {
                        @Override
                        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                            super.getTableCellEditorComponent(table, value, isSelected, row, column);

                            // Obtener los valores actuales de la fila seleccionada
                            String nombreClase = table.getValueAt(row, 3).toString();
                            int numeroFicha = Integer.parseInt(table.getValueAt(row, 0).toString());
                            String documentoInstructor = table.getValueAt(row, 5).toString();
                            String jornadaClase = table.getValueAt(row, 4).toString();

                            // Confirmar eliminación
                            int opcion = JOptionPane.showConfirmDialog(this.getComponent(),
                                    "¿Está seguro que desea eliminar esta asociación?",
                                    "Confirmar Eliminación",
                                    JOptionPane.YES_NO_OPTION);

                            if (opcion == JOptionPane.YES_OPTION) {
                                // Llamar al método para eliminar la asociación
                                API_DataInstructorFichaClaseApplications.eliminarAsociacionFichaInstructorClase(nombreClase, numeroFicha, documentoInstructor, jornadaClase);

                                // Eliminar la fila de la tabla
                                ((DefaultTableModel) table.getModel()).removeRow(row);
                                JOptionPane.showMessageDialog(null, "Asociación eliminada correctamente.");
                            } else {
                                JOptionPane.showMessageDialog(null, "Eliminación cancelada.");
                            }
                            return this.getComponent();
                        }
                    });
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        }
    }//GEN-LAST:event_AsociarClaseInstructorFichaDataFichasCBActionPerformed

    private void RefrescarCombosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefrescarCombosActionPerformed
        refrescarComboBoxes();
    }//GEN-LAST:event_RefrescarCombosActionPerformed


    public void comportamientoTabla(JTable table, String tipoTabla) {
        // Configurar el comportamiento de los botones de "Editar"
        TableColumn editarColumna = table.getColumnModel().getColumn(2); // Columna de "Editar"
        editarColumna.setCellRenderer(new ButtonColumnHelper.ButtonRenderer());
        editarColumna.setCellEditor(new ButtonColumnHelper.ButtonEditor(new JCheckBox(), table) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                if (row >= 0 && row < table.getRowCount()) {
                    String valor = (String) table.getValueAt(row, 1);  // Obtener el valor de la segunda columna (nombre del rol)
                    Integer id = (Integer) table.getValueAt(row, 0);  // Obtener el ID de la primera columna (ID del rol)

                    System.out.println(STR."Editando \{tipoTabla}: \{valor} con ID: \{id}");
                    String nuevoValor = JOptionPane.showInputDialog(null, STR."Editar \{tipoTabla}: ", valor);
                    if (nuevoValor != null && !nuevoValor.trim().isEmpty()) {
                        fireEditingStopped();  // Detener la edición antes de actualizar el valor
                        switch (tipoTabla){
                            case "Rol":
                                API_DataRolesApplications RolApplications = new API_DataRolesApplications(); // Instancia de la API para roles
                                RolApplications.actualizarRol(id, nuevoValor);  // Llamar a la API para actualizar el valor
                                table.setValueAt(nuevoValor, row, 1);  // Actualizar la celda con el nuevo valor
                                actualizarTablaRoles();  // Refrescar la tabla después de la edición

                            case "Tipo de Documento":
                                API_DataTipoDocApplications TipoDocApplications = new API_DataTipoDocApplications(); // Instancia de la API
                                TipoDocApplications.actualizarTipoDocumento(id, nuevoValor);  // Llamar a la API para actualizar el valor
                                table.setValueAt(nuevoValor, row, 1);  // Actualizar la celda con el nuevo valor
                                actualizarTipoDocTable();  // Refrescar la tabla

                            case "Genero":
                                API_DataGenerosApplications GenerosApplications = new API_DataGenerosApplications(); // Instancia de la API
                                GenerosApplications.actualizarGenero(id, nuevoValor);  // Llamar a la API para actualizar el valor
                                table.setValueAt(nuevoValor, row, 1);  // Actualizar la celda con el nuevo valor
                                actualizarTablaGeneros();  // Refrescar la tabla
                                break;

                            case "Ambiente":
                                API_DataAmbientesApplications AmbientesApplications = new API_DataAmbientesApplications();
                                AmbientesApplications.actualizarAmbiente(id, nuevoValor);  // Llamar a la API para actualizar el valor
                                table.setValueAt(nuevoValor, row, 1);  // Actualizar la celda con el nuevo valor
                                actualizarTablaAmbientes();  // Refrescar la tabla
                                break;

                            case "Actividad":
                                API_DataActividadApplications ActividadApplications = new API_DataActividadApplications();
                                ActividadApplications.actualizarActividad(id, nuevoValor);  // Llamar a la API para actualizar el valor
                                table.setValueAt(nuevoValor, row, 1);  // Actualizar la celda con el nuevo valor
                                actualizarTablaActividades();  // Refrescar la tabla
                                break;

                            case "Area":
                                API_DataAreasApplications DataApplications = new API_DataAreasApplications();
                                DataApplications.actualizarArea(id, nuevoValor);  // Llamar a la API para actualizar el valor
                                table.setValueAt(nuevoValor, row, 1);  // Actualizar la celda con el nuevo valor
                                actualizarTablaAreas();  // Refrescar la tabla
                                break;

                            case "Jornada de Formacion":
                                API_DataJornadaApplications JornadaApplications = new API_DataJornadaApplications();
                                JornadaApplications.actualizarJornada(id, nuevoValor);
                                table.setValueAt(nuevoValor, row, 1);  // Actualizar la celda con el nuevo valor
                                actualizarTablaJornadas();  // Refrescar la tabla
                                break;

                            case "Nivel de Formacion":
                                API_DataNivelFormacionApplications NivelFormacion = new API_DataNivelFormacionApplications();
                                NivelFormacion.actualizarNivelFormacion(id, nuevoValor);
                                table.setValueAt(nuevoValor, row, 1);  // Actualizar la celda con el nuevo valor
                                actualizarTablaNivelesFormacion();  // Refrescar la tabla
                                break;

                            case "Sede":
                                API_DataSedeApplications SedeApplications = new API_DataSedeApplications();
                                SedeApplications.actualizarSede(id, nuevoValor);
                                table.setValueAt(nuevoValor, row, 1);  // Actualizar la celda con el nuevo valor
                                actualizarTablaSedesFormacion();  // Refrescar la tabla
                                break;
                        }

                    }
                }
                return super.getTableCellEditorComponent(table, value, isSelected, row, column);
            }

            @Override
            public boolean stopCellEditing() {
                fireEditingStopped();  // Asegurarse de detener correctamente la edición
                return super.stopCellEditing();
            }
        });

        // Configurar el comportamiento de los botones de "Eliminar"
        TableColumn eliminarColumna = table.getColumnModel().getColumn(3); // Columna de "Eliminar"
        eliminarColumna.setCellRenderer(new ButtonColumnHelper.ButtonRenderer());
        eliminarColumna.setCellEditor(new ButtonColumnHelper.ButtonEditor(new JCheckBox(),table) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                if (row >= 0 && row < table.getRowCount()) {
                    Integer id = (Integer) table.getValueAt(row, 0);  // Obtener el ID de la primera columna (ID del rol)
                    int respuesta = JOptionPane.showConfirmDialog(null, STR."¿Estás seguro de eliminar el \{tipoTabla} con ID: \{id}?",
                            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        fireEditingStopped();  // Detener la edición antes de eliminar la fila
                        switch (tipoTabla){
                            case "Rol":
                                API_DataRolesApplications RolApplications = new API_DataRolesApplications(); // Instancia de la API para roles
                                RolApplications.eliminarRol(id);  // Llamar a la API para actualizar el valor
                                actualizarTablaRoles();  // Refrescar la tabla después de la edición
                                ((DefaultTableModel) table.getModel()).removeRow(row);
                                actualizarTablaRoles();
                                break;

                            case "Tipo de Documento":
                                API_DataTipoDocApplications TipoDocApplications = new API_DataTipoDocApplications(); // Instancia de la API
                                TipoDocApplications.eliminarTipoDocumento(id);  // Llamar a la API para actualizar el valor
                                actualizarTipoDocTable();  // Refrescar la tabla
                                ((DefaultTableModel) table.getModel()).removeRow(row);
                                actualizarTipoDocTable();
                                break;

                            case "Genero":
                                API_DataGenerosApplications GenerosApplications = new API_DataGenerosApplications(); // Instancia de la API
                                GenerosApplications.eliminarGenero(id);  // Llamar a la API para actualizar el valor
                                actualizarTablaGeneros();  // Refrescar la tabla
                                ((DefaultTableModel) table.getModel()).removeRow(row);
                                actualizarTablaGeneros();
                                break;

                            case "Ambiente":
                                API_DataAmbientesApplications AmbientesApplications = new API_DataAmbientesApplications();
                                AmbientesApplications.eliminarAmbiente(id);  // Llamar a la API para actualizar el valor
                                actualizarTablaAmbientes();  // Refrescar la tabla
                                ((DefaultTableModel) table.getModel()).removeRow(row);
                                actualizarTablaAmbientes();
                                break;

                            case "Actividad":
                                API_DataActividadApplications ActividadApplications = new API_DataActividadApplications();
                                ActividadApplications.eliminarActividad(id);  // Llamar a la API para actualizar el valor
                                actualizarTablaActividades();  // Refrescar la tabla
                                ((DefaultTableModel) table.getModel()).removeRow(row);
                                actualizarTablaActividades();
                                break;

                            case "Area":
                                API_DataAreasApplications DataApplications = new API_DataAreasApplications();
                                DataApplications.eliminarArea(id);  // Llamar a la API para actualizar el valor
                                actualizarTablaAreas();  // Refrescar la tabla
                                ((DefaultTableModel) table.getModel()).removeRow(row);
                                actualizarTablaAreas();
                                break;

                            case "Jornada de Formacion":
                                API_DataJornadaApplications JornadaApplications = new API_DataJornadaApplications();
                                JornadaApplications.eliminarJornada(id);  // Llamar a la API para actualizar el valor
                                actualizarTablaJornadas();  // Refrescar la tabla
                                ((DefaultTableModel) table.getModel()).removeRow(row);
                                actualizarTablaJornadas();
                                break;

                            case "Nivel de Formacion":
                                API_DataNivelFormacionApplications NivelFormacion = new API_DataNivelFormacionApplications();
                                NivelFormacion.eliminarNivelFormacion(id);
                                ((DefaultTableModel) table.getModel()).removeRow(row);
                                actualizarTablaNivelesFormacion();
                                break;

                            case "Sede":
                                API_DataSedeApplications SedeApplications = new API_DataSedeApplications();
                                SedeApplications.eliminarSede(id);
                                ((DefaultTableModel) table.getModel()).removeRow(row);

                                actualizarTablaSedesFormacion();

                                break;

                            default:
                                break;

                        }
                    } else {
                        System.out.println("Sin cambios");
                        cancelCellEditing(); // Cancela la edición en lugar de detenerla
                    }
                }
                return super.getTableCellEditorComponent(table, value, isSelected, row, column);
            }

            @Override
            public boolean stopCellEditing() {
                fireEditingStopped();  // Asegurarse de detener correctamente la edición
                return super.stopCellEditing();
            }
        });
    }

    public void actualizarTipoDocTable() {

        agregarModelo(TipoDocTableData, dt.obtenerTipoDocumentos());
        comportamientoTabla(TipoDocTableData, "Tipo de Documento");

    }

    public void actualizarTablaRoles() {
        agregarModelo(RolesTableData, dt.obtenerRoles());
        comportamientoTabla(RolesTableData, "Rol");
    }

    public void actualizarTablaGeneros(){
        agregarModelo(GenerosTableData, dt.obtenerGeneros());
        comportamientoTabla(GenerosTableData, "Genero");
    }

    public void actualizarTablaAmbientes(){
        agregarModelo(AmbientesDataTable, dt.obtenerAmbientes());
        comportamientoTabla(AmbientesDataTable, "Ambiente");
    }
    
    public void actualizarTablaActividades(){
        agregarModelo(ActividadesDataTable, dt.obtenerActividades());
        comportamientoTabla(ActividadesDataTable, "Actividad");
    }

    public void actualizarTablaAreas(){
        agregarModelo(AreasDataTable, dt.obtenerAreas());
        comportamientoTabla(AreasDataTable, "Area");
    }

    public void actualizarTablaJornadas(){
        agregarModelo(JornadasDataTable, dt.obtenerJornadasFormacion());
        comportamientoTabla(JornadasDataTable, "Jornada de Formacion");
    }

    public void actualizarTablaNivelesFormacion(){
        agregarModelo(NivelesDataTable, dt.obtenerNivelesFormacion());
        comportamientoTabla(NivelesDataTable, "Nivel de Formacion");
    }

    public void actualizarTablaSedesFormacion(){
        agregarModelo(SedesDataTables, dt.obtenerSedes());
        comportamientoTabla(SedesDataTables, "Sede");
    }

    public void actualizarTablaProgramaFormacion(){
        agregarModeloProgramaFormacion(ProgramaFormacionDataTable, API_DataProgramaFormacionApplications.obtenerProgramasFormacion());
    }

    public void actualizarTablaFichas(){
        agregarModeloAdicional(FichasDataTable, dt.obtenerFichas());
    }


    public void actualizarTablaClaseFormacion(){
        agregarModeloClaseFormacion(ClaseFormacionDataTable, DataTables.obtenerClases());
    }

    public void actualizarTablaAsociarFichas() {
        String fichaSeleccionada = (String) AsociarClaseInstructorFichaDataFichasCB.getSelectedItem();

        if (fichaSeleccionada != null && !fichaSeleccionada.equals("Seleccionar...")) {
            try {
                // Convertir el valor seleccionado a un número de ficha
                int ficha = Integer.parseInt(fichaSeleccionada);
                System.out.println("Ficha seleccionada: " + ficha);

                // Crear una instancia de DataTables para realizar la consulta
                DataTables dataTables = new DataTables();
                List<Map<String, Object>> vinculaciones = dataTables.obtenerVinculacionesPorFicha(ficha);

                if (vinculaciones != null && !vinculaciones.isEmpty()) {

                    // Limpiar la tabla antes de llenarla con nuevas vinculaciones
                    DefaultTableModel model = new DefaultTableModel(
                            new Object[]{"Ficha", "Area", "Sede", "Clase de Formacion", "Jornada de Formacion", "Documento del Instructor", "Nivel de Formacion", "Programa de Formacion", "Editar", "Eliminar"},
                            0  // Número de filas iniciales
                    );

                    // Llenar la tabla AprendizVinculacionesTB con los datos de las vinculaciones

                    for (Map<String, Object> vinculacion : vinculaciones) {
                        String area = (String) vinculacion.get("Area");
                        String sede = (String) vinculacion.get("Sede");
                        String nombreClase = (String) vinculacion.get("ClaseFormacion");
                        String jornada = (String) vinculacion.get("JornadaFormacion");
                        String documentoInstructor = (String) vinculacion.get("DocumentoInstructor");
                        String nivelFormacion = (String) vinculacion.get("NivelFormacion");
                        String programaFormacion = (String) vinculacion.get("ProgramaFormacion");


                        // Añadir una nueva fila a la tabla
                        model.addRow(new Object[]{ficha, area, sede, nombreClase, jornada, documentoInstructor, nivelFormacion, programaFormacion, "Editar", "Eliminar"});
                    }


                    // Asignar el modelo a la tabla
                    ClaseInstructorFichasAsociadasDataTable.setModel(model);

                    ClaseInstructorFichasAsociadasDataTable.getColumnModel().getColumn(8).setCellRenderer(new ButtonColumnHelper.ButtonRenderer()); // Columna "Editar"
                    ClaseInstructorFichasAsociadasDataTable.getColumnModel().getColumn(8).setCellEditor(new ButtonColumnHelper.ButtonEditor(new JCheckBox(), ClaseInstructorFichasAsociadasDataTable) {
                        @Override
                        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                            super.getTableCellEditorComponent(table, value, isSelected, row, column);

                            // Obtener los valores actuales de la fila seleccionada
                            String nombreClase = table.getValueAt(row, 3).toString();
                            int numeroFicha = Integer.parseInt(table.getValueAt(row, 0).toString());
                            String documentoInstructor = table.getValueAt(row, 5).toString();
                            String jornadaClase = table.getValueAt(row, 4).toString();

                            // Abrir el diálogo personalizado con el documento actual
                            EditarAsociacionDialog dialog = new EditarAsociacionDialog(null, nombreClase, jornadaClase, documentoInstructor);
                            dialog.setVisible(true);

                            // Verificar si se confirmó la actualización
                            if (dialog.isActualizacionConfirmada()) {
                                // Obtener los nuevos valores después de cerrar el diálogo
                                String nuevaClase = dialog.getClaseFormacion();
                                String nuevaJornada = dialog.getJornadaFormacion();
                                String nuevoDocumentoInstructor = dialog.getDocumentoInstructor();

                                // Confirmar la actualización
                                int opcion = JOptionPane.showConfirmDialog(this.getComponent(),
                                        "¿Actualizar la clase a: " + nuevaClase + " y la jornada a: " + nuevaJornada + "?",
                                        "Confirmar Actualización",
                                        JOptionPane.YES_NO_OPTION);

                                if (opcion == JOptionPane.YES_OPTION) {
                                    // Llamar al método para actualizar la asociación
                                    API_DataInstructorFichaClaseApplications.editarAsociacionFichaInstructorClase(
                                            nombreClase, nuevaClase, numeroFicha, numeroFicha, documentoInstructor, nuevoDocumentoInstructor, nuevaJornada
                                    );

                                    // Actualizar la tabla con los nuevos valores
                                    table.setValueAt(nuevaClase, row, 3);
                                    table.setValueAt(nuevaJornada, row, 4);
                                    table.setValueAt(nuevoDocumentoInstructor, row, 5);
                                    JOptionPane.showMessageDialog(null, "Asociación actualizada correctamente.");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Actualización cancelada.");
                                }
                            } else {
                                // Se presionó cancelar, no realizar ninguna acción
                                JOptionPane.showMessageDialog(null, "Actualización cancelada.");
                            }

                            return this.getComponent();
                        }
                    });

// Botón de "Eliminar"
                    ClaseInstructorFichasAsociadasDataTable.getColumnModel().getColumn(9).setCellRenderer(new ButtonColumnHelper.ButtonRenderer()); // Columna "Eliminar"
                    ClaseInstructorFichasAsociadasDataTable.getColumnModel().getColumn(9).setCellEditor(new ButtonColumnHelper.ButtonEditor(new JCheckBox(), ClaseInstructorFichasAsociadasDataTable) {
                        @Override
                        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                            super.getTableCellEditorComponent(table, value, isSelected, row, column);

                            // Obtener los valores actuales de la fila seleccionada
                            String nombreClase = table.getValueAt(row, 3).toString();
                            int numeroFicha = Integer.parseInt(table.getValueAt(row, 0).toString());
                            String documentoInstructor = table.getValueAt(row, 5).toString();
                            String jornadaClase = table.getValueAt(row, 4).toString();

                            // Confirmar eliminación
                            int opcion = JOptionPane.showConfirmDialog(this.getComponent(),
                                    "¿Está seguro que desea eliminar esta asociación?",
                                    "Confirmar Eliminación",
                                    JOptionPane.YES_NO_OPTION);

                            if (opcion == JOptionPane.YES_OPTION) {
                                // Llamar al método para eliminar la asociación
                                API_DataInstructorFichaClaseApplications.eliminarAsociacionFichaInstructorClase(nombreClase, numeroFicha, documentoInstructor, jornadaClase);

                                // Eliminar la fila de la tabla
                                ((DefaultTableModel) table.getModel()).removeRow(row);
                                JOptionPane.showMessageDialog(null, "Asociación eliminada correctamente.");
                            } else {
                                JOptionPane.showMessageDialog(null, "Eliminación cancelada.");
                            }
                            return this.getComponent();
                        }
                    });
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void refrescarComboBoxes() {
        try {
            ComboBoxModels cbm = new ComboBoxModels();
            // Actualizar los ComboBox con los modelos más recientes
            AsociarClaseInstructorFichaDataFichasCB.setModel(cbm.generarComboBoxModelPorTipo("Fichas"));
            AsociarInstructorClaseFichaDataProgramaFormacionCB.setModel(cbm.generarComboBoxModelPorTipo("ClaseFormacion"));
            FichaDataProgramaFormacionCB.setModel(cbm.generarComboBoxModelPorTipo("ProgramaFormacion"));
            FichaDataJornadaFormacionCB.setModel(cbm.generarComboBoxModelPorTipo("JornadaFormacion"));
            DocumentoInstructorClaseFormacionCB.setModel(cbm.generarComboBoxModelPorTipo("JornadaFormacion"));
            NivelFormacionProgramaCB.setModel(cbm.generarComboBoxModelPorTipo("NivelFormacion"));
            SedeFormacionCB.setModel(cbm.generarComboBoxModelPorTipo("Sede"));
            AreaFormacionCB.setModel(cbm.generarComboBoxModelPorTipo("Areas"));

            // Reiniciar los ComboBox al índice 0
            AsociarClaseInstructorFichaDataFichasCB.setSelectedIndex(0);
            AsociarInstructorClaseFichaDataProgramaFormacionCB.setSelectedIndex(0);
            FichaDataProgramaFormacionCB.setSelectedIndex(0);
            FichaDataJornadaFormacionCB.setSelectedIndex(0);
            NivelFormacionProgramaCB.setSelectedIndex(0);
            SedeFormacionCB.setSelectedIndex(0);
            AreaFormacionCB.setSelectedIndex(0);

            JOptionPane.showMessageDialog(this, "Los ComboBox han sido actualizados correctamente.", "ComboBox Actualizados", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al refrescar los ComboBox: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ActividadHolder;
    private javax.swing.JTable ActividadesDataTable;
    private javax.swing.JPanel ActividadesPanel;
    private javax.swing.JButton AgregarActividad;
    private javax.swing.JButton AgregarAmbiente;
    private javax.swing.JButton AgregarArea;
    private javax.swing.JButton AgregarClaseFormacion;
    private javax.swing.JButton AgregarFicha;
    private javax.swing.JButton AgregarGenero;
    private javax.swing.JButton AgregarJornadaFormacion;
    private javax.swing.JButton AgregarNivelFormacion;
    private javax.swing.JButton AgregarProgramaFormacion;
    private javax.swing.JButton AgregarRol;
    private javax.swing.JButton AgregarSede;
    private javax.swing.JButton AgregarTipoDoc;
    private javax.swing.JTextField AmbienteHolder;
    private javax.swing.JTable AmbientesDataTable;
    private javax.swing.JPanel AmbientesPanel;
    private javax.swing.JComboBox<String> AreaFormacionCB;
    private javax.swing.JTextField AreaHolder;
    private javax.swing.JTable AreasDataTable;
    private javax.swing.JPanel AreasPanel;
    private javax.swing.JPanel AsignarFIchasSubPanel;
    private javax.swing.JButton AsociarClaseInstructorFIcha;
    private javax.swing.JComboBox<String> AsociarClaseInstructorFichaDataFichasCB;
    private javax.swing.JTextField AsociarClaseInstructorFichaDocumentoHolder;
    private javax.swing.JComboBox<String> AsociarInstructorClaseFichaDataProgramaFormacionCB;
    private javax.swing.JTable ClaseFormacionDataTable;
    private javax.swing.JTextField ClaseFormacionHolder;
    private javax.swing.JTable ClaseInstructorFichasAsociadasDataTable;
    private javax.swing.JPanel ClasesFormacionPanel;
    private javax.swing.JPanel CrearFichasSubPanel;
    private javax.swing.JComboBox<String> DocumentoInstructorClaseFormacionCB;
    private javax.swing.JComboBox<String> FichaDataJornadaFormacionCB;
    private javax.swing.JComboBox<String> FichaDataProgramaFormacionCB;
    private javax.swing.JTextField FichaHolder;
    private javax.swing.JTable FichasDataTable;
    private javax.swing.JPanel FichasPanel;
    private javax.swing.JTabbedPane FichasTabPanel;
    private javax.swing.JTextField GeneroHolder;
    private javax.swing.JPanel GenerosPanel;
    private javax.swing.JTable GenerosTableData;
    private javax.swing.JTextField JornadaHolder;
    private javax.swing.JTable JornadasDataTable;
    private javax.swing.JPanel JornadasPanel;
    private javax.swing.JTextField NivelFormacionHolder;
    private javax.swing.JComboBox<String> NivelFormacionProgramaCB;
    private javax.swing.JTable NivelesDataTable;
    private javax.swing.JPanel NivelesPanel;
    private javax.swing.JTabbedPane PrincipalTabPanel;
    private javax.swing.JTable ProgramaFormacionDataTable;
    private javax.swing.JTextField ProgramaFormacionHolder;
    private javax.swing.JPanel ProgramasFormacionPanel;
    private javax.swing.JPanel ProgramasFormacionSubPanel;
    private javax.swing.JTabbedPane ProgramasFormacionTabPanel;
    private javax.swing.JButton RefrescarCombos;
    private javax.swing.JButton RefrescarTablaAsociarFichas;
    private javax.swing.JButton RefrescarTablaClaseFormacion;
    private javax.swing.JButton RefrescarTablaFichas;
    private javax.swing.JButton RefrescarTablaTipoDoc;
    private javax.swing.JTextField RolHolder;
    private javax.swing.JPanel RolesPanel;
    private javax.swing.JTable RolesTableData;
    private javax.swing.JComboBox<String> SedeFormacionCB;
    private javax.swing.JTextField SedeHolder;
    private javax.swing.JTable SedesDataTables;
    private javax.swing.JPanel SedesPanel;
    private javax.swing.JTable TipoDocTableData;
    private javax.swing.JTextField TipoDocumentoHolder;
    private javax.swing.JPanel TiposDocPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    // End of variables declaration//GEN-END:variables
}
