package com.github.algafood.api.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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
import com.github.algafood.core.data.PageWrapper;
import com.github.algafood.core.data.PageableTranslator;
import com.github.algafood.core.security.AlgaSecurity;
import com.github.algafood.core.security.CheckSecurity;
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
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;

	@Autowired
	private EmissaoPedidoService emissaoPedidoService;

	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;

	@Autowired
	private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;
	
	@Autowired
	private AlgaSecurity algaSecurity;

	@GetMapping
	public PagedModel<PedidoResumoModel> listarComFiltro(PedidoFilter pedidoFilter,
			@PageableDefault(size = 10) Pageable pageable) {
		
		Pageable pageableTraduzido = this.traduzirPageable(pageable);

		Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(pedidoFilter), pageableTraduzido);
		
		pedidosPage = new PageWrapper<Pedido>(pedidosPage, pageable);
		
		PagedModel<PedidoResumoModel> pedidosPagedModel = pagedResourcesAssembler.toModel(pedidosPage,
				pedidoResumoModelAssembler);

		return pedidosPagedModel;
	}

	@CheckSecurity.Pedidos.PodeBuscar
	@GetMapping(value = "/{codigoPedido}")
	public PedidoModel buscar(@PathVariable String codigoPedido) {
		return pedidoAssembler.toModel(emissaoPedidoService.buscarOuFalhar(codigoPedido));
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public PedidoModel cadastrar(@RequestBody @Valid PedidoInput pedidoInput) {

		try {
			Pedido pedido = pedidoInputDisassembler.toPedido(pedidoInput);

			// pega o id do cliente através do token JWT autenticado
			pedido.setCliente(new Usuario());
			pedido.getCliente().setId(algaSecurity.getUsuarioId());

			pedido = emissaoPedidoService.emitir(pedido);

			return pedidoAssembler.toModel(pedido);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	private Pageable traduzirPageable(Pageable pageable) {

		Map<String, String> mapeamento = Map.of(
				"codigo", "codigo",
				"subtotal", "subtotal",
				"taxaFrete", "taxaFrete",
				"valorTotal", "valorTotal",
				"dataCriacao", "dataCriacao",
				"nomerestaurante", "restaurante.nome",
				"restaurante.id", "restaurante.id",
				"cliente.id", "cliente.id",
				"cliente.nome", "cliente.nome"
		);

		return PageableTranslator.translate(pageable, mapeamento);
	}
}
