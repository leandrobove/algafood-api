package com.github.algafood.api.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTO {

	private String codigo;
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private String status;
	private OffsetDateTime dataCriacao;
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataEntrega;
	private OffsetDateTime dataCancelamento;

	private RestauranteResumoDTO restaurante;
	private UsuarioDTO cliente;
	private FormaPagamentoDTO formaPagamento;
	private EnderecoDTO enderecoEntrega;
	private List<ItemPedidoDTO> itens;
	
}
