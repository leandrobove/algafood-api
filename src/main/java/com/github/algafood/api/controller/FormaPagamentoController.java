package com.github.algafood.api.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.github.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.github.algafood.api.assembler.input.FormaPagamentoInputDisassembler;
import com.github.algafood.api.dto.FormaPagamentoModel;
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
	private FormaPagamentoModelAssembler formaPagamentoAssembler;

	@Autowired
	private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

	@GetMapping
	public ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request) {
		//desabilita o shallow ETag
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		String eTag = "0";
		
		OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.findDataMaisRecenteAtualizacao();
		
		if(dataUltimaAtualizacao != null) {
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}
		
		//verifica se a etag não foi alterada e retorna
		if (request.checkNotModified(eTag)) {
			return null;
		} 
		
		//caso etag seja diferente, faz a consulta normalmente
		List<FormaPagamento> formasPagamento = formaPagamentoRepository.findAll();
		
		CollectionModel<FormaPagamentoModel> formasPagamentoDto = formaPagamentoAssembler
				.toCollectionModel(formasPagamento);

		//adiciona 10 segundos de cache
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10L, TimeUnit.SECONDS).cachePublic())
				.eTag(eTag)
				.body(formasPagamentoDto);
	}

	@GetMapping(value = "/{formaPagamentoId}")
	public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long formaPagamentoId, ServletWebRequest request) {
		//desabilita o shallow ETag
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
				
		String eTag = "0";
		
		OffsetDateTime dataAtualizacao = formaPagamentoRepository.findDataAtualizacaoById(formaPagamentoId);
		
		if(dataAtualizacao != null) {
			eTag = String.valueOf(dataAtualizacao.toEpochSecond());
		}
		
		if(request.checkNotModified(eTag)) {
			return null;
		}
		
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		
		FormaPagamentoModel formaPagamentoDto = formaPagamentoAssembler.toModel(formaPagamento);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10L, TimeUnit.SECONDS))
				.eTag(eTag)
				.body(formaPagamentoDto);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public FormaPagamentoModel cadastrar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		try {
			FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toFormaPagamento(formaPagamentoInput);

			return formaPagamentoAssembler.toModel(cadastroFormaPagamento.salvar(formaPagamento));
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
	public FormaPagamentoModel atualizar(@PathVariable Long formaPagamentoId,
			@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {

		FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);

		formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);

		cadastroFormaPagamento.salvar(formaPagamentoAtual);

		return formaPagamentoAssembler.toModel(formaPagamentoAtual);
	}

}
