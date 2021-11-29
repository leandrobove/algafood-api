package com.github.algafood.domain.service;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

public interface EnvioEmailService {

	void enviar(Mensagem email);

	@Getter
	@Builder
	class Mensagem {

		@NonNull
		private String assunto;

		@Singular
		@NonNull
		private Set<String> destinatarios;

		@NonNull
		private String corpo;

	}

}
