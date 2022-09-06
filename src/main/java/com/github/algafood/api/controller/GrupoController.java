package com.github.algafood.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import com.github.algafood.api.assembler.GrupoModelAssembler;
import com.github.algafood.api.assembler.input.GrupoInputDisassembler;
import com.github.algafood.api.dto.GrupoModel;
import com.github.algafood.api.dto.input.GrupoInput;
import com.github.algafood.core.security.CheckSecurity;
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
	private GrupoModelAssembler grupoAssembler;

	@Autowired
	private GrupoInputDisassembler grupoInputDisassembler;

	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@GetMapping
	public CollectionModel<GrupoModel> listar() {
		return grupoAssembler.toCollectionModel(grupoRepository.findAll());
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@GetMapping(value = "/{grupoId}")
	public GrupoModel buscarPorId(@PathVariable Long grupoId) {
		return grupoAssembler.toModel(grupoService.buscarOuFalhar(grupoId));
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public GrupoModel cadastrar(@RequestBody @Valid GrupoInput grupoInput) {

		return grupoAssembler.toModel(grupoService.salvar(grupoInputDisassembler.toGrupo(grupoInput)));
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@PutMapping(value = "/{grupoId}")
	public GrupoModel atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {

		Grupo grupoAtual = grupoService.buscarOuFalhar(grupoId);

		grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);

		return grupoAssembler.toModel(grupoService.salvar(grupoAtual));
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@DeleteMapping(value = "/{grupoId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long grupoId) {
		grupoService.deletar(grupoId);
	}

}
