package com.github.algafood.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
public class ItemPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@Column(nullable = false)
	private Integer quantidade;

	@Column(nullable = false)
	private BigDecimal precoUnitario;

	@Column(nullable = false)
	private BigDecimal precoTotal;

	private String observacao;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Pedido pedido;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Produto produto;

	public void calcularPrecoTotal() {
		BigDecimal precoUnitario = this.getPrecoUnitario();
		Integer quantidade = this.getQuantidade();

		if (precoUnitario == null) {
			precoUnitario = BigDecimal.ZERO;
		}

		if (quantidade == null) {
			quantidade = 0;
		}

		this.setPrecoTotal(precoUnitario.multiply(new BigDecimal(quantidade)));
	}

}
