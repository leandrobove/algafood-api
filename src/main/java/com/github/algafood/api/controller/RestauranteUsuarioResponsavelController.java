package com.github.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.algafood.api.AlgaLinksHelper;
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

	@Autowired
	private AlgaLinksHelper algaLinksHelper;

	@GetMapping
	public CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId) {

		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

		CollectionModel<UsuarioModel> usuariosModel = usuarioAssembler.toCollectionModel(restaurante.getResponsaveis()).removeLinks()
				.add(algaLinksHelper.linkToResponsaveisRestaurante(restaurante.getId()))
				.add(algaLinksHelper.linkToRestauranteResponsavelAssociacao(restaurante.getId(), "associar"));
		
		//Adicionar link de desassociação para cada usuario
		usuariosModel.getContent().forEach((usuarioModel) -> {
			usuarioModel.add(algaLinksHelper.linkToRestauranteResponsavelDesassociacao(restaurante.getId(), usuarioModel.getId(), "desassociar"));
		});
		
		return usuariosModel;
	}

	@PutMapping(value = "/{usuarioId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associarResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.associarResponsavel(restauranteId, usuarioId);
		
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/{usuarioId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociarResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.desassociarResponsavel(restauranteId, usuarioId);
		
		return ResponseEntity.noContent().build();
	}

}
