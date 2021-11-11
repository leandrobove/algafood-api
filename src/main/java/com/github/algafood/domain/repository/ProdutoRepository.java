package com.github.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.algafood.domain.model.Produto;
import com.github.algafood.domain.model.Restaurante;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	List<Produto> findTodosByRestaurante(Restaurante restaurante);

	@Query("from Produto where restaurante.id = :restaurante and id = :produto")
	Optional<Produto> findByRestauranteIdAndProdutoId(@Param("restaurante") Long restauranteId, @Param("produto") Long produtoId);
	
	@Query("from Produto p where p.restaurante = :restaurante and p.ativo = true")
	List<Produto> findAtivosByRestaurante(Restaurante restaurante);
}
