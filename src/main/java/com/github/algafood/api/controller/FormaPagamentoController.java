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

import com.github.algafood.api.assembler.FormaPagamentoAssembler;
import com.github.algafood.api.assembler.input.FormaPagamentoInputDisassembler;
import com.github.algafood.api.dto.FormaPagamentoDTO;
import com.github.algafood.api.dto.input.FormaPagamentoInput;
import com.github.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.github.algafood.domain.exception.NegocioException;
import com.github.algafood.domain.model.FormaPagamento;
import com.github.algafood.domain.repository.FormaPagamentoRepository;
import com.github.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping(value = "/formas-pagamento")
public class FormaPagamentoController {

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;

	@Autowired
	private FormaPagamentoAssembler formaPagamentoAssembler;

	@Autowired
	private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

	@GetMapping
	public List<FormaPagamentoDTO> listar() {
		return formaPagamentoAssembler.toListDTO(formaPagamentoRepository.findAll());
	}

	@GetMapping(value = "/{formaPagamentoId}")
	public FormaPagamentoDTO buscar(@PathVariable Long formaPagamentoId) {
		return formaPagamentoAssembler.toDTO(cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId));
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public FormaPagamentoDTO cadastrar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		try {
			FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toFormaPagamento(formaPagamentoInput);

			return formaPagamentoAssembler.toDTO(cadastroFormaPagamento.salvar(formaPagamento));
		} catch (FormaPagamentoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping(value = "/{formaPagamentoId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long formaPagamentoId) {
		cadastroFormaPagamento.deletar(formaPagamentoId);
	}

	@PutMapping(value = "/{formaPagamentoId}")
	public FormaPagamentoDTO atualizar(@PathVariable Long formaPagamentoId,
			@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {

		FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);

		formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);

		cadastroFormaPagamento.salvar(formaPagamentoAtual);

		return formaPagamentoAssembler.toDTO(formaPagamentoAtual);
	}

}
