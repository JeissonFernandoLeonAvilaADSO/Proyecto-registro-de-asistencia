/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main.AdminFrames.AdminActionScreens;

import javax.swing.JOptionPane;
import main.util.API_AdminActions.API_Admin_BuscarUsuario;
import main.util.API_AdminActions.API_Admin_DeletUsuario;
import org.json.JSONObject;

/**
 *
 * @author Propietario
 */
public class BorrarUsuarioPanel extends javax.swing.JPanel {

    /**
     * Creates new form BorrarUsuarioPanel
     */
    public BorrarUsuarioPanel() {
        initComponents();
       
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
        ResultadoTelefono = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        ResultadoTipoDoc = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        IDInstructorField = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        ResultadoDocumento = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        ResultadoFicha = new javax.swing.JTextField();
        ResultadoProgramaFormacion = new javax.swing.JTextField();
        ResultadoGenero = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        ResultadoJornada = new javax.swing.JTextField();
        ResultadoNivelFormacion = new javax.swing.JTextField();
        ResultadoCorreo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        ResultadoArea = new javax.swing.JTextField();
        ResultadoContra = new javax.swing.JTextField();
        ResultadoSede = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        ResultadoApellido = new javax.swing.JTextField();
        ResultadoUsuario = new javax.swing.JTextField();
        ResultadoRol = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        ResultadoNombre = new javax.swing.JTextField();
        ResultadoCod = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        ConfirmarEliminarUsuario = new javax.swing.JButton();
        BuscarDatosUsuario = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        ResultadoTelefono.setEditable(false);
        ResultadoTelefono.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ResultadoTelefono.setFocusable(false);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Telefono");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Genero");

        ResultadoTipoDoc.setEditable(false);
        ResultadoTipoDoc.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ResultadoTipoDoc.setFocusable(false);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Tipo de documento");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 0, 0));
        jLabel36.setText("Numero de ficha");

        jLabel35.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 0, 0));
        jLabel35.setText("Programa formacion");

        IDInstructorField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        IDInstructorField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDInstructorFieldActionPerformed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 0));
        jLabel34.setText("Jornada de formacion");

        ResultadoDocumento.setEditable(false);
        ResultadoDocumento.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ResultadoDocumento.setFocusable(false);

        jLabel33.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 0, 0));
        jLabel33.setText("Nivel de formacion");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Documento");

        ResultadoFicha.setEditable(false);
        ResultadoFicha.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ResultadoFicha.setFocusable(false);

        ResultadoProgramaFormacion.setEditable(false);
        ResultadoProgramaFormacion.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ResultadoProgramaFormacion.setFocusable(false);

        ResultadoGenero.setEditable(false);
        ResultadoGenero.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ResultadoGenero.setFocusable(false);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Sede");

        ResultadoJornada.setEditable(false);
        ResultadoJornada.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ResultadoJornada.setFocusable(false);

        ResultadoNivelFormacion.setEditable(false);
        ResultadoNivelFormacion.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ResultadoNivelFormacion.setFocusable(false);

        ResultadoCorreo.setEditable(false);
        ResultadoCorreo.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ResultadoCorreo.setFocusable(false);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Nombres");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setText("Correo");

        ResultadoArea.setEditable(false);
        ResultadoArea.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ResultadoArea.setFocusable(false);

        ResultadoContra.setEditable(false);
        ResultadoContra.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ResultadoContra.setFocusable(false);

        ResultadoSede.setEditable(false);
        ResultadoSede.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ResultadoSede.setFocusable(false);

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 0));
        jLabel31.setText("Contraseña");

        ResultadoApellido.setEditable(false);
        ResultadoApellido.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ResultadoApellido.setFocusable(false);

        ResultadoUsuario.setEditable(false);
        ResultadoUsuario.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ResultadoUsuario.setFocusable(false);

        ResultadoRol.setEditable(false);
        ResultadoRol.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ResultadoRol.setFocusable(false);

        jLabel30.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 0));
        jLabel30.setText("Usuario");

        ResultadoNombre.setEditable(false);
        ResultadoNombre.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ResultadoNombre.setFocusable(false);

        ResultadoCod.setEditable(false);
        ResultadoCod.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ResultadoCod.setFocusable(false);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Documento del usuario");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Codigo de usuario");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setText("Area de trabajo");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("Rol");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setText("Apellidos");

        ConfirmarEliminarUsuario.setBackground(new java.awt.Color(0, 34, 64));
        ConfirmarEliminarUsuario.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ConfirmarEliminarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        ConfirmarEliminarUsuario.setText("Eliminar Usuario");
        ConfirmarEliminarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConfirmarEliminarUsuarioActionPerformed(evt);
            }
        });

        BuscarDatosUsuario.setBackground(new java.awt.Color(57, 169, 0));
        BuscarDatosUsuario.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        BuscarDatosUsuario.setForeground(new java.awt.Color(255, 255, 255));
        BuscarDatosUsuario.setText("Buscar");
        BuscarDatosUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarDatosUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(IDInstructorField)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BuscarDatosUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel18)
                                            .addComponent(jLabel31)
                                            .addComponent(jLabel30)
                                            .addComponent(jLabel16)
                                            .addComponent(jLabel15)
                                            .addComponent(jLabel20)
                                            .addComponent(jLabel14)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel13))
                                        .addGap(33, 33, 33)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(ResultadoGenero, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 855, Short.MAX_VALUE)
                                            .addComponent(ResultadoApellido, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(ResultadoNombre, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(ResultadoTipoDoc, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(ResultadoDocumento, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(ResultadoContra)
                                            .addComponent(ResultadoUsuario)
                                            .addComponent(ResultadoCod)
                                            .addComponent(ResultadoTelefono, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel35)
                                        .addGap(25, 25, 25)
                                        .addComponent(ResultadoProgramaFormacion))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel36)
                                        .addGap(53, 53, 53)
                                        .addComponent(ResultadoFicha))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel33)
                                            .addComponent(jLabel34)
                                            .addComponent(jLabel32)
                                            .addComponent(jLabel19)
                                            .addComponent(jLabel25)
                                            .addComponent(jLabel17))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(ResultadoSede)
                                            .addComponent(ResultadoArea, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(ResultadoRol, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(ResultadoCorreo, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(ResultadoJornada, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(ResultadoNivelFormacion)))
                                    .addComponent(ConfirmarEliminarUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(47, 47, 47))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IDInstructorField, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BuscarDatosUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ResultadoCod, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(ResultadoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(ResultadoContra, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(ResultadoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(ResultadoTipoDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ResultadoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(ResultadoApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(ResultadoGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(ResultadoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ResultadoProgramaFormacion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ResultadoFicha, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ResultadoNivelFormacion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ResultadoJornada, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ResultadoCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ResultadoRol, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ResultadoArea, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ResultadoSede, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(21, 21, 21)
                .addComponent(ConfirmarEliminarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    
     private void DesHabilitarCampos(){
        ResultadoCod.setEnabled(false);
        ResultadoUsuario.setEnabled(false);
        ResultadoContra.setEnabled(false);
        ResultadoDocumento.setEnabled(false);
        ResultadoTipoDoc.setEnabled(false);
        ResultadoNombre.setEnabled(false);
        ResultadoApellido.setEnabled(false);
        ResultadoGenero.setEnabled(false);
        ResultadoTelefono.setEnabled(false);
        ResultadoNivelFormacion.setEnabled(false);
        ResultadoJornada.setEnabled(false);
        ResultadoProgramaFormacion.setEnabled(false);
        ResultadoCorreo.setEnabled(false);
        ResultadoRol.setEnabled(false);
        ResultadoArea.setEnabled(false);
        ResultadoSede.setEnabled(false);
    }
    
    private void HabilitarCampos(){
        ResultadoCod.setEnabled(true);
        ResultadoUsuario.setEnabled(true);
        ResultadoContra.setEnabled(true);
        ResultadoDocumento.setEnabled(true);
        ResultadoTipoDoc.setEnabled(true);
        ResultadoNombre.setEnabled(true);
        ResultadoApellido.setEnabled(true);
        ResultadoGenero.setEnabled(true);
        ResultadoTelefono.setEnabled(true);
        ResultadoNivelFormacion.setEnabled(true);
        ResultadoJornada.setEnabled(true);
        ResultadoProgramaFormacion.setEnabled(true);
        ResultadoCorreo.setEnabled(true);
        ResultadoRol.setEnabled(true);
        ResultadoArea.setEnabled(true);
        ResultadoSede.setEnabled(true);
    }
    
    private void LimpiarCampos(){
        IDInstructorField.setText("");
        ResultadoCod.setText("");
        ResultadoUsuario.setText("");
        ResultadoContra.setText("");
        ResultadoDocumento.setText("");
        ResultadoTipoDoc.setText("");
        ResultadoNombre.setText("");
        ResultadoApellido.setText("");
        ResultadoGenero.setText("");
        ResultadoTelefono.setText("");
        ResultadoCorreo.setText("");
        ResultadoRol.setText("");
        ResultadoArea.setText("");
        ResultadoSede.setText("");
    }
    
    
    private void IDInstructorFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IDInstructorFieldActionPerformed
        BuscarDatosUsuario.doClick();
    }//GEN-LAST:event_IDInstructorFieldActionPerformed

    private void ConfirmarEliminarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfirmarEliminarUsuarioActionPerformed
        // Muestra un cuadro de diálogo de confirmación.
        int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro que desea modificar los datos del instructor?", "Confirmacion" ,JOptionPane.YES_NO_CANCEL_OPTION);

        // Procesa la respuesta del usuario.
        switch (respuesta){
            case JOptionPane.YES_OPTION:
            API_Admin_DeletUsuario borrarUsuario = new API_Admin_DeletUsuario();
            borrarUsuario.AdminEliminarUsuario(Integer.valueOf(ResultadoDocumento.getText()));
            LimpiarCampos();
            DesHabilitarCampos();
            break;

            case JOptionPane.NO_OPTION:
            // Si el usuario rechaza, no se hace nada.
            break;

            case JOptionPane.CANCEL_OPTION:
            // Si el usuario cancela, no se hace nada.
            break;
        }
    }//GEN-LAST:event_ConfirmarEliminarUsuarioActionPerformed

    private void BuscarDatosUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarDatosUsuarioActionPerformed
        // Crea una nueva instancia de DB_AdminBuscarInstructor.
        API_Admin_BuscarUsuario buscarUsuario = new API_Admin_BuscarUsuario();

        // Intenta buscar al instructor en la base de datos.
        JSONObject usuario = buscarUsuario.AdminBuscarUsuario(IDInstructorField.getText());
        if (usuario != null){
            ResultadoCod.setText(String.valueOf(usuario.getInt("id")));
            ResultadoUsuario.setText(usuario.getString("user"));
            ResultadoContra.setText(usuario.getString("pass"));
            ResultadoDocumento.setText(usuario.getString("documento"));
            ResultadoTipoDoc.setText(usuario.getString("tipoDocumento"));
            ResultadoNombre.setText(usuario.getString("nombres"));
            ResultadoApellido.setText(usuario.getString("apellidos"));
            ResultadoGenero.setText(usuario.getString("genero"));
            ResultadoCorreo.setText(usuario.getString("correo"));
            ResultadoTelefono.setText(usuario.getString("telefono"));
            ResultadoProgramaFormacion.setText(usuario.getString("programaFormacion"));
            ResultadoFicha.setText(String.valueOf(usuario.getInt("numeroFicha")));
            ResultadoJornada.setText(usuario.getString("jornadaFormacion"));
            ResultadoNivelFormacion.setText(usuario.getString("nivelFormacion"));
            ResultadoRol.setText(usuario.getString("rol"));
            ResultadoArea.setText(usuario.getString("area"));
            ResultadoSede.setText(usuario.getString("sede"));
        }
    }//GEN-LAST:event_BuscarDatosUsuarioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BuscarDatosUsuario;
    private javax.swing.JButton ConfirmarEliminarUsuario;
    private javax.swing.JTextField IDInstructorField;
    private javax.swing.JTextField ResultadoApellido;
    private javax.swing.JTextField ResultadoArea;
    private javax.swing.JTextField ResultadoCod;
    private javax.swing.JTextField ResultadoContra;
    private javax.swing.JTextField ResultadoCorreo;
    private javax.swing.JTextField ResultadoDocumento;
    private javax.swing.JTextField ResultadoFicha;
    private javax.swing.JTextField ResultadoGenero;
    private javax.swing.JTextField ResultadoJornada;
    private javax.swing.JTextField ResultadoNivelFormacion;
    private javax.swing.JTextField ResultadoNombre;
    private javax.swing.JTextField ResultadoProgramaFormacion;
    private javax.swing.JTextField ResultadoRol;
    private javax.swing.JTextField ResultadoSede;
    private javax.swing.JTextField ResultadoTelefono;
    private javax.swing.JTextField ResultadoTipoDoc;
    private javax.swing.JTextField ResultadoUsuario;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
