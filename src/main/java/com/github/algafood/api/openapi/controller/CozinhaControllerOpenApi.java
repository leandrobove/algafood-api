package com.github.algafood.api.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;

import com.github.algafood.api.dto.CozinhaModel;
import com.github.algafood.api.dto.input.CozinhaInput;
import com.github.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

	@ApiOperation(value = "Lista as cozinhas com paginação")
	PagedModel<CozinhaModel> listar(Pageable pageable);

	@ApiOperation(value = "Busca uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID da cozinha inválido", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Cozinha não encontrada", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class)))
	})
	CozinhaModel buscar(@ApiParam(value = "ID de uma cozinha", example = "1") Long cozinhaId);

	@ApiOperation(value = "Cadastra uma cozinha")
	CozinhaModel cadastrar(CozinhaInput cozinhaInput);

	@ApiOperation(value = "Atualiza uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "Cozinha não encontrada", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class)))
	})
	CozinhaModel atualizar(@ApiParam(value = "ID de uma cozinha", example = "1") Long cozinhaId, CozinhaInput cozinhaInput);

	@ApiOperation(value = "Exclui uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "Cozinha não encontrada", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class)))
	})
	void deletar(@ApiParam(value = "ID de uma cozinha", example = "1") Long cozinhaId);

}
