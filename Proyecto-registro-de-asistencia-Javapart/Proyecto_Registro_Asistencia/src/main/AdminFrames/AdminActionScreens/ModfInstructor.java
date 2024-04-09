/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main.AdminFrames.AdminActionScreens;

import java.awt.Component;
import javax.swing.JOptionPane;
import main.util.DB_AdminActions.DB_AdminBuscarInstructor;
import main.util.DB_AdminActions.DB_Admin_ModifInstructor;
import main.AdminFrames.AdminHomeScreen;
/**
 *
 * @author IZHAR
 */
public class ModfInstructor extends javax.swing.JFrame {

    /**
     * Creates new form ModfInstructor
     */
    public ModfInstructor() {
        initComponents();
        this.setLocationRelativeTo(null);
        for (Component component : Panel_ModificarDatos.getComponents()) {
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

        jPanel1 = new javax.swing.JPanel();
        Panel_ModificarDatos = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        CheckActivarApellido = new javax.swing.JCheckBox();
        ModificarInstructor = new javax.swing.JButton();
        CheckActivarCorreo = new javax.swing.JCheckBox();
        ModificarApellidoInstructor = new javax.swing.JTextField();
        CheckActivarArea = new javax.swing.JCheckBox();
        ModificarCorreoInstructor = new javax.swing.JTextField();
        CheckActivarContra = new javax.swing.JCheckBox();
        ModificarNombreInstructor = new javax.swing.JTextField();
        ModificarContraInstructor = new javax.swing.JTextField();
        ModificarAreaInstructor = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        CheckActivarNombre = new javax.swing.JCheckBox();
        jLabel13 = new javax.swing.JLabel();
        ModificarCedulaInstructor = new javax.swing.JTextField();
        CheckActivarCedula = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        ResultadoNombreInstructor = new javax.swing.JTextField();
        ResultadoCorreoInstructor = new javax.swing.JTextField();
        ResultadoApellidoInstructor = new javax.swing.JTextField();
        ResultadoContraInstructor = new javax.swing.JTextField();
        ResultadoAreaInstructor = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        BuscarDatosInstructor = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        ResultadoCedulaInstructor = new javax.swing.JTextField();
        IDInstructorField = new javax.swing.JTextField();
        VolverHome = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Panel_ModificarDatos.setBorder(javax.swing.BorderFactory.createTitledBorder("Modificar Datos"));

        jLabel5.setText("Contraseña");

        CheckActivarApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckActivarApellidoActionPerformed(evt);
            }
        });

        ModificarInstructor.setText("Modificar datos del instructor");
        ModificarInstructor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarInstructorActionPerformed(evt);
            }
        });

        CheckActivarCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckActivarCorreoActionPerformed(evt);
            }
        });

        ModificarApellidoInstructor.setEnabled(false);

        CheckActivarArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckActivarAreaActionPerformed(evt);
            }
        });

        ModificarCorreoInstructor.setEnabled(false);

        CheckActivarContra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckActivarContraActionPerformed(evt);
            }
        });

        ModificarNombreInstructor.setEnabled(false);

        ModificarContraInstructor.setEnabled(false);

        ModificarAreaInstructor.setEnabled(false);

        jLabel1.setText("Nombre");

        jLabel2.setText("Apellido");

        jLabel3.setText("Correo");

        jLabel4.setText("Area de trabajo");

        CheckActivarNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckActivarNombreActionPerformed(evt);
            }
        });

        jLabel13.setText("Cedula");

        ModificarCedulaInstructor.setEnabled(false);

        CheckActivarCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckActivarCedulaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_ModificarDatosLayout = new javax.swing.GroupLayout(Panel_ModificarDatos);
        Panel_ModificarDatos.setLayout(Panel_ModificarDatosLayout);
        Panel_ModificarDatosLayout.setHorizontalGroup(
            Panel_ModificarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_ModificarDatosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(Panel_ModificarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_ModificarDatosLayout.createSequentialGroup()
                        .addGroup(Panel_ModificarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel13)
                            .addComponent(ModificarCedulaInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CheckActivarCedula))
                    .addGroup(Panel_ModificarDatosLayout.createSequentialGroup()
                        .addGroup(Panel_ModificarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(ModificarInstructor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ModificarApellidoInstructor)
                            .addComponent(ModificarCorreoInstructor)
                            .addComponent(ModificarContraInstructor)
                            .addComponent(ModificarAreaInstructor)
                            .addComponent(ModificarNombreInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Panel_ModificarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CheckActivarCorreo)
                            .addComponent(CheckActivarArea)
                            .addComponent(CheckActivarContra)
                            .addComponent(CheckActivarNombre)
                            .addComponent(CheckActivarApellido))))
                .addGap(22, 22, 22))
        );
        Panel_ModificarDatosLayout.setVerticalGroup(
            Panel_ModificarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_ModificarDatosLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addGap(4, 4, 4)
                .addGroup(Panel_ModificarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ModificarCedulaInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CheckActivarCedula))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(4, 4, 4)
                .addGroup(Panel_ModificarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ModificarNombreInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CheckActivarNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_ModificarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ModificarApellidoInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CheckActivarApellido))
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_ModificarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ModificarCorreoInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CheckActivarCorreo))
                .addGap(16, 16, 16)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_ModificarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ModificarAreaInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CheckActivarArea))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_ModificarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ModificarContraInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CheckActivarContra))
                .addGap(18, 18, 18)
                .addComponent(ModificarInstructor)
                .addGap(42, 42, 42))
        );

        jLabel6.setText("Documento del instructor");

        ResultadoNombreInstructor.setEditable(false);
        ResultadoNombreInstructor.setFocusable(false);

        ResultadoCorreoInstructor.setEditable(false);
        ResultadoCorreoInstructor.setFocusable(false);

        ResultadoApellidoInstructor.setEditable(false);
        ResultadoApellidoInstructor.setFocusable(false);

        ResultadoContraInstructor.setEditable(false);
        ResultadoContraInstructor.setFocusable(false);

        ResultadoAreaInstructor.setEditable(false);
        ResultadoAreaInstructor.setFocusable(false);

        jLabel7.setText("Nombre");

        jLabel8.setText("Apellido");

        jLabel9.setText("Correo");

        jLabel10.setText("Area de trabajo");

        jLabel11.setText("Contraseña");

        BuscarDatosInstructor.setText("Buscar");
        BuscarDatosInstructor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarDatosInstructorActionPerformed(evt);
            }
        });

        jLabel12.setText("Cedula");

        ResultadoCedulaInstructor.setEditable(false);
        ResultadoCedulaInstructor.setFocusable(false);

        VolverHome.setText("Volver");
        VolverHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VolverHomeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(ResultadoNombreInstructor)
                            .addComponent(ResultadoApellidoInstructor)
                            .addComponent(ResultadoCorreoInstructor)
                            .addComponent(ResultadoAreaInstructor)
                            .addComponent(ResultadoContraInstructor, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                            .addComponent(jLabel12)
                            .addComponent(ResultadoCedulaInstructor)
                            .addComponent(VolverHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(79, 79, 79)
                        .addComponent(Panel_ModificarDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(65, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(IDInstructorField, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(BuscarDatosInstructor))
                            .addComponent(jLabel6))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BuscarDatosInstructor)
                    .addComponent(IDInstructorField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(Panel_ModificarDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ResultadoCedulaInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ResultadoNombreInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ResultadoApellidoInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ResultadoCorreoInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ResultadoAreaInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ResultadoContraInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(VolverHome)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Método que se ejecuta cuando se realiza una acción sobre el checkbox CheckActivarNombre.
     * Habilita o deshabilita el campo ModificarNombreInstructor dependiendo del estado del checkbox.
     *
     */
    private void CheckActivarNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckActivarNombreActionPerformed
        if (CheckActivarNombre.isSelected()){
            ModificarNombreInstructor.setEnabled(true);
        } else {
            ModificarNombreInstructor.setEnabled(false);
        }
    }//GEN-LAST:event_CheckActivarNombreActionPerformed

    /**
     * Método que se ejecuta cuando se realiza una acción sobre el checkbox CheckActivarContra.
     * Habilita o deshabilita el campo ModificarContraInstructor dependiendo del estado del checkbox.
     *
     */
    private void CheckActivarContraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckActivarContraActionPerformed
        if (CheckActivarContra.isSelected()){
            ModificarContraInstructor.setEnabled(true);
        } else {
            ModificarContraInstructor.setEnabled(false);
        }
    }//GEN-LAST:event_CheckActivarContraActionPerformed

    /**
     * Método que se ejecuta cuando se realiza una acción sobre el checkbox CheckActivarArea.
     * Habilita o deshabilita el campo ModificarAreaInstructor dependiendo del estado del checkbox.
     *
     */
    private void CheckActivarAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckActivarAreaActionPerformed
        if (CheckActivarArea.isSelected()){
            ModificarAreaInstructor.setEnabled(true);
        } else {
            ModificarAreaInstructor.setEnabled(false);
        }
    }//GEN-LAST:event_CheckActivarAreaActionPerformed

    /**
     * Método que se ejecuta cuando se realiza una acción sobre el checkbox CheckActivarCorreo.
     * Habilita o deshabilita el campo ModificarCorreoInstructor dependiendo del estado del checkbox.
     *
     */
    private void CheckActivarCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckActivarCorreoActionPerformed
        if (CheckActivarCorreo.isSelected()){
            ModificarCorreoInstructor.setEnabled(true);
        } else {
            ModificarCorreoInstructor.setEnabled(false);
        }
    }//GEN-LAST:event_CheckActivarCorreoActionPerformed


    /**
     * Método que se ejecuta cuando se realiza una acción sobre el botón ModificarInstructor.
     * Muestra un cuadro de diálogo de confirmación y, si el usuario confirma, modifica los datos del instructor.
     *
     */
    private void ModificarInstructorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarInstructorActionPerformed
        // Muestra un cuadro de diálogo de confirmación.
        int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro que desea modificar los datos del instructor?", "Confirmacion" ,JOptionPane.YES_NO_CANCEL_OPTION);

        // Procesa la respuesta del usuario.
        switch (respuesta){
            case JOptionPane.YES_OPTION:
                // Si el usuario confirma, crea una instancia de DB_Admin_ModifInstructor y llama al método AdminModifInstructor con los datos del instructor.
                DB_Admin_ModifInstructor modifInstructor = new DB_Admin_ModifInstructor();
                modifInstructor.AdminModifInstructor(CheckActivarCedula.isSelected(),
                        CheckActivarNombre.isSelected(),
                        CheckActivarApellido.isSelected(),
                        CheckActivarCorreo.isSelected(),
                        CheckActivarArea.isSelected(),
                        CheckActivarContra.isSelected(),
                        Integer.parseInt(ModificarCedulaInstructor.getText()),
                        ModificarNombreInstructor.getText(),
                        ModificarApellidoInstructor.getText(),
                        ModificarCorreoInstructor.getText(),
                        ModificarAreaInstructor.getText(),
                        ModificarContraInstructor.getText(),
                        Integer.parseInt(ResultadoCedulaInstructor.getText()));
                break;

            case JOptionPane.NO_OPTION:
                // Si el usuario rechaza, no se hace nada.
                break;

            case JOptionPane.CANCEL_OPTION:
                // Si el usuario cancela, no se hace nada.
                break;
        }
    }//GEN-LAST:event_ModificarInstructorActionPerformed

    /**
     * Método que se ejecuta cuando se realiza una acción sobre el checkbox CheckActivarApellido.
     * Habilita o deshabilita el campo ModificarApellidoInstructor dependiendo del estado del checkbox.
     *
     */
    private void CheckActivarApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckActivarApellidoActionPerformed
        if (CheckActivarApellido.isSelected()){
            ModificarApellidoInstructor.setEnabled(true);
        } else {
            ModificarApellidoInstructor.setEnabled(false);
        }
    }//GEN-LAST:event_CheckActivarApellidoActionPerformed


    /**
     * Método que se ejecuta cuando se realiza una acción sobre el checkbox CheckActivarCedula.
     * Habilita o deshabilita el campo ModificarCedulaInstructor dependiendo del estado del checkbox.
     *
     */
    private void CheckActivarCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckActivarCedulaActionPerformed
        if (CheckActivarCedula.isSelected()){
            ModificarCedulaInstructor.setEnabled(true);
        } else {
            ModificarCedulaInstructor.setEnabled(false);
        }
    }//GEN-LAST:event_CheckActivarCedulaActionPerformed

    
    private void DesHabilitarCampos(){
        ModificarCedulaInstructor.setEnabled(false);
        ModificarNombreInstructor.setEnabled(false);
        ModificarApellidoInstructor.setEnabled(false);
        ModificarCorreoInstructor.setEnabled(false);
        ModificarAreaInstructor.setEnabled(false);
        ModificarContraInstructor.setEnabled(false);
    }
    
    private void HabilitarCampos(){
        ModificarCedulaInstructor.setEnabled(true);
        ModificarNombreInstructor.setEnabled(true);
        ModificarApellidoInstructor.setEnabled(true);
        ModificarCorreoInstructor.setEnabled(true);
        ModificarAreaInstructor.setEnabled(true);
        ModificarContraInstructor.setEnabled(true);
    }
    
    private void BuscarDatosInstructorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarDatosInstructorActionPerformed
        // Crea una nueva instancia de DB_AdminBuscarInstructor.
        DB_AdminBuscarInstructor buscarInstructor = new DB_AdminBuscarInstructor();

        // Intenta buscar al instructor en la base de datos.
        if (buscarInstructor.AdminBuscarInstructor(Integer.parseInt(IDInstructorField.getText()))){
            // Si el instructor se encuentra, se muestran sus datos en los campos correspondientes.
            ResultadoCedulaInstructor.setText(buscarInstructor.ResultadoCedulaInstructor);
            ResultadoNombreInstructor.setText(buscarInstructor.ResultadoNombreInstructor);
            ResultadoApellidoInstructor.setText(buscarInstructor.ResultadoApellidoInstructor);
            ResultadoCorreoInstructor.setText(buscarInstructor.ResultadoCorreoInstructor);
            ResultadoAreaInstructor.setText(buscarInstructor.ResultadoAreaInstructor);
            ResultadoContraInstructor.setText(buscarInstructor.ResultadoContraInstructor);
            IDInstructorField.setText("");

            // Habilita los componentes del Panel_ModificarDatos.
            for (Component component : Panel_ModificarDatos.getComponents()) {
                component.setEnabled(true);
            }

            // Deshabilita los campos de entrada de datos.
            DesHabilitarCampos();
        } else {
            // Si el instructor no se encuentra, se muestra un mensaje de error y se deshabilitan los componentes del Panel_ModificarDatos.
            JOptionPane.showMessageDialog(null, "No se encontraron coincidencias");
            for (Component component : Panel_ModificarDatos.getComponents()) {
                component.setEnabled(false);
            }
        }
    }//GEN-LAST:event_BuscarDatosInstructorActionPerformed

    private void VolverHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VolverHomeActionPerformed
        AdminHomeScreen adminHomre = new AdminHomeScreen();
        adminHomre.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_VolverHomeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ModfInstructor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ModfInstructor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ModfInstructor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ModfInstructor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ModfInstructor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BuscarDatosInstructor;
    private javax.swing.JCheckBox CheckActivarApellido;
    private javax.swing.JCheckBox CheckActivarArea;
    private javax.swing.JCheckBox CheckActivarCedula;
    private javax.swing.JCheckBox CheckActivarContra;
    private javax.swing.JCheckBox CheckActivarCorreo;
    private javax.swing.JCheckBox CheckActivarNombre;
    private javax.swing.JTextField IDInstructorField;
    private javax.swing.JTextField ModificarApellidoInstructor;
    private javax.swing.JTextField ModificarAreaInstructor;
    private javax.swing.JTextField ModificarCedulaInstructor;
    private javax.swing.JTextField ModificarContraInstructor;
    private javax.swing.JTextField ModificarCorreoInstructor;
    private javax.swing.JButton ModificarInstructor;
    private javax.swing.JTextField ModificarNombreInstructor;
    private javax.swing.JPanel Panel_ModificarDatos;
    private javax.swing.JTextField ResultadoApellidoInstructor;
    private javax.swing.JTextField ResultadoAreaInstructor;
    private javax.swing.JTextField ResultadoCedulaInstructor;
    private javax.swing.JTextField ResultadoContraInstructor;
    private javax.swing.JTextField ResultadoCorreoInstructor;
    private javax.swing.JTextField ResultadoNombreInstructor;
    private javax.swing.JButton VolverHome;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
