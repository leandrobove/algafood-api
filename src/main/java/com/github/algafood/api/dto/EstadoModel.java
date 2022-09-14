package com.github.algafood.api.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@ApiModel(value = "Estado", description = "Representa um estado")

@Relation(collectionRelation = "estados")
public class EstadoModel extends RepresentationModel<EstadoModel> {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "São Paulo")
	private String nome;
}
