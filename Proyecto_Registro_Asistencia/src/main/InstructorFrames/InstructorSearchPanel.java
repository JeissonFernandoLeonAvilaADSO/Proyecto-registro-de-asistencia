/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main.InstructorFrames;

import java.util.List;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import main.util.API_Actions.ConvertirDatos;
import main.util.API_Actions.ListarAsitenciasInstructorAPI;
import main.util.models.ButtonEditor;
import main.util.models.ButtonRenderer;
import main.util.models.ButtonStyler;
import main.util.models.ComboBoxModels;
import main.util.models.UserSession;

/**
 *
 * @author Propietario
 */
public class InstructorSearchPanel extends javax.swing.JPanel {

    /**
     * Creates new form InstructorSearchPanel
     */
    public InstructorSearchPanel() {
        initComponents();
        AditionalConfig();
    }
    
        public void AditionalConfig() {
        ComboBoxModels ComboBoxModels = new ComboBoxModels();
        try {
            List<String> tiposProgramaFormacion = ComboBoxModels.BoxProgramaFormacionModel();
            if (tiposProgramaFormacion == null) {
                JOptionPane.showMessageDialog(null, "Hubo un error cargando los programas de formacion de la API");
            } else {
                tiposProgramaFormacion.add(0, "Seleccionar...");
                DefaultComboBoxModel<String> ProgramaFormacionBoxModel = new DefaultComboBoxModel<>(ComboBoxModels.toArray(tiposProgramaFormacion));
                ProgramaFormacionCB.setModel(ProgramaFormacionBoxModel);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            List<String> tiposAmbientes = ComboBoxModels.BoxAmbientesModel();
            if (tiposAmbientes == null) {
                JOptionPane.showMessageDialog(null, "Hubo un error cargando los Ambientes de la API");
            } else {
                tiposAmbientes.add(0, "Seleccionar...");
                DefaultComboBoxModel<String> AmbientesBoxModel = new DefaultComboBoxModel<>(ComboBoxModels.toArray(tiposAmbientes));
                AmbienteCB.setModel(AmbientesBoxModel);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            List<String> tiposFicha = ComboBoxModels.BoxAmbientesModel();
            if (tiposFicha == null) {
                JOptionPane.showMessageDialog(null, "Hubo un error cargando los Ambientes de la API");
            } else {
                tiposFicha.add(0, "Seleccionar...");
                DefaultComboBoxModel<String> FichasBoxModel = new DefaultComboBoxModel<>(ComboBoxModels.toArray(tiposFicha));
                FichaCB.setModel(FichasBoxModel);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        actualizarTablaAsistencias();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaAsitencias = new javax.swing.JTable();
        ProgramaFormacionCB = new javax.swing.JComboBox<>();
        AmbienteCB = new javax.swing.JComboBox<>();
        FichaCB = new javax.swing.JComboBox<>();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 34, 64));
        jLabel3.setText("Buscardor");

        TablaAsitencias.setBackground(new java.awt.Color(255, 255, 255));
        TablaAsitencias.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TablaAsitencias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Ambiente", "Competencia", "Instructor", "Fecha", "Hora inicio", "Hora fin", "Tabla"
            }
        ));
        TablaAsitencias.setName(""); // NOI18N
        TablaAsitencias.setRowHeight(44);
        TablaAsitencias.setSelectionBackground(new java.awt.Color(215, 213, 177));
        jScrollPane1.setViewportView(TablaAsitencias);

        ProgramaFormacionCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ProgramaFormacionCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProgramaFormacionCBActionPerformed(evt);
            }
        });

        AmbienteCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        AmbienteCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AmbienteCBActionPerformed(evt);
            }
        });

        FichaCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        FichaCB.setEnabled(false);
        FichaCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FichaCBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(AmbienteCB, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(ProgramaFormacionCB, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(FichaCB, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ProgramaFormacionCB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AmbienteCB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FichaCB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void ProgramaFormacionCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProgramaFormacionCBActionPerformed
        if (!ProgramaFormacionCB.getSelectedItem().equals("Seleccionar...") && !ProgramaFormacionCB.getSelectedItem().equals("No aplica")) {
            try {
                ConvertirDatos convertirDatos = new ConvertirDatos();

                Integer idProgramaFormacion = convertirDatos.ObtenerIDProgramaFormacion(ProgramaFormacionCB.getSelectedItem().toString());
                List<Integer> tiposFichas = convertirDatos.ObtenerFichasPorPrograma(idProgramaFormacion);
                

                if (tiposFichas == null) {
                    JOptionPane.showMessageDialog(null, "Hubo un error cargando las Fichas de la API");
                } else {
                    List<String> tiposFichasStr = tiposFichas.stream().map(String::valueOf).collect(Collectors.toList());
                    tiposFichasStr.add(0, "Seleccionar...");
                    DefaultComboBoxModel<String> FichasBoxModel = new DefaultComboBoxModel<>(tiposFichasStr.toArray(new String[0]));
                    FichaCB.setModel(FichasBoxModel);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            FichaCB.setEnabled(true);
        } else {
            FichaCB.setSelectedItem("Seleccionar...");
            FichaCB.setEnabled(false);
        }
        actualizarTablaAsistencias();
    }//GEN-LAST:event_ProgramaFormacionCBActionPerformed

    private void AmbienteCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AmbienteCBActionPerformed
        actualizarTablaAsistencias();
    }//GEN-LAST:event_AmbienteCBActionPerformed

    private void FichaCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FichaCBActionPerformed
        actualizarTablaAsistencias();
    }//GEN-LAST:event_FichaCBActionPerformed

    
    private void actualizarTablaAsistencias() {
        Integer instructor = UserSession.getInstance().getDocumento();
        String ambiente = (String) AmbienteCB.getSelectedItem();
        if ("Seleccionar...".equals(ambiente) || "No Aplica".equals(ambiente)) {
            ambiente = null;
        }

        String programaFormacion = (String) ProgramaFormacionCB.getSelectedItem();
        if ("Seleccionar...".equals(programaFormacion) || "No aplica".equals(programaFormacion)) {
            programaFormacion = null;
        }

        Integer ficha = null;
        String fichaSeleccionada = (String) FichaCB.getSelectedItem();
        if (!"Seleccionar...".equals(fichaSeleccionada)) {
            try {
                ficha = Integer.parseInt(fichaSeleccionada);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Ficha no válida.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        try {
            ListarAsitenciasInstructorAPI listarAsis = new ListarAsitenciasInstructorAPI();
            DefaultTableModel modeloTabla = listarAsis.llenarTablaAsistencias(instructor, ambiente, programaFormacion, ficha);
            TablaAsitencias.setModel(modeloTabla);

            // Configurar el renderizador y el editor de botones en la tabla
            TablaAsitencias.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
            TablaAsitencias.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar la tabla de asistencias: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> AmbienteCB;
    private javax.swing.JComboBox<String> FichaCB;
    private javax.swing.JComboBox<String> ProgramaFormacionCB;
    private javax.swing.JTable TablaAsitencias;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
