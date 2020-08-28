package com.github.algafood.domain.repository;

import java.util.List;

import com.github.algafood.domain.entity.Cozinha;

public interface CozinhaRepository {

	List<Cozinha> listar();
	Cozinha buscar(Long id);
	Cozinha salvar(Cozinha cozinha);
	void remover(Cozinha cozinha);

}
