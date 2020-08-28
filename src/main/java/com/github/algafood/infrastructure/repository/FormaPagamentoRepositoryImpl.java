package com.github.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.algafood.domain.entity.FormaPagamento;
import com.github.algafood.domain.repository.FormaPagamentoRepository;

@Component
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<FormaPagamento> listar() {
		return em.createQuery("from FormaPagamento", FormaPagamento.class).getResultList();
	}

	@Override
	public FormaPagamento buscar(Long id) {
		return em.find(FormaPagamento.class, id);
	}

	@Transactional
	@Override
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return em.merge(formaPagamento);
	}

	@Transactional
	@Override
	public void remover(FormaPagamento formaPagamento) {
		formaPagamento = buscar(formaPagamento.getId());
		em.remove(formaPagamento);
	}

}
