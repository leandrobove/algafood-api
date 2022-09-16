package com.github.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.github.algafood.api.dto.GrupoModel;
import com.github.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Usuarios")
public interface UsuarioGrupoControllerOpenApi {

	@ApiOperation(value = "Lista os grupos associados a um usuário")
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "Usuário não encontrado", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class))) 
    })
	CollectionModel<GrupoModel> listar(@ApiParam(value = "ID de um usuário", example = "1", required = true) Long usuarioId);

	@ApiOperation("Associação de grupo com usuário")
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "Usuário ou grupo não encontrado", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class))) 
    })
	ResponseEntity<Void> associarGrupo(
			@ApiParam(value = "ID de um usuário", example = "1", required = true) Long usuarioId, 
			@ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId
	);

	@ApiOperation("Desassociação de grupo com usuário")
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "Usuário ou grupo não encontrado", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class))) 
    })
	ResponseEntity<Void> desassociarGrupo(
			@ApiParam(value = "ID de um usuário", example = "1", required = true) Long usuarioId, 
			@ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId
	);
}
