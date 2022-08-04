package com.github.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.algafood.api.AlgaLinksHelper;
import com.github.algafood.api.controller.PedidoController;
import com.github.algafood.api.dto.PedidoModel;
import com.github.algafood.domain.model.Pedido;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinksHelper algaLinksHelper;

	public PedidoModelAssembler() {
		super(PedidoController.class, PedidoModel.class);
	}

	@Override
	public PedidoModel toModel(Pedido pedido) {

		PedidoModel pedidoModel = this.createModelWithId(pedido.getCodigo(), pedido);
				
		modelMapper.map(pedido, pedidoModel);

		// hypermedia Pedidos
		pedidoModel.add(algaLinksHelper.linkToPedidos("pedidos"));
		
		if(pedido.podeSerConfirmado()) {
			// Confirmação Pedido
			pedidoModel.add(algaLinksHelper.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
		}
		
		if(pedido.podeSerCancelado()) {
			// Cancelamento Pedido
			pedidoModel.add(algaLinksHelper.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
		}
				
		if(pedido.podeSerEntregue()) {
			// Entrega Pedido
			pedidoModel.add(algaLinksHelper.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
		}

		// Restaurante
		pedidoModel.getRestaurante().add(algaLinksHelper.linkToRestaurante(pedido.getRestaurante().getId()));

		// cliente
		pedidoModel.getCliente().add(algaLinksHelper.linkToUsuario(pedidoModel.getCliente().getId()));

		// forma pagamento
		pedidoModel.getFormaPagamento()
				.add(algaLinksHelper.linkToFormaPagamento(pedidoModel.getFormaPagamento().getId()));

		// cidade
		pedidoModel.getEnderecoEntrega().getCidade()
				.add(algaLinksHelper.linkToCidade(pedidoModel.getEnderecoEntrega().getCidade().getId()));

		// produto / item pedido
		pedidoModel.getItens().forEach((item) -> {
			item.add(algaLinksHelper.linkToProduto(pedidoModel.getRestaurante().getId(), item.getProdutoId(),
					"produto"));
		});

		return pedidoModel;
	}

}
