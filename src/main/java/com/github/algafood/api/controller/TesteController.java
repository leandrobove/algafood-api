package com.github.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.algafood.domain.model.Restaurante;
import com.github.algafood.domain.repository.RestauranteRepository;

@RestController

@RequestMapping(value = "/teste")
public class TesteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@GetMapping(value = "/buscarPorFormaPagamento/{id}")
	private List<Restaurante> buscarPorFormaPagamento(@PathVariable(value = "id") Long formaPagamentoId) {
		return restauranteRepository.buscarPorFormaPagamentoId(formaPagamentoId);
	}

}
