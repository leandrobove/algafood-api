package com.github.algafood.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	private static final String MSG_PRODUTO_NAO_ENCONTRADO = "Não existe cadastro de produto com o id %d";

	public ProdutoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public ProdutoNaoEncontradoException(Long produtoId) {
		this(String.format(MSG_PRODUTO_NAO_ENCONTRADO, produtoId));
	}

}
