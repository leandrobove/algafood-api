package com.github.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
public class FotoProduto {

	@Id
	@EqualsAndHashCode.Include
	@Column(name = "produto_id")
	private Long id;

	private String nomeArquivo;

	private String descricao;

	private String contentType;

	private Long tamanho;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private Produto produto;

	public Long getRestauranteId() {
		if (this.getProduto() != null) {
			return this.getProduto().getRestaurante().getId();
		}
		return null;
	}

}
