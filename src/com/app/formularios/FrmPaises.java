/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.formularios;

import com.app.dao.PaisDAO;
import com.app.modelo.Pais;
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
public class FrmPaises extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmPaises
     */
    private PaisDAO pdao;

    public FrmPaises() {
        initComponents();
        try {
            pdao = new PaisDAO();
            llenarTabla();
            siguienteId();
        } catch (Exception e) {
            e.toString();
        }
    }

    //Metodo para validar los datos
    private boolean validacion() {
        boolean validado = false;
        if (this.txtIdPais.getText().equals("") || this.txtNombrePais.getText().equals("")) {
            JOptionPane.showMessageDialog(this,
                    "No se han ingresado algunos datos necesarios",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            validado = true;
        }

        return validado;
    }

    //Metodo para buscar el siguiente ID
    private void siguienteId() {
        String id = String.valueOf(pdao.siguienteId());
        this.txtIdPais.setText(id);
    }

    //Metodo para llenar la tabla
    public void llenarTabla() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre Pais");
        PaisDAO pDao = new PaisDAO();
        List<Pais> lista = (List<Pais>) pDao.consultar();
        Object[] fila = new Object[2];
        for (int i = 0; i < lista.size(); i++) {
            fila[0] = lista.get(i).getIdPais();
            fila[1] = lista.get(i).getNombrePais();
            modelo.addRow(fila);
        }
        tblLista.setModel(modelo);
    }

    //Se utilizara este metodo para limpiar los campos.
    private void limpiarCampos() {
        this.txtBuscar.setText("");
        this.txtNombrePais.setText("");
        this.txtIdPais.setText("");
        siguienteId();
    }

    //Con este metodo se gestionara la BD, insertar modificar o eliminar datos.
    private void gestion(String op) {
        String msj = null;
        int id = Integer.parseInt(this.txtIdPais.getText());
        String nombrePais = this.txtNombrePais.getText();
        Pais pais = new Pais(id, nombrePais);
        switch (op) {
            case "agregar":
                try {
                    msj = pdao.insertar(pais);
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
                    int opp = JOptionPane.showOptionDialog(this, "¿Seguro que desea modificar el pais?","Modificar", JOptionPane.YES_NO_CANCEL_OPTION, 
                            JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[2]);
                    if (opp == 0) {
                        msj = pdao.modificar(pais);
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
                    int opp = JOptionPane.showOptionDialog(this, "¿Seguro que desea eliminar el pais?","Eliminar", JOptionPane.YES_NO_CANCEL_OPTION, 
                            JOptionPane.WARNING_MESSAGE, null, opciones, opciones[2]);
                    if (opp == 0) {
                        msj = pdao.eliminar(pais);
                        JOptionPane.showMessageDialog(this, msj);
                        limpiarCampos();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "No se puede eliminar, "
                            + "necesita eliminar antes los paises asociados", "Error", JOptionPane.ERROR_MESSAGE);
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

        jLabel1 = new javax.swing.JLabel();
        btnModificar = new javax.swing.JButton();
        txtIdPais = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLista = new javax.swing.JTable();
        btnEliminar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNombrePais = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnHelpPaises = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        jLabel1.setText("Países");

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

        txtIdPais.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtIdPais.setToolTipText("ID");
        txtIdPais.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

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

        jLabel2.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel2.setText("ID ");

        txtBuscar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel3.setText("Nombre");

        jLabel6.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel6.setText("Buscar");

        txtNombrePais.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

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

        btnHelpPaises.setBackground(new java.awt.Color(11, 104, 240));
        btnHelpPaises.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/img/help.png"))); // NOI18N
        btnHelpPaises.setToolTipText("Almacenar los países de donde provienen los clientes con los que se trabajan, aunque actualmente se trabaja nacionalmente no se restringe la posibilidad crecer y realizar viajes internacionales");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnHelpPaises, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(txtIdPais, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(txtNombrePais, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(42, 42, 42)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnModificar)
                                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addComponent(btnHelpPaises, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 21, Short.MAX_VALUE)
                        .addComponent(btnAgregar)
                        .addGap(18, 18, 18)
                        .addComponent(btnModificar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)
                        .addGap(27, 27, 27)
                        .addComponent(btnLimpiar)
                        .addGap(90, 90, 90))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNombrePais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtIdPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        //Modifica
        if (validacion()) {
            gestion("modificar");
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void tblListaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListaMouseClicked
        int i = tblLista.getSelectedRow();
        TableModel model = tblLista.getModel();
        String[] opciones = {"Si", "No", "Cancelar"};
        int opp = JOptionPane.showOptionDialog(this, "¿Seleccionar Pais: " + model.getValueAt(i, 1).toString() + " ?", "Seleccionar", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (opp == 0) {
            // Al momento de hacer clic sobre una fila se captura la informacion
            String id = model.getValueAt(i, 0).toString();
            String nombre = model.getValueAt(i, 1).toString();

            //Se colocan los datos en los campos
            this.txtIdPais.setText(id);
            this.txtNombrePais.setText(nombre);
        }
    }//GEN-LAST:event_tblListaMouseClicked

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // Elimina
        if (validacion()) {
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

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // Se agrega el pais
        if (validacion()) {
            gestion("agregar");
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // Limpiamos los campos
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnHelpPaises;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblLista;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JFormattedTextField txtIdPais;
    private javax.swing.JTextField txtNombrePais;
    // End of variables declaration//GEN-END:variables
}
