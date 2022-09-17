package com.github.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.github.algafood.api.dto.RestauranteBasicoModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "RestaurantesBasicoModel")
@Data
public class RestaurantesBasicoModelOpenApi {
	
	private RestaurantesEmbeddedModelOpenApi _embedded;
	private Links _links;

	@ApiModel(value = "RestaurantesEmbeddedModel")
	@Data
	public class RestaurantesEmbeddedModelOpenApi {
		
		private List<RestauranteBasicoModel> restaurantes;
		
	}
}
