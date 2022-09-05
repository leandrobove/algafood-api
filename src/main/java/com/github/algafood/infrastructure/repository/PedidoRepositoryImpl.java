package com.github.algafood.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.github.algafood.domain.repository.PedidoRepositoryQueries;

@Repository
public class PedidoRepositoryImpl implements PedidoRepositoryQueries {

	@PersistenceContext
	private EntityManager em;

	@Override
	public boolean isPedidoGerenciadoPor(String codigoPedido, Long usuarioId) {
		String jpql = "SELECT case when count(1) > 0 then true else false end "
				+ "from Pedido ped join ped.restaurante rest join rest.responsaveis resp "
				+ "where ped.codigo = :codigoPedido and "
				+ "resp.id = :usuarioId";
		
		TypedQuery<Boolean> query = em.createQuery(jpql, Boolean.class);
		
		query.setParameter("codigoPedido", codigoPedido);
		query.setParameter("usuarioId", usuarioId);

		return query.getSingleResult();
	}

}
