package com.github.algafood.api.openapi.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.github.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Pedidos")
public interface FluxoPedidoControllerOpenApi {

	@ApiOperation(value = "Confirmação de um pedido por código")
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "Pedido não encontrado", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class)))
	})
	ResponseEntity<Void> confirmar(@ApiParam(value = "Código de um pedido", example = "fc12223e-a6d2-4041-9af9-d14df6e7c514", required = true) String codigoPedido);

	@ApiOperation(value = "Cancelamento de pedido por código")
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "Pedido não encontrado", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class)))
	})
	ResponseEntity<Void> cancelar(@ApiParam(value = "Código de um pedido", example = "fc12223e-a6d2-4041-9af9-d14df6e7c514", required = true) String codigoPedido);

	@ApiOperation(value = "Registrar a entrega de um pedido por código")
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "Pedido não encontrado", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class)))
	})
	ResponseEntity<Void> entregar(@ApiParam(value = "Código de um pedido", example = "fc12223e-a6d2-4041-9af9-d14df6e7c514", required = true) String codigoPedido);

}
