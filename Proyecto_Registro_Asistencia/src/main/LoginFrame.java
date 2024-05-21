/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ItemEvent;
import javax.swing.ButtonGroup;
import main.util.API_Login.API_Login_Admin;
import main.util.API_Login.API_Login_Instructor;
import main.AdminFrames.AdminHomeScreen;
import main.InstructorFrames.InstructorHomeScreen;
import javax.swing.JOptionPane;
import javax.swing.border.AbstractBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonUI;


/**
 *
 * @author Jeisson Leon
 */
public class LoginFrame extends javax.swing.JFrame {

    /**
     * Creates new form LoginFrame
     */
    public LoginFrame() {
        initComponents();
        ModifComponent();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Ingresar = new javax.swing.JButton();
        UserField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        PassField = new javax.swing.JPasswordField();
        InstructorCheck = new javax.swing.JToggleButton();
        AdminCheck = new javax.swing.JToggleButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Iniciar sesion");

        Ingresar.setBackground(new java.awt.Color(57, 169, 0));
        Ingresar.setFont(new java.awt.Font("Dubai Light", 0, 24)); // NOI18N
        Ingresar.setForeground(new java.awt.Color(255, 255, 255));
        Ingresar.setText("Iniciar sesion");
        Ingresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                IngresarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                IngresarMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                IngresarMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                IngresarMouseReleased(evt);
            }
        });
        Ingresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IngresarActionPerformed(evt);
            }
        });

        UserField.setBackground(new java.awt.Color(255, 255, 255));
        UserField.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Usuario");

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Contraseña");

        PassField.setBackground(new java.awt.Color(255, 255, 255));
        PassField.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N

        InstructorCheck.setBackground(new java.awt.Color(255, 255, 255));
        InstructorCheck.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        InstructorCheck.setForeground(new java.awt.Color(0, 0, 0));
        InstructorCheck.setText("Instructor");
        InstructorCheck.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                InstructorCheckItemStateChanged(evt);
            }
        });

        AdminCheck.setBackground(new java.awt.Color(255, 255, 255));
        AdminCheck.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        AdminCheck.setForeground(new java.awt.Color(0, 0, 0));
        AdminCheck.setText("Admininistrador");
        AdminCheck.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                AdminCheckItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Ingresar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(PassField)
            .addComponent(UserField)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(107, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(114, 114, 114))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(InstructorCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AdminCheck, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(UserField, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PassField, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(InstructorCheck, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AdminCheck, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(Ingresar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 160, -1, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/util/icons/BgLog.jpg"))); // NOI18N
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 820));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
   
    


    private void ModifComponent(){
        ButtonGroup CheckButtons = new ButtonGroup();
        CheckButtons.add(InstructorCheck);
        CheckButtons.add(AdminCheck);
        this.setLocationRelativeTo(null);
        UserField.setBorder(new LineBorder(Color.decode("#39A900")));
        PassField.setBorder(new LineBorder(Color.decode("#39A900")));
        Ingresar.setUI(new BasicButtonUI());
        Ingresar.setFocusPainted(false);
        Ingresar.setContentAreaFilled(true);
        Ingresar.setOpaque(true);
        
        AbstractBorder border = new AbstractBorder() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Habilita el antialiasing
                g2d.setColor(Color.decode("#39A900"));
                g2d.drawRoundRect(x, y, width - 1, height - 1, 10, 10);
            }
        };

        UserField.setBorder(border);
        PassField.setBorder(border);

    }


// Este método se ejecuta cuando se realiza una acción en el botón Ingresar.
    private void IngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IngresarActionPerformed
        // Verifica si el checkbox AdminCheck está seleccionado.
        if (AdminCheck.isSelected()){
            // Crea una nueva instancia de API_Login_Admin.
            API_Login_Admin AdminLog = new API_Login_Admin();
            
            // Verifica las credenciales del administrador.
            if(AdminLog.LogAdmin(UserField.getText(), new String(PassField.getPassword()))){
                // Si las credenciales son válidas, muestra un mensaje de bienvenida.
                JOptionPane.showMessageDialog(null, "Usuario válido. Bienvenido.");
                // Crea una nueva instancia de AdminHomeScreen y la hace visible.
                AdminHomeScreen AdminHome = new AdminHomeScreen();
                AdminHome.setVisible(true);
                // Cierra la ventana actual.
                this.dispose();
            } else {
                // Si las credenciales no son válidas, muestra un mensaje de error.
                JOptionPane.showMessageDialog(null, "Usuario invalido o no registrado.");
            }
            
        // Verifica si el checkbox InstructorCheck está seleccionado.
        } else if (InstructorCheck.isSelected()){
            // Crea una nueva instancia de API_Login_Instructor.
            API_Login_Instructor InstructorLog = new API_Login_Instructor();
            if (InstructorLog.LogInstructor(UserField.getText(), PassField.getText())) {
                JOptionPane.showMessageDialog(null, "Usuario válido. Bienvenido.");
                InstructorHomeScreen InstructorHome = new InstructorHomeScreen();
                InstructorHome.setVisible(true);// Aquí puedes ver los datos del instructor
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Usuario invalido o no registrado.");
            }
        } else {
            // Si no se seleccionó ningún rol, muestra un mensaje solicitando la selección de un rol.
            JOptionPane.showMessageDialog(null, "Seleccione un rol");
        }
    }//GEN-LAST:event_IngresarActionPerformed

    private void IngresarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IngresarMouseEntered
        Ingresar.setBackground(Color.decode("#008550"));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_IngresarMouseEntered

    private void IngresarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IngresarMouseExited
        Ingresar.setBackground(Color.decode("#39A900"));
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_IngresarMouseExited

    private void IngresarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IngresarMousePressed
        Ingresar.setBackground(Color.decode("#87CA66"));
    }//GEN-LAST:event_IngresarMousePressed

    private void IngresarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IngresarMouseReleased
        Ingresar.setBackground(Color.decode("#39A900"));
    }//GEN-LAST:event_IngresarMouseReleased

    private void InstructorCheckItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_InstructorCheckItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            // El botón está activo, cambia el color de fondo a verde
            InstructorCheck.setBackground(Color.decode("#39A900"));
            InstructorCheck.setForeground(Color.WHITE);
        } else {
            // El botón no está activo, cambia el color de fondo a blanco
            InstructorCheck.setBackground(Color.WHITE);
            InstructorCheck.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_InstructorCheckItemStateChanged

    private void AdminCheckItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_AdminCheckItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            // El botón está activo, cambia el color de fondo a verde
            AdminCheck.setBackground(Color.decode("#39A900"));
            AdminCheck.setForeground(Color.WHITE);
        } else {
            // El botón no está activo, cambia el color de fondo a blanco
            AdminCheck.setBackground(Color.WHITE);
            AdminCheck.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_AdminCheckItemStateChanged

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
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton AdminCheck;
    private javax.swing.JButton Ingresar;
    private javax.swing.JToggleButton InstructorCheck;
    private javax.swing.JPasswordField PassField;
    private javax.swing.JTextField UserField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
