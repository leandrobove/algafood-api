package com.github.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.algafood.api.AlgaLinksHelper;
import com.github.algafood.api.controller.CozinhaController;
import com.github.algafood.api.dto.CozinhaModel;
import com.github.algafood.domain.model.Cozinha;

@Component
public class CozinhaModelAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinksHelper algaLinksHelper; 
	
	public CozinhaModelAssembler() {
		super(CozinhaController.class, CozinhaModel.class);
	}

	@Override
	public CozinhaModel toModel(Cozinha cozinha) {
		CozinhaModel cozinhaModel = modelMapper.map(cozinha, CozinhaModel.class);
		
		cozinhaModel.add(algaLinksHelper.linkToCozinha(cozinhaModel.getId()));
		cozinhaModel.add(algaLinksHelper.linkToCozinhas("cozinhas"));
		
		return cozinhaModel;
	}

}
