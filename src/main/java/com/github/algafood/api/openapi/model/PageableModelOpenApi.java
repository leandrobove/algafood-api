package com.github.algafood.api.openapi.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Pageable")

@Setter
@Getter
public class PageableModelOpenApi {

	@ApiModelProperty(value = "Número da página (começa em 0)", example = "0")
	private int page;
	
	@ApiModelProperty(value = "Quantidade de elementos por página", example = "12")
	private int size;
	
	@ApiModelProperty(value = "Nome da propriedade para ordenação", example = "nome,asc")
	private List<String> sort;

}
