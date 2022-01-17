package com.github.algafood.api.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Relation(collectionRelation = "cidades")
public class CidadeDTO extends RepresentationModel<CidadeDTO> {

	private Long id;

	private String nome;

	private EstadoDTO estado;

}
