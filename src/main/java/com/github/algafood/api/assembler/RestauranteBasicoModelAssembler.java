package com.github.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.algafood.api.AlgaLinksHelper;
import com.github.algafood.api.controller.RestauranteController;
import com.github.algafood.api.dto.RestauranteBasicoModel;
import com.github.algafood.domain.model.Restaurante;

@Component
public class RestauranteBasicoModelAssembler
		extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinksHelper algaLinksHelper;

	public RestauranteBasicoModelAssembler() {
		super(RestauranteController.class, RestauranteBasicoModel.class);
	}

	@Override
	public RestauranteBasicoModel toModel(Restaurante restaurante) {
		RestauranteBasicoModel restauranteBasicoModel = createModelWithId(restaurante.getId(), restaurante);

		modelMapper.map(restaurante, restauranteBasicoModel);

		//add link para restaurantes
		restauranteBasicoModel.add(algaLinksHelper.linkToRestaurantes("restaurantes"));

		//add link para um único restaurante
		restauranteBasicoModel.getCozinha().add(algaLinksHelper.linkToCozinha(restaurante.getCozinha().getId()));

		return restauranteBasicoModel;
	}

	@Override
	public CollectionModel<RestauranteBasicoModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities).add(algaLinksHelper.linkToRestaurantes());
	}

}
