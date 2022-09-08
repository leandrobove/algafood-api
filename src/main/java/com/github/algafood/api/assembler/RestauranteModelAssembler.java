package com.github.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.algafood.api.AlgaLinksHelper;
import com.github.algafood.api.controller.RestauranteController;
import com.github.algafood.api.dto.RestauranteModel;
import com.github.algafood.core.security.AlgaSecurity;
import com.github.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinksHelper algaLinksHelper;
	
	@Autowired
	private AlgaSecurity algaSecurity;

	public RestauranteModelAssembler() {
		super(RestauranteController.class, RestauranteModel.class);
	}

	@Override
	public RestauranteModel toModel(Restaurante restaurante) {
		RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
		modelMapper.map(restaurante, restauranteModel);

		// add link restaurante
		if(algaSecurity.podeConsultarRestaurantes()) {
			restauranteModel.add(algaLinksHelper.linkToRestaurantes("restaurantes"));
			restauranteModel.add(algaLinksHelper.linkToProdutos(restaurante.getId(), "produtos"));
		}

		// add link cozinha
		if(algaSecurity.podeConsultarCozinhas()) {
			restauranteModel.getCozinha().add(algaLinksHelper.linkToCozinha(restaurante.getCozinha().getId()));
		}

		// add link cidade
		if(algaSecurity.podeConsultarCidades()) {
			if (restauranteModel.getEndereco() != null 
		            && restauranteModel.getEndereco().getCidade() != null) {
				restauranteModel.getEndereco().getCidade().add(algaLinksHelper.linkToCidade(restaurante.getEndereco().getCidade().getId()));
			}
		}

		// add link formas pagamento
		if(algaSecurity.podeConsultarRestaurantes()) {
			restauranteModel.add(algaLinksHelper.linkToRestauranteFormaPagamento(restaurante.getId(), "formas-pagamento"));
		}
		
		// add link responsaveis
		if(algaSecurity.podeGerenciarCadastroRestaurantes()) {
			restauranteModel.add(algaLinksHelper.linkToRestauranteResponsaveis(restaurante.getId(), "responsaveis"));
		}
		
		//links de abertura e fechamento condicionais
		if(algaSecurity.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {
			if(restaurante.aberturaPermitida()) {
				restauranteModel.add(algaLinksHelper.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
			}
			
			if(restaurante.fechamentoPermitido()) {
				restauranteModel.add(algaLinksHelper.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
			}
		}
		
		//links de ativacao, inativacao
		if(algaSecurity.podeGerenciarCadastroRestaurantes()) {
			if(restaurante.ativacaoPermitida()) {
				restauranteModel.add(algaLinksHelper.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
			}
			
			if(restaurante.inativacaoPermitida()) {
				restauranteModel.add(algaLinksHelper.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
			}
		}

		return restauranteModel;
	}

	@Override
	public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
		CollectionModel<RestauranteModel> restaurantesModel = super.toCollectionModel(entities);
		
		if(algaSecurity.podeConsultarRestaurantes()) {
			restaurantesModel.add(algaLinksHelper.linkToRestaurantes());
		}
		
		return restaurantesModel;
	}

}
