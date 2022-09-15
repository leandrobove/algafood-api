package com.github.algafood.api.dto;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "produtos")
@Getter
@Setter
public class ProdutoModel extends RepresentationModel<ProdutoModel> {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Hot roll de salmão com cream cheese e molho tarê")
	private String nome;

	@ApiModelProperty(example = "Acompanha hashi")
	private String descricao;

	@ApiModelProperty(example = "39.90")
	private BigDecimal preco;

	@ApiModelProperty(example = "true")
	private Boolean ativo;

}
