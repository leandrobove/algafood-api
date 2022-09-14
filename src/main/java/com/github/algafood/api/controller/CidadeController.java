package com.github.algafood.api.controller;

import java.util.List;

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

import com.github.algafood.api.ResourceUriHelper;
import com.github.algafood.api.assembler.CidadeModelAssembler;
import com.github.algafood.api.assembler.input.CidadeInputDisassembler;
import com.github.algafood.api.controller.openapi.CidadeControllerOpenApi;
import com.github.algafood.api.dto.CidadeModel;
import com.github.algafood.api.dto.input.CidadeInput;
import com.github.algafood.core.security.CheckSecurity;
import com.github.algafood.domain.exception.EstadoNaoEncontradoException;
import com.github.algafood.domain.exception.NegocioException;
import com.github.algafood.domain.model.Cidade;
import com.github.algafood.domain.repository.CidadeRepository;
import com.github.algafood.domain.service.CadastroCidadeService;


@RestController
@RequestMapping(value = "/cidades")
public class CidadeController implements CidadeControllerOpenApi {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cidadeService;

	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;

	@CheckSecurity.Cidades.PodeConsultar
	@GetMapping
	public CollectionModel<CidadeModel> listar() {

		List<Cidade> cidades = cidadeRepository.findAll();

		return cidadeModelAssembler.toCollectionModel(cidades);
	}

	@CheckSecurity.Cidades.PodeConsultar
	@GetMapping(value = "/{cidadeId}")
	public CidadeModel buscar(@PathVariable Long cidadeId) {

		Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);

		return cidadeModelAssembler.toModel(cidade);
	}

	@CheckSecurity.Cidades.PodeEditar
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel cadastrar(@RequestBody @Valid CidadeInput cidadeDTOInput) {
		try {
			Cidade cidade = cidadeInputDisassembler.toCidade(cidadeDTOInput);

			Cidade cidadeSalva = cidadeService.salvar(cidade);

			CidadeModel cidadeDTO = cidadeModelAssembler.toModel(cidadeSalva);

			ResourceUriHelper.addUriInResponseHeader(cidadeDTO.getId());

			return cidadeDTO;
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@CheckSecurity.Cidades.PodeEditar
	@PutMapping(value = "/{cidadeId}")
	public CidadeModel atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeDTOInput) {

		var cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);

		cidadeInputDisassembler.copyToDomainObject(cidadeDTOInput, cidadeAtual);

		try {
			return cidadeModelAssembler.toModel(cidadeService.salvar(cidadeAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@CheckSecurity.Cidades.PodeEditar
	@DeleteMapping(value = "/{cidadeId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long cidadeId) {
		cidadeService.excluir(cidadeId);
	}
}
