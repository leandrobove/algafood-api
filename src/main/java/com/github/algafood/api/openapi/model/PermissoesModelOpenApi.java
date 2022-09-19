package com.github.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.github.algafood.api.dto.PermissaoModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "PermissoesModel")
@Data
public class PermissoesModelOpenApi {
	
	private PermissoesEmbeddedModelOpenApi _embedded;
	private Links _links;

	@ApiModel(value = "PermissoesEmbeddedModel")
	@Data
	public class PermissoesEmbeddedModelOpenApi {

		private List<PermissaoModel> permissoes;

	}
}