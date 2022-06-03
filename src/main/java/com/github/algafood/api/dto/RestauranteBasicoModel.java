package com.github.algafood.api.dto;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantes")
@Getter
@Setter
public class RestauranteBasicoModel extends RepresentationModel<RestauranteBasicoModel> {

	private Long id;

	private String nome;

	private BigDecimal taxaFrete;

	private CozinhaModel cozinha;

}
