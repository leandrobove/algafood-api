package com.github.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;

import com.github.algafood.api.dto.UsuarioModel;
import com.github.algafood.api.dto.input.SenhaInput;
import com.github.algafood.api.dto.input.UsuarioComSenhaInput;
import com.github.algafood.api.dto.input.UsuarioSemSenhaInput;
import com.github.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Usuarios")
public interface UsuarioControllerOpenApi {

	@ApiOperation("Lista os usuários")
	CollectionModel<UsuarioModel> listar();

	@ApiOperation("Busca um usuário por ID")
	@ApiResponses({
    	@ApiResponse(responseCode = "400", description = "ID do usuário é inválido", 
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
				schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Usuário não encontrado", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class))) 
    })
	UsuarioModel buscarPorId(@ApiParam(value = "ID de um usuário", example = "1", required = true) Long usuarioId);

	@ApiOperation("Cadastra um usuário")
	UsuarioModel cadastrar(@ApiParam(name = "corpo", value = "Representação de um novo usuário", required = true) UsuarioComSenhaInput usuarioComSenhaInput);

	@ApiOperation("Atualiza um usuário por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "Usuário não encontrado", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class))) 
    })
	UsuarioModel atualizar(
			@ApiParam(value = "ID de um usuário", example = "1", required = true) Long usuarioId, 
			@ApiParam(name = "corpo", value = "Representação de um usuário com os novos dados", required = true) UsuarioSemSenhaInput usuarioSemSenhaInput
	);

	@ApiOperation("Exclui um usuário por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "Usuário não encontrado", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class))) 
    })
	void deletar(@ApiParam(value = "ID de um usuário", example = "1", required = true) Long usuarioId);

	@ApiOperation("Atualiza a senha de um usuário")
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "Usuário não encontrado", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Problem.class))) 
    })
	void alterarSenha(
			@ApiParam(value = "ID de um usuário", example = "1", required = true) Long usuarioId, 
			 @ApiParam(name = "corpo", value = "Representação de uma nova senha", required = true) SenhaInput senhaInput
	);
}
