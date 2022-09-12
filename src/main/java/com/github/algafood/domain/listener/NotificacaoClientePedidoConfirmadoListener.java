package com.github.algafood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.github.algafood.domain.event.PedidoConfirmadoEvent;
import com.github.algafood.domain.model.Pedido;
import com.github.algafood.domain.service.EnvioEmailService;
import com.github.algafood.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

	@Autowired
	private EnvioEmailService emailService;

	// @EventListener
	@TransactionalEventListener // executado somente depois do commit
	public void aoConfirmarPedido(PedidoConfirmadoEvent event) {

		Pedido pedido = event.getPedido();

		Mensagem mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
				.corpo("emails/pedido-confirmado.html")
				.destinatario(pedido.getCliente().getEmail())
				.variavel("pedido", pedido)
				.build();

		emailService.enviar(mensagem);

	}

}
