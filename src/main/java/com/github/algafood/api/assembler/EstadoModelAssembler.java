package com.github.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.algafood.api.AlgaLinksHelper;
import com.github.algafood.api.controller.EstadoController;
import com.github.algafood.api.dto.EstadoModel;
import com.github.algafood.core.security.AlgaSecurity;
import com.github.algafood.domain.model.Estado;

@Component
public class EstadoModelAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinksHelper algaLinksHelper;
	
	@Autowired
	private AlgaSecurity algaSecurity;

	public EstadoModelAssembler() {
		super(EstadoController.class, EstadoModel.class);
	}

	@Override
	public EstadoModel toModel(Estado estado) {
		EstadoModel estadoModel = createModelWithId(estado.getId(), estado);
	    modelMapper.map(estado, estadoModel);

	    if(algaSecurity.podeConsultarEstados()) {
	    	estadoModel.add(algaLinksHelper.linkToEstados("estados"));
	    }
		return estadoModel;
	}

	@Override
	public CollectionModel<EstadoModel> toCollectionModel(Iterable<? extends Estado> entities) {
		CollectionModel<EstadoModel> estadosModel = super.toCollectionModel(entities);
		
		if(algaSecurity.podeConsultarEstados()) {
			estadosModel.add(algaLinksHelper.linkToEstados());
		}
		
		return estadosModel;
	}

}
