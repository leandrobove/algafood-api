package com.github.algafood.domain.model.mixin;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.github.algafood.domain.model.Restaurante;

@JsonRootName(value = "cozinha")
public class CozinhaMixin {

	@JsonIgnore
	private List<Restaurante> restaurantes = new ArrayList<Restaurante>();

}
