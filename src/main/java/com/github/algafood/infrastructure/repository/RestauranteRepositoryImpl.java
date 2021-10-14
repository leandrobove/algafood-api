package com.github.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.github.algafood.domain.model.Restaurante;
import com.github.algafood.domain.repository.RestauranteRepository;
import com.github.algafood.domain.repository.RestauranteRepositoryQueries;
import com.github.algafood.infrastructure.repository.spec.RestauranteSpecs;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	@Lazy
	private RestauranteRepository restauranteRepository;

	@Override
	public List<Restaurante> consultarPorNomeETaxaFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {

		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		Root<Restaurante> root = criteria.from(Restaurante.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.hasLength(nome)) {
			predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
		}
		if (taxaInicial != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaInicial));
		}
		if (taxaFinal != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFinal));
		}

		criteria.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Restaurante> query = em.createQuery(criteria);

		return query.getResultList();
	}

	@Override
	public List<Restaurante> buscarPorCozinhaId(Long cozinhaId) {

		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		Root<Restaurante> root = criteria.from(Restaurante.class);

		Predicate predicateCozinhaId = builder.equal(root.get("cozinha"), cozinhaId);
		criteria.where(predicateCozinhaId);

		TypedQuery<Restaurante> query = em.createQuery(criteria);

		return query.getResultList();

		/*
		 * var jpql = "from Restaurante where cozinha.id = :cozinhaId";
		 * 
		 * TypedQuery<Restaurante> query = em.createQuery(jpql, Restaurante.class);
		 * 
		 * query.setParameter("cozinhaId", cozinhaId);
		 * 
		 * return query.getResultList();
		 */
	}

	@Override
	public List<Restaurante> buscarComFreteGratisENomeSemelhante(String nome) {
		return restauranteRepository
				.findAll(RestauranteSpecs.comFreteGratis().and(RestauranteSpecs.comNomeSemelhante(nome)));
	}

	@Override
	public List<Restaurante> buscarPorFormaPagamentoId(Long formaPagamentoId) {
		String jpql = "SELECT r FROM Restaurante r left join fetch r.formasPagamento f where f.id = :formaPagamentoId";

		TypedQuery<Restaurante> query = em.createQuery(jpql, Restaurante.class);

		query.setParameter("formaPagamentoId", formaPagamentoId);

		return query.getResultList();
	}

}
