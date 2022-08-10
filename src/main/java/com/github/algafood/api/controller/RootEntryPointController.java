package com.github.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.algafood.api.AlgaLinksHelper;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {
	
	@Autowired
	private AlgaLinksHelper algaLinksHelper;

	@GetMapping
	public RootEntryPointModel getRoot() {
		RootEntryPointModel rootEntryPointModel = new RootEntryPointModel();
		
		rootEntryPointModel.add(algaLinksHelper.linkToCozinhas("cozinhas"));
		rootEntryPointModel.add(algaLinksHelper.linkToPedidos("pedidos"));
		rootEntryPointModel.add(algaLinksHelper.linkToRestaurantes("restaurantes"));
		rootEntryPointModel.add(algaLinksHelper.linkToGrupos("grupos"));
		rootEntryPointModel.add(algaLinksHelper.linkToUsuarios("usuarios"));
		rootEntryPointModel.add(algaLinksHelper.linkToPermissoes("permissoes"));
		rootEntryPointModel.add(algaLinksHelper.linkToFormasPagamento("formas-pagamento"));
		rootEntryPointModel.add(algaLinksHelper.linkToEstados("estados"));
		rootEntryPointModel.add(algaLinksHelper.linkToCidades("cidades"));
		rootEntryPointModel.add(algaLinksHelper.linkToEstatisticas("estatisticas"));
		
		return rootEntryPointModel;
	}

	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
	}

}
