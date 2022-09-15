package com.github.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.github.algafood.api.dto.FormaPagamentoModel;
import com.github.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

	@ApiOperation(value = "Lista as formas de pagamento associadas a restaurante")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do restaurante inválido", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class)))
	})
	CollectionModel<FormaPagamentoModel> listar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Desassociação de restaurante com forma de pagamento")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do restaurante ou ID da forma de pagamento inválido", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante ou forma de pagamento não encontrado", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class)))
	})
	ResponseEntity<Void> desassociar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId);

	@ApiOperation("Associação de restaurante com forma de pagamento")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do restaurante ou ID da forma de pagamento inválido", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante ou forma de pagamento não encontrado", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class)))
	})
	ResponseEntity<Void> associar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId);

}
