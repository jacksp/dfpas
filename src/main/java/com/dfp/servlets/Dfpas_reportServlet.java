package com.dfp.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;

import com.dfp.persistence.dao.ReclamacionDao;
import com.dfp.persistencia.entities.Reclamacion;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

@SuppressWarnings("serial")
public class Dfpas_reportServlet extends HttpServlet {
	String salida;
	
	
    ReclamacionDao reclamacionDao;
    

  ApplicationContext ac;

  DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
  
  @Override
  public void init(ServletConfig config) throws ServletException {
     super.init(config);
     ac = (ApplicationContext) config.getServletContext().getAttribute("applicationContext");
     reclamacionDao = (ReclamacionDao) ac.getBean("reclamacionDao");
  
  }
  
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

			 String jrxmlFileName =   System.getenv("OPENSHIFT_DATA_DIR");
             jrxmlFileName = jrxmlFileName+"/jasper/reclamacionesAena.jasper";
            File archivoReporte = new File(jrxmlFileName);
            HashMap hm = null;
            hm = new HashMap();
            
            String idClaim = request.getParameter("idClaim");
            
            Reclamacion reclamacion = new Reclamacion();
            reclamacion.setId(new Integer(idClaim));
            
            reclamacion = reclamacionDao.getReclamacionByExample(reclamacion).get(0);
 
            ServletOutputStream servletOutputStream = response.getOutputStream();
            
            hm.put("aeropuerto_origen", reclamacion.getPasajero().getVuelo().getAeropuertoOrigen());
            hm.put("aeropuerto_destino", reclamacion.getPasajero().getVuelo().getAeropuertoDestino());
            hm.put("codigo_vuelo", reclamacion.getPasajero().getVuelo().getCodigoVuelo());
            hm.put("nombre_completo", reclamacion.getPasajero().getNombre()+" "+ reclamacion.getPasajero().getApellidos());
            hm.put("fecha_prevista", df.format(reclamacion.getHoraInicioVueloPrevista())+" "+df.format(reclamacion.getHoraFinVueloPrevista()));
            hm.put("fecha_real", df.format(reclamacion.getHoraInicioVueloReal())+" "+ df.format(reclamacion.getHoraFinVueloReal()));
            hm.put("comentarios", reclamacion.getTextoReclamacion());
            
 
            byte[] bytes = null;
 
            try {
                bytes = JasperRunManager.runReportToPdf(archivoReporte.getPath(), hm, new JREmptyDataSource());
 
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                servletOutputStream.write(bytes, 0, bytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
            } catch (JRException e) {
                StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter);
                e.printStackTrace(printWriter);
                response.setContentType("text/plain");
                response.getOutputStream().print(stringWriter.toString());
            }
	}
}
