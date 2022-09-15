package com.github.algafood.api.openapi.controller;

import java.io.IOException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import com.github.algafood.api.dto.FotoProdutoModel;
import com.github.algafood.api.dto.input.FotoProdutoInput;
import com.github.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Produtos")
public interface RestauranteFotoProdutoControllerOpenApi {

	@ApiOperation("Atualiza a foto do produto de um restaurante")
	@ApiResponses({
    	@ApiResponse(responseCode = "400", description = "ID do restaurante ou do produto é inválido", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante ou produto não encontrado", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class))) 
    })
	FotoProdutoModel atualizarFoto(
			@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId, 
			FotoProdutoInput fotoProdutoInput
	) throws IOException;

	@ApiOperation(value = "Busca a foto do produto de um restaurante", produces = "application/json, image/jpeg, image/png")
	@ApiResponses({
    	@ApiResponse(responseCode = "400", description = "ID do restaurante ou do produto é inválido", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante ou produto não encontrado", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class))) 
    })
	FotoProdutoModel buscar(
			@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
			@ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId
	);

	@ApiResponses({
    	@ApiResponse(responseCode = "400", description = "ID do restaurante ou do produto é inválido", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante ou produto não encontrado", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class))) 
    })
	@ApiOperation("Exclui a foto do produto de um restaurante")
	void excluirFoto(
			@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId
	);
	
	@ApiOperation(value = "Busca a foto do produto de um restaurante", hidden = true)
	ResponseEntity<?> baixarFoto(Long restauranteId, Long produtoId, String acceptHeader) throws HttpMediaTypeNotAcceptableException;
}
