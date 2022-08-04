package com.github.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.algafood.api.AlgaLinksHelper;
import com.github.algafood.api.assembler.ProdutoModelAssembler;
import com.github.algafood.api.assembler.input.ProdutoInputDisassembler;
import com.github.algafood.api.dto.ProdutoModel;
import com.github.algafood.api.dto.input.ProdutoInput;
import com.github.algafood.domain.model.Produto;
import com.github.algafood.domain.model.Restaurante;
import com.github.algafood.domain.repository.ProdutoRepository;
import com.github.algafood.domain.service.CadastroProdutoService;
import com.github.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

	@Autowired
	private ProdutoModelAssembler produtoAssembler;

	@Autowired
	private CadastroRestauranteService restauranteService;

	@Autowired
	private CadastroProdutoService produtoService;

	@Autowired
	private ProdutoInputDisassembler produtoInputDisassembler;

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private AlgaLinksHelper algaLinksHelper;

	@GetMapping
	public CollectionModel<ProdutoModel> listar(@PathVariable Long restauranteId,
			@RequestParam(required = false, defaultValue = "false") Boolean incluirInativos) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

		List<Produto> restaurantesRetorno = null;

		if (incluirInativos) {
			// listar todos
			restaurantesRetorno = produtoRepository.findTodosByRestaurante(restaurante);
		} else {
			// listar somente produtos ativos
			restaurantesRetorno = produtoRepository.findAtivosByRestaurante(restaurante);
		}

		return produtoAssembler.toCollectionModel(restaurantesRetorno)
				.add(algaLinksHelper.linkToProdutos(restaurante.getId()));
	}

	@GetMapping(value = "/{produtoId}")
	public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		// trata exception caso o restaurante não exista
		restauranteService.buscarOuFalhar(restauranteId);

		Produto produto = produtoService.buscarOuFalhar(produtoId, restauranteId);

		return produtoAssembler.toModel(produto);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ProdutoModel cadastrar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {

		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

		Produto produto = produtoInputDisassembler.toProduto(produtoInput);
		produto.setRestaurante(restaurante);

		return produtoAssembler.toModel(produtoService.salvar(produto));
	}

	@PutMapping(value = "/{produtoId}")
	public ProdutoModel atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@RequestBody @Valid ProdutoInput produtoInput) {
		// trata exception caso o restaurante não exista
		restauranteService.buscarOuFalhar(restauranteId);

		Produto produtoAtual = produtoService.buscarOuFalhar(produtoId, restauranteId);

		produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);

		Produto produto = produtoService.salvar(produtoAtual);

		return produtoAssembler.toModel(produto);
	}

}
