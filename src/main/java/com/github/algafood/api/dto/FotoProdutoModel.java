package com.github.algafood.api.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoModel extends RepresentationModel<FotoProdutoModel> {

	private String nomeArquivo;

	private String descricao;

	private String contentType;

	private Long tamanho;
}
