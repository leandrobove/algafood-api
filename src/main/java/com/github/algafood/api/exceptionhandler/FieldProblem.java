package com.github.algafood.api.exceptionhandler;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel(value = "CampoProblema")

@Getter
@Builder
public class FieldProblem {

	@ApiModelProperty(example = "preco")
	private String name;
	
	@ApiModelProperty(example = "O preço do produto deve ser maior ou igual a 0")
	private String message;
}
