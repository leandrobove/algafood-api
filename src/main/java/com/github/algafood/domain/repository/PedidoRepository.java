package com.github.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.github.algafood.domain.model.Pedido;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {

}
