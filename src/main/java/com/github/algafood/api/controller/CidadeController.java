package com.github.algafood.api.controller;

import java.util.List;

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

import com.github.algafood.api.ResourceUriHelper;
import com.github.algafood.api.assembler.CidadeModelAssembler;
import com.github.algafood.api.assembler.input.CidadeInputDisassembler;
import com.github.algafood.api.dto.CidadeModel;
import com.github.algafood.api.dto.input.CidadeInput;
import com.github.algafood.api.exceptionhandler.Problem;
import com.github.algafood.core.security.CheckSecurity;
import com.github.algafood.domain.exception.EstadoNaoEncontradoException;
import com.github.algafood.domain.exception.NegocioException;
import com.github.algafood.domain.model.Cidade;
import com.github.algafood.domain.repository.CidadeRepository;
import com.github.algafood.domain.service.CadastroCidadeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@Api(tags = "Cidades")

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cidadeService;

	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;

	@CheckSecurity.Cidades.PodeConsultar
	@ApiOperation(value = "Lista as cidades")
	@GetMapping
	public CollectionModel<CidadeModel> listar() {

		List<Cidade> cidades = cidadeRepository.findAll();

		return cidadeModelAssembler.toCollectionModel(cidades);
	}

	@CheckSecurity.Cidades.PodeConsultar
	@ApiOperation(value = "Busca uma cidade por ID")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "400", description = "ID da Cidade é inválido", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class)))	
	})
	@GetMapping(value = "/{cidadeId}")
	public CidadeModel buscar(@ApiParam(value = "ID de uma cidade") @PathVariable Long cidadeId) {

		Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);

		return cidadeModelAssembler.toModel(cidade);
	}

	@CheckSecurity.Cidades.PodeEditar
	@ApiOperation(value = "Cadastra uma cidade")
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
	@ApiOperation(value = "Atualiza uma cidade por ID")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class)))	
	})
	@PutMapping(value = "/{cidadeId}")
	public CidadeModel atualizar(@ApiParam(value = "ID de uma cidade") @PathVariable Long cidadeId,
			@RequestBody @Valid CidadeInput cidadeDTOInput) {

		var cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);

		cidadeInputDisassembler.copyToDomainObject(cidadeDTOInput, cidadeAtual);

		try {
			return cidadeModelAssembler.toModel(cidadeService.salvar(cidadeAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@CheckSecurity.Cidades.PodeEditar
	@ApiOperation(value = "Exclui uma cidade por ID")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class)))	
	})
	@DeleteMapping(value = "/{cidadeId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletar(@ApiParam(value = "ID de uma cidade") @PathVariable Long cidadeId) {
		cidadeService.excluir(cidadeId);
	}
}
