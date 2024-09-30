/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main.AdminFrames.AdminActionScreens;

import main.util.API_Actions.API_Data.*;
import main.util.models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
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
        TabPanelStyler.applyPrimaryStyle(ResidenciasTabPanel);
        TabPanelStyler.applyPrimaryStyle(ProgramasFormacionTabPanel);
        ButtonStyler.applyPrimaryStyle(AgregarTipoDoc);
        try {
            ComboBoxModels cbm = new ComboBoxModels();
            actualizarTipoDocTable();
            actualizarTablaRoles();
            actualizarTablaGeneros();
            actualizarTablaAmbientes();
            actualizarTablaActividades();
            actualizarTablaDepartamentos();
            agregarModeloAdicional(MunicipiosDataTable, dt.obtenerMunicipios(), dt.obtenerDepartamentos());
            agregarModeloAdicional(BarriosDataTable, dt.obtenerBarrios(), dt.obtenerMunicipios());
            agregarModeloAdicional(FichasDataTable, dt.obtenerFichas(), dt.obtenerProgramaFormacion());
            actualizarTablaAreas();
            actualizarTablaJornadas();
            actualizarTablaNivelesFormacion();
            actualizarTablaSedesFormacion();
            agregarModeloClaseFormacion(ClaseFormacionDataTable, dt.obtenerClasesConInstructor(), dt.obtenerInstructores());
            NombreInstructorCB.setModel(cbm.generarComboBoxModelPorTipo("Instructores"));
            DesActivarComponentes(ClaseFormacionForm);
            DesActivarComponentes(InstructorAddInfoPanel);
            FichaDataProgramaFormacionCB.setModel(cbm.generarComboBoxModelPorTipo("ProgramaFormacion"));



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


    // Método para agregar modelo sin ComboBox, con valores adicionales mostrados como texto plano
    public void agregarModeloAdicional(JTable tabla, Map<Integer, String> datos, Map<Integer, String> valoresAdicionales) {
        // Crear el DefaultTableModel con columnas: ID, Valor, Selección (texto plano), Editar, Eliminar
        DefaultTableModel modeloTabla = new DefaultTableModel(new Object[]{"ID", "Valor", "Selección", "Editar", "Eliminar"}, 0);

        // Rellenar el modelo con los datos y valores adicionales
        for (Map.Entry<Integer, String> entry : datos.entrySet()) {
            Integer id = entry.getKey();
            String valorPrincipal = entry.getValue();
            String valorAdicional = valoresAdicionales.getOrDefault(id, ""); // Obtener el valor adicional (Departamento o Municipio)

            // Añadir una fila al modelo con los datos y el valor adicional como texto plano
            modeloTabla.addRow(new Object[]{id, valorPrincipal, valorAdicional, "Editar", "Eliminar"});
        }

        // Setear el modelo a la JTable
        tabla.setModel(modeloTabla);

        // Asignar el ButtonRenderer y ButtonEditor para las columnas de "Editar" y "Eliminar"
        TableColumn editarColumna = tabla.getColumnModel().getColumn(3); // Columna de "Editar"
        editarColumna.setCellRenderer(new ButtonColumnHelper.ButtonRenderer());
        editarColumna.setCellEditor(new ButtonColumnHelper.ButtonEditor(new JCheckBox(), tabla));

        TableColumn eliminarColumna = tabla.getColumnModel().getColumn(4); // Columna de "Eliminar"
        eliminarColumna.setCellRenderer(new ButtonColumnHelper.ButtonRenderer());
        eliminarColumna.setCellEditor(new ButtonColumnHelper.ButtonEditor(new JCheckBox(), tabla));
    }

    // Método para agregar modelo de clase de formación sin ComboBox, usando texto plano para mostrar el instructor
    public void agregarModeloClaseFormacion(JTable tabla, List<Map<String, Object>> clases, Map<Integer, String> instructores) {
        // Crear el DefaultTableModel con columnas: ID, Nombre de la Clase, Instructor (texto plano), Documento, Correo, Editar, Eliminar
        DefaultTableModel modeloTabla = new DefaultTableModel(new Object[]{"ID", "NombreClase", "Instructor", "Documento", "Correo", "Editar", "Eliminar"}, 0);

        // Rellenar el modelo con los datos de las clases y los instructores
        for (Map<String, Object> clase : clases) {
            System.out.println(clase);
            Integer idClase = (Integer) clase.get("IDClase");
            String nombreClase = (String) clase.get("NombreClase");
            String nombreInstructor = (String) clase.get("NombreInstructor");
            String documentoInstructor = (String) clase.get("DocumentoInstructor");
            String correoInstructor = (String) clase.get("CorreoInstructor");

            // Añadir una fila al modelo con los datos de la clase y el instructor en texto plano
            modeloTabla.addRow(new Object[]{idClase, nombreClase, nombreInstructor, documentoInstructor, correoInstructor, "Editar", "Eliminar"});
        }

        // Setear el modelo a la JTable
        tabla.setModel(modeloTabla);

        // Asignar el ButtonRenderer y ButtonEditor para las columnas de "Editar" y "Eliminar"
        TableColumn editarColumna = ClaseFormacionDataTable.getColumnModel().getColumn(5); // Columna de "Editar"
        editarColumna.setCellRenderer(new ButtonColumnHelper.ButtonRenderer());
        editarColumna.setCellEditor(new ButtonColumnHelper.ButtonEditor(new JCheckBox(), ClaseFormacionDataTable) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                super.getTableCellEditorComponent(table, value, isSelected, row, column);

                // Obtener los valores de la fila seleccionada
                String idClase = String.valueOf(table.getValueAt(row, 0));
                String nombreClase = (String) table.getValueAt(row, 1); // Columna de "NombreClase"
                String nombreInstructor = (String) table.getValueAt(row, 2); // Columna de "Instructor"
                String documentoInstructor = (String) table.getValueAt(row, 3); // Columna de "Documento"
                String correoInstructor = (String) table.getValueAt(row, 4); // Columna de "Correo"


                System.out.println(correoInstructor);


                // Llenar los campos del formulario con los datos obtenidos
                ResultadoClaseFormacion.setText(nombreClase);
                ResultadoIDClase.setText(idClase);
                NombreInstructorCB.setSelectedItem(nombreInstructor);

                activarComponentes(ClaseFormacionForm);
                activarComponentes(InstructorAddInfoPanel);
                return this.getComponent();
            }
        });

        TableColumn eliminarColumna = ClaseFormacionDataTable.getColumnModel().getColumn(6); // Columna de "Eliminar"
        eliminarColumna.setCellRenderer(new ButtonColumnHelper.ButtonRenderer());
        eliminarColumna.setCellEditor(new ButtonColumnHelper.ButtonEditor(new JCheckBox(), ClaseFormacionDataTable) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                super.getTableCellEditorComponent(table, value, isSelected, row, column);

                // Obtener el ID de la clase de la fila seleccionada
                int idClase = (int) table.getValueAt(row, 0); // Asegúrate de que la primera columna contenga el ID de la clase

                // Confirmación de eliminación
                int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar la clase con ID " + idClase + "?");

                if (confirm == JOptionPane.YES_OPTION) {
                    try {

                        dt.eliminarClase(idClase);
                        System.out.println("Clase eliminada con ID: " + idClase);

                        // Refrescar la tabla después de la eliminación
                        refrescarTablaClaseFormacion();
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error al eliminar la clase: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                return this.getComponent();
            }
        });
    }

    public void activarComponentes(JPanel panel) {
        Component[] components = panel.getComponents();
        for (Component component : components) {
            component.setEnabled(true);
        }
    }

    public void DesActivarComponentes(JPanel panel) {
        Component[] components = panel.getComponents();
        for (Component component : components) {
            component.setEnabled(false);
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

        jPanel4 = new javax.swing.JPanel();
        PrincipalTabPanel = new javax.swing.JTabbedPane();
        TiposDocPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TipoDocTableData = new javax.swing.JTable();
        AgregarTipoDoc = new javax.swing.JButton();
        TipoDocumentoHolder = new javax.swing.JTextField();
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
        AgregarFicha = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        FichasDataTable = new javax.swing.JTable();
        FichaDataProgramaFormacionCB = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        FichaHolder = new javax.swing.JTextField();
        ClasesFormacionPanel = new javax.swing.JPanel();
        ResultadoCorreo4 = new javax.swing.JTextField();
        AgregarClaseFormacion = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        ClaseFormacionDataTable = new javax.swing.JTable();
        ClaseFormacionForm = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        InstructorAddInfoPanel = new javax.swing.JPanel();
        ResultadoIDClase = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        NombreInstructorCB = new javax.swing.JComboBox<>();
        ConfirmarModClaseFormacion = new javax.swing.JButton();
        ResultadoClaseFormacion = new javax.swing.JTextField();
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
        ResidenciasPanel = new javax.swing.JPanel();
        ResidenciasTabPanel = new javax.swing.JTabbedPane();
        DepartamentosPanel = new javax.swing.JPanel();
        DepartamentoHolder = new javax.swing.JTextField();
        AgregarDepartamento = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        DepartamentosDataTable = new javax.swing.JTable();
        MunicipiosPanel = new javax.swing.JPanel();
        ResultadoCorreo8 = new javax.swing.JTextField();
        AgregarMunicipio = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        MunicipiosDataTable = new javax.swing.JTable();
        BarriosPanel = new javax.swing.JPanel();
        ResultadoCorreo9 = new javax.swing.JTextField();
        AgregarBarrio = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        BarriosDataTable = new javax.swing.JTable();
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
        ResultadoCorreo13 = new javax.swing.JTextField();
        AgregarProgramaFormacion = new javax.swing.JButton();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTable14 = new javax.swing.JTable();
        SedesPanel = new javax.swing.JPanel();
        SedeHolder = new javax.swing.JTextField();
        AgregarSede = new javax.swing.JButton();
        jScrollPane15 = new javax.swing.JScrollPane();
        SedesDataTables = new javax.swing.JTable();

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
                .addContainerGap(473, Short.MAX_VALUE))
        );
        TiposDocPanelLayout.setVerticalGroup(
            TiposDocPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TiposDocPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(TiposDocPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AgregarTipoDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TipoDocumentoHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(406, Short.MAX_VALUE))
        );

        PrincipalTabPanel.addTab("Tipos de Documentos", TiposDocPanel);

        RolHolder.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        RolHolder.setForeground(new java.awt.Color(0, 0, 0));

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
                .addContainerGap(608, Short.MAX_VALUE))
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
                .addContainerGap(199, Short.MAX_VALUE))
        );

        PrincipalTabPanel.addTab("Roles", RolesPanel);

        GeneroHolder.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        GeneroHolder.setForeground(new java.awt.Color(0, 0, 0));

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
                .addContainerGap(580, Short.MAX_VALUE))
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
                .addContainerGap(199, Short.MAX_VALUE))
        );

        PrincipalTabPanel.addTab("Generos", GenerosPanel);

        AgregarFicha.setBackground(new java.awt.Color(57, 169, 0));
        AgregarFicha.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        AgregarFicha.setForeground(new java.awt.Color(255, 255, 255));
        AgregarFicha.setText("Agregar Ficha");
        AgregarFicha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarFichaActionPerformed(evt);
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

        FichaDataProgramaFormacionCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Programa de Formacion");

        jLabel2.setText("Ficha Nueva");

        FichaHolder.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        FichaHolder.setForeground(new java.awt.Color(0, 0, 0));
        FichaHolder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FichaHolderKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout FichasPanelLayout = new javax.swing.GroupLayout(FichasPanel);
        FichasPanel.setLayout(FichasPanelLayout);
        FichasPanelLayout.setHorizontalGroup(
            FichasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FichasPanelLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(FichasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane4)
                    .addGroup(FichasPanelLayout.createSequentialGroup()
                        .addGroup(FichasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(FichaHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(FichasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(FichasPanelLayout.createSequentialGroup()
                                .addComponent(FichaDataProgramaFormacionCB, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(AgregarFicha)))))
                .addContainerGap(511, Short.MAX_VALUE))
        );
        FichasPanelLayout.setVerticalGroup(
            FichasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FichasPanelLayout.createSequentialGroup()
                .addContainerGap(298, Short.MAX_VALUE)
                .addGroup(FichasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(FichasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FichaDataProgramaFormacionCB, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AgregarFicha, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FichaHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );

        PrincipalTabPanel.addTab("Fichas", FichasPanel);

        ResultadoCorreo4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ResultadoCorreo4.setForeground(new java.awt.Color(0, 0, 0));

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

        ClaseFormacionForm.setBorder(javax.swing.BorderFactory.createTitledBorder("Editar Clase de formacion"));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setText("Instructor");

        InstructorAddInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Info Adicional"));

        ResultadoIDClase.setEditable(false);
        ResultadoIDClase.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ResultadoIDClase.setForeground(new java.awt.Color(0, 0, 0));
        ResultadoIDClase.setFocusable(false);

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 0));
        jLabel31.setText("ID Clase de Formacion");

        javax.swing.GroupLayout InstructorAddInfoPanelLayout = new javax.swing.GroupLayout(InstructorAddInfoPanel);
        InstructorAddInfoPanel.setLayout(InstructorAddInfoPanelLayout);
        InstructorAddInfoPanelLayout.setHorizontalGroup(
            InstructorAddInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InstructorAddInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(ResultadoIDClase, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        InstructorAddInfoPanelLayout.setVerticalGroup(
            InstructorAddInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InstructorAddInfoPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(InstructorAddInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(ResultadoIDClase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(87, 87, 87))
        );

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setText("Nombre de clase");

        NombreInstructorCB.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        NombreInstructorCB.setForeground(new java.awt.Color(0, 0, 0));
        NombreInstructorCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        NombreInstructorCB.setPreferredSize(new java.awt.Dimension(64, 28));
        NombreInstructorCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NombreInstructorCBActionPerformed(evt);
            }
        });

        ConfirmarModClaseFormacion.setBackground(new java.awt.Color(0, 34, 64));
        ConfirmarModClaseFormacion.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ConfirmarModClaseFormacion.setForeground(new java.awt.Color(255, 255, 255));
        ConfirmarModClaseFormacion.setText("Modificar Clase de formacion");
        ConfirmarModClaseFormacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConfirmarModClaseFormacionActionPerformed(evt);
            }
        });

        ResultadoClaseFormacion.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ResultadoClaseFormacion.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout ClaseFormacionFormLayout = new javax.swing.GroupLayout(ClaseFormacionForm);
        ClaseFormacionForm.setLayout(ClaseFormacionFormLayout);
        ClaseFormacionFormLayout.setHorizontalGroup(
            ClaseFormacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ClaseFormacionFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ClaseFormacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ClaseFormacionFormLayout.createSequentialGroup()
                        .addComponent(InstructorAddInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(ClaseFormacionFormLayout.createSequentialGroup()
                        .addGroup(ClaseFormacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28))
                        .addGap(37, 37, 37)
                        .addGroup(ClaseFormacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(NombreInstructorCB, 0, 260, Short.MAX_VALUE)
                            .addComponent(ResultadoClaseFormacion)))
                    .addComponent(ConfirmarModClaseFormacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        ClaseFormacionFormLayout.setVerticalGroup(
            ClaseFormacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ClaseFormacionFormLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(ClaseFormacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(ResultadoClaseFormacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ClaseFormacionFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(NombreInstructorCB, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(InstructorAddInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ConfirmarModClaseFormacion, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(244, 244, 244))
        );

        javax.swing.GroupLayout ClasesFormacionPanelLayout = new javax.swing.GroupLayout(ClasesFormacionPanel);
        ClasesFormacionPanel.setLayout(ClasesFormacionPanelLayout);
        ClasesFormacionPanelLayout.setHorizontalGroup(
            ClasesFormacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ClasesFormacionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ClasesFormacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 737, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ClasesFormacionPanelLayout.createSequentialGroup()
                        .addComponent(ResultadoCorreo4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AgregarClaseFormacion)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ClaseFormacionForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        ClasesFormacionPanelLayout.setVerticalGroup(
            ClasesFormacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ClasesFormacionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ClasesFormacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ClasesFormacionPanelLayout.createSequentialGroup()
                        .addGroup(ClasesFormacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AgregarClaseFormacion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ResultadoCorreo4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(ClaseFormacionForm, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(199, Short.MAX_VALUE))
        );

        PrincipalTabPanel.addTab("Clases de Formacion", ClasesFormacionPanel);

        AmbienteHolder.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        AmbienteHolder.setForeground(new java.awt.Color(0, 0, 0));

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
                .addContainerGap(564, Short.MAX_VALUE))
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
                .addContainerGap(199, Short.MAX_VALUE))
        );

        PrincipalTabPanel.addTab("Ambientes", AmbientesPanel);

        ActividadHolder.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ActividadHolder.setForeground(new java.awt.Color(0, 0, 0));

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
                .addContainerGap(567, Short.MAX_VALUE))
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
                .addContainerGap(199, Short.MAX_VALUE))
        );

        PrincipalTabPanel.addTab("Actividades", ActividadesPanel);

        ResidenciasTabPanel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        DepartamentoHolder.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        DepartamentoHolder.setForeground(new java.awt.Color(0, 0, 0));

        AgregarDepartamento.setBackground(new java.awt.Color(57, 169, 0));
        AgregarDepartamento.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        AgregarDepartamento.setForeground(new java.awt.Color(255, 255, 255));
        AgregarDepartamento.setText("Agregar Departamento");
        AgregarDepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarDepartamentoActionPerformed(evt);
            }
        });

        DepartamentosDataTable.setModel(new javax.swing.table.DefaultTableModel(
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
        DepartamentosDataTable.setRowHeight(30);
        jScrollPane8.setViewportView(DepartamentosDataTable);
        if (DepartamentosDataTable.getColumnModel().getColumnCount() > 0) {
            DepartamentosDataTable.getColumnModel().getColumn(1).setPreferredWidth(150);
            DepartamentosDataTable.getColumnModel().getColumn(1).setMaxWidth(150);
            DepartamentosDataTable.getColumnModel().getColumn(2).setPreferredWidth(150);
            DepartamentosDataTable.getColumnModel().getColumn(2).setMaxWidth(150);
        }

        javax.swing.GroupLayout DepartamentosPanelLayout = new javax.swing.GroupLayout(DepartamentosPanel);
        DepartamentosPanel.setLayout(DepartamentosPanelLayout);
        DepartamentosPanelLayout.setHorizontalGroup(
            DepartamentosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DepartamentosPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DepartamentosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 973, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DepartamentosPanelLayout.createSequentialGroup()
                        .addComponent(DepartamentoHolder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AgregarDepartamento)))
                .addContainerGap(194, Short.MAX_VALUE))
        );
        DepartamentosPanelLayout.setVerticalGroup(
            DepartamentosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DepartamentosPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DepartamentosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AgregarDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DepartamentoHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(152, Short.MAX_VALUE))
        );

        ResidenciasTabPanel.addTab("Departamentos", DepartamentosPanel);

        ResultadoCorreo8.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ResultadoCorreo8.setForeground(new java.awt.Color(0, 0, 0));

        AgregarMunicipio.setBackground(new java.awt.Color(57, 169, 0));
        AgregarMunicipio.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        AgregarMunicipio.setForeground(new java.awt.Color(255, 255, 255));
        AgregarMunicipio.setText("Agregar Municipio");
        AgregarMunicipio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarMunicipioActionPerformed(evt);
            }
        });

        MunicipiosDataTable.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        MunicipiosDataTable.setModel(new javax.swing.table.DefaultTableModel(
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
        MunicipiosDataTable.setRowHeight(30);
        jScrollPane9.setViewportView(MunicipiosDataTable);
        if (MunicipiosDataTable.getColumnModel().getColumnCount() > 0) {
            MunicipiosDataTable.getColumnModel().getColumn(1).setPreferredWidth(150);
            MunicipiosDataTable.getColumnModel().getColumn(1).setMaxWidth(150);
            MunicipiosDataTable.getColumnModel().getColumn(2).setPreferredWidth(150);
            MunicipiosDataTable.getColumnModel().getColumn(2).setMaxWidth(150);
        }

        javax.swing.GroupLayout MunicipiosPanelLayout = new javax.swing.GroupLayout(MunicipiosPanel);
        MunicipiosPanel.setLayout(MunicipiosPanelLayout);
        MunicipiosPanelLayout.setHorizontalGroup(
            MunicipiosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MunicipiosPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MunicipiosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 922, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MunicipiosPanelLayout.createSequentialGroup()
                        .addComponent(ResultadoCorreo8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AgregarMunicipio)))
                .addContainerGap(245, Short.MAX_VALUE))
        );
        MunicipiosPanelLayout.setVerticalGroup(
            MunicipiosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MunicipiosPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MunicipiosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AgregarMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ResultadoCorreo8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(152, Short.MAX_VALUE))
        );

        ResidenciasTabPanel.addTab("Municipios", MunicipiosPanel);

        ResultadoCorreo9.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ResultadoCorreo9.setForeground(new java.awt.Color(0, 0, 0));

        AgregarBarrio.setBackground(new java.awt.Color(57, 169, 0));
        AgregarBarrio.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        AgregarBarrio.setForeground(new java.awt.Color(255, 255, 255));
        AgregarBarrio.setText("Agregar Barrio");
        AgregarBarrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarBarrioActionPerformed(evt);
            }
        });

        BarriosDataTable.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        BarriosDataTable.setModel(new javax.swing.table.DefaultTableModel(
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
        BarriosDataTable.setRowHeight(30);
        jScrollPane10.setViewportView(BarriosDataTable);
        if (BarriosDataTable.getColumnModel().getColumnCount() > 0) {
            BarriosDataTable.getColumnModel().getColumn(1).setPreferredWidth(150);
            BarriosDataTable.getColumnModel().getColumn(1).setMaxWidth(150);
            BarriosDataTable.getColumnModel().getColumn(2).setPreferredWidth(150);
            BarriosDataTable.getColumnModel().getColumn(2).setMaxWidth(150);
        }

        javax.swing.GroupLayout BarriosPanelLayout = new javax.swing.GroupLayout(BarriosPanel);
        BarriosPanel.setLayout(BarriosPanelLayout);
        BarriosPanelLayout.setHorizontalGroup(
            BarriosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BarriosPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BarriosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 906, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BarriosPanelLayout.createSequentialGroup()
                        .addComponent(ResultadoCorreo9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AgregarBarrio)))
                .addContainerGap(261, Short.MAX_VALUE))
        );
        BarriosPanelLayout.setVerticalGroup(
            BarriosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BarriosPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BarriosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AgregarBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ResultadoCorreo9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(152, Short.MAX_VALUE))
        );

        ResidenciasTabPanel.addTab("Barrios", BarriosPanel);

        javax.swing.GroupLayout ResidenciasPanelLayout = new javax.swing.GroupLayout(ResidenciasPanel);
        ResidenciasPanel.setLayout(ResidenciasPanelLayout);
        ResidenciasPanelLayout.setHorizontalGroup(
            ResidenciasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ResidenciasPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ResidenciasTabPanel)
                .addContainerGap())
        );
        ResidenciasPanelLayout.setVerticalGroup(
            ResidenciasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ResidenciasPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ResidenciasTabPanel)
                .addContainerGap())
        );

        PrincipalTabPanel.addTab("Residencias", ResidenciasPanel);

        ProgramasFormacionTabPanel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        AreaHolder.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        AreaHolder.setForeground(new java.awt.Color(0, 0, 0));

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
                .addContainerGap(587, Short.MAX_VALUE))
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
                .addContainerGap(152, Short.MAX_VALUE))
        );

        ProgramasFormacionTabPanel.addTab("Areas", AreasPanel);

        JornadaHolder.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        JornadaHolder.setForeground(new java.awt.Color(0, 0, 0));

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
                .addContainerGap(465, Short.MAX_VALUE))
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
                .addContainerGap(152, Short.MAX_VALUE))
        );

        ProgramasFormacionTabPanel.addTab("Jornadas de Formacion", JornadasPanel);

        NivelFormacionHolder.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        NivelFormacionHolder.setForeground(new java.awt.Color(0, 0, 0));

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
                .addContainerGap(484, Short.MAX_VALUE))
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
                .addContainerGap(152, Short.MAX_VALUE))
        );

        ProgramasFormacionTabPanel.addTab("Niveles de Formacion", NivelesPanel);

        ResultadoCorreo13.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ResultadoCorreo13.setForeground(new java.awt.Color(0, 0, 0));

        AgregarProgramaFormacion.setBackground(new java.awt.Color(57, 169, 0));
        AgregarProgramaFormacion.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        AgregarProgramaFormacion.setForeground(new java.awt.Color(255, 255, 255));
        AgregarProgramaFormacion.setText("Agregar Programa de Formacion");
        AgregarProgramaFormacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarProgramaFormacionActionPerformed(evt);
            }
        });

        jTable14.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable14.setRowHeight(30);
        jScrollPane14.setViewportView(jTable14);
        if (jTable14.getColumnModel().getColumnCount() > 0) {
            jTable14.getColumnModel().getColumn(1).setPreferredWidth(150);
            jTable14.getColumnModel().getColumn(1).setMaxWidth(150);
            jTable14.getColumnModel().getColumn(2).setPreferredWidth(150);
            jTable14.getColumnModel().getColumn(2).setMaxWidth(150);
        }

        javax.swing.GroupLayout ProgramasFormacionSubPanelLayout = new javax.swing.GroupLayout(ProgramasFormacionSubPanel);
        ProgramasFormacionSubPanel.setLayout(ProgramasFormacionSubPanelLayout);
        ProgramasFormacionSubPanelLayout.setHorizontalGroup(
            ProgramasFormacionSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProgramasFormacionSubPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ProgramasFormacionSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane14)
                    .addGroup(ProgramasFormacionSubPanelLayout.createSequentialGroup()
                        .addComponent(ResultadoCorreo13, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AgregarProgramaFormacion)))
                .addContainerGap(451, Short.MAX_VALUE))
        );
        ProgramasFormacionSubPanelLayout.setVerticalGroup(
            ProgramasFormacionSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProgramasFormacionSubPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ProgramasFormacionSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AgregarProgramaFormacion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ResultadoCorreo13, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(152, Short.MAX_VALUE))
        );

        ProgramasFormacionTabPanel.addTab("Programas de Formacion", ProgramasFormacionSubPanel);

        SedeHolder.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        SedeHolder.setForeground(new java.awt.Color(0, 0, 0));

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
                .addContainerGap(585, Short.MAX_VALUE))
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
                .addContainerGap(152, Short.MAX_VALUE))
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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PrincipalTabPanel)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PrincipalTabPanel)
                .addContainerGap())
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

    private void AgregarSedeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarSedeActionPerformed
        API_DataSedeApplications SedeApplications = new API_DataSedeApplications();
        String resultadoCrear = SedeApplications.crearSede(SedeHolder.getText());
        actualizarTablaSedesFormacion();

        JOptionPane.showMessageDialog(null, resultadoCrear, "Resultado", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_AgregarSedeActionPerformed

    private void AgregarProgramaFormacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarProgramaFormacionActionPerformed
        // TODO add your handling code here:
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

    private void AgregarBarrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarBarrioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AgregarBarrioActionPerformed

    private void AgregarMunicipioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarMunicipioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AgregarMunicipioActionPerformed

    private void AgregarDepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarDepartamentoActionPerformed
        API_DataDepartamentosApplications DepartamentosApplications = new API_DataDepartamentosApplications();
        String resultadoCrear = DepartamentosApplications.crearDepartamento(DepartamentoHolder.getText());
        actualizarTablaDepartamentos();
        DepartamentoHolder.setText("");
        JOptionPane.showMessageDialog(null, resultadoCrear, "Resultado", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_AgregarDepartamentoActionPerformed

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

    private void ConfirmarModClaseFormacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfirmarModClaseFormacionActionPerformed
        try {
            DataTables modClaseFormacion = new DataTables();

            // Llamada para obtener el documento del instructor
            String documentoInstructor = modClaseFormacion.obtenerDocumentoPorNombre(
                    NombreInstructorCB.getSelectedItem().toString());

            if (documentoInstructor != null) {
                // Actualizar la clase de formación
                modClaseFormacion.actualizarClaseFormacion(Integer.valueOf(ResultadoIDClase.getText()), ResultadoClaseFormacion.getText(), documentoInstructor);

                // Refrescar la tabla para reflejar los cambios
                refrescarTablaClaseFormacion();

                // Limpiar los datos del formulario
                limpiarFormularioClaseFormacion();

                // Deshabilitar el formulario
                DesActivarComponentes(ClaseFormacionForm);
            } else {
                System.out.println("No se encontró el documento del instructor.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }//GEN-LAST:event_ConfirmarModClaseFormacionActionPerformed

    public void refrescarTablaClaseFormacion() {
        try {
            DataTables modClaseFormacion = new DataTables();
            // Llamada para obtener las clases actualizadas
            List<Map<String, Object>> clasesActualizadas = modClaseFormacion.obtenerClasesConInstructor();

            // Actualizar la tabla con los nuevos datos
            agregarModeloClaseFormacion(ClaseFormacionDataTable, clasesActualizadas, modClaseFormacion.obtenerInstructores());
        } catch (Exception e) {
            System.out.println("Error al refrescar la tabla: " + e.getMessage());
        }
    }

    public void limpiarFormularioClaseFormacion() {
        ResultadoClaseFormacion.setText("");  // Limpiar ComboBox de Clase de Formación
        NombreInstructorCB.setSelectedIndex(0); // Limpiar ComboBox de Instructor
        ResultadoIDClase.setText("");  // Limpiar el campo de ID de Clase
    }

    private void NombreInstructorCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NombreInstructorCBActionPerformed

    }//GEN-LAST:event_NombreInstructorCBActionPerformed

    private void AgregarClaseFormacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarClaseFormacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AgregarClaseFormacionActionPerformed

    private void AgregarFichaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarFichaActionPerformed
        DefaultTableModel modelofichasData = (DefaultTableModel) FichasDataTable.getModel();
        String fichaEntrante = FichaHolder.getText().trim();  // Eliminar espacios en blanco

        // Verificar si la ficha y el programa de formación no están vacíos
        if (fichaEntrante.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La ficha no puede estar vacía.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (FichaDataProgramaFormacionCB.getSelectedItem().toString().equals("Seleccionar...")) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un programa de formación.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Convertir la ficha entrante a Integer
            int fichaNumero = Integer.parseInt(fichaEntrante);

            boolean perteneceAFicha = false;
            // Recorrer las filas de la tabla para verificar si la ficha ya existe
            for (int i = 0; i < modelofichasData.getRowCount(); i++) {
                String fichaTablaString = modelofichasData.getValueAt(i, 1).toString(); // Convertir el valor a String
                Integer fichaTabla = Integer.parseInt(fichaTablaString); // Convertir el String a Integer

                if (fichaTabla.equals(fichaNumero)) {
                    perteneceAFicha = true;
                    break;
                }
            }

            if (perteneceAFicha) {
                JOptionPane.showMessageDialog(this, "La ficha " + fichaNumero + " ya está registrada.");
            } else {
                JOptionPane.showMessageDialog(this, "Ficha registrada correctamente.", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Aquí puedes agregar la lógica para añadir la ficha a la tabla o sistema
            }

        } catch (NumberFormatException e) {
            // Manejo de error si el texto no es un número
            JOptionPane.showMessageDialog(this, "Por favor ingrese un número de ficha válido.", "Error", JOptionPane.ERROR_MESSAGE);
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

    private void AgregarTipoDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarTipoDocActionPerformed
        API_DataTipoDocApplications tipoDocApplications = new API_DataTipoDocApplications();
        String resultadoCrear = tipoDocApplications.crearTipoDocumento(TipoDocumentoHolder.getText());
        actualizarTipoDocTable();
        JOptionPane.showMessageDialog(null, resultadoCrear, "Resultado", JOptionPane.INFORMATION_MESSAGE);
        TipoDocumentoHolder.setText("");
    }//GEN-LAST:event_AgregarTipoDocActionPerformed

    private void FichaNuevaHolderKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FichaNuevaHolderKeyTyped

    }//GEN-LAST:event_FichaNuevaHolderKeyTyped

    private void FichaHolderKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FichaHolderKeyTyped
        char caracter = evt.getKeyChar();

        // Permitir solo números y la tecla de retroceso
        if (!Character.isDigit(caracter) && caracter != KeyEvent.VK_BACK_SPACE) {
            evt.consume();  // Evitar que se ingrese el carácter no válido
            JOptionPane.showMessageDialog(this, "Solo se permiten números.");
        }
    }//GEN-LAST:event_FichaHolderKeyTyped

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

                            case "Departamento":
                                API_DataDepartamentosApplications DepartamentosApplications = new API_DataDepartamentosApplications();
                                DepartamentosApplications.actualizarDepartamento(id, nuevoValor);  // Llamar a la API para actualizar el valor
                                table.setValueAt(nuevoValor, row, 1);  // Actualizar la celda con el nuevo valor
                                actualizarTablaDepartamentos();  // Refrescar la tabla
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
                                // Actualizar la tabla después de eliminar
                                table.revalidate();
                                table.repaint();
                                break;

                            case "Tipo de Documento":
                                API_DataTipoDocApplications TipoDocApplications = new API_DataTipoDocApplications(); // Instancia de la API
                                TipoDocApplications.eliminarTipoDocumento(id);  // Llamar a la API para actualizar el valor
                                actualizarTipoDocTable();  // Refrescar la tabla
                                ((DefaultTableModel) table.getModel()).removeRow(row);
                                // Actualizar la tabla después de eliminar
                                table.revalidate();
                                table.repaint();
                                break;

                            case "Genero":
                                API_DataGenerosApplications GenerosApplications = new API_DataGenerosApplications(); // Instancia de la API
                                GenerosApplications.eliminarGenero(id);  // Llamar a la API para actualizar el valor
                                actualizarTablaGeneros();  // Refrescar la tabla
                                ((DefaultTableModel) table.getModel()).removeRow(row);
                                // Actualizar la tabla después de eliminar
                                table.revalidate();
                                table.repaint();
                                break;

                            case "Ambiente":
                                API_DataAmbientesApplications AmbientesApplications = new API_DataAmbientesApplications();
                                AmbientesApplications.eliminarAmbiente(id);  // Llamar a la API para actualizar el valor
                                actualizarTablaAmbientes();  // Refrescar la tabla
                                ((DefaultTableModel) table.getModel()).removeRow(row);
                                // Actualizar la tabla después de eliminar
                                table.revalidate();
                                table.repaint();
                                break;

                            case "Actividad":
                                API_DataActividadApplications ActividadApplications = new API_DataActividadApplications();
                                ActividadApplications.eliminarActividad(id);  // Llamar a la API para actualizar el valor
                                actualizarTablaActividades();  // Refrescar la tabla
                                ((DefaultTableModel) table.getModel()).removeRow(row);
                                // Actualizar la tabla después de eliminar
                                table.revalidate();
                                table.repaint();
                                break;

                            case "Departamento":
                                API_DataDepartamentosApplications DepartamentosApplications = new API_DataDepartamentosApplications();
                                DepartamentosApplications.eliminarDepartamento(id);  // Llamar a la API para actualizar el valor
                                actualizarTablaDepartamentos();  // Refrescar la tabla
                                ((DefaultTableModel) table.getModel()).removeRow(row);
                                // Actualizar la tabla después de eliminar
                                table.revalidate();
                                table.repaint();
                                break;

                            case "Area":
                                API_DataAreasApplications DataApplications = new API_DataAreasApplications();
                                DataApplications.eliminarArea(id);  // Llamar a la API para actualizar el valor
                                actualizarTablaDepartamentos();  // Refrescar la tabla
                                ((DefaultTableModel) table.getModel()).removeRow(row);
                                // Actualizar la tabla después de eliminar
                                table.revalidate();
                                table.repaint();
                                break;

                            case "Jornada de Formacion":
                                API_DataJornadaApplications JornadaApplications = new API_DataJornadaApplications();
                                JornadaApplications.eliminarJornada(id);  // Llamar a la API para actualizar el valor
                                actualizarTablaJornadas();  // Refrescar la tabla
                                ((DefaultTableModel) table.getModel()).removeRow(row);
                                // Actualizar la tabla después de eliminar
                                table.revalidate();
                                table.repaint();
                                break;

                            case "Nivel de Formacion":
                                API_DataNivelFormacionApplications NivelFormacion = new API_DataNivelFormacionApplications();
                                NivelFormacion.eliminarNivelFormacion(id);
                                ((DefaultTableModel) table.getModel()).removeRow(row);
                                // Actualizar la tabla después de eliminar
                                table.revalidate();
                                table.repaint();
                                break;

                            case "Sede":
                                API_DataSedeApplications SedeApplications = new API_DataSedeApplications();
                                SedeApplications.eliminarSede(id);
                                ((DefaultTableModel) table.getModel()).removeRow(row);
                                // Actualizar la tabla después de eliminar
                                table.revalidate();
                                table.repaint();
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

    public void actualizarTablaDepartamentos(){
        agregarModelo(DepartamentosDataTable, dt.obtenerDepartamentos());
        comportamientoTabla(DepartamentosDataTable, "Departamento");
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ActividadHolder;
    private javax.swing.JTable ActividadesDataTable;
    private javax.swing.JPanel ActividadesPanel;
    private javax.swing.JButton AgregarActividad;
    private javax.swing.JButton AgregarAmbiente;
    private javax.swing.JButton AgregarArea;
    private javax.swing.JButton AgregarBarrio;
    private javax.swing.JButton AgregarClaseFormacion;
    private javax.swing.JButton AgregarDepartamento;
    private javax.swing.JButton AgregarFicha;
    private javax.swing.JButton AgregarGenero;
    private javax.swing.JButton AgregarJornadaFormacion;
    private javax.swing.JButton AgregarMunicipio;
    private javax.swing.JButton AgregarNivelFormacion;
    private javax.swing.JButton AgregarProgramaFormacion;
    private javax.swing.JButton AgregarRol;
    private javax.swing.JButton AgregarSede;
    private javax.swing.JButton AgregarTipoDoc;
    private javax.swing.JTextField AmbienteHolder;
    private javax.swing.JTable AmbientesDataTable;
    private javax.swing.JPanel AmbientesPanel;
    private javax.swing.JTextField AreaHolder;
    private javax.swing.JTable AreasDataTable;
    private javax.swing.JPanel AreasPanel;
    private javax.swing.JTable BarriosDataTable;
    private javax.swing.JPanel BarriosPanel;
    private javax.swing.JTable ClaseFormacionDataTable;
    private javax.swing.JPanel ClaseFormacionForm;
    private javax.swing.JPanel ClasesFormacionPanel;
    private javax.swing.JButton ConfirmarModClaseFormacion;
    private javax.swing.JTextField DepartamentoHolder;
    private javax.swing.JTable DepartamentosDataTable;
    private javax.swing.JPanel DepartamentosPanel;
    private javax.swing.JComboBox<String> FichaDataProgramaFormacionCB;
    private javax.swing.JTextField FichaHolder;
    private javax.swing.JTable FichasDataTable;
    private javax.swing.JPanel FichasPanel;
    private javax.swing.JTextField GeneroHolder;
    private javax.swing.JPanel GenerosPanel;
    private javax.swing.JTable GenerosTableData;
    private javax.swing.JPanel InstructorAddInfoPanel;
    private javax.swing.JTextField JornadaHolder;
    private javax.swing.JTable JornadasDataTable;
    private javax.swing.JPanel JornadasPanel;
    private javax.swing.JTable MunicipiosDataTable;
    private javax.swing.JPanel MunicipiosPanel;
    private javax.swing.JTextField NivelFormacionHolder;
    private javax.swing.JTable NivelesDataTable;
    private javax.swing.JPanel NivelesPanel;
    private javax.swing.JComboBox<String> NombreInstructorCB;
    private javax.swing.JTabbedPane PrincipalTabPanel;
    private javax.swing.JPanel ProgramasFormacionPanel;
    private javax.swing.JPanel ProgramasFormacionSubPanel;
    private javax.swing.JTabbedPane ProgramasFormacionTabPanel;
    private javax.swing.JPanel ResidenciasPanel;
    private javax.swing.JTabbedPane ResidenciasTabPanel;
    private javax.swing.JTextField ResultadoClaseFormacion;
    private javax.swing.JTextField ResultadoCorreo13;
    private javax.swing.JTextField ResultadoCorreo4;
    private javax.swing.JTextField ResultadoCorreo8;
    private javax.swing.JTextField ResultadoCorreo9;
    private javax.swing.JTextField ResultadoIDClase;
    private javax.swing.JTextField RolHolder;
    private javax.swing.JPanel RolesPanel;
    private javax.swing.JTable RolesTableData;
    private javax.swing.JTextField SedeHolder;
    private javax.swing.JTable SedesDataTables;
    private javax.swing.JPanel SedesPanel;
    private javax.swing.JTable TipoDocTableData;
    private javax.swing.JTextField TipoDocumentoHolder;
    private javax.swing.JPanel TiposDocPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
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
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable14;
    // End of variables declaration//GEN-END:variables
}
