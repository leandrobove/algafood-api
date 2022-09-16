package com.github.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.github.algafood.api.dto.CozinhaModel;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "CozinhasModel")
@Setter
@Getter
public class CozinhasModelOpenApi {
	
	private CozinhaEmbeddedModelOpenApi _embedded;
	private Links _links;
	private PageModelOpenApi page;
	
	@ApiModel("CozinhasEmbeddedModel")
	@Setter
	@Getter
	public class CozinhaEmbeddedModelOpenApi {
		
		private List<CozinhaModel> cozinhas;
		
	} 

}
