package com.github.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.github.algafood.domain.exception.EntidadeEmUsoException;
import com.github.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.github.algafood.domain.model.Cozinha;
import com.github.algafood.domain.model.Restaurante;
import com.github.algafood.domain.repository.CozinhaRepository;
import com.github.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();

		Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);

		if (cozinha == null) {
			throw new EntidadeNaoEncontradaException("Não existe cadastro de cozinha com o id: " + cozinhaId);
		}

		restaurante.setCozinha(cozinha);

		return restauranteRepository.salvar(restaurante);
	}

	public void deletar(Long id) {
		try {
			restauranteRepository.remover(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException("Não existe cadastro de restaurante com o id: " + id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("Não é possível excluir o restaurante" + id + ", pois há relacionamentos");
		}
	}

}
