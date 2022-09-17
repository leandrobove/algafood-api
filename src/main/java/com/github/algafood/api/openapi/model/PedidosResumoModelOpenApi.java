package com.github.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.github.algafood.api.dto.PedidoResumoModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "PedidosResumoModel")
@Data
public class PedidosResumoModelOpenApi {

	private PedidosResumoEmbeddedModelOpenApi _embedded;
	private Links _links;
	private PageModelOpenApi page;

	@ApiModel(value = "PedidosResumoEmbeddedModel")
	@Data
	public class PedidosResumoEmbeddedModelOpenApi {

		private List<PedidoResumoModel> pedidos;
		
	}

}
