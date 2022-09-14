package com.github.algafood.api.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Relation(collectionRelation = "formasPagamento")
public class FormaPagamentoModel extends RepresentationModel<FormaPagamentoModel> {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Cartão de crédito")
	private String descricao;

}
