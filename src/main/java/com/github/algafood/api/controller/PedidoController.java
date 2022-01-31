package com.github.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.algafood.api.assembler.PedidoModelAssembler;
import com.github.algafood.api.assembler.PedidoResumoModelAssembler;
import com.github.algafood.api.assembler.input.PedidoInputDisassembler;
import com.github.algafood.api.dto.PedidoModel;
import com.github.algafood.api.dto.PedidoResumoModel;
import com.github.algafood.api.dto.input.PedidoInput;
import com.github.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.github.algafood.domain.exception.NegocioException;
import com.github.algafood.domain.filter.PedidoFilter;
import com.github.algafood.domain.model.Pedido;
import com.github.algafood.domain.model.Usuario;
import com.github.algafood.domain.repository.PedidoRepository;
import com.github.algafood.domain.service.EmissaoPedidoService;
import com.github.algafood.infrastructure.repository.spec.PedidoSpecs;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PedidoModelAssembler pedidoAssembler;

	@Autowired
	private PedidoResumoModelAssembler pedidoResumoDTOAssembler;

	@Autowired
	private EmissaoPedidoService emissaoPedidoService;

	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;

	@GetMapping
	public Page<PedidoResumoModel> listarComFiltro(PedidoFilter pedidoFilter,
			@PageableDefault(size = 10) Pageable pageable) {
		
		Page<Pedido> pedidoPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(pedidoFilter), pageable);

		List<PedidoResumoModel> pedidosDTOPage = pedidoResumoDTOAssembler.toCollectionModel(pedidoPage.getContent());

		return new PageImpl<PedidoResumoModel>(pedidosDTOPage, pageable, pedidoPage.getTotalElements());
	}

	@GetMapping(value = "/{codigoPedido}")
	public PedidoModel buscar(@PathVariable String codigoPedido) {
		return pedidoAssembler.toModel(emissaoPedidoService.buscarOuFalhar(codigoPedido));
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public PedidoModel cadastrar(@RequestBody @Valid PedidoInput pedidoInput) {

		try {
			Pedido pedido = pedidoInputDisassembler.toPedido(pedidoInput);

			// autenticar usuario
			Usuario cliente = new Usuario();
			cliente.setId(1L);

			pedido.setCliente(cliente);

			Pedido pedidoSalvo = emissaoPedidoService.emitir(pedido);

			return pedidoAssembler.toModel(pedidoSalvo);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
}
