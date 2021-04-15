package com.github.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	ENTIDADE_NA0_ENCONTRADA("entidade-nao-encontrada", "Entidade não encontrada");

	private String title;
	private String uri;

	private ProblemType(String path, String title) {
		this.title = title;
		this.uri = "https://localhost:8080/" + path;
	}

}
