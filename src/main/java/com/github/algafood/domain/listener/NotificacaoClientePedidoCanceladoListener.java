package com.github.algafood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.github.algafood.domain.event.PedidoCanceladoEvent;
import com.github.algafood.domain.model.Pedido;
import com.github.algafood.domain.service.EnvioEmailService;
import com.github.algafood.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoClientePedidoCanceladoListener {
	
	@Autowired
	private EnvioEmailService emailService;
	
	@TransactionalEventListener
	public void aoCancelarPedido(PedidoCanceladoEvent event) {
		
		Pedido pedido = event.getPedido();
		
		var mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - Pedido cancelado")
				.corpo("emails/pedido-cancelado.html")
				.variavel("pedido", pedido)
				.destinatario(pedido.getCliente().getEmail())
				.build();
		
		emailService.enviar(mensagem);
	}

}
