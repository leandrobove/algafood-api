package com.github.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.algafood.api.assembler.RestauranteApenasNomeModelAssembler;
import com.github.algafood.api.assembler.RestauranteBasicoModelAssembler;
import com.github.algafood.api.assembler.RestauranteModelAssembler;
import com.github.algafood.api.assembler.input.RestauranteInputDisassembler;
import com.github.algafood.api.dto.RestauranteApenasNomeModel;
import com.github.algafood.api.dto.RestauranteBasicoModel;
import com.github.algafood.api.dto.RestauranteModel;
import com.github.algafood.api.dto.input.RestauranteInput;
import com.github.algafood.api.openapi.controller.RestauranteControllerOpenApi;
import com.github.algafood.core.security.CheckSecurity;
import com.github.algafood.domain.exception.CidadeNaoEncontradaException;
import com.github.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.github.algafood.domain.exception.NegocioException;
import com.github.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.github.algafood.domain.model.Restaurante;
import com.github.algafood.domain.repository.RestauranteRepository;
import com.github.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private RestauranteModelAssembler restauranteAssembler;
	
	@Autowired
	private RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;
	
	@Autowired
	private RestauranteApenasNomeModelAssembler restauranteApenasNomeModelAssembler;

	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;

	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping
	public CollectionModel<RestauranteBasicoModel> listar() {
		return restauranteBasicoModelAssembler.toCollectionModel(restauranteRepository.findAll());
	}

	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping(value = "/{restauranteId}")
	public RestauranteModel buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

		return restauranteAssembler.toModel(restaurante);
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping(params = "projecao=apenas-nome")
	public CollectionModel<RestauranteApenasNomeModel> listarApenasNomes() {
		return restauranteApenasNomeModelAssembler.toCollectionModel(restauranteRepository.findAll());
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteModel cadastrar(@RequestBody @Valid RestauranteInput restauranteDTOInput) {
		try {
			Restaurante restaurante = restauranteInputDisassembler.toRestaurante(restauranteDTOInput);

			return restauranteAssembler.toModel(cadastroRestaurante.salvar(restaurante));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping(value = "/{restauranteId}")
	public RestauranteModel atualizar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteInput restauranteDTOInput) {

		var restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);

		restauranteInputDisassembler.copyToDomainObject(restauranteDTOInput, restauranteAtual);

		try {
			return restauranteAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@DeleteMapping(value = "/{restauranteId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long restauranteId) {
		cadastroRestaurante.deletar(restauranteId);
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping(value = "/{restauranteId}/ativo")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.ativar(restauranteId);
		
		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@DeleteMapping(value = "/{restauranteId}/inativo")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.inativar(restauranteId);
		
		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping(value = "/ativacoes")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.ativar(restauranteIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@DeleteMapping(value = "/ativacoes")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.inativar(restauranteIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@PutMapping(value = "/{restauranteId}/abertura")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
		cadastroRestaurante.abrir(restauranteId);
		
		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@PutMapping(value = "/{restauranteId}/fechamento")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
		cadastroRestaurante.fechar(restauranteId);
		
		return ResponseEntity.noContent().build();
	}
}
