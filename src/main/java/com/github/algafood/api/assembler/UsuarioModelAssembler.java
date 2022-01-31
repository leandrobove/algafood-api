package com.github.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.algafood.api.controller.UsuarioController;
import com.github.algafood.api.controller.UsuarioGrupoController;
import com.github.algafood.api.dto.UsuarioModel;
import com.github.algafood.domain.model.Usuario;

@Component
public class UsuarioModelAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {

	@Autowired
	private ModelMapper modelMapper;

	public UsuarioModelAssembler() {
		super(UsuarioController.class, UsuarioModel.class);
	}

	@Override
	public UsuarioModel toModel(Usuario usuario) {
		UsuarioModel usuarioModel = modelMapper.map(usuario, UsuarioModel.class);

		// hypermedia
		usuarioModel.add(linkTo(methodOn(UsuarioController.class).buscarPorId(usuarioModel.getId())).withSelfRel());
		usuarioModel.add(linkTo(methodOn(UsuarioController.class).listar()).withRel("usuarios"));
		usuarioModel.add(linkTo(methodOn(UsuarioGrupoController.class).listar(usuarioModel.getId())).withRel("grupos-usuarios"));

		return usuarioModel;
	}
	
	@Override
	public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> entities) {
		return super.toCollectionModel(entities).add(linkTo(UsuarioController.class).withSelfRel());
	}

}
