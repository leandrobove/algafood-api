package com.github.algafood.domain.repository;

import java.util.List;

import com.github.algafood.domain.entity.Restaurante;

public interface RestauranteRepository {
	
	Restaurante salvar(Restaurante restaurante);
	List<Restaurante> listar();
	void remover(Restaurante restaurante);
	Restaurante buscar(Long id);

}
