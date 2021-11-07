package com.github.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	private static final String MSG_PEDIDO_NAO_ENCONTRADO = "Não existe cadastro de pedido com o código %s";

	public PedidoNaoEncontradoException(String codigoPedido) {
		super(String.format(MSG_PEDIDO_NAO_ENCONTRADO, codigoPedido));
	}

}
