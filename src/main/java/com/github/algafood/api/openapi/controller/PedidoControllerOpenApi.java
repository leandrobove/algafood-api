package com.github.algafood.api.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;

import com.github.algafood.api.dto.PedidoModel;
import com.github.algafood.api.dto.PedidoResumoModel;
import com.github.algafood.api.dto.input.PedidoInput;
import com.github.algafood.api.exceptionhandler.Problem;
import com.github.algafood.domain.filter.PedidoFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

	@ApiOperation(value = "Pesquisa os pedidos com filtro")
	PagedModel<PedidoResumoModel> listarComFiltro(PedidoFilter pedidoFilter, Pageable pageable);

	@ApiOperation(value = "Busca um pedido por código")
	@ApiResponses({
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class))) })
	PedidoModel buscar(@ApiParam(value = "Código de um pedido", 
			example = "7035f4b8-a79a-4c2b-9a7e-a6f4c200f269") String codigoPedido);

	@ApiOperation(value = "Cadastra um novo pedido")
	PedidoModel cadastrar(@ApiParam(name = "corpo", value = "Representação de um novo pedido") PedidoInput pedidoInput);

}
