package com.github.algafood.api.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Relation(collectionRelation = "usuarios")
public class UsuarioModel extends RepresentationModel<UsuarioModel> {

	@ApiModelProperty(example = "123")
	private Long id;

	@ApiModelProperty(example = "João da Silva")
	private String nome;

	@ApiModelProperty(example = "joao.ger@yopmail.com")
	private String email;

}
