package com.github.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.algafood.api.controller.CidadeController;
import com.github.algafood.api.controller.FormaPagamentoController;
import com.github.algafood.api.controller.PedidoController;
import com.github.algafood.api.controller.RestauranteController;
import com.github.algafood.api.controller.RestauranteProdutoController;
import com.github.algafood.api.controller.UsuarioController;
import com.github.algafood.api.dto.PedidoModel;
import com.github.algafood.domain.model.Pedido;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	public PedidoModelAssembler() {
		super(PedidoController.class, PedidoModel.class);
	}

	@Override
	public PedidoModel toModel(Pedido pedido) {
		
		PedidoModel pedidoModel = modelMapper.map(pedido, PedidoModel.class);
		
		//hypermedia Pedido
		pedidoModel.add(linkTo(methodOn(PedidoController.class).buscar(pedidoModel.getCodigo())).withSelfRel());
		pedidoModel.add(linkTo(PedidoController.class).withRel("pedidos"));
		
		//Restaurante
		pedidoModel.getRestaurante().add(linkTo(methodOn(RestauranteController.class).buscar(pedidoModel.getRestaurante().getId())).withSelfRel());
		
		//cliente
		pedidoModel.getCliente().add(linkTo(methodOn(UsuarioController.class).buscarPorId(pedidoModel.getCliente().getId())).withSelfRel());
		
		//forma pagamento
		pedidoModel.getFormaPagamento().add(linkTo(methodOn(FormaPagamentoController.class).buscar(pedidoModel.getFormaPagamento().getId(), null)).withSelfRel());
		
		//cidade
		pedidoModel.getEnderecoEntrega().getCidade().add(linkTo(methodOn(CidadeController.class).buscar(pedidoModel.getEnderecoEntrega().getCidade().getId())).withSelfRel());
		
		//produto / item pedido
		pedidoModel.getItens().forEach((item) -> {
			item.add(linkTo(methodOn(RestauranteProdutoController.class)
                    .buscar(pedidoModel.getRestaurante().getId(), item.getProdutoId()))
                    .withRel("produto"));
		});
		
		return pedidoModel;
	}

}
