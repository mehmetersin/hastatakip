package com.hastatakip;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class MailSender {

	private final Logger logger = Logger.getLogger(MailSender.class);

	public void mailSend( String subject, String text)
			throws Exception {
		// gmail smtp
		// String host = "smtp.gmail.com"; String port = "587"
		try {

			String host = Util.getProp("smtp.ip");
			String port = Util.getProp("smtp.port");
			String from = Util.getProp("smtp.user");
			String password = Util.getProp("smtp.password");
			String[] to = Util.getProp("smtp.to").split("-");

			Properties props = System.getProperties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.user", from);
			props.put("mail.smtp.password", password);
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.auth", "true");

			Session session = Session.getDefaultInstance(props, null);
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			InternetAddress[] toAddress = new InternetAddress[to.length];
			for (int i = 0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}

			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
				logger.debug("sendMail to: " + toAddress[i]);
			}
			message.setSubject(subject);
			logger.debug("sendMail subject: " + subject);
			message.setText(text);
			logger.debug("sendMail text: " + text);
			message.setContent(text, "text/html; charset=utf-8");
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			logger.error("sendMail Exception:" + e.getMessage());
			logger.error(e);
		}
	}
}
