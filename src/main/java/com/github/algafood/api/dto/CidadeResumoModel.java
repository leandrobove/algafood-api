package com.github.algafood.api.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Relation(collectionRelation = "cidades")
public class CidadeResumoModel extends RepresentationModel<CidadeResumoModel> {

	private Long id;

	private String nome;

	private String estado;

}
