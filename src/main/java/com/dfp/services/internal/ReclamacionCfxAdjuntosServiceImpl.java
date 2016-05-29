package com.dfp.services.internal;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.dfp.business.ExtraeDatosReclamacionDesdeRequest;
import com.dfp.core.StringKeys;
import com.dfp.core.dto.PasajeroDTO;
import com.dfp.core.dto.ReclamacionDTO;
import com.dfp.core.dto.VueloDTO;
import com.dfp.persistencia.entities.Reclamacion;
import com.dfp.services.ReclamacionCxfAdjuntosService;
import com.dfp.utiles.FileUtils;

@Path("/")
@WebService(name = "claimService")
public class ReclamacionCfxAdjuntosServiceImpl implements ReclamacionCxfAdjuntosService {

    @Autowired
    private ApplicationContext appContext;

    // add the attribute to your implementation
    @Context
    private MessageContext context;


    @Override
    public Response enviaMail() {
	ServletFileUpload upload = new ServletFileUpload();
	System.out.println("Entrada de un email");
	ReclamacionDTO reclamacion = new ReclamacionDTO();
	PasajeroDTO pasajero = new PasajeroDTO();
	VueloDTO vuelo = new VueloDTO();

	HttpServletRequest req = context.getHttpServletRequest();
	try {
	    FileItemIterator fileIterator = upload.getItemIterator(req);

	    byte[] content = null;
	    StringWriter writer = new StringWriter();
	    while (fileIterator.hasNext()) {

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
		    pasajero.setEmail(writer.toString());
		} else if ("aeropuertosalida".equals(item.getFieldName())) {
		    IOUtils.copy(item.openStream(), writer, StandardCharsets.UTF_8);
		    vuelo.setAeropuertoOrigen(writer.toString());
		} 
//		else if ("retrasosalida".equals(item.getFieldName())) {
//		    IOUtils.copy(item.openStream(), writer, StandardCharsets.UTF_8);
//		    reclamacion.setHoraInicioVueloReal(StringKeys.formatter.parse(writer.toString()));
//		} else if ("horasalidaprevista".equals(item.getFieldName())) {
//		    IOUtils.copy(item.openStream(), writer, StandardCharsets.UTF_8);
//		    reclamacion.setHoraInicioVueloPrevista(StringKeys.formatter.parse(writer.toString()));
//		} 
		else if ("AeropuertoLlegada".equals(item.getFieldName())) {
		    IOUtils.copy(item.openStream(), writer, StandardCharsets.UTF_8);
		    vuelo.setAeropuertoDestino(writer.toString());
		} 
//	    }else if ("retrasollegada".equals(item.getFieldName())) {
//		    IOUtils.copy(item.openStream(), writer, StandardCharsets.UTF_8);
//		    reclamacion.setHoraFinVueloReal(StringKeys.formatter.parse(writer.toString()));
//		} else if ("horallegadaprevista".equals(item.getFieldName())) {
//		    IOUtils.copy(item.openStream(), writer, StandardCharsets.UTF_8);
//		    reclamacion.setHoraFinVueloPrevista(StringKeys.formatter.parse(writer.toString()));
//		} 
		
		else if ("codigoReclamacion".equals(item.getFieldName())) {
		    IOUtils.copy(item.openStream(), writer, StandardCharsets.UTF_8);
		    reclamacion.setCodigoReclamacion(writer.toString());
		}
	    }
	    
	    reclamacion.setPasajero(pasajero);
	    reclamacion.setVuelo(vuelo);

	    Reclamacion oReclamacion = ExtraeDatosReclamacionDesdeRequest.insertaDatosReclamacion(reclamacion, appContext);
	    
//	    File oFile = FileUtils.toFileFromByteArray(content);
//	    List<File> attachments = new LinkedList<File>();
//	    attachments.add(oFile);
	    
	    
//	    Reclamacion oReclamacion = new Reclamacion();
//	    oReclamacion.populateFromReclamacionDTO(reclamacion);
	    ExtraeDatosReclamacionDesdeRequest.envioMailConAdjuntos(null, oReclamacion, appContext);
	    

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


}
