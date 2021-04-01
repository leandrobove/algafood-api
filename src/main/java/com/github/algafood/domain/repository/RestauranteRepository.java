package com.github.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository
		extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante> {

	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

	@Query(value = "from Restaurante where nome like %:nome% and cozinha.id = :id")
	List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinhaId);

	Optional<Restaurante> findFirstByNomeContaining(String nome);

	@Query(value = "SELECT * from restaurante r where r.nome like %:nome% limit 2", nativeQuery = true)
	List<Restaurante> consultarDoisPrimeirosPorNome(String nome);

	@Query(value = "SELECT COUNT(id) from Restaurante", nativeQuery = true)
	int countByRestauranteId();

	int countRestaurantesByCozinhaId(Long cozinhaId);

}
