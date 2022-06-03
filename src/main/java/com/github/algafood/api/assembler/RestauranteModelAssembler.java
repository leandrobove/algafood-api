package com.github.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.algafood.api.AlgaLinksHelper;
import com.github.algafood.api.controller.RestauranteController;
import com.github.algafood.api.dto.RestauranteModel;
import com.github.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinksHelper algaLinksHelper;

	public RestauranteModelAssembler() {
		super(RestauranteController.class, RestauranteModel.class);
	}

	@Override
	public RestauranteModel toModel(Restaurante restaurante) {

		RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);

		modelMapper.map(restaurante, restauranteModel);

		// add link restaurante
		restauranteModel.add(algaLinksHelper.linkToRestaurantes("restaurantes"));

		// add link cozinha
		restauranteModel.getCozinha().add(algaLinksHelper.linkToCozinha(restaurante.getCozinha().getId()));

		// add link cidade
		restauranteModel.getEndereco().getCidade()
				.add(algaLinksHelper.linkToCidade(restaurante.getEndereco().getCidade().getId()));

		// add link formas pagamento
		restauranteModel.add(algaLinksHelper.linkToRestauranteFormaPagamento(restaurante.getId(), "formas-pagamento"));

		// add link responsaveis
		restauranteModel.add(algaLinksHelper.linkToRestauranteResponsaveis(restaurante.getId(), "responsaveis"));

		return restauranteModel;
	}

	@Override
	public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities).add(algaLinksHelper.linkToRestaurantes());
	}

}
