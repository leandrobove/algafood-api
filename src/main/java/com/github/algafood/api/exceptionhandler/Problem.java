package com.github.algafood.api.exceptionhandler;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

//não permite serializar atributos nulos
@JsonInclude(Include.NON_NULL)

@Getter
@Builder
public class Problem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer status;
	private String type;
	private String title;
	private String detail;
	
	private List<FieldProblem> fields;
	
	private String userMessage; //UI message
	private LocalDateTime timestamp;

}
