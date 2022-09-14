package com.github.algafood.api.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Grupo", description = "Representa um grupo")

@Relation(collectionRelation = "grupos")
@Getter
@Setter
public class GrupoModel extends RepresentationModel<GrupoModel> {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Vendedor")
	private String nome;
}
