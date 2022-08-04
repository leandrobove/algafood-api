package com.github.algafood.api.dto;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "produtos")
@Getter
@Setter
public class ProdutoModel extends RepresentationModel<ProdutoModel> {

	private Long id;

	private String nome;

	private String descricao;

	private BigDecimal preco;

	private Boolean ativo;

}
