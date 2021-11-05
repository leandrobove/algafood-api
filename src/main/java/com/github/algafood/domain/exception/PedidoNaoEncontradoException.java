package com.github.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	private static final String MSG_PEDIDO_NAO_ENCONTRADO = "Não existe cadastro de pedido com o id %d";

	public PedidoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public PedidoNaoEncontradoException(Long pedidoId) {
		this(String.format(MSG_PEDIDO_NAO_ENCONTRADO, pedidoId));
	}

}
