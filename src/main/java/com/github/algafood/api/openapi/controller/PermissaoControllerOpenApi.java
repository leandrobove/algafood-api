package com.github.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.github.algafood.api.dto.PermissaoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Permissões")
public interface PermissaoControllerOpenApi {
	
	@ApiOperation(value = "Lista as permissões")
	CollectionModel<PermissaoModel> listar();

}
