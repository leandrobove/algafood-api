package com.github.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.algafood.domain.exception.EntidadeEmUsoException;
import com.github.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.github.algafood.domain.model.Cidade;
import com.github.algafood.domain.repository.CidadeRepository;
import com.github.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cidadeService;

	@GetMapping
	public List<Cidade> listar() {
		return cidadeRepository.listar();
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Cidade> buscar(@PathVariable Long id) {
		Cidade cidade = cidadeRepository.buscar(id);

		if (cidade != null) {
			return ResponseEntity.status(HttpStatus.OK).body(cidade);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody Cidade cidade) {
		try {
			Cidade cidadeSalva = cidadeService.salvar(cidade);

			return ResponseEntity.status(HttpStatus.CREATED).body(cidadeSalva);

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Cidade cidade) {

		try {
			Cidade cidadeAtual = cidadeRepository.buscar(id);

			if (cidadeAtual != null) {
				BeanUtils.copyProperties(cidade, cidadeAtual, "id");

				Cidade cidadeSalva = cidadeService.salvar(cidadeAtual);

				return ResponseEntity.status(HttpStatus.OK).body(cidadeSalva);
			}

			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		try {
			cidadeService.excluir(id);

			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

	}
}
