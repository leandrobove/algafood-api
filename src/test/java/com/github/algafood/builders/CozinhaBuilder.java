package com.github.algafood.builders;

import com.github.algafood.domain.model.Cozinha;

public class CozinhaBuilder {

	private Cozinha cozinha;

	private CozinhaBuilder() {
	}

	public static CozinhaBuilder umaCozinha() {
		CozinhaBuilder builder = new CozinhaBuilder();
		builder.cozinha = new Cozinha();
		
		builder.cozinha.setId(1L);
		builder.cozinha.setNome("Japonesa");

		return builder;
	}
	
	public CozinhaBuilder comIdNulo() {
		this.cozinha.setId(null);
		
		return this;
	}

	public Cozinha build() {
		return this.cozinha;
	}
}
