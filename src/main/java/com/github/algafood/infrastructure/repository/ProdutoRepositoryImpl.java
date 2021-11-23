package com.github.algafood.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.algafood.domain.model.FotoProduto;
import com.github.algafood.domain.repository.ProdutoRepositoryQueries;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {
	
	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	public FotoProduto save(FotoProduto foto) {
		return em.merge(foto);
	}

	@Transactional
	@Override
	public void delete(FotoProduto foto) {
		em.remove(foto);
	}

}
