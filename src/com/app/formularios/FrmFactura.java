/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.formularios;

import com.app.dao.ClienteDAO;
import com.app.dao.CondicionPagoDAO;
import com.app.dao.DetalleFacDAO;
import com.app.dao.FacturaEncDAO;
import com.app.dao.ProductoDAO;
import com.app.dao.TipoVentaDAO;
import com.app.modelo.Cliente;
import com.app.modelo.CondicionPago;
import com.app.modelo.DetalleFactura;
import com.app.modelo.FacturaEncabezado;
import com.app.modelo.Producto;
import com.app.modelo.TipoVenta;
import com.app.util.NumerosATexto;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Steven
 */
public class FrmFactura extends javax.swing.JInternalFrame {

    //Variables locales
    private ClienteDAO clidao;
    private CondicionPagoDAO cpdao;
    private ProductoDAO pdao;
    private FacturaEncDAO fedao;
    private DetalleFacDAO dfdao;
    private TipoVentaDAO tpdao;
    private DefaultComboBoxModel mComboCli = new DefaultComboBoxModel();
    private DefaultComboBoxModel mComboCP = new DefaultComboBoxModel();
    private DefaultTableModel modeloDetalle = new DefaultTableModel();
    private DecimalFormat df = new DecimalFormat("#.00");
    private JComboBox comboProductos = new JComboBox();
    private JComboBox comboTipoVenta = new JComboBox();

    public FrmFactura() {
        initComponents();
        try {
            establecerFecha();
            clidao = new ClienteDAO();
            cpdao = new CondicionPagoDAO();
            pdao = new ProductoDAO();
            fedao = new FacturaEncDAO();
            dfdao = new DetalleFacDAO();
            tpdao = new TipoVentaDAO();
            llenarComboCliente();
            llenarComboCondicion();
            numToTxt();
            crearTabla();

        } catch (Exception e) {
            e.toString();
        }
    }
    
    //Metodo para validar los datos
    private boolean validar(){
        boolean validado = false;
        if(this.txtNoFactura.getText().equals("")){
            JOptionPane.showMessageDialog(this, "No se han ingresado algunos datos necesarios", "Error", JOptionPane.ERROR_MESSAGE);
        } else if(tblDetalleFactura.getRowCount()==0){
            JOptionPane.showMessageDialog(this, "Necesita agregar un detalle por lo menos", "Error", JOptionPane.ERROR_MESSAGE);
        }else if(!validacionTabla()){
        
        }else {
            validado = true;
        }
        return validado;
    }

    //Metodo para limpiar los campos
    private void limpiarCampos() {
        this.txtNoFactura.setText("");
        this.txtCantText.setText("");
        this.txtACuentaDe.setText("");
        this.txtIva.setText("");
        this.txtIvaRete.setText("");
        this.txtNotaRemision.setText("");
        this.txtSub.setText("");
        this.txtSumas.setText("");
        this.txtVentaExe.setText("");
        this.txtVentaTotal.setText("");
        comboCliente.setSelectedIndex(0);
        comboCondicion.setSelectedIndex(0);
        modeloDetalle.setRowCount(0);
        this.btnBorrar.setEnabled(false);
        establecerFecha();
    }

    //Metodo que gestion agregar la factura o impirmirla
    private void gestion(String op) {

        try {
            String msj = null;
//            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
//            java.util.Date date = dateformat.parse(this.txtFecha.getDateFormatString());
//            java.sql.Date fecha = new java.sql.Date(date.getTime());
            java.sql.Date fecha = new java.sql.Date(this.txtFecha.getDate().getTime());
            int noFactura = Integer.parseInt(this.txtNoFactura.getText());
            String aCuentaDe = this.txtACuentaDe.getText();
            String notaRemision = this.txtNotaRemision.getText();
            if (comboCliente.getSelectedIndex() == 0 || comboCondicion.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this, "Debe Seleccionar un elemento de las listas");
                comboCliente.setFocusable(true);
            } else {
                int idCliente = Integer.parseInt(comboCliente.getSelectedItem().toString().substring(0, comboCliente.getSelectedItem().toString().indexOf("-")));
                Cliente cliente = new Cliente();
                cliente.setIdCliente(idCliente);

                int idCP = Integer.parseInt(comboCondicion.getSelectedItem().toString().substring(0, comboCondicion.getSelectedItem().toString().indexOf("-")));
                CondicionPago cp = new CondicionPago();
                cp.setIdCP(idCP);
                FacturaEncabezado facturaEnc = new FacturaEncabezado(noFactura, fecha, notaRemision, aCuentaDe, cp, cliente);
                switch (op) {
                    case "agregar":
                        try {
                            fedao.insertar(facturaEnc);
                            // Se insertara los datos de la tabla detalle
                            if (tblDetalleFactura.getRowCount() != 0 && tblDetalleFactura.getColumnCount() != 0) {
                                for (int i = 0; i < tblDetalleFactura.getRowCount(); i++) {
                                    int noDetalle = i + 1;
                                    int cantidad = Integer.parseInt(tblDetalleFactura.getValueAt(i, 1).toString());
                                    String descripcionFactura = tblDetalleFactura.getValueAt(i, 2).toString();
                                    float precioUnitario = Float.valueOf(tblDetalleFactura.getValueAt(i, 3).toString());
                                    int idProducto = Integer.parseInt(tblDetalleFactura.getValueAt(i, 0).toString().substring(0, tblDetalleFactura.getValueAt(i, 0).toString().indexOf("-")));
                                    Producto prod = new Producto();
                                    prod.setIdProducto(idProducto);
                                    int idTipoVenta = Integer.parseInt(tblDetalleFactura.getValueAt(i, 4).toString().substring(0, tblDetalleFactura.getValueAt(i, 4).toString().indexOf("-")));
                                    TipoVenta tp = new TipoVenta();
                                    tp.setIdTipoVenta(idTipoVenta);
                                    DetalleFactura facDet = new DetalleFactura(noDetalle, facturaEnc, prod, cantidad, precioUnitario, descripcionFactura, tp);
                                    msj = dfdao.insertar(facDet);
                                }

                            }
                            JOptionPane.showMessageDialog(this, msj);
                            limpiarCampos();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(this, "Error " + e.toString());
                        }
                        break;

                    default:
                        JOptionPane.showMessageDialog(this, "Hay un error, contactar con el administrador", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    //Metodo para establecer automaticamente la fecha actual
    private void establecerFecha() {
        LocalDate localDate = LocalDate.now();
        this.txtFecha.setDate(Date.valueOf(localDate));

    }

    //Metodo para validar los datos que se ingresan en la tabla Detalle
    private boolean validacionTabla() {
        if (tblDetalleFactura.getRowCount() != 0 && tblDetalleFactura.getColumnCount() != 0) {
            for (int i = 0; i < tblDetalleFactura.getRowCount(); i++) {
                for (int j = 0; j < tblDetalleFactura.getColumnCount(); j++) {
                    if (tblDetalleFactura.getValueAt(i, j) != null) {
                        String value = tblDetalleFactura.getValueAt(i, j).toString();
                        if (value.trim().length() == 0) {
                            JOptionPane.showMessageDialog(rootPane, "No hay datos", "ERROR", JOptionPane.ERROR_MESSAGE);
                            return false;
                        } else {
                            if (tblDetalleFactura.getValueAt(i, 1) != null && tblDetalleFactura.getValueAt(i, 3) != null) {
                                String cantidad = tblDetalleFactura.getValueAt(i, 1).toString();
                                String precio = tblDetalleFactura.getValueAt(i, 3).toString();
                                for (int k = 0; k < cantidad.length(); k++) {
                                    if (Character.isLetter(cantidad.charAt(k))) {
                                        JOptionPane.showMessageDialog(this, "Ha ingresado letras en la columna de Cantidad", "ERROR", JOptionPane.ERROR_MESSAGE);
                                        return false;
                                    }
                                }
                                for (int k = 0; k < precio.length(); k++) {
                                    if (Character.isLetter(precio.charAt(k))) {
                                        JOptionPane.showMessageDialog(this, "Ha ingresado letras en la columna de Precio", "ERROR", JOptionPane.ERROR_MESSAGE);
                                        return false;
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(this, "No ha ingresado  datos en la tabla", "ERROR", JOptionPane.ERROR_MESSAGE);
                                return false;
                            }

                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "No ha ingresado algunos datos en la tabla", "ERROR", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }

                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No ha ingresado datos en la tabla", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    //Metodo para calcular la suma
    private void calcularSuma() {
        if (validacionTabla()) {
            String sumasGravadas;
            int filas = modeloDetalle.getRowCount();
            int cantidad = 0;
            int tipoVenta;
            float precio, suma, ventaTotal, subTotal, iva, ivaretenido = 0, contadorGravadas = 0, contadorExentas = 0;

            for (int i = 0; i < filas; i++) {
                tipoVenta = Integer.parseInt(tblDetalleFactura.getValueAt(i, 4).toString().substring(0, tblDetalleFactura.getValueAt(i, 4).toString().indexOf("-")));
                cantidad = Integer.parseInt(modeloDetalle.getValueAt(i, 1).toString());
                precio = Float.valueOf(modeloDetalle.getValueAt(i, 3).toString());
                suma = cantidad * precio;
                //1=Venta Gravada, 2= Venta Exenta.
                if (tipoVenta == 1) {
                    contadorGravadas += suma;
                } else {
                    contadorExentas += suma;
                }

            }
            sumasGravadas = df.format(contadorGravadas);
            this.txtSumas.setText(sumasGravadas);
            iva = contadorGravadas * 0.13f;
            this.txtIva.setText(df.format(iva));
            subTotal = contadorGravadas + iva;
            this.txtSub.setText(df.format(subTotal));
            if (contadorGravadas > 100) {
                ivaretenido = contadorGravadas * 0.01f;
            }
            ventaTotal = (subTotal - ivaretenido) + contadorExentas;
            this.txtVentaExe.setText(df.format(contadorExentas));
            this.txtIvaRete.setText(df.format(ivaretenido));
            this.txtVentaTotal.setText(df.format(ventaTotal));
        }
    }

    //Metodo para crear la tabla
    private void crearTabla() throws Exception {

        modeloDetalle.addColumn("Producto");
        modeloDetalle.addColumn("Cantidad");
        modeloDetalle.addColumn("Descripcion");
        modeloDetalle.addColumn("Precio Unitario");
        modeloDetalle.addColumn("Tipo Venta");
        tblDetalleFactura.setModel(modeloDetalle);

        //Se crea la lista para los productos
        llenarComboProductos();
        //Se crea la lista para decidir el tipo de venta
        llenarComboVentas();
    }

    //Metodo para llenar el combo de productos en la tabla detalle
    private void llenarComboProductos() throws Exception {
        try {
            TableColumn columnaProducto = tblDetalleFactura.getColumnModel().getColumn(0);

            List<Producto> lista = (List<Producto>) pdao.consultar();
            Producto prod = new Producto();
            for (int i = 0; i < lista.size(); i++) {
                prod = (Producto) lista.get(i);
                comboProductos.addItem(prod.getIdProducto() + "-" + prod.getNombreProducto());
            }
            columnaProducto.setCellEditor(new DefaultCellEditor(comboProductos));
        } catch (Exception ex) {
            Logger.getLogger(this.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Metodo para llenar el combo del tipo de venta en la tabla detalle
    private void llenarComboVentas() throws Exception {
        try {
            TableColumn columnaTP = tblDetalleFactura.getColumnModel().getColumn(4);
            List<TipoVenta> lista = (List<TipoVenta>) tpdao.consultar();
            TipoVenta tp = new TipoVenta();
            for (int i = 0; i < lista.size(); i++) {
                tp = (TipoVenta) lista.get(i);
                comboTipoVenta.addItem(tp.getIdTipoVenta() + "-" + tp.getNombreTipoVenta());
            }
            columnaTP.setCellEditor(new DefaultCellEditor(comboTipoVenta));
        } catch (Exception ex) {
            Logger.getLogger(this.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Metodo para convertir numeros a texto
    private void numToTxt() {
        // Escucha los eventos
        this.txtVentaTotal.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                convertir();
            }

            public void removeUpdate(DocumentEvent e) {
                convertir();
            }

            public void insertUpdate(DocumentEvent e) {
                convertir();
            }

            public void convertir() {
                String numeroTxt;
                if (!txtVentaTotal.getText().equals("")) {
                    String numero = txtVentaTotal.getText();
                    if (numero.contains(".")) {
                        String dolares = NumerosATexto.cantidadConLetra(numero.substring(0, numero.indexOf("."))) + "Dolares";
                        String centavos = "";
                        if (!numero.substring(numero.indexOf(".") + 1).equals("")) {
                            centavos = NumerosATexto.cantidadConLetra(numero.substring(numero.indexOf(".") + 1)) + "Centavos";
                        }

                        numeroTxt = dolares + " Con " + centavos;
                    } else {
                        numeroTxt = NumerosATexto.cantidadConLetra(numero) + "Dolares";
                    }
                    txtCantText.setText(numeroTxt);
                } else {
                    txtCantText.setText("");
                }
            }
        });
    }

    //Metodo para llenar el combo 1
    private void llenarComboCliente() throws Exception {
        try {
            List<Cliente> lista = (List<Cliente>) clidao.consultar();
            mComboCli.addElement("Seleccionar Cliente");
            comboCliente.setModel(mComboCli);
            Cliente cliente = new Cliente();
            for (int i = 0; i < lista.size(); i++) {
                cliente = (Cliente) lista.get(i);
                mComboCli.addElement(cliente.getIdCliente() + "-" + cliente.getNombreCliente());
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Metodo para llenar el combo 2
    private void llenarComboCondicion() throws Exception {
        try {
            List<CondicionPago> lista = (List<CondicionPago>) cpdao.consultar();
            mComboCP.addElement("Condicion de Pago");
            comboCondicion.setModel(mComboCP);
            CondicionPago cp = new CondicionPago();
            for (int i = 0; i < lista.size(); i++) {
                cp = (CondicionPago) lista.get(i);
                mComboCP.addElement(cp.getIdCP() + "-" + cp.getNombreTipoCP());
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getName()).log(Level.SEVERE, null, ex);
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

        jLabel10 = new javax.swing.JLabel();
        txtNotaRemision = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetalleFactura = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        lblCantText = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        comboCliente = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtSumas = new javax.swing.JTextField();
        txtSub = new javax.swing.JTextField();
        txtIvaRete = new javax.swing.JTextField();
        txtVentaExe = new javax.swing.JTextField();
        txtVentaTotal = new javax.swing.JTextField();
        btnCalcular = new javax.swing.JButton();
        btnCrearFactura = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        comboCondicion = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        txtACuentaDe = new javax.swing.JTextField();
        txtNoFactura = new javax.swing.JFormattedTextField();
        btnAgregar = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtCantText = new javax.swing.JTextArea();
        btnBorrar = new javax.swing.JButton();
        txtIva = new javax.swing.JFormattedTextField();
        btnLimpiar = new javax.swing.JButton();
        btnHelpFact = new javax.swing.JButton();
        txtFecha = new com.toedter.calendar.JDateChooser();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);

        jLabel10.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel10.setText("de Remisión Anterior");

        txtNotaRemision.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        tblDetalleFactura.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblDetalleFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CANT", "DESCRIPCION", "PRECIO UNITARIO", "VENTAS EXENTAS", "VENTAS GRAVADAS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblDetalleFactura);

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel1.setText("Cliente");

        lblCantText.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lblCantText.setText("SON:");

        jLabel11.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel11.setText("Sumas");

        jLabel12.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel12.setText("13% IVA");

        jLabel13.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel13.setText("Sub-Total");

        jLabel3.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel3.setText("Número de Comprobante");

        jLabel14.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel14.setText("(-) Iva Retenido");

        jLabel4.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel4.setText("Fecha de Emisión");

        comboCliente.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        comboCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel15.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel15.setText("Ventas Exentas");

        jLabel16.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel16.setText("Venta Total");

        txtSumas.setEditable(false);
        txtSumas.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtSumas.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtSumasInputMethodTextChanged(evt);
            }
        });

        txtSub.setEditable(false);
        txtSub.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtIvaRete.setEditable(false);
        txtIvaRete.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtVentaExe.setEditable(false);
        txtVentaExe.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtVentaTotal.setEditable(false);
        txtVentaTotal.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        btnCalcular.setBackground(new java.awt.Color(11, 104, 204));
        btnCalcular.setFont(new java.awt.Font("Gadugi", 0, 20)); // NOI18N
        btnCalcular.setForeground(new java.awt.Color(255, 255, 255));
        btnCalcular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/img/calc2.png"))); // NOI18N
        btnCalcular.setText("Calcular");
        btnCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularActionPerformed(evt);
            }
        });

        btnCrearFactura.setBackground(new java.awt.Color(11, 104, 204));
        btnCrearFactura.setFont(new java.awt.Font("Gadugi", 0, 18)); // NOI18N
        btnCrearFactura.setForeground(new java.awt.Color(255, 255, 255));
        btnCrearFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/img/billing.png"))); // NOI18N
        btnCrearFactura.setText("Crear Factura");
        btnCrearFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearFacturaActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel9.setText("Cond. de Pago");

        comboCondicion.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        comboCondicion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel17.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel17.setText("Venta a cuenta de");

        txtACuentaDe.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtNoFactura.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtNoFactura.setToolTipText("ID");
        txtNoFactura.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        btnAgregar.setBackground(new java.awt.Color(11, 104, 204));
        btnAgregar.setFont(new java.awt.Font("Gadugi", 0, 12)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/img/addDet.png"))); // NOI18N
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel18.setText("Fecha y No. de Nota ");

        txtCantText.setEditable(false);
        txtCantText.setColumns(20);
        txtCantText.setLineWrap(true);
        txtCantText.setRows(3);
        jScrollPane2.setViewportView(txtCantText);

        btnBorrar.setBackground(new java.awt.Color(11, 104, 204));
        btnBorrar.setFont(new java.awt.Font("Gadugi", 0, 12)); // NOI18N
        btnBorrar.setForeground(new java.awt.Color(255, 255, 255));
        btnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/img/lessDet.png"))); // NOI18N
        btnBorrar.setEnabled(false);
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        txtIva.setEditable(false);
        txtIva.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00;(¤#,##0.00)"))));
        txtIva.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

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

        btnHelpFact.setBackground(new java.awt.Color(11, 104, 240));
        btnHelpFact.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/img/help.png"))); // NOI18N
        btnHelpFact.setToolTipText("Este Formulario se utiliza para generar la factura, Seleccion el cliente, ingresa los servicios y una descripcion, luego la cantidad, posteriormente da clic en calcular y se generan los calculos para imprimir la factura clic en Crea Factura.");

        txtFecha.setDateFormatString("dd-MM-yyyy");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnCrearFactura)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(lblCantText)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(21, 21, 21)
                                        .addComponent(txtNotaRemision, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtACuentaDe, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAgregar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBorrar)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(97, 97, 97)
                                .addComponent(btnCalcular, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel16))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtSumas)
                                        .addComponent(txtSub)
                                        .addComponent(txtIvaRete)
                                        .addComponent(txtVentaExe)
                                        .addComponent(txtVentaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(31, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtNoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(btnHelpFact, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel1))
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboCondicion, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnHelpFact)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtFecha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtNoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(comboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(comboCondicion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel18)
                                .addGap(2, 2, 2)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(txtNotaRemision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17)
                                    .addComponent(txtACuentaDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnCalcular)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 160, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(txtSumas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(22, 22, 22)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(txtSub, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtIvaRete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(txtVentaExe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel16)
                                    .addComponent(txtVentaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnAgregar)
                                    .addComponent(btnBorrar))
                                .addGap(24, 24, 24)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCantText)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(10, 10, 10)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrearFactura)
                    .addComponent(btnLimpiar))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSumasInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtSumasInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSumasInputMethodTextChanged

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // Agrega una fila a la tabla
        Object[] fila = new Object[4];
        modeloDetalle.addRow(fila);
        btnBorrar.setEnabled(true);
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularActionPerformed
        // TODO add your handling code here:
        calcularSuma();
    }//GEN-LAST:event_btnCalcularActionPerformed

    private void btnCrearFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearFacturaActionPerformed
        //Creamos la factura
        if(validar()){
            gestion("agregar");
        }
        
    }//GEN-LAST:event_btnCrearFacturaActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        // TODO add your handling code here:
        if (modeloDetalle.getRowCount() > 0) {
            modeloDetalle.removeRow(modeloDetalle.getRowCount() - 1);
            if (modeloDetalle.getRowCount() == 0) {
                btnBorrar.setEnabled(false);
            }
        }
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // Limpiamos los campos
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnCalcular;
    private javax.swing.JButton btnCrearFactura;
    private javax.swing.JButton btnHelpFact;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> comboCliente;
    private javax.swing.JComboBox<String> comboCondicion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCantText;
    private javax.swing.JTable tblDetalleFactura;
    private javax.swing.JTextField txtACuentaDe;
    private javax.swing.JTextArea txtCantText;
    private com.toedter.calendar.JDateChooser txtFecha;
    private javax.swing.JFormattedTextField txtIva;
    private javax.swing.JTextField txtIvaRete;
    private javax.swing.JFormattedTextField txtNoFactura;
    private javax.swing.JTextField txtNotaRemision;
    private javax.swing.JTextField txtSub;
    private javax.swing.JTextField txtSumas;
    private javax.swing.JTextField txtVentaExe;
    private javax.swing.JTextField txtVentaTotal;
    // End of variables declaration//GEN-END:variables
}
