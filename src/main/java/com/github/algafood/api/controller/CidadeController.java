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

import com.github.algafood.api.assembler.CidadeAssembler;
import com.github.algafood.api.assembler.CidadeInputDisassembler;
import com.github.algafood.api.dto.CidadeDTO;
import com.github.algafood.api.dto.input.CidadeInput;
import com.github.algafood.domain.exception.EstadoNaoEncontradoException;
import com.github.algafood.domain.exception.NegocioException;
import com.github.algafood.domain.repository.CidadeRepository;
import com.github.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cidadeService;

	@Autowired
	private CidadeAssembler cidadeDTOAssembler;

	@Autowired
	private CidadeInputDisassembler cidadeDTOInputDisassembler;

	@GetMapping
	public List<CidadeDTO> listar() {
		return cidadeDTOAssembler.toListDTO(cidadeRepository.findAll());
	}

	@GetMapping(value = "/{cidadeId}")
	public CidadeDTO buscar(@PathVariable Long cidadeId) {
		return cidadeDTOAssembler.toDTO(cidadeService.buscarOuFalhar(cidadeId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO cadastrar(@RequestBody @Valid CidadeInput cidadeDTOInput) {
		try {
			return cidadeDTOAssembler.toDTO(cidadeService.salvar(cidadeDTOInputDisassembler.toCidade(cidadeDTOInput)));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping(value = "/{cidadeId}")
	public CidadeDTO atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeDTOInput) {

		var cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);

		cidadeDTOInputDisassembler.copyToDomainObject(cidadeDTOInput, cidadeAtual);

		try {
			return cidadeDTOAssembler.toDTO(cidadeService.salvar(cidadeAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping(value = "/{cidadeId}")
	public void deletar(@PathVariable Long cidadeId) {
		cidadeService.excluir(cidadeId);
	}
}
