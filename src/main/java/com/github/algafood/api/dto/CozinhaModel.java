package com.github.algafood.api.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Relation(collectionRelation = "cozinhas")
public class CozinhaModel extends RepresentationModel<CozinhaModel> {

	private Long id;

	private String nome;

}
