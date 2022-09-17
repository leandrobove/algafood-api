package com.github.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.github.algafood.api.dto.CidadeModel;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "CidadesModel")
@Setter
@Getter
public class CidadesModelOpenApi {
	
	private CidadesEmbeddedModelOpenApi _embedded;
	private Links _links;
	
	@ApiModel("CidadesEmbeddedModel")
	@Setter
	@Getter
	public class CidadesEmbeddedModelOpenApi {
		
		private List<CidadeModel> cidades;
		
	} 

}
