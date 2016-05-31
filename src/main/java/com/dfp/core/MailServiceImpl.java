package com.dfp.core;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.dfp.persistencia.entities.Reclamacion;
 
/**
 * Servicio de envío de emails
 */

public class MailServiceImpl implements MailService {
 
	private static final Log log = LogFactory.getLog(MailServiceImpl.class);
 
	/** wrapper de Spring sobre javax.mail */
	private JavaMailSenderImpl mailSender;
	
	private Reclamacion reclamacion;
	
	private String text;
	
	private String subTemplate;
	
	private String subTemplateAdmin;
 
	public String getSubTemplateAdmin() {
	    return subTemplateAdmin;
	}

	public void setSubTemplateAdmin(String subTemplateAdmin) {
	    this.subTemplateAdmin = subTemplateAdmin;
	}

	public String getSubTemplate() {
	    return subTemplate;
	}

	public void setSubTemplate(String subTemplate) {
	    this.subTemplate = subTemplate;
	}

	public Reclamacion getReclamacion() {
	    return reclamacion;
	}

	public void setReclamacion(Reclamacion reclamacion) {
	    this.reclamacion = reclamacion;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}
	 
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	/** correo electronico del remitente */
	private String from;
 
	public void setFrom(String from) {
		this.from = from;
	}
 
	public String getFrom() {
		return from;
	}
 
	/** flag para indicar si esta  activo el servicio */
	public boolean active = true;
 
	public boolean isActive() {
		return active;
	}
 
	public void setActive(boolean active) {
		this.active = active;
	}
 
	private static final List<File>  NO_ATTACHMENTS = null;
 
	/** envío de email 
	 * @param to correo electronico del destinatario
	 * @param subject asunto del mensaje
	 * @param text cuerpo del mensaje
	 */
	public void send(String to, String subject, Reclamacion oReclamacion) {
		send(to, subject,  NO_ATTACHMENTS,oReclamacion, true);
	}
	
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
 
	/** envío de email con attachments
	 * @param to correo electrónico del destinatario
	 * @param subject asunto del mensaje
	 * @param text cuerpo del mensaje
	 * @param attachments ficheros que se anexarón al mensaje 
	 */
	
	public void send(String to, String subject,List<File>  attachments, Reclamacion oReclamacion,boolean emailAdministracion) {
		// chequeo de parámetros 
	    	String textmensaje = this.text;
		Assert.hasLength(to, "email 'to' needed");
		Assert.hasLength(subject, "email 'subject' needed");
		Assert.hasLength(textmensaje, "email 'text' needed");
 
		// asegurando la trazabilidad
		if (log.isDebugEnabled()) {
			final boolean usingPassword = !"".equals(mailSender.getPassword());
			log.debug("Sending email to: '" + to + "' [through host: '" + mailSender.getHost() + ":"
					+ mailSender.getPort() + "', username: '" + mailSender.getUsername() + "' usingPassword:"
					+ usingPassword + "].");
			log.debug("isActive: " + active);
		}
		// el servicio esta activo?
		if (!active) return;
 
		// plantilla para el envío de email
		final MimeMessage message = mailSender.createMimeMessage();
 
		try {
			// el flag a true indica que va a ser multipart
			final MimeMessageHelper helper = new MimeMessageHelper(message, true);
			
			// settings de los parámetros del envío
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setFrom(getFrom());
//			helper.setText(text);
			if (oReclamacion.getCodigoReclamacion()!=null && oReclamacion.getId()!=null)
			    textmensaje = textmensaje.replaceFirst("%CASORECLAMACION%", oReclamacion.getCodigoReclamacion()+"-"+oReclamacion.getId());
			textmensaje = textmensaje.replaceAll("%CASORECLAMACION%", "");
			
			if (oReclamacion.getCodigoReclamacion()!=null)
			    textmensaje = textmensaje.replaceFirst("%MOTIVORECLAMACION%", "Motivo de la reclamación "+oReclamacion.getCodigoReclamacion());			
			textmensaje = textmensaje.replaceAll("%MOTIVORECLAMACION%", "");
			
			if (oReclamacion.getPasajero().getNombre()!=null)
			    textmensaje = textmensaje.replaceFirst("%NOMBRE%",  oReclamacion.getPasajero().getNombre());			
			textmensaje = textmensaje.replaceAll("%NOMBRE%", "");
			
			if (oReclamacion.getPasajero().getNombre()!=null && oReclamacion.getPasajero().getApellidos()!=null)
			    textmensaje = textmensaje.replaceFirst("%NOMBRECOMPLETO%", oReclamacion.getPasajero().getNombre()+" "+oReclamacion.getPasajero().getApellidos());			
			textmensaje = textmensaje.replaceAll("%NOMBRECOMPLETO%", "");
			
			if (oReclamacion.getPasajero().getTelefono()!=null)
			    textmensaje = textmensaje.replaceFirst("%TELEFONO%","Teléfono: "+ oReclamacion.getPasajero().getTelefono());
			textmensaje = textmensaje.replaceAll("%TELEFONO%", "");
			
			if (oReclamacion.getPasajero().getVuelo().getAeropuertoOrigen()!=null && oReclamacion.getPasajero().getVuelo().getAeropuertoDestino()!=null)
			    textmensaje = textmensaje.replaceFirst("%TRAYECTOVUELO%", " Trayecto: "+oReclamacion.getPasajero().getVuelo().getAeropuertoOrigen()+"-"+oReclamacion.getPasajero().getVuelo().getAeropuertoDestino());			
			textmensaje = textmensaje.replaceAll("%TRAYECTOVUELO%", "");
			
			if (oReclamacion.getPasajero().getVuelo().getCodigoVuelo()!=null)
			    textmensaje = textmensaje.replaceFirst("%TRAYECTOVUELO%", " Código de vuelo: "+oReclamacion.getPasajero().getVuelo().getCodigoVuelo());
			textmensaje = textmensaje.replaceAll("%TRAYECTOVUELO%", "");
			
			if (oReclamacion.getHoraInicioVueloPrevista()!=null && oReclamacion.getHoraFinVueloPrevista()!=null)
			    textmensaje = textmensaje.replaceFirst("%HORARIOPREVISTO%", " Horario previsto: "+df.format(oReclamacion.getHoraInicioVueloPrevista())+" "+df.format(oReclamacion.getHoraFinVueloPrevista()));
			textmensaje = textmensaje.replaceAll("%HORARIOPREVISTO%", "");
			
			if (oReclamacion.getHoraInicioVueloPrevista()!=null)
			    textmensaje = textmensaje.replaceFirst("%HORAINICIOPREVISTA%", " Fecha y hora prevista: "+df.format(oReclamacion.getHoraInicioVueloPrevista()));
			textmensaje = textmensaje.replaceAll("%HORAINICIOPREVISTA%", "");
			
			if (oReclamacion.getHoraInicioVueloReal()!=null && oReclamacion.getHoraFinVueloReal()!=null)
			    textmensaje = textmensaje.replaceFirst("%HORARIOREAL%", " Horario real: "+df.format(oReclamacion.getHoraInicioVueloReal())+" "+df.format(oReclamacion.getHoraFinVueloReal()));
			textmensaje = textmensaje.replaceAll("%HORARIOREAL%", "");
			
			if (oReclamacion.getTextoReclamacion()!=null)
			    textmensaje = textmensaje.replaceFirst("%TEXTORECLAMACION%", oReclamacion.getTextoReclamacion());			
			textmensaje = textmensaje.replaceAll("%TEXTORECLAMACION%", "");
			
			if (oReclamacion.getEstado().getNombreEstado()!=null)
			    textmensaje = textmensaje.replaceFirst("%ESTADO%", oReclamacion.getEstado().getNombreEstado()); 
			textmensaje = textmensaje.replaceAll("%ESTADO%", "");
			
			if (oReclamacion.getEstado().getDescripcionEstado()!=null)
			    textmensaje = textmensaje.replaceFirst("%DESCRIPCIONESTADO%", oReclamacion.getEstado().getDescripcionEstado()); 
			textmensaje = textmensaje.replaceAll("%DESCRIPCIONESTADO%", "");
		
			
			if (emailAdministracion && oReclamacion.getEstado().getSecEstado()!=5 && oReclamacion.getEstado().getSecEstado()!=0){				
				textmensaje = textmensaje.replaceFirst("%ENLACEESTADOSACEPTA%","<td class='button' height='45' bgcolor='green' ><a href='"+StringKeys.urlEstadoAcepta+oReclamacion.getId()+"'>Aceptar</a></td>" );
				textmensaje = textmensaje.replaceFirst("%ENLACEESTADOSRECHAZA%", "<td class='button' height='45' bgcolor='#e05443' ><a href='"+StringKeys.urlEstadoRechaza+oReclamacion.getId()+"'>Rechazar</a></td>" );
				textmensaje = textmensaje.replaceFirst("%ENLACEPDF%", "<td class='button' height='45' bgcolor='blue'><a href='"+StringKeys.urlPdf+oReclamacion.getId()+"'>Pdf</a></td>" );
			}else{
				textmensaje = textmensaje.replaceFirst("%ENLACEESTADOSACEPTA%", "");				
				textmensaje = textmensaje.replaceFirst("%ENLACEESTADOSRECHAZA%", "");
				textmensaje = textmensaje.replaceFirst("%ENLACEPDF%", "");
			}

			if (this.getSubTemplate()!=null)
			    textmensaje = textmensaje.replaceFirst("%SUBTEMPLATERECLAMACION%", this.getSubTemplate()); 
			textmensaje = textmensaje.replaceAll("%SUBTEMPLATERECLAMACION%", "");

			if (this.getSubTemplateAdmin()!=null)
			    textmensaje = textmensaje.replaceFirst("%SUBTEMPLATERECLAMACIONADMIN%", this.getSubTemplateAdmin()); 
			textmensaje = textmensaje.replaceAll("%SUBTEMPLATERECLAMACIONADMIN%", "");
			
			helper.setText(textmensaje,true);
			
	//		helper.setText(text, "<h1>HOla</h1>");
			
 
			// adjuntando los ficheros
			if (attachments != null) {
				for (File attachment:attachments) {
					FileSystemResource file = new FileSystemResource(attachment);
					helper.addAttachment(attachment.getName(), file);
					if (log.isDebugEnabled()) {
						log.debug("File '" + file + "' attached.");
					}
				}
			}
 
		} catch (MessagingException e) {
		    System.out.println("done");
			new RuntimeException(e);
		}
		
		// el envío
		this.mailSender.send(message);
		//System.out.println("mensaje------------------>" +textmensaje);
	}

	

	

 
}