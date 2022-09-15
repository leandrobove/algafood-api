package com.github.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.algafood.api.openapi.controller.FluxoPedidoControllerOpenApi;
import com.github.algafood.core.security.CheckSecurity;
import com.github.algafood.domain.service.FluxoPedidoService;

@RestController
@RequestMapping(value = "/pedidos/{codigoPedido}", produces = MediaType.APPLICATION_JSON_VALUE)
public class FluxoPedidoController implements FluxoPedidoControllerOpenApi {

	@Autowired
	private FluxoPedidoService fluxoPedidoService;

	@CheckSecurity.Pedidos.PodeGerenciar
	@PutMapping(value = "/confirmacao")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> confirmar(@PathVariable String codigoPedido) {
		fluxoPedidoService.confirmar(codigoPedido);
		
		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.Pedidos.PodeGerenciar
	@PutMapping("/cancelamento")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> cancelar(@PathVariable String codigoPedido) {
		fluxoPedidoService.cancelar(codigoPedido);

		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Pedidos.PodeGerenciar
	@PutMapping("/entrega")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> entregar(@PathVariable String codigoPedido) {
		fluxoPedidoService.entregar(codigoPedido);

		return ResponseEntity.noContent().build();
	}

}
