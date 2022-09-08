package com.github.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.algafood.api.AlgaLinksHelper;
import com.github.algafood.api.controller.RestauranteProdutoController;
import com.github.algafood.api.dto.ProdutoModel;
import com.github.algafood.core.security.AlgaSecurity;
import com.github.algafood.domain.model.Produto;

@Component
public class ProdutoModelAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinksHelper algaLinksHelper;
	
	@Autowired
	private AlgaSecurity algaSecurity;
	
	public ProdutoModelAssembler() {
		super(RestauranteProdutoController.class, ProdutoModel.class);
	}

	@Override
	public ProdutoModel toModel(Produto produto) {
		ProdutoModel produtoModel = this.createModelWithId(produto.getId(), produto, produto.getRestaurante().getId());
		modelMapper.map(produto, produtoModel);
		
		if(algaSecurity.podeConsultarRestaurantes()) {
			produtoModel.add(algaLinksHelper.linkToProdutos(produto.getRestaurante().getId(), "produtos"));
			produtoModel.add(algaLinksHelper.linkToFotoProduto(produto.getRestaurante().getId(), produto.getId(), "foto"));
		}
		return produtoModel;
	}

}
