package com.github.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.github.algafood.api.dto.PermissaoModel;
import com.github.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoPermissaoControllerOpenApi {

	@ApiOperation("Lista as permissões associadas a um grupo")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do grupo inválido", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Grupo não encontrado", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class)))
	})
	CollectionModel<PermissaoModel> listar(
			@ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId
	);

	@ApiOperation("Desassociação de permissão com grupo")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do grupo ou permissão inválido", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Grupo ou permissão não encontrada", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class)))
	})
	ResponseEntity<Void> desassociar(
			@ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId, 
			@ApiParam(value = "ID de uma permissão", example = "1", required = true) Long permissaoId
	);

	@ApiOperation("Associação de permissão com grupo")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do grupo ou permissão inválido", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Grupo ou permissão não encontrada", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class)))
	})
	ResponseEntity<Void> associar(
			@ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId, 
			@ApiParam(value = "ID de uma permissão", example = "1", required = true) Long permissaoId
	);
}
