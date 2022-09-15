package com.github.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;

import com.github.algafood.api.dto.EstadoModel;
import com.github.algafood.api.dto.input.EstadoInput;
import com.github.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {
	
	@ApiOperation(value = "Lista os estados")
	CollectionModel<EstadoModel> listar();
	
	@ApiOperation(value = "Busca um estado por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do estado inválido", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Estado não encontrado", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class)))
	})
	EstadoModel buscar(@ApiParam(value = "ID de um estado", example = "1", required = true) Long estadoId);
	
	@ApiOperation(value = "Cadastra um novo estado")
	EstadoModel cadastrar(@ApiParam(name = "corpo", value = "Representação de um novo estado", required = true) EstadoInput estadoInput);
	
	@ApiOperation(value = "Atualiza um estado por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "Estado não encontrado", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class)))
	})
	EstadoModel atualizar(
			@ApiParam(value = "ID de um estado", example = "1", required = true) Long estadoId, 
			@ApiParam(name = "corpo", value = "Representação de um estado com novos dados", required = true) EstadoInput estadoInput);
	
	@ApiOperation(value = "Exclui um estado por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "Estado não encontrado", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class)))
	})
	void deletar(@ApiParam(value = "ID de um estado", example = "1", required = true) Long estadoId);

}
