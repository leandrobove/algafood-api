package com.github.algafood.api.openapi.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.github.algafood.api.dto.RestauranteApenasNomeModel;
import com.github.algafood.api.dto.RestauranteBasicoModel;
import com.github.algafood.api.dto.RestauranteModel;
import com.github.algafood.api.dto.input.RestauranteInput;
import com.github.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nome da projeção de restaurantes", 
				name = "projecao", paramType = "query", type = "string", allowableValues = "apenas-nome", dataType = "string", required = false)
	})
	@ApiOperation(value = "Lista os restaurantes")
	CollectionModel<RestauranteBasicoModel> listar();

	@ApiOperation("Busca um restaurante por ID")
    @ApiResponses({
    	@ApiResponse(responseCode = "400", description = "ID do restaurante é inválido", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class))) 
    })
	RestauranteModel buscar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation(value = "Lista os restaurantes", hidden = true)
	CollectionModel<RestauranteApenasNomeModel> listarApenasNomes();

	@ApiOperation("Cadastra um restaurante")
	RestauranteModel cadastrar(
			@ApiParam(name = "corpo", value = "Representação de um novo restaurante", required = true) RestauranteInput restauranteDTOInput);

	@ApiOperation("Atualiza um restaurante por ID")
    @ApiResponses({
    	@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", 
    			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
    			schema = @Schema(implementation = Problem.class))) 
    })
	RestauranteModel atualizar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(name = "corpo", value = "Representação de um restaurante com os novos dados", required = true) RestauranteInput restauranteDTOInput);

	@ApiOperation("Exclui um restaurante por ID")
	@ApiResponses({
    	@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", 
			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
			schema = @Schema(implementation = Problem.class))) 
	})
	void deletar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Ativa um restaurante por ID")
	@ApiResponses({
    	@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", 
    			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
    			schema = @Schema(implementation = Problem.class))) 
    })
	ResponseEntity<Void> ativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	 @ApiOperation("Inativa um restaurante por ID")
	 @ApiResponses({
	    	@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class))) 
	})
	ResponseEntity<Void> inativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Ativa múltiplos restaurantes")
	void ativarMultiplos(@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true) List<Long> restauranteIds);

	@ApiOperation("Inativa múltiplos restaurantes")
	void inativarMultiplos(@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true) List<Long> restauranteIds);

	@ApiOperation("Abre um restaurante por ID")
	@ApiResponses({
    	@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", 
			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
			schema = @Schema(implementation = Problem.class))) 
	})
	ResponseEntity<Void> abrir(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Fecha um restaurante por ID")
	@ApiResponses({
    	@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", 
			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
			schema = @Schema(implementation = Problem.class))) 
	})
	ResponseEntity<Void> fechar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

}
