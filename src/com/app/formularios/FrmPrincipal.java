/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.formularios;

import java.awt.Color;

/**
 *
 * @author Steven
 */
public class FrmPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form FrmPrincipal
     */
    public FrmPrincipal() {
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

        desktopPane = new javax.swing.JDesktopPane();
        escritorioPane = new javax.swing.JDesktopPane();
        menuBar = new javax.swing.JMenuBar();
        menuAdmin = new javax.swing.JMenu();
        tuItem = new javax.swing.JMenuItem();
        usuariosItem = new javax.swing.JMenuItem();
        paisesItem = new javax.swing.JMenuItem();
        departamentosItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        menuGestion = new javax.swing.JMenu();
        girosItem = new javax.swing.JMenuItem();
        condicionesItem = new javax.swing.JMenuItem();
        productosItem = new javax.swing.JMenuItem();
        clientesItem = new javax.swing.JMenuItem();
        facturasGestionItem = new javax.swing.JMenuItem();
        menuOperacion = new javax.swing.JMenu();
        facturaItem = new javax.swing.JMenuItem();
        imprimirFacturaItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout escritorioPaneLayout = new javax.swing.GroupLayout(escritorioPane);
        escritorioPane.setLayout(escritorioPaneLayout);
        escritorioPaneLayout.setHorizontalGroup(
            escritorioPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 890, Short.MAX_VALUE)
        );
        escritorioPaneLayout.setVerticalGroup(
            escritorioPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        desktopPane.add(escritorioPane);
        escritorioPane.setBounds(0, 0, 890, 640);

        menuAdmin.setMnemonic('f');
        menuAdmin.setText("Administracion");
        menuAdmin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        tuItem.setMnemonic('o');
        tuItem.setText("Tipo de Usuarios");
        tuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tuItemActionPerformed(evt);
            }
        });
        menuAdmin.add(tuItem);

        usuariosItem.setMnemonic('s');
        usuariosItem.setText("Usuarios");
        usuariosItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usuariosItemActionPerformed(evt);
            }
        });
        menuAdmin.add(usuariosItem);

        paisesItem.setMnemonic('a');
        paisesItem.setText("Paises");
        paisesItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paisesItemActionPerformed(evt);
            }
        });
        menuAdmin.add(paisesItem);

        departamentosItem.setMnemonic('a');
        departamentosItem.setText("Departamentos");
        departamentosItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                departamentosItemActionPerformed(evt);
            }
        });
        menuAdmin.add(departamentosItem);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Salir");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        menuAdmin.add(exitMenuItem);

        menuBar.add(menuAdmin);

        menuGestion.setMnemonic('e');
        menuGestion.setText("Gestion");
        menuGestion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        girosItem.setMnemonic('t');
        girosItem.setText("Giros");
        girosItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                girosItemActionPerformed(evt);
            }
        });
        menuGestion.add(girosItem);

        condicionesItem.setMnemonic('y');
        condicionesItem.setText("Condiciones de Pago");
        condicionesItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                condicionesItemActionPerformed(evt);
            }
        });
        menuGestion.add(condicionesItem);

        productosItem.setMnemonic('p');
        productosItem.setText("Productos");
        productosItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productosItemActionPerformed(evt);
            }
        });
        menuGestion.add(productosItem);

        clientesItem.setMnemonic('d');
        clientesItem.setText("Clientes");
        clientesItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientesItemActionPerformed(evt);
            }
        });
        menuGestion.add(clientesItem);

        facturasGestionItem.setMnemonic('d');
        facturasGestionItem.setText("Facturas");
        facturasGestionItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facturasGestionItemActionPerformed(evt);
            }
        });
        menuGestion.add(facturasGestionItem);

        menuBar.add(menuGestion);

        menuOperacion.setMnemonic('h');
        menuOperacion.setText("Operaciones");
        menuOperacion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        facturaItem.setMnemonic('c');
        facturaItem.setText("Generar Factura");
        facturaItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facturaItemActionPerformed(evt);
            }
        });
        menuOperacion.add(facturaItem);

        imprimirFacturaItem.setMnemonic('a');
        imprimirFacturaItem.setText("Imprimir Factura");
        menuOperacion.add(imprimirFacturaItem);

        menuBar.add(menuOperacion);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 891, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void tuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tuItemActionPerformed
        // Abrimos el formulario
        FrmTipoUsuario frmTU = new FrmTipoUsuario();
        escritorioPane.add(frmTU);
        frmTU.show();

        //Diseno, se agrega un color customizado para el fondo.
        Color nColor = new Color(248, 249, 252);
        frmTU.getContentPane().setBackground(nColor);
    }//GEN-LAST:event_tuItemActionPerformed

    private void usuariosItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usuariosItemActionPerformed
        // Abrimos el formulario
        FrmUsuarios frmUsuarios = new FrmUsuarios();
        escritorioPane.add(frmUsuarios);
        frmUsuarios.show();

        //Diseno, se agrega un color customizado para el fondo.
        Color nColor = new Color(248, 249, 252);
        frmUsuarios.getContentPane().setBackground(nColor);
    }//GEN-LAST:event_usuariosItemActionPerformed

    private void paisesItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paisesItemActionPerformed
        // Abrimos el formulario
        FrmPaises frmPaises = new FrmPaises();
        escritorioPane.add(frmPaises);
        frmPaises.show();

        //Diseno, se agrega un color customizado para el fondo.
        Color nColor = new Color(248, 249, 252);
        frmPaises.getContentPane().setBackground(nColor);
    }//GEN-LAST:event_paisesItemActionPerformed

    private void departamentosItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_departamentosItemActionPerformed
        // Abrimos el formulario
        FrmDepartamentos frmDepartamentos = new FrmDepartamentos();
        escritorioPane.add(frmDepartamentos);
        frmDepartamentos.show();

        //Diseno, se agrega un color customizado para el fondo.
        Color nColor = new Color(248, 249, 252);
        frmDepartamentos.getContentPane().setBackground(nColor);
    }//GEN-LAST:event_departamentosItemActionPerformed

    private void girosItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_girosItemActionPerformed
        // Abrimos el formulario
        FrmGiros frmGiros = new FrmGiros();
        escritorioPane.add(frmGiros);
        frmGiros.show();

        //Diseno, se agrega un color customizado para el fondo.
        Color nColor = new Color(248, 249, 252);
        frmGiros.getContentPane().setBackground(nColor);
    }//GEN-LAST:event_girosItemActionPerformed

    private void condicionesItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_condicionesItemActionPerformed
        // Abrimos el formulario
        FrmCondicionesP frmCondicionesP = new FrmCondicionesP();
        escritorioPane.add(frmCondicionesP);
        frmCondicionesP.show();

        //Diseno, se agrega un color customizado para el fondo.
        Color nColor = new Color(248, 249, 252);
        frmCondicionesP.getContentPane().setBackground(nColor);
    }//GEN-LAST:event_condicionesItemActionPerformed

    private void productosItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productosItemActionPerformed
        // Abrimos el formulario
        FrmProductos frmProductos = new FrmProductos();
        escritorioPane.add(frmProductos);
        frmProductos.show();

        //Diseno, se agrega un color customizado para el fondo.
        Color nColor = new Color(248, 249, 252);
        frmProductos.getContentPane().setBackground(nColor);
    }//GEN-LAST:event_productosItemActionPerformed

    private void clientesItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientesItemActionPerformed
        // Abrimos el formulario
        FrmClientes frmClientes = new FrmClientes();
        escritorioPane.add(frmClientes);
        frmClientes.show();

        //Diseno, se agrega un color customizado para el fondo.
        Color nColor = new Color(248, 249, 252);
        frmClientes.getContentPane().setBackground(nColor);
    }//GEN-LAST:event_clientesItemActionPerformed

    private void facturaItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facturaItemActionPerformed
        // Abrimos el formulario
        FrmFactura frmFactura = new FrmFactura();
        escritorioPane.add(frmFactura);
        frmFactura.show();

        //Diseno, se agrega un color customizado para el fondo.
        Color nColor = new Color(248, 249, 252);
        frmFactura.getContentPane().setBackground(nColor);
    }//GEN-LAST:event_facturaItemActionPerformed

    private void facturasGestionItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facturasGestionItemActionPerformed
        // Abrimos el formulario
        FrmGestionFacturas frmGestionFacturas = new FrmGestionFacturas();
        escritorioPane.add(frmGestionFacturas);
        frmGestionFacturas.show();

        //Diseno, se agrega un color customizado para el fondo.
        Color nColor = new Color(248, 249, 252);
        frmGestionFacturas.getContentPane().setBackground(nColor);
    }//GEN-LAST:event_facturasGestionItemActionPerformed

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
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem clientesItem;
    private javax.swing.JMenuItem condicionesItem;
    private javax.swing.JMenuItem departamentosItem;
    private javax.swing.JDesktopPane desktopPane;
    public static javax.swing.JDesktopPane escritorioPane;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenuItem facturaItem;
    private javax.swing.JMenuItem facturasGestionItem;
    private javax.swing.JMenuItem girosItem;
    private javax.swing.JMenuItem imprimirFacturaItem;
    private javax.swing.JMenu menuAdmin;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuGestion;
    private javax.swing.JMenu menuOperacion;
    private javax.swing.JMenuItem paisesItem;
    private javax.swing.JMenuItem productosItem;
    private javax.swing.JMenuItem tuItem;
    private javax.swing.JMenuItem usuariosItem;
    // End of variables declaration//GEN-END:variables

}
