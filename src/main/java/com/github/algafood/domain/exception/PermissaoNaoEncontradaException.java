package com.github.algafood.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	private static final String MSG_PERMISSAO_NAO_ENCONTRADA = "Não existe cadastro de permissão com o id %d";

	public PermissaoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public PermissaoNaoEncontradaException(Long permissaoId) {
		this(String.format(MSG_PERMISSAO_NAO_ENCONTRADA, permissaoId));
	}

}
