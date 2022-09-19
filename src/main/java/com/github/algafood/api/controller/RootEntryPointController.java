package com.github.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.algafood.api.AlgaLinksHelper;
import com.github.algafood.core.security.AlgaSecurity;

import io.swagger.v3.oas.annotations.Hidden;
import springfox.documentation.annotations.ApiIgnore;

@Hidden
@ApiIgnore
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

	@Autowired
	private AlgaLinksHelper algaLinksHelper;

	@Autowired
	private AlgaSecurity algaSecurity;

	@GetMapping
	public RootEntryPointModel getRoot() {
		RootEntryPointModel rootEntryPointModel = new RootEntryPointModel();

		if (algaSecurity.podeConsultarCozinhas()) {
			rootEntryPointModel.add(algaLinksHelper.linkToCozinhas("cozinhas"));
		}
		if (algaSecurity.podePesquisarPedidos()) {
			rootEntryPointModel.add(algaLinksHelper.linkToPedidos("pedidos"));
		}
		if (algaSecurity.podeConsultarRestaurantes()) {
			rootEntryPointModel.add(algaLinksHelper.linkToRestaurantes("restaurantes"));
		}
		if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			rootEntryPointModel.add(algaLinksHelper.linkToGrupos("grupos"));
			rootEntryPointModel.add(algaLinksHelper.linkToUsuarios("usuarios"));
			rootEntryPointModel.add(algaLinksHelper.linkToPermissoes("permissoes"));
		}
		if (algaSecurity.podeConsultarFormasPagamento()) {
			rootEntryPointModel.add(algaLinksHelper.linkToFormasPagamento("formas-pagamento"));
		}
		if (algaSecurity.podeConsultarEstados()) {
			rootEntryPointModel.add(algaLinksHelper.linkToEstados("estados"));
		}
		if (algaSecurity.podeConsultarCidades()) {
			rootEntryPointModel.add(algaLinksHelper.linkToCidades("cidades"));
		}
		if (algaSecurity.podeConsultarEstatisticas()) {
			rootEntryPointModel.add(algaLinksHelper.linkToEstatisticas("estatisticas"));
		}

		return rootEntryPointModel;
	}

	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
	}

}
