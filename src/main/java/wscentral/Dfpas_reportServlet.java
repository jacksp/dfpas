package wscentral;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

@SuppressWarnings("serial")
public class Dfpas_reportServlet extends HttpServlet {
	String salida;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			 String jrxmlFileName = this.getServletConfig().getServletContext().getRealPath("//formatosjasper//reclamacionesAena1.jasper");
//			String s = request.getServletPath();
//            String jrxmlFileName = s+"//formatosjasper//reclamacionesAena.jasper";
            File archivoReporte = new File(jrxmlFileName);
            HashMap hm = null;
            hm = new HashMap();
 
            ServletOutputStream servletOutputStream = response.getOutputStream();
 
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
        } catch (Exception e) {
            salida = "Error generando Reporte Jasper, el error del Sistema es " + e;
            System.out.println(salida);
        }
	}
}
