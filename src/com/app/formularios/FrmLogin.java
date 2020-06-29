/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.formularios;

import com.app.dao.UsuarioDAO;
import com.app.modelo.Usuario;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.image.ImageObserver.FRAMEBITS;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author ALEX
 */
public class FrmLogin extends javax.swing.JFrame {

    /**
     * Creates new form FrmLogin
     */
    private int cambioColor = 0;
    private int xObj = 0;
    private int yObj = 0;
    private int moveObj = 0;
    UsuarioDAO udao;

    public FrmLogin() {
        udao = new UsuarioDAO();
        initComponents();
        setLogin();

    }

    //Metodo para una animacion
    private void animate(JComponent component, Point newPoint, int frames, int interval) {
        Rectangle compBounds = component.getBounds();

        Point oldPoint = new Point(compBounds.x, compBounds.y),
                animFrame = new Point((newPoint.x - oldPoint.x) / frames,
                        (newPoint.y - oldPoint.y) / frames);

        new Timer(interval, new ActionListener() {
            int currentFrame = 0;

            public void actionPerformed(ActionEvent e) {
                component.setBounds(oldPoint.x + (animFrame.x * currentFrame),
                        oldPoint.y + (animFrame.y * currentFrame),
                        compBounds.width,
                        compBounds.height);

                if (currentFrame != frames) {
                    currentFrame++;
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        }).start();
    }

    private void animate2(JComponent component, Point newPoint, int frames, int interval) {
        Rectangle compBounds = component.getBounds();

        Point oldPoint = new Point(compBounds.x, compBounds.y),
                animFrame = new Point((newPoint.x - oldPoint.x) / frames,
                        (newPoint.y - oldPoint.y) / frames);

        new Timer(interval, new ActionListener() {
            int currentFrame = 0;

            public void actionPerformed(ActionEvent e) {
                component.setBounds(oldPoint.x + (animFrame.x * currentFrame),
                        oldPoint.y + (animFrame.y * currentFrame),
                        compBounds.width,
                        compBounds.height);

                if (currentFrame != frames) {
                    currentFrame++;
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        }).start();
    }

    //Metodo para login correcto
    private void loginCorrecto(Usuario us) {
        String user = this.txtUsuario.getText();
        String usuario = user.substring(0, 1).toUpperCase() + user.substring(1);
        Color nColor = new Color(248, 249, 252);
        Color verde = new Color(102, 211, 102);
        getContentPane().setBackground(verde);
        this.lblTitulo.setForeground(Color.WHITE);
        this.lblTitulo1.setForeground(Color.WHITE);
        this.lblUsuario.setForeground(Color.WHITE);
        this.lblContraseña.setForeground(Color.WHITE);
        this.btnAceptar.setBackground(nColor);
        this.btnAceptar.setForeground(Color.BLACK);
        this.btnSalir.setBackground(nColor);
        this.btnSalir.setForeground(Color.BLACK);
        this.jLabel2.setIcon(new ImageIcon(getClass().getResource("/com/app/img/Inicio150.png")));
        JOptionPane.showMessageDialog(this, "Bienvenido " + usuario, "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
        FrmPrincipal frmTU = new FrmPrincipal(us);
        frmTU.show();
        this.dispose();
    }

    //Metodo en caso de mal login
    private void loginIncorrecto() {
        Color nColor = new Color(248, 249, 252);
        Color rojo = new Color(211, 102, 102);
        getContentPane().setBackground(rojo);
        this.lblTitulo.setForeground(Color.WHITE);
        this.lblTitulo1.setForeground(Color.WHITE);
        this.lblUsuario.setForeground(Color.WHITE);
        this.lblContraseña.setForeground(Color.WHITE);
        this.btnAceptar.setBackground(nColor);
        this.btnAceptar.setForeground(Color.BLACK);
        this.btnSalir.setBackground(nColor);
        this.btnSalir.setForeground(Color.BLACK);
        this.jLabel2.setIcon(new ImageIcon(getClass().getResource("/com/app/img/Inicio150.png")));
        JOptionPane.showMessageDialog(this, "Verifique las credenciales y vuelva a intentarlo", "Credenciales Incorrectas", JOptionPane.ERROR_MESSAGE);
    }

    //Metodo para verificar el login
    private void verificarLogin() {
        if (this.txtUsuario.getText().equals("") || this.txtContraseña.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Necesita llenar los campos de usuario y contraseña", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String usuario = this.txtUsuario.getText();
            String pwd = this.txtContraseña.getText();
            if (udao.login(usuario, pwd)!=null) {
                loginCorrecto(udao.login(usuario, pwd));
            } else {
                loginIncorrecto();
            }
        }

    }

    //Metodo para configurar el formulario
    private void setLogin() {
        Color nColor = new Color(248, 249, 252);
        getContentPane().setBackground(nColor);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.getRootPane().setDefaultButton(btnAceptar);
    }

    //Metodo para darle color al formulario
    private void color2() {
        if (cambioColor == 0) {
            Color nColor = new Color(248, 249, 252);
            Color azul = new Color(11, 104, 204);
            getContentPane().setBackground(azul);
            this.lblTitulo.setForeground(Color.WHITE);
            this.lblTitulo1.setForeground(Color.WHITE);
            this.lblUsuario.setForeground(Color.WHITE);
            this.lblContraseña.setForeground(Color.WHITE);
            this.btnAceptar.setBackground(nColor);
            this.btnAceptar.setForeground(Color.BLACK);
            this.btnSalir.setBackground(nColor);
            this.btnSalir.setForeground(Color.BLACK);
            this.jLabel2.setIcon(new ImageIcon(getClass().getResource("/com/app/img/Inicio150.png")));
            this.cambioColor = 1;
        } else {
            Color nColor = new Color(248, 249, 252);
            Color azul = new Color(11, 104, 204);
            getContentPane().setBackground(nColor);
            this.lblTitulo.setForeground(Color.BLACK);
            this.lblTitulo1.setForeground(Color.BLACK);
            this.lblUsuario.setForeground(Color.BLACK);
            this.lblContraseña.setForeground(Color.BLACK);
            this.btnAceptar.setBackground(azul);
            this.btnAceptar.setForeground(Color.WHITE);
            this.btnSalir.setBackground(azul);
            this.btnSalir.setForeground(Color.WHITE);
            this.jLabel2.setIcon(new ImageIcon(getClass().getResource("/com/app/img/Inicio250.png")));
            this.cambioColor = 0;
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

        btnSalir = new javax.swing.JButton();
        lblUsuario = new javax.swing.JLabel();
        lblContraseña = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JButton();
        txtUsuario = new javax.swing.JTextField();
        lblTitulo = new javax.swing.JLabel();
        txtContraseña = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblTitulo1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(11, 104, 204));
        btnSalir.setFont(new java.awt.Font("Gadugi", 0, 18)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        lblUsuario.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        lblUsuario.setText("Usuario");

        lblContraseña.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        lblContraseña.setText("Contraseña");

        btnAceptar.setBackground(new java.awt.Color(11, 104, 204));
        btnAceptar.setFont(new java.awt.Font("Gadugi", 0, 18)); // NOI18N
        btnAceptar.setForeground(new java.awt.Color(255, 255, 255));
        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        txtUsuario.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        lblTitulo.setFont(new java.awt.Font("Calibri", 1, 48)); // NOI18N
        lblTitulo.setText("Villalta");

        txtContraseña.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel1.setMaximumSize(new java.awt.Dimension(256, 256));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/img/Inicio250.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jLabel2.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                jLabel2ComponentMoved(evt);
            }
        });

        lblTitulo1.setFont(new java.awt.Font("Calibri", 1, 48)); // NOI18N
        lblTitulo1.setText("Transportes ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(lblTitulo))
                            .addComponent(lblTitulo1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(240, 240, 240)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(103, 103, 103)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(87, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUsuario)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblContraseña)
                    .addComponent(txtContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(186, 186, 186))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(lblTitulo))
                    .addComponent(lblTitulo1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblUsuario)
                .addGap(10, 10, 10)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(lblContraseña)
                .addGap(10, 10, 10)
                .addComponent(txtContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAceptar)
                    .addComponent(btnSalir))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        verificarLogin();
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // animacion

        xObj = jLabel2.getX();
        yObj = jLabel2.getY();
        Point point = new Point(xObj + 300, yObj);
        animate(jLabel2, point, FRAMEBITS, 1);
        moveObj = 1;
    }//GEN-LAST:event_jLabel2MouseClicked

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
        if(moveObj==1){
            moveObj=2;
        }
        color2();
        if (moveObj != 0) {
            Point point2 = new Point(xObj, yObj);
            animate(jLabel2, point2, FRAMEBITS, 1);
            moveObj = 0;
        }
    }//GEN-LAST:event_formMouseClicked

    private void jLabel2ComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jLabel2ComponentMoved
        // TODO add your handling code here:
        if (moveObj == 2) {
            Point p = new Point(xObj - 480, yObj);
            jLabel2.setLocation(p);
        }
    }//GEN-LAST:event_jLabel2ComponentMoved

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        // TODO add your handling code here:
        if(moveObj==1){
            moveObj=2;
        }
        
    }//GEN-LAST:event_formMouseMoved

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
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblContraseña;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTitulo1;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPasswordField txtContraseña;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
