package com.github.algafood.api.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Relation(collectionRelation = "pedidos")
public class PedidoModel extends RepresentationModel<PedidoModel> {

	@ApiModelProperty(example = "7035f4b8-a79a-4c2b-9a7e-a6f4c200f269")
	private String codigo;
	
	@ApiModelProperty(example = "298.90")
	private BigDecimal subtotal;
	
	@ApiModelProperty(example = "10.00")
	private BigDecimal taxaFrete;
	
	@ApiModelProperty(example = "308.90")
	private BigDecimal valorTotal;
	
	@ApiModelProperty(example = "CRIADO")
	private String status;
	
	@ApiModelProperty(example = "2019-12-01T20:34:04Z")
	private OffsetDateTime dataCriacao;
	
	@ApiModelProperty(example = "2019-12-01T20:36:10Z")
	private OffsetDateTime dataConfirmacao;
	
	@ApiModelProperty(example = "2019-12-01T20:55:30Z")
	private OffsetDateTime dataEntrega;
	
	@ApiModelProperty(example = "2019-12-01T20:37:00Z")
	private OffsetDateTime dataCancelamento;

	private RestauranteApenasNomeModel restaurante;
	private UsuarioModel cliente;
	private FormaPagamentoModel formaPagamento;
	private EnderecoModel enderecoEntrega;
	private List<ItemPedidoModel> itens;

}
