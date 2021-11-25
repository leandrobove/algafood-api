package com.github.algafood.domain.exception;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public FotoProdutoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public FotoProdutoNaoEncontradaException(Long restauranteId, Long produtoId) {
		super(String.format("Não existe um cadastro de foto do produto para o restaurante de código %d e código de produto %d",
				restauranteId, produtoId));
	}
}
