package com.github.algafood.infrastructure.service.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
	public List<VendaDiaria> listarVendasDiarias(VendaDiariaFilter filter, String timeOffset) {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<VendaDiaria> criteriaQuery = criteriaBuilder.createQuery(VendaDiaria.class);

		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		var funcaoConvertTz = criteriaBuilder.function("convert_tz", Date.class, root.get("dataCriacao"),
				criteriaBuilder.literal("+00:00"), criteriaBuilder.literal(timeOffset));
		var funcaoDateDataCriacao = criteriaBuilder.function("date", Date.class, funcaoConvertTz);

		CompoundSelection<VendaDiaria> selection = criteriaBuilder.construct(VendaDiaria.class, funcaoDateDataCriacao,
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
		criteriaQuery.groupBy(funcaoDateDataCriacao);

		return em.createQuery(criteriaQuery).getResultList();
	}

}
