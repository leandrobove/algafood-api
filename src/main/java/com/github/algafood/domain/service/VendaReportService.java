package com.github.algafood.domain.service;

import com.github.algafood.domain.filter.VendaDiariaFilter;

public interface VendaReportService {

	byte[] emitirVendasDiarias(VendaDiariaFilter vendaDiariaFilter, String timeOffset);
}
