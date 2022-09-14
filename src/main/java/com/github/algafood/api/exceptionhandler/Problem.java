package com.github.algafood.api.exceptionhandler;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

//não permite serializar atributos nulos
@JsonInclude(Include.NON_NULL)

@ApiModel(value = "Problema")

@Getter
@Builder
public class Problem implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(example = "400", position = 1)
	private Integer status;
	
	@ApiModelProperty(example = "https://algafood.com.br/dados-invalidos")
	private String type;
	
	@ApiModelProperty(example = "Dados inválidos", position = 2)
	private String title;
	
	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
	private String detail;
	
	private List<FieldProblem> fields;
	
	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
	private String userMessage; //UI message
	
	@ApiModelProperty(example = "2022-09-14T14:27:46.0137233Z")
	private OffsetDateTime timestamp;

}
