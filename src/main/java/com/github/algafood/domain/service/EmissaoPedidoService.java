package com.github.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.algafood.domain.exception.NegocioException;
import com.github.algafood.domain.exception.PedidoNaoEncontradoException;
import com.github.algafood.domain.model.Cidade;
import com.github.algafood.domain.model.FormaPagamento;
import com.github.algafood.domain.model.Pedido;
import com.github.algafood.domain.model.Produto;
import com.github.algafood.domain.model.Restaurante;
import com.github.algafood.domain.model.Usuario;
import com.github.algafood.domain.repository.PedidoRepository;

@Service
public class EmissaoPedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private CadastroCidadeService cidadeService;

	@Autowired
	private CadastroRestauranteService restauranteService;

	@Autowired
	private CadastroUsuarioService usuarioService;

	@Autowired
	private CadastroFormaPagamentoService formaPagamentoService;

	@Autowired
	private CadastroProdutoService produtoService;

	public Pedido buscarOuFalhar(String codigoPedido) {
		return pedidoRepository.findByCodigo(codigoPedido).orElseThrow(() -> new PedidoNaoEncontradoException(codigoPedido));
	}

	@Transactional
	public Pedido emitir(Pedido pedido) {
		this.validarPedido(pedido);
		this.validarItensPedido(pedido);

		pedido.calcularValorTotal();

		return pedidoRepository.save(pedido);
	}

	private void validarPedido(Pedido pedido) {
		Cidade cidade = cidadeService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
		Restaurante restaurante = restauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
		Usuario cliente = usuarioService.buscarOuFalhar(pedido.getCliente().getId());
		FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());

		pedido.setTaxaFrete(restaurante.getTaxaFrete());

		if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
			throw new NegocioException(String.format("Forma de pagamento %s não é aceita por este restaurante.",
					formaPagamento.getDescricao()));
		}
	}

	private void validarItensPedido(Pedido pedido) {

		pedido.getItens().forEach(item -> {
			Produto produto = produtoService.buscarOuFalhar(item.getProduto().getId(), pedido.getRestaurante().getId());

			item.setPedido(pedido);
			item.setProduto(produto);
			item.setPrecoUnitario(produto.getPreco());
		});
	}

}
