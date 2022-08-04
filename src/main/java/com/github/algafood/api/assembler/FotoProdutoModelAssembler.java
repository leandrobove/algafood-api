package com.github.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.algafood.api.AlgaLinksHelper;
import com.github.algafood.api.controller.RestauranteFotoProdutoController;
import com.github.algafood.api.dto.FotoProdutoModel;
import com.github.algafood.domain.model.FotoProduto;

@Component
public class FotoProdutoModelAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinksHelper algaLinksHelper;

	public FotoProdutoModelAssembler() {
		super(RestauranteFotoProdutoController.class, FotoProdutoModel.class);
	}

	@Override
	public FotoProdutoModel toModel(FotoProduto fotoProduto) {
		FotoProdutoModel fotoProdutoModel = modelMapper.map(fotoProduto, FotoProdutoModel.class);

		fotoProdutoModel.add(
				algaLinksHelper.linkToFotoProduto(fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId()));

		fotoProdutoModel.add(algaLinksHelper.linkToProduto(fotoProduto.getRestauranteId(),
				fotoProduto.getProduto().getId(), "produto"));

		return fotoProdutoModel;
	}

}
