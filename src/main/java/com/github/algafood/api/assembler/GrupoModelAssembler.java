package com.github.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.algafood.api.AlgaLinksHelper;
import com.github.algafood.api.controller.GrupoController;
import com.github.algafood.api.dto.GrupoModel;
import com.github.algafood.domain.model.Grupo;

@Component
public class GrupoModelAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinksHelper algaLinksHelper;

	public GrupoModelAssembler() {
		super(GrupoController.class, GrupoModel.class);
	}

	@Override
	public GrupoModel toModel(Grupo grupo) {

		GrupoModel grupoModel = this.createModelWithId(grupo.getId(), grupo);
		
		modelMapper.map(grupo, grupoModel);
		
		grupoModel.add(algaLinksHelper.linkToGrupos("grupos"));
		grupoModel.add(algaLinksHelper.linkToGruposPermissoes(grupo.getId(), "permissoes"));

		return grupoModel;
	}
	
	@Override
	public CollectionModel<GrupoModel> toCollectionModel(Iterable<? extends Grupo> entities) {
		return super.toCollectionModel(entities)
				.add(algaLinksHelper.linkToGrupos());
	}

}
