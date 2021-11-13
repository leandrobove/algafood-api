package com.github.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.algafood.domain.filter.VendaDiariaFilter;
import com.github.algafood.domain.model.dto.VendaDiaria;
import com.github.algafood.domain.service.VendaQueryService;

@RestController
@RequestMapping(value = "/estatisticas")
public class EstatisticasController {
	
	@Autowired
	private VendaQueryService vendaQueryService;

	@GetMapping(value = "/vendas-diarias")
	public List<VendaDiaria> listarPorVendasDiarias(VendaDiariaFilter filter) {
		return vendaQueryService.listarVendasDiarias(filter);
	}

}
