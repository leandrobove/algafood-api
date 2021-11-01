package com.github.algafood.api.dto;

import com.github.algafood.domain.model.Estado;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeDTO {

	private Long id;

	private String nome;

	private Estado estado;

}
