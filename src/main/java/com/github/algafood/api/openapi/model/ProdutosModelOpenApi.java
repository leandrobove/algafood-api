package com.github.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.github.algafood.api.dto.ProdutoModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "ProdutosModel")
@Data
public class ProdutosModelOpenApi {

	private ProdutosEmbeddedModelOpenApi _embedded;
	private Links _links;
	
	@ApiModel(value = "ProdutosEmbeddedModel")
	@Data
	public class ProdutosEmbeddedModelOpenApi {
		
		private List<ProdutoModel> produtos;
		
	}
}
