package com.github.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.algafood.domain.core.validation.Offset;
import com.github.algafood.domain.filter.VendaDiariaFilter;
import com.github.algafood.domain.model.dto.VendaDiaria;
import com.github.algafood.domain.service.VendaQueryService;

@RestController
@RequestMapping(value = "/estatisticas")
@Validated
public class EstatisticasController {
	
	@Autowired
	private VendaQueryService vendaQueryService;

	@GetMapping(value = "/vendas-diarias")
	public List<VendaDiaria> listarPorVendasDiarias(VendaDiariaFilter filter, @RequestParam(required = false, defaultValue = "+00:00") @Offset String timeOffset) {
		return vendaQueryService.listarVendasDiarias(filter, timeOffset);
	}

}
