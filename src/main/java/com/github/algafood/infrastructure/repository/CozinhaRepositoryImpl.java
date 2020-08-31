package com.github.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.algafood.domain.model.Cozinha;
import com.github.algafood.domain.repository.CozinhaRepository;

@Component
public class CozinhaRepositoryImpl implements CozinhaRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Cozinha> listar() {
		TypedQuery<Cozinha> query = em.createQuery("from Cozinha", Cozinha.class);

		return query.getResultList();
	}

	@Override
	public Cozinha buscar(Long id) {
		return em.find(Cozinha.class, id);
	}

	@Override
	@Transactional
	public Cozinha salvar(Cozinha cozinha) {
		return em.merge(cozinha);
	}

	@Override
	@Transactional
	public void remover(Long id) {
		Cozinha buscarPorId = buscar(id);
		if(buscarPorId == null) {
			throw new EmptyResultDataAccessException(1);
		}
		em.remove(buscarPorId);
	}

}
