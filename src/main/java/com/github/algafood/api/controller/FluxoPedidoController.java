package com.github.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.algafood.domain.service.FluxoPedidoService;

@RestController
@RequestMapping(value = "/pedidos/{codigoPedido}")
public class FluxoPedidoController {

	@Autowired
	private FluxoPedidoService fluxoPedidoService;

	@PutMapping(value = "/confirmacao")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void confirmar(@PathVariable String codigoPedido) {

		fluxoPedidoService.confirmar(codigoPedido);

	}

	@PutMapping("/cancelamento")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void cancelar(@PathVariable String codigoPedido) {

		fluxoPedidoService.cancelar(codigoPedido);

	}

}
