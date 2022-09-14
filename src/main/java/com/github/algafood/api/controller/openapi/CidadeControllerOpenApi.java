package com.github.algafood.api.controller.openapi;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;

import com.github.algafood.api.dto.CidadeModel;
import com.github.algafood.api.dto.input.CidadeInput;
import com.github.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

	@ApiOperation(value = "Lista as cidades")
	CollectionModel<CidadeModel> listar();

	@ApiOperation(value = "Busca uma cidade por ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", description = "ID da Cidade é inválido", 
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
					schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Cidade não encontrada", 
			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
			schema = @Schema(implementation = Problem.class))) 
	})
	CidadeModel buscar(@ApiParam(value = "ID de uma cidade") Long cidadeId);

	@ApiOperation(value = "Cadastra uma cidade")
	CidadeModel cadastrar(CidadeInput cidadeDTOInput);

	@ApiOperation(value = "Atualiza uma cidade por ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "404", description = "Cidade não encontrada", 
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
					schema = @Schema(implementation = Problem.class))) 
	})
	CidadeModel atualizar(@ApiParam(value = "ID de uma cidade") Long cidadeId, CidadeInput cidadeDTOInput);

	@ApiOperation(value = "Exclui uma cidade por ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "404", description = "Cidade não encontrada", 
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
					schema = @Schema(implementation = Problem.class))) 
	})
	void deletar(@ApiParam(value = "ID de uma cidade") Long cidadeId);

}
