package com.dfp.core;


import java.io.File;
import java.util.List;

import com.dfp.persistencia.entities.Reclamacion;
 
public interface MailService {
 
	public void send(String to, String subject);
	
	public void send(String to, String subject,  List<File> attachments);

	public void send(String string, String string2, List<File> attachments,
		Reclamacion oReclamacion,boolean emailAdministracion);
	
	public String getText();
	
	public void setText(String text);
 
}
