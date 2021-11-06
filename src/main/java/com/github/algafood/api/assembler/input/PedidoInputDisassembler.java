package com.github.algafood.api.assembler.input;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.algafood.api.dto.input.PedidoInput;
import com.github.algafood.domain.model.Pedido;

@Component
public class PedidoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Pedido toPedido(PedidoInput pedidoInput) {
		return modelMapper.map(pedidoInput, Pedido.class);
	}

	public void copyToDomainObject(PedidoInput pedidoInput, Pedido pedido) {
		modelMapper.map(pedidoInput, pedido);
	}
}
