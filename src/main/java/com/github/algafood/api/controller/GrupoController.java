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

import com.github.algafood.api.assembler.GrupoAssembler;
import com.github.algafood.api.assembler.input.GrupoInputDisassembler;
import com.github.algafood.api.dto.GrupoDTO;
import com.github.algafood.api.dto.input.GrupoInput;
import com.github.algafood.domain.model.Grupo;
import com.github.algafood.domain.repository.GrupoRepository;
import com.github.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(value = "/grupos")
public class GrupoController {

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private CadastroGrupoService grupoService;

	@Autowired
	private GrupoAssembler grupoAssembler;

	@Autowired
	private GrupoInputDisassembler grupoInputDisassembler;

	@GetMapping
	public List<GrupoDTO> listar() {
		return grupoAssembler.toListDTO(grupoRepository.findAll());
	}

	@GetMapping(value = "/{grupoId}")
	public GrupoDTO buscarPorId(@PathVariable Long grupoId) {
		return grupoAssembler.toDTO(grupoService.buscarOuFalhar(grupoId));
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public GrupoDTO cadastrar(@RequestBody @Valid GrupoInput grupoInput) {

		return grupoAssembler.toDTO(grupoService.salvar(grupoInputDisassembler.toGrupo(grupoInput)));
	}

	@PutMapping(value = "/{grupoId}")
	public GrupoDTO atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {

		Grupo grupoAtual = grupoService.buscarOuFalhar(grupoId);

		grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);

		return grupoAssembler.toDTO(grupoService.salvar(grupoAtual));
	}

	@DeleteMapping(value = "/{grupoId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long grupoId) {
		grupoService.deletar(grupoId);
	}

}
