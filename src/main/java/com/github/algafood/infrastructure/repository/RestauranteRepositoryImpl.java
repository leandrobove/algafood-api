package com.github.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.algafood.domain.model.Restaurante;
import com.github.algafood.domain.repository.RestauranteRepository;

@Component
public class RestauranteRepositoryImpl implements RestauranteRepository {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	public Restaurante salvar(Restaurante restaurante) {
		return em.merge(restaurante);
	}

	@Override
	public List<Restaurante> listar() {
		return em.createQuery("from Restaurante", Restaurante.class).getResultList();
	}

	@Transactional
	@Override
	public void remover(Long id) {
		Restaurante restaurandeBuscado = buscar(id);

		if (restaurandeBuscado == null) {
			throw new EmptyResultDataAccessException(1);
		}

		em.remove(restaurandeBuscado);
	}

	@Override
	public Restaurante buscar(Long id) {
		return em.find(Restaurante.class, id);
	}

}
