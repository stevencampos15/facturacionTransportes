
package com.app.formularios;

import com.app.dao.TipoUsuarioDAO;
import com.app.modelo.TipoUsuario;
import java.awt.Color;
import javax.swing.JOptionPane;

/**
 *
 * @author Steven
 */
public class FrmTipoUsuario extends javax.swing.JInternalFrame {

    //Atributos de clase que se usara para modificar o eliminar datos
    public static String idTipoUsuario = "";
    public static String nombreTU;
    public static String descripcionTU;
    private TipoUsuario tipoUs;
    private TipoUsuarioDAO tudao;
    
    public FrmTipoUsuario() {
        initComponents();
        try {
            tudao = new TipoUsuarioDAO();
        } catch (Exception e) {
            e.toString();
        }
    }

    //Con este metodo se llenaran los campos al escoger un dato en la lista
    private void llenarCampos() {
        if (!idTipoUsuario.equals("")) {
            this.txtIdTipoUsuario.setText(idTipoUsuario);
            this.txtNombreTipoUsuario.setText(nombreTU);
            this.txtDescripcionTU.setText(descripcionTU);
        }
    }
    
    //Se utilizara este metodo para limpiar los campos.
    private void limpiarCampos(){
        this.txtIdTipoUsuario.setText("");
        this.txtNombreTipoUsuario.setText("");
        this.txtDescripcionTU.setText("");
    }

    //Con este metodo se gestionara la BD, insertar modificar o eliminar datos.
    private void gestion(String op) {
        int id = Integer.parseInt(this.txtIdTipoUsuario.getText());
        String nombre = this.txtNombreTipoUsuario.getText();
        String descripcion = this.txtDescripcionTU.getText();
        tipoUs = new TipoUsuario(id, nombre, descripcion);
        switch (op) {
            case "agregar":
                try {
                    tudao.insertar(tipoUs);
                    JOptionPane.showMessageDialog(this, "El Tipo de Usuario se ha agregado exitosamente.");
                    limpiarCampos();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error " + e.toString());
                }
                break;

            case "modificar":
                try {
                    int opp = JOptionPane.showConfirmDialog(this, "¿Seguro que desea modificar el tipo de usuario?",
                            "Modificar", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (opp == 0) {
                        tudao.modificar(tipoUs);
                        JOptionPane.showMessageDialog(this, "El Tipo de Usuario se ha modificado exitosamente");
                        limpiarCampos();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error " + e.toString());
                }
                break;

            case "eliminar":
                try {
                    int opp = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar el Tipo de Usuario?",
                            "Eliminar", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (opp == 0) {
                        String msj = tudao.eliminar(tipoUs);
                        JOptionPane.showMessageDialog(this, msj);
                        limpiarCampos();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "No se puede eliminar, "
                            + "necesita eliminar antes los usuarios asociados", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;

            default:
                JOptionPane.showMessageDialog(null, "Hay un error, contactar con el administrador", "Error", JOptionPane.ERROR_MESSAGE);
                break;
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

        jLabel1 = new javax.swing.JLabel();
        txtIdTipoUsuario = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNombreTipoUsuario = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnMostrar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcionTU = new javax.swing.JTextArea();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        jLabel1.setText("Tipo de Usuario");

        txtIdTipoUsuario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtIdTipoUsuario.setToolTipText("ID");
        txtIdTipoUsuario.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel2.setText("ID ");

        jLabel3.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel3.setText("Nombre");

        txtNombreTipoUsuario.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        btnAgregar.setBackground(new java.awt.Color(11, 104, 204));
        btnAgregar.setFont(new java.awt.Font("Gadugi", 0, 18)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnMostrar.setBackground(new java.awt.Color(11, 104, 204));
        btnMostrar.setFont(new java.awt.Font("Gadugi", 0, 18)); // NOI18N
        btnMostrar.setForeground(new java.awt.Color(255, 255, 255));
        btnMostrar.setText("Mostrar Lista");
        btnMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel4.setText("Descripcion");

        txtDescripcionTU.setColumns(20);
        txtDescripcionTU.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtDescripcionTU.setLineWrap(true);
        txtDescripcionTU.setRows(5);
        jScrollPane1.setViewportView(txtDescripcionTU);

        btnModificar.setBackground(new java.awt.Color(11, 104, 204));
        btnModificar.setFont(new java.awt.Font("Gadugi", 0, 18)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(255, 255, 255));
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(11, 104, 204));
        btnEliminar.setFont(new java.awt.Font("Gadugi", 0, 18)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(txtIdTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(txtNombreTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                                .addComponent(btnMostrar))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAgregar)
                                .addGap(18, 18, 18)
                                .addComponent(btnModificar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEliminar)
                                .addGap(38, 38, 38))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 12, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMostrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar))
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarActionPerformed
        // TODO add your handling code here:
        FrmTUDatos tuDatos = new FrmTUDatos();
        FrmPrincipal.escritorioPane.add(tuDatos);
        tuDatos.setLocation(this.getX() + 10, this.getY() + 10);
        tuDatos.toFront();
        tuDatos.setVisible(true);

        //Diseno, se agrega un color customizado para el fondo.
        Color nColor = new Color(248, 249, 252);
        tuDatos.getContentPane().setBackground(nColor);
    }//GEN-LAST:event_btnMostrarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // Se agrega el tipo de usuario
        gestion("agregar");
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_formFocusGained

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentResized

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        // TODO add your handling code here:
        llenarCampos();
    }//GEN-LAST:event_formInternalFrameActivated

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        //Modifica
        gestion("modificar");

    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // Elimina
        gestion("eliminar");
    }//GEN-LAST:event_btnEliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnMostrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea txtDescripcionTU;
    private javax.swing.JFormattedTextField txtIdTipoUsuario;
    private javax.swing.JTextField txtNombreTipoUsuario;
    // End of variables declaration//GEN-END:variables
}
