package com.github.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.github.algafood.api.assembler.ProdutoAssembler;
import com.github.algafood.api.assembler.input.ProdutoInputDisassembler;
import com.github.algafood.api.dto.ProdutoDTO;
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
	private ProdutoAssembler produtoAssembler;

	@Autowired
	private CadastroRestauranteService restauranteService;

	@Autowired
	private CadastroProdutoService produtoService;

	@Autowired
	private ProdutoInputDisassembler produtoInputDisassembler;

	@Autowired
	private ProdutoRepository produtoRepository;

	@GetMapping
	public List<ProdutoDTO> listar(@PathVariable Long restauranteId,
			@RequestParam(required = false) boolean incluirInativos) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

		List<Produto> restaurantesRetorno = null;

		if (incluirInativos) {
			// listar todos
			restaurantesRetorno = produtoRepository.findTodosByRestaurante(restaurante);
		} else {
			// listar somente produtos ativos
			restaurantesRetorno = produtoRepository.findAtivosByRestaurante(restaurante);
		}

		return produtoAssembler.toListDTO(restaurantesRetorno);
	}

	@GetMapping(value = "/{produtoId}")
	public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		// trata exception caso o restaurante não exista
		restauranteService.buscarOuFalhar(restauranteId);

		Produto produto = produtoService.buscarOuFalhar(produtoId, restauranteId);

		return produtoAssembler.toDTO(produto);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ProdutoDTO cadastrar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {

		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

		Produto produto = produtoInputDisassembler.toProduto(produtoInput);
		produto.setRestaurante(restaurante);

		return produtoAssembler.toDTO(produtoService.salvar(produto));
	}

	@PutMapping(value = "/{produtoId}")
	public ProdutoDTO atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@RequestBody @Valid ProdutoInput produtoInput) {
		// trata exception caso o restaurante não exista
		restauranteService.buscarOuFalhar(restauranteId);

		Produto produtoAtual = produtoService.buscarOuFalhar(produtoId, restauranteId);

		produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);

		Produto produto = produtoService.salvar(produtoAtual);

		return produtoAssembler.toDTO(produto);
	}

}
