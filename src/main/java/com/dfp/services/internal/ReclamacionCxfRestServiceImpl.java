package com.dfp.services.internal;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.dfp.core.MailService;
import com.dfp.core.MailServiceImpl;
import com.dfp.core.StringKeys;
import com.dfp.core.dto.ReclamacionDTO;
import com.dfp.persistence.dao.EstadoDao;
import com.dfp.persistence.dao.ReclamacionDao;
import com.dfp.persistencia.entities.Estado;
import com.dfp.persistencia.entities.Reclamacion;
import com.dfp.services.ReclamacionCxfRestService;
import com.dfp.utiles.hibernate.HibernateUtil;

public class ReclamacionCxfRestServiceImpl implements ReclamacionCxfRestService {
	
//	private static final Logger log = Logger.getLogger(ReclamacionCxfRestServiceImpl.class.getName());

	@Autowired
	private ReclamacionDao reclamacionDao;
	
	@Autowired
	private EstadoDao estadoDao;
	
	@Autowired
	private ApplicationContext appContext;

	public Response getClaimDetail(String claimId) {
	    Session session =HibernateUtil.getSessionFactory().getCurrentSession();	        
	    session.beginTransaction();
	    
	    Reclamacion claim = new Reclamacion();		
	    claim.setId(new Integer(claimId));	    
	    claim = reclamacionDao.getReclamacionByExample(claim);
	    
	    session.getTransaction().commit();
	    ReclamacionDTO claimDTO = new ReclamacionDTO();
	    claimDTO.populateFromEntity(claim);
	    
	    return Response.ok(claimDTO).build();
	}
	
	@Override
	public Response getClaimDetails(String arrayReclamaciones) {
	    Session session =HibernateUtil.getSessionFactory().getCurrentSession();	        
	    session.beginTransaction();
	    
	    List oListReclamaciones = new ArrayList<ReclamacionDTO>();
	    String[] array =  arrayReclamaciones.split(",");
	    
	    for(String sClaim :array){
		Reclamacion claim = new Reclamacion();
		claim.setId(new Integer(sClaim));
		
		claim =  reclamacionDao.getReclamacionByExample(claim);
	
			if (claim!=null){
				ReclamacionDTO claimDTO = new ReclamacionDTO();
				claimDTO.populateFromEntity(claim);
				oListReclamaciones.add(claimDTO);
			}
	    }
	    session.getTransaction().commit();
	    return Response.ok(oListReclamaciones).build();
	}
	
	

	public Response listClaims() {
	   Session session =HibernateUtil.getSessionFactory().getCurrentSession();	        
	   session.beginTransaction();
	   List<Reclamacion> oListReclamaciones = reclamacionDao.findAll();
	   List<ReclamacionDTO> oListReclamacionesDTO = new ArrayList<ReclamacionDTO>();
	   
	   for(Reclamacion claim:oListReclamaciones){
		   ReclamacionDTO claimDTO = new ReclamacionDTO();
		   claimDTO.populateFromEntity(claim);
		   oListReclamacionesDTO.add(claimDTO);
	   }		
	   session.getTransaction().commit();
	   return Response.ok(oListReclamacionesDTO).build();
	}

//	public Response insertClaim(ReclamacionDTO claimDTO) {
//		reclamacionDao.openCurrentSessionwithTransaction();
//		Reclamacion claim = new Reclamacion();
//		claim.setTextoReclamacion(claimDTO.getTextoReclamacion());
//		return Response.ok(reclamacionDao.persist(claim)).build();
//	}

	
	private long getTimeStamp(){
		Calendar calendar = Calendar.getInstance(); 
		return calendar.getTimeInMillis();
	}
	
	public Response enviaMail2(@Multipart("note") String note) {
		return Response.ok(new ReclamacionDTO()).build();
	}

	public Boolean enviaMailNuevoEstado(Reclamacion claim, Estado state) {
	    try{
		String sNombreEstado = state.getNombreEstado(); 
		if (state.getNombreEstado().equals(StringKeys.RECLAMADAAEROLINEA))
		    sNombreEstado = "ReclamadaAerolinea";
		    else if (state.getNombreEstado().equals(StringKeys.RECLAMADAUNION))
			sNombreEstado = "ReclamadaComision";
		
		MailService mm = (MailServiceImpl) appContext.getBean("mailReclamacion"+sNombreEstado);	    
		    mm.send(claim.getPasajero().getEmail(), "Nuevo estado en la reclamación::" + claim.getCodigoReclamacion()
			  , null, claim, false);
		    
		mm = (MailServiceImpl) appContext.getBean("mailReclamacion"+sNombreEstado);	    
		    mm.send(StringKeys.mailTecnico, "Nuevo estado en la reclamación::" + claim.getCodigoReclamacion()
			  , null, claim, true);
		    
	    }catch (Exception e) {
		return true;
	    }	    
	    return false;
	}
	
	private Estado getEstadoBySecuence(int iEstado){
	    Estado state = new Estado();
	    
	    state.setSecEstado(iEstado);
	    
	    return estadoDao.getEstadoByExample(state);
	}
	
	private String cambioEstadoBySecuence(String claimId,Integer secuence) {
	    Boolean error= false;
		String sMensaje = "ok";
		if (claimId!=null){
		    try{
			Session session =HibernateUtil.getSessionFactory().getCurrentSession();	        
			session.beginTransaction();
			Reclamacion claim = new Reclamacion();
			
			claim.setId(new Integer(claimId));
			claim = reclamacionDao.getReclamacionByExample(claim);
			
			if (claim==null)
				return "noOK";
			
			if (claim.getEstado().getSecEstado().equals(StringKeys.ULTIMONIVEL)||
					claim.getEstado().getSecEstado().equals(StringKeys.ULTIMONIVELDENEGADA))
				return "La reclamación ya está en su último nivel";
			
			Estado state = null;
			
			if (secuence.equals(1))
			    state = getEstadoBySecuence(claim.getEstado().getSecEstado()+secuence);
			else
			    state = getEstadoBySecuence(secuence);
			
			if (state==null)
				error	= true;
			else{			
    			claim.setEstado(state);
    			error = enviaMailNuevoEstado( claim,  state);
    			if (!error)
    			    claim = reclamacionDao.update(claim);
			}
	
			session.getTransaction().commit();
		    }catch(Exception e){  
			    StringWriter errors = new StringWriter();
			    e.printStackTrace(new PrintWriter(errors));
			    sMensaje = errors.toString();
		    }
		}
	    return sMensaje;
	}

	@Override
	public Response aceptaSiguienteEstadoReclamacion(String claimId) {
		String sMensaje = "ok";
		if (claimId!=null){
			sMensaje = cambioEstadoBySecuence( claimId,1);
			
			return Response.ok(sMensaje).build();
		}else
			return Response.serverError().build();
	}

	@Override
	public Response rechazaSiguienteEstadoReclamacion(String claimId) {	
		String sMensaje = "ok";
		if (claimId!=null){
		    	cambioEstadoBySecuence( claimId,0);
			
			return Response.ok(sMensaje).build();
		}else
			return Response.serverError().build();
	}


	

}
