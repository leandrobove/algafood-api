package com.github.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.algafood.api.AlgaLinksHelper;
import com.github.algafood.api.controller.PermissaoController;
import com.github.algafood.api.dto.PermissaoModel;
import com.github.algafood.core.security.AlgaSecurity;
import com.github.algafood.domain.model.Permissao;

@Component
public class PermissaoModelAssembler extends RepresentationModelAssemblerSupport<Permissao, PermissaoModel> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinksHelper algaLinksHelper;
	
	@Autowired
	private AlgaSecurity algaSecurity;

	public PermissaoModelAssembler() {
		super(PermissaoController.class, PermissaoModel.class);
	}

	@Override
	public PermissaoModel toModel(Permissao permissao) {
		
		PermissaoModel permissaoModel = modelMapper.map(permissao, PermissaoModel.class);
		
		return permissaoModel;
	}
	
	@Override
	public CollectionModel<PermissaoModel> toCollectionModel(Iterable<? extends Permissao> entities) {
		CollectionModel<PermissaoModel> permissoesModel = super.toCollectionModel(entities);
		
		if(algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			permissoesModel.add(algaLinksHelper.linkToPermissoes());
		}
		
		return permissoesModel;
	}

}
