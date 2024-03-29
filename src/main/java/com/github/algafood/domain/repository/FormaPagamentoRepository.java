package com.github.algafood.domain.repository;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.algafood.domain.model.FormaPagamento;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {
	
	@Query("select max(f.dataAtualizacao) from FormaPagamento f")
	OffsetDateTime findDataMaisRecenteAtualizacao();
	
	@Query("select f.dataAtualizacao from FormaPagamento f where f.id = :formaPagamentoId")
	OffsetDateTime findDataAtualizacaoById(@Param(value = "formaPagamentoId") Long formaPagamentoId);

}
