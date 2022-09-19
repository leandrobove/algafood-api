package com.github.algafood.infrastructure.service.email;

import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.github.algafood.core.email.EmailProperties;
import com.github.algafood.domain.service.EnvioEmailService;

public class SmtpEnvioEmailService implements EnvioEmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private EmailProperties emailProperties;

	@Autowired
	private ProcessadorTemplateEmail processadorTemplateEmail;

	@Override
	public void enviar(Mensagem mensagem) {

		try {

			MimeMessage mimeMessage = criarMimeMessage(mensagem);

			mailSender.send(mimeMessage);

		} catch (Exception e) {
			throw new EmailException("Não foi possível enviar e-mail", e);
		}

	}

	protected MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {
		String corpo = processadorTemplateEmail.processarTemplate(mensagem);

		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());

		helper.setSubject(mensagem.getAssunto());
		helper.setText(corpo, true);
		helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
		helper.setFrom(emailProperties.getRemetente());

		return mimeMessage;
	}

}
