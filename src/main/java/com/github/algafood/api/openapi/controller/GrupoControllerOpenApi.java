package com.github.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;

import com.github.algafood.api.dto.GrupoModel;
import com.github.algafood.api.dto.input.GrupoInput;
import com.github.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {
	
	@ApiOperation(value = "Lista os grupos")
	CollectionModel<GrupoModel> listar();
	
	@ApiOperation(value = "Busca um grupo por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do grupo inválido", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Grupo não encontrado", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class)))
	})
	GrupoModel buscarPorId(@ApiParam(value = "ID de um grupo", example = "1") Long grupoId);
	
	@ApiOperation(value = "Cadastra um grupo")
	GrupoModel cadastrar(GrupoInput grupoInput);
	
	@ApiOperation(value = "Atualiza um grupo por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "Grupo não encontrado", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class)))
	})
	GrupoModel atualizar(@ApiParam(value = "ID de um grupo", example = "1") Long grupoId, GrupoInput grupoInput);
	
	@ApiOperation(value = "Exclui um grupo por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "Grupo não encontrado", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class)))
	})
	void deletar(@ApiParam(value = "ID de um grupo", example = "1") Long grupoId);
	
}
