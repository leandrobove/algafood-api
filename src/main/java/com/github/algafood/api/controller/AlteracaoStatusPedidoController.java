package com.github.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.algafood.domain.service.AlteracaoStatusPedidoService;

@RestController
@RequestMapping(value = "/pedidos/{codigoPedido}")
public class AlteracaoStatusPedidoController {

	@Autowired
	private AlteracaoStatusPedidoService alteracaoStatusPedidoService;

	@PutMapping(value = "/confirmacao")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void confirmar(@PathVariable String codigoPedido) {
		alteracaoStatusPedidoService.confirmar(codigoPedido);
	}

	@PutMapping(value = "/entrega")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void entregar(@PathVariable String codigoPedido) {
		alteracaoStatusPedidoService.entregar(codigoPedido);
	}
	
	@PutMapping(value = "/cancelamento")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void cancelar(@PathVariable String codigoPedido) {
		alteracaoStatusPedidoService.cancelar(codigoPedido);
	}
}
