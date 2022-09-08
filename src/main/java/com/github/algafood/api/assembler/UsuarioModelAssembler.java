package com.github.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.algafood.api.AlgaLinksHelper;
import com.github.algafood.api.controller.UsuarioController;
import com.github.algafood.api.dto.UsuarioModel;
import com.github.algafood.core.security.AlgaSecurity;
import com.github.algafood.domain.model.Usuario;

@Component
public class UsuarioModelAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinksHelper algaLinksHelper;
	
	@Autowired
	private AlgaSecurity algaSecurity;

	public UsuarioModelAssembler() {
		super(UsuarioController.class, UsuarioModel.class);
	}

	@Override
	public UsuarioModel toModel(Usuario usuario) {
		UsuarioModel usuarioModel = modelMapper.map(usuario, UsuarioModel.class);

		// hypermedia
		if(algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			usuarioModel.add(algaLinksHelper.linkToUsuarios("usuarios"));
			usuarioModel.add(algaLinksHelper.linkToGruposUsuario(usuario.getId(), "grupos-usuario"));
		}

		return usuarioModel;
	}

	@Override
	public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> entities) {
		
		CollectionModel<UsuarioModel> usuariosModel = super.toCollectionModel(entities);
		
		if(algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			usuariosModel.add(algaLinksHelper.linkToUsuarios());
		}
		
		return usuariosModel;
	}

}
