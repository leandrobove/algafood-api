package com.github.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.algafood.domain.entity.Cidade;
import com.github.algafood.domain.repository.CidadeRepository;

@Component
public class CidadeRepositoryImpl implements CidadeRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Cidade> listar() {
		return em.createQuery("from Cidade", Cidade.class).getResultList();
	}

	@Override
	public Cidade buscar(Long id) {
		return em.find(Cidade.class, id);
	}

	@Transactional
	@Override
	public Cidade salvar(Cidade cidade) {
		return em.merge(cidade);
	}

	@Transactional
	@Override
	public void remover(Cidade cidade) {
		cidade = buscar(cidade.getId());
		em.remove(cidade);
	}

}
