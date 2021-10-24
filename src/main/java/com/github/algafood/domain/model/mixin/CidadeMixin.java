package com.github.algafood.domain.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.algafood.domain.model.Estado;

public class CidadeMixin extends SimpleModule {

	private static final long serialVersionUID = 1L;

	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Estado estado;

}
