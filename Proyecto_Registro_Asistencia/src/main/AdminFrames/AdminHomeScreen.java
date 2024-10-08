/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main.AdminFrames;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import main.AdminFrames.AdminActionScreens.BorrarUsuarioPanel;
import main.AdminFrames.AdminActionScreens.CrearUsuarioPanel;
import main.AdminFrames.AdminActionScreens.DataManagerPanel;
import main.AdminFrames.AdminActionScreens.ModificarUsuarioPanel;
import main.LoginFrame;
import main.util.models.ButtonStyler;

/**
 *
 * @author Jeisson Leon
 */
public class AdminHomeScreen extends javax.swing.JFrame {

    /**
     * Creates new form AdminHomeScreen
     */
    public AdminHomeScreen() {
        initComponents();
        try{
            AditionalConfig();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.setLocationRelativeTo(null);
    }
    
    private CardLayout cardLayout;
    
    private void AditionalConfig() throws Exception{
        
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int frameWidth = (int) (screenSize.width * 0.95);
    int frameHeight = (int) (screenSize.height * 0.95);
    
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;


    // Configurar el tamaño del JFrame
    if (screenWidth < 1700 && screenHeight < 900){
        this.setSize(frameWidth, frameHeight);
    }
    
    this.setLocationRelativeTo(null); // Centrar el JFrame

    if (screenWidth < 1920 || screenHeight < 1080) {
    
        // Crear un JScrollPane con barras de desplazamiento visibles
        JScrollPane scrollPane = new JScrollPane(jPanel1);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Establecer el layout del JFrame a BorderLayout para que el scrollPane ocupe todo el espacio
        this.setLayout(new BorderLayout());

        // Agregar el JScrollPane al JFrame
        this.add(scrollPane, BorderLayout.CENTER);

        // Manejar el desplazamiento con la rueda del ratón en cualquier parte del JFrame
        this.addMouseWheelListener(e -> {
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
            JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
            int notches = e.getWheelRotation();

            if (e.isShiftDown()) {
                // Si se presiona Shift, desplazarse horizontalmente
                int newValue = horizontalScrollBar.getValue() + notches * 20; // Ajustar la velocidad de desplazamiento
                horizontalScrollBar.setValue(newValue);
            } else {
                // De lo contrario, desplazarse verticalmente
                int newValue = verticalScrollBar.getValue() + notches * 20; // Ajustar la velocidad de desplazamiento
                verticalScrollBar.setValue(newValue);
            }
        });
    }
        ButtonStyler ButtonStyler = new ButtonStyler();
        ButtonStyler.applyPrimaryStyle(CerrarSesion);
        ButtonStyler.applySecondaryStyle(CrearUsuarioFrame);
        ButtonStyler.applySecondaryStyle(ModificarUsuarioFrame);
        ButtonStyler.applySecondaryStyle(EliminarUsuarioFrame);
        ButtonStyler.applySecondaryStyle(DataFrame);
        
        cardLayout = new CardLayout();
        MainPanel.setLayout(cardLayout);
        
        CrearUsuarioPanel crearUsuario = new CrearUsuarioPanel();
        DataManagerPanel dataMan = new DataManagerPanel();
        ModificarUsuarioPanel ModUsuario = new ModificarUsuarioPanel();
        BorrarUsuarioPanel delUsuario = new BorrarUsuarioPanel();
        
        MainPanel.add(HomePanel, "HomePanel");
        MainPanel.add(crearUsuario, "CrearUsuarioPanel");
        MainPanel.add(dataMan, "DataManagerPanel");
        MainPanel.add(ModUsuario, "ModUsuarioPanel");
        MainPanel.add(delUsuario, "EliminarUsuarioPanel");
        
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
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        CerrarSesion = new javax.swing.JButton();
        ModificarUsuarioFrame = new javax.swing.JButton();
        EliminarUsuarioFrame = new javax.swing.JButton();
        CrearUsuarioFrame = new javax.swing.JButton();
        DataFrame = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        MainPanel = new javax.swing.JPanel();
        HomePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(102, 132, 147));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 208, 78));
        jLabel3.setText("Acciones");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/util/icons/LogoSena.png"))); // NOI18N

        jButton4.setBackground(new java.awt.Color(255, 208, 78));
        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 34, 64));
        jButton4.setText("Administrador");
        jButton4.setBorderPainted(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("________________________________________________________");

        CerrarSesion.setBackground(new java.awt.Color(57, 169, 0));
        CerrarSesion.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        CerrarSesion.setForeground(new java.awt.Color(255, 255, 255));
        CerrarSesion.setText("Cerrar sesión");
        CerrarSesion.setBorderPainted(false);
        CerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CerrarSesionActionPerformed(evt);
            }
        });

        ModificarUsuarioFrame.setBackground(new java.awt.Color(0, 34, 64));
        ModificarUsuarioFrame.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ModificarUsuarioFrame.setForeground(new java.awt.Color(255, 255, 255));
        ModificarUsuarioFrame.setText("Modificar usuario");
        ModificarUsuarioFrame.setBorderPainted(false);
        ModificarUsuarioFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarUsuarioFrameActionPerformed(evt);
            }
        });

        EliminarUsuarioFrame.setBackground(new java.awt.Color(0, 34, 64));
        EliminarUsuarioFrame.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        EliminarUsuarioFrame.setForeground(new java.awt.Color(255, 255, 255));
        EliminarUsuarioFrame.setText("Eliminar usuario");
        EliminarUsuarioFrame.setBorderPainted(false);
        EliminarUsuarioFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarUsuarioFrameActionPerformed(evt);
            }
        });

        CrearUsuarioFrame.setBackground(new java.awt.Color(0, 34, 64));
        CrearUsuarioFrame.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        CrearUsuarioFrame.setForeground(new java.awt.Color(255, 255, 255));
        CrearUsuarioFrame.setText("Crear usuario");
        CrearUsuarioFrame.setBorderPainted(false);
        CrearUsuarioFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CrearUsuarioFrameActionPerformed(evt);
            }
        });

        DataFrame.setBackground(new java.awt.Color(0, 34, 64));
        DataFrame.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        DataFrame.setForeground(new java.awt.Color(255, 255, 255));
        DataFrame.setText("Gestionar Datos");
        DataFrame.setBorderPainted(false);
        DataFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DataFrameActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 208, 78));
        jLabel5.setText("Control");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(CrearUsuarioFrame, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ModificarUsuarioFrame, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(EliminarUsuarioFrame, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel7))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(168, 168, 168)
                                .addComponent(jLabel3))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(177, 177, 177)
                                .addComponent(jLabel5)))
                        .addGap(0, 46, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DataFrame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(CerrarSesion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CrearUsuarioFrame, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ModificarUsuarioFrame, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EliminarUsuarioFrame, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DataFrame, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 271, Short.MAX_VALUE)
                .addComponent(CerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );

        MainPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(0, 34, 64));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 34, 64));
        jLabel1.setText("Bienvenido al sistema de ");

        jLabel2.setBackground(new java.awt.Color(0, 34, 64));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 34, 64));
        jLabel2.setText("registro de asistencia");

        javax.swing.GroupLayout HomePanelLayout = new javax.swing.GroupLayout(HomePanel);
        HomePanel.setLayout(HomePanelLayout);
        HomePanelLayout.setHorizontalGroup(
            HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 414, Short.MAX_VALUE)
            .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(HomePanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addGroup(HomePanelLayout.createSequentialGroup()
                            .addGap(35, 35, 35)
                            .addComponent(jLabel2)))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        HomePanelLayout.setVerticalGroup(
            HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
            .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(HomePanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel2)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout MainPanelLayout = new javax.swing.GroupLayout(MainPanel);
        MainPanel.setLayout(MainPanelLayout);
        MainPanelLayout.setHorizontalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addGap(183, 183, 183)
                .addComponent(HomePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(564, Short.MAX_VALUE))
        );
        MainPanelLayout.setVerticalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addGap(171, 171, 171)
                .addComponent(HomePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void ModificarUsuarioFrameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarUsuarioFrameActionPerformed
        cardLayout.show(MainPanel, "ModUsuarioPanel");
    }//GEN-LAST:event_ModificarUsuarioFrameActionPerformed

    private void EliminarUsuarioFrameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarUsuarioFrameActionPerformed
        cardLayout.show(MainPanel, "EliminarUsuarioPanel");
    }//GEN-LAST:event_EliminarUsuarioFrameActionPerformed

    private void CrearUsuarioFrameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CrearUsuarioFrameActionPerformed
        cardLayout.show(MainPanel, "CrearUsuarioPanel");
    }//GEN-LAST:event_CrearUsuarioFrameActionPerformed

    private void DataFrameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DataFrameActionPerformed
        cardLayout.show(MainPanel, "DataManagerPanel");
    }//GEN-LAST:event_DataFrameActionPerformed

    private void CerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CerrarSesionActionPerformed
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_CerrarSesionActionPerformed

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
            java.util.logging.Logger.getLogger(AdminHomeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminHomeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminHomeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminHomeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminHomeScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CerrarSesion;
    private javax.swing.JButton CrearUsuarioFrame;
    private javax.swing.JButton DataFrame;
    private javax.swing.JButton EliminarUsuarioFrame;
    private javax.swing.JPanel HomePanel;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JButton ModificarUsuarioFrame;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
