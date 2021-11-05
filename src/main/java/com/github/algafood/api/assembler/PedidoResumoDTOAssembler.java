package com.github.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.algafood.api.dto.PedidoResumoDTO;
import com.github.algafood.domain.model.Pedido;

@Component
public class PedidoResumoDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public PedidoResumoDTO toDTO(Pedido pedido) {
		return modelMapper.map(pedido, PedidoResumoDTO.class);
	}

	public List<PedidoResumoDTO> toListDTO(List<Pedido> pedidos) {
		return pedidos.stream().map((pedido) -> this.toDTO(pedido)).collect(Collectors.toList());
	}
}
