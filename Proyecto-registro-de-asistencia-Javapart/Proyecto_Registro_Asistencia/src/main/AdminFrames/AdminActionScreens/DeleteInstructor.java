/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main.AdminFrames.AdminActionScreens;
import javax.swing.JOptionPane;
import main.util.DB_AdminActions.DB_AdminBuscarInstructor;
import main.util.DB_AdminActions.DB_Admin_DeletInstructor;
import main.AdminFrames.AdminHomeScreen;
/**
/**
 *
 * @author IZHAR
 */
public class DeleteInstructor extends javax.swing.JFrame {

    /**
     * Creates new form DeleteInstructor
     */
    public DeleteInstructor() {
        initComponents();
        this.setLocationRelativeTo(null);
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
        IDInstructorField = new javax.swing.JTextField();
        BuscarDatosInstructor = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        ResultadoCedulaInstructor = new javax.swing.JTextField();
        ResultadoNombreInstructor = new javax.swing.JTextField();
        ResultadoApellidoInstructor = new javax.swing.JTextField();
        ResultadoCorreoInstructor = new javax.swing.JTextField();
        ResultadoAreaInstructor = new javax.swing.JTextField();
        ResultadoContraInstructor = new javax.swing.JTextField();
        VolverHome = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        EliminarInstructor = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        BuscarDatosInstructor.setText("Buscar");
        BuscarDatosInstructor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarDatosInstructorActionPerformed(evt);
            }
        });

        jLabel1.setText("Cedula");

        jLabel2.setText("Nombre");

        jLabel3.setText("Apellido");

        jLabel4.setText("Correo");

        jLabel5.setText("Contraseña");

        jLabel6.setText("Area de trabajo");

        ResultadoCedulaInstructor.setEditable(false);
        ResultadoCedulaInstructor.setToolTipText("");
        ResultadoCedulaInstructor.setFocusable(false);

        ResultadoNombreInstructor.setEditable(false);
        ResultadoNombreInstructor.setToolTipText("");
        ResultadoNombreInstructor.setFocusable(false);

        ResultadoApellidoInstructor.setEditable(false);
        ResultadoApellidoInstructor.setToolTipText("");
        ResultadoApellidoInstructor.setFocusable(false);

        ResultadoCorreoInstructor.setEditable(false);
        ResultadoCorreoInstructor.setToolTipText("");
        ResultadoCorreoInstructor.setFocusable(false);

        ResultadoAreaInstructor.setEditable(false);
        ResultadoAreaInstructor.setToolTipText("");
        ResultadoAreaInstructor.setFocusable(false);

        ResultadoContraInstructor.setEditable(false);
        ResultadoContraInstructor.setToolTipText("");
        ResultadoContraInstructor.setFocusable(false);

        VolverHome.setText("Volver");
        VolverHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VolverHomeActionPerformed(evt);
            }
        });

        jLabel7.setText("Documento del instructor");

        EliminarInstructor.setText("Eliminar instructor");
        EliminarInstructor.setEnabled(false);
        EliminarInstructor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarInstructorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(IDInstructorField, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BuscarDatosInstructor))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(ResultadoCedulaInstructor)
                            .addComponent(ResultadoNombreInstructor)
                            .addComponent(ResultadoApellidoInstructor)
                            .addComponent(ResultadoCorreoInstructor)
                            .addComponent(ResultadoAreaInstructor)
                            .addComponent(ResultadoContraInstructor)
                            .addComponent(VolverHome, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(131, 131, 131)
                        .addComponent(EliminarInstructor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(81, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IDInstructorField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BuscarDatosInstructor))
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ResultadoCedulaInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(ResultadoNombreInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ResultadoApellidoInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ResultadoCorreoInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6))
                    .addComponent(EliminarInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ResultadoAreaInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ResultadoContraInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(VolverHome)
                .addContainerGap(33, Short.MAX_VALUE))
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
            EliminarInstructor.setEnabled(true);
            IDInstructorField.setText("");

        } else {
            // Si el instructor no se encuentra, se muestra un mensaje de error y se deshabilitan los componentes del Panel_ModificarDatos.
            JOptionPane.showMessageDialog(null, "No se encontraron coincidencias");
            EliminarInstructor.setEnabled(false);
        }
    }//GEN-LAST:event_BuscarDatosInstructorActionPerformed

    private void VolverHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VolverHomeActionPerformed
        AdminHomeScreen adminHomre = new AdminHomeScreen();
        adminHomre.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_VolverHomeActionPerformed

    private void EliminarInstructorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarInstructorActionPerformed
                // Muestra un cuadro de diálogo de confirmación.
        int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro que desea modificar los datos del instructor?", "Confirmacion" ,JOptionPane.YES_NO_CANCEL_OPTION);

        // Procesa la respuesta del usuario.
        switch (respuesta){
            case JOptionPane.YES_OPTION:
                // Si el usuario confirma, crea una instancia de DB_Admin_ModifInstructor y llama al método AdminModifInstructor con los datos del instructor.
                DB_Admin_DeletInstructor modifInstructor = new DB_Admin_DeletInstructor();
                modifInstructor.borrarInstructor(Integer.parseInt(ResultadoCedulaInstructor.getText()));
                break;

            case JOptionPane.NO_OPTION:
                // Si el usuario rechaza, no se hace nada.
                break;

            case JOptionPane.CANCEL_OPTION:
                // Si el usuario cancela, no se hace nada.
                break;
        }
    }//GEN-LAST:event_EliminarInstructorActionPerformed

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
            java.util.logging.Logger.getLogger(DeleteInstructor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DeleteInstructor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DeleteInstructor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DeleteInstructor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DeleteInstructor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BuscarDatosInstructor;
    private javax.swing.JButton EliminarInstructor;
    private javax.swing.JTextField IDInstructorField;
    private javax.swing.JTextField ResultadoApellidoInstructor;
    private javax.swing.JTextField ResultadoAreaInstructor;
    private javax.swing.JTextField ResultadoCedulaInstructor;
    private javax.swing.JTextField ResultadoContraInstructor;
    private javax.swing.JTextField ResultadoCorreoInstructor;
    private javax.swing.JTextField ResultadoNombreInstructor;
    private javax.swing.JButton VolverHome;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
