package com.dfp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;

import com.dfp.business.ExtraeDatosReclamacionDesdeRequest;
import com.dfp.core.StringKeys;
import com.dfp.core.dto.PasajeroDTO;
import com.dfp.core.dto.ReclamacionDTO;
import com.dfp.core.dto.VueloDTO;
import com.dfp.persistence.dao.EstadoDao;
import com.dfp.persistence.dao.ReclamacionDao;
import com.dfp.persistencia.entities.Reclamacion;

public class JSONServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    ReclamacionDao reclamacionDao;

    EstadoDao estadoDao;

    ApplicationContext ac;


    @Override
    public void init(ServletConfig config) throws ServletException {
	super.init(config);
	ac = (ApplicationContext) config.getServletContext().getAttribute("applicationContext");
    }

    /***************************************************
     * URL: /jsonservlet doPost(): receives JSON data, parse it, map it and send
     * back as JSON
     ****************************************************/
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	    IOException {

	try {
	    if (request.getParameter("caso").equals("1")) {
		PasajeroDTO pasajeroDTO = new PasajeroDTO(request.getParameter("nombre"),
			request.getParameter("apellidos"), request.getParameter("email"),
			request.getParameter("telefono"));

		VueloDTO vueloDTO = new VueloDTO(request.getParameter("id-vuelo"),
			request.getParameter("aeropuerto-salida"), request.getParameter("aeropuerto-llegada"));
		Date hSalidaPrevista = null;
		if (request.getParameter("hsalidaprevista")!=null && !request.getParameter("hsalidaprevista").equals("") )
		    hSalidaPrevista = StringKeys.formatter.parse(request.getParameter("hsalidaprevista"));
		
		Date hSalidaReal = null;
		if (request.getParameter("hsalidareal")!=null && !request.getParameter("hsalidareal").equals("") )
		    hSalidaReal = StringKeys.formatter.parse(request.getParameter("hsalidareal"));
		
		Date hLlegadaPrevista = null;
		if (request.getParameter("hllegadaprevista")!=null && !request.getParameter("hllegadaprevista").equals("") )
		    hLlegadaPrevista = StringKeys.formatter.parse(request.getParameter("hllegadaprevista"));
		
		Date hLlegadaReal = null;
		if (request.getParameter("hllegadareal")!=null && !request.getParameter("hllegadareal").equals("") )
		    hLlegadaReal = StringKeys.formatter.parse(request.getParameter("hllegadareal"));
		
		if (request.getParameter("hsalidaprevistacancel")!=null && !request.getParameter("hsalidaprevistacancel").equals("") )
		    hSalidaPrevista = StringKeys.formatter.parse(request.getParameter("hsalidaprevistacancel"));
		

		ReclamacionDTO reclamacionDTO = new ReclamacionDTO(pasajeroDTO, vueloDTO,
			hSalidaPrevista,
			hSalidaReal,
			hLlegadaPrevista,
			hLlegadaReal,
			request.getParameter("comentarios"), request.getParameter("aceptar-condiciones"),
			request.getParameter("codigoReclamacion"));

		Reclamacion reclamacion = ExtraeDatosReclamacionDesdeRequest.insertaDatosReclamacion(reclamacionDTO,
			this.ac);
		
		 response.sendRedirect("http://defensadelpasajero.com/reclamacion/Reclamacion2.html?caso=2&codigoReclamacion="+reclamacion.getCodigoReclamacion());
		//response.sendRedirect("./reclamacion/Reclamacion2.html?caso=2&codigoReclamacion="			+ reclamacion.getCodigoReclamacion());
	    } else if (request.getParameter("caso").equals("2")) {

		Boolean result = ExtraeDatosReclamacionDesdeRequest.insertaAdjuntosReclamacion(request, response,
			this.ac);

		 response.sendRedirect("http://defensadelpasajero.com/reclamacion/Reclamacion4.html?resultEnvio="+result);
		//response.sendRedirect("./reclamacion/Reclamacion4.html?resultEnvio=" + result);

		System.out.println("done");
	    }
	} catch (Exception e) {
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();

	    StringWriter errors = new StringWriter();
	    e.printStackTrace(new PrintWriter(errors));
	    out.println(errors.toString());
	}
    }

}