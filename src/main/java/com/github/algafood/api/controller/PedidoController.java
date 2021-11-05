package com.github.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.algafood.api.assembler.PedidoAssembler;
import com.github.algafood.api.dto.PedidoDTO;
import com.github.algafood.domain.repository.PedidoRepository;
import com.github.algafood.domain.service.EmissaoPedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PedidoAssembler pedidoAssembler;

	@Autowired
	private EmissaoPedidoService emissaoPedidoService;

	@GetMapping
	public List<PedidoDTO> listar() {
		return pedidoAssembler.toListDTO(pedidoRepository.findAll());
	}

	@GetMapping(value = "/{pedidoId}")
	public PedidoDTO buscar(@PathVariable Long pedidoId) {
		return pedidoAssembler.toDTO(emissaoPedidoService.buscarOuFalhar(pedidoId));
	}
}
