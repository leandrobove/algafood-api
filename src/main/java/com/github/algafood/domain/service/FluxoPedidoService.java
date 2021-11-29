package com.github.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.algafood.domain.model.Pedido;
import com.github.algafood.domain.service.EnvioEmailService.Mensagem;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedidoService;
	
	@Autowired
	private EnvioEmailService emailService;

	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);

		pedido.confirmar();
		
		Mensagem mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
				.corpo("O pedido de código <strong>" + pedido.getCodigo() + "</strong> foi confirmado!")
				.destinatario(pedido.getCliente().getEmail())
				.build();
		
		emailService.enviar(mensagem);
	}

	@Transactional
	public void cancelar(String codigoPedido) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);

		pedido.cancelar();
	}

	@Transactional
	public void entregar(String codigoPedido) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);

		pedido.entregar();
	}

}
