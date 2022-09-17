package com.github.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.github.algafood.api.dto.EstadoModel;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "EstadosModel")
@Getter
@Setter
public class EstadosModelOpenApi {

	private EstadosEmbeddedModel _embedded;
	private Links _links;
	
	@ApiModel("EstadosEmbeddedModel")
	@Getter
	@Setter
	public class EstadosEmbeddedModel {
		
		private List<EstadoModel> estados;
		
	}
}
