package com.app.formularios;

import com.app.dao.ClienteDAO;
import com.app.dao.CondicionPagoDAO;
import com.app.dao.DetalleFacDAO;
import com.app.dao.FacturaEncDAO;
import com.app.dao.ProductoDAO;
import com.app.dao.TipoVentaDAO;
import static com.app.formularios.FrmPrincipal.escritorioPane;
import com.app.modelo.Cliente;
import com.app.modelo.CondicionPago;
import com.app.modelo.DetalleFactura;
import com.app.modelo.FacturaEncabezado;
import com.app.modelo.Producto;
import com.app.modelo.TipoVenta;
import java.awt.Color;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Steven
 */
public class FrmGestionFacturas extends javax.swing.JInternalFrame {

    //Variables locales necesarias
    public static String noFacturaCC = "";
    private FacturaEncDAO fedao;
    private DetalleFacDAO dfdao;
    private ClienteDAO clidao;
    private ProductoDAO pdao;
    private CondicionPagoDAO cpdao;
    private TipoVentaDAO tpdao;
    private DefaultComboBoxModel mComboCli = new DefaultComboBoxModel();
    private DefaultComboBoxModel mComboCP = new DefaultComboBoxModel();
    private JComboBox comboProductos = new JComboBox();
    private JComboBox comboTipoVenta = new JComboBox();
    private DefaultTableModel modeloDetalle = new DefaultTableModel();
    private DecimalFormat df = new DecimalFormat("#.00");

    public FrmGestionFacturas() {
        initComponents();
        try {
            fedao = new FacturaEncDAO();
            dfdao = new DetalleFacDAO();
            clidao = new ClienteDAO();
            cpdao = new CondicionPagoDAO();
            pdao = new ProductoDAO();
            tpdao = new TipoVentaDAO();

            //se llenan los combos
            llenarComboCliente();
            llenarComboCondicion();

            //Se crea la tabla
            crearTabla();

        } catch (Exception e) {
            e.toString();
        }
    }

    //Se activaran los botones en caso que exista informacion a modificar o eliminar
    private void activarBotones() {
        if (!this.txtNoFactura.getText().equals("") || !this.txtFecha.getText().equals("")) {
            this.btnModificar.setEnabled(true);
            this.btnEliminar.setEnabled(true);
        }
    }

    //Metodo para limpiar los campos
    private void limpiarCampos() {
        this.txtNoFactura.setText("");
        this.txtFecha.setText("");
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
        this.btnModificar.setEnabled(false);
        this.btnEliminar.setEnabled(false);
        this.btnBorrar.setEnabled(false);
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

    //Con este metodo se llenaran los campos al escoger un dato en la lista
    private void llenarCampos() {
        if (!noFacturaCC.equals("")) {
            limpiarCampos();
            int no = Integer.parseInt(noFacturaCC);
            FacturaEncabezado feBuscar = new FacturaEncabezado();
            feBuscar.setNoFactura(no);
            FacturaEncabezado fe = (FacturaEncabezado) fedao.buscar(feBuscar);

            this.txtNoFactura.setText(String.valueOf(fe.getNoFactura()));
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String fecha = formatter.format(fe.getFecha());
            this.txtFecha.setText(fecha);
            this.txtNotaRemision.setText(fe.getFechaNotaRemision());
            this.txtACuentaDe.setText(fe.getaCuentaDe());

            int idCliente = fe.getCliente().getIdCliente();
            int idCP = fe.getCondicionPago().getIdCP();
            //Se busca los datos extraidos para los combos
            Cliente cli = new Cliente();
            cli.setIdCliente(idCliente);
            Cliente c = (Cliente) clidao.buscar(cli);

            CondicionPago condicion = new CondicionPago();
            condicion.setIdCP(idCP);
            CondicionPago cp = (CondicionPago) cpdao.buscar(condicion);
            //Se coloca en el combo
            this.comboCliente.setSelectedItem(c.getIdCliente() + "-" + c.getNombreCliente());
            this.comboCondicion.setSelectedItem(cp.getIdCP() + "-" + cp.getNombreTipoCP());

            DetalleFacDAO dfDao = new DetalleFacDAO();
            DetalleFactura detfact = new DetalleFactura();
            FacturaEncabezado fenca = new FacturaEncabezado();
            fenca.setNoFactura(no);
            detfact.setFactura(fenca);
            List<DetalleFactura> lista = (List<DetalleFactura>) dfDao.buscar(detfact);
            Object[] fila = new Object[5];
            for (int i = 0; i < lista.size(); i++) {
                int idProducto = lista.get(i).getProducto().getIdProducto();
                //Se busca el Producto
                Producto prod = new Producto();
                prod.setIdProducto(idProducto);
                Producto p = (Producto) pdao.buscar(prod);
                comboProductos.setSelectedItem(p.getIdProducto() + "-" + p.getNombreProducto());
                fila[0] = p.getIdProducto() + "-" + p.getNombreProducto();
                fila[1] = lista.get(i).getCantidad();
                fila[2] = lista.get(i).getDescripcionFactura();
                fila[3] = lista.get(i).getPrecioUnitario();

                int idTipoVenta = lista.get(i).getTipoventa().getIdTipoVenta();
                //Se busca el Tipo de venta
                TipoVenta tipoVenta = new TipoVenta();
                tipoVenta.setIdTipoVenta(idTipoVenta);
                TipoVenta tp = (TipoVenta) tpdao.buscar(tipoVenta);
                comboTipoVenta.setSelectedItem(tp.getIdTipoVenta() + "-" + tp.getNombreTipoVenta());
                fila[4] = tp.getIdTipoVenta() + "-" + tp.getNombreTipoVenta();
                modeloDetalle.addRow(fila);
                noFacturaCC = "";
            }
        }

        //Se activa el boton borrar para tabla de detalle si hay datos
        if (modeloDetalle.getRowCount() > 0) {
            this.btnBorrar.setEnabled(true);
        }
        activarBotones();
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
            float precio, suma,ventaTotal,subTotal,iva, ivaretenido = 0, contadorGravadas=0, contadorExentas=0;

            for (int i = 0; i < filas; i++) {
                tipoVenta = Integer.parseInt(tblDetalleFactura.getValueAt(i, 4).toString().substring(0, tblDetalleFactura.getValueAt(i, 4).toString().indexOf("-")));
                cantidad = Integer.parseInt(modeloDetalle.getValueAt(i, 1).toString());
                precio = Float.valueOf(modeloDetalle.getValueAt(i, 3).toString());
                suma = cantidad * precio;
                //1=Venta Gravada, 2= Venta Exenta.
                if(tipoVenta==1){
                    contadorGravadas += suma;
                } else {
                    contadorExentas += suma;
                }
                
            }
            sumasGravadas= df.format(contadorGravadas);
            this.txtSumas.setText(sumasGravadas);
            iva = contadorGravadas * 0.13f;
            this.txtIva.setText(df.format(iva));
            subTotal = contadorGravadas + iva;
            this.txtSub.setText(df.format(subTotal));
            if(contadorGravadas>100){
                ivaretenido = contadorGravadas*0.01f;
            }
            ventaTotal = (subTotal-ivaretenido) + contadorExentas;
            this.txtVentaExe.setText(df.format(contadorExentas));
            this.txtIvaRete.setText(df.format(ivaretenido));
            this.txtVentaTotal.setText(df.format(ventaTotal));
        }

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

    //Metodo que gestion agregar la factura o impirmirla
    private void gestion(String op) {

        try {
            String msj = null;
            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date date = dateformat.parse(this.txtFecha.getText());
            java.sql.Date fecha = new java.sql.Date(date.getTime());
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
                    case "modificar":
                        try {
                            int opp = JOptionPane.showConfirmDialog(this, "¿Seguro que desea modificar la factura?",
                                    "Modificar", JOptionPane.YES_NO_CANCEL_OPTION);
                            if (opp == 0) {
                                fedao.modificar(facturaEnc);
                                // Se insertara los datos de la tabla detalle
                                if (tblDetalleFactura.getRowCount() != 0 && tblDetalleFactura.getColumnCount() != 0) {
                                    //Se vacia la tabla de detalles en caso de nuevas modificaciones
                                    List listaDetalle = dfdao.consultar();
                                    for (int i = 0; i < listaDetalle.size(); i++) {
                                        DetalleFactura det = new DetalleFactura();
                                        det.setNoDetalle(i + 1);
                                        det.setFactura(facturaEnc);
                                        if (dfdao.buscar(det) != null) {
                                            //Se eliminan los datos del detalle si existen para volverlos ingresar los actualizados
                                            dfdao.eliminar(det);
                                        }
                                    }
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
                                        if(dfdao.insertar(facDet).contains("Se ha ingresado")){
                                            msj = "Se ha modificado exitosamente";
                                        } else {
                                            msj = "Error al modificar";
                                        }

                                    }

                                }
                                JOptionPane.showMessageDialog(this, msj);
                                limpiarCampos();
                            }

                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(this, "Error " + e.toString());
                        }
                        break;
                    case "eliminar":
                        try {
                            int opp = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar la factura? Se borrará toda información asociada.",
                                    "Eliminar", JOptionPane.YES_NO_CANCEL_OPTION);
                            if (opp == 0) {
                                msj = fedao.eliminar(facturaEnc);
                                JOptionPane.showMessageDialog(this, msj);
                                limpiarCampos();
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(this, "No se puede eliminar, "
                                    + "necesita eliminar antes campos asociados", "Error", JOptionPane.ERROR_MESSAGE);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtVentaTotal = new javax.swing.JTextField();
        btnCalcular = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JFormattedTextField();
        comboCondicion = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        txtACuentaDe = new javax.swing.JTextField();
        txtNoFactura = new javax.swing.JFormattedTextField();
        btnAgregar = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnBorrar = new javax.swing.JButton();
        comboCliente = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        txtIva = new javax.swing.JFormattedTextField();
        jLabel16 = new javax.swing.JLabel();
        txtSumas = new javax.swing.JTextField();
        txtSub = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtNotaRemision = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetalleFactura = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtIvaRete = new javax.swing.JTextField();
        txtVentaExe = new javax.swing.JTextField();
        btnListaFacturas = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();

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

        txtVentaTotal.setEditable(false);
        txtVentaTotal.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        btnCalcular.setBackground(new java.awt.Color(11, 104, 204));
        btnCalcular.setFont(new java.awt.Font("Gadugi", 0, 20)); // NOI18N
        btnCalcular.setForeground(new java.awt.Color(255, 255, 255));
        btnCalcular.setText("Calcular");
        btnCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularActionPerformed(evt);
            }
        });

        btnModificar.setBackground(new java.awt.Color(11, 104, 204));
        btnModificar.setFont(new java.awt.Font("Gadugi", 0, 18)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(255, 255, 255));
        btnModificar.setText("Modificar");
        btnModificar.setEnabled(false);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel9.setText("Cond. de Pago");

        txtFecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd-MM-yyyy"))));
        txtFecha.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

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
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel13.setText("Sub-Total");

        jLabel18.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel18.setText("Fecha y No. de");

        jLabel3.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel3.setText("Numero de Comprobante");

        jLabel14.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel14.setText("(-) Iva Retenido");

        jLabel4.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel4.setText("Fecha de Emision");

        btnBorrar.setBackground(new java.awt.Color(11, 104, 204));
        btnBorrar.setFont(new java.awt.Font("Gadugi", 0, 12)); // NOI18N
        btnBorrar.setForeground(new java.awt.Color(255, 255, 255));
        btnBorrar.setText("Borrar");
        btnBorrar.setEnabled(false);
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        comboCliente.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        comboCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel15.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel15.setText("Ventas Exentas");

        txtIva.setEditable(false);
        txtIva.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00;(¤#,##0.00)"))));
        txtIva.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

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

        jLabel10.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel10.setText("Nota de Rem Ant");

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

        jLabel11.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel11.setText("Sumas");

        jLabel12.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel12.setText("13% IVA");

        txtIvaRete.setEditable(false);
        txtIvaRete.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtVentaExe.setEditable(false);
        txtVentaExe.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        btnListaFacturas.setBackground(new java.awt.Color(11, 104, 204));
        btnListaFacturas.setFont(new java.awt.Font("Gadugi", 0, 20)); // NOI18N
        btnListaFacturas.setForeground(new java.awt.Color(255, 255, 255));
        btnListaFacturas.setText("Lista");
        btnListaFacturas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListaFacturasActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(11, 104, 204));
        btnEliminar.setFont(new java.awt.Font("Gadugi", 0, 18)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.setEnabled(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnLimpiar.setBackground(new java.awt.Color(11, 104, 204));
        btnLimpiar.setFont(new java.awt.Font("Gadugi", 0, 18)); // NOI18N
        btnLimpiar.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jLabel18)
                                                    .addGap(18, 18, 18))
                                                .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtNotaRemision, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel17)
                                            .addGap(18, 18, 18)
                                            .addComponent(txtACuentaDe, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnAgregar)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnBorrar)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel1))
                                .addGap(23, 23, 23)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comboCliente, 0, 287, Short.MAX_VALUE)
                                    .addComponent(comboCondicion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnModificar)
                                .addGap(18, 18, 18)
                                .addComponent(btnEliminar)
                                .addGap(114, 114, 114)
                                .addComponent(btnLimpiar)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnCalcular, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
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
                                                .addComponent(txtVentaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnListaFacturas, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtNoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(156, 156, 156)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(comboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(comboCondicion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnListaFacturas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCalcular, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAgregar)
                            .addComponent(btnBorrar)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtNotaRemision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtACuentaDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnModificar)
                            .addComponent(btnEliminar)
                            .addComponent(btnLimpiar)))
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
                            .addComponent(txtVentaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularActionPerformed
        // TODO add your handling code here:
        calcularSuma();
    }//GEN-LAST:event_btnCalcularActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        //Modificamos la factura
        gestion("modificar");
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // Agrega una fila a la tabla
        Object[] fila = new Object[4];
        modeloDetalle.addRow(fila);
        btnBorrar.setEnabled(true);
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        // TODO add your handling code here:
        if (modeloDetalle.getRowCount() > 0) {
            modeloDetalle.removeRow(modeloDetalle.getRowCount() - 1);
            if (modeloDetalle.getRowCount() == 0) {
                btnBorrar.setEnabled(false);
            }
        }
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void txtSumasInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtSumasInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSumasInputMethodTextChanged

    private void btnListaFacturasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListaFacturasActionPerformed
        // Abrimos la lista de facturas
        FrmListaFacturas frmListaFacturas = new FrmListaFacturas();
        escritorioPane.add(frmListaFacturas);
        frmListaFacturas.show();

        //Diseno, se agrega un color customizado para el fondo.
        Color nColor = new Color(248, 249, 252);
        frmListaFacturas.getContentPane().setBackground(nColor);
    }//GEN-LAST:event_btnListaFacturasActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // Eliminamos la factura
        gestion("eliminar");
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        // Se colocan los datos extraidos de la lista
        llenarCampos();
    }//GEN-LAST:event_formInternalFrameActivated

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // Limpiamos los campos
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnCalcular;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnListaFacturas;
    private javax.swing.JButton btnModificar;
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
    private javax.swing.JTable tblDetalleFactura;
    private javax.swing.JTextField txtACuentaDe;
    private javax.swing.JFormattedTextField txtFecha;
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
