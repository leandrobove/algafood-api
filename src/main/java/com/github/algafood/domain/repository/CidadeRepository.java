package com.github.algafood.domain.repository;

import java.util.List;

import com.github.algafood.domain.entity.Cidade;

public interface CidadeRepository {

	List<Cidade> listar();
	Cidade buscar(Long id);
	Cidade salvar(Cidade cidade);
	void remover(Cidade cidade);

}
