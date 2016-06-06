package com.dfp.core;


import java.io.File;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.dfp.persistencia.entities.Reclamacion;
 
@Service
public interface MailService {
 
    	
	@Async
	public void send(String string, String string2, List<File> attachments,
		Reclamacion oReclamacion,boolean emailAdministracion);
	
	public String getText();
	
	public void setText(String text);
 
}
