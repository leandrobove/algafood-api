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

import com.github.algafood.api.ResourceUriHelper;
import com.github.algafood.api.assembler.CidadeAssembler;
import com.github.algafood.api.assembler.input.CidadeInputDisassembler;
import com.github.algafood.api.dto.CidadeDTO;
import com.github.algafood.api.dto.input.CidadeInput;
import com.github.algafood.domain.exception.EstadoNaoEncontradoException;
import com.github.algafood.domain.exception.NegocioException;
import com.github.algafood.domain.model.Cidade;
import com.github.algafood.domain.repository.CidadeRepository;
import com.github.algafood.domain.service.CadastroCidadeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Cidades")

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cidadeService;

	@Autowired
	private CidadeAssembler cidadeAssembler;

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;

	@ApiOperation(value = "Lista as cidades")
	@GetMapping
	public List<CidadeDTO> listar() {
		return cidadeAssembler.toListDTO(cidadeRepository.findAll());
	}

	@ApiOperation(value = "Busca uma cidade por ID")
	@GetMapping(value = "/{cidadeId}")
	public CidadeDTO buscar(@ApiParam(value = "ID de uma cidade") @PathVariable Long cidadeId) {
		return cidadeAssembler.toDTO(cidadeService.buscarOuFalhar(cidadeId));
	}

	@ApiOperation(value = "Cadastra uma cidade")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO cadastrar(@RequestBody @Valid CidadeInput cidadeDTOInput) {
		try {
			Cidade cidade = cidadeInputDisassembler.toCidade(cidadeDTOInput);
			
			Cidade cidadeSalva = cidadeService.salvar(cidade);
			
			CidadeDTO cidadeDTO = cidadeAssembler.toDTO(cidadeSalva);
			
			ResourceUriHelper.addUriInResponseHeader(cidadeDTO.getId());
			
			return cidadeDTO;
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@ApiOperation(value = "Atualiza uma cidade por ID")
	@PutMapping(value = "/{cidadeId}")
	public CidadeDTO atualizar(@ApiParam(value = "ID de uma cidade") @PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeDTOInput) {

		var cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);

		cidadeInputDisassembler.copyToDomainObject(cidadeDTOInput, cidadeAtual);

		try {
			return cidadeAssembler.toDTO(cidadeService.salvar(cidadeAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@ApiOperation(value = "Exclui uma cidade por ID")
	@DeleteMapping(value = "/{cidadeId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletar(@ApiParam(value = "ID de uma cidade") @PathVariable Long cidadeId) {
		cidadeService.excluir(cidadeId);
	}
}
