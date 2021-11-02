package com.github.algafood.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	private static final String MSG_FORMA_PAGAMENTO_NAO_ENCONTRADA = "Não existe um cadastro de forma de pagamento com o código %d";

	public FormaPagamentoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public FormaPagamentoNaoEncontradaException(Long formaPagamentoId) {
		this(String.format(MSG_FORMA_PAGAMENTO_NAO_ENCONTRADA, formaPagamentoId));
	}

}
