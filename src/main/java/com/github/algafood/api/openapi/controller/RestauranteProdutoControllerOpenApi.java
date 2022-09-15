package com.github.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;

import com.github.algafood.api.dto.ProdutoModel;
import com.github.algafood.api.dto.input.ProdutoInput;
import com.github.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Produtos")
public interface RestauranteProdutoControllerOpenApi {

	@ApiOperation(value = "Lista os produtos de um restaurante")
	@ApiResponses({
    	@ApiResponse(responseCode = "400", description = "ID do restaurante é inválido", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class))) 
    })
	CollectionModel<ProdutoModel> listar(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "Indica se deve ou não incluir produtos inativos no resultado da listagem", 
            example = "false", defaultValue = "false") Boolean incluirInativos
	);

	@ApiResponses({
    	@ApiResponse(responseCode = "400", description = "ID do restaurante ou do produto é inválido", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante ou produto não encontrado", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class))) 
    })
	@ApiOperation("Busca um produto de um restaurante")
	ProdutoModel buscar(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "ID de um produto", example = "1", required = true) Long produtoId
	);

	@ApiOperation("Cadastra um produto de um restaurante")
	@ApiResponses({
    	@ApiResponse(responseCode = "400", description = "ID do restaurante é inválido", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class))) 
    })
	ProdutoModel cadastrar(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(name = "corpo", value = "Representação de um novo produto", required = true) ProdutoInput produtoInput
	);

	@ApiOperation("Atualiza um produto de um restaurante")
	@ApiResponses({
    	@ApiResponse(responseCode = "400", description = "ID do restaurante ou do produto é inválido", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante ou produto não encontrado", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class))) 
    })
	ProdutoModel atualizar(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "ID de um produto", example = "1", required = true) Long produtoId, 
			@ApiParam(name = "corpo", value = "Representação de produto com novos dados", required = true) ProdutoInput produtoInput
	);
}
