package com.dfp.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;

import com.dfp.core.dto.ReclamacionDTO;
import com.dfp.persistence.dao.ReclamacionDao;
import com.dfp.persistencia.entities.Reclamacion;
 
//import com.fasterxml.jackson.databind.ObjectMapper;
 
public class JSONServlet extends HttpServlet {
 
    private static final long serialVersionUID = 1L;
    
    ReclamacionDao reclamacionDao;
 
    // This will store all received articles
//    List<Article> articles = new LinkedList<Article>();
    
    @Override
    public void init(ServletConfig config) throws ServletException {
       super.init(config);

       ApplicationContext ac = (ApplicationContext) config.getServletContext().getAttribute("applicationContext");

       this.reclamacionDao = (ReclamacionDao) ac.getBean("reclamacionDao");
    }
 
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
    	 String json = "";
    	
    }
    /***************************************************
     * URL: /jsonservlet
     * doPost(): receives JSON data, parse it, map it and send back as JSON
     ****************************************************/
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
 
        // 1. get received JSON data from request
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        if(br != null){
            json = br.readLine();
        }
 
        // 2. initiate jackson mapper
        ObjectMapper mapper = new ObjectMapper();
 
        // 3. Convert received JSON to Article
        ReclamacionDTO reclamacion = mapper.readValue(json, ReclamacionDTO.class);
 
        // 4. Set response type to JSON
        response.setContentType("application/json");
        
    	reclamacionDao.openCurrentSession();
		Reclamacion claim = new Reclamacion();
		claim.setTextoReclamacion(reclamacion.getTextoReclamacion());
		
		int codigo = reclamacionDao.persist(claim);
		reclamacionDao.closeCurrentSession();
 
        // 5. Add article to List<Article>
       // articles.add(article);
 
        // 6. Send List<Article> as JSON to client
        mapper.writeValue(response.getOutputStream(), codigo);
    }
}