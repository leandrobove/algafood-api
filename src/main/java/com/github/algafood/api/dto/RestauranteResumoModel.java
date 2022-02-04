package com.github.algafood.api.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Relation(collectionRelation = "restaurantes")
public class RestauranteResumoModel extends RepresentationModel<RestauranteResumoModel> {

	private Long id;

	private String nome;

}
