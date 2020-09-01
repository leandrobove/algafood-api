package com.github.algafood.domain.repository;

import java.util.List;

import com.github.algafood.domain.model.Restaurante;

public interface RestauranteRepository {
	
	Restaurante salvar(Restaurante restaurante);
	List<Restaurante> listar();
	void remover(Long id);
	Restaurante buscar(Long id);

}
