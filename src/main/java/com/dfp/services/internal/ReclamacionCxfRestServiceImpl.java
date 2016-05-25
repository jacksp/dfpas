package com.dfp.services.internal;

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
	    claim = (reclamacionDao.getReclamacionByExample(claim)).get(0);
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
		claim = (reclamacionDao.getReclamacionByExample(claim)).get(0);
		ReclamacionDTO claimDTO = new ReclamacionDTO();
		claimDTO.populateFromEntity(claim);
		oListReclamaciones.add(claimDTO);
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
			    + "-" + claim.getId(), null, claim, false);
		    
		mm = (MailServiceImpl) appContext.getBean("mailReclamacion"+sNombreEstado);	    
		    mm.send(claim.getPasajero().getEmail(), "Nuevo estado en la reclamación::" + claim.getCodigoReclamacion()
			    + "-" + claim.getId(), null, claim, true);
		    
	    }catch (Exception e) {
		return false;
	    }	    
	    return true;
	}

	@Override
	public Response aceptaSiguienteEstadoReclamacion(String claimId) {
		Boolean error= false;
		if (claimId!=null){
			Session session =HibernateUtil.getSessionFactory().getCurrentSession();	        
			session.beginTransaction();
			Reclamacion claim = new Reclamacion();
			
			claim.setId(new Integer(claimId));
			claim =  (reclamacionDao.getReclamacionByExample(claim)).get(0);
			

			Estado state = new Estado();
			state.setSecEstado(claim.getEstado().getSecEstado()+1);
			
			state = estadoDao.getEstadoByExample(state).get(0);
			
//			Map<Integer,Estado> oMapEstado = estadoDao.hashMapFindAll();
			
//			state = oMapEstado.get(state.getSecEstado());
			
			if (state==null)
				error	= true;
			else{			
        			claim.setEstado(state);
        			error = enviaMailNuevoEstado( claim,  state);
        			claim = reclamacionDao.update(claim);
			}
	
			session.getTransaction().commit();
			
			return Response.ok(error).build();
		}else
			return Response.serverError().build();
	}

	@Override
	public Response rechazaSiguienteEstadoReclamacion(String claimId) {		
		Boolean error= false;
		
		if (claimId!=null){
			Session session =HibernateUtil.getSessionFactory().getCurrentSession();	        
			session.beginTransaction();
			Reclamacion claim = new Reclamacion();
			
			claim.setId(new Integer(claimId));
			claim =  (reclamacionDao.getReclamacionByExample(claim)).get(0);
			Integer oldEstado = claim.getEstado().getSecEstado();
			
	//		Reclamacion reclamacion = reclamacionDao.findById(claimId);
			Estado state = new Estado();
			state.setSecEstado(0);
			
			List<Estado> oListEstado = estadoDao.getEstadoByExample(state);
			
			if (oListEstado!=null && oListEstado.size()==1)
				state = (Estado) oListEstado.get(0);
			else
				error	= true;
						
			claim.setEstado(state);
			
			claim = reclamacionDao.update(claim);
			
			error = enviaMailNuevoEstado( claim,  state);
			session.getTransaction().commit();
			
			return Response.ok(claim).build();
		}else
			return Response.serverError().build();
	}

//	@Override
//	public Response enviaMail(HttpServletRequest request) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
//	@Override
//	@POST
//	public Response enviaMail(@Context HttpServletRequest request) {
//		ServletFileUpload upload = new ServletFileUpload();
//		String sResultado = "";
////		log.info("Entrada de un email");
//		System.out.println("Entrada de un email");
//		// log.debug("Entrada de un email"+request.getParameter("photo"));
////		EntityManager em  = EMFService.get().createEntityManager();
//		try {
//			FileItemIterator fileIterator = upload.getItemIterator(request);
//			String email = "";
//			String msgBody = "";
//			String textarea = "";
//			String telf = "";
//			String calendario = "";
//			byte[] content = null;
//			int error = 0;
//
//			MailUtil oMailUtil = new MailUtil();
//
//			while (fileIterator.hasNext()) {
//
//				FileItemStream item = fileIterator.next();
//				if ("file".equals(item.getFieldName())) {
//					System.out.println("uploadedFile leido");
//					content = IOUtils.toByteArray(item.openStream());
//					System.out.println("uploadedFile leido" + content);
//					// Save content into datastore
//					// ...
//				} else if ("email".equals(item.getFieldName())) {
//					email = IOUtils.toString(item.openStream());
//					System.out.println("email leido" + email);
//					// Do something with the name string
//					// ...
//				} else if ("textarea".equals(item.getFieldName())) {
//					textarea = IOUtils.toString(item.openStream());
//
//				} else if ("calendario".equals(item.getFieldName())) {
//					calendario = IOUtils.toString(item.openStream());
//				} else if ("telf".equals(item.getFieldName())) {
//					telf = IOUtils.toString(item.openStream());
//				}
//			}
//
//			// construct our entity objects
////			Blob imageBlob = new Blob(content);
//			String sTimeStamp = email + getTimeStamp();
////			BilleteImagen myImage = new BilleteImagen(sTimeStamp, imageBlob);
////			
////			em.persist(myImage);
//			
//			String sEmailPeticionPresupuesto = TextoEmail.textoCorreoPresupuesto;
//			sEmailPeticionPresupuesto = sEmailPeticionPresupuesto.replace("###MSGBODY###",
//					email + " <br> " + telf + " <br>  " + calendario + " <br> Observaciones:  <br> " + textarea
//							+ "<br><a href=\"http://multasradar4.appspot.com/upload?email=" + sTimeStamp
//							+ "\" >Ver multa foto</a>");
//			sResultado = oMailUtil.envioCorreo1(email, sEmailPeticionPresupuesto);
//
//		} catch (FileUploadException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}catch(Exception e){
//			sResultado = "error->>";
//        }finally {
//           
//        }	
//		Reclamacion claim = new Reclamacion();
//		claim.setTextoReclamacion("");
//		ReclamacionDTO claimDTO = new ReclamacionDTO();
//		claim.populateFromReclamacionDTO(claimDTO);
//		int iClaim = reclamacionDao.persist(claim);
//		
//		return Response.ok(claim).build();
//	}

	

}
