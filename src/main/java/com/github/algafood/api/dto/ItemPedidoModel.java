package com.github.algafood.api.dto;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoModel extends RepresentationModel<ItemPedidoModel> {

	private Long produtoId;

	private String produtoNome;

	private Integer quantidade;

	private BigDecimal precoUnitario;

	private BigDecimal precoTotal;

	private String observacao;

}
