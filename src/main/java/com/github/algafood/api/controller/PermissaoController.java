package com.github.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.algafood.api.assembler.PermissaoModelAssembler;
import com.github.algafood.api.dto.PermissaoModel;
import com.github.algafood.api.openapi.controller.PermissaoControllerOpenApi;
import com.github.algafood.core.security.CheckSecurity;
import com.github.algafood.domain.model.Permissao;
import com.github.algafood.domain.repository.PermissaoRepository;

@RestController
@RequestMapping(value = "/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerOpenApi {

	@Autowired
	private PermissaoRepository permissaoRepository;

	@Autowired
	private PermissaoModelAssembler permissaoModelAssembler;

	@Override
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@GetMapping
	public CollectionModel<PermissaoModel> listar() {

		List<Permissao> permissoes = permissaoRepository.findAll();

		return permissaoModelAssembler.toCollectionModel(permissoes);
	}
}
