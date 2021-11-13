package com.github.algafood.domain.service;

import java.util.List;

import com.github.algafood.domain.filter.VendaDiariaFilter;
import com.github.algafood.domain.model.dto.VendaDiaria;

public interface VendaQueryService {

	List<VendaDiaria> listarVendasDiarias(VendaDiariaFilter filter);

}
