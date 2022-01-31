package com.github.algafood.api.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteModel {

	private Long id;

	private String nome;

	private BigDecimal taxaFrete;

	private CozinhaModel cozinha;

	private Boolean ativo;
	
	private Boolean aberto;
	
	private EnderecoModel endereco;
}
