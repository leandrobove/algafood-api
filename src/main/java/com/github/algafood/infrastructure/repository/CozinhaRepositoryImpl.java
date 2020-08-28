package com.github.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.algafood.domain.entity.Cozinha;
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
	public void remover(Cozinha cozinha) {
		Cozinha buscarPorId = buscar(cozinha.getId());
		em.remove(buscarPorId);
	}

}
