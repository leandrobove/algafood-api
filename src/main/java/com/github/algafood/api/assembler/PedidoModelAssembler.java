package com.github.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.algafood.api.AlgaLinksHelper;
import com.github.algafood.api.controller.PedidoController;
import com.github.algafood.api.dto.PedidoModel;
import com.github.algafood.core.security.AlgaSecurity;
import com.github.algafood.domain.model.Pedido;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinksHelper algaLinksHelper;

	@Autowired
	private AlgaSecurity algaSecurity;

	public PedidoModelAssembler() {
		super(PedidoController.class, PedidoModel.class);
	}

	@Override
	public PedidoModel toModel(Pedido pedido) {
		PedidoModel pedidoModel = this.createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoModel);

		// hypermedia Pedidos
		if (algaSecurity.podePesquisarPedidos()) {
			pedidoModel.add(algaLinksHelper.linkToPedidos("pedidos"));
		}

		if (algaSecurity.podeGerenciarPedidos(pedido.getCodigo())) {
			if (pedido.podeSerConfirmado()) {
				// Confirmação Pedido
				pedidoModel.add(algaLinksHelper.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
			}

			if (pedido.podeSerCancelado()) {
				// Cancelamento Pedido
				pedidoModel.add(algaLinksHelper.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
			}

			if (pedido.podeSerEntregue()) {
				// Entrega Pedido
				pedidoModel.add(algaLinksHelper.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
			}
		}

		// Restaurante
		if (algaSecurity.podeConsultarRestaurantes()) {
			pedidoModel.getRestaurante().add(algaLinksHelper.linkToRestaurante(pedido.getRestaurante().getId()));
		}

		// cliente
		if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			pedidoModel.getCliente().add(algaLinksHelper.linkToUsuario(pedidoModel.getCliente().getId()));
		}

		// forma pagamento
		if (algaSecurity.podeConsultarFormasPagamento()) {
			pedidoModel.getFormaPagamento()
					.add(algaLinksHelper.linkToFormaPagamento(pedidoModel.getFormaPagamento().getId()));
		}

		// cidade
		if (algaSecurity.podeConsultarCidades()) {
			pedidoModel.getEnderecoEntrega().getCidade()
					.add(algaLinksHelper.linkToCidade(pedidoModel.getEnderecoEntrega().getCidade().getId()));
		}

		// produto / item pedido
		// Quem pode consultar restaurantes, também pode consultar os produtos dos
		// restaurantes
		if (algaSecurity.podeConsultarRestaurantes()) {
			pedidoModel.getItens().forEach((item) -> {
				item.add(algaLinksHelper.linkToProduto(pedidoModel.getRestaurante().getId(), item.getProdutoId(),
						"produto"));
			});
		}

		return pedidoModel;
	}

}
