package com.github.algafood.infrastructure.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.algafood.domain.filter.VendaDiariaFilter;
import com.github.algafood.domain.model.Pedido;
import com.github.algafood.domain.model.StatusPedido;
import com.github.algafood.domain.model.dto.VendaDiaria;
import com.github.algafood.domain.service.VendaQueryService;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

	@Autowired
	private EntityManager em;

	@Override
	public List<VendaDiaria> listarVendasDiarias(VendaDiariaFilter filter) {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<VendaDiaria> criteriaQuery = criteriaBuilder.createQuery(VendaDiaria.class);

		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		Expression<LocalDate> funcaoDate = criteriaBuilder.function("date", LocalDate.class, root.get("dataCriacao"));

		CompoundSelection<VendaDiaria> selection = criteriaBuilder.construct(VendaDiaria.class, funcaoDate,
				criteriaBuilder.count(root.get("id")), criteriaBuilder.sum(root.get("valorTotal")));

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (filter.getRestauranteId() != null) {
			predicates.add(criteriaBuilder.equal(root.get("restaurante"), filter.getRestauranteId()));
		}

		if (filter.getDataCriacaoInicio() != null && filter.getDataCriacaoFim() != null) {
			predicates.add(criteriaBuilder.between(root.get("dataCriacao"), filter.getDataCriacaoInicio(),
					filter.getDataCriacaoFim()));
		}

		predicates.add(root.get("status").in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));

		criteriaQuery.select(selection);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.groupBy(funcaoDate);

		return em.createQuery(criteriaQuery).getResultList();
	}

}
