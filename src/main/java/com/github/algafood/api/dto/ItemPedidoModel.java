package com.github.algafood.api.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoModel {

	private Long produtoId;

	private String produtoNome;

	private Integer quantidade;

	private BigDecimal precoUnitario;

	private BigDecimal precoTotal;

	private String observacao;

}
