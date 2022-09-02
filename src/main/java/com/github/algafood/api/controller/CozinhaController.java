package com.github.algafood.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.algafood.api.assembler.CozinhaModelAssembler;
import com.github.algafood.api.assembler.input.CozinhaInputDisassembler;
import com.github.algafood.api.dto.CozinhaModel;
import com.github.algafood.api.dto.input.CozinhaInput;
import com.github.algafood.domain.model.Cozinha;
import com.github.algafood.domain.repository.CozinhaRepository;
import com.github.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Autowired
	private CozinhaModelAssembler cozinhaAssembler;

	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public PagedModel<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
		
		PagedModel<CozinhaModel> cozinhasPagedModel = pagedResourcesAssembler.toModel(cozinhasPage, cozinhaAssembler);
		
		return cozinhasPagedModel;
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/{cozinhaId}")
	public CozinhaModel buscar(@PathVariable Long cozinhaId) {
		return cozinhaAssembler.toModel(cadastroCozinha.buscarOuFalhar(cozinhaId));
	}

	@PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel cadastrar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		return cozinhaAssembler.toModel(cadastroCozinha.salvar(cozinhaInputDisassembler.toCozinha(cozinhaInput)));
	}

	@PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
	@PutMapping(value = "/{cozinhaId}")
	public CozinhaModel atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput) {

		Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);

		cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);

		return cozinhaAssembler.toModel(cadastroCozinha.salvar(cozinhaAtual));

	}

	@PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
	@DeleteMapping(value = "/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long cozinhaId) {
		cadastroCozinha.excluir(cozinhaId);
	}

}
