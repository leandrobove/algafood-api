package com.github.algafood.api.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@ApiModel(value = "Cidade", description = "Representa uma cidade")

@Relation(collectionRelation = "cidades")
public class CidadeModel extends RepresentationModel<CidadeModel> {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Campinas")
	private String nome;

	private EstadoModel estado;

}
