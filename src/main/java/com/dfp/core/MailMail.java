package com.dfp.core;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MailMail {
	
	private MailSender mailSender;
	
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	private SimpleMailMessage simpleMailMessage;

	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}

	
	public int sendMail(String emailTo,String emailFrom, String sParametro) {
		try {
			SimpleMailMessage message = new SimpleMailMessage(simpleMailMessage);
			if(!emailTo.equals(""))
				message.setTo(emailTo);
			if(!emailFrom.equals(""))
				message.setFrom(emailFrom);

			
			// Para agregarle3 cadenas al mensaje
			 message.setText(String.format(
			// simpleMailMessage.getText(), sDocumentoIdentificativo, sMotivo));
				 simpleMailMessage.getText(), sParametro));

			mailSender.send(message);
		} catch (Exception e) {
			return -1;
		}

		return 1;
	}

}
