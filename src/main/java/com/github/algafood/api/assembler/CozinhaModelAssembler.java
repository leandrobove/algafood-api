package com.github.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.github.algafood.api.controller.CozinhaController;
import com.github.algafood.api.dto.CozinhaModel;
import com.github.algafood.domain.model.Cozinha;

@Component
public class CozinhaModelAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	public CozinhaModelAssembler() {
		super(CozinhaController.class, CozinhaModel.class);
	}

	@Override
	public CozinhaModel toModel(Cozinha cozinha) {
		CozinhaModel cozinhaModel = modelMapper.map(cozinha, CozinhaModel.class);
		
		cozinhaModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CozinhaController.class).buscar(cozinhaModel.getId())).withSelfRel());
		cozinhaModel.add(WebMvcLinkBuilder.linkTo(CozinhaController.class).withRel("cozinhas"));
		
		return cozinhaModel;
	}

}
