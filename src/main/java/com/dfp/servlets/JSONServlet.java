package com.dfp.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;

import com.dfp.business.ExtraeDatosReclamacionDesdeRequest;
import com.dfp.core.dto.ReclamacionDTO;
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
    
    
 
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        try {
//            // Begin unit of work
//            HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
//
//            // Process request and render page...
//
//            // End unit of work
//            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        	Reclamacion reclamacion = new Reclamacion();
        	reclamacion.setTextoReclamacion("sssssssssss");
        	reclamacionDao.persist(reclamacion);
        	
        	List<Reclamacion> oList  = reclamacionDao.findAll();
        	
        	for(Reclamacion recla :oList){
        		System.out.println(recla.getTextoReclamacion());
        	}
        	
        }
        catch (Exception ex) {
        	
        	System.out.println("Exception "+ex.getMessage());
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
            if ( ServletException.class.isInstance( ex ) ) {
                throw ( ServletException ) ex;
            }
            else {
                throw new ServletException( ex );
            }
        }
    	
    }
    
    
    
    
    /***************************************************
     * URL: /jsonservlet
     * doPost(): receives JSON data, parse it, map it and send back as JSON
     ****************************************************/
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{    
    	 BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	      /*  String json = "";
	        if(br != null){
	            json = br.readLine();
	        }
	        ReclamacionDTO reclamacionDTO = null;
	        ObjectMapper mapper = new ObjectMapper();
	        if(json!=null){
               reclamacionDTO = mapper.readValue(json, ReclamacionDTO.class);
	        }*/
    	
    	if (request.getParameter("caso").equals("1")){   
//    		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        String json = "";
	        if(br != null){
	            json = br.readLine();
	        }	        
	        ObjectMapper mapper = new ObjectMapper();            
        	ReclamacionDTO reclamacionDTO = mapper.readValue(json, ReclamacionDTO.class);        
    		String codigoReclamacion = ExtraeDatosReclamacionDesdeRequest.insertaDatosReclamacion(reclamacionDTO ,this.ac);
    		  
    		response.setContentType("application/json");
    		mapper.writeValue(response.getOutputStream(), codigoReclamacion);
//    		insertaDatosReclamacion(request, response);
    	}
    	else if (request.getParameter("caso").equals("2")){
    		Boolean result = ExtraeDatosReclamacionDesdeRequest.insertaAdjuntosReclamacion(request, response,this.ac);
    		request.getParameter("codigoReclamacion");
    		
    		response.sendRedirect("./reclamacion/Reclamacion4.html?resultEnvio="+result);
    	}
    	//response.sendRedirect("http://www.defensadelpasajero.com/");
    }



	
}