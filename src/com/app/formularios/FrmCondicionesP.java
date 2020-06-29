package com.app.formularios;

import com.app.dao.CondicionPagoDAO;
import com.app.modelo.CondicionPago;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Steven
 */
public class FrmCondicionesP extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmCondicionesP
     */
    private CondicionPagoDAO cpdao;

    public FrmCondicionesP() {
        initComponents();
        try {
            cpdao = new CondicionPagoDAO();
            llenarTabla();
            siguienteId();
        } catch (Exception e) {
            e.toString();
        }
    }

    //Metodo para validar los datos
    private boolean validacion() {
        boolean validado = false;
        if (this.txtIdCP.getText().equals("") || this.txtNombreCP.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "No se han ingresado algunos datos necesarios", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            validado = true;
        }

        return validado;
    }

    //Metodo para buscar el siguiente ID
    private void siguienteId() {
        String id = String.valueOf(cpdao.siguienteId());
        this.txtIdCP.setText(id);
    }

    //Metodo para llenar la tabla
    public void llenarTabla() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Descripcion");
        CondicionPagoDAO cpDao = new CondicionPagoDAO();
        List<CondicionPago> lista = (List<CondicionPago>) cpDao.consultar();
        Object[] fila = new Object[3];
        for (int i = 0; i < lista.size(); i++) {
            fila[0] = lista.get(i).getIdCP();
            fila[1] = lista.get(i).getNombreTipoCP();
            fila[2] = lista.get(i).getDescripcionCP();
            modelo.addRow(fila);
        }
        tblLista.setModel(modelo);
    }

    //Se utilizara este metodo para limpiar los campos.
    private void limpiarCampos() {
        this.txtBuscar.setText("");
        this.txtNombreCP.setText("");
        this.txtDescripcionCP.setText("");
        this.txtIdCP.setText("");
        siguienteId();
    }

    //Con este metodo se gestionara la BD, insertar modificar o eliminar datos.
    private void gestion(String op) {
        String msj = null;
        int id = Integer.parseInt(this.txtIdCP.getText());
        String nombre = this.txtNombreCP.getText();
        String descripcion = this.txtDescripcionCP.getText();
        CondicionPago cp = new CondicionPago(id, nombre, descripcion);
        switch (op) {
            case "agregar":
                try {
                    msj = cpdao.insertar(cp);
                    JOptionPane.showMessageDialog(this, msj);
                    limpiarCampos();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error " + e.toString());
                }
                llenarTabla();
                break;

            case "modificar":
                try {
                    String[] opciones = { "Si", "No", "Cancelar" };
                    int opp = JOptionPane.showOptionDialog(this, "¿Seguro que desea modificar la condición de pago?","Modificar", JOptionPane.YES_NO_CANCEL_OPTION, 
                            JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[2]);
                    if (opp == 0) {
                        msj = cpdao.modificar(cp);
                        JOptionPane.showMessageDialog(this, msj);
                        limpiarCampos();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error " + e.toString());
                }
                llenarTabla();
                break;

            case "eliminar":
                try {
                    String[] opciones = { "Si", "No", "Cancelar" };
                    int opp = JOptionPane.showOptionDialog(this, "¿Seguro que desea eliminar la condición de pago?","Eliminar", JOptionPane.YES_NO_CANCEL_OPTION, 
                            JOptionPane.WARNING_MESSAGE, null, opciones, opciones[2]);
                    if (opp == 0) {
                        msj = cpdao.eliminar(cp);
                        JOptionPane.showMessageDialog(this, msj);
                        limpiarCampos();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "No se puede eliminar, "
                            + "necesita eliminar antes las condiciones asociadas", "Error", JOptionPane.ERROR_MESSAGE);
                }
                llenarTabla();
                break;

            default:
                JOptionPane.showMessageDialog(this, "Hay un error, contactar con el administrador", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
        siguienteId();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtIdCP = new javax.swing.JFormattedTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescripcionCP = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLista = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNombreCP = new javax.swing.JTextField();
        txtBuscar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnHelpCond = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);

        btnAgregar.setBackground(new java.awt.Color(11, 104, 204));
        btnAgregar.setFont(new java.awt.Font("Gadugi", 0, 18)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/img/add.png"))); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnModificar.setBackground(new java.awt.Color(11, 104, 204));
        btnModificar.setFont(new java.awt.Font("Gadugi", 0, 18)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(255, 255, 255));
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/img/update.png"))); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnLimpiar.setBackground(new java.awt.Color(11, 104, 204));
        btnLimpiar.setFont(new java.awt.Font("Gadugi", 0, 18)); // NOI18N
        btnLimpiar.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/img/clear2.png"))); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel4.setText("Descripción");

        txtIdCP.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtIdCP.setToolTipText("ID");
        txtIdCP.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtDescripcionCP.setColumns(20);
        txtDescripcionCP.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtDescripcionCP.setLineWrap(true);
        txtDescripcionCP.setRows(5);
        txtDescripcionCP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDescripcionCPKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(txtDescripcionCP);

        tblLista.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblLista.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblLista.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblLista);

        jLabel3.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel3.setText("Nombre");

        btnEliminar.setBackground(new java.awt.Color(11, 104, 204));
        btnEliminar.setFont(new java.awt.Font("Gadugi", 0, 18)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/img/delete.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel6.setText("Buscar");

        jLabel2.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel2.setText("ID ");

        txtNombreCP.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtNombreCP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreCPKeyPressed(evt);
            }
        });

        txtBuscar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        jLabel1.setText("Condiciones de Pago");

        btnHelpCond.setBackground(new java.awt.Color(11, 104, 240));
        btnHelpCond.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/img/help.png"))); // NOI18N
        btnHelpCond.setToolTipText("Almacena los tipos de condiciones de pago en los que el empresario puede vender, por ejemplo: Pago al contado, Pago anticipado, Pago aplazado, etc.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txtIdCP, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(74, 74, 74)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtNombreCP, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 38, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnHelpCond, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnHelpCond, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIdCP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombreCP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAgregar)
                        .addGap(18, 18, 18)
                        .addComponent(btnModificar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)
                        .addGap(18, 18, 18)
                        .addComponent(btnLimpiar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // Se agrega el tipo de usuario
        if(validacion()){
            gestion("agregar");
        }
        
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        //Modifica
        if(validacion()){
            gestion("modificar");
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // Limpiamos los campos
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void tblListaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListaMouseClicked
        int i = tblLista.getSelectedRow();
        TableModel model = tblLista.getModel();
        String[] opciones = { "Si", "No", "Cancelar" };
                    int opp = JOptionPane.showOptionDialog(this, "¿Seleccionar Condicion: " + model.getValueAt(i, 1).toString() + " ?","Seleccionar", JOptionPane.YES_NO_CANCEL_OPTION, 
                            JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (opp == 0) {
            // Al momento de hacer clic sobre una fila se captura la informacion
            String id = model.getValueAt(i, 0).toString();
            String nombre = model.getValueAt(i, 1).toString();
            String descripcion = model.getValueAt(i, 2).toString();

            //Se colocan los datos en los campos
            this.txtIdCP.setText(id);
            this.txtNombreCP.setText(nombre);
            this.txtDescripcionCP.setText(descripcion);
        }
    }//GEN-LAST:event_tblListaMouseClicked

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // Eliminar
        if(validacion()){
            gestion("eliminar");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        // Metodo para filtrar tabla
        DefaultTableModel modelo = (DefaultTableModel) tblLista.getModel();
        String buscar = txtBuscar.getText();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(modelo);
        tblLista.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(buscar));
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void txtDescripcionCPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionCPKeyPressed
        // Validacion para que no se ingrese mas caracteres de lo que se permite
        String texto = this.txtDescripcionCP.getText();
        int tamano = texto.length();
        char c = evt.getKeyChar();
        if (tamano < 800) {
            this.txtDescripcionCP.setEditable(true);
        } else {
            this.txtDescripcionCP.setEditable(false);
            if (evt.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
                this.txtDescripcionCP.setEditable(true);
            }
        }
    }//GEN-LAST:event_txtDescripcionCPKeyPressed

    private void txtNombreCPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreCPKeyPressed
        // Validacion para que no se ingrese mas caracteres de lo que se permite
        String texto = this.txtNombreCP.getText();
        int tamano = texto.length();
        char c = evt.getKeyChar();
        if (tamano < 100) {
            this.txtNombreCP.setEditable(true);
        } else {
            this.txtNombreCP.setEditable(false);
            if (evt.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
                this.txtNombreCP.setEditable(true);
            }
        }
    }//GEN-LAST:event_txtNombreCPKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnHelpCond;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblLista;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextArea txtDescripcionCP;
    private javax.swing.JFormattedTextField txtIdCP;
    private javax.swing.JTextField txtNombreCP;
    // End of variables declaration//GEN-END:variables
}
