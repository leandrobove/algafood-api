package com.github.algafood.api.assembler;

import org.springframework.stereotype.Component;

import com.github.algafood.api.dto.PedidoDTO;
import com.github.algafood.domain.model.Pedido;

@Component
public class PedidoAssembler extends GenericModelAssembler<Pedido, PedidoDTO> {

}
