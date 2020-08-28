package com.github.algafood.domain.repository;

import java.util.List;

import com.github.algafood.domain.entity.Estado;

public interface EstadoRepository {

	List<Estado> listar();
	Estado buscar(Long id);
	Estado salvar(Estado estado);
	void remover(Estado estado);

}
