package pl.diakowski.announcementwebsite.web.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	private final JavaMailSender emailSender;

	public EmailService(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}

	public void sendActivationEmail(String email, String token) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom("pai2024@interia.eu");

		simpleMailMessage.setTo(email);
		simpleMailMessage.setSubject("Aktywacja konta");
		simpleMailMessage.setText("Aby aktywować konto kliknij w link: http://localhost:8080/activation?token=" + token);
		emailSender.send(simpleMailMessage);
	}
}

