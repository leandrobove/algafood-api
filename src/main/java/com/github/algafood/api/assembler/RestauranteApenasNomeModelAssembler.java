package com.github.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.algafood.api.AlgaLinksHelper;
import com.github.algafood.api.controller.RestauranteController;
import com.github.algafood.api.dto.RestauranteApenasNomeModel;
import com.github.algafood.core.security.AlgaSecurity;
import com.github.algafood.domain.model.Restaurante;

@Component
public class RestauranteApenasNomeModelAssembler
		extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinksHelper algaLinksHelper;
	
	@Autowired
	private AlgaSecurity algaSecurity;

	public RestauranteApenasNomeModelAssembler() {
		super(RestauranteController.class, RestauranteApenasNomeModel.class);
	}

	@Override
	public RestauranteApenasNomeModel toModel(Restaurante restaurante) {
		RestauranteApenasNomeModel restauranteApenasNomeModel = createModelWithId(restaurante.getId(), restaurante);
		modelMapper.map(restaurante, restauranteApenasNomeModel);

		if(algaSecurity.podeConsultarRestaurantes()) {
			restauranteApenasNomeModel.add(algaLinksHelper.linkToRestaurantes("restaurantes"));
		}

		return restauranteApenasNomeModel;
	}

	@Override
	public CollectionModel<RestauranteApenasNomeModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
		CollectionModel<RestauranteApenasNomeModel> restaurantesApenasNomeModel = super.toCollectionModel(entities);
		
		if(algaSecurity.podeConsultarRestaurantes()) {
			restaurantesApenasNomeModel.add(algaLinksHelper.linkToRestaurantes());
		}
		
		return restaurantesApenasNomeModel;
	}

}
