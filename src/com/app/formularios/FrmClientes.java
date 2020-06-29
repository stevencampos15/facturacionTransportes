package com.app.formularios;

import com.app.dao.ClienteDAO;
import com.app.dao.DepartamentoDAO;
import com.app.dao.GiroDAO;
import static com.app.formularios.FrmPrincipal.escritorioPane;
import com.app.modelo.Cliente;
import com.app.modelo.Departamento;
import com.app.modelo.Giro;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Steven
 */
public class FrmClientes extends javax.swing.JInternalFrame {

    //Datos necesarios de la lista
    public static String idCC = "";
    public static String nitCC;
    public static String nombreCC;
    public static String telefonoCC;
    public static String descripcionCC;
    public static String registroCC;
    public static String direccionCC;
    public static String depCC;
    public static String giroCC;

    //Datos locales
    private DepartamentoDAO depdao;
    private GiroDAO gdao;
    private ClienteDAO clidao;
    private DefaultComboBoxModel mComboDep = new DefaultComboBoxModel();
    private DefaultComboBoxModel mComboGiro = new DefaultComboBoxModel();

    public FrmClientes() {
        initComponents();
        try {
            depdao = new DepartamentoDAO();
            llenarComboDepartamento();
            gdao = new GiroDAO();
            llenarComboGiro();
            clidao = new ClienteDAO();
            siguienteId();
        } catch (Exception e) {
            e.toString();
        }

    }

    //Metodo para buscar el siguiente ID
    private void siguienteId() {
        String id = String.valueOf(clidao.siguienteId());
        this.txtIdCliente.setText(id);
    }

    //Metodo para validar los campos que se ingresan
    private boolean validacion() {
        boolean validado = false;
        if (this.txtIdCliente.getText().equals("") || this.txtNombreCliente.getText().equals("") || this.txtNit.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "No se han ingresado algunos datos necesarios", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            validado = true;
        }
        return validado;
    }

    //Con este metodo se llenaran los campos al escoger un dato en la lista
    private void llenarCampos() {
        if (!idCC.equals("")) {
            this.txtIdCliente.setText(idCC);
            this.txtNit.setText(nitCC);
            this.txtNombreCliente.setText(nombreCC);
            this.txtTelefono.setText(telefonoCC);
            this.txtDescripcion.setText(descripcionCC);
            this.txtRegistro.setText(registroCC);
            this.txtDireccionCliente.setText(direccionCC);

            int idDep = Integer.parseInt(depCC);
            int idGiro = Integer.parseInt(giroCC);
            //Se busca los datos extraidos para los combos
            Departamento dep = new Departamento();
            dep.setIdDepartamento(idDep);
            Departamento d = (Departamento) depdao.buscar(dep);

            Giro giro = new Giro();
            giro.setIdGiro(idGiro);
            Giro g = (Giro) gdao.buscar(giro);

            //Se coloca en el combo
            this.comboDepartamento.setSelectedItem(d.getIdDepartamento() + "-" + d.getNombreDepartamento());
            this.comboGiro.setSelectedItem(g.getIdGiro() + "-" + g.getNombreGiro());

        }
    }

    //Metodo para llenar el combo 1
    public void llenarComboDepartamento() throws Exception {
        try {
            List<Departamento> lista = (List<Departamento>) depdao.consultar();
            mComboDep.addElement("Seleccionar Departamento");
            comboDepartamento.setModel(mComboDep);
            Departamento dep = new Departamento();
            for (int i = 0; i < lista.size(); i++) {
                dep = (Departamento) lista.get(i);
                mComboDep.addElement(dep.getIdDepartamento() + "-" + dep.getNombreDepartamento());
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Metodo para llenar el combo 2
    public void llenarComboGiro() throws Exception {
        try {
            List<Giro> lista = (List<Giro>) gdao.consultar();
            mComboGiro.addElement("Seleccionar Giro");
            comboGiro.setModel(mComboGiro);
            Giro giro = new Giro();
            for (int i = 0; i < lista.size(); i++) {
                giro = (Giro) lista.get(i);
                mComboGiro.addElement(giro.getIdGiro() + "-" + giro.getNombreGiro());
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Se utilizara este metodo para limpiar los campos.
    private void limpiarCampos() {
        this.txtIdCliente.setText("");
        this.txtNombreCliente.setText("");
        this.txtDireccionCliente.setText("");
        this.txtNit.setText("");
        this.txtTelefono.setText("");
        this.txtRegistro.setText("");
        this.txtDescripcion.setText("");
        comboDepartamento.setSelectedIndex(0);
        comboGiro.setSelectedIndex(0);
    }

    //Con este metodo se gestionara la BD, insertar modificar o eliminar datos.
    private void gestion(String op) {
        String msj = null;
        int id = Integer.parseInt(this.txtIdCliente.getText());
        String nit = this.txtNit.getText();
        String nombre = this.txtNombreCliente.getText();
        String telefono = this.txtTelefono.getText();
        String descripcion = this.txtDescripcion.getText();
        String registro = this.txtRegistro.getText();
        String direccion = this.txtDireccionCliente.getText();

        if (this.txtIdCliente.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "No se ha ingresado el Codigo de cliente", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (comboDepartamento.getSelectedIndex() == 0 || comboGiro.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar los elementos de las listas");
                comboDepartamento.setFocusable(true);
            } else {
                int idDepartamento = Integer.parseInt(comboDepartamento.getSelectedItem().toString().substring(0, comboDepartamento.getSelectedItem().toString().indexOf("-")));
                Departamento dep = new Departamento();
                dep.setIdDepartamento(idDepartamento);

                int idGiro = Integer.parseInt(comboGiro.getSelectedItem().toString().substring(0, comboGiro.getSelectedItem().toString().indexOf("-")));
                Giro giro = new Giro();
                giro.setIdGiro(idGiro);

                Cliente cliente = new Cliente(id, nit, nombre, telefono, descripcion, registro, direccion, dep, giro);
                switch (op) {
                    case "agregar":
                        try {
                            msj = clidao.insertar(cliente);
                            JOptionPane.showMessageDialog(this, msj);
                            limpiarCampos();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(this, "Error " + e.toString());
                        }
                        break;

                    case "modificar":
                        try {
                            String[] opciones = {"Si", "No", "Cancelar"};
                            int opp = JOptionPane.showOptionDialog(this, "¿Seguro que desea modificar el cliente?", "Modificar", JOptionPane.YES_NO_CANCEL_OPTION,
                                    JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[2]);
                            if (opp == 0) {
                                msj = clidao.modificar(cliente);
                                JOptionPane.showMessageDialog(this, msj);
                                limpiarCampos();
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(this, "Error " + e.toString());
                        }
                        break;

                    case "eliminar":
                        try {
                            String[] opciones = {"Si", "No", "Cancelar"};
                            int opp = JOptionPane.showOptionDialog(this, "¿Seguro que desea eliminar el cliente?", "Eliminar", JOptionPane.YES_NO_CANCEL_OPTION,
                                    JOptionPane.WARNING_MESSAGE, null, opciones, opciones[2]);
                            if (opp == 0) {
                                msj = clidao.eliminar(cliente);
                                JOptionPane.showMessageDialog(this, msj);
                                limpiarCampos();
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(this, "No se puede eliminar, "
                                    + "necesita eliminar antes los clientes asociados", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;

                    default:
                        JOptionPane.showMessageDialog(this, "Hay un error, contactar con el administrador", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                }
                siguienteId();
            }
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

        txtNombreCliente = new javax.swing.JTextField();
        txtDireccionCliente = new javax.swing.JTextField();
        btnAtras = new javax.swing.JButton();
        btnFin = new javax.swing.JButton();
        btnInicio = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        lblCodigo = new javax.swing.JLabel();
        lblNomEmpresa = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        lblNit = new javax.swing.JLabel();
        lblGiro = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        btnModificar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        txtIdCliente = new javax.swing.JFormattedTextField();
        txtTelefono = new javax.swing.JFormattedTextField();
        lblTelefono1 = new javax.swing.JLabel();
        txtRegistro = new javax.swing.JTextField();
        comboGiro = new javax.swing.JComboBox<>();
        lblDireccion1 = new javax.swing.JLabel();
        comboDepartamento = new javax.swing.JComboBox<>();
        txtNit = new javax.swing.JFormattedTextField();
        lblTelefono2 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        btnHelpCli = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
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

        txtNombreCliente.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtDireccionCliente.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        btnAtras.setFont(new java.awt.Font("Wide Latin", 0, 18)); // NOI18N
        btnAtras.setForeground(new java.awt.Color(255, 255, 255));
        btnAtras.setText("<");
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });

        btnFin.setFont(new java.awt.Font("Wide Latin", 0, 18)); // NOI18N
        btnFin.setForeground(new java.awt.Color(255, 255, 255));
        btnFin.setText(">>");

        btnInicio.setFont(new java.awt.Font("Wide Latin", 0, 18)); // NOI18N
        btnInicio.setForeground(new java.awt.Color(255, 255, 255));
        btnInicio.setText("<<");

        btnSiguiente.setFont(new java.awt.Font("Wide Latin", 0, 18)); // NOI18N
        btnSiguiente.setForeground(new java.awt.Color(255, 255, 255));
        btnSiguiente.setText(">");

        lblCodigo.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        lblCodigo.setText("Código:");

        lblNomEmpresa.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        lblNomEmpresa.setText("Nombre de la Empresa:");

        lblDireccion.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        lblDireccion.setText("Dirección:");

        lblNit.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        lblNit.setText("NIT:");

        lblGiro.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        lblGiro.setText("Giro:");

        lblTelefono.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        lblTelefono.setText("Teléfono:");

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        jLabel1.setText("Clientes");

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

        btnSalir.setBackground(new java.awt.Color(11, 104, 204));
        btnSalir.setFont(new java.awt.Font("Gadugi", 0, 18)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/img/exit.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnBuscar.setBackground(new java.awt.Color(11, 104, 204));
        btnBuscar.setFont(new java.awt.Font("Gadugi", 0, 18)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/img/search.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        txtIdCliente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtIdCliente.setToolTipText("ID");
        txtIdCliente.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        try {
            txtTelefono.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtTelefono.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        lblTelefono1.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        lblTelefono1.setText("Registro:");

        txtRegistro.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtRegistro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRegistroKeyPressed(evt);
            }
        });

        comboGiro.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        comboGiro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblDireccion1.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        lblDireccion1.setText("Departamento:");

        comboDepartamento.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        comboDepartamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        try {
            txtNit.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-######-###-#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtNit.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        lblTelefono2.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        lblTelefono2.setText("Descripción:");

        txtDescripcion.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyPressed(evt);
            }
        });

        btnHelpCli.setBackground(new java.awt.Color(11, 104, 240));
        btnHelpCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/img/help.png"))); // NOI18N
        btnHelpCli.setToolTipText("Almacena los datos de los clientes,  a los cuales la empresa les presta servicio.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnHelpCli, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator2))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAgregar)
                        .addGap(18, 18, 18)
                        .addComponent(btnModificar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLimpiar)
                        .addGap(18, 18, 18)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(lblCodigo)
                        .addGap(18, 18, 18)
                        .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar)
                        .addGap(18, 18, 18)
                        .addComponent(btnSiguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnFin, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(83, 83, 83)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblGiro)
                                    .addComponent(lblNit)
                                    .addComponent(lblTelefono)
                                    .addComponent(lblTelefono1)
                                    .addComponent(lblTelefono2))
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(comboGiro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDescripcion)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNomEmpresa)
                                    .addComponent(lblDireccion, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblDireccion1, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(86, 86, 86)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addComponent(btnHelpCli, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCodigo)
                            .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnFin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSiguiente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNomEmpresa)
                    .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDireccion))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDireccion1)
                    .addComponent(comboDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNit)
                    .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGiro)
                    .addComponent(comboGiro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTelefono)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTelefono1)
                    .addComponent(txtRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTelefono2)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModificar)
                    .addComponent(btnAgregar)
                    .addComponent(btnLimpiar)
                    .addComponent(btnEliminar)
                    .addComponent(btnSalir))
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtrasActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // Se modifica el cliente
        if (validacion()) {
            gestion("modificar");
        }

    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // Se agrega el cliente
        if (validacion()) {
            gestion("agregar");
        }

    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // Se elimina el cliente
        gestion("eliminar");
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // Abrimos la lista de clientes
        FrmListaClientes frmListaClientes = new FrmListaClientes();
        escritorioPane.add(frmListaClientes);
        frmListaClientes.show();

        //Diseno, se agrega un color customizado para el fondo.
        Color nColor = new Color(248, 249, 252);
        frmListaClientes.getContentPane().setBackground(nColor);
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtRegistroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRegistroKeyPressed
        // Validacion para que no se ingrese mas caracteres de lo que se permite
        String texto = this.txtRegistro.getText();
        int tamano = texto.length();
        char c = evt.getKeyChar();
        if (tamano < 100) {
            this.txtRegistro.setEditable(true);
        } else {
            this.txtRegistro.setEditable(false);
            if (evt.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
                this.txtRegistro.setEditable(true);
            }
        }
    }//GEN-LAST:event_txtRegistroKeyPressed

    private void txtDescripcionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionKeyPressed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        // Se colocan los datos extraidos de la lista
        llenarCampos();
    }//GEN-LAST:event_formInternalFrameActivated


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnFin;
    private javax.swing.JButton btnHelpCli;
    private javax.swing.JButton btnInicio;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JComboBox<String> comboDepartamento;
    private javax.swing.JComboBox<String> comboGiro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblDireccion1;
    private javax.swing.JLabel lblGiro;
    private javax.swing.JLabel lblNit;
    private javax.swing.JLabel lblNomEmpresa;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTelefono1;
    private javax.swing.JLabel lblTelefono2;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtDireccionCliente;
    private javax.swing.JFormattedTextField txtIdCliente;
    private javax.swing.JFormattedTextField txtNit;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtRegistro;
    private javax.swing.JFormattedTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
