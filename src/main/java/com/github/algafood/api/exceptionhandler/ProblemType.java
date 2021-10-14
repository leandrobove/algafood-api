package com.github.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	RECURSO_NAO_ENCONTRADO("recurso-nao-encontrado", "Recurso não encontrado"),
	ERRO_NEGOCIO("erro-negocio", "Violação de regra de negócio"),
	ENTIDADE_EM_USO("entidade-em-uso", "Entidade em uso"), 
	MENSAGEM_INCOMPREENSIVEL("mensagem-incompreensivel", "Corpo da mensagem não é compreensivel"),
	PARAMETRO_INVALIDO("parametro-invalido", "Parâmetro inválido"),
	ERRO_DE_SISTEMA("erro-de-sistema", "Erro de sistema"),
	DADOS_INVALIDOS("dados-invalidos", "Dados inválidos");
	

	private String title;
	private String uri;

	private ProblemType(String path, String title) {
		this.title = title;
		this.uri = "https://localhost:8080/" + path;
	}

}
