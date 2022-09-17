package com.github.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.github.algafood.api.dto.FormaPagamentoModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "FormasPagamentoModel")
@Data
public class FormasPagamentoModelOpenApi {
	
	private FormasPagamentoEmbeddedModelOpenApi _embedded;
	private Links _links;
	
	@ApiModel(value = "FormasPagamentoEmbeddedModel")
	@Data
	public class FormasPagamentoEmbeddedModelOpenApi {
		
		private List<FormaPagamentoModel> formasPagamento;
		
	}

}
