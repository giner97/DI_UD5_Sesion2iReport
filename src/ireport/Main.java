/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ireport;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

/**
 *
 * @author dam2
 */
public class Main implements JRDataSource{

    private List<Participantes> listaParticipantes = new ArrayList<>();
    private int indiceParticipanteActual = -1;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        
        Main datasource = new Main();
        
        for (int i = 1; i<= 10; i++) {
            
            Participantes p = new Participantes(i, "Particpante " + i, "Usuario "+ i, "Pass " + i, "Comentarios para " + i);
            datasource.addParticipante(p);
        
        } 
        
        try{
            
            JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile("report4.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, null, datasource);
            Exporter exporter = new JRPdfExporter();

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new java.io.File("reporteSesion2PDF.pdf")));
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            exporter.setConfiguration(configuration);

            exporter.exportReport();    
        
        } 
        
        catch (JRException ex) {

            ex.printStackTrace();
            
        }
                
    }   

    @Override
    public boolean next() throws JRException {
        
        return ++indiceParticipanteActual<listaParticipantes.size();
        
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        
        Object valor = null;
        
        if("nombre".equals(jrf.getName())) {
            valor = listaParticipantes.get(indiceParticipanteActual).getNombre();
        }
        else if("username".equals(jrf.getName())) {
            valor = listaParticipantes.get(indiceParticipanteActual).getUsername(); 
        }
        else if("password".equals(jrf.getName())) {
            valor = listaParticipantes.get(indiceParticipanteActual).getPassword();
        }
        else if("comentarios".equals(jrf.getName())) {
            valor = listaParticipantes.get(indiceParticipanteActual).getComentarios();
        }
        else if("id".equals(jrf.getName())){
           valor = listaParticipantes.get(indiceParticipanteActual).getId();
        }
        
        return valor;
        
    }
    
    public void addParticipante(Participantes participante){
        
        this.listaParticipantes.add(participante);
        
    }
        
}
