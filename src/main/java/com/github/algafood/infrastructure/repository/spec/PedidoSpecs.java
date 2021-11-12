package com.github.algafood.infrastructure.repository.spec;

import java.util.ArrayList;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.github.algafood.domain.model.Pedido;
import com.github.algafood.domain.repository.filter.PedidoFilter;

public class PedidoSpecs {

	public static Specification<Pedido> usandoFiltro(PedidoFilter filter) {

		var specification = new Specification<Pedido>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Pedido> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				if (query.getResultType().equals(Pedido.class)) {
					root.fetch("restaurante").fetch("cozinha");
					root.fetch("cliente");
				}

				var predicates = new ArrayList<Predicate>();

				if (filter.getClienteId() != null) {
					predicates.add(criteriaBuilder.equal(root.get("cliente"), filter.getClienteId()));
				}

				if (filter.getRestauranteId() != null) {
					predicates.add(criteriaBuilder.equal(root.get("restaurante"), filter.getRestauranteId()));
				}

				if (filter.getDataCriacaoFim() != null) {
					predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dataCriacao"),
							filter.getDataCriacaoInicio()));
				}

				if (filter.getDataCriacaoFim() != null) {
					predicates.add(
							criteriaBuilder.lessThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoFim()));
				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}
		};

		return specification;
	}

}
