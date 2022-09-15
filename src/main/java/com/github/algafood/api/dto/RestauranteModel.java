package com.github.algafood.api.dto;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantes")
@Getter
@Setter
public class RestauranteModel extends RepresentationModel<RestauranteModel> {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Thai Gourmet")
	private String nome;

	@ApiModelProperty(example = "10.00")
	private BigDecimal taxaFrete;

	private CozinhaModel cozinha;

	@ApiModelProperty(example = "true")
	private Boolean ativo;
	
	@ApiModelProperty(example = "true")
	private Boolean aberto;
	
	private EnderecoModel endereco;
}
