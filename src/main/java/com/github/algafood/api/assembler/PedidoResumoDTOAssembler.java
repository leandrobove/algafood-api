package com.github.algafood.api.assembler;

import org.springframework.stereotype.Component;

import com.github.algafood.api.dto.PedidoResumoDTO;
import com.github.algafood.domain.model.Pedido;

@Component
public class PedidoResumoDTOAssembler extends GenericModelAssembler<Pedido, PedidoResumoDTO> {

}
