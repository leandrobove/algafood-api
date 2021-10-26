package com.github.algafood.domain.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.algafood.domain.model.Cidade;
import com.github.algafood.domain.model.Cozinha;
import com.github.algafood.domain.model.mixin.CidadeMixin;
import com.github.algafood.domain.model.mixin.CozinhaMixin;

@Component
public class JacksonMixinModule extends SimpleModule {

	private static final long serialVersionUID = 1L;

	public JacksonMixinModule() {
		setMixInAnnotation(Cidade.class, CidadeMixin.class);
		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
	}

}
