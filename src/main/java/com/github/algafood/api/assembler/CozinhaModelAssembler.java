package com.github.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.algafood.api.AlgaLinksHelper;
import com.github.algafood.api.controller.CozinhaController;
import com.github.algafood.api.dto.CozinhaModel;
import com.github.algafood.core.security.AlgaSecurity;
import com.github.algafood.domain.model.Cozinha;

@Component
public class CozinhaModelAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinksHelper algaLinksHelper; 
	
	@Autowired
	private AlgaSecurity algaSecurity;
	
	public CozinhaModelAssembler() {
		super(CozinhaController.class, CozinhaModel.class);
	}

	@Override
	public CozinhaModel toModel(Cozinha cozinha) {
		CozinhaModel cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
	    modelMapper.map(cozinha, cozinhaModel);
		
		if(algaSecurity.podeConsultarCozinhas()) {
			cozinhaModel.add(algaLinksHelper.linkToCozinhas("cozinhas"));
		}
		
		return cozinhaModel;
	}

}
