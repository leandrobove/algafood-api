package com.github.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.algafood.api.dto.PedidoDTO;
import com.github.algafood.domain.model.Pedido;

@Component
public class PedidoAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public PedidoDTO toDTO(Pedido pedido) {
		return modelMapper.map(pedido, PedidoDTO.class);
	}

	public List<PedidoDTO> toListDTO(List<Pedido> pedidos) {
		return pedidos.stream().map((pedido) -> this.toDTO(pedido)).collect(Collectors.toList());
	}
}
