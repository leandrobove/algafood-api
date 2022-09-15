package com.github.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import com.github.algafood.api.dto.FormaPagamentoModel;
import com.github.algafood.api.dto.input.FormaPagamentoInput;
import com.github.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Formas de Pagamento")

public interface FormaPagamentoControllerOpenApi {

	@ApiOperation(value = "Lista as formas de pagamento")
	ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request);

	@ApiOperation(value = "Busca uma forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID da forma de pagamento inválido", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class)))
	})
	ResponseEntity<FormaPagamentoModel> buscar(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId, ServletWebRequest request);

	@ApiOperation(value = "Cadastra uma nova forma de pagamento")
	FormaPagamentoModel cadastrar(@ApiParam(name = "corpo", value = "Representação de uma nova forma de pagamento", required = true) 
		FormaPagamentoInput formaPagamentoInput);

	@ApiOperation(value = "Exclui uma forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class)))
	})
	void deletar(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId);

	@ApiOperation(value = "Atualiza uma forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class)))
	})
	FormaPagamentoModel atualizar(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId, 
			@ApiParam(name = "corpo", value = "Representação de uma forma de pagamento com novos dados", required = true) 
			FormaPagamentoInput formaPagamentoInput);
}
