package com.github.algafood.api.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Relation(collectionRelation = "formasPagamento")
public class FormaPagamentoModel extends RepresentationModel<FormaPagamentoModel> {

	private Long id;

	private String descricao;

}
