package com.github.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.algafood.api.controller.PedidoController;
import com.github.algafood.api.controller.UsuarioController;
import com.github.algafood.api.dto.PedidoResumoModel;
import com.github.algafood.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

	@Autowired
	private ModelMapper modelMapper;

	public PedidoResumoModelAssembler() {
		super(PedidoController.class, PedidoResumoModel.class);
	}

	@Override
	public PedidoResumoModel toModel(Pedido pedido) {
		PedidoResumoModel pedidoResumoModel = modelMapper.map(pedido, PedidoResumoModel.class);

		pedidoResumoModel.getCliente()
				.add(linkTo(methodOn(UsuarioController.class).buscarPorId(pedidoResumoModel.getCliente().getId()))
						.withSelfRel());

		return pedidoResumoModel;
	}

}
