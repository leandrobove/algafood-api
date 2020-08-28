package com.github.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.algafood.domain.entity.Permissao;
import com.github.algafood.domain.repository.PermissaoRepository;

@Component
public class PermissaoRepositoryImpl implements PermissaoRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Permissao> listar() {
		return em.createQuery("from Permissao", Permissao.class).getResultList();
	}

	@Override
	public Permissao buscar(Long id) {
		return em.find(Permissao.class, id);
	}

	@Transactional
	@Override
	public Permissao salvar(Permissao permissao) {
		return em.merge(permissao);
	}

	@Transactional
	@Override
	public void remover(Permissao permissao) {
		permissao = buscar(permissao.getId());
		em.remove(permissao);
	}

}
