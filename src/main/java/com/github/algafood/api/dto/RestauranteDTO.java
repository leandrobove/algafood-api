package com.github.algafood.api.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteDTO {

	private Long id;

	private String nome;

	private BigDecimal frete;

	private CozinhaDTO cozinha;
}
