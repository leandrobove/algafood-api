package com.github.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.algafood.domain.core.validation.Offset;
import com.github.algafood.domain.filter.VendaDiariaFilter;
import com.github.algafood.domain.model.dto.VendaDiaria;
import com.github.algafood.domain.service.VendaQueryService;
import com.github.algafood.domain.service.VendaReportService;

@RestController
@RequestMapping(value = "/estatisticas")
@Validated
public class EstatisticasController {
	
	@Autowired
	private VendaQueryService vendaQueryService;
	
	@Autowired
	private VendaReportService vendaReportService;
	
	@GetMapping(value = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaDiaria> listarPorVendasDiarias(VendaDiariaFilter filter, @RequestParam(required = false, defaultValue = "+00:00") @Offset String timeOffset) {
		return vendaQueryService.listarVendasDiarias(filter, timeOffset);
	}

	@GetMapping(value = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> listarPorVendasDiariasPdf(VendaDiariaFilter filter, @RequestParam(required = false, defaultValue = "+00:00") @Offset String timeOffset) {
		
		byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filter, timeOffset);
		
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(bytesPdf);
	}
	
	

}
