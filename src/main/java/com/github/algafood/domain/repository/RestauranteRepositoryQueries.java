package com.github.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.github.algafood.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> consultarPorNomeETaxaFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);
	List<Restaurante> buscarPorCozinhaId(Long cozinhaId);
	List<Restaurante> buscarComFreteGratisENomeSemelhante(String nome);
	List<Restaurante> buscarPorFormaPagamentoId(Long formaPagamentoId);
	boolean isResponsavel(Long usuarioId, Long restauranteId);
}