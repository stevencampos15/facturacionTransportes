package com.app.dao;

import com.app.conexion.ConexionBD;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.*;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Steven
 */
public class GenerarReporte {

    private ConexionBD conexion = new ConexionBD();
    private JasperReport reporte = null;
    private String path;

    public void reporteFactura(int noFactura) {
        try {
            Connection cn = this.conexion.conectar();
            path = "src\\com\\app\\reportes\\Reporte.jasper";
            Map parametro = new HashMap();
            parametro.put("noFactura", noFactura);

            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/com/app/reportes/Reporte.jasper"));
            JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, cn);
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
//            InputStream reportStream = GenerarReporte.class.getResourceAsStream(path);
//            if (reportStream == null) {
//                path = "src/com/app/reportes/Reporte.jrxml";
//                InputStream input = new FileInputStream(path);
//                //reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
//                JasperDesign jasperDesign = JRXmlLoader.load(input);
//                JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
//                JasperPrint jprint = JasperFillManager.fillReport(jasperReport, parametro, cn);
//                JasperViewer view = new JasperViewer(jprint, false);
//                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//                view.setVisible(true);
//            } else {
//                reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
//                JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, cn);
//                JasperViewer view = new JasperViewer(jprint, false);
//                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//                view.setVisible(true);
//            }

        } catch (Exception e) {
            //Logger.getLogger(GenerarReporte.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            this.conexion.desconectar();
        }
    }

}
