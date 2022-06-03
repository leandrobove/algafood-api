package com.github.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.algafood.api.AlgaLinksHelper;
import com.github.algafood.api.controller.CidadeController;
import com.github.algafood.api.dto.CidadeModel;
import com.github.algafood.domain.model.Cidade;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinksHelper algaLinksHelper;

	public CidadeModelAssembler() {
		super(CidadeController.class, CidadeModel.class);
	}

	@Override
	public CidadeModel toModel(Cidade model) {
		CidadeModel cidadeModel = modelMapper.map(model, CidadeModel.class);

		// Hypermedia
		cidadeModel.add(algaLinksHelper.linkToCidade(cidadeModel.getId()));
		cidadeModel.add(algaLinksHelper.linkToCidades("cidades"));

		cidadeModel.getEstado().add(algaLinksHelper.linkToEstado(cidadeModel.getEstado().getId()));

		return cidadeModel;
	}

	@Override
	public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
		return super.toCollectionModel(entities).add(algaLinksHelper.linkToCidades());
	}
}
