package com.github.algafood.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.algafood.api.assembler.EstadoModelAssembler;
import com.github.algafood.api.assembler.input.EstadoInputDisassembler;
import com.github.algafood.api.dto.EstadoModel;
import com.github.algafood.api.dto.input.EstadoInput;
import com.github.algafood.api.openapi.controller.EstadoControllerOpenApi;
import com.github.algafood.core.security.CheckSecurity;
import com.github.algafood.domain.repository.EstadoRepository;
import com.github.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping(value = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CadastroEstadoService cadastroEstado;

	@Autowired
	private EstadoModelAssembler estadoAssembler;

	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;

	@CheckSecurity.Estados.PodeConsultar
	@GetMapping
	public CollectionModel<EstadoModel> listar() {
		return estadoAssembler.toCollectionModel(estadoRepository.findAll());
	}

	@CheckSecurity.Estados.PodeConsultar
	@GetMapping(value = "/{estadoId}")
	public EstadoModel buscar(@PathVariable Long estadoId) {
		return estadoAssembler.toModel(cadastroEstado.buscarOuFalhar(estadoId));
	}

	@CheckSecurity.Estados.PodeEditar
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoModel cadastrar(@RequestBody @Valid EstadoInput estadoInput) {
		return estadoAssembler.toModel(cadastroEstado.salvar(estadoInputDisassembler.toEstado(estadoInput)));
	}

	@CheckSecurity.Estados.PodeEditar
	@PutMapping(value = "/{estadoId}")
	public EstadoModel atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {
		var estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);

		estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);

		return estadoAssembler.toModel(estadoRepository.save(estadoAtual));
	}

	@CheckSecurity.Estados.PodeEditar
	@DeleteMapping(value = "/{estadoId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long estadoId) {
		cadastroEstado.deletar(estadoId);
	}

}
