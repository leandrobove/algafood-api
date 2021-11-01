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

import com.github.algafood.api.assembler.EstadoAssembler;
import com.github.algafood.api.assembler.EstadoInputDisassembler;
import com.github.algafood.api.dto.EstadoDTO;
import com.github.algafood.api.dto.input.EstadoInput;
import com.github.algafood.domain.repository.EstadoRepository;
import com.github.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CadastroEstadoService cadastroEstado;

	@Autowired
	private EstadoAssembler estadoAssembler;

	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;

	@GetMapping
	public List<EstadoDTO> listar() {
		return estadoAssembler.toListDTO(estadoRepository.findAll());
	}

	@GetMapping(value = "/{estadoId}")
	public EstadoDTO buscar(@PathVariable Long estadoId) {
		return estadoAssembler.toDTO(cadastroEstado.buscarOuFalhar(estadoId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO cadastrar(@RequestBody @Valid EstadoInput estadoInput) {
		return estadoAssembler.toDTO(cadastroEstado.salvar(estadoInputDisassembler.toEstado(estadoInput)));
	}

	@PutMapping(value = "/{estadoId}")
	public EstadoDTO atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {
		var estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);

		estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);

		return estadoAssembler.toDTO(estadoRepository.save(estadoAtual));
	}

	@DeleteMapping(value = "/{estadoId}")
	public void deletar(@PathVariable Long estadoId) {
		cadastroEstado.deletar(estadoId);
	}

}
