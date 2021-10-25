package com.github.algafood.domain.model.mixin;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.algafood.domain.model.Cozinha;
import com.github.algafood.domain.model.Endereco;
import com.github.algafood.domain.model.FormaPagamento;
import com.github.algafood.domain.model.Produto;

public class RestauranteMixin {
	
	/*
	 * Autorizar a serialização de consulta e negar de cadastro
	 */
	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Cozinha cozinha;

	@JsonIgnore
	private Endereco endereco;

	@JsonIgnore
	private OffsetDateTime dataCadastro;

	@JsonIgnore
	private OffsetDateTime dataAtualizacao;

	@JsonIgnore
	private List<FormaPagamento> formasPagamento = new ArrayList<FormaPagamento>();

	@JsonIgnore
	private List<Produto> produtos = new ArrayList<Produto>();

}
