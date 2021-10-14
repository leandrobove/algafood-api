package com.github.algafood.api.exceptionhandler;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FieldProblem {

	private String name;
	private String message;
}
