package com.app.formularios;

import com.app.dao.ClienteDAO;
import com.app.modelo.Cliente;
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
public class FrmListaClientes extends javax.swing.JInternalFrame {

    public FrmListaClientes() {
        initComponents();
        llenarTabla();
    }

    //Metodo para llenar la tabla
    public void llenarTabla() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Nit");
        modelo.addColumn("Nombre");
        modelo.addColumn("Telefono");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Registro");
        modelo.addColumn("Direccion");
        modelo.addColumn("Departamento");
        modelo.addColumn("Giro");

        ClienteDAO cliDao = new ClienteDAO();
        List<Cliente> lista = (List<Cliente>) cliDao.consultar();
        Object[] fila = new Object[9];
        for (int i = 0; i < lista.size(); i++) {
            fila[0] = lista.get(i).getIdCliente();
            fila[1] = lista.get(i).getNit();
            fila[2] = lista.get(i).getNombreCliente();
            fila[3] = lista.get(i).getTelefonoCliente();
            fila[4] = lista.get(i).getDescripcionCliente();
            fila[5] = lista.get(i).getRegistro();
            fila[6] = lista.get(i).getDireccionCliente();
            fila[7] = lista.get(i).getDepartamento().getIdDepartamento();
            fila[8] = lista.get(i).getGiro().getIdGiro();
            modelo.addRow(fila);
        }

        tblLista.setModel(modelo);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        txtBuscar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLista = new javax.swing.JTable();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        jLabel1.setText("Lista Clientes");

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel2.setText("Buscar:");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(35, 35, 35)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(253, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        // Metodo para filtrar tabla
        DefaultTableModel modelo = (DefaultTableModel) tblLista.getModel();
        String buscar = txtBuscar.getText();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(modelo);
        tblLista.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(buscar));
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void tblListaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListaMouseClicked

        int i = tblLista.getSelectedRow();
        TableModel model = tblLista.getModel();
        String[] opciones = {"Si", "No", "Cancelar"};
        int opp = JOptionPane.showOptionDialog(this, "¿Seleccionar Cliente: " + model.getValueAt(tblLista.convertRowIndexToModel(i), 2).toString() + " ?", "Seleccionar", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (opp == 0) {
            // Al momento de hacer clic sobre una fila se trasladara la informacion
            FrmClientes frmCliente = new FrmClientes();
            String id = model.getValueAt(tblLista.convertRowIndexToModel(i), 0).toString();
            String nit = model.getValueAt(tblLista.convertRowIndexToModel(i), 1).toString();
            String nombre = model.getValueAt(tblLista.convertRowIndexToModel(i), 2).toString();
            String telefono = model.getValueAt(tblLista.convertRowIndexToModel(i), 3).toString();
            String descripcion = model.getValueAt(tblLista.convertRowIndexToModel(i), 4).toString();
            String registro = model.getValueAt(tblLista.convertRowIndexToModel(i), 5).toString();
            String direccion = model.getValueAt(tblLista.convertRowIndexToModel(i), 6).toString();
            String idDep = model.getValueAt(tblLista.convertRowIndexToModel(i), 7).toString();
            String idGiro = model.getValueAt(tblLista.convertRowIndexToModel(i), 8).toString();

            //Los regresamos al frm anterior
            frmCliente.idCC = id;
            frmCliente.nitCC = nit;
            frmCliente.nombreCC = nombre;
            frmCliente.telefonoCC = telefono;
            frmCliente.descripcionCC = descripcion;
            frmCliente.registroCC = registro;
            frmCliente.direccionCC = direccion;
            frmCliente.depCC = idDep;
            frmCliente.giroCC = idGiro;

            //Se cierra el formulario
            this.dispose();
        }

    }//GEN-LAST:event_tblListaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblLista;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
