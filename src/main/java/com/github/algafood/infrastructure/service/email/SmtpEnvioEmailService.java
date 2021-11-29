package com.github.algafood.infrastructure.service.email;

import java.nio.charset.StandardCharsets;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.github.algafood.domain.core.email.EmailProperties;
import com.github.algafood.domain.service.EnvioEmailService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private EmailProperties emailProperties;

	@Autowired
	private Configuration freeMarkerConfig;

	@Override
	public void enviar(Mensagem mensagem) {

		try {

			String corpo = this.processarTemplate(mensagem);

			MimeMessage mimeMessage = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());

			helper.setSubject(mensagem.getAssunto());
			helper.setText(corpo, true);
			helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
			helper.setFrom(emailProperties.getRemetente());

			mailSender.send(mimeMessage);

		} catch (Exception e) {
			throw new EmailException("Não foi possível enviar e-mail", e);
		}

	}

	private String processarTemplate(Mensagem mensagem) {

		try {
			Template template = freeMarkerConfig.getTemplate(mensagem.getCorpo());
			
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());
			
		} catch (Exception e) {
			throw new EmailException("Não foi possível montar o template do e-mail", e);
		}
	}

}
