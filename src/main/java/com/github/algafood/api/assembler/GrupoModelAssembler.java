package com.github.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.algafood.api.AlgaLinksHelper;
import com.github.algafood.api.controller.GrupoController;
import com.github.algafood.api.dto.GrupoModel;
import com.github.algafood.core.security.AlgaSecurity;
import com.github.algafood.domain.model.Grupo;

@Component
public class GrupoModelAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinksHelper algaLinksHelper;
	
	@Autowired
	private AlgaSecurity algaSecurity;

	public GrupoModelAssembler() {
		super(GrupoController.class, GrupoModel.class);
	}

	@Override
	public GrupoModel toModel(Grupo grupo) {
		GrupoModel grupoModel = this.createModelWithId(grupo.getId(), grupo);
		modelMapper.map(grupo, grupoModel);
		
		if(algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			grupoModel.add(algaLinksHelper.linkToGrupos("grupos"));
			grupoModel.add(algaLinksHelper.linkToGruposPermissoes(grupo.getId(), "permissoes"));
		}
		return grupoModel;
	}
	
	@Override
	public CollectionModel<GrupoModel> toCollectionModel(Iterable<? extends Grupo> entities) {
		CollectionModel<GrupoModel> gruposModel = super.toCollectionModel(entities);
		
		if(algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			gruposModel.add(algaLinksHelper.linkToGrupos());
		}
		
		return gruposModel;
	}

}
