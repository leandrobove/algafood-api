package com.github.algafood.api.controller;

import java.util.List;
import java.util.Optional;

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
import com.github.algafood.domain.model.Estado;
import com.github.algafood.domain.repository.EstadoRepository;
import com.github.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CadastroEstadoService cadastroEstadoService;

	@GetMapping
	public List<Estado> listar() {
		return estadoRepository.findAll();
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Estado> buscar(@PathVariable Long id) {
		Optional<Estado> estado = estadoRepository.findById(id);

		if (estado.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(estado.get());
	}

	@PostMapping
	public ResponseEntity<Estado> cadastrar(@RequestBody Estado estado) {
		Estado estadoSalvo = cadastroEstadoService.salvar(estado);

		return ResponseEntity.status(HttpStatus.CREATED).body(estadoSalvo);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		try {
			cadastroEstadoService.deletar(id);
			return ResponseEntity.noContent().build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();

		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Estado> atualizar(@PathVariable Long id, @RequestBody Estado estado) {
		Optional<Estado> estadoBusca = estadoRepository.findById(id);

		if (estadoBusca.isPresent()) {

			BeanUtils.copyProperties(estado, estadoBusca.get(), "id");

			Estado estadoAtualizado = estadoRepository.save(estadoBusca.get());

			return ResponseEntity.status(HttpStatus.OK).body(estadoAtualizado);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

}
