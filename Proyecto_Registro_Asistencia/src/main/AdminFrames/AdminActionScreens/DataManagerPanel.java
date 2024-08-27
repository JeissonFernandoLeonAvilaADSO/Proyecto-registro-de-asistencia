/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main.AdminFrames.AdminActionScreens;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableModel;
import main.util.API_AdminActions.API_Admin_DataManager;

import main.util.models.ButtonStyler;
import main.util.models.ComboBoxModels;
import main.util.models.ModTableRequest;
import main.util.models.ToggleButtonStyler;

/**
 *
 * @author Propietario
 */
public class DataManagerPanel extends javax.swing.JPanel {

    /**
     * Creates new form DataManagerPanel
     */
    
    private DefaultTableModel previousTipoDocModel;
    private DefaultTableModel previousAmbModel;
    private DefaultTableModel previousGenModel;
    private DefaultTableModel previousProgramFormacionModel;
    private DefaultTableModel previousSedeModel;
    
    public DataManagerPanel() {
        initComponents();
        AditionalConfig();
        ModifComponents();
    }

    private void AditionalConfig() {
        ToggleButtons(ModTipoDocs, TipoDocTable);
        ToggleButtons(ModAmb, AmbienteTable);
        ToggleButtons(ModGen, GenerosTable);
        ToggleButtons(ModProgramFormacion, ProgramaFormacionTable);
        ToggleButtons(ModSedes, SedesTable);
     

        try {
            ComboBoxModels TableModels = new ComboBoxModels();
            ArrayList<String> TipoDocTableData = TableModels.BoxTipoDocModel();
            TipoDocTable.setModel(LlenarTabla(TipoDocTableData, "Tipo de documento"));
            previousTipoDocModel = copyTableModel((DefaultTableModel) TipoDocTable.getModel()); // Inicializar estado anterior
            desHabilitarTabla(TipoDocTable);

            ArrayList<String> AmbientesTableData = TableModels.BoxAmbientesModel();
            AmbienteTable.setModel(LlenarTabla(AmbientesTableData, "Ambientes"));
            previousAmbModel = copyTableModel((DefaultTableModel) AmbienteTable.getModel()); // Inicializar estado anterior
            desHabilitarTabla(AmbienteTable);

            ArrayList<String> GeneroTableData = TableModels.BoxTipoGeneroModel();
            GenerosTable.setModel(LlenarTabla(GeneroTableData, "Generos"));
            previousGenModel = copyTableModel((DefaultTableModel) GenerosTable.getModel()); // Inicializar estado anterior
            desHabilitarTabla(GenerosTable);

            ArrayList<String> ProgramaFormacionTableData = TableModels.BoxProgramaFormacionModel();
            ProgramaFormacionTable.setModel(LlenarTabla(ProgramaFormacionTableData, "Programas de Formacion"));
            previousProgramFormacionModel = copyTableModel((DefaultTableModel) ProgramaFormacionTable.getModel()); // Inicializar estado anterior
            desHabilitarTabla(ProgramaFormacionTable);

            ArrayList<String> SedesTableData = TableModels.BoxSedeModel();
            SedesTable.setModel(LlenarTabla(SedesTableData, "Sedes"));
            previousSedeModel = copyTableModel((DefaultTableModel) SedesTable.getModel()); // Inicializar estado anterior
            desHabilitarTabla(SedesTable);

            List<Map<String, Object>> FichasTableData = TableModels.BoxFichasFormModel();
            FichasTable.setModel(LlenarTabla(FichasTableData, new String[]{"Numero Ficha", "Programa de Formacion Asociado"}));
            desHabilitarTabla(FichasTable);

        } catch (Exception ex) {
            Logger.getLogger(DataManagerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private DefaultTableModel copyTableModel(DefaultTableModel original) {
        DefaultTableModel copy = new DefaultTableModel();
        for (int i = 0; i < original.getColumnCount(); i++) {
            copy.addColumn(original.getColumnName(i));
        }
        for (int i = 0; i < original.getRowCount(); i++) {
            Object[] rowData = new Object[original.getColumnCount()];
            for (int j = 0; j < original.getColumnCount(); j++) {
                rowData[j] = original.getValueAt(i, j);
            }
            copy.addRow(rowData);
        }
        return copy;
    }
    
    private void ModifComponents(){
        ToggleButtonStyler.applyPrimaryStyle(ModTipoDocs);
        ToggleButtonStyler.applyPrimaryStyle(ModGen);
        ToggleButtonStyler.applyPrimaryStyle(ModProgramFormacion);
        ToggleButtonStyler.applyPrimaryStyle(ModSedes);
        ToggleButtonStyler.applyPrimaryStyle(ModAmb);
        ToggleButtonStyler.applyPrimaryStyle(ModFichas);
        ButtonStyler.applyPrimaryStyle(Actualizar);
    }
    
    private void ToggleButtons(JToggleButton toggleButton, JTable Tabla){
        toggleButton.addActionListener(e -> {
        if (toggleButton.isSelected()) {
            toggleButton.setText("DesHabilitar Edición");
            HabilitarTabla(Tabla);
        } else {
            toggleButton.setText("Habilitar Edición");
            desHabilitarTabla(Tabla);
        }

     });
    }

    private DefaultTableModel LlenarTabla(ArrayList<String> Datos, String NombreTabla) {
        DefaultTableModel Tabla = new DefaultTableModel();
        Tabla.addColumn(NombreTabla);

        for (String dato : Datos) {
            Tabla.addRow(new Object[]{dato});
        }
        return Tabla;
    }

    private DefaultTableModel LlenarTabla(List<Map<String, Object>> Datos, String[] columnNames) {
        DefaultTableModel Tabla = new DefaultTableModel();

        // Añadir las columnas a la tabla
        for (String columnName : columnNames) {
            Tabla.addColumn(columnName);
        }

        // Llenar las filas de la tabla
        for (Map<String, Object> fila : Datos) {
            Object[] rowData = new Object[columnNames.length];
            rowData[0] = fila.get("NumeroFicha");
            rowData[1] = fila.get("ProgramaFormacion");
            Tabla.addRow(rowData);
        }

        return Tabla;
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
        jScrollPane19 = new javax.swing.JScrollPane();
        FichasTable = new javax.swing.JTable();
        jScrollPane20 = new javax.swing.JScrollPane();
        TipoDocTable = new javax.swing.JTable();
        jScrollPane21 = new javax.swing.JScrollPane();
        AmbienteTable = new javax.swing.JTable();
        jScrollPane22 = new javax.swing.JScrollPane();
        GenerosTable = new javax.swing.JTable();
        jScrollPane23 = new javax.swing.JScrollPane();
        ProgramaFormacionTable = new javax.swing.JTable();
        jScrollPane24 = new javax.swing.JScrollPane();
        SedesTable = new javax.swing.JTable();
        Actualizar = new javax.swing.JButton();
        ModAmb = new javax.swing.JToggleButton();
        ModTipoDocs = new javax.swing.JToggleButton();
        ModGen = new javax.swing.JToggleButton();
        ModFichas = new javax.swing.JToggleButton();
        ModProgramFormacion = new javax.swing.JToggleButton();
        ModSedes = new javax.swing.JToggleButton();

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        FichasTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Fichas", "Programa asociado"
            }
        ));
        jScrollPane19.setViewportView(FichasTable);

        TipoDocTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Tipos de documentos"
            }
        ));
        TipoDocTable.setRequestFocusEnabled(false);
        jScrollPane20.setViewportView(TipoDocTable);

        AmbienteTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Ambientes"
            }
        ));
        jScrollPane21.setViewportView(AmbienteTable);

        GenerosTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Generos"
            }
        ));
        jScrollPane22.setViewportView(GenerosTable);

        ProgramaFormacionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Programas de formacion"
            }
        ));
        jScrollPane23.setViewportView(ProgramaFormacionTable);

        SedesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Sedes"
            }
        ));
        jScrollPane24.setViewportView(SedesTable);

        Actualizar.setText("Actualizar base de datos");
        Actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarActionPerformed(evt);
            }
        });

        ModAmb.setText("Modificar Ambientes");

        ModTipoDocs.setText("Modificar Tipos de documentos");

        ModGen.setText("Modificar Generos");

        ModFichas.setText("Modificar FIchas");

        ModProgramFormacion.setText("Modificar Programas de Formacion");

        ModSedes.setText("Modificar Sedes");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Actualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 912, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ModTipoDocs, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ModProgramFormacion, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ModSedes, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane19)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ModAmb, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ModGen, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(ModFichas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 12, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(ModAmb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(ModGen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(ModTipoDocs)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ModFichas)
                    .addComponent(ModProgramFormacion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ModSedes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Actualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
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


    
    private void desHabilitarTabla(JTable Table){
        Table.setRowSelectionAllowed(false);
        Table.setColumnSelectionAllowed(false);
        Table.setCellSelectionEnabled(false);
        Table.setDefaultEditor(Object.class, null);
    }
    
        private void HabilitarTabla(JTable Table){
        Table.setRowSelectionAllowed(true);
        Table.setColumnSelectionAllowed(true);
        Table.setCellSelectionEnabled(true);
        Table.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()));
    }
        
    
    
    private void ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarActionPerformed
        API_Admin_DataManager client = new API_Admin_DataManager();

        if (ModTipoDocs.isSelected()) {
            ModTableRequest request = ModTableRequest.fromTable(previousTipoDocModel, TipoDocTable);
            client.enviarDatosTabla("ModTipoDocTable", request);
            previousTipoDocModel = (DefaultTableModel) TipoDocTable.getModel(); // Actualizar estado anterior
        }

        if (ModAmb.isSelected()) {
            ModTableRequest request = ModTableRequest.fromTable(previousAmbModel, AmbienteTable);
            client.enviarDatosTabla("ModAmbientesTable", request);
            previousAmbModel = (DefaultTableModel) AmbienteTable.getModel(); // Actualizar estado anterior
        }

        if (ModGen.isSelected()) {
            ModTableRequest request = ModTableRequest.fromTable(previousGenModel, GenerosTable);
            client.enviarDatosTabla("ModGeneroTable", request);
            previousGenModel = (DefaultTableModel) GenerosTable.getModel(); // Actualizar estado anterior
        }

        if (ModProgramFormacion.isSelected()) {
            ModTableRequest request = ModTableRequest.fromTable(previousProgramFormacionModel, ProgramaFormacionTable);
            client.enviarDatosTabla("ModProgramaFormacionTable", request);
            previousProgramFormacionModel = (DefaultTableModel) ProgramaFormacionTable.getModel(); // Actualizar estado anterior
        }

        if (ModSedes.isSelected()) {
            ModTableRequest request = ModTableRequest.fromTable(previousSedeModel, SedesTable);
            client.enviarDatosTabla("ModSedeTable", request);
            previousSedeModel = (DefaultTableModel) SedesTable.getModel(); // Actualizar estado anterior
        }

        JOptionPane.showMessageDialog(null, "Tablas seleccionadas actualizadas correctamente", "Actualización", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_ActualizarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Actualizar;
    private javax.swing.JTable AmbienteTable;
    private javax.swing.JTable FichasTable;
    private javax.swing.JTable GenerosTable;
    private javax.swing.JToggleButton ModAmb;
    private javax.swing.JToggleButton ModFichas;
    private javax.swing.JToggleButton ModGen;
    private javax.swing.JToggleButton ModProgramFormacion;
    private javax.swing.JToggleButton ModSedes;
    private javax.swing.JToggleButton ModTipoDocs;
    private javax.swing.JTable ProgramaFormacionTable;
    private javax.swing.JTable SedesTable;
    private javax.swing.JTable TipoDocTable;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    // End of variables declaration//GEN-END:variables
}
