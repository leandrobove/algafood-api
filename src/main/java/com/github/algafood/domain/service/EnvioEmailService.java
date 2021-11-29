package com.github.algafood.domain.service;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;

public interface EnvioEmailService {

	void enviar(Mensagem email);

	@Getter
	@Builder
	class Mensagem {

		private String assunto;

		private Set<String> destinatarios;

		private String corpo;

	}

}
