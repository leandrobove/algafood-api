package com.github.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.algafood.api.AlgaLinksHelper;
import com.github.algafood.api.controller.PedidoController;
import com.github.algafood.api.dto.PedidoResumoModel;
import com.github.algafood.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinksHelper algaLinksHelper;

	public PedidoResumoModelAssembler() {
		super(PedidoController.class, PedidoResumoModel.class);
	}

	@Override
	public PedidoResumoModel toModel(Pedido pedido) {
		
		PedidoResumoModel pedidoResumoModel = createModelWithId(pedido.getCodigo(), pedido);
		
		modelMapper.map(pedido, pedidoResumoModel);
		
		pedidoResumoModel.add(algaLinksHelper.linkToPedidos("pedidos"));

		pedidoResumoModel.getCliente().add(algaLinksHelper.linkToUsuario(pedidoResumoModel.getCliente().getId()));
		
		pedidoResumoModel.getRestaurante().add(algaLinksHelper.linkToRestaurante(pedido.getRestaurante().getId()));

		return pedidoResumoModel;
	}

}
