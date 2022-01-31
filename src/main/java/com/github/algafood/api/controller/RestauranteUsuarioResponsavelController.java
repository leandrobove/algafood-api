package com.github.algafood.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.algafood.api.assembler.UsuarioModelAssembler;
import com.github.algafood.api.dto.UsuarioModel;
import com.github.algafood.domain.model.Restaurante;
import com.github.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController {

	@Autowired
	private CadastroRestauranteService restauranteService;

	@Autowired
	private UsuarioModelAssembler usuarioAssembler;

	@GetMapping
	public CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId) {

		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

		return usuarioAssembler.toCollectionModel(restaurante.getResponsaveis()).removeLinks()
				.add(linkTo(methodOn(RestauranteUsuarioResponsavelController.class).listar(restaurante.getId()))
						.withSelfRel());
	}

	@PutMapping(value = "/{usuarioId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void associarResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.associarResponsavel(restauranteId, usuarioId);
	}

	@DeleteMapping(value = "/{usuarioId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void desassociarResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.desassociarResponsavel(restauranteId, usuarioId);
	}

}
