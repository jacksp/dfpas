package com.dfp.business;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
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
import com.dfp.persistencia.entities.Pasajero;
import com.dfp.persistencia.entities.Reclamacion;
import com.dfp.persistencia.entities.Vuelo;
import com.dfp.utiles.FileUtils;
import com.dfp.utiles.hibernate.HibernateUtil;

public class ExtraeDatosReclamacionDesdeRequest {
    
//    @Autowired
//    private static MailServiceImpl mm;

    public static boolean envioMailConAdjuntos(List<File> attachments, Reclamacion oReclamacion, ApplicationContext ac) {
	MailService mm = (MailServiceImpl) ac.getBean("mailReclamacionRecibidaAdjuntos");
	//String text = mm.getText();
	mm.send(oReclamacion.getPasajero().getEmail(), "Nueva reclamación::" + oReclamacion.getCodigoReclamacion()
		+ "-" + oReclamacion.getId(), null, oReclamacion, false);
	//mm.setText(text);
	mm.send(StringKeys.mailTecnico, "Nueva reclamación::" + oReclamacion.getCodigoReclamacion() + "-"
		+ oReclamacion.getId(), null, oReclamacion, true);
	return true;
    }

    public static boolean insertaAdjuntosReclamacion(HttpServletRequest request, HttpServletResponse response,
	    ApplicationContext ac) {
	ReclamacionDao reclamacionDao = (ReclamacionDao) ac.getBean("reclamacionDao");

	String codigoReclamacion = request.getParameter("codigoReclamacion").split("-")[1];
	// codigoReclamacion = codigoReclamacion.split("-")[1];

	Reclamacion oReclamacion = new Reclamacion();
	oReclamacion.setId(new Integer(codigoReclamacion));

	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	try {
	    session.beginTransaction();
	    oReclamacion = (Reclamacion) reclamacionDao.getReclamacionByExample(oReclamacion).get(0);
	    session.getTransaction().commit();
	} catch (Exception e) {
	    session.getTransaction().rollback();
	    return false;
	}

	ServletFileUpload fileUpload = new ServletFileUpload();
	FileItemIterator items;
	try {
	    items = fileUpload.getItemIterator(request);

	    List<File> attachments = convertItemsIteratorToFiles(items);

	    envioMailConAdjuntos(attachments, oReclamacion, ac);

	    /*
	     * MailService mm = (MailServiceImpl)
	     * ac.getBean("mailReclamacionRecibidaAdjuntos"); String text =
	     * mm.getText();
	     * 
	     * mm.send(oReclamacion.getPasajero().getEmail(),
	     * "Nueva reclamación::" + oReclamacion.getCodigoReclamacion() + "-"
	     * + oReclamacion.getId(), attachments, oReclamacion, false);
	     * 
	     * mm.setText(text);
	     * 
	     * mm.send(StringKeys.mailTecnico, "Nueva reclamación::" +
	     * oReclamacion.getCodigoReclamacion() + "-" + oReclamacion.getId(),
	     * attachments, oReclamacion, true);
	     */
	} catch (Exception e) {
	    MailService mm = (MailServiceImpl) ac.getBean("mailReclamacionRecibidaSinAdjuntos");
	    mm.send(StringKeys.mailTecnico, "Nueva reclamación::" + oReclamacion.getCodigoReclamacion());
	    return false;
	}
	return true;
    }

    private static List<File> convertItemsIteratorToFiles(FileItemIterator items) throws Exception {
	List<File> attachments = new LinkedList<File>();
	while (items.hasNext()) {
	    FileItemStream item = items.next();
	    if (!item.isFormField() && !item.getName().equals("")) {
		String sName = FilenameUtils.getExtension(item.getName());

		File oFile = FileUtils.stream2file(item, sName);
		attachments.add(oFile);
	    }
	}
	return attachments;
    }

    public static Reclamacion insertaDatosReclamacion(ReclamacionDTO reclamacionDTO, ApplicationContext ac)
	    throws IOException, JsonGenerationException, JsonMappingException {

	ReclamacionDao reclamacionDao = (ReclamacionDao) ac.getBean("reclamacionDao");
	EstadoDao estadoDao = (EstadoDao) ac.getBean("estadoDao");
	Boolean error = false;
	String codigoReclamacion = "";
	Reclamacion reclamacion = new Reclamacion();
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();

	try {

	    session.beginTransaction();

	    Pasajero pasajero = new Pasajero();
	    Vuelo vuelo = new Vuelo();
	    vuelo.populate(reclamacionDTO.getVuelo());
	    pasajero.populate(reclamacionDTO.getPasajero());

	    reclamacion.populateFromReclamacionDTO(reclamacionDTO);
	    reclamacion.setPasajero(pasajero);

	    pasajero.setVuelo(vuelo);
	    // reclamacion.setHoraInicioVueloReclamacion(reclamacionDTO.getIdvuelo());
	    // int pasajeroId = pasajeroDao.persist(pasajero);
	    // reclamacion.setIdPasajero(pasajeroId);

	    Estado estado = new Estado();
	    estado.setNombreEstado("Recibida");

	    List<Estado> oListEstado = estadoDao.getEstadoByExample(estado);

	    if (oListEstado != null && oListEstado.size() == 1)
		estado = oListEstado.get(0);
	    else
		error = true;

	    reclamacion.setEstado(estado);

	    int codigo = reclamacionDao.persist(reclamacion);

	    if (codigo == -1)
		return null;
	    codigoReclamacion = reclamacionDTO.getCodigoReclamacion() + "-" + codigo;
	    reclamacion.setCodigoReclamacion(codigoReclamacion);
	    session.getTransaction().commit();

	} catch (Exception e) {
	    session.getTransaction().rollback();
	    return null;
	}
	// 5. Add article to List<Article>
	// articles.add(article);
	return reclamacion;

	// 6. Send List<Article> as JSON to client

    }

}
