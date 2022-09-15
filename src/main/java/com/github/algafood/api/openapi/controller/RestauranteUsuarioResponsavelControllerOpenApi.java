package com.github.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.github.algafood.api.dto.UsuarioModel;
import com.github.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteUsuarioResponsavelControllerOpenApi {

	@ApiOperation(value = "Lista os usuários responsáveis associados a restaurante")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do restaurante inválido", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class)))
	})
	CollectionModel<UsuarioModel> listar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation(value = "Associação de restaurante com usuário responsável")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do restaurante ou ID do usuário inválido", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante ou usuário não encontrado", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class)))
	})
	ResponseEntity<Void> associarResponsavel(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId);

	@ApiOperation(value = "Desassociação de restaurante com usuário responsável")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do restaurante ou ID do usuário inválido", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante ou usuário não encontrado", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class)))
	})
	ResponseEntity<Void> desassociarResponsavel(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId);
}
