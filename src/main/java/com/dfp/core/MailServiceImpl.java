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
	
	
 
	public Reclamacion getReclamacion() {
	    return reclamacion;
	}

	public void setReclamacion(Reclamacion reclamacion) {
	    this.reclamacion = reclamacion;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}
	
	private String text;
 
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	/** correo electr�nico del remitente */
	private String from;
 
	public void setFrom(String from) {
		this.from = from;
	}
 
	public String getFrom() {
		return from;
	}
 
	/** flag para indicar si est� activo el servicio */
	public boolean active = true;
 
	public boolean isActive() {
		return active;
	}
 
	public void setActive(boolean active) {
		this.active = active;
	}
 
	private static final List<File>  NO_ATTACHMENTS = null;
 
	/** envío de email 
	 * @param to correo electr�nico del destinatario
	 * @param subject asunto del mensaje
	 * @param text cuerpo del mensaje
	 */
	public void send(String to, String subject) {
		send(to, subject,  NO_ATTACHMENTS);
	}
	
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
 
	/** envío de email con attachments
	 * @param to correo electr�nico del destinatario
	 * @param subject asunto del mensaje
	 * @param text cuerpo del mensaje
	 * @param attachments ficheros que se anexar�n al mensaje 
	 */
	public void send(String to, String subject,List<File>  attachments, Reclamacion oReclamacion,boolean emailAdministracion) {
		// chequeo de parámetros 
		Assert.hasLength(to, "email 'to' needed");
		Assert.hasLength(subject, "email 'subject' needed");
		Assert.hasLength(text, "email 'text' needed");
 
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
			text = text.replaceFirst("%CASORECLAMACION%", oReclamacion.getCodigoReclamacion()+"-"+oReclamacion.getId());			
			text = text.replaceAll("%CASORECLAMACION%", "");
			text = text.replaceFirst("%MOTIVORECLAMACION%", "Motivo de la reclamación "+oReclamacion.getCodigoReclamacion());			
			text = text.replaceAll("%MOTIVORECLAMACION%", "");
			text = text.replaceFirst("%NOMBRE%",  oReclamacion.getPasajero().getNombre());			
			text = text.replaceAll("%NOMBRE%", "");
			text = text.replaceFirst("%NOMBRECOMPLETO%", oReclamacion.getPasajero().getNombre()+" "+oReclamacion.getPasajero().getApellidos());			
			text = text.replaceAll("%NOMBRECOMPLETO%", "");
			text = text.replaceFirst("%TELEFONO%","Teléfono: "+ oReclamacion.getPasajero().getTelefono());
			text = text.replaceAll("%TELEFONO%", "");
			text = text.replaceFirst("%TRAYECTOVUELO%", " Trayecto: "+oReclamacion.getPasajero().getVuelo().getAeropuertoOrigen()+"-"+oReclamacion.getPasajero().getVuelo().getAeropuertoDestino());
			text = text.replaceAll("%TRAYECTOVUELO%", "");
			text = text.replaceFirst("%TRAYECTOVUELO%", " Código de vuelo: "+oReclamacion.getPasajero().getVuelo().getCodigoVuelo());
			text = text.replaceAll("%TRAYECTOVUELO%", "");
			text = text.replaceFirst("%HORARIOPREVISTO%", " Horario previsto: "+df.format(oReclamacion.getHoraInicioVueloPrevista())+" "+df.format(oReclamacion.getHoraFinVueloPrevista()));
			text = text.replaceAll("%HORARIOPREVISTO%", "");
			text = text.replaceFirst("%HORARIOREAL%", " Horario real: "+df.format(oReclamacion.getHoraInicioVueloReal())+" "+df.format(oReclamacion.getHoraFinVueloReal()));
			text = text.replaceAll("%HORARIOREAL%", "");
			text = text.replaceFirst("%TEXTORECLAMACION%", oReclamacion.getTextoReclamacion());
			text = text.replaceAll("%TEXTORECLAMACION%", "");
			text = text.replaceFirst("%ESTADO%", oReclamacion.getEstado().getNombreEstado()); 
			text = text.replaceAll("%ESTADO%", "");
			
			if (emailAdministracion){
				
				text = text.replaceFirst("%ENLACEESTADOSACEPTA%","<a href='"+StringKeys.urlEstadoAcepta+"'>Aceptar</a>" );
				text = text.replaceFirst("%ENLACEESTADOSRECHAZA%", "<a href='"+StringKeys.urlEstadoRechaza+"'>Rechazar</a>" );
				text = text.replaceFirst("%ENLACEPDF%", "<a href='"+StringKeys.urlPdf+oReclamacion.getId()+"'>Pdf</a>" );
				
			}else{
				text = text.replaceFirst("%ENLACEESTADOSACEPTA%", "");				
				text = text.replaceFirst("%ENLACEESTADOSRECHAZA%", "");
				text = text.replaceFirst("%ENLACEPDF%", "");
			}
			
			helper.setText(text,true);
			
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
			new RuntimeException(e);
		}
		
		// el envío
		this.mailSender.send(message);
	}

	@Override
	public void send(String string, String string2, List<File> attachments) {
	    // TODO Auto-generated method stub
	    
	}

	

 
}