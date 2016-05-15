package com.dfp.utiles;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;




public class MailUtil  {
	
	private static final Logger log = Logger.getLogger(MailUtil.class.getName());

	public String envioCorreo(String sEmailConsulta,   String msgBody) {
		 Properties props = new Properties();
		 Session session = Session.getDefaultInstance(props, null);
		 //String msgBody = "Se ha encontrado una multa en el boletin de la provincia de Zaragoza (http://www1.dpz.es/bop/2013/09/pdf/bop0930.pdf) te podemos ayudar a recurrirla."+msg1;
		 String sResultado = "mail=ok";
		 
		  try {
	            Message msg = new MimeMessage(session);
	            msg.setFrom(new InternetAddress("alvarezdespachoabogados@gmail.com", "Correo Admin Alvarez Abogados"));
	            msg.addRecipient(Message.RecipientType.TO,
                     new InternetAddress("info@defensadelconductor.com", "Notificacion de multa"));
	            msg.addRecipient(Message.RecipientType.CC,
	                             new InternetAddress(sEmailConsulta, "Notificacion de multa "+ sEmailConsulta));
	            msg.setSubject("Notificacion de multa");
	            msg.setContent(msgBody, "text/html");
	        
	            Transport.send(msg);
	    
	        } catch (AddressException e) {
	        	log.severe(e.getMessage());
	        	sResultado = "mail=nook";
	        } catch (MessagingException e) {
	        	log.severe(e.getMessage());
	        	sResultado = "mail=nook";
	        } catch (UnsupportedEncodingException e) {
	        	log.severe(e.getMessage());
	        	sResultado = "mail=nook";
			}
		 
		  return sResultado;
	}
	
	// private  byte[] buffer = new byte[8192000];
	// private int size=0;
	// private String attname=null;

//	public String envioCorreo(String sEmailConsulta, String msgBody, byte[] attachmentData) {
//		 Properties props = new Properties();
//		//ssion session = Session.getDefaultInstance(props, null);
//		 //String msgBody = "Se ha encontrado una multa en el boletin de la provincia de Zaragoza (http://www1.dpz.es/bop/2013/09/pdf/bop0930.pdf) te podemos ayudar a recurrirla."+msg1;
//		 String sResultado = "mail=ok";
//		 
//		 System.out.println("envioCorreo"+sEmailConsulta);
//		 
//		 Session session = Session.getDefaultInstance(props, null);
//		
//		 Message msg = new MimeMessage(session);
//		 
//		  try {
//			  
//	            msg.setFrom(new InternetAddress("alvarezdespachoabogados@gmail.com", "Correo Admin Alvarez Abogados"));
//	            msg.addRecipient(Message.RecipientType.TO,
//                   new InternetAddress("info@defensadelconductor.com", "Notificacion de multa"));
//	            msg.addRecipient(Message.RecipientType.CC,
//	                             new InternetAddress(sEmailConsulta, "Notificacion de multa "+ sEmailConsulta));
//	            msg.setSubject("Petición presupuesto");
//			    msg.setContent(msgBody, "text/html");
//			    Transport.send(msg);
//         } catch (MessagingException e) {
//        		log.severe(e.getMessage());
//	        	sResultado = "mail=nook";
// 		}catch (UnsupportedEncodingException e1) {
// 			log.severe(e1.getMessage());
//        	sResultado = "mail=nook";
//		} catch (Exception e1) {
// 			log.severe(e1.getMessage());
// 			System.out.println(e1.getMessage());
//        	sResultado = "mail=nook";
//		} 
//		 
////		  try {
//
////	            //http://stackoverflow.com/questions/11155381/send-email-with-attachment-on-gae-java
//
//		  System.out.println("envioCorreo"+sEmailConsulta+" sResultado "+sResultado);
//		  return sResultado;
//	}

	public String envioCorreo1(String sEmailConsulta, String msgBody) {
		 Properties props = new Properties();
			//ssion session = Session.getDefaultInstance(props, null);
			 //String msgBody = "Se ha encontrado una multa en el boletin de la provincia de Zaragoza (http://www1.dpz.es/bop/2013/09/pdf/bop0930.pdf) te podemos ayudar a recurrirla."+msg1;
			 String sResultado = "mail=ok";
			 
			 System.out.println("envioCorreo"+sEmailConsulta);
			 
			 Session session = Session.getDefaultInstance(props, null);
			
			 Message msg = new MimeMessage(session);
			 
			  try {
				  
		            msg.setFrom(new InternetAddress("alvarezdespachoabogados@gmail.com", "Correo Admin Alvarez Abogados"));
		            msg.addRecipient(Message.RecipientType.TO,
	                   new InternetAddress("info@defensadelconductor.com", "Notificacion de multa"));
		            msg.addRecipient(Message.RecipientType.CC,
		                             new InternetAddress(sEmailConsulta, "Notificacion de multa "+ sEmailConsulta));
		            msg.setSubject("Petición presupuesto");
				    msg.setContent(msgBody, "text/html");
				    Transport.send(msg);
	         } catch (MessagingException e) {
	        		log.severe(e.getMessage());
		        	sResultado = "mail=nook";
	 		}catch (UnsupportedEncodingException e1) {
	 			log.severe(e1.getMessage());
	        	sResultado = "mail=nook";
			} catch (Exception e1) {
	 			log.severe(e1.getMessage());
	 			System.out.println(e1.getMessage());
	        	sResultado = "mail=nook";
			} 
			 
//			  try {

//		            //http://stackoverflow.com/questions/11155381/send-email-with-attachment-on-gae-java

			  System.out.println("envioCorreo"+sEmailConsulta+" sResultado "+sResultado);
			  return sResultado;
	}
	
	

}
