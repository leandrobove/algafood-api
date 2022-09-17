package com.github.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.github.algafood.api.dto.GrupoModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "GruposModel")
@Data
public class GruposModelOpenApi {

	private GruposEmbeddedModelOpenApi _embedded;
	private Links _links;

	@ApiModel(value = "GruposEmbeddedModel")
	@Data
	public class GruposEmbeddedModelOpenApi {

		private List<GrupoModel> grupos;

	}

}
