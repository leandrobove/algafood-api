package com.github.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.algafood.api.assembler.RestauranteAssembler;
import com.github.algafood.api.assembler.RestauranteInputDisassembler;
import com.github.algafood.api.dto.RestauranteDTO;
import com.github.algafood.api.dto.input.RestauranteInput;
import com.github.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.github.algafood.domain.exception.NegocioException;
import com.github.algafood.domain.model.Restaurante;
import com.github.algafood.domain.repository.RestauranteRepository;
import com.github.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private RestauranteAssembler restauranteDTOAssembler;
	
	@Autowired
	private RestauranteInputDisassembler restauranteDTOInputDisassembler;

	@GetMapping
	public List<RestauranteDTO> listar() {
		return restauranteDTOAssembler.toListDTO(restauranteRepository.findAll());
	}

	@GetMapping(value = "/{id}")
	public RestauranteDTO buscar(@PathVariable Long id) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(id);

		return restauranteDTOAssembler.toDTO(restaurante);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO cadastrar(@RequestBody @Valid RestauranteInput restauranteDTOInput) {
		try {
			Restaurante restaurante = restauranteDTOInputDisassembler.toRestaurante(restauranteDTOInput);
			
			return restauranteDTOAssembler.toDTO(cadastroRestaurante.salvar(restaurante));
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping(value = "/{id}")
	public RestauranteDTO atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInput restauranteDTOInput) {
		
		var restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);
		
		restauranteDTOInputDisassembler.copyToDomainObject(restauranteDTOInput, restauranteAtual);

		try {
			return restauranteDTOAssembler.toDTO(cadastroRestaurante.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping(value = "/{id}")
	public void deletar(@PathVariable Long id) {
		cadastroRestaurante.deletar(id);
	}
	
	@PutMapping(value = "/{id}/ativo")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable(name = "id") Long restauranteId) {
		cadastroRestaurante.ativar(restauranteId);
	}
	
	@DeleteMapping(value = "/{id}/inativo")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable(name = "id") Long restauranteId) {
		cadastroRestaurante.inativar(restauranteId);
	}

	/*@PatchMapping(value = "/{id}")
	public RestauranteDTO atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> mapCampos,
			HttpServletRequest request) {

		Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);

		// lambda expression
		merge(mapCampos, restauranteAtual, request);

		return atualizar(id, restauranteAtual);
	}

	private void merge(Map<String, Object> mapCamposOrigem, Restaurante restauranteDestino,
			HttpServletRequest request) {

		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

		try {
			ObjectMapper objectMapper = new ObjectMapper();

			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

			Restaurante restauranteOrigem = objectMapper.convertValue(mapCamposOrigem, Restaurante.class);

			mapCamposOrigem.forEach((chave, valor) -> {
				Field field = ReflectionUtils.findField(Restaurante.class, chave);
				field.setAccessible(true);

				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

				ReflectionUtils.setField(field, restauranteDestino, novoValor);
			});
		} catch (IllegalArgumentException e) {
			// Relançar a exception para ser capturada no handler
			Throwable rootCause = ExceptionUtils.getRootCause(e);

			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}

	}*/

}
