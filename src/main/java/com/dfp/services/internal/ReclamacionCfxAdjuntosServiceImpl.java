package com.dfp.services.internal;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.dfp.business.ExtraeDatosReclamacionDesdeRequest;
import com.dfp.core.MailService;
import com.dfp.core.MailServiceImpl;
import com.dfp.core.StringKeys;
import com.dfp.core.dto.PasajeroDTO;
import com.dfp.core.dto.ReclamacionDTO;
import com.dfp.core.dto.VueloDTO;
import com.dfp.persistence.dao.ReclamacionDao;
import com.dfp.persistencia.entities.Reclamacion;
import com.dfp.services.ReclamacionCxfAdjuntosService;
import com.dfp.utiles.FileUtils;
import com.dfp.utiles.hibernate.HibernateUtil;

@Path("/")
@WebService(name = "claimService")
public class ReclamacionCfxAdjuntosServiceImpl implements ReclamacionCxfAdjuntosService {
	
	
	@Autowired
	private ReclamacionDao reclamacionDao;

    @Autowired
    private ApplicationContext appContext;

    // add the attribute to your implementation
    @Context
    private MessageContext context;
    
    
    private static String[] concat(String[] a, String[] b) {
	int aLen = a.length;
	int bLen = b.length;
	String[] c = new String[aLen + bLen];
	System.arraycopy(a, 0, c, 0, aLen);
	System.arraycopy(b, 0, c, aLen, bLen);
	return c;
    }

    private static String[] getArrayFromString(String s1) {
	return concat(s1.split(",")[0].trim().split(""), s1.split(",")[1].trim().split(":"));
    }

    private static Date sumaDateAlRetraso(Date oDate, String[] array) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(oDate);
	cal.add(Calendar.DAY_OF_YEAR, new Integer(array[1].trim().replaceFirst("^0+(?!$)", "")));
	cal.add(Calendar.HOUR, new Integer(array[2].trim().replaceFirst("^0+(?!$)", "")));
	cal.add(Calendar.MINUTE, new Integer(array[3].trim().replaceFirst("^0+(?!$)", "")));

	return cal.getTime();
    }
    
    
    private ReclamacionDTO procesarFechas(ReclamacionDTO oReclamacion,Map<String, String> oMapTimes) throws ParseException{
	
	 // params desde el movil
	    String sDateSalidaPrevista = (String) oMapTimes.get(StringKeys.DATESALIDAPREVISTA);
	    String sHoraSalidaPrevista = (String) oMapTimes.get(StringKeys.HORASALIDAPREVISTA);

	    String sDateLlegadaPrevista = (String) oMapTimes.get(StringKeys.DATELLEGADAPREVISTA);
	    String sHoraLlegadaPrevista = (String) oMapTimes.get(StringKeys.HORALLEGADAPREVISTA);

	    String sRetrasoSalida = (String) oMapTimes.get(StringKeys.RETRASOSALIDA);
	    String sRetrasoLlegada = (String) oMapTimes.get(StringKeys.RETRASOLLEGADA);

	    Locale oLocale = new Locale((String) oMapTimes.get(StringKeys.LOCALEPHONE));

	    // Fecha hora prevista
	    String sFechaHoraSalidaPrevista = sDateSalidaPrevista + " " + sHoraSalidaPrevista;
	    String sFechaHoraLlegadaPrevista = sDateLlegadaPrevista + " " + sHoraLlegadaPrevista;

	    Date oDateHoraSalidaPrevista = new SimpleDateFormat(StringKeys.sformatter, oLocale).parse(sFechaHoraSalidaPrevista);
	    Date oDateHoraSalidaLlegadaPrevista = new SimpleDateFormat(StringKeys.sformatter, oLocale).parse(sFechaHoraLlegadaPrevista);

	    // la suma de la duración del retraso

	    // limpiamos las cadenas
	    for (int i = 0; i < StringKeys.STRINGLABELSDURATION.length; i++) {
		sRetrasoSalida = sRetrasoSalida.replaceAll(StringKeys.STRINGLABELSDURATION[i], "");
		sRetrasoLlegada = sRetrasoLlegada.replaceAll(StringKeys.STRINGLABELSDURATION[i], "");
	    }

	    // Hayo las fechas horas reales
	    String[] aRetrasoSalida = getArrayFromString(sRetrasoSalida);
	    String[] aRetrasoLlegada = getArrayFromString(sRetrasoLlegada);

	    if (aRetrasoSalida.length > 1) {
		oReclamacion.setHoraInicioVueloPrevista(oDateHoraSalidaPrevista);
		oReclamacion.setHoraFinVueloPrevista(oDateHoraSalidaLlegadaPrevista);
		
		oReclamacion.setHoraInicioVueloReal(sumaDateAlRetraso(oDateHoraSalidaPrevista, aRetrasoSalida));
		oReclamacion.setHoraFinVueloReal(sumaDateAlRetraso(oDateHoraSalidaLlegadaPrevista, aRetrasoLlegada));
		
//		System.out.println(oDateHoraSalidaPrevista);
//		System.out.println(oDateHoraSalidaLlegadaPrevista);
//		System.out.println(sumaDateAlRetraso(oDateHoraSalidaPrevista, aRetrasoSalida));
//		System.out.println(sumaDateAlRetraso(oDateHoraSalidaLlegadaPrevista, aRetrasoLlegada));
	    }	
	return oReclamacion;
    }

    @Override
    public Response enviaMail() {
	ServletFileUpload upload = new ServletFileUpload();
	System.out.println("Entrada de un email");
	ReclamacionDTO reclamacion = new ReclamacionDTO();
	PasajeroDTO pasajero = new PasajeroDTO();
	VueloDTO vuelo = new VueloDTO();
	Map<String,String> oMapTimes = new HashMap<String,String>();
	Boolean error = false;
	HttpServletRequest req = context.getHttpServletRequest();
	try {
	    FileItemIterator fileIterator = upload.getItemIterator(req);
	    
	    byte[] content = null;
	   
	    while (fileIterator.hasNext()) {
	    	StringWriter writer = new StringWriter();
		FileItemStream item = fileIterator.next();
		if ("file".equals(item.getFieldName())) {
		    content = IOUtils.toByteArray(item.openStream());
		} else if ("email".equals(item.getFieldName())) {
		    IOUtils.copy(item.openStream(), writer, StandardCharsets.UTF_8);
		    pasajero.setEmail(writer.toString());
		} else if ("apellidos".equals(item.getFieldName())) {
		    IOUtils.copy(item.openStream(), writer, StandardCharsets.UTF_8);
		    pasajero.setApellidos(writer.toString());
		} else if ("nombre".equals(item.getFieldName())) {
		    IOUtils.copy(item.openStream(), writer, StandardCharsets.UTF_8);
		    pasajero.setNombre(writer.toString());
		} else if ("telefono".equals(item.getFieldName())) {
		    IOUtils.copy(item.openStream(), writer, StandardCharsets.UTF_8);
		    pasajero.setTelefono(writer.toString());
		} else if ("idvuelo".equals(item.getFieldName())) {
		    IOUtils.copy(item.openStream(), writer, StandardCharsets.UTF_8);
		    vuelo.setCodigoVuelo(writer.toString());
		} else if ("aeropuertosalida".equals(item.getFieldName())) {
		    IOUtils.copy(item.openStream(), writer, StandardCharsets.UTF_8);
		    vuelo.setAeropuertoOrigen(writer.toString());
		} 
		else if ("AeropuertoLlegada".equals(item.getFieldName())) {
		    IOUtils.copy(item.openStream(), writer, StandardCharsets.UTF_8);
		    vuelo.setAeropuertoDestino(writer.toString());
		} 
		
		else if ("observaciones".equals(item.getFieldName())) {
		    IOUtils.copy(item.openStream(), writer, StandardCharsets.UTF_8);		    
		    reclamacion.setTextoReclamacion(writer.toString());
		} else if ("casoReclamacion".equals(item.getFieldName())) {
		    IOUtils.copy(item.openStream(), writer, StandardCharsets.UTF_8);
		    reclamacion.setCodigoReclamacion(writer.toString());
		} else if (StringKeys.RETRASOSALIDA.equals(item.getFieldName())) {
		    IOUtils.copy(item.openStream(), writer, StandardCharsets.UTF_8);
		    oMapTimes.put(StringKeys.RETRASOSALIDA, writer.toString());
		}  else if (StringKeys.HORASALIDAPREVISTA.equals(item.getFieldName())) {
		    IOUtils.copy(item.openStream(), writer, StandardCharsets.UTF_8);
		    oMapTimes.put(StringKeys.HORASALIDAPREVISTA, writer.toString());
		} else if (StringKeys.DATESALIDAPREVISTA.equals(item.getFieldName())) {
		    IOUtils.copy(item.openStream(), writer, StandardCharsets.UTF_8);
		    oMapTimes.put(StringKeys.DATESALIDAPREVISTA, writer.toString());
		} else if (StringKeys.RETRASOLLEGADA.equals(item.getFieldName())) {
		    IOUtils.copy(item.openStream(), writer, StandardCharsets.UTF_8);
		    oMapTimes.put(StringKeys.RETRASOLLEGADA, writer.toString());
		} else if (StringKeys.HORALLEGADAPREVISTA.equals(item.getFieldName())) {
		    IOUtils.copy(item.openStream(), writer, StandardCharsets.UTF_8);
		    oMapTimes.put(StringKeys.HORALLEGADAPREVISTA, writer.toString());
		} else if (StringKeys.LOCALEPHONE.equals(item.getFieldName())) {
		    IOUtils.copy(item.openStream(), writer, StandardCharsets.UTF_8);
		    oMapTimes.put(StringKeys.LOCALEPHONE, writer.toString());
		} else if (StringKeys.DATELLEGADAPREVISTA.equals(item.getFieldName())) {
		    IOUtils.copy(item.openStream(), writer, StandardCharsets.UTF_8);
		    oMapTimes.put(StringKeys.DATELLEGADAPREVISTA, writer.toString());
		} 
	    }
	    
	    
	    //Calculos de los dates del dto de la reclamación
	    
	    
//	    oMapTimes.put(StringKeys.HORASALIDAPREVISTA, "01:33 PM");
//	    oMapTimes.put(StringKeys.DATESALIDAPREVISTA, "01/06/2016");
//	    oMapTimes.put(StringKeys.DATELLEGADAPREVISTA, "03/06/2016");
//	    oMapTimes.put(StringKeys.RETRASOSALIDA, "0 Días, 00:00:00");
//
//	    oMapTimes.put(StringKeys.RETRASOLLEGADA, "4 Días, 04:01:00");
//	    oMapTimes.put(StringKeys.HORALLEGADAPREVISTA, "05:33 PM");
//	    oMapTimes.put(StringKeys.LOCALEPHONE, "ES");
	    
	    try{
		if (oMapTimes.size()>1)
		    reclamacion = procesarFechas( reclamacion, oMapTimes);
		else 
		    reclamacion
		    .setHoraInicioVueloPrevista(new SimpleDateFormat(StringKeys.sformatter,new Locale((String) oMapTimes.get(StringKeys.LOCALEPHONE)))
		    .parse((String) oMapTimes.get(StringKeys.HORASALIDAPREVISTA)));
	    }catch (Exception e) {
		error = true;
	    }
	    
	    

	    //Calculos de los dates del dto de la reclamación
	    
	    reclamacion.setPasajero(pasajero);
	    reclamacion.setVuelo(vuelo);

	    Reclamacion oReclamacion = ExtraeDatosReclamacionDesdeRequest.insertaDatosReclamacion(reclamacion, appContext);
	    
	    File oFile = FileUtils.toFileFromByteArray(content);
	    List<File> attachments = new LinkedList<File>();
	    attachments.add(oFile);
//	    Reclamacion oReclamacion = new Reclamacion();
//	    oReclamacion.populateFromReclamacionDTO(reclamacion);
	    ExtraeDatosReclamacionDesdeRequest.envioMailConAdjuntos(attachments, oReclamacion, appContext);
	    
	    if (error){
		MailService mm = (MailServiceImpl) appContext.getBean("mailReclamacionRecibidaAdjuntos");	
		mm.send(StringKeys.mailTecnico, "Nueva reclamación::" + oReclamacion.getCodigoReclamacion() , null, oReclamacion, true);
	    }
	    

	} catch (FileUploadException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {

	}
	Reclamacion claim = new Reclamacion();
	claim.setTextoReclamacion("");
	ReclamacionDTO claimDTO = new ReclamacionDTO();
	claim.populateFromReclamacionDTO(claimDTO);
	// int iClaim = reclamacionDao.persist(claim);

	return Response.ok(null).build();
    }
    
    
	@Override
	public Response getClaimDetails()  {
		ServletFileUpload upload = new ServletFileUpload();
		String arrayReclamaciones="";
		HttpServletRequest req = context.getHttpServletRequest();
		 FileItemIterator fileIterator;
		  ReclamacionDTO claimDTO = new ReclamacionDTO();
	//	 List oListReclamaciones = new ArrayList<ReclamacionDTO>();
		 String codigoReclamacion = "";
		 String emailCodigoReclamacion = "";
		 
		 String json = "";
		try {
			fileIterator = upload.getItemIterator(req);
		
		    
		    byte[] content = null;
		   
		while (fileIterator.hasNext()) {
		    	StringWriter writer = new StringWriter();
			FileItemStream item = fileIterator.next();
			if ("referenciaProgresoReclamacion".equals(item.getFieldName())) {
			    IOUtils.copy(item.openStream(), writer, StandardCharsets.UTF_8);
			    codigoReclamacion=(writer.toString());
			} else if ("referenciaEmailProgresoReclamacion".equals(item.getFieldName())) {
			    IOUtils.copy(item.openStream(), writer, StandardCharsets.UTF_8);
			    emailCodigoReclamacion=(writer.toString());
			} 
		 }
		
		
		
	    Session session =HibernateUtil.getSessionFactory().getCurrentSession();	        
	    session.beginTransaction();
	    
	    
	    String[] array =  codigoReclamacion.split("-");
	    
	  
	    
	    if (array.length==2){	    	
			Reclamacion claim = new Reclamacion();
			claim.setId(new Integer(array[1]));
			claim = (reclamacionDao.getReclamacionByExample(claim)).get(0);
			
			claimDTO.populateFromEntity(claim);
	    }
	    
	    session.getTransaction().commit();
	    
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
	    try {
			 json = ow.writeValueAsString(claimDTO.getEstadoDTO());
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return Response.ok(json).build();
	}


}
