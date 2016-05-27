package com.dfp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

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
import com.dfp.utiles.hibernate.HibernateUtil;
 
//import com.fasterxml.jackson.databind.ObjectMapper;
 
public class JSONServlet extends HttpServlet {
 
    private static final long serialVersionUID = 1L;
    
    ReclamacionDao reclamacionDao;
    
//    PasajeroDao pasajeroDao;
//    
//    VueloDao vueloDao;
//    
    EstadoDao estadoDao;
    
    ApplicationContext ac;
 
    // This will store all received articles
//    List<Article> articles = new LinkedList<Article>();
    
    @Override
    public void init(ServletConfig config) throws ServletException {
       super.init(config);
       ac = (ApplicationContext) config.getServletContext().getAttribute("applicationContext");
    }
    
    
 
    

    
    
    
    
    /***************************************************
     * URL: /jsonservlet
     * doPost(): receives JSON data, parse it, map it and send back as JSON
     ****************************************************/
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
	
	try{
        	//BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        	if (request.getParameter("caso").equals("1")){   
    //    		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
//    	        String json = null;
//    	        json.length();
//    	        if(br != null){
//    	            json = br.readLine();
//    	        }	        
//    	        	ObjectMapper mapper = new ObjectMapper();   
    	        	PasajeroDTO pasajeroDTO = new PasajeroDTO(
    	        		request.getParameter("nombre")
            			,request.getParameter("apellidos")
            			,request.getParameter("email")
            			,request.getParameter("telefono")
    	        		);
    	        	
    	        	VueloDTO vueloDTO = new VueloDTO(
    	        		request.getParameter("id-vuelo")
            			,request.getParameter("aeropuerto-salida")
            			,request.getParameter("aeropuerto-llegada")
    	        		);
    	        	Date hsalidaprevista = new Date();
    	        	if (request.getParameter("hsalidaprevista")!=null  && !request.getParameter("hsalidaprevista").equals("") )
    	        		 hsalidaprevista = StringKeys.formatter.parse(request.getParameter("hsalidaprevista"));
    	        	
    	        	Date hsalidareal = new Date();
    	        	if (request.getParameter("hsalidareal")!=null  && !request.getParameter("hsalidareal").equals(""))
    	        		hsalidareal = StringKeys.formatter.parse(request.getParameter("hsalidareal"));
    	        	
    	        	Date hllegadaprevista = new Date();
    	        	if (request.getParameter("hllegadaprevista")!=null  && !request.getParameter("hllegadaprevista").equals(""))
    	        		hllegadaprevista = StringKeys.formatter.parse(request.getParameter("hllegadaprevista"));
    	        	
    	        	Date hllegadareal = new Date();
    	        	if (request.getParameter("hllegadareal")!=null && !request.getParameter("hllegadareal").equals(""))
    	        		hllegadareal = StringKeys.formatter.parse(request.getParameter("hllegadareal"));

    	        	
            		ReclamacionDTO reclamacionDTO = new ReclamacionDTO(pasajeroDTO,vueloDTO
            			,hsalidaprevista
            			,hsalidareal
            			,hllegadaprevista
            			,hllegadareal
            			,request.getParameter("comentarios"),request.getParameter("aceptar-condiciones")
            			,request.getParameter("codigoReclamacion") );
            		
            		
            		
        		String codigoReclamacion = ExtraeDatosReclamacionDesdeRequest.insertaDatosReclamacion(reclamacionDTO ,this.ac);
        		 //respuesta con cors habilitado 
        		//response.setContentType("application/json");
        		//mapper.writeValue(response.getOutputStream(), codigoReclamacion);
        		
        		response.sendRedirect(StringKeys.urlBase+"/reclamacion/Reclamacion2.html?caso=2&codigoReclamacion="+codigoReclamacion);
        	}
        	else if (request.getParameter("caso").equals("2")){
        		Boolean result = ExtraeDatosReclamacionDesdeRequest.insertaAdjuntosReclamacion(request, response,this.ac);
//        		request.getParameter("codigoReclamacion");
        		
        		response.sendRedirect(StringKeys.urlBase+"/reclamacion/Reclamacion4.html?resultEnvio="+result);
        	}
	}catch (Exception e) {
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();

	  
	    StringWriter errors = new StringWriter();
	    e.printStackTrace(new PrintWriter(errors));
	    out.println(errors.toString());
	}
    }



	
}